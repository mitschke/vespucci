package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

public class ConversionUtils {

	public static IEnsemble createEnsemble(Shape shape) {
		if (shape instanceof Dummy)
			return new EmptyEnsemble((Dummy) shape);
		else
			return new Ensemble(shape);
	}

	static Set<IConstraint> getIConnections(
			EList<de.tud.cs.st.vespucci.vespucci_model.Connection> eList) {
		Set<IConstraint> connections = new HashSet<IConstraint>();

		for (de.tud.cs.st.vespucci.vespucci_model.Connection connection : eList) {
			if (!connection.getTarget().eIsProxy())
				connections.add(createIConnectedSubtypeInstance(connection));
		}

		return connections;
	}

	static Constraint createIConnectedSubtypeInstance(
			de.tud.cs.st.vespucci.vespucci_model.Connection connection) {

		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Expected) {
			return new Expected(
					(de.tud.cs.st.vespucci.vespucci_model.Expected) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming) {
			return new Incoming(
					(de.tud.cs.st.vespucci.vespucci_model.Incoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
			return new NotAllowed(
					(de.tud.cs.st.vespucci.vespucci_model.NotAllowed) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing) {
			return new Outgoing(
					(de.tud.cs.st.vespucci.vespucci_model.Outgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) {
			return new GlobalIncoming(
					(de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing) {
			return new GlobalOutgoing(
					(de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut) {
			return new InAndOut(
					(de.tud.cs.st.vespucci.vespucci_model.InAndOut) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Violation) {
			return new DocumentedViolation(
					(de.tud.cs.st.vespucci.vespucci_model.Violation) connection);
		}

		return new Constraint(connection);

	}

}
