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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart;

/**
 * The DropEditPolicy that handles the behavior of {@link AbstractEnsemble}s being dropped on the canvas.
 * @author Robert Cibulla
 *
 */
public class GlobalRepositoryDropEditPolicy extends
		DiagramDragDropEditPolicy {
	
	
	/**
	 *	Returns the command to create views of ensembles.
	 */
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		ArrayList<CreateViewRequest.ViewDescriptor> viewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		for (Iterator<?> it = dropRequest.getObjects().iterator(); it.hasNext();) {
			EObject nextObject = loadFromEditingDomain(it.next());
			if (!canBeDropped(nextObject, dropRequest)) {
				continue;
			}
			viewDescriptors
					.add(new CreateViewRequest.ViewDescriptor(
							new EObjectAdapter((EObject) nextObject),
							Node.class, null, ((GraphicalEditPart) getHost())
									.getDiagramPreferencesHint()));
		}
		return createEnsembleCommand(dropRequest, viewDescriptors);
	}
	
	
	/**
	 * load the object from the editing domain
	 * @param nextObject
	 * @return
	 */
	private EObject loadFromEditingDomain(Object nextObject) {
		if(!(nextObject instanceof AbstractEnsemble) || nextObject == null){
			return null;
		}
		ResourceSet srcSet = getEditingDomain().getResourceSet();
		URI elementURI = EcoreUtil.getURI((EObject) nextObject);
		
		if(elementURI != null){
			return srcSet.getEObject(elementURI, true);
		}
		return null;
	}


	/**
	 * prevent child nodes and duplicates from being dropped
	 * @param nextObject
	 * @return
	 */
	private boolean canBeDropped(Object nextObject, DropObjectsRequest dropRequest){
		return (nextObject instanceof AbstractEnsemble && 
			isCorrectParent(nextObject, dropRequest) &&
				!isDuplicate(nextObject, dropRequest));
	}
	
	/**
	 *TODO: may want to create children automatically and just prevent children from being dropped
	 * @param nextObject
	 * @param dropRequest
	 * @return
	 */
	private boolean isCorrectParent(Object nextObject, DropObjectsRequest dropRequest){
		EditPart epart = getTargetEditPart(dropRequest);
		boolean result = true;
		if(isChild(nextObject)){
			if(!((View)epart.getModel()).getElement().equals(((EObject)nextObject).eContainer()))
				result &= false;
		}
		return result;
	}
	
	/**
	 * 
	 * @param nextObject
	 * @return
	 */
	private boolean isChild(Object nextObject){
		return (nextObject instanceof AbstractEnsemble) && !(((AbstractEnsemble)nextObject).eContainer() instanceof ArchitectureModel);
	}
	
	
	/**
	 * determine whether the nextObject is a duplicate
	 * @param nextObject
	 * @return
	 */
	private boolean isDuplicate(Object nextObject, DropObjectsRequest dropRequest){
		List l = null;
		EditPart ep = getTargetEditPart(dropRequest);
		if(ep instanceof ArchitectureModelEditPart)
			l = ((View)((ArchitectureModelEditPart)ep).getModel()).getChildren();
		else if(ep instanceof CompartmentEditPart)
			l = ((View)((CompartmentEditPart)ep).getModel()).getChildren();
		else return true;
		for(Object o : l){
			if(o instanceof View && ((AbstractEnsemble)((View)o).getElement()).equals(((AbstractEnsemble)nextObject))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the EditingDomain;
	 * @return
	 */
	private TransactionalEditingDomain getEditingDomain(){
		return ((IGraphicalEditPart)getHost()).getEditingDomain();
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
