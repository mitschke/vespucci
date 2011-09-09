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
package de.tud.cs.st.vespucci.vespucci_model.diagram.expressions;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.OCL.Helper;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.helper.OCLHelper;
import org.eclipse.ocl.options.ParsingOptions;
import org.eclipse.ocl.utilities.AbstractVisitor;
import org.eclipse.ocl.utilities.PredefinedType;

/**
 * @generated
 */
public class VespucciOCLFactory {

	/**
	 * @generated
	 */
	private final de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression[] expressions;

	/**
	 * @generated
	 */
	protected VespucciOCLFactory() {
		this.expressions = new de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression[18];
	}

	/**
	 * @generated
	 */
	public static de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression getExpression(int index,
			EClassifier context, Map<String, EClassifier> environment) {
		VespucciOCLFactory cached = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance()
				.getVespucciOCLFactory();
		if (cached == null) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().setVespucciOCLFactory(
					cached = new VespucciOCLFactory());
		}
		if (index < 0 || index >= cached.expressions.length) {
			throw new IllegalArgumentException();
		}
		if (cached.expressions[index] == null) {
			final String[] exprBodies = new String[] { "self <> oppositeEnd", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"(self <> oppositeEnd) and (not self.oclIsTypeOf(Dummy))", //$NON-NLS-1$
					"not self.oclIsTypeOf(Dummy)", //$NON-NLS-1$
					"name.size()>0 and not name.oclIsUndefined()", //$NON-NLS-1$
					"Ensemble.allInstances()->forAll(e| e <> self implies e.name <> self.name)", //$NON-NLS-1$
					"query.size()>0 and not query.oclIsUndefined()", //$NON-NLS-1$
			};
			cached.expressions[index] = getExpression(exprBodies[index], context,
					environment == null ? Collections.<String, EClassifier> emptyMap() : environment);
		}
		return cached.expressions[index];
	}

	/**
	 * @generated
	 */
	public static de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression getExpression(String body,
			EClassifier context, Map<String, EClassifier> environment) {
		return new Expression(body, context, environment);
	}

	/**
	 * @generated
	 */
	public static de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression getExpression(String body,
			EClassifier context) {
		return getExpression(body, context, Collections.<String, EClassifier> emptyMap());
	}

	/**
	 * @generated
	 */
	private static class Expression extends de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression {

		/**
		 * @generated
		 */
		private final org.eclipse.ocl.ecore.OCL oclInstance;

		/**
		 * @generated
		 */
		private org.eclipse.ocl.ecore.OCLExpression oclExpression;

		/**
		 * @generated
		 */
		public Expression(String body, EClassifier context, Map<String, EClassifier> environment) {
			super(body, context);
			oclInstance = org.eclipse.ocl.ecore.OCL.newInstance();
			initCustomEnv(oclInstance.getEnvironment(), environment);
			Helper oclHelper = oclInstance.createOCLHelper();
			oclHelper.setContext(context());
			try {
				oclExpression = oclHelper.createQuery(body());
				setStatus(IStatus.OK, null, null);
			} catch (ParserException e) {
				setStatus(IStatus.ERROR, e.getMessage(), e);
			}
		}

		/**
		 * @generated
		 */
		@SuppressWarnings("rawtypes")
		protected Object doEvaluate(Object context, Map env) {
			if (oclExpression == null) {
				return null;
			}
			// on the first call, both evalEnvironment and extentMap are clear, for later we have finally, below.
			EvaluationEnvironment<?, ?, ?, ?, ?> evalEnv = oclInstance.getEvaluationEnvironment();
			// initialize environment
			for (Object nextKey : env.keySet()) {
				evalEnv.replace((String) nextKey, env.get(nextKey));
			}
			try {
				Object result = oclInstance.evaluate(context, oclExpression);
				return oclInstance.isInvalid(result) ? null : result;
			} finally {
				evalEnv.clear();
				oclInstance.setExtentMap(null); // clear allInstances cache, and get the oclInstance ready for the next call
			}
		}

		/**
		 * @generated
		 */
		private static void initCustomEnv(Environment<?, EClassifier, ?, ?, ?, EParameter, ?, ?, ?, ?, ?, ?> ecoreEnv,
				Map<String, EClassifier> environment) {
			// Use EObject as implicit root class for any object, to allow eContainer() and other EObject operations from OCL expressions
			ParsingOptions.setOption(ecoreEnv, ParsingOptions.implicitRootClass(ecoreEnv), EcorePackage.eINSTANCE.getEObject());
			for (String varName : environment.keySet()) {
				EClassifier varType = environment.get(varName);
				ecoreEnv.addElement(varName, createVar(ecoreEnv, varName, varType), false);
			}
		}

		/**
		 * @generated
		 */
		private static org.eclipse.ocl.ecore.Variable createVar(
				Environment<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> ecoreEnv, String name, EClassifier type) {
			org.eclipse.ocl.ecore.Variable var = EcoreFactory.eINSTANCE.createVariable();
			var.setName(name);
			var.setType(ecoreEnv.getUMLReflection().getOCLType(type));
			return var;
		}
	}
}
