/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt 
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
 */

package de.tud.cs.st.vespucci.proxy;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * <p>
 * Central class for the ActionHandler extension point which helps
 * to avoid circular dependencies between plugins in a bundle by offering
 * an interface to share IActionHandler instances between plugins.
 * </p>
 * 
 * <p>
 * Procedure: Contribute to the Vespucci_ProxyActionHandler extension point
 * in a plugin project which has data to publish, and access the provided
 * ActionHandler from another project using this class to retrieve the
 * desired data.
 * </p> 
 * 
 * @author Tejash Shah, Dominic Scheurer
 * @see <a href="http://technical-tejash.blogspot.com/2010/03/eclipse-avoid-cyclic-dependency-between.html">Original Source</a>
 */
public class ActionManager {

	/**
	 * Singleton instance of this class.
	 */
	private static ActionManager INSTANCE = new ActionManager();

	/**
	 * The ID of the Vespucci ActionHandler extension point.
	 */
	private static final String ACTIONHANDLER_EXTENSION_POINT_ID = "de.tud.cs.st.vespucci.proxy.Vespucci_ProxyActionHandler";
	
	/**
	 * The identifier of the ActionHandler ID attribute.
	 */
	private static final String ACTIONHANDLER_ATTRIB_ID = "id";

	/**
	 * The identifier of the ActionHandler class attribute.
	 */
	private static final String ACTIONHANDLER_ATTRIB_CLASS = "handlerClass";

	/**
	 * Stores the registered ActionHandler contributions.
	 */
	private Map<String, IActionHandler> actionHandlers;

	/**
	 * Starts the initialization process.
	 */
	private ActionManager() {
		loadContributingActionHandlers();
	}

	/**
	 * Initializes the map containing the contributing ActionHandlers.
	 */
	private void loadContributingActionHandlers() {
		actionHandlers = new HashMap<String, IActionHandler>();
		IConfigurationElement[] configurationElements = getConfigurationElements(ACTIONHANDLER_EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : configurationElements) {
			try {
				Object extensionObject = configurationElement.createExecutableExtension(ACTIONHANDLER_ATTRIB_CLASS);
				IActionHandler contributor = (IActionHandler) extensionObject;
				String id = configurationElement.getAttribute(ACTIONHANDLER_ATTRIB_ID);
				contributor.setId(id);
				actionHandlers.put(id, contributor);
			} catch (CoreException e) {
				handleError("Unable to create executable extension", e);
			}
		}
	}

	/**
	 * @return The Singleton instance of this class.
	 */
	public static ActionManager getInstance() {
		return INSTANCE;
	}

	/**
	 * @param id Identifier of the ActionHandler to look for.
	 * @return The ActionHandler identified by the given ID or null.
	 */
	public IActionHandler getActionHandlerbyId(String id) {
		return actionHandlers.get(id);
	}

	/**
	 * Fetches the configuration elements of the given extension point.
	 * 
	 * @param extensionPointID The ID of the extension point to load the configuration data for.
	 * @return The configuration elements of the given extension point.
	 */
	public static IConfigurationElement[] getConfigurationElements(String extensionPointID) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint contentTypesXP = registry.getExtensionPoint(extensionPointID);
		
		if (contentTypesXP == null) {
			return new IConfigurationElement[0];
		}
		
		IConfigurationElement[] allContentTypeConfElements = contentTypesXP.getConfigurationElements();
		return allContentTypeConfElements;
	}
	
	/**
	 * Simple error handler.
	 * 
	 * @param message A custom error message.
	 * @param cause Source Exception.
	 */
	private static void handleError(String message, Exception cause) {
		IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, cause);
		StatusManager.getManager().handle(is, StatusManager.SHOW);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}
}