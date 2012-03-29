/**
 * 
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * @author Robert Cibulla
 *
 */
public class VespucciCanonicalEditPolicy extends CanonicalEditPolicy implements NotificationListener{

	public static final String SEMI_CANONICAL_ROLE = "SemiCanonicalRole";
	
	public VespucciCanonicalEditPolicy() {
		super();
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getSemanticChildrenList() {
		EObject modelObject = ((View) getHost().getModel()).getElement();
		List result = new ArrayList();
		if(modelObject instanceof ArchitectureModel){
			result.addAll(((ArchitectureModel)modelObject).getEnsembles());
		} else if (modelObject instanceof Ensemble){
			result.addAll(((Ensemble)modelObject).getEnsembles());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#refreshSemantic()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void refreshSemantic() {
		List<View> testIfOrphaned = getViewChildren((View)getHost().getModel());
		List semanticChildren = getSemanticChildrenList();
		List<View> orphaned = new ArrayList<View>();
		for(View child : testIfOrphaned){
			if(isOrphaned(semanticChildren, child)){
				orphaned.add(child);
			}
		}
		if(!orphaned.isEmpty()){
			super.deleteViews(orphaned.iterator());
		}
	}
	
	private static List<View> getViewChildren(View view){
		List<View> result = new ArrayList<View>();
		for(Object child : view.getChildren()){
			if(child instanceof View){
				result.add((View)child);
			}
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#activate()
	 */
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		super.activate();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#deactivate()
	 */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		super.deactivate();
	}
	
	
}
