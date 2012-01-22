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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.tud.cs.st.vespucci.sadclient.model.Client;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * Encapsulates state of the interaction and manages threads.
 * 
 * @author Mateusz Parzonka
 *
 */
public class Controller {
    
    final private static Controller instance = new Controller();
    final private Client client;
    private ExecutorService pool;
    private Future<SAD[]> sadCollectionFuture;

    private Controller() {
	pool = Executors.newFixedThreadPool(2);
	client = new Client();
    }

    public static Controller getInstance() {
	return instance;
    }
    
    public void start() {
	pool = Executors.newFixedThreadPool(2);
    }
    
    public void stop() {
	pool.shutdown();
    }

    /**
     * Returns 
     * @param shell
     * @return
     * @throws SADClientException
     */
    public SAD[] getSADCollection(Shell shell) {
	refreshSADCollection();
	try {
	    return sadCollectionFuture.get();
	} catch (Exception e) {
	   e.printStackTrace();
	   return null;
	}
    }
    
    /**
     * Executes a working thread getting the SADs from the server.
     */
    public void refreshSADCollection() {
	// TODO is it wise to cancel a running future before?
	sadCollectionFuture = pool.submit(new Callable<SAD[]>() {
	    @Override
	    public SAD[] call() throws Exception {
		return client.getSADCollection();
	    }
	});
    }

    public void saveDescription(Control control) {
	refreshSADCollection();
    }

    public void deleteModel(Control control) {
    }

    public void downloadModel(Control control) {
    }

    public void uploadModel(Control control) {
    }

    public void deleteDocumentation(Control control) {
    }

    public void downloadDocumentation(Control control) {
    }

    public void uploadDocumentation(Control control) {
    }

}
