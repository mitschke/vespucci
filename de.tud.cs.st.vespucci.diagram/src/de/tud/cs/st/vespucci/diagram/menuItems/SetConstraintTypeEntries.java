/**
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
package de.tud.cs.st.vespucci.diagram.menuItems;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import de.tud.cs.st.vespucci.diagram.handler.SetConstraintTypeParameter;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;

/**
 * This class provides the entries for the "Edit Constraint"/"Set Type"-menu.
 * For each entry in {@link #types} one menu-entry will be generated.
 * 
 * @author Alexander Weitzmann
 * @version 0.5
 * 
 */
public class SetConstraintTypeEntries extends CompoundContributionItem {
	/**
	 * Descriptors for the check marks. There are two available check marks:
	 * <UL>
	 * <LI>Index 0: grey check mark, used to indicate a dependency, that is not set for all selected
	 * constraints, but for at least one.
	 * </UL>
	 * <UL>
	 * <LI>Index 1: black check mark, used to indicate a dependency, that is set for all selected
	 * constraints.
	 * </UL>
	 * <UL>
	 * <LI>Index 2: unchecked, used to indicate a dependency, that is set for no selected
	 * constraints.
	 * </UL>
	 */
	private static final ImageDescriptor[] checkmark = new ImageDescriptor[3];

	static {
		checkmark[0] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/icons/checkboxes/grayed.gif"));
				return img.getImageData();
			}
		};

		checkmark[1] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/icons/checkboxes/checked.gif"));
				return img.getImageData();
			}
		};

		checkmark[2] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/icons/checkboxes/unchecked.gif"));
				return img.getImageData();
			}
		};
	}

	/**
	 * This method traverses all selected constraints and returns, which check mark should be used.
	 * 
	 * @param type
	 *            Connection-Type to be checked.
	 * @return The index for the correct check mark in {@link #checkmark}.
	 */
	private static ImageDescriptor getCheckMark(final IElementType type) {
		final ConnectionEditPart[] selectedConnections = getSelectedConnectionEditParts();

		boolean selectionContainsType = false;

		for (int i = 0; i < selectedConnections.length; ++i) {

			final ConnectionEditPart currentConnection = selectedConnections[i];

			if (isConnectionOfGivenType(currentConnection, type)) {
				if (i != 0 && !selectionContainsType) {
					// current constraint is of given type, but some constraints aren't.
					return checkmark[0];
				}
				selectionContainsType = true;

			} else {
				if (i != 0 && selectionContainsType) {
					// current constraint is not of given type, but some constraints are.
					return checkmark[0];
				}
			}
		}

		if (selectionContainsType) {
			// all constraints are of the given type.
			return checkmark[1];
		}

		// all constraints aren't of the given type.
		return checkmark[2];
	}

	/**
	 * @return The connections currently selected in the active window.
	 */
	private static ConnectionEditPart[] getSelectedConnectionEditParts() {
		final IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();

		final Object[] selectionArr = selection.toArray();

		// This array will contain the casted selection-objects.
		final ConnectionEditPart[] selectedConnections = new ConnectionEditPart[selectionArr.length];

		for (int i = 0; i < selectionArr.length; ++i) {
			selectedConnections[i] = (ConnectionEditPart) selectionArr[i];
		}
		
		return selectedConnections;
	}

	private static boolean isConnectionOfGivenType(final ConnectionEditPart connection, final IElementType type) {
		return type.getEClass().isSuperTypeOf(connection.resolveSemanticElement().eClass());
	}

	@Override
	protected IContributionItem[] getContributionItems() {

		// Map of all constraint types
		final Map<String, IElementType> typeParams = new SetConstraintTypeParameter().getParameterValues();

		// entries to be generated
		final IContributionItem[] entries = new CommandContributionItem[typeParams.size()];		
		
		// generate entries
		int i = 0;
		for (final String typeName : typeParams.keySet()) {			
			final IElementType type = typeParams.get(typeName);

			// Create parameter for menu item
			final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow(), "de.tud.cs.st.vespucci.diagram.menuItems.SetConstraintType_"
					+ typeName, "de.tud.cs.st.vespucci.diagram.SetConstraintType", CommandContributionItem.STYLE_PUSH);

			// Set type for handler
			final Map<String, String> parameter = new HashMap<String, String>(1);
			parameter.put("de.tud.cs.st.vespucci.diagram.SetConstraintTypeParam", typeName);
			contributionParameter.parameters = parameter;

			// Delete "EditPart" at end of class name and use it as label
			contributionParameter.label = typeName;

			// Set icon
			contributionParameter.icon = getCheckMark(type);
			
			// set menu-entry
			entries[i] = new CommandContributionItem(contributionParameter);
			
			if (allConnectionsPointToEmptyEnsembles()) {
				entries[i].setVisible(false);
			}

			++i;
		}

		return entries;
	}

	private static boolean allConnectionsPointToEmptyEnsembles() {
		boolean result = true;
		
		for (ConnectionEditPart connectionEditPart : getSelectedConnectionEditParts()) {
			result &= (((Connection)connectionEditPart.resolveSemanticElement()).getSource() instanceof Dummy) ||
					  (((Connection)connectionEditPart.resolveSemanticElement()).getTarget() instanceof Dummy);
		}

		return result;
	}
}
