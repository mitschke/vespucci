package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.List;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;

@SuppressWarnings("restriction")
// uses JavaModelUtil
public class JavaElementQueryBuilder implements IQueryBuilderConstants {

	public static String createQueryFromIJavaElements(
			List<IJavaElement> javaElements) {
		if (javaElements.size() == 0)
			return "";
		String newQuery = "";

		for (IJavaElement javaElement : javaElements) {
			String elementQuery = createQueryFromJavaElement(javaElement);
			newQuery = QueryBuilder.concatenateQueries(newQuery, elementQuery);
		}

		return newQuery;
	}

	public static String createQueryFromJavaElement(IJavaElement javaElement) {
		switch (javaElement.getElementType()) {
		case IJavaElement.CLASS_FILE:
			return createQueryFromClassFile((IClassFile) javaElement);
		case IJavaElement.COMPILATION_UNIT:
			return createQueryFromCompilationUnit((ICompilationUnit) javaElement);
		case IJavaElement.FIELD:
			return createQueryFromField((IField) javaElement);
		case IJavaElement.METHOD:
			return createQueryFromMethod((IMethod) javaElement);
		case IJavaElement.PACKAGE_FRAGMENT:
			return createQueryFromPackageFragment((IPackageFragment) javaElement);
		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			return createQueryFromPackageFragmentRoot((IPackageFragmentRoot) javaElement);
		case IJavaElement.JAVA_PROJECT:
			return createQueryFromJavaProject((IJavaProject) javaElement);
		case IJavaElement.TYPE:
			return createQueryFromType((IType) javaElement);
		}
		throw new IllegalArgumentException(
				"could not create a Vespucci query for " + javaElement);
	}

	private static String createResolvedTypeQuery(String typeSignature,
			IType declaringType) {
		try {
			StringBuilder arraySuffix = new StringBuilder("");
			for (int i = 0; i < Signature.getArrayCount(typeSignature); i++) {
				arraySuffix.append("[]");
			}
			String resolvedTypeName = JavaModelUtil.getResolvedTypeName(
					typeSignature, declaringType);
			return "'" + resolvedTypeName + arraySuffix.toString() + "'";
		} catch (JavaModelException e) {
			throw new IllegalArgumentException(
					(String.format(
							"Failed to resolve type signature [%s] in declaring type [%s]",
							typeSignature, declaringType)), e);
		}
	}

	private static String createQueryFromType(IType javaElement) {
		IPackageFragment packageFragment = javaElement.getPackageFragment();
		String elementName = javaElement.getElementName();
		return String.format("%s('%s','%s')", CLASS_WITH_MEMBERS_QUERY,
				packageFragment.getElementName(), elementName);
	}

	private static String createQueryFromJavaProject(IJavaProject javaElement) {
		String query = "";
		try {
			for (IPackageFragmentRoot child : javaElement
					.getAllPackageFragmentRoots()) {
				query = QueryBuilder.concatenateQueries(query,
						createQueryFromPackageFragmentRoot(child));

			}
		} catch (JavaModelException e) {
			throw new IllegalArgumentException((String.format(
					"Failed to resolve package fragment roots of [%s]",
					javaElement)), e);
		}
		return query;
	}

	private static String createQueryFromPackageFragmentRoot(
			IPackageFragmentRoot javaElement) {
		String query = "";
		try {
			for (IJavaElement child : javaElement.getChildren()) {
				query = QueryBuilder.concatenateQueries(query,
						createQueryFromJavaElement(child));
			}
		} catch (JavaModelException e) {
			throw new IllegalArgumentException((String.format(
					"Failed to resolve children of [%s]", javaElement)), e);
		}
		return query;
	}

	private static String createQueryFromPackageFragment(
			IPackageFragment javaElement) {
		return String.format("%s('%s')", PACKAGE_QUERY,
				javaElement.getElementName());
	}

	private static String createQueryFromMethod(IMethod javaElement) {
		IType declaringType = javaElement.getDeclaringType();
		try {
			String parameters = "";
			for (String parameterType : javaElement.getParameterTypes()) {
				parameters = QueryBuilder.concatenateParameters(parameters,
						createResolvedTypeQuery(parameterType, declaringType));
			}
			return String.format(
					"%s('%s','%s','%s',%s,[%s])",
					METHOD_QUERY,
					declaringType.getPackageFragment().getElementName(),
					declaringType.getElementName(),
					javaElement.getElementName(),
					createResolvedTypeQuery(javaElement.getReturnType(),
							declaringType), parameters);
		} catch (JavaModelException e) {
			throw new IllegalArgumentException((String.format(
					"Failed to resolve type signature of [%s]", javaElement)),
					e);
		}
	}

	private static String createQueryFromField(IField javaElement) {
		IType declaringType = javaElement.getDeclaringType();
		try {
			return String.format(
					"%s('%s','%s','%s',%s)",
					FIELD_QUERY,
					declaringType.getPackageFragment().getElementName(),
					declaringType.getElementName(),
					javaElement.getElementName(),
					createResolvedTypeQuery(javaElement.getTypeSignature(),
							declaringType));
		} catch (JavaModelException e) {
			throw new IllegalArgumentException((String.format(
					"Failed to resolve type signature of [%s]", javaElement)),
					e);
		}
	}

	private static String createQueryFromCompilationUnit(
			ICompilationUnit javaElement) {
		String query = "";
		try {
			for (IType type : javaElement.getTypes()) {
				query = QueryBuilder.concatenateQueries(query,
						createQueryFromType(type));
			}
		} catch (JavaModelException e) {
			throw new IllegalArgumentException((String.format(
					"Failed to resolve top-level types of [%s]", javaElement)),
					e);
		}

		return query;
	}

	private static String createQueryFromClassFile(IClassFile javaElement) {
		return createQueryFromType(javaElement.getType());
	}

}
