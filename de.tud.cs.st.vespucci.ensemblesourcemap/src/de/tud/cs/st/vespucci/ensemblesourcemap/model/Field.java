package de.tud.cs.st.vespucci.ensemblesourcemap.model;

import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;

public class Field extends TreeElement implements IClassIncredits {

	private IFieldDeclaration fieldDeclaration;
	
	public Field(IFieldDeclaration field, TreeElement parent) {
		super(parent);
		this.fieldDeclaration = field;
	}

	@Override
	public Object[] getChildren() {
		return new Object[0];
	}

	public IFieldDeclaration getFieldDeclaration(){
		return fieldDeclaration;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}


}
