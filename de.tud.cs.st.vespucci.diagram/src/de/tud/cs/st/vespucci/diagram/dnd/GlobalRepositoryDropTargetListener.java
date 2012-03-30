package de.tud.cs.st.vespucci.diagram.dnd;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart;

	/**
	 * Extension of a DiagramDropTargetListener to listen for objects being dropped from the VespucciEditor
	 */
	public class GlobalRepositoryDropTargetListener extends
			DiagramDropTargetListener {
		
		private EditingDomain editingDomain;
		
		/**
		 * Basic Constructor
		 * @param editingDomain	the current EditingDomain
		 * @param idgv
		 * @param lt
		 * @generated NOT
		 */
		public GlobalRepositoryDropTargetListener(EditingDomain editingDomain, IDiagramGraphicalViewer idgv , Transfer lt){
			super(idgv, lt);
			this.editingDomain = editingDomain;
		}
	

		/**
		 * Get objects to be dropped from the current {@link DropTargetEvent}
		 * @generated NOT
		 */
		@SuppressWarnings("rawtypes")
		protected List getObjectsBeingDropped() {
			TransferData data = getCurrentEvent().currentDataType;
			
			//added via URIs
			HashSet<URI> uris = new HashSet<URI>();
		
			//gets Java Objects from transferred data (LocalTransfer)
			Object transferedObject = getJavaObject(data);
			if (transferedObject instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) transferedObject;
				for (Iterator<?> it = selection.iterator(); it.hasNext();) {
					Object nextSelectedObject = it.next();
					if (nextSelectedObject instanceof AbstractEnsemble) {
						AbstractEnsemble modelElement = (AbstractEnsemble) nextSelectedObject;
						uris.add(EcoreUtil.getURI(modelElement));
					}
				}
			}
			//add Objects via URI
			ArrayList<EObject> result = new ArrayList<EObject>(uris.size());
			for (URI nextURI : uris) {
				EObject modelObject = getEditingDomain().getResourceSet()
						.getEObject(nextURI, true);
				result.add(modelObject);
			}
			return result;
		}


		/**
		 * Handles the DragOver and changes the mouse icon if needed
		 */
		@Override
		protected void handleDragOver() {
			super.handleDragOver();
			if(canBeDropped()){
				getCurrentEvent().detail = DND.DROP_COPY;
			} else {
				getCurrentEvent().detail = DND.DROP_NONE;
			}
				
		}


		/* (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener#isDataTransfered()
		 */
		@Override
		protected boolean isDataTransfered() {
			Object transferedData = getJavaObject(getCurrentEvent().currentDataType);
			return getCurrentEvent().currentDataType != null && transferedData instanceof IStructuredSelection && !((IStructuredSelection)transferedData).isEmpty();
		}


		/**
		 * Determines whether current selection can be dropped.
		 * @return boolean
		 * @generated NOT
		 */
		private boolean canBeDropped() {
			return (getTargetEditPart() instanceof ArchitectureModelEditPart) && !containsChild() ||
					(getTargetEditPart() instanceof CompartmentEditPart && areCorrectChildren());
		}
		
		
		/**
		 * Prevent children from being dropped to prevent inconsistency in the model.
		 * @return boolean
		 * @generated NOT
		 */
		@SuppressWarnings("rawtypes")
		private boolean containsChild(){
			List l = getObjectsBeingDropped();
			for(Object o : l){
				if((o instanceof AbstractEnsemble) && !(((AbstractEnsemble)o).eContainer() instanceof ArchitectureModel))
						return true;
			}
			return false;
		}
		
		/**
		 * Determine if the objects being dropped are correct children
		 * @return
		 */
		private boolean areCorrectChildren(){
			List l = getObjectsBeingDropped();
			for(Object o : l){
				if(!((o instanceof AbstractEnsemble) && isCorrectChild((AbstractEnsemble)o)))
					return false;
			}
			return true;
		}
		
		/**
		 * Determine if current {@link AbstractEnsemble} is the correct child
		 * @param ens
		 * @return
		 */
		private boolean isCorrectChild(AbstractEnsemble ens){
			return ((View)(getTargetEditPart().getModel())).getElement().equals(ens.eContainer());
		}
	

		/**
		 * Get the EditingDomain
		 * @return
		 */
		protected EditingDomain getEditingDomain() {
			return editingDomain;
		}
		

		/**
		 * Decodes the LocalTransfer and returns the native Java Object(s)
		 * @generated NOT
		 */
		protected Object getJavaObject(TransferData data){
			return LocalTransfer.getInstance().nativeToJava(data);
		}
	}