package de.tud.cs.st.vespucci.marker.extra;

import java.util.LinkedList;

import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.internal.core.search.JavaSearchScope;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

public interface IComplexeCodeElement extends ICodeElement{
	
	void setSimpleClassName(String SimpleClassName);
	
	ICodeElement getSpecialElement();
	
	void pushToWaitingArea(String string);
	
	String popFromWaitingArea();
	
	boolean isWaitingAreaEmtpy();
	
	void pushLastFoundKeyWordAndScope(String keyWord, IJavaSearchScope searchScope);
	
	String peekLastFoundKey();
	
	IJavaSearchScope peekLastScope();
	
	boolean areStacksEmpty();
	
}
