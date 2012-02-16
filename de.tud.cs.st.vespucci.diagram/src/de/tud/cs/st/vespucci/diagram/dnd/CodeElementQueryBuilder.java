package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.List;

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

public class CodeElementQueryBuilder implements IQueryBuilderConstants {

	public static String createQueryFromICodeElements(
			List<ICodeElement> codeElements) {
		if (codeElements.size() == 0)
			return "";
		String newQuery = "";

		for (ICodeElement codeElement : codeElements) {
			String elementQuery = createQueryFromCodeElement(codeElement);
			newQuery = QueryBuilder.concatenateQueries(newQuery, elementQuery);
		}

		return newQuery;
	}

	public static String createQueryFromCodeElement(ICodeElement codeElement) {
		if (codeElement instanceof IClassDeclaration)
			return createQueryFromClassDeclaration((IClassDeclaration) codeElement);
		if (codeElement instanceof IMethodDeclaration)
			return createQueryFromMethodDeclaration((IMethodDeclaration) codeElement);
		if (codeElement instanceof IFieldDeclaration)
			return createQueryFromFieldDeclaration((IFieldDeclaration) codeElement);
		throw new IllegalArgumentException(
				"could not create a Vespucci query for " + codeElement);
	}

	private static String createQueryFromClassDeclaration(
			IClassDeclaration codeElement) {
		return String.format("%s('%s','%s')", CLASS_WITH_MEMBERS_QUERY,
				qualifierToJava(codeElement.getPackageIdentifier()),
				codeElement.getSimpleClassName());
	}

	private static String createQueryFromMethodDeclaration(
			IMethodDeclaration codeElement) {

		String parameters = "";
		for (String parameterType : codeElement.getParameterTypeQualifiers()) {
			parameters = QueryBuilder.concatenateParameters(parameters, "'"
					+ typeQualifierToJava(parameterType) + "'");
		}
		return String.format("%s('%s','%s','%s','%s',[%s])", METHOD_QUERY,
				qualifierToJava(codeElement.getPackageIdentifier()),
				codeElement.getSimpleClassName(), codeElement.getMethodName(),
				typeQualifierToJava(codeElement.getReturnTypeQualifier()),
				parameters);
	}

	private static String createQueryFromFieldDeclaration(
			IFieldDeclaration codeElement) {
		return String.format("%s('%s','%s','%s','%s')", FIELD_QUERY,
				qualifierToJava(codeElement.getPackageIdentifier()),
				codeElement.getSimpleClassName(), codeElement.getFieldName(),
				typeQualifierToJava(codeElement.getTypeQualifier()));
	}

	private static String typeQualifierToJava(String typeQualifier) {
		StringBuilder arraySuffix = new StringBuilder("");
		for (int i = 0; i < typeQualifierArrayCount(typeQualifier); i++) {
			arraySuffix.append("[]");
		}
		String componentType = stripArrayQualifiers(typeQualifier);
		if (isReferenceType(componentType))
			return qualifierToJava(stripReferenceQualifier(componentType))
					+ arraySuffix.toString();
		return getPrimitiveType(componentType)
				+ arraySuffix.toString();
	}

	private static String stripArrayQualifiers(String typeQualifier) {
		if (!typeQualifier.startsWith("["))
			return typeQualifier;
		return typeQualifier.substring(typeQualifier.lastIndexOf('[') + 1);
	}

	private static String stripReferenceQualifier(String typeQualifier) {
		if (!isReferenceType(typeQualifier))
			return typeQualifier;
		return typeQualifier.substring(1, typeQualifier.length() - 1);
	}

	private static int typeQualifierArrayCount(String typeQualifier) {
		if (!typeQualifier.startsWith("["))
			return 0;
		return typeQualifier.lastIndexOf('[') - typeQualifier.indexOf('[') + 1;
	}

	private static boolean isReferenceType(String typeQualifier) {
		return typeQualifier.startsWith("L") && typeQualifier.endsWith(";");
	}

	private static String getPrimitiveType(String typeQualifier) {
		if(typeQualifier.length() != 1)
			throw new IllegalArgumentException(String.format(
					"[%s] is not a primitive type", typeQualifier));
		switch (typeQualifier.charAt(0)) {
		case 'B':
			return "byte";
		case 'C':
			return "char";
		case 'D':
			return "double";
		case 'F':
			return "float";
		case 'I':
			return "int";
		case 'J':
			return "long";
		case 'S':
			return "short";
		case 'Z':
			return "boolean";
		case 'V':
			return "void";
		}
		throw new IllegalArgumentException(String.format(
				"Failed to determine primitive type of [%s]", typeQualifier));
	}

	private static String qualifierToJava(String qualifier) {
		return qualifier.replaceAll("/", ".");
	}
}
