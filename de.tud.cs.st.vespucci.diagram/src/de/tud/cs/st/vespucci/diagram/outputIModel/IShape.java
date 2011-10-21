package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;

public interface IShape {
	
	String getDescription();
	
	String getName();
	
	String getQuery();
	
	LinkedList<IConnection> getSourceConnections();
	
	LinkedList<IConnection> getTargetConnections();

	void setName(String name);
	
	void setDescription(String desc);
	
	void setQuery(String Query);

}
