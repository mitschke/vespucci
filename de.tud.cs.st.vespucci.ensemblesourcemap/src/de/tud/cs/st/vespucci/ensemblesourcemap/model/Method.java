package de.tud.cs.st.vespucci.ensemblesourcemap.model;

import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

public class Method extends TreeElement implements IClassIncredits {
	
	public IMethodDeclaration methodDeclaration;
	
	public Method(IMethodDeclaration methodDeclaration, TreeElement parent) {
		super(parent);
		this.methodDeclaration = methodDeclaration;
	}

	@Override
	public Object[] getChildren() {
		return new Object[0];
	}

	public IMethodDeclaration getMethodDeclaration(){
		return methodDeclaration;
	}
	
	
	@Override
	public boolean hasChildren() {
		return false;
	}

}
