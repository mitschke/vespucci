package de.tud.cs.st.vespucci.diagram.outputModelImpl;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import de.tud.cs.st.vespucci.diagram.outputModelInterfaces.IConnection;
import de.tud.cs.st.vespucci.diagram.outputModelInterfaces.IEnsemble;

public class Ensemble implements IEnsemble {

	private de.tud.cs.st.vespucci.vespucci_model.Shape shape;
	
	private LinkedList<IEnsemble> innerEnsemble;
	private LinkedList<IConnection> sourceConnection;
	private LinkedList<IConnection> targetConnection;
	
	public Ensemble(de.tud.cs.st.vespucci.vespucci_model.Shape ensemble) {
		this.shape = ensemble;
	}
	
	@Override
	public LinkedList<IEnsemble> getInnerEnsembles() {
		if (innerEnsemble == null){
			innerEnsemble = getInnerEnsemble();
		}
		return innerEnsemble;	
	}

	private LinkedList<IEnsemble> getInnerEnsemble() {
		if (shape instanceof de.tud.cs.st.vespucci.vespucci_model.Ensemble){
			de.tud.cs.st.vespucci.vespucci_model.Ensemble ensemble = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) shape;
						
			LinkedList<IEnsemble> connections = new LinkedList<IEnsemble>();
			for (de.tud.cs.st.vespucci.vespucci_model.Shape shape : ensemble.getShapes()) {
				connections.add(new Ensemble(shape));
			}		
			return connections;
		}
		return new LinkedList<IEnsemble>();
	}

	@Override
	public String getDescription() {
		return shape.getDescription();
	}

	@Override
	public String getName() {
		return shape.getName();
	}

	@Override
	public String getQuery() {
		return shape.getQuery();
	}

	@Override
	public LinkedList<IConnection> getSourceConnections() {
		if (sourceConnection == null){
			sourceConnection = getIConnections(shape.getSourceConnections());
		}
		return sourceConnection;	
	}

	@Override
	public LinkedList<IConnection> getTargetConnections() {
		if (targetConnection == null){
			targetConnection = getIConnections(shape.getTargetConnections());
		}
		return targetConnection;	
	}

	private LinkedList<IConnection> getIConnections(EList<de.tud.cs.st.vespucci.vespucci_model.Connection> eList) {
		LinkedList<IConnection> connections = new LinkedList<IConnection>();
		for (de.tud.cs.st.vespucci.vespucci_model.Connection connection : eList) {
						connections.add(createIConnectedSubtypeInstance(connection));
		}		
		return connections;
	}

	private Connection createIConnectedSubtypeInstance(de.tud.cs.st.vespucci.vespucci_model.Connection connection) {
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Expected){
			return new Expected((de.tud.cs.st.vespucci.vespucci_model.Expected) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming){
			return new Incoming((de.tud.cs.st.vespucci.vespucci_model.Incoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed){
			return new NotAllowed((de.tud.cs.st.vespucci.vespucci_model.NotAllowed) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing){
			return new Outgoing((de.tud.cs.st.vespucci.vespucci_model.Outgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming){
			return new GlobalIncoming((de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing){
			return new GlobalOutgoing((de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut){
			return new InAndOut((de.tud.cs.st.vespucci.vespucci_model.InAndOut) connection);
		}
		if (connection instanceof de.tud.cs.st.vespucci.vespucci_model.Violation){
			return new DocumentedViolation((de.tud.cs.st.vespucci.vespucci_model.Violation) connection);
		}
		
		return new Connection(connection);

	}
	
}