package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import scala.Tuple2;
import unisson.query.code_model.SourceElement;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

final class EnsembleElementPair implements IPair<IEnsemble, ICodeElement> {
	private final IEnsemble ensemble;

	private final SourceElement<Object> element;

	EnsembleElementPair(Tuple2<IEnsemble, SourceElement<Object>> arg0) {
		this.ensemble = arg0._1;
		this.element = arg0._2;
	}

	@Override
	public IEnsemble getFirst() {
		return ensemble;
	}

	@Override
	public ICodeElement getSecond() {
		return element;
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
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result
				+ ((ensemble == null) ? 0 : ensemble.hashCode());
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
		EnsembleElementPair other = (EnsembleElementPair) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (ensemble == null) {
			if (other.ensemble != null)
				return false;
		} else if (!ensemble.equals(other.ensemble))
			return false;
		return true;
	}
}