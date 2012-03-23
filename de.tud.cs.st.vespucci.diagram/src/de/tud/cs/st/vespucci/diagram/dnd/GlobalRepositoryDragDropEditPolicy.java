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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart;

/**
 * 
 * @author Robert Cibulla
 *
 */
public class GlobalRepositoryDragDropEditPolicy extends
		DiagramDragDropEditPolicy {
	
	private ArchitectureModelEditPart sep = null;
	
	public GlobalRepositoryDragDropEditPolicy(ArchitectureModelEditPart sep){
		super();
		this.sep = sep;
	}
	
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		ArrayList<CreateViewRequest.ViewDescriptor> viewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		for (Iterator<?> it = dropRequest.getObjects().iterator(); it.hasNext();) {
			Object nextObject = it.next();
			if (!canBeDropped(nextObject)) {
				continue;
			}
			viewDescriptors.add( new CreateViewRequest.ViewDescriptor(
					new EObjectAdapter((EObject) nextObject), Node.class, null,
					sep.getDiagramPreferencesHint()));
		}
		return createEnsembleCommand(dropRequest, viewDescriptors);
	}
	
	
	/**
	 * prevent childnodes and duplicates from being dropped
	 * @param nextObject
	 * @return
	 */
	private boolean canBeDropped(Object nextObject){
		return (nextObject instanceof Shape && 
				(((Shape) nextObject).eContainer() instanceof ArchitectureModel) &&
				!isDuplicate(nextObject));
	}
	
	
	/**
	 * prevent duplicates in the Diagram
	 * @param nextObject
	 * @return
	 */
	private boolean isDuplicate(Object nextObject){
		List l = null;
		Object eo = getHost().getModel();
		if(eo instanceof Diagram)
			l = ((Diagram)eo).getChildren();
		for(Object o : l){
			if(o instanceof View && ((View)o).getElement().equals(nextObject)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * create and arrange the ensembles based on a ViewDescriptor
	 * @param dropRequest
	 * @param viewDescriptors
	 * @return
	 */
	private Command createEnsembleCommand(DropObjectsRequest dropRequest,
			List<CreateViewRequest.ViewDescriptor> viewDescriptors) {
		Command command = createViewsAndArrangeCommand(dropRequest,
				viewDescriptors);
		return command;
	}
}
