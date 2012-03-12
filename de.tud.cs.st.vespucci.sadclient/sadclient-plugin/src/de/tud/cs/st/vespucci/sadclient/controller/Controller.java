/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.Viewer;

import de.tud.cs.st.vespucci.sadclient.Activator;
import de.tud.cs.st.vespucci.sadclient.model.SAD;
import de.tud.cs.st.vespucci.sadclient.model.SADClient;
import de.tud.cs.st.vespucci.sadclient.model.Transaction;
import de.tud.cs.st.vespucci.sadclient.model.http.RequestException;
import de.tud.cs.st.vespucci.sadclient.preferences.PreferenceConstants;
import de.tud.cs.st.vespucci.sadclient.preferences.SADClientPreferences;
import de.tud.cs.st.vespucci.sadclient.view.IconAndMessageDialogs;
import de.tud.cs.st.vespucci.sadclient.view.OverwriteDialog;
import de.tud.cs.st.vespucci.sadclient.view.OverwriteDialog.OverwriteSettings;

/**
 * Gets called by the views and orchestrates the {@link SADClient}.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class Controller {

    final private static Controller instance = new Controller();
    final private SADClient sadClient;
    private ExecutorService pool;

    private Controller() {
	sadClient = new SADClient();
    }

    public static Controller getInstance() {
	return instance;
    }

    /**
     * Called by the activator.
     */
    public void start() {
	pool = Executors.newFixedThreadPool(2);
    }

    /**
     * Called by the activator.
     */
    public void stop() {
	pool.shutdown();
	sadClient.shutdown();
    }

    /**
     * Retrieves the collection of SADs.
     * 
     * 
     */
    public SAD[] getSADCollection() {
	// Executes a working thread getting the SADs from the server. Implementing this
	// functionality with Eclipse Job API was unsuccessful due to missing Future-semantics.
	// job.join was not working as promised.
	try {
	    Future<SAD[]> sadCollectionFuture = pool.submit(new Callable<SAD[]>() {
		@Override
		public SAD[] call() throws Exception {
		    return sadClient.getSADCollection();
		}
	    });
	    return sadCollectionFuture.get();
	} catch (Exception e) {
	    IconAndMessageDialogs.showErrorDialog("Could not connect to server: "
		    + Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVER),
		    e.getMessage());
	    return new SAD[0];
	}
    }

    /**
     * Deletes the SAD at the server.
     * 
     * @param sad
     * @param viewer
     */
    public void deleteSAD(final List<SAD> sads, final Viewer viewer) {
	Job job = new Job("Deleting SADs") {
	    @Override
	    protected IStatus run(IProgressMonitor monitor) {
		try {
		    for (SAD sad : sads) {
			sadClient.deleteSAD(sad.getId());
		    }
		} catch (RequestException e) {
		    refresh(viewer);
		    return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while deleting SADs.", e);
		}
		refresh(viewer);
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "SADs deleted.");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    /**
     * Download all models in a list of SADs at the downloadPath, and documentation files too, if
     * specified in the {@link SADClientPreferences} .
     * 
     * @param sads
     * @param downloadPath
     * @param resourceToRefresh
     *            is refreshed after all files are downloaded into the given folder.
     */
    public void downloadModelsAndDocumentation(final List<SAD> sads, final String downloadPath,
	    final IResource resourceToRefresh) {
	final boolean downloadDocumentation = Activator.getDefault().getPreferenceStore()
		.getBoolean(PreferenceConstants.P_DND_WITH_DOCUMENTATION);
	String jobName = "Downloading Model(s)";
	jobName += downloadDocumentation ? " And Documentation" : "";
	Job job = new Job(jobName) {
	    @Override
	    protected IStatus run(IProgressMonitor progressMonitor) {
		final SubMonitor monitor = SubMonitor.convert(progressMonitor);
		OverwriteSettings overwriteSettings = new OverwriteSettings();
		try {

		    final int modelCount = getModelCount(sads);
		    final int documentationCount = downloadDocumentation ? getDocumentationCount(sads) : 0;
		    String taskMessage = "Downloading " + modelCount + " SAD model(s)";
		    taskMessage += downloadDocumentation ? " and " + documentationCount + " documentation file(s)..."
			    : "...";
		    monitor.beginTask(taskMessage, (modelCount + documentationCount) * 100);
		    for (SAD sad : sads) {
			if (sad.getModel() != null) {
			    byte[] bytes = sadClient.getModel(sad.getId(), monitor.newChild(100));
			    writeBytes(bytes, sad.getModel().getName(), overwriteSettings);
			}
			if (downloadDocumentation && sad.getDocumentation() != null) {
			    byte[] bytes = sadClient.getDocumentation(sad.getId(), monitor.newChild(100));
			    writeBytes(bytes, sad.getDocumentation().getName(), overwriteSettings);
			}
		    }
		    resourceToRefresh.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (OperationCanceledException e) {
		    return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Download canceled.");
		} catch (Exception e) {
		    return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
			    "Error while downloading model and documentation.", e);
		} finally {
		    monitor.done();
		}
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Models and documentation downloaded.");
	    }

	    private int getModelCount(List<SAD> sads) {
		int count = 0;
		for (SAD sad : sads)
		    if (sad.getModel() != null)
			count++;
		return count;
	    }

	    private int getDocumentationCount(List<SAD> sads) {
		int count = 0;
		for (SAD sad : sads)
		    if (sad.getDocumentation() != null)
			count++;
		return count;
	    }

	    private void writeBytes(byte[] bytes, String name, OverwriteSettings overwriteSettings) throws IOException {
		FileOutputStream fos = getFileOutput(name, overwriteSettings);
		IOUtils.write(bytes, fos);
		fos.flush();
		fos.close();
	    }

	    private FileOutputStream getFileOutput(String name, OverwriteSettings overwriteSettings)
		    throws FileNotFoundException {
		return new FileOutputStream(getNextFile(new File(downloadPath + File.separator + name),
			overwriteSettings));
	    }

	    /**
	     * Returns a file which is either
	     * <ul>
	     * <li>the same as the given file, when <code>overwriteSettings.isOverwrite()</code> is
	     * <code>false</code>
	     * <li>a file which does not exist at the base path of the input file. The name is
	     * suffixed by a digit starting from 1.
	     * </ul>
	     * 
	     * @param file
	     * @param overwriteSettings
	     * @return a file which does not exist at the base path of the input file
	     */
	    private File getNextFile(File file, OverwriteSettings overwriteSettings) {
		if (file.exists()) {
		    if (!overwriteSettings.isApplyToAll()) {
			overwriteSettings = OverwriteDialog.askForSettings(overwriteSettings, file.getName());
			System.out.println(overwriteSettings);
		    }
		    if (!overwriteSettings.isOverwrite()) {
			String path = file.getAbsolutePath();
			String prefix = FilenameUtils.removeExtension(path);
			String suffix = FilenameUtils.getExtension(path);
			int i = 1;
			do {
			    file = new File(String.format("%s %d.%s", prefix, i, suffix));
			    i++;
			} while (file.exists());
		    }
		}
		return file;
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    /**
     * Downloads a single model to disk.
     * 
     * @param id
     * @param downloadLocation
     */
    public void downloadModel(final String id, final File downloadLocation) {
	Job job = new Job("Downloading Model") {
	    @Override
	    protected IStatus run(IProgressMonitor progressMonitor) {
		SubMonitor monitor = SubMonitor.convert(progressMonitor);
		monitor.beginTask("Downloading model " + id + "...", 100);
		try {
		    byte[] bytes = sadClient.getModel(id, monitor);
		    IOUtils.write(bytes, new FileOutputStream(downloadLocation));
		} catch (OperationCanceledException e) {
		    return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Download canceled.");
		} catch (Exception e) {
		    return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while downloading model.", e);
		} finally {
		    monitor.done();
		}
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Model downloaded.");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    /**
     * Downloads a single documentation to disk.
     * 
     * @param id
     * @param downloadLocation
     */
    public void downloadDocumentation(final String id, final File downloadLocation) {
	Job job = new Job("Downloading Documentation") {
	    @Override
	    protected IStatus run(IProgressMonitor progressMonitor) {
		SubMonitor monitor = SubMonitor.convert(progressMonitor);
		monitor.beginTask("Downloading documentation " + id + "...", 100);
		try {
		    byte[] bytes = sadClient.getModel(id, monitor);
		    IOUtils.write(bytes, new FileOutputStream(downloadLocation));
		} catch (OperationCanceledException e) {
		    return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Download canceled.");
		} catch (Exception e) {
		    return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while downloading documentation.", e);
		} finally {
		    monitor.done();
		}
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Documentation downloaded.");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    /**
     * Stores or updates the given SAD and applies the changes passed in the additional fields.
     * 
     * @param sadUpdate
     */
    public void performUpdate(final SADUpdate sadUpdate) {

	final int amount = getAmountOfWork(sadUpdate);
	if (amount > 0) {
	    Job job = new Job("Updating SAD") {
		@Override
		protected IStatus run(IProgressMonitor monitor) {
		    String transactionId = null;
		    try {
			Transaction transaction = sadClient.startTransaction(sadUpdate.getSAD().getId());
			transactionId = transaction.getTransactionId();
			monitor.beginTask("Uploading changes...", amount);
			try {
			    if (sadUpdate.isDescriptionChanged()) {
				monitor.subTask("Updating description...");
				sadClient.storeSAD(transactionId, sadUpdate.getSAD());
			    }
			    if (sadUpdate.isDeleteModel()) {
				monitor.subTask("Deleting model...");
				sadClient.deleteModel(transactionId);
			    } else if (sadUpdate.getModelFile() != null) {
				monitor.subTask("Uploading model file...");
				sadClient.putModel(transactionId, sadUpdate.getModelFile(), monitor);
			    }
			    if (sadUpdate.isDeleteDocumentation()) {
				monitor.subTask("Deleting documentation...");
				sadClient.deleteDocumentation(transactionId);
			    } else if (sadUpdate.getDocumentationFile() != null) {
				monitor.subTask("Uploading documentation file...");
				sadClient.putDocumentation(transactionId, sadUpdate.getDocumentationFile(), monitor);
			    }
			    monitor.subTask("Finishing update...");
			    sadClient.commitTransaction(transactionId);
			    monitor.done();
			} catch (RequestException e) {
			    sadClient.rollbackTransaction(transactionId);
			    throw e;
			}
		    } catch (Exception e) {
			IconAndMessageDialogs.showErrorDialog("Upload to SADServer failed.", e.getMessage());
		    }

		    refresh(sadUpdate.getViewer());

		    return new Status(IStatus.OK, Activator.PLUGIN_ID, "OK");
		}
	    };
	    job.setUser(true);
	    job.schedule();
	}
    }

    /**
     * Returns the total number of bytes to be transferred. Returns 0 when no work has to be done.
     * 
     * @param sadUpdate
     * @return amount of work
     */
    private static int getAmountOfWork(SADUpdate sadUpdate) {
	int amount = 0;
	if (sadUpdate.isDescriptionChanged()) {
	    amount += sadUpdate.getSAD().getName().getBytes().length;
	    amount += sadUpdate.getSAD().getType().getBytes().length;
	    amount += sadUpdate.getSAD().getAbstrct().getBytes().length;
	}
	amount += getAmountOfWork(sadUpdate.isDeleteModel(), sadUpdate.getModelFile());
	amount += getAmountOfWork(sadUpdate.isDeleteDocumentation(), sadUpdate.getDocumentationFile());
	return amount;
    }

    private static int getAmountOfWork(boolean delete, File file) {
	int amount = 0;
	if (delete)
	    amount += 1;
	else if (file != null && file.exists() && !file.isDirectory())
	    amount += file.length();
	return amount;
    }

    private static void refresh(final Viewer viewer) {
	viewer.getControl().getDisplay().asyncExec(new Runnable() {
	    @Override
	    public void run() {
		viewer.refresh();
	    }
	});
    }

}
