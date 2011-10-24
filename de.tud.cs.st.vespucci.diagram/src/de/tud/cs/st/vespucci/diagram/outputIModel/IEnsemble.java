package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;

public interface IEnsemble extends IShape {
	
	LinkedList<IShape> getShapes();

	void setShapes(LinkedList<IShape> shapes);
}
