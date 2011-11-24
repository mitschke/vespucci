package de.tud.cs.st.vespucci.testprocessor;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.diagram.interfaces.IEnsemble;

public class OutputModel {

	LinkedList<IEnsemble> elements;

	public OutputModel(LinkedList<IEnsemble> elements) {
		this.elements = elements;
	}

	public LinkedList<IEnsemble> getElements() {
		return elements;
	}
	
	
	
	
}
