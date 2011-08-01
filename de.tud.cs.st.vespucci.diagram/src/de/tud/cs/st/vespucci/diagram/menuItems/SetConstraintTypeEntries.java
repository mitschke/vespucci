package de.tud.cs.st.vespucci.diagram.menuItems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
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
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart;

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
						"/resources/grayed.gif"));
				return img.getImageData();
			}
		};

		checkmark[1] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/resources/checked.gif"));
				return img.getImageData();
			}
		};
		
		checkmark[2] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/resources/unchecked.gif"));
				return img.getImageData();
			}
		};
	}

	/**
	 * This method traverses all selected constraints and returns, which check mark should be used.
	 * 
	 * @param type Connection-Type to be checked.
	 * @return The index for the correct check mark in {@link #checkmark}.
	 */
	private static ImageDescriptor getCheckMark(final IElementType type) {
		final IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();

		final Object[] selectionArr = selection.toArray();
		
		// This array will contain the casted selection-objects.
		ConnectionEditPart[] selectedConnections = new ConnectionEditPart[selectionArr.length];

		for (int i = 0; i < selectionArr.length; ++i) {
				selectedConnections[i] = (ConnectionEditPart) selectionArr[i];
		}

		boolean selectionContainsType = false;
		for (int i = 0; i < selectedConnections.length; ++i) {
			if (type.getEClass().isSuperTypeOf(selectedConnections[i].resolveSemanticElement().eClass())) {
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
		// // all constraints aren't of the given type.
		return checkmark[2];
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		// Map of all constrain types
		Map<String, IElementType> typeParams = new SetConstraintTypeParameter().getParameterValues();
		// entries to be generated
		final IContributionItem[] entries = new CommandContributionItem[typeParams.size()];
		// generate entries
		int i = 0;
		for(String typeName: typeParams.keySet()){
			IElementType type = typeParams.get(typeName);
			
			// Create parameter for menu item
			final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow(), "de.tud.cs.st.vespucci.diagram.menuItems.SetConstraintType_"
					+ typeName, "de.tud.cs.st.vespucci.diagram.SetConstraintType",
					CommandContributionItem.STYLE_PUSH);
			// Set type for handler
			Map<String, String> parameter = new HashMap<String, String>(1);
			parameter.put("de.tud.cs.st.vespucci.diagram.SetConstraintTypeParam", typeName);
			contributionParameter.parameters = parameter;
			// Delete "EditPart" at end of class name and use it as label
			contributionParameter.label = typeName;
			// Set icon
			contributionParameter.icon = getCheckMark(type);
			// set menu-entry
			entries[i] = new CommandContributionItem(contributionParameter);
			// increment counter
			++i;
		}

		return entries;
	}

}
