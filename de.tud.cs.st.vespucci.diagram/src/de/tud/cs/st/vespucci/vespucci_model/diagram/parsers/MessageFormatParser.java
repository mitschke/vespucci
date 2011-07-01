/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.parsers;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.ParsePosition;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.osgi.util.NLS;

/**
 * @generated
 */
public class MessageFormatParser extends
		de.tud.cs.st.vespucci.vespucci_model.diagram.parsers.AbstractParser {

	/**
	 * @generated
	 */
	private String defaultPattern;

	/**
	 * @generated
	 */
	private String defaultEditablePattern;

	/**
	 * @generated
	 */
	private MessageFormat viewProcessor;

	/**
	 * @generated
	 */
	private MessageFormat editorProcessor;

	/**
	 * @generated
	 */
	private MessageFormat editProcessor;

	/**
	 * @generated
	 */
	public MessageFormatParser(EAttribute[] features) {
		super(features);
	}

	/**
	 * @generated
	 */
	public MessageFormatParser(EAttribute[] features,
			EAttribute[] editableFeatures) {
		super(features, editableFeatures);
	}

	/**
	 * @generated
	 */
	protected String getDefaultPattern() {
		if (defaultPattern == null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < features.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultPattern = sb.toString();
		}
		return defaultPattern;
	}

	/**
	 * @generated
	 */
	public void setViewPattern(String viewPattern) {
		super.setViewPattern(viewPattern);
		viewProcessor = null;
	}

	/**
	 * @generated
	 */
	public void setEditorPattern(String editorPattern) {
		super.setEditorPattern(editorPattern);
		editorProcessor = null;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getViewProcessor() {
		if (viewProcessor == null) {
			viewProcessor = new MessageFormat(
					getViewPattern() == null ? getDefaultPattern()
							: getViewPattern());
		}
		return viewProcessor;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getEditorProcessor() {
		if (editorProcessor == null) {
			editorProcessor = new MessageFormat(
					getEditorPattern() == null ? getDefaultEditablePattern()
							: getEditorPattern());
		}
		return editorProcessor;
	}

	/**
	 * @generated
	 */
	protected String getDefaultEditablePattern() {
		if (defaultEditablePattern == null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < editableFeatures.length; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append('{');
				sb.append(i);
				sb.append('}');
			}
			defaultEditablePattern = sb.toString();
		}
		return defaultEditablePattern;
	}

	/**
	 * @generated
	 */
	public void setEditPattern(String editPattern) {
		super.setEditPattern(editPattern);
		editProcessor = null;
	}

	/**
	 * @generated
	 */
	protected MessageFormat getEditProcessor() {
		if (editProcessor == null) {
			editProcessor = new MessageFormat(
					getEditPattern() == null ? getDefaultEditablePattern()
							: getEditPattern());
		}
		return editProcessor;
	}

	/**
	 * @generated
	 */
	public String getEditString(IAdaptable adapter, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		return getEditorProcessor().format(getEditableValues(element),
				new StringBuffer(), new FieldPosition(0)).toString();
	}

	/**
	 * @generated
	 */
	public IParserEditStatus isValidEditString(IAdaptable adapter,
			String editString) {
		ParsePosition pos = new ParsePosition(0);
		Object[] values = getEditProcessor().parse(editString, pos);
		if (values == null) {
			return new ParserEditStatus(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.ID,
					IParserEditStatus.UNEDITABLE,
					NLS.bind(
							de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.MessageFormatParser_InvalidInputError,
							new Integer(pos.getErrorIndex())));
		}
		return validateNewValues(values);
	}

	/**
	 * @generated
	 */
	public ICommand getParseCommand(IAdaptable adapter, String newString,
			int flags) {
		Object[] values = getEditProcessor().parse(newString,
				new ParsePosition(0));
		return getParseCommand(adapter, values, flags);
	}

	/**
	 * @generated
	 */
	public String getPrintString(IAdaptable adapter, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		return getViewProcessor().format(getValues(element),
				new StringBuffer(), new FieldPosition(0)).toString();
	}

}
