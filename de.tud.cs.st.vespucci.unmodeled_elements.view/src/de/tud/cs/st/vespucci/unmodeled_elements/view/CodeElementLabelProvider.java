package de.tud.cs.st.vespucci.unmodeled_elements.view;

import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.codeelementfinder.Util;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

class CodeElementLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
	 * .Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element == null) {
			return "";
		}
		if (!(element instanceof ICodeElement))
			return "";
		ICodeElement value = (ICodeElement) element;
		switch (columnIndex) {
		case 0:
			return createPackageText(value);
		case 1:
			return createClassText(value);
		case 2:
			return createCodeElementText(value);
		default:
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element != null) {
			switch (columnIndex) {
			case 0:
				return JavaUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_PACKAGE);
			case 1:
				return JavaUI.getSharedImages()
						.getImage(ISharedImages.IMG_OBJS_CFILE);
			case 2:
				if (element instanceof IClassDeclaration) {
					return JavaUI.getSharedImages()
							.getImage(ISharedImages.IMG_OBJS_CLASS);

				}
				if (element instanceof IMethodDeclaration) {
					return JavaUI.getSharedImages()
							.getImage(ISharedImages.IMG_OBJS_DEFAULT);
				}
				if (element instanceof IFieldDeclaration) {
					return JavaUI.getSharedImages()
							.getImage(ISharedImages.IMG_FIELD_DEFAULT);
				}
			default:
			}
		}
		return null;
	}

	static String createPackageText(ICodeElement value) {
		return value.getPackageIdentifier().replaceAll("/", ".");
	}

	static String createClassText(ICodeElement value) {
		return value.getSimpleClassName();
	}

	static String createCodeElementText(ICodeElement value) {

		if (value instanceof IClassDeclaration) {
			return ((IClassDeclaration) value).getSimpleClassName();
		}
		if (value instanceof IMethodDeclaration) {
			IMethodDeclaration methodDeclaration = (IMethodDeclaration) value;
			String label = methodDeclaration.getMethodName() + "(";
			for (int i = 0; i < methodDeclaration.getParameterTypeQualifiers().length; i++) {
				label += Util.createSimpleTypeText(methodDeclaration
						.getParameterTypeQualifiers()[i]);
				if (i < methodDeclaration.getParameterTypeQualifiers().length - 1) {
					label += ", ";
				}
			}
			label += ")"
					+ " : "
					+ Util.createSimpleTypeText(((IMethodDeclaration) value)
							.getReturnTypeQualifier());
			;
			return label;
		}
		if (value instanceof IFieldDeclaration) {
			return ((IFieldDeclaration) value).getFieldName()
					+ " : "
					+ Util.createSimpleTypeText(((IFieldDeclaration) value)
							.getTypeQualifier());
		}

		return "";
	}

}