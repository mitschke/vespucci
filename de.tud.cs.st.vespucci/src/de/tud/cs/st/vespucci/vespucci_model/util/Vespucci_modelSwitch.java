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
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model.util;

import de.tud.cs.st.vespucci.vespucci_model.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Empty;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming;
import de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.Violation;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage
 * @generated
 */
public class Vespucci_modelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Vespucci_modelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_modelSwitch() {
		if (modelPackage == null) {
			modelPackage = Vespucci_modelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case Vespucci_modelPackage.ARCHITECTURE_MODEL: {
				ArchitectureModel architectureModel = (ArchitectureModel)theEObject;
				T result = caseArchitectureModel(architectureModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE: {
				AbstractEnsemble abstractEnsemble = (AbstractEnsemble)theEObject;
				T result = caseAbstractEnsemble(abstractEnsemble);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.EMPTY: {
				Empty empty = (Empty)theEObject;
				T result = caseEmpty(empty);
				if (result == null) result = caseAbstractEnsemble(empty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.ENSEMBLE: {
				Ensemble ensemble = (Ensemble)theEObject;
				T result = caseEnsemble(ensemble);
				if (result == null) result = caseAbstractEnsemble(ensemble);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.CONNECTION: {
				Connection connection = (Connection)theEObject;
				T result = caseConnection(connection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.NOT_ALLOWED: {
				NotAllowed notAllowed = (NotAllowed)theEObject;
				T result = caseNotAllowed(notAllowed);
				if (result == null) result = caseConnection(notAllowed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.OUTGOING: {
				Outgoing outgoing = (Outgoing)theEObject;
				T result = caseOutgoing(outgoing);
				if (result == null) result = caseConnection(outgoing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.INCOMING: {
				Incoming incoming = (Incoming)theEObject;
				T result = caseIncoming(incoming);
				if (result == null) result = caseConnection(incoming);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.IN_AND_OUT: {
				InAndOut inAndOut = (InAndOut)theEObject;
				T result = caseInAndOut(inAndOut);
				if (result == null) result = caseConnection(inAndOut);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.EXPECTED: {
				Expected expected = (Expected)theEObject;
				T result = caseExpected(expected);
				if (result == null) result = caseConnection(expected);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.GLOBAL_OUTGOING: {
				GlobalOutgoing globalOutgoing = (GlobalOutgoing)theEObject;
				T result = caseGlobalOutgoing(globalOutgoing);
				if (result == null) result = caseConnection(globalOutgoing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.GLOBAL_INCOMING: {
				GlobalIncoming globalIncoming = (GlobalIncoming)theEObject;
				T result = caseGlobalIncoming(globalIncoming);
				if (result == null) result = caseConnection(globalIncoming);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_modelPackage.VIOLATION: {
				Violation violation = (Violation)theEObject;
				T result = caseViolation(violation);
				if (result == null) result = caseConnection(violation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Architecture Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Architecture Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArchitectureModel(ArchitectureModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Ensemble</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Ensemble</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractEnsemble(AbstractEnsemble object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Empty</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Empty</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmpty(Empty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ensemble</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ensemble</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnsemble(Ensemble object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnection(Connection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Not Allowed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Not Allowed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotAllowed(NotAllowed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Outgoing</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Outgoing</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOutgoing(Outgoing object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Incoming</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Incoming</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncoming(Incoming object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>In And Out</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>In And Out</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInAndOut(InAndOut object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expected</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expected</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpected(Expected object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Outgoing</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Outgoing</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalOutgoing(GlobalOutgoing object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Incoming</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Incoming</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalIncoming(GlobalIncoming object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Violation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Violation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViolation(Violation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //Vespucci_modelSwitch
