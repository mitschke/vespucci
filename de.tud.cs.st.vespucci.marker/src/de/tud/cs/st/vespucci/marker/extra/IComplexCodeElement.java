package de.tud.cs.st.vespucci.marker.extra;

import org.eclipse.jdt.core.search.IJavaSearchScope;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

public interface IComplexCodeElement extends ICodeElement{
	
	void setSimpleClassName(String SimpleClassName);
	
	ICodeElement getSpecialElement();
	
	void pushToWaitingArea(String string);
	
	String popFromWaitingArea();
	
	boolean isWaitingAreaEmtpy();
	
	void setFoundPartInfos(String keyWord, IJavaSearchScope searchScope);
	
	String getLastFoundSimpleClassName();
	
	IJavaSearchScope getSearchScopeOfLastFoundSimpleClassName();
	
	boolean alreadyFindSomePart();
	
}
