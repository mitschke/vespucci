package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class ArchitectureModel implements IArchitectureModel {
	
	private Set<IEnsemble> ensembles;
	private Set<IConstraint> contraints;
	
	public ArchitectureModel(Set<IEnsemble> ensembles){
		
		this.ensembles = ensembles;
		
		this.contraints = new HashSet<IConstraint>();
		for (IEnsemble ensemble : ensembles) {
			this.contraints.addAll(ensemble.getTargetConnections());
		}
	}
	
	
	@Override
	public Set<IEnsemble> getEnsembles() {
		return this.ensembles;
	}

	@Override
	public Set<IConstraint> getConstraints() {
		return this.contraints;
	}

}
