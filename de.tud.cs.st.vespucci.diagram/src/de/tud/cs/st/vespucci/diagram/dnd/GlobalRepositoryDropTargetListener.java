package de.tud.cs.st.vespucci.diagram.dnd;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart;

	/**
	 * @generated NOT
	 */
	public abstract class GlobalRepositoryDropTargetListener extends
			DiagramDropTargetListener {
		
		EditingDomain editingDomain;
		
		public GlobalRepositoryDropTargetListener(EditingDomain editingDomain, IDiagramGraphicalViewer idgv , Transfer lt){
			super(idgv, lt);
			this.editingDomain = editingDomain;
		}
		
		@Override
		protected void handleDragOver() {
			super.handleDragOver();
			if(canBeDropped()){
				getCurrentEvent().detail = DND.DROP_COPY;
			} else {
				getCurrentEvent().detail = DND.DROP_NONE;
			}
				
		}
		
		/**
		 * 
		 * @return boolean
		 * @generated NOT
		 */
		private boolean canBeDropped() {
			return (getTargetEditPart() instanceof ArchitectureModelEditPart) && !containsChild();
		}
		
		
		/**
		 * to prevent children from being dropped
		 * @return boolean
		 * @generated NOT
		 */
		@SuppressWarnings("rawtypes")
		private boolean containsChild(){
			List l = getObjectsBeingDropped();
			for(Object o : l){
				if((o instanceof Ensemble) && !(((Ensemble)o).eContainer() instanceof ArchitectureModel))
						return true;
			}
			return false;
		}
	

		/**
		 * @generated NOT
		 */
		public GlobalRepositoryDropTargetListener(EditPartViewer viewer,
				Transfer xfer) {
			super(viewer, xfer);
		}

		/**
		 * @generated NOT
		 */
		@SuppressWarnings("rawtypes")
		protected List getObjectsBeingDropped() {
			TransferData data = getCurrentEvent().currentDataType;
			HashSet<URI> uris = new HashSet<URI>();

			Object transferedObject = getJavaObject(data);
			if (transferedObject instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) transferedObject;
				for (Iterator<?> it = selection.iterator(); it.hasNext();) {
					Object nextSelectedObject = it.next();
					if (nextSelectedObject instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) {
						View view = ((de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) nextSelectedObject)
								.getView();
						nextSelectedObject = view.getElement();
					} else if (nextSelectedObject instanceof IAdaptable) {
						IAdaptable adaptable = (IAdaptable) nextSelectedObject;
						nextSelectedObject = adaptable
								.getAdapter(EObject.class);
					}
					if (nextSelectedObject instanceof Shape) {
						Shape modelElement = (Shape) nextSelectedObject;
						uris.add(EcoreUtil.getURI(modelElement));
					}
				}
			}

			ArrayList<EObject> result = new ArrayList<EObject>(uris.size());
			for (URI nextURI : uris) {
				EObject modelObject = editingDomain.getResourceSet()
						.getEObject(nextURI, true);
				result.add(modelObject);
			}
			return result;
		}

		/**
		 * @generated NOT
		 */
		protected abstract Object getJavaObject(TransferData data);

	}