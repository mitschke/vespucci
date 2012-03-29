package de.tud.cs.st.vespucci.utilities;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsemblePair implements IPair<IEnsemble, IEnsemble> {

	private IEnsemble first;

	private IEnsemble second;

	public EnsemblePair(IEnsemble first, IEnsemble second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public IEnsemble getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public IEnsemble getSecond() {
		return second;
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
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result
				+ ((second == null) ? 0 : second.hashCode());
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
		EnsemblePair other = (EnsemblePair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}
}