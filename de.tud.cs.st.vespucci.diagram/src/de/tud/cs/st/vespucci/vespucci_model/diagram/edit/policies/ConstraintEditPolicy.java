/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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

package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

/**
 * Super-class for all constraint-EditPolicies. To effect the behavior of constraints, methods of
 * {@link VespucciBaseItemSemanticEditPolicy} are overwritten. The adapted methods can now
 * manipulate the commands, that will be returned to the emf-framework
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 * 
 */
public class ConstraintEditPolicy extends VespucciBaseItemSemanticEditPolicy {

	protected ConstraintEditPolicy(final IElementType elementType) {
		super(elementType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method will be called, if the constraint shall be deleted. Handling of IMarker was
	 * manually added.
	 * 
	 * @generated not
	 */
	@Override
	protected Command getDestroyElementCommand(final DestroyElementRequest req) {
		// Command, that bundles multiple commands
		final CompositeCommand compCom = new CompositeCommand("Reset marker and destroy connection");

		// TODO add reset marker command (and before that: integrate marker as attribute/feature in
		// model)
		// add "unset marker"-command
//		final SetRequest unsetMarkerReq = new SetRequest(req.getElementToDestroy(), feature, value);
//		compCom.add(new SetValueCommand(unsetMarkerReq));

		// add destroy-command
		compCom.add(new DestroyElementCommand(req));
		return getGEFWrapper(compCom);
	}

}
