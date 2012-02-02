package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.Set;

import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class ArchitectureModel implements IArchitectureModel {

	private Set<IEnsemble> ensembles;

	private Set<IConstraint> contraints;

	private String name;

	public ArchitectureModel(Set<IEnsemble> ensembles,
			Set<IConstraint> constraints, String name) {

		this.ensembles = ensembles;
		this.contraints = constraints;
		this.name = name;
	}

	@Override
	public Set<IEnsemble> getEnsembles() {
		return this.ensembles;
	}

	@Override
	public Set<IConstraint> getConstraints() {
		return this.contraints;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
