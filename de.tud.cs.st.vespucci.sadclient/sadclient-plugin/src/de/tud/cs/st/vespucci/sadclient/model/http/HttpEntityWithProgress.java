package de.tud.cs.st.vespucci.sadclient.model.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * 
 * @author Mateusz Parzonka
 *
 */
public class HttpEntityWithProgress implements HttpEntity {

    private final HttpEntity entity;
    private final InputStream inputStreamWithProgress;

    public HttpEntityWithProgress(HttpEntity entity, IProgressMonitor progressMonitor) {
	super();
	this.entity = entity;
	try {
	    this.inputStreamWithProgress = new InputStreamWithProgress(entity.getContent(), progressMonitor);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public static void attachProgressMonitor(HttpResponse httpResponse, IProgressMonitor progressMonitor) {
	Header[] clHeaders = httpResponse.getHeaders("Content-Length");
	Header header = clHeaders[0];
	int totalSize = Integer.parseInt(header.getValue());
	progressMonitor.beginTask("Download HttpEntity with content-size: " + totalSize, totalSize);
	HttpEntity entity = httpResponse.getEntity();
	httpResponse.setEntity(new HttpEntityWithProgress(entity, progressMonitor));
    }
    
    @Override
    public void consumeContent() throws IOException {
	throw new RuntimeException("Operation is not supported!");
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
	return inputStreamWithProgress;
    }

    @Override
    public Header getContentEncoding() {
	return entity.getContentEncoding();
    }

    @Override
    public long getContentLength() {
	return entity.getContentLength();
    }

    @Override
    public Header getContentType() {
	return entity.getContentType();
    }

    @Override
    public boolean isChunked() {
	return entity.isChunked();
    }

    @Override
    public boolean isRepeatable() {
	return entity.isRepeatable();
    }

    @Override
    public boolean isStreaming() {
	return entity.isStreaming();
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
	if (outstream == null) {
	    throw new IllegalArgumentException("Output stream may not be null");
	}
	InputStream instream = getContent();
	try {
	    byte[] tmp = new byte[4096];
	    int l;
	    while ((l = instream.read(tmp)) != -1) {
		outstream.write(tmp, 0, l);
	    }
	    outstream.flush();
	} finally {
	    instream.close();
	}
    }

    public static class InputStreamWithProgress extends InputStream {

	private final InputStream inputStream;
	private int transferredBytes;
	private final IProgressMonitor progressMonitor;

	public InputStreamWithProgress(InputStream inputStream, IProgressMonitor progressMonitor) {
	    super();
	    this.inputStream = inputStream;
	    this.progressMonitor = progressMonitor;
	    transferredBytes = 0;
	}

	@Override
	public int read() throws IOException {
	    
	    final int workload = 4096;
	    
	    if (transferredBytes++ >= workload) {
		transferredBytes = 0;
		if (progressMonitor.isCanceled())
		    throw new OperationCanceledException();
		progressMonitor.worked(workload);
	    }
	    return inputStream.read();
	}

//	@Override
//	public int available() throws IOException {
//	    return inputStream.available();
//	}

	@Override
	public void close() throws IOException {
	    System.out.println("Closing inner stream");
	    inputStream.close();
	    progressMonitor.done();
	}

//	@Override
//	public synchronized void mark(int readlimit) {
//	    inputStream.mark(readlimit);
//	}
//
//	@Override
//	public boolean markSupported() {
//	    return inputStream.markSupported();
//	}
//
//	@Override
//	public int read(byte[] b, int off, int len) throws IOException {
//	    return inputStream.read(b, off, len);
//	}
//
//	@Override
//	public int read(byte[] b) throws IOException {
//	    return inputStream.read(b);
//	}
//
//	@Override
//	public synchronized void reset() throws IOException {
//	    inputStream.reset();
//	}
//
//	@Override
//	public long skip(long n) throws IOException {
//	    return inputStream.skip(n);
//	}

	
	

    }

}
