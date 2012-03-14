package de.tud.cs.st.vespucci.utilities;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.model.IExpected;
import de.tud.cs.st.vespucci.model.IGlobalIncoming;
import de.tud.cs.st.vespucci.model.IGlobalOutgoing;
import de.tud.cs.st.vespucci.model.IInAndOut;
import de.tud.cs.st.vespucci.model.IIncoming;
import de.tud.cs.st.vespucci.model.INotAllowed;
import de.tud.cs.st.vespucci.model.IOutgoing;

public class ModelUtils {

	public static String getFullEnsembleName(
			IEnsemble ensemble) {
		String label =  ensemble.getName();
		
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		
		return label;
	}
	
	public static String getConstraintType(IConstraint constraint) {
		if (constraint instanceof IIncoming)
			return "incoming";
		if (constraint instanceof IOutgoing)
			return "incoming";
		if (constraint instanceof IInAndOut)
			return "in/out";
		if (constraint instanceof IGlobalIncoming)
			return "global incoming";
		if (constraint instanceof IGlobalOutgoing)
			return "global outgoing";
		if (constraint instanceof IExpected)
			return "expected";
		if (constraint instanceof INotAllowed)
			return "not allowed";
		return "unknown";
	}
	

	
	/** 
	 * 
	 * @param ensemble
	 */
	public static IEnsemble getOuterMostEnclosingEnsemble(IEnsemble ensemble)
	{
		if(ensemble.getParent() == null)
			return ensemble;
		return getOuterMostEnclosingEnsemble(ensemble.getParent());
	}
}
