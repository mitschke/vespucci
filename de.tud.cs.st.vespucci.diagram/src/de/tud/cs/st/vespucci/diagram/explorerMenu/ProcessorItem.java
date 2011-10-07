package de.tud.cs.st.vespucci.diagram.explorerMenu;

public class ProcessorItem {

	private IDiagramProcessor diagramProcessor;
	private String label;

	public ProcessorItem(IDiagramProcessor converter, String label) {

		this.diagramProcessor = converter;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IDiagramProcessor getProcessors() {
		return diagramProcessor;
	}

	public void setConverter(IDiagramProcessor diagramProcessor) {
		this.diagramProcessor = diagramProcessor;
	}

}
