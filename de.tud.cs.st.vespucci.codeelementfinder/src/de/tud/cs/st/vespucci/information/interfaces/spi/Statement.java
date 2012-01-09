package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.IStatement;

public class Statement extends CodeElement implements IStatement {

	int lineNumber;
	
	public Statement(String packageIdentifier, String simpleClassName, int lineNumber){
		super(packageIdentifier, simpleClassName);
		
		this.lineNumber = lineNumber;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}

}
