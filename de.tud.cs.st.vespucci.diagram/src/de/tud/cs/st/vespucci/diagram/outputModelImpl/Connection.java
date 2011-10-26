package de.tud.cs.st.vespucci.diagram.outputModelImpl;

import de.tud.cs.st.vespucci.diagram.outputModelInterfaces.IConnection;
import de.tud.cs.st.vespucci.diagram.outputModelInterfaces.IEnsemble;

public class Connection implements IConnection {

	private de.tud.cs.st.vespucci.vespucci_model.Connection connection;
	
	public Connection(de.tud.cs.st.vespucci.vespucci_model.Connection connection) {
		this.connection = connection;
	}

	@Override
	public String getName() {
		return connection.getName();
	}

	@Override
	public IEnsemble getSource() {
		return new Ensemble(connection.getSource());
	}

	@Override
	public IEnsemble getTarget() {
		return new Ensemble(connection.getSource());
	}

}
