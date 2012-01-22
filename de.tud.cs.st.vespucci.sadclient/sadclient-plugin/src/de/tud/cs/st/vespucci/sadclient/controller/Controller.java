/*
   Copyright 2011 Michael Eichberg et al

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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
