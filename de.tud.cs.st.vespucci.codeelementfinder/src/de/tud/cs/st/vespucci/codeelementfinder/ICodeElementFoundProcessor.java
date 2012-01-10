package de.tud.cs.st.vespucci.codeelementfinder;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

public interface ICodeElementFoundProcessor<T> {

	public void processFoundCodeElement(IMember member, T passenger, IProject project);
	
	public void processFoundCodeElement(IMember member, T passenger, int lineNr, IProject project);
	
	public void noMatchFound(ICodeElement codeElement, T passenger);
}
