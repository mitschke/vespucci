package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;


public class Ensemble extends Shape implements IEnsemble {

	LinkedList<IShape> shapes;
	
	@Override
	public LinkedList<IShape> getShapes() {
		return shapes;
	}

	@Override
	public void setShapes(LinkedList<IShape> shapes) {
		this.shapes = shapes;
	}

}
