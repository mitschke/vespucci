/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
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
 */
package de.tud.cs.st.vespucci.diagram.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;

/**
 * This is a convenience wrapper class for IObjectDelegates which enables the user to run the action implemented in the
 * delegate programatically with a given (list of) file(s).
 * 
 * @author Dominic Scheurer
 * @version 0.8
 */
public class ActionDelegateWrapper {
	/** The wrapped delegate. */
	private IActionDelegate actionDelegate;

	/**
	 * @param actionDelegate
	 *            The wrapped delegate object.
	 */
	public ActionDelegateWrapper(IActionDelegate actionDelegate) {
		this.actionDelegate = actionDelegate;
	}

	/**
	 * @return The wrapped delegate object.
	 */
	public IActionDelegate getActionDelegate() {
		return actionDelegate;
	}

	/**
	 * @param actionDelegate
	 *            The wrapped delegate object.
	 */
	public void setActionDelegate(IActionDelegate actionDelegate) {
		this.actionDelegate = actionDelegate;
	}

	/**
	 * Executes the wrapped delegate with a given array of files.
	 * 
	 * @param action
	 *            The outer Action instance.
	 * @param files
	 *            List of files to supply to the wrapper.
	 */
	public void executeWithFiles(Action action, IFile[] files) {
		StructuredSelection selection = new StructuredSelection(files);
		actionDelegate.selectionChanged(action, selection);
		actionDelegate.run(action);
	}

	/**
	 * Executes the wrapped delegate with a given file.
	 * 
	 * @param action
	 *            The outer Action instance.
	 * @param file
	 *            File to supply to the wrapper.
	 */
	public void executeWithFile(Action action, IFile file) {
		executeWithFiles(action, new IFile[] { file });
	}
}