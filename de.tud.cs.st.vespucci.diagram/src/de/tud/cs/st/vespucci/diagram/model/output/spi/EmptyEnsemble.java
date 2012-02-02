package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.HashSet;
import java.util.Set;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;

public class EmptyEnsemble implements IEnsemble {

	private static Set<IEnsemble> noEnsembles = new HashSet<IEnsemble>(0);

	private static String emptyEnsembleName = "@EmptyEnsembleName";

	private static String emptyQuery = "empty";

	private Dummy emptyEnsemble;

	private Set<IConstraint> sourceConnection;

	private Set<IConstraint> targetConnection;

	public EmptyEnsemble(Dummy emptyEnsemble) {
		this.emptyEnsemble = emptyEnsemble;
	}

	@Override
	public IEnsemble getParent() {
		return null;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getName() {
		return emptyEnsembleName;
	}

	@Override
	public String getQuery() {
		return emptyQuery;
	}

	@Override
	public Set<IEnsemble> getInnerEnsembles() {
		return noEnsembles;
	}

	@Override
	public Set<IConstraint> getSourceConnections() {
		if (sourceConnection == null) {
			sourceConnection = ConversionUtils.getIConnections(emptyEnsemble
					.getSourceConnections());
		}
		return sourceConnection;
	}

	@Override
	public Set<IConstraint> getTargetConnections() {
		if (targetConnection == null) {
			targetConnection = ConversionUtils.getIConnections(emptyEnsemble
					.getTargetConnections());
		}
		return targetConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmptyEnsemble";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * emptyEnsembleName.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
