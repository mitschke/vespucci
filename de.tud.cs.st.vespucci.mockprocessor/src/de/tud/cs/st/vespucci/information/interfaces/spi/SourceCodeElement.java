package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;

public class SourceCodeElement implements ISourceCodeElement {
	
	private String packageIdentifier;
	private String simpleClassName;
	private int lineNumber;
	
	public SourceCodeElement(String packageName, String simpleClassName,
			Integer lineNumber) {
		
		this.packageIdentifier = packageName;
		this.simpleClassName = simpleClassName;
		this.lineNumber = lineNumber;
	}

	@Override
	public String getPackageIdentifier() {
		return packageIdentifier;
	}

	@Override
	public String getSimpleClassName() {
		return simpleClassName;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}
	
}
