/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * @generated
 */
public class VespucciPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createModules1Group());
		paletteRoot.add(createDependencies2Group());
	}

	/**
	 * Creates "Modules" palette tool group
	 * @generated
	 */
	private PaletteContainer createModules1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Modules1Group_title);
		paletteContainer.setId("createModules1Group"); //$NON-NLS-1$
		paletteContainer
				.setDescription(de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Modules1Group_desc);
		paletteContainer.add(createEnsemble1CreationTool());
		paletteContainer.add(createEmptyEnsemble2CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Dependencies" palette tool group
	 * @generated
	 */
	private PaletteContainer createDependencies2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Dependencies2Group_title);
		paletteContainer.setId("createDependencies2Group"); //$NON-NLS-1$
		paletteContainer.add(createIncoming1CreationTool());
		paletteContainer.add(createOutgoing2CreationTool());
		paletteContainer.add(createInandOut3CreationTool());
		paletteContainer.add(createExpected4CreationTool());
		paletteContainer.add(createNotAllowed5CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEnsemble1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
		NodeToolEntry entry = new NodeToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Ensemble1CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Ensemble1CreationTool_desc,
				types);
		entry.setId("createEnsemble1CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEmptyEnsemble2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_2002);
		NodeToolEntry entry = new NodeToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.EmptyEnsemble2CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.EmptyEnsemble2CreationTool_desc,
				types);
		entry.setId("createEmptyEnsemble2CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createIncoming1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		LinkToolEntry entry = new LinkToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Incoming1CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Incoming1CreationTool_desc,
				types);
		entry.setId("createIncoming1CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createOutgoing2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		LinkToolEntry entry = new LinkToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Outgoing2CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Outgoing2CreationTool_desc,
				types);
		entry.setId("createOutgoing2CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInandOut3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		LinkToolEntry entry = new LinkToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.InandOut3CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.InandOut3CreationTool_desc,
				types);
		entry.setId("createInandOut3CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createExpected4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		LinkToolEntry entry = new LinkToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Expected4CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.Expected4CreationTool_desc,
				types);
		entry.setId("createExpected4CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createNotAllowed5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types
				.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		LinkToolEntry entry = new LinkToolEntry(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NotAllowed5CreationTool_title,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NotAllowed5CreationTool_desc,
				types);
		entry.setId("createNotAllowed5CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes
						.getImageDescriptor(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				List elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				List relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
