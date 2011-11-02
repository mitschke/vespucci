/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
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
 *   - Neither the name of the Software Technology Group Group or Technische 
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
package de.tud.cs.st.vespucci.diagram.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.SetConnectionTypeCommand;

/**
 * Handler for "Set Constraint"-commands in the dependency popup menu.<br>
 * Set Constraint changes the class of the constraint, e.g. from Incoming to Outgoing. Thus the class of
 * the connection must be changed for the graphical and semantical object. In order to preserve
 * consistency the dependencies will be deleted and then recreated via commands.
 * 
 * @author Alexander Weitzmann
 */
public final class SetDependencyConstraintHandler extends AbstractHandler {
	private static final String COMMAND_LABEL = "Change dependency constraint";
	
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final SetConstraintTypeParameter typeParams = new SetConstraintTypeParameter();
		final IElementType setType = typeParams.getParameterValues().get(
				event.getParameter("de.tud.cs.st.vespucci.diagram.SetConstraintTypeParam"));

		final IStructuredSelection currentSelection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		// ensure selection is not empty
		if (currentSelection.size() == 0) {
			// nothing to do
			return null;
		}
		final Object[] currentSelectionArr = currentSelection.toArray();

		// This array will contain the casted selection-objects.
		final ConnectionEditPart[] selectedConnections = new ConnectionEditPart[currentSelection.size()];

		for (int i = 0; i < currentSelection.size(); ++i) {
			if (currentSelectionArr[i] instanceof ConnectionEditPart) {
				selectedConnections[i] = (ConnectionEditPart) currentSelectionArr[i];
			} else {
				// If this exception is reached, then there should be something wrong with the
				// visibleWhen entry of the popUp-menu.
				return new VespucciUnexpectedException(COMMAND_LABEL + ": Selection is not a connection!");
			}
		}

		CompoundCommand cmd = new CompoundCommand(COMMAND_LABEL);

		for(ConnectionEditPart conn : selectedConnections){
			cmd.append(new SetConnectionTypeCommand(conn, setType));
		}
		selectedConnections[0].getEditingDomain().getCommandStack().execute(cmd);
		
		return null;
	}
}