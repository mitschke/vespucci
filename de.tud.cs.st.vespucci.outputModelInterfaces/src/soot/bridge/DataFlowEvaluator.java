package soot.bridge;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

/**
 * 
 * @author Ralf Mitschke
 *
 */
public interface DataFlowEvaluator {
	
	/**
	 * Checks whether data-flow dependencies exist between the given code elements.
	 * Returns a mapping from source ensembles to their possible targets.
	 * In other words, if data can flow from ensemble A to ensemble B then
	 * <code>retVal.get(A).contains(b)==true</code>.
	 */
	public Map<ISootCodeElement, Set<ISootCodeElement>> determineDataFlows(List<IPair<IEnsemble, ISootCodeElement>> extension);
	

}