package de.tud.cs.st.vespucci.marker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class DescriptionFab {
	
	HashMap<String, Method> descMap;
	
	public DescriptionFab(){
		
		this.descMap = new HashMap<String, Method>();
		fillDescMapWithViolationKinds();
		
	}
	
	public String getDescription(IViolation violation){
	
		Method genMethod = this.descMap.get(violation.getViolatingKind());
		return callMethod(violation, genMethod);
	}
	
	public String getDescription(IViolationSummary violationSummmary){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("There are ");
		sb.append(violationSummmary.numberOfViolations());
		sb.append("from Ensemble ");
		sb.append(violationSummmary.getSourceEnsemble());
		sb.append(" to Ensemble ");
		sb.append(violationSummmary.getTargetEnsemble());
		// Optional:
		sb.append(" (Contraint Type: )");
		sb.append(violationSummmary.getConstraint().getDependencyKind());
		sb.append(")");
		
		return sb.toString();
	}

	private String callMethod(IViolation violation,
			Method genMethod) {
		
		String result = null;
		try {	
			result = (String) genMethod.invoke(null, violation);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void fillDescMapWithViolationKinds() {
		try {
			this.descMap.put("extends", GenMethods.class.getMethod("genExtends", IViolation.class));
			this.descMap.put("implements", GenMethods.class.getMethod("genImplements", IViolation.class));
			this.descMap.put("invoke_special", GenMethods.class.getMethod("genInvokeSpecial", IViolation.class));
			this.descMap.put("invoke_virtual", GenMethods.class.getMethod("genInvokeVirtual", IViolation.class));
			this.descMap.put("invoke_interface", GenMethods.class.getMethod("genInvokeInterface", IViolation.class));
			this.descMap.put("write_field", GenMethods.class.getMethod("genWriteField", IViolation.class));
			this.descMap.put("read_field", GenMethods.class.getMethod("genReadField", IViolation.class));
			this.descMap.put("parameter", GenMethods.class.getMethod("genParameter", IViolation.class));
			this.descMap.put("return_type", GenMethods.class.getMethod("genReturnType", IViolation.class));
			this.descMap.put("cast", GenMethods.class.getMethod("genCast", IViolation.class));
			this.descMap.put("create", GenMethods.class.getMethod("genCreate", IViolation.class));
			this.descMap.put("field_type", GenMethods.class.getMethod("genFieldType", IViolation.class));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
	}
	
	private static class GenMethods{
		
		@SuppressWarnings("unused")
		public static String genExtends(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(false, violation));
			sb.append("extends class: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genImplements(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(false, violation));
			sb.append("implements interface: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		
		@SuppressWarnings("unused")
		public static String genInvokeVirtual(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("calls a method on class: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		

		@SuppressWarnings("unused")
		public static String genInvokeInterface(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("calls a method on interface: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genWriteField(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("assigns a new Value to a field in ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genReadField(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("reads the Value of a field in ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genParameter(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("declares a parameter of type ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genReturnType(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("has the return type of");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genCast(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("casts an object to ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genCreate(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(sourcePrefix(true, violation));
			sb.append("instantiates class: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}
		
		@SuppressWarnings("unused")
		public static String genFieldType(IViolation violation){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("A field in ");
			sb.append(sourcePrefix(false, violation));
			sb.append("is declared as type: ");
			sb.append(targetSuffix(violation));
			
			return sb.toString();
		}

		private static Object targetSuffix(IViolation violation) {
			
			StringBuffer sb = new StringBuffer();
			sb.append(violation.getTargetElement().getSimpleClassName());
			sb.append(" (Ensemble: ");
			sb.append(violation.getTargetEnsemble().getName());
			sb.append(")");
			
			return sb;
		}
		
		private static Object sourcePrefix(boolean methodPrefix, IViolation violation) {
			
			StringBuffer sb = new StringBuffer();
			
			if (methodPrefix){
				sb.append("A method in class: ");
			}else{
				sb.append("Class: ");
			}
			sb.append(violation.getSourceElement().getSimpleClassName());
			sb.append(" (Ensemble: ");
			sb.append(violation.getSourceEnsemble().getName());
			sb.append(") ");
			
			return sb;
		}
		
		@SuppressWarnings("unused")
		public static String genInvokeSpecial(IViolation violation){
			
			return violation.getViolatingKind();
		}
		
		
		
	}

}
