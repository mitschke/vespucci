package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;

public class FieldDeclaration extends CodeElement implements IFieldDeclaration {

	private String fieldName;
	private String typeQualifier;
	
	public FieldDeclaration(String packageName, String simpleClassName, String fieldName, String typeQualifier) {
		super(packageName, simpleClassName);
		this.fieldName = fieldName;
		this.typeQualifier = typeQualifier;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}
	
	@Override
	public String getTypeQualifier(){
		return typeQualifier;
	}

}
