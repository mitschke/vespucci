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

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class VespucciReorientConnectionViewCommand extends
		AbstractTransactionalCommand {

	/**
	 * @generated
	 */
	private IAdaptable edgeAdaptor;

	/**
	 * @generated
	 */
	public VespucciReorientConnectionViewCommand(
			TransactionalEditingDomain editingDomain, String label) {
		super(editingDomain, label, null);
	}

	/**
	 * @generated
	 */
	public List getAffectedFiles() {
		View view = (View) edgeAdaptor.getAdapter(View.class);
		if (view != null) {
			return getWorkspaceFiles(view);
		}
		return super.getAffectedFiles();
	}

	/**
	 * @generated
	 */
	public IAdaptable getEdgeAdaptor() {
		return edgeAdaptor;
	}

	/**
	 * @generated
	 */
	public void setEdgeAdaptor(IAdaptable edgeAdaptor) {
		this.edgeAdaptor = edgeAdaptor;
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(
			IProgressMonitor progressMonitor, IAdaptable info) {
		assert null != edgeAdaptor : "Null child in VespucciReorientConnectionViewCommand"; //$NON-NLS-1$
		Edge edge = (Edge) getEdgeAdaptor().getAdapter(Edge.class);
		assert null != edge : "Null edge in VespucciReorientConnectionViewCommand"; //$NON-NLS-1$
		View tempView = edge.getSource();
		edge.setSource(edge.getTarget());
		edge.setTarget(tempView);
		return CommandResult.newOKCommandResult();
	}
}
