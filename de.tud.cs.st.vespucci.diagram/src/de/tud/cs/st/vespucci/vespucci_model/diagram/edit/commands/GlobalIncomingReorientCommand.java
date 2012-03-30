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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

/**
 * @generated
 */
public class GlobalIncomingReorientCommand extends EditElementCommand {

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
	public GlobalIncomingReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) {
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
		if (!(oldEnd instanceof de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble && newEnd instanceof de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble target = getLink()
				.getTarget();
		if (!(getLink().eContainer() instanceof de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel container = (de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel) getLink()
				.eContainer();
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciBaseItemSemanticEditPolicy
				.getLinkConstraints().canExistGlobalIncoming_4006(container,
						getLink(), getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble && newEnd instanceof de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble source = getLink()
				.getSource();
		if (!(getLink().eContainer() instanceof de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel)) {
			return false;
		}
		de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel container = (de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel) getLink()
				.eContainer();
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciBaseItemSemanticEditPolicy
				.getLinkConstraints().canExistGlobalIncoming_4006(container,
						getLink(), source, getNewTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in reorient link command"); //$NON-NLS-1$
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
	 * @generated NOT
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		if (lastSourceIsNotOldEnd()) {
			getLink().getOriginalSource().clear();
			if (getLink().getOriginalTarget().isEmpty()) {
				getLink().setTemp(false);
			}
		}
		getLink().setSource(getNewSource());
		getOldSource().getTargetConnections().remove(getLink());
		getNewSource().getTargetConnections().add(getLink());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		if (lastTargetIsNotOldEnd()) {
			getLink().getOriginalTarget().clear();
			if (getLink().getOriginalSource().isEmpty()) {
				getLink().setTemp(false);
			}
		}
		getLink().setTarget(getNewTarget());
		getOldTarget().getSourceConnections().remove(getLink());
		getNewTarget().getSourceConnections().add(getLink());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming getLink() {
		return (de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble getOldSource() {
		return (de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble) oldEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble getNewSource() {
		return (de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble) newEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble getOldTarget() {
		return (de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble) oldEnd;
	}

	/**
	 * @generated
	 */
	protected de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble getNewTarget() {
		return (de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble) newEnd;
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return
	 */
	private boolean lastSourceIsNotOldEnd() {
		return !getLink().getOriginalSource().isEmpty()
				&& oldEnd != getLink().getOriginalSource().get(
						getLink().getOriginalSource().size() - 1);
	}

	/**
	 * @author Artem Vovk
	 * @generated NOT
	 * @return
	 */
	private boolean lastTargetIsNotOldEnd() {
		return !getLink().getOriginalTarget().isEmpty()
				&& oldEnd != getLink().getOriginalTarget().get(
						getLink().getOriginalTarget().size() - 1);
	}
}
