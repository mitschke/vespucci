package de.tud.cs.st.vespucci.diagram.outputModelInterfaces;

import java.util.LinkedList;

public interface IEnsemble {
	
	String getDescription();
	
	String getName();
	
	String getQuery();
	
	LinkedList<IConnection> getSourceConnections();
	
	LinkedList<IConnection> getTargetConnections();
	
	LinkedList<IEnsemble> getInnerEnsembles();

}
