package de.tud.cs.st.vespucci.diagram.explorerMenu;

public class ConverterItem {

	private IDiagramConverter converter;
	private String label;

	public ConverterItem(IDiagramConverter converter, String label) {

		this.converter = converter;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IDiagramConverter getConverter() {
		return converter;
	}

	public void setConverter(IDiagramConverter converter) {
		this.converter = converter;
	}

}
