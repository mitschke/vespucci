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
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ResourceBundle;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * A EditDropPolicy that allows and handles the drop of ISelections on the
 * EditPart that is corresponding to a subtype of the meta class Shape. The
 * Request of the drop must be a DirectEditRequest.
 * 
 * @author Ralf Mitschke
 * @author Malte Viering
 */
public class JavaElementEnsembleDropPolicy extends DirectEditPolicy {

	/**
	 * Unfortunately DirectEditPolicy.getCommand checks for a specific type
	 * instead of using understandsRequest(request). Hence, we override to use
	 * our own request type.
	 */
	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request))
			return null;
		return getDirectEditCommand((DirectEditRequest) request);
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		if (!understandsRequest(request))
			return null;
		EditPart targetEditPart = getTargetEditPart(request);
		if(!(targetEditPart.getModel() instanceof View))
			return null;
		View view = (View) targetEditPart.getModel();
		EObject element = view.getElement();
		if (!(element instanceof Shape))
			return null;

		Shape ensemble = (Shape) element;
		// get info about the EMF meta model so we can refer to the
		// singleton that saves Vespucci_modelPackage
		final String modelNamespace = ResourceBundle.getBundle("plugin")
				.getString("vespucci_modelNamespaceURI");

		final EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
				.getEPackage(modelNamespace);
		final Vespucci_modelPackage vespucciMetamodel = (Vespucci_modelPackage) epackage;

		// get the old query
		final String oldQuery = ensemble.getQuery();

		final SetRequest sr = new SetRequest(ensemble,
				vespucciMetamodel.getShape_Query(),
				QueryBuilder.createQueryFromRequestData(
						request.getExtendedData(), oldQuery));

		;
		// wrap the command as gef command using ICommandProxy
		return new ICommandProxy(new SetValueCommand(sr));

	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		// do nothing; the query is not shown in the figure
	}

	/**
	 * We understand requests of type
	 * IJavaElementDropConstants.REQ_DROP_EXTEND_ENSEMBLE.
	 */
	@Override
	public boolean understandsRequest(Request request) {
		return IJavaElementDropConstants.REQ_DROP_EXTEND_ENSEMBLE
				.equals(request.getType());
	}

	/**
	 * Returns the host as target EditPart if our REQ_DROP_EXTEND_ENSEMBLE
	 * request is given.
	 * 
	 * The DropTargetListener will ask all EditParts at a location if they can
	 * provide a target for the request. By default the policy would return
	 * null. We install this policy on EnsembleEditParts as host, so it is okay
	 * to provide this as our target.
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		if (understandsRequest(request))
			return getHost();
		return null;
	}

}
