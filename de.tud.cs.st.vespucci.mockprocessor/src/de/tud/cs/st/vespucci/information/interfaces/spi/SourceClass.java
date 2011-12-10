package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.ISourceClass;

public class SourceClass extends SourceCodeElement implements ISourceClass {

	public SourceClass(String packageName, String className) {
		super(packageName, className, -1);
	}

}
