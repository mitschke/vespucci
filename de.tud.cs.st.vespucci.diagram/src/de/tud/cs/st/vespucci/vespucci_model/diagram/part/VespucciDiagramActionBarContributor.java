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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;

import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;

/**
 * @generated
 */
public class VespucciDiagramActionBarContributor extends
		DiagramActionBarContributor {

	/**
	 * @generated
	 */
	@Override
	protected Class getEditorClass() {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditor.class;
	}

	/**
	 * @generated
	 */
	@Override
	protected String getEditorId() {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditor.ID;
	}

	/**
	 * remove the compartment icon from the menu-bar
	 * 
	 * @generated NOT
	 * @author Artem Vovk
	 * @author Alexander Weitzmann
	 * @author Thomas Schulz
	 */
	@Override
	public void init(final IActionBars bars, final IWorkbenchPage page) {
		super.init(bars, page);
		// print preview
		final IMenuManager fileMenu = bars.getMenuManager().findMenuUsingPath(
				IWorkbenchActionConstants.M_FILE);
		if (fileMenu == null) {
			throw new VespucciUnexpectedException(String.format(
					"FileMenu [%s] must not be null.", fileMenu));
		}
		fileMenu.remove("pageSetupAction");
		final IMenuManager editMenu = bars.getMenuManager().findMenuUsingPath(
				IWorkbenchActionConstants.M_EDIT);
		if (editMenu == null) {
			throw new VespucciUnexpectedException(String.format(
					"EditMenu [%s] must not be null.", editMenu));
		}
		if (editMenu.find("validationGroup") == null) {
			editMenu.add(new GroupMarker("validationGroup"));
		}
		final IAction validateAction = new de.tud.cs.st.vespucci.vespucci_model.diagram.part.ValidateAction(
				page);
		editMenu.appendToGroup("validationGroup", validateAction);

		// remove compartmentMenu
		final IToolBarManager toolBarManager = bars.getToolBarManager();
		toolBarManager.remove("compartmentMenu");
		toolBarManager.remove("diagramFilterActionMenu");
	}
}
