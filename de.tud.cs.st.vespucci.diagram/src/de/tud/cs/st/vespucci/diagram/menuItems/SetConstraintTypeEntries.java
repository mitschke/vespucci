package de.tud.cs.st.vespucci.diagram.menuItems;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

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
 * @version 0.2
 * 
 */
public class SetConstraintTypeEntries extends CompoundContributionItem {

	/**
	 * All types of a constraint.
	 */
	private static final Class[] types = { ExpectedEditPart.class, InAndOutEditPart.class, IncomingEditPart.class,
			NotAllowedEditPart.class, OutgoingEditPart.class };
	/**
	 * Label to be used for menu entries. The order must corresponded to {@link #types}.
	 */
	private static final String[] typeLabel = {"Expected", "In- and Out", "Incoming",
		"Not Allowed", "Outgoing" };

	/**
	 * Descriptors for the check marks. There are two available check marks:
	 * <UL>
	 * <LI>Index 0: grey check mark, used to indicate a type, that at least one, but not all,
	 * selected constraints are of this type.
	 * </UL>
	 * <UL>
	 * <LI>Index 1: black check mark, used to indicate, that all selected constraints are of this
	 * type.
	 * </UL>
	 * <UL>
	 * <LI>Index 2: null, used to indicate, that all selected constraints are not of this type.
	 * </UL>
	 */
	private static final ImageDescriptor[] checkmark = new ImageDescriptor[3];

	static {
		checkmark[0] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/resources/checkmark_grey.png"));
				return img.getImageData();
			}
		};

		checkmark[1] = new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				final Image img = new Image(PlatformUI.getWorkbench().getDisplay(), this.getClass().getResourceAsStream(
						"/resources/checkmark_black.png"));
				return img.getImageData();
			}
		};

		checkmark[2] = null;
	}

	private static ImageDescriptor getCheckMark(final Class type) {
		final IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();

		final Object[] selectionArr = selection.toArray();

		boolean selectionContainsType = false;
		for (int i = 0; i < selection.size(); ++i) {
			if (selectionArr[i].getClass().equals(type)) {
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
		// generate entries
		final IContributionItem[] entries = new CommandContributionItem[types.length];

		for (int i = 0; i < types.length; i++) {
			final Class type = types[i];
			// Create parameter for menu item
			final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow(), "de.tud.cs.st.vespucci.diagram.menuItems.SetConstraintType_"
					+ type.getSimpleName(), "de.tud.cs.st.vespucci.diagram.SetConstraintType" + type.getSimpleName(),
					CommandContributionItem.STYLE_CHECK);
			// Delete "EditPart" at end of class name and use it as label
			contributionParameter.label = typeLabel[i];
			// Set icon
			contributionParameter.icon = getCheckMark(type);

			entries[i] = new CommandContributionItem(contributionParameter);
		}

		return entries;
	}

}
