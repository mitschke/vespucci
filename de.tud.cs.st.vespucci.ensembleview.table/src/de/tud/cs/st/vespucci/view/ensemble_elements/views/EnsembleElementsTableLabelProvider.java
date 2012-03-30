/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.view.ensemble_elements.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.codeelementfinder.Util;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.view.ImageManager;
import de.tud.cs.st.vespucci.view.model.Pair;

/**
 * TableLabelProvider for the EnsembleElements table
 * 
 * @author Olav Lenz
 */
class EnsembleElementsTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		return createText(Pair.cast(obj, IEnsemble.class, ICodeElement.class), index);
	}

	public Image getColumnImage(Object obj, int index) {
		IPair<IEnsemble, ICodeElement> pair = Pair.cast(obj, IEnsemble.class, ICodeElement.class);
		if (pair != null){
			switch (index) {
			case 0:
				return ImageManager.getImage(ImageManager.ENSEMBLE);
			case 1:
				return ImageManager.getImage(ImageManager.ICON_PACKAGE);
			case 2:
				return ImageManager.getImage(ImageManager.ICON_CLASS);
			case 3:
				if (pair.getSecond() instanceof IClassDeclaration){
					return ImageManager.getImage(ImageManager.ICON_CLASS);
				}
				if (pair.getSecond() instanceof IMethodDeclaration){
					return ImageManager.getImage(ImageManager.ICON_METHOD_UNCOLORED);
				}
				if (pair.getSecond() instanceof IFieldDeclaration){
					return ImageManager.getImage(ImageManager.ICON_FIELD_UNCOLORED);
				}
			default:
			}
		}
		return null;
	}

	/**
	 * Create an ranking for ICodeElements to order them
	 * 
	 * @param element Element to rank
	 * @return Ranking for the given Element
	 */
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

	/**
	 * Creates text which can be used in visualizations
	 * 
	 * @param element Element text created for
	 * @param column Column text created for
	 * @return Text created for the given element and column
	 */
	private static String createText(IPair<IEnsemble, ICodeElement> element, int column){
		if (element == null){
			return "";
		}
		switch (column){
		case EnsembleElementsTableView.COLOUMN_ENSEMBLE:
			return createEnsembleText(element);
		case EnsembleElementsTableView.COLOUMN_PACKAGE:
			return createPackageText(element);
		case EnsembleElementsTableView.COLOUMN_CLASS:
			return createClassText(element);
		case EnsembleElementsTableView.COLOUMN_ELEMENT:
			return createCodeElementText(element);
		default:
			return "";
		}
	}

	/**
	 * Creates text which can be used in visualizations
	 * which contains information about the ensemble of the given element
	 * 
	 * @param element Element text created for
	 * @return Text created for the given element
	 */
	private static String createEnsembleText(IPair<IEnsemble, ICodeElement> element) {
		IEnsemble ensemble = element.getFirst();
		String label =  ensemble.getName();
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		return label;
	}

	/**
	 * Creates text which can be used in visualizations
	 * which contains information about the package of the given element
	 * 
	 * @param element Element text created for
	 * @return Text created for the given element
	 */
	private static String createPackageText(IPair<IEnsemble, ICodeElement> element) {
		return element.getSecond().getPackageIdentifier().replaceAll("/", ".");
	}

	/**
	 * Creates text for an ICodeElement which can be used in visualizations
	 * which contains information about the Class of the given element
	 * 
	 * @param element Element text created for
	 * @return Text created for the given element
	 */
	private static String createClassText(IPair<IEnsemble, ICodeElement> element) {
		return element.getSecond().getSimpleClassName();
	}

	/**
	 * Creates text which can be used in visualizations
	 * which contains information about the ICodeElement of the given element
	 * 
	 * @param element Element text created for
	 * @return Text created for the given element
	 */
	private static String createCodeElementText(IPair<IEnsemble, ICodeElement> element) {
		ICodeElement codeElement = element.getSecond();
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
}