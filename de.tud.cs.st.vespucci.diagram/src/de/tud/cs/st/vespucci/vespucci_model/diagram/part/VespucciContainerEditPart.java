package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.BasicCompartmentImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;


public class VespucciContainerEditPart extends TreeEditPart{

	public VespucciContainerEditPart(Object model) {
		super(model);
	}
	
	

	/**
	 * Returns the children of this from the model,
	 * as this is capable enough of holding EditParts.
	 *
	 * @return  List of children.
	 */
	protected List getModelChildren() {
		Object model = getModel();
		if(model instanceof EnsembleImpl) {
			EnsembleImpl shape = (EnsembleImpl) getModel();
			return shape.getShapes();
		}
		if (model instanceof ShapeImpl){
			ShapeImpl shape = (ShapeImpl) getModel();
			
			EList shapes = shape.getPersistedChildren();
			EList edges = shape.getSourceEdges();
			for (Object i : shapes) {
				if(i instanceof BasicCompartmentImpl) {
					BasicCompartmentImpl bci = (BasicCompartmentImpl)i;
					EList<View> out = new BasicEList<View>(); 
					 out.addAll(bci.getPersistedChildren());
					out.addAll(edges);
					return out;
				}
					
			}
//			
//			EList persChild = shape.getPersistedChildren();
//			EList persChild2 = shape.getTransientChildren();
//			EList persChild3 = shape.getVisibleChildren();
//			System.out.println(persChild.toString() +persChild2 +persChild3);
//			//shape.
//		
//			EnsembleImpl ensemble = (EnsembleImpl)shape.getElement();
//			
		//	EnsembleImpl object = (EnsembleImpl)shape.getElement();
//			return ensemble.getShapes();
		}
			
		return Collections.EMPTY_LIST;
	}

	private static final String ENSEMBLE_IMAGE = "icons/obj16/Ensemble.gif";
	
	@Override
	protected Image getImage() {
		ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin
		.getBundledImageDescriptor(ENSEMBLE_IMAGE);
 
		return imageDescriptor.createImage();
	}



	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart#handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
	 */
	protected void handleNotificationEvent(Notification event) {
		Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getView_PersistedChildren()==feature||
			NotationPackage.eINSTANCE.getView_TransientChildren()==feature)
			refreshChildren();
		else
			super.handleNotificationEvent(event);
	}

}
