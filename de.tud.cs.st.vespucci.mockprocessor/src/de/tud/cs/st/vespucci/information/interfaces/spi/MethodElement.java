package de.tud.cs.st.vespucci.information.interfaces.spi;

import java.util.List;

import de.tud.cs.st.vespucci.interfaces.IMethodElement;

public class MethodElement extends SourceCodeElement implements IMethodElement  {
	
	private String methodName;
	private String returnType;
	private List<String> paramTypes;

	public MethodElement(String packageIdentifier, String simpleClassName, String methodName, String returnType, List<String> paramTypes){
		
		super(packageIdentifier, simpleClassName, -1);
		
		this.methodName = methodName;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
	}

	@Override
	public String getMethodName() {
		return this.methodName;
	}

	@Override
	public String getReturnType() {
		return this.returnType;
	}
	
	@Override
	public List<String> getListParamTypes() {
		return this.paramTypes;
	}

}
