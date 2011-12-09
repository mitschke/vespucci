package de.tud.cs.st.vespucci.interfaces;

/**
 * A source code element is either a whole class, a method or a field
 * Always identified by a PackageIdentifier, a SimpleClassName and a LineNumber
 * We dont distinguish here between classes, methods or just simple fields
 * 
 */
public interface ISourceCodeElement {
	
	public String getPackageIdentifier();
		
	// NOT SimpleClassName.java
	public String getSimpleClassName();
		
	public int getLineNumber();

}
