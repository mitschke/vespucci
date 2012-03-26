/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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
 *   - Neither the name of the Software Engineering Group or Technische 
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class VespucciParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser ensembleName_5015Parser;

	/**
	 * @generated
	 */
	private IParser getEnsembleName_5015Parser() {
		if (ensembleName_5015Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			ensembleName_5015Parser = parser;
		}
		return ensembleName_5015Parser;
	}

	/**
	 * @generated
	 */
	private IParser ensembleDescription_5016Parser;

	/**
	 * @generated
	 */
	private IParser getEnsembleDescription_5016Parser() {
		if (ensembleDescription_5016Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Description() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			ensembleDescription_5016Parser = parser;
		}
		return ensembleDescription_5016Parser;
	}

	/**
	 * @generated
	 */
	private IParser emptyName_5017Parser;

	/**
	 * @generated
	 */
	private IParser getEmptyName_5017Parser() {
		if (emptyName_5017Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			emptyName_5017Parser = parser;
		}
		return emptyName_5017Parser;
	}

	/**
	 * @generated
	 */
	private IParser ensembleName_5013Parser;

	/**
	 * @generated
	 */
	private IParser getEnsembleName_5013Parser() {
		if (ensembleName_5013Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			ensembleName_5013Parser = parser;
		}
		return ensembleName_5013Parser;
	}

	/**
	 * @generated
	 */
	private IParser ensembleDescription_5014Parser;

	/**
	 * @generated
	 */
	private IParser getEnsembleDescription_5014Parser() {
		if (ensembleDescription_5014Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Description() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			ensembleDescription_5014Parser = parser;
		}
		return ensembleDescription_5014Parser;
	}

	/**
	 * @generated
	 */
	private IParser emptyName_5012Parser;

	/**
	 * @generated
	 */
	private IParser getEmptyName_5012Parser() {
		if (emptyName_5012Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getAbstractEnsemble_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features);
			emptyName_5012Parser = parser;
		}
		return emptyName_5012Parser;
	}

	/**
	 * @generated
	 */
	private IParser incomingName_6005Parser;

	/**
	 * @generated
	 */
	private IParser getIncomingName_6005Parser() {
		if (incomingName_6005Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			incomingName_6005Parser = parser;
		}
		return incomingName_6005Parser;
	}

	/**
	 * @generated
	 */
	private IParser outgoingName_6003Parser;

	/**
	 * @generated
	 */
	private IParser getOutgoingName_6003Parser() {
		if (outgoingName_6003Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			outgoingName_6003Parser = parser;
		}
		return outgoingName_6003Parser;
	}

	/**
	 * @generated
	 */
	private IParser inAndOutName_6001Parser;

	/**
	 * @generated
	 */
	private IParser getInAndOutName_6001Parser() {
		if (inAndOutName_6001Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			inAndOutName_6001Parser = parser;
		}
		return inAndOutName_6001Parser;
	}

	/**
	 * @generated
	 */
	private IParser notAllowedName_6004Parser;

	/**
	 * @generated
	 */
	private IParser getNotAllowedName_6004Parser() {
		if (notAllowedName_6004Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			notAllowedName_6004Parser = parser;
		}
		return notAllowedName_6004Parser;
	}

	/**
	 * @generated
	 */
	private IParser expectedName_6002Parser;

	/**
	 * @generated
	 */
	private IParser getExpectedName_6002Parser() {
		if (expectedName_6002Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			expectedName_6002Parser = parser;
		}
		return expectedName_6002Parser;
	}

	/**
	 * @generated
	 */
	private IParser globalIncomingName_6006Parser;

	/**
	 * @generated
	 */
	private IParser getGlobalIncomingName_6006Parser() {
		if (globalIncomingName_6006Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			globalIncomingName_6006Parser = parser;
		}
		return globalIncomingName_6006Parser;
	}

	/**
	 * @generated
	 */
	private IParser globalOutgoingName_6007Parser;

	/**
	 * @generated
	 */
	private IParser getGlobalOutgoingName_6007Parser() {
		if (globalOutgoingName_6007Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			globalOutgoingName_6007Parser = parser;
		}
		return globalOutgoingName_6007Parser;
	}

	/**
	 * @generated
	 */
	private IParser violationName_6009Parser;

	/**
	 * @generated
	 */
	private IParser getViolationName_6009Parser() {
		if (violationName_6009Parser == null) {
			EAttribute[] features = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			EAttribute[] editableFeatures = new EAttribute[] { de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Name() };
			de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser parser = new de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.MessageFormatParser(
					features, editableFeatures);
			violationName_6009Parser = parser;
		}
		return violationName_6009Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleNameEditPart.VISUAL_ID:
			return getEnsembleName_5015Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescriptionEditPart.VISUAL_ID:
			return getEnsembleDescription_5016Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyNameEditPart.VISUAL_ID:
			return getEmptyName_5017Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart.VISUAL_ID:
			return getEnsembleName_5013Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescription2EditPart.VISUAL_ID:
			return getEnsembleDescription_5014Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyName2EditPart.VISUAL_ID:
			return getEmptyName_5012Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingNameEditPart.VISUAL_ID:
			return getIncomingName_6005Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingNameEditPart.VISUAL_ID:
			return getOutgoingName_6003Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutNameEditPart.VISUAL_ID:
			return getInAndOutName_6001Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedNameEditPart.VISUAL_ID:
			return getNotAllowedName_6004Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedNameEditPart.VISUAL_ID:
			return getExpectedName_6002Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingNameEditPart.VISUAL_ID:
			return getGlobalIncomingName_6006Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingNameEditPart.VISUAL_ID:
			return getGlobalOutgoingName_6007Parser();
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationNameEditPart.VISUAL_ID:
			return getViolationName_6009Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object,
			String parserHint) {
		return ParserService.getInstance().getParser(
				new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
					.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
