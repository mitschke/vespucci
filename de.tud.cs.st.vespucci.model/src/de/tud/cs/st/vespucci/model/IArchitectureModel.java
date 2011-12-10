package de.tud.cs.st.vespucci.model;

import java.util.Set;


public interface IArchitectureModel {
	
	Set<IEnsemble> getEnsembles();
	Set<IConstraint> getConstraints();

}
