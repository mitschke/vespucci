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
	private VespucciOCLFactory() {
	}

	/**
	 * @generated
	 */
	public static de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression getExpression(
			String body, EClassifier context, Map environment) {
		return new Expression(body, context, environment);
	}

	/**
	 * @generated
	 */
	public static de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression getExpression(
			String body, EClassifier context) {
		return getExpression(body, context, Collections.EMPTY_MAP);
	}

	/**
	 * @generated
	 */
	private static class Expression
			extends
			de.tud.cs.st.vespucci.vespucci_model.diagram.expressions.VespucciAbstractExpression {

		/**
		 * @generated
		 */
		private WeakReference queryRef;

		/**
		 * @generated
		 */
		private final org.eclipse.ocl.ecore.OCL oclInstance;

		/**
		 * @generated
		 */
		public Expression(String body, EClassifier context, Map environment) {
			super(body, context);
			oclInstance = org.eclipse.ocl.ecore.OCL.newInstance();
			initCustomEnv(oclInstance.getEnvironment(), environment);
		}

		/**
		 * @generated
		 */
		protected Query getQuery() {
			Query oclQuery = null;
			if (this.queryRef != null) {
				oclQuery = (Query) this.queryRef.get();
			}
			if (oclQuery == null) {
				OCLHelper oclHelper = oclInstance.createOCLHelper();
				oclHelper.setContext(context());
				try {
					OCLExpression oclExpression = oclHelper.createQuery(body());
					oclQuery = oclInstance.createQuery(oclExpression);
					this.queryRef = new WeakReference(oclQuery);
					setStatus(IStatus.OK, null, null);
				} catch (ParserException e) {
					setStatus(IStatus.ERROR, e.getMessage(), e);
				}
			}
			return oclQuery;
		}

		/**
		 * @generated
		 */
		@SuppressWarnings("rawtypes")
		protected Object doEvaluate(Object context, Map env) {
			Query oclQuery = getQuery();
			if (oclQuery == null) {
				return null;
			}
			EvaluationEnvironment evalEnv = oclQuery.getEvaluationEnvironment();
			// init environment
			for (Iterator it = env.entrySet().iterator(); it.hasNext();) {
				Map.Entry nextEntry = (Map.Entry) it.next();
				evalEnv.replace((String) nextEntry.getKey(), nextEntry
						.getValue());
			}
			try {
				initExtentMap(context);
				Object result = oclQuery.evaluate(context);
				return (result != oclInstance.getEnvironment()
						.getOCLStandardLibrary().getOclInvalid()) ? result
						: null;
			} finally {
				evalEnv.clear();
				oclQuery.getExtentMap().clear();
			}
		}

		/**
		 * @generated
		 */
		private void initExtentMap(Object context) {
			if (!getStatus().isOK() || context == null) {
				return;
			}
			final Query queryToInit = getQuery();
			final Object extentContext = context;
			queryToInit.getExtentMap().clear();
			if (queryToInit.queryText() != null
					&& queryToInit.queryText().indexOf(
							PredefinedType.ALL_INSTANCES_NAME) >= 0) {
				AbstractVisitor visitior = new AbstractVisitor() {

					private boolean usesAllInstances = false;

					public Object visitOperationCallExp(OperationCallExp oc) {
						if (!usesAllInstances) {
							usesAllInstances = PredefinedType.ALL_INSTANCES == oc
									.getOperationCode();
							if (usesAllInstances) {
								queryToInit
										.getExtentMap()
										.putAll(
												oclInstance
														.getEvaluationEnvironment()
														.createExtentMap(
																extentContext));
							}
						}
						return super.visitOperationCallExp(oc);
					}
				};
				queryToInit.getExpression().accept(visitior);
			}
		}

		/**
		 * @generated
		 */
		private static void initCustomEnv(Environment ecoreEnv, Map environment) {
			for (Iterator it = environment.keySet().iterator(); it.hasNext();) {
				String varName = (String) it.next();
				EClassifier varType = (EClassifier) environment.get(varName);
				ecoreEnv.addElement(varName, createVar(ecoreEnv, varName,
						varType), false);
			}
		}

		/**
		 * @generated
		 */
		private static Variable createVar(Environment ecoreEnv, String name,
				EClassifier type) {
			Variable var = EcoreFactory.eINSTANCE.createVariable();
			var.setName(name);
			var.setType(ecoreEnv.getUMLReflection().getOCLType(type));
			return var;
		}
	}
}
