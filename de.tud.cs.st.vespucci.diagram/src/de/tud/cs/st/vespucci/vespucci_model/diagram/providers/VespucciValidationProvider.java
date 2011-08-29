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
package de.tud.cs.st.vespucci.vespucci_model.diagram.providers;

import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.osgi.framework.FrameworkUtil;

import de.tud.cs.st.vespucci.io.KeywordReader;

/**
 * @generated
 */
public class VespucciValidationProvider {

	/**
	 * @generated
	 */
	private static boolean constraintsActive = false;

	/**
	 * @generated
	 */
	public static boolean shouldConstraintsBePrivate() {
		return false;
	}

	/**
	 * @generated
	 */
	public static void runWithConstraints(final TransactionalEditingDomain editingDomain, final Runnable operation) {
		final Runnable op = operation;
		final Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					constraintsActive = true;
					op.run();
				} finally {
					constraintsActive = false;
				}
			}
		};
		if (editingDomain != null) {
			try {
				editingDomain.runExclusive(task);
			} catch (final Exception e) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
						"Validation failed", e); //$NON-NLS-1$
			}
		} else {
			task.run();
		}
	}

	/**
	 * @generated
	 */
	static boolean isInDefaultEditorContext(final Object object) {
		if (shouldConstraintsBePrivate() && !constraintsActive) {
			return false;
		}
		if (object instanceof View) {
			return constraintsActive
					&& de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID
							.equals(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
									.getModelID((View) object));
		}
		return true;
	}

	/**
	 * @generated
	 */
	public static class DefaultCtx implements IClientSelector {

		/**
		 * @generated
		 */
		@Override
		public boolean selects(final Object object) {
			return isInDefaultEditorContext(object);
		}
	}

	/**
	 * @generated
	 */
	public static class Adapter4 extends AbstractModelConstraint {
		// TODO change the name Adapter4 to a more expressive name (CAUTION: Refactoring may affect code generator!)
		/**
		 * Java implementation for the constraint NonLeafEnsembleMustBeDerived in All constraints
		 * about Ensemble
		 * 
		 * @author Malte Viering
		 * @generated NOT
		 * @return Success-Status, if validation successful; Failure otherwise.
		 */
		@Override
		public IStatus validate(final IValidationContext ctx) {
			final de.tud.cs.st.vespucci.vespucci_model.Ensemble context = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) ctx
					.getTarget();
			if (context.getShapes().size() == 0) {
				return ctx.createSuccessStatus();
			}
			if (context.getQuery().equals("derived")) {
				return ctx.createSuccessStatus();
			}
			return ctx.createFailureStatus("Queries of non leaf ensemble must be derived.");
		}
	}

	/**
	 * @generated
	 */
	public static class Adapter5 extends AbstractModelConstraint {
		// TODO change the name Adapter5 to a more expressive name (CAUTION: Refactoring may affect code generator!)
		private static ResourceBundle pluginProperties = ResourceBundle.getBundle("plugin");

		/**
		 * Checks if given dependency kind for constrain is valid.
		 * 
		 * @author Alexander Weitzmann
		 * @generated NOT
		 * @return Success-Status, if validation successful; Failure otherwise.
		 */
		@Override
		public IStatus validate(final IValidationContext ctx) {
			final String context = (String) ctx.getTarget().eGet(
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getConnection_Name());
			if (context == null) {
				return Status.OK_STATUS;
			}

			/**
			 * Valid names for dependencies read from the resource-file.
			 */
			final String[] validDependencies = KeywordReader.readAndParseResourceFile(FrameworkUtil.getBundle(Adapter5.class)
					.getSymbolicName(), pluginProperties.getString("validDependenciesFile"));

			final String[] dependencies = context.split(", ");
			boolean valid = false;

			// check all dependencies
			for (final String dep : dependencies) {
				// probe for all valid names
				valid = false;
				for (final String validDep : validDependencies) {
					if (validDep.equals(dep)) {
						valid = true;
						break;
					}
				}
				if (!valid) {
					return ctx.createFailureStatus(String.format("Depdendency kind %s is invalid", context));
				}
			}
			return ctx.createSuccessStatus();
		}
	}

	/**
	 * @generated
	 */
	static String formatElement(final EObject object) {
		return EMFCoreUtil.getQualifiedName(object, true);
	}

}
