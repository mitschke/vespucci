package de.tud.cs.st.vespucci.diagram.outputModelInterfaces;

public interface IConnection {
	
	String getName();
	
	IEnsemble getSource();
	
	IEnsemble getTarget();

}
