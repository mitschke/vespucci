package de.tud.cs.st.vespucci.marker.extra.spi;

import java.util.Stack;

import org.eclipse.jdt.core.search.IJavaSearchScope;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.marker.extra.IComplexeCodeElement;

public class ComplexCodeElement implements IComplexeCodeElement {

	private String packageIdentifier;
	private String simpleClassName;
	private ICodeElement specialCodeElement;
	private Stack<String> keyWords;
	private Stack<IJavaSearchScope> searchScope;
	private Stack<String> waitingArea;

	
	public ComplexCodeElement(String packageIdentifier, String simpleClassName,
			ICodeElement specialCodeElement) {
		this.packageIdentifier = packageIdentifier;
		this.simpleClassName = simpleClassName;
		this.specialCodeElement = specialCodeElement;
		keyWords = new Stack<String>();
		searchScope = new Stack<IJavaSearchScope>();
		waitingArea = new Stack<String>();
	}

	@Override
	public String getPackageIdentifier() {
		return packageIdentifier;
	}

	@Override
	public String getSimpleClassName() {
		return simpleClassName;
	}
	
	public void setSimpleClassName(String simpleClassName){
		this.simpleClassName = simpleClassName;
	}

	@Override
	public ICodeElement getSpecialElement() {
		return specialCodeElement;
	}

	@Override
	public void pushLastFoundKeyWordAndScope(String keyWord,
			IJavaSearchScope searchScope) {
		this.keyWords.push(keyWord);
		this.searchScope.push(searchScope);
	}

	@Override
	public String peekLastFoundKey() {
		if (!keyWords.isEmpty()){
			return keyWords.peek();
		}
		return null;
	}

	@Override
	public IJavaSearchScope peekLastScope() {
		if (!searchScope.isEmpty()){
			return searchScope.peek();
		}
		return null;		
	}

	@Override
	public boolean areStacksEmpty() {
		return (keyWords.isEmpty() && searchScope.isEmpty());
	}

	@Override
	public void pushToWaitingArea(String string) {
		waitingArea.push(string);
	}

	@Override
	public String popFromWaitingArea() {
		return waitingArea.pop();
	}

	@Override
	public boolean isWaitingAreaEmtpy() {
		return waitingArea.isEmpty();
	}

}
