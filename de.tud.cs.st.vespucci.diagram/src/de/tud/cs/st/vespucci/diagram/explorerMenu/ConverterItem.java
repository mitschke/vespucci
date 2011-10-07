package de.tud.cs.st.vespucci.diagram.explorerMenu;

public class ConverterItem {

	private IDiagramProcessor converter;
	private String label;

	public ConverterItem(IDiagramProcessor converter, String label) {

		this.converter = converter;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IDiagramProcessor getConverter() {
		return converter;
	}

	public void setConverter(IDiagramProcessor converter) {
		this.converter = converter;
	}

}
