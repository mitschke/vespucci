package de.tud.cs.st.vespucci.ensemblesourcemap.model;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;

public class Class extends TreeElement {

	private IClassDeclaration classDeclaration;
	private LinkedList<IClassIncredits> incredits = new LinkedList<IClassIncredits>();
	
	public Class(IClassDeclaration classDeclaration, TreeElement parent) {
		super(parent);
		this.classDeclaration = classDeclaration;
	}

	@Override
	public Object[] getChildren() {
		return incredits.toArray();
	}
	
	public IClassDeclaration getClassDeclaration(){
		return classDeclaration;
	}
	
	public void setChildren(LinkedList<IClassIncredits> incredits){
		this.incredits = incredits;
	}

	@Override
	public boolean hasChildren() {
		if (incredits.size() > 0){
			return true;
		}
		return false;
	}
	
}
