package de.tud.cs.st.vespucci.information.interfaces;

/**
 * TODO find a better name
 * 
 */
public interface IMethodElement extends ISourceCodeElement {
	IClassElement getDeclaringClass();

	String getName();

	/*
	 * see which parts of signature are needed, ReturnType/Parameters?
	 */

	int getLineNumber(); // this is relevant for all source code elements

}
