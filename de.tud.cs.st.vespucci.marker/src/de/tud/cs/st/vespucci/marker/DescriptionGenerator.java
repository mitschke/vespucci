package de.tud.cs.st.vespucci.marker;

import de.tud.cs.st.vespucci.interfaces.IViolation;

abstract class DescriptionGenerator{
	
	protected String targetSuffix(IViolation violation) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(violation.getTargetElement().getSimpleClassName());
		sb.append(" (Ensemble: ");
		sb.append(violation.getTargetEnsemble().getName());
		sb.append(")");
		
		return sb.toString();
	}
	
	protected String sourcePrefix(boolean methodPrefix, IViolation violation) {
		
		StringBuffer sb = new StringBuffer();
		
		if (methodPrefix){
			sb.append("A method in class: ");
		}else{
			sb.append("Class: ");
		}
		sb.append(violation.getSourceElement().getSimpleClassName());
		sb.append(" (Ensemble: ");
		sb.append(violation.getSourceEnsemble().getName());
		sb.append(") ");
		
		return sb.toString();
	}
	
	public abstract String buildDescription(IViolation violation);
	
}
