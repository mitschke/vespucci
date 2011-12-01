package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.information.interfaces.ISourceCodeElement;

public class SourceCodeElement implements ISourceCodeElement {
	
	private String codeElementPackage;
	private String codeClassName;
	private Integer lineNr;
	
	
	
	public SourceCodeElement(String codeElementPackage, String codeClassName,
			Integer lineNr) {
		this.codeElementPackage = codeElementPackage;
		this.codeClassName = codeClassName;
		this.lineNr = lineNr;
	}



	@Override
	public String getCodeElementPackage() {
		return codeElementPackage;
	}



	@Override
	public void setCodeElementPackage(String codeElementPackage) {
		this.codeElementPackage = codeElementPackage;
	}



	@Override
	public String getCodeClassName() {
		return codeClassName;
	}



	@Override
	public void setCodeClassName(String codeClassName) {
		this.codeClassName = codeClassName;
	}



	@Override
	public Integer getLineNr() {
		return lineNr;
	}



	@Override
	public void setLineNr(Integer lineNr) {
		this.lineNr = lineNr;
	}


	
	
}
