package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart;

public class VespucciDragDropEditPolicy extends
		DiagramDragDropEditPolicy {
	
	private ShapesDiagramEditPart sep = null;
	
	public VespucciDragDropEditPolicy(ShapesDiagramEditPart sep){
		super();
		this.sep = sep;
	}
	
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		ArrayList<CreateViewRequest.ViewDescriptor> viewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		for (Iterator<?> it = dropRequest.getObjects().iterator(); it.hasNext();) {
			Object nextObject = it.next();
			if (false == nextObject instanceof EObject) {
				continue;
			}
			viewDescriptors.add(new CreateViewRequest.ViewDescriptor(
					new EObjectAdapter((EObject) nextObject), Node.class, null,
					sep.getDiagramPreferencesHint()));
		}
		return createEnsembleCommand(dropRequest, viewDescriptors);
	}

	
	private Command createEnsembleCommand(DropObjectsRequest dropRequest,
			List<CreateViewRequest.ViewDescriptor> viewDescriptors) {
		Command command = createViewsAndArrangeCommand(dropRequest,
				viewDescriptors);
		if (command != null) {
			return command
					.chain(new ICommandProxy(
							new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands.VespucciCreateShortcutDecorationsCommand(
									sep.getEditingDomain(), (View) sep.getModel(),
									viewDescriptors)));
		}
		return null;
	}
}
