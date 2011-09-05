/*
 *  License (BSD Style License):
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universiti�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.st.vespucci.exceptions;

import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Base class for all exceptions thrown by the Vespucci project.
 * 
 * @author Theo Kischka
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * @author Alexander Weitzmann
 */
public abstract class VespucciException extends RuntimeException {
	
	private static final String PLUGIN_ID = ResourceBundle.getBundle("plugin").getString("vespucci_pluginID");
	private static final String DEFAULT_MESSAGE = "No message available.";

	/**
	 * Constructor also logs and shows given message.
	 * 
	 * @param message
	 */
	protected VespucciException(final String message) {
		super(message);
		logException(message);
	}

	/**
	 * Constructor also logs and shows given cause.
	 * 
	 * @param cause
	 */
	protected VespucciException(final Throwable cause) {
		super(cause);
		logException(DEFAULT_MESSAGE, cause);
	}

	/**
	 * Constructor also logs and shows given message and cause.
	 * 
	 * @param message
	 * @param cause
	 */
	protected VespucciException(final String message, final Throwable cause) {
		super(message, cause);
		logException(message, cause);
	}
	
	/**
	 * @param message
	 *            A custom error message.
	 * @param cause
	 *            Source Exception.
	 */
	private static void logException(final String message, final Throwable cause) {
		final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, message, cause);
		StatusManager.getManager().handle(is, StatusManager.SHOW);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}

	/**
	 * @param message
	 *            A custom error message.
	 */
	private static void logException(final String message) {
		final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, message);
		StatusManager.getManager().handle(is, StatusManager.SHOW);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}
}
