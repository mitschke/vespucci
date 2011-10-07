package de.tud.cs.st.vespucci.diagram.processing;



import org.eclipse.core.resources.IFile;


public interface IDiagramProcessor {

	public void process(IFile diagramFile);
	
	/*
	 * ToDo: For further Conventions with Ralf
	 */
	
	@Deprecated
	public void convert(Object diagramElement);
	
}
