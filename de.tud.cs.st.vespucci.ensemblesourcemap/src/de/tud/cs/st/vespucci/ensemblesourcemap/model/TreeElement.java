package de.tud.cs.st.vespucci.ensemblesourcemap.model;

public abstract class TreeElement {
	
	private TreeElement parent;
	
	public TreeElement(TreeElement parent){
		this.parent = parent;
	}
	
	public TreeElement getParent(){
		return parent;
	}
	
	abstract public boolean hasChildren();
	
	abstract public Object[] getChildren();
}
