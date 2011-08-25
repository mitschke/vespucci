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
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ResourceBundle;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * A EditDropPolicy that allows and handles the drop of ISelections on the EditPart that is corresponding to a subtype
 * of the metaclass Shape. The Request of the drop must be a DirectEditRequest.
 * 
 * @author Malte Viering
 */
public class EditDropPolicy extends DirectEditPolicy {

	@Override
	public Command getCommand(final Request request) {
		if (RequestConstants.REQ_DIRECT_EDIT.equals(request.getType())) {
			return getDirectEditCommand((DirectEditRequest) request);
		}
		return null;
	}

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		final EObject semanticElement;
		if (getHost() instanceof GraphicalEditPart) {
			semanticElement = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		} else {
			return null;
		}

		if (semanticElement instanceof Shape) {
			// get info about the EMF meta model so we can refer to the singleton that saves Vespucci_modelPackage
			final String modelNamespace = ResourceBundle.getBundle("plugin").getString("vespucci_modelNamespaceURI");
			final EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage(modelNamespace);
			final Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;

			if (semanticElement.eClass().getEAllStructuralFeatures().contains(vesPackage.getShape_Query())) {
				// get the old query
				final Object oldQuery = semanticElement.eGet(vesPackage.getShape_Query());
				@SuppressWarnings("unchecked")
				final SetRequest sr = new SetRequest(semanticElement, vesPackage.getShape_Query(),
						QueryBuilder.createQueryFromRequestData(request.getExtendedData(), (String) oldQuery));

				final org.eclipse.gmf.runtime.common.core.command.ICommand com = new SetValueCommand(sr);
				// return the edit Request in a proxy so it can be handled
				return new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(com);
			}
		}
		return null;
	}

	@Override
	public EditPart getTargetEditPart(final Request request) {
		final EditPart editPart = super.getTargetEditPart(request);
		if (editPart != null) {
			return editPart;
		}
		if (REQ_DIRECT_EDIT.equals(request.getType())) {
			return getHost();
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
	}
}
