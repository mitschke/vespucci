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
package de.tud.cs.st.vespucci.sadclient;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;

/**
 * Activator starts and stops threading in {@link Controller}. 
 * 
 * @author Mateusz Parzonka
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.sadclient.SADClient"; //$NON-NLS-1$

    private static Activator plugin;

    public Activator() {
    }

    public void start(BundleContext context) throws Exception {
	super.start(context);
	plugin = this;
	Controller.getInstance().start();
    }

    public void stop(BundleContext context) throws Exception {
	plugin = null;
	super.stop(context);
	Controller.getInstance().stop();
    }

    /**
     * @return the shared instance
     */
    public static Activator getDefault() {
	return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
	return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
