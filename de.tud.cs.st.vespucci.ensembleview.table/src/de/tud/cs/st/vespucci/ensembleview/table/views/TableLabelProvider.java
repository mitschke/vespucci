package de.tud.cs.st.vespucci.ensembleview.table.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.codeelementfinder.Util;
import de.tud.cs.st.vespucci.ensembleview.table.Activator;
import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		return TableLabelProvider.createText(DataManager.transfer(obj), index);
	}

	private Image loadImage(String fileLocation) {
		ImageDescriptor imageDescriptor = Activator.getImageDescriptor(fileLocation);
		if (imageDescriptor != null){
			return imageDescriptor.createImage();
		}
		return null;
	}

	private Image ensemble_icon_cache = null;
	private Image class_icon_cache = null;
	private Image method_icon_cache = null;
	private Image field_icon_cache = null;
	private Image package_icon_cache = null;
	
	public Image getColumnImage(Object obj, int index) {
		IPair<IEnsemble, ICodeElement> pair = DataManager.transfer(obj);
		if (pair != null){
			switch (index) {
			case 0:
				if (ensemble_icon_cache == null){
					ensemble_icon_cache = loadImage("icons/newpackfolder_wiz.gif");
				}
				return ensemble_icon_cache;
			case 1:
				if (package_icon_cache == null){
					package_icon_cache = loadImage("icons/package_obj.gif");
				}
				return package_icon_cache;
			case 2:
				if (class_icon_cache == null){
					class_icon_cache = loadImage("icons/class.gif");
				}
				return class_icon_cache;
			case 3:
				if (pair.getSecond() instanceof IClassDeclaration){
					if (class_icon_cache == null){
						class_icon_cache = loadImage("icons/class.gif");
					}
					return class_icon_cache;
				}
				if (pair.getSecond() instanceof IMethodDeclaration){
					if (method_icon_cache == null){
						method_icon_cache = loadImage("icons/method.gif");
					}
					return method_icon_cache;
				}
				if (pair.getSecond() instanceof IFieldDeclaration){
					if (field_icon_cache == null){
						field_icon_cache = loadImage("icons/field.gif");
					}
					return field_icon_cache;
				}
			default:
			}
		}
		return null;
	}

	public static Integer createElementTypQualifier(ICodeElement element){
		if (element instanceof IClassDeclaration){
			return 1;
		}
		if (element instanceof IFieldDeclaration){
			return 2;
		}
		if (element instanceof IMethodDeclaration){
			return 3;
		}
		return 0;
	}
	
	public static String createText(IPair<IEnsemble, ICodeElement> value, int column){
		if (value == null){
			return "";
		}
		switch (column){
		case 0:
			return createEnsembleText(value);
		case 1:
			return createPackageText(value);
		case 2:
			return createClassText(value);
		case 3:
			return createCodeElementText(value);
		default:
			return "";
		}
	}

	private static String createPackageText(IPair<IEnsemble, ICodeElement> value) {
		return value.getSecond().getPackageIdentifier().replaceAll("/", ".");
	}

	private static String createClassText(
			IPair<IEnsemble, ICodeElement> value) {
		return value.getSecond().getSimpleClassName();
	}

	private static String createCodeElementText(
			IPair<IEnsemble, ICodeElement> value) {
		
		ICodeElement codeElement = value.getSecond();
		if (codeElement instanceof IClassDeclaration){
			return ((IClassDeclaration)codeElement).getSimpleClassName();
		}
		if (codeElement instanceof IMethodDeclaration){
			IMethodDeclaration methodDeclaration = (IMethodDeclaration)codeElement;
			String label = methodDeclaration.getMethodName()+"(";
			for (int i = 0; i < methodDeclaration.getParameterTypeQualifiers().length; i++) {
				label += Util.createSimpleTypeText(methodDeclaration.getParameterTypeQualifiers()[i]);
				if (i < methodDeclaration.getParameterTypeQualifiers().length-1){
					label += ", ";
				}
			}
			label += ")"+" : " + Util.createSimpleTypeText(((IMethodDeclaration)codeElement).getReturnTypeQualifier());;
			return label;
		}
		if (codeElement instanceof IFieldDeclaration){
			return ((IFieldDeclaration)codeElement).getFieldName()+" : " + Util.createSimpleTypeText(((IFieldDeclaration)codeElement).getTypeQualifier());
		}
	
		return "";
	}

	private static String createEnsembleText(
			IPair<IEnsemble, ICodeElement> value) {
		
		IEnsemble ensemble = value.getFirst();
		String label =  ensemble.getName();
		
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		
		return label;
	}
}