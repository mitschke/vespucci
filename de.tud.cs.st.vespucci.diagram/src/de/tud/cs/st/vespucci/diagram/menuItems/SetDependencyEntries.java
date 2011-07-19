/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
 *   Software Engineering
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import de.tud.cs.st.vespucci.io.ValidDependenciesReader;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * This class provides the entries for the
 * "Edit Constraint"/"Set Dependency"-menu. For each entry in the validDependencies-textfile
 * ({@link de.tud.cs.st.vespucci/resources/validDependencies.txt}) one menu-entry will be generated.
 * 
 * @author Alexander Weitzmann
 * @version 0.3
 */
public class SetDependencyEntries extends CompoundContributionItem {

	/**
	 * Valid names for dependencies read from the resource-file.
	 */
	private static final String[] dependencies = new ValidDependenciesReader().getKeywords();

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

	/**
	 * This method traverses all selected constraints and returns, which check mark should be used,
	 * 
	 * @param dependency
	 *            Dependency to be checked.
	 * @return The index for the correct check mark in {@link #checkmark}.
	 */
	private static int getCheckMarkIndex(final String dependency) {
		final IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();

		final Object[] selectionArr = selection.toArray();

		// This array will contain the casted selection-objects.
		final ConnectionEditPart[] selectedConnections = new ConnectionEditPart[selection.size()];

		for (int i = 0; i < selection.size(); ++i) {
			selectedConnections[i] = (ConnectionEditPart) selectionArr[i];
		}

		boolean selectionContainsDep = false;
		boolean firstConstraint = true;
		for (final ConnectionEditPart connection : selectedConnections) {
			// Get dependencies of constraint a.k.a. name of connection
			final EObject semanticConnection = connection.resolveSemanticElement();
			final EStructuralFeature feature = semanticConnection.eClass().getEStructuralFeature(
					Vespucci_modelPackage.CONNECTION__NAME);
			final String conName = (String) semanticConnection.eGet(feature);

			// check if given dependency is set
			boolean conContainsDep = false;
			for (final String str : conName.split(", ")) {
				if (dependency.equals(str)) {
					if (!firstConstraint && !selectionContainsDep) {
						// dependency set, but there are constraints without this dependency
						return 0;
					}
					selectionContainsDep = true;
					conContainsDep = true;
					break;
				}
			}
			if (!firstConstraint && selectionContainsDep && !conContainsDep) {
				// dependency not set, but there are constraints with this dependency
				return 0;
			}

			firstConstraint = false;
		}

		if (selectionContainsDep) {
			// all constraints have the given dependency set.
			return 1;
		}
		// 2 indicates, that no icon will be used i.e. entry not checked.
		return 2;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		// generate entries
		final IContributionItem[] entries = new CommandContributionItem[dependencies.length];

		for (int i = 0; i < dependencies.length; i++) {
			final String dependency = dependencies[i];

			final int checkMarkIndex = getCheckMarkIndex(dependency);
			final String command;
			if (checkMarkIndex == 0) {
				// set command to set dependency for all constraints
				command = "de.tud.cs.st.vespucci.diagram.setDependenciesCommand";
			} else {
				// set command to toggle dependency for all constraints
				command = "de.tud.cs.st.vespucci.diagram.toggleDependenciesCommand";
			}

			final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow(),
					"de.tud.cs.st.vespucci.diagram.menuItems.SetDependencyContribution_" + dependency, command,
					CommandContributionItem.STYLE_CHECK);
			contributionParameter.label = dependency;
			contributionParameter.icon = checkmark[checkMarkIndex];

			entries[i] = new CommandContributionItem(contributionParameter);
		}

		return entries;
	}
}
