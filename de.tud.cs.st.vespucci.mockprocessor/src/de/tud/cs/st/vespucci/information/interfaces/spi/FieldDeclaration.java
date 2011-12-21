package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;

public class FieldDeclaration extends CodeElement implements IFieldDeclaration {

	private String fieldName;
	
	public FieldDeclaration(String packageName, String simpleClassName, String fieldName) {
		super(packageName, simpleClassName);
		this.fieldName = fieldName;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

}
