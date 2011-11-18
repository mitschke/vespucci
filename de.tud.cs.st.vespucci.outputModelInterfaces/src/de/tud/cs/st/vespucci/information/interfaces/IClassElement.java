package de.tud.cs.st.vespucci.information.interfaces;

/**
 * TODO find a better name
 * 
 */
public interface IClassElement extends ISourceCodeElement {
	String getName();

	String getPackageName(); /* for type safty this could be of type Package */

	int getLineNumber(); // this is relevant for all source code elements

}
