package de.tud.cs.st.vespucci.ensemblesourcemap.model;

import java.util.LinkedList;

public class Package extends TreeElement {

	private String packageIdentifier;
	private LinkedList<Class> classes = new LinkedList<Class>();
	
	public Package(String packageIdentifier, TreeElement parent) {
		super(parent);
		this.packageIdentifier = packageIdentifier;
	}

	@Override
	public Object[] getChildren() {
		return classes.toArray();
	}
	
	public void setChildren(LinkedList<Class> classes){
		this.classes = classes;
	}
	
	public String getPackageIdentifier(){
		return packageIdentifier;
	}

	@Override
	public boolean hasChildren() {
		if (classes.size() > 0){
			return true;
		}
		return false;
	}

}
