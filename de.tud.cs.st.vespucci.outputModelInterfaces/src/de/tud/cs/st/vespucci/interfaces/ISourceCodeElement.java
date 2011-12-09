package de.tud.cs.st.vespucci.interfaces;

/**
 * A source code element is either a whole class, a method or a field
 * 
 */
public interface ISourceCodeElement {
	
	public String getPackageName();
		
	public String getClassName();
		
	public int getLineNumber();

}
