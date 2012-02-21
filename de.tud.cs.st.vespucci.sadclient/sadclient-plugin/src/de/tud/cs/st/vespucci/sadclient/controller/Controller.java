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
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.tud.cs.st.vespucci.sadclient.Activator;
import de.tud.cs.st.vespucci.sadclient.concurrent.Callback;
import de.tud.cs.st.vespucci.sadclient.concurrent.RunnableWithCallback;
import de.tud.cs.st.vespucci.sadclient.model.SAD;
import de.tud.cs.st.vespucci.sadclient.model.SADClient;
import de.tud.cs.st.vespucci.sadclient.model.SADClientException;
import de.tud.cs.st.vespucci.sadclient.model.Transaction;
import de.tud.cs.st.vespucci.sadclient.model.http.RequestException;
import de.tud.cs.st.vespucci.sadclient.view.IconAndMessageDialogs;

/**
 * The me Responsible for starting computations in a separate thread.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class Controller {

    final private static Controller instance = new Controller();
    final private SADClient sadClient;
    private ExecutorService pool;
    private Future<SAD[]> sadCollectionFuture;

    private Controller() {
	pool = Executors.newFixedThreadPool(4);
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
    }

    /**
     * Returns
     * 
     * @param shell
     * @return
     * @throws Exception
     * @throws InterruptedException
     * @throws SADClientException
     */
    public SAD[] getSADCollection() {
	getSADCollectionFromServer();
	try {
	    return sadCollectionFuture.get();
	} catch (Exception e) {
	    throw new SADClientException(e);
	}
    }

    /**
     * Executes a working thread getting the SADs from the server.
     */
    public void getSADCollectionFromServer() {
	// TODO is it wise to cancel a running future before?
	sadCollectionFuture = pool.submit(new Callable<SAD[]>() {
	    @Override
	    public SAD[] call() throws Exception {
		return sadClient.getSADCollection();
	    }
	});
    }

    public void getSAD(final String id, Callback<SAD> callback) {
	System.out.println("Calling getSAD with id " + id + " and callback " + callback);
	pool.execute(new RunnableWithCallback<SAD>(new Callable<SAD>() {
	    @Override
	    public SAD call() throws Exception {
		return sadClient.getSAD(id);
	    }
	}, callback));
    }

    public void downloadModel(final String id, final File downloadLocation) {
	Job job = new Job("Download Model") {
	    @Override
	    protected IStatus run(IProgressMonitor monitor) {
		try {
		    IOUtils.write(sadClient.getModel(id, downloadLocation, monitor), new FileOutputStream(
			    downloadLocation));
		} catch (Exception e) {
		    return new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage());
		}
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Model downloaded.");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    public void downloadDocumentation(final String id, final File downloadLocation) {
	Job job = new Job("Download Documentation") {
	    @Override
	    protected IStatus run(IProgressMonitor monitor) {
		try {
		    IOUtils.write(sadClient.getDocumentation(id, downloadLocation, monitor), new FileOutputStream(
			    downloadLocation));
		} catch (Exception e) {
		    new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage());
		}
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Documentation downloaded.");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

    public void storeSAD(final boolean descriptionChanged, final SAD sad, final boolean deleteModel,
	    final File modelFile, final boolean deleteDoc, final File docFile, final Callback<SAD> callback) {
	Job job = new Job("Uploading changes SADServer...") {
	Job job = new Job("Uploading changes to SADServer...") {
	    @Override
	    protected IStatus run(IProgressMonitor monitor) {
		String transactionId = null;
		try {
		    Transaction transaction = sadClient.startTransaction(sad.getId());
		    System.out.println(transaction);
		    transactionId = transaction.getTransactionId();
		    try {
			if (descriptionChanged) {
			    sadClient.storeSAD(transactionId, sad);
			}
			if (deleteModel) {
			    sadClient.deleteModel(transactionId);
			} else if (modelFile != null) {
			    sadClient.putModel(transactionId, modelFile, monitor);
			}
			if (deleteDoc) {
			    sadClient.deleteDocumentation(transactionId);
			} else if (docFile != null) {
			    sadClient.putDocumentation(transactionId, docFile, monitor);
			}
			sadClient.commitTransaction(transactionId);
		    } catch (RequestException e) {
			sadClient.rollbackTransaction(transactionId);
			throw e;
		    }
		} catch (Exception e) {
		    IconAndMessageDialogs.showErrorDialog("Upload to SADServer failed.", e.getMessage());
		    e.printStackTrace();
		    return new Status(IStatus.WARNING, Activator.PLUGIN_ID, e.getMessage());
		}

		return new Status(IStatus.OK, Activator.PLUGIN_ID, "OK");
	    }
	};
	job.setUser(true);
	job.schedule();
    }

}
