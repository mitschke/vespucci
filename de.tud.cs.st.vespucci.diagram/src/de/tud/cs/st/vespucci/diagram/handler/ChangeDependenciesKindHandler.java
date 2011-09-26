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

package de.tud.cs.st.vespucci.diagram.handler;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * Abstract Handler for the "Toggle Dependency"-commands.<br>
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 */
public abstract class ChangeDependenciesKindHandler extends AbstractHandler {

	/**
	 * Creates a string containing all strings from given array. The strings will be separated by
	 * ", ".
	 * 
	 * @param strArr
	 *            Array of strings, whose elements shall be joined.
	 * @return A string with all elements of given String-array, separated with ", ".
	 */
	protected static String joinString(final String[] strArr) {
		if (strArr.length == 0) {
			return "";
		}

		final StringBuffer sb = new StringBuffer(strArr.length);
		sb.append(strArr[0]);
		for (int i = 1; i < strArr.length; ++i) {
			sb.append(", ");
			sb.append(strArr[i]);
		}

		return sb.toString();
	}

	/**
	 * Creates a new string-array, with same strings as given original, but the "transform-string".
	 * 
	 * @param original
	 *            The original array to be copied.
	 * @param transformString
	 *            String indicating entry to be transformed.
	 * @return Transformed string-array.
	 */
	abstract String[] transformedCopy(final String[] original, final String transformString);

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection currentSelection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		final Object[] currentSelectionArr = currentSelection.toArray();

		// This array will contain the casted selection-objects.
		final ConnectionEditPart[] selectedConnections = new ConnectionEditPart[currentSelection.size()];

		for (int i = 0; i < currentSelection.size(); ++i) {
			if (currentSelectionArr[i] instanceof ConnectionEditPart) {
				selectedConnections[i] = (ConnectionEditPart) currentSelectionArr[i];
			} else {
				// If this exception is reached, then there should be something wrong with the
				// visibleWhen entry of the popUp-menu.
				return new ExecutionException("Selection is not a connection!");
			}
		}

		final Event trigger = (Event) event.getTrigger();
		
		// Dependency kind to be toggled (added or removed).
		final String dependencyKind = ((MenuItem) trigger.widget).getText();

		for (final ConnectionEditPart connection : selectedConnections) {
			// Editing domain. Must be used to keep consistency.
			final TransactionalEditingDomain editDomain = connection.getEditingDomain();
			// EObject representing the connection.
			final EObject semanticConnection = connection.resolveSemanticElement();

			// Feature to be set or unset. Here: The dependencies of the constraint.
			final EStructuralFeature toggleFeature = semanticConnection.eClass().getEStructuralFeature(
					Vespucci_modelPackage.CONNECTION__NAME);

			// get current dependencies
			String[] currentDependencies = ((String) semanticConnection.eGet(toggleFeature, true)).split(", ");

			// only one empty string should be empty array
			if (Arrays.equals(currentDependencies, new String[] { "" })) {
				currentDependencies = new String[0];
			}

			// toggle dependency
			final String[] newDependencies = transformedCopy(currentDependencies, dependencyKind);

			// Command that will update the connection
			final SetCommand setCommand = new SetCommand(editDomain, semanticConnection, toggleFeature,
					joinString(newDependencies));

			editDomain.getCommandStack().execute(setCommand);
		}

		return null;
	}

}
