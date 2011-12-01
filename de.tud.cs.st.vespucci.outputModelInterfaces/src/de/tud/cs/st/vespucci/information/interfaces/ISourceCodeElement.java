package de.tud.cs.st.vespucci.information.interfaces;

/**
 * A source code element is either a whole class, a method or a field
 * 
 */
public interface ISourceCodeElement {

	/**
	 * can I mark elements just with information from this interface?
	 */
	
	public String getCodeElementPackage();
	
	public void setCodeElementPackage(String codeElementPackage);
	
	public String getCodeClassName();
	
	public void setCodeClassName(String codeClassName);
	
	public Integer getLineNr();
	
	public void setLineNr(Integer lineNr);

}
