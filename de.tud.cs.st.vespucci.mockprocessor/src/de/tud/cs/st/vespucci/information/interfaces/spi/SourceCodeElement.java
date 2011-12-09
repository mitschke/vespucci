package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;

public class SourceCodeElement implements ISourceCodeElement {
	
	private String packageName;
	private String className;
	private int lineNumber;
	
	public SourceCodeElement(String packageName, String className,
			Integer lineNumber) {
		this.packageName = packageName;
		this.className = className;
		this.lineNumber = lineNumber;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}
	
}
