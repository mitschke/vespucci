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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;

/**
 * @generated
 */
public class NotAllowedReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public NotAllowedReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	@Override
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof de.tud.cs.st.vespucci.vespucci_model.Shape && newEnd instanceof de.tud.cs.st.vespucci.vespucci_model.Shape)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.Shape target = getLink().getTarget();
		if (!(getLink().eContainer() instanceof de.tud.cs.st.vespucci.vespucci_model.Shape)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.Shape container = (de.tud.cs.st.vespucci.vespucci_model.Shape) getLink()
				.eContainer();
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciBaseItemSemanticEditPolicy.getLinkConstraints()
				.canExistNotAllowed_4004(container, getLink(), getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof de.tud.cs.st.vespucci.vespucci_model.Shape && newEnd instanceof de.tud.cs.st.vespucci.vespucci_model.Shape)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.Shape source = getLink().getSource();
		if (!(getLink().eContainer() instanceof de.tud.cs.st.vespucci.vespucci_model.Shape)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.Shape container = (de.tud.cs.st.vespucci.vespucci_model.Shape) getLink()
				.eContainer();
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciBaseItemSemanticEditPolicy.getLinkConstraints()
				.canExistNotAllowed_4004(container, getLink(), source, getNewTarget());
	}

	/**
	 * @generated
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return Returns result of execution.
	 */
	protected CommandResult reorientSource() {
		if (lastSourceIsNotOldEnd()) {
			getLink().getOriginalSource().clear();
			if (getLink().getOriginalTarget().isEmpty()) {
				getLink().setTemp(false);
			}
		}
		getLink().setSource(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return
	 */
	private boolean lastSourceIsNotOldEnd() {
		return !getLink().getOriginalSource().isEmpty()
				&& oldEnd != getLink().getOriginalSource().get(getLink().getOriginalSource().size() - 1);
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return Returns result of execution.
	 */
	protected CommandResult reorientTarget() {
		if (lastTargetIsNotOldEnd()) {
			getLink().getOriginalTarget().clear();
			if (getLink().getOriginalSource().isEmpty()) {
				getLink().setTemp(false);
			}
		}
		getLink().setTarget(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return
	 */
	private boolean lastTargetIsNotOldEnd() {
		return !getLink().getOriginalTarget().isEmpty()
				&& oldEnd != getLink().getOriginalTarget().get(getLink().getOriginalTarget().size() - 1);
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.NotAllowed getLink() {
		return (de.tud.cs.st.vespucci.vespucci_model.NotAllowed) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.Shape getOldSource() {
		return (de.tud.cs.st.vespucci.vespucci_model.Shape) oldEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.Shape getNewSource() {
		return (de.tud.cs.st.vespucci.vespucci_model.Shape) newEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.Shape getOldTarget() {
		return (de.tud.cs.st.vespucci.vespucci_model.Shape) oldEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.Shape getNewTarget() {
		return (de.tud.cs.st.vespucci.vespucci_model.Shape) newEnd;
	}
}
