package de.tud.cs.st.vespucci.sadclient.model.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.FileEntity;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * 
 * @author Mateusz Parzonka
 * 
 */
public class FileEntityWithProgress extends FileEntity {

    final IProgressMonitor progressMonitor;

    public FileEntityWithProgress(File file, String contentType, IProgressMonitor progressMonitor) {
	super(file, contentType);
	this.progressMonitor = progressMonitor;
	this.progressMonitor.beginTask("Upstream file", (int) file.length());
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {

	final int bufferSize = 4096;

	if (outstream == null) {
	    throw new IllegalArgumentException("Output stream may not be null");
	}
	InputStream instream = new FileInputStream(this.file);
	try {
	    byte[] tmp = new byte[bufferSize];
	    int l;
	    while ((l = instream.read(tmp)) != -1) {
		outstream.write(tmp, 0, l);
		progressMonitor.worked(bufferSize);
	    }
	    outstream.flush();
	} finally {
	    instream.close();
	    progressMonitor.done();
	}
    }

}
