package de.tud.cs.st.vespucci.utilities;

import java.util.LinkedList;
import java.util.List;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IDocumentedViolation;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.model.IExpected;
import de.tud.cs.st.vespucci.model.IGlobalIncoming;
import de.tud.cs.st.vespucci.model.IGlobalOutgoing;
import de.tud.cs.st.vespucci.model.IInAndOut;
import de.tud.cs.st.vespucci.model.IIncoming;
import de.tud.cs.st.vespucci.model.INotAllowed;
import de.tud.cs.st.vespucci.model.IOutgoing;

public class ModelUtils {

	public static IPair<IEnsemble, IEnsemble> Pair(IEnsemble source,
			IEnsemble target) {
		return new EnsemblePair(source, target);
	}

	
	public static String getFullEnsembleName(IEnsemble ensemble) {
		String label = ensemble.getName();

		while (ensemble.getParent() != null) {
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}

		return label;
	}

	public static String getConstraintType(IConstraint constraint) {
		if (constraint instanceof IIncoming)
			return "incoming";
		if (constraint instanceof IOutgoing)
			return "outgoing";
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
		if (constraint instanceof IDocumentedViolation)
			return "documented violation";
		return "unknown";
	}

	/**
	 * @param ensemble
	 */
	public static IEnsemble getOuterMostEnclosingEnsemble(IEnsemble ensemble) {
		if (ensemble.getParent() == null)
			return ensemble;
		return getOuterMostEnclosingEnsemble(ensemble.getParent());
	}

	/**
	 * Returns the sequence of all parents in a list. The innermost parent is
	 * the first element, the outermost parent is the last element
	 * 
	 * @param source
	 */
	public static List<IEnsemble> getParentList(IEnsemble source) {
		IEnsemble parent = source.getParent();
		if (parent == null)
			return new LinkedList<IEnsemble>();
		List<IEnsemble> result = new LinkedList<IEnsemble>();
		result.add(parent);
		result.addAll(getParentList(parent));
		return result;
	}
}
