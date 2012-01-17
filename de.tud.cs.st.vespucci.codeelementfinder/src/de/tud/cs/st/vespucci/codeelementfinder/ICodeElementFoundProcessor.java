package de.tud.cs.st.vespucci.codeelementfinder;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

public interface ICodeElementFoundProcessor {

	public void processFoundCodeElement(IMember member);
	
	public void processFoundCodeElement(IMember member, int lineNr);
	
	public void noMatchFound(ICodeElement codeElement);
}
