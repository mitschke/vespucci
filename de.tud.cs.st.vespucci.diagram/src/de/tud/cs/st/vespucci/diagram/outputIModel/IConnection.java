package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;

public interface IConnection {
	
	String getName();
	
	LinkedList<IShape> getOriginalSource();
	
	LinkedList<IShape> getOriginalTarget();
	
	IShape getSource();
	
	IShape getTarget();
	
	boolean isTemp();
	
	void setSource(IShape source);
	
	void setTarget(IShape target);
	
	void setTemp(boolean temp);
	
	void setName(String name);

}
