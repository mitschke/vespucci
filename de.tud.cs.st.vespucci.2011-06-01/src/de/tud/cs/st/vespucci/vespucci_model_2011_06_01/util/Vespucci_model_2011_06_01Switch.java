/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01.util;

import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package
 * @generated
 */
public class Vespucci_model_2011_06_01Switch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Vespucci_model_2011_06_01Package modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_model_2011_06_01Switch() {
		if (modelPackage == null) {
			modelPackage = Vespucci_model_2011_06_01Package.eINSTANCE;
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
			case Vespucci_model_2011_06_01Package.SHAPES_DIAGRAM: {
				ShapesDiagram shapesDiagram = (ShapesDiagram)theEObject;
				T result = caseShapesDiagram(shapesDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.SHAPE: {
				Shape shape = (Shape)theEObject;
				T result = caseShape(shape);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.DUMMY: {
				Dummy dummy = (Dummy)theEObject;
				T result = caseDummy(dummy);
				if (result == null) result = caseShape(dummy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.ENSEMBLE: {
				Ensemble ensemble = (Ensemble)theEObject;
				T result = caseEnsemble(ensemble);
				if (result == null) result = caseShape(ensemble);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.CONNECTION: {
				Connection connection = (Connection)theEObject;
				T result = caseConnection(connection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.NOT_ALLOWED: {
				NotAllowed notAllowed = (NotAllowed)theEObject;
				T result = caseNotAllowed(notAllowed);
				if (result == null) result = caseConnection(notAllowed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.OUTGOING: {
				Outgoing outgoing = (Outgoing)theEObject;
				T result = caseOutgoing(outgoing);
				if (result == null) result = caseConnection(outgoing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.INCOMING: {
				Incoming incoming = (Incoming)theEObject;
				T result = caseIncoming(incoming);
				if (result == null) result = caseConnection(incoming);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.IN_AND_OUT: {
				InAndOut inAndOut = (InAndOut)theEObject;
				T result = caseInAndOut(inAndOut);
				if (result == null) result = caseConnection(inAndOut);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.EXPECTED: {
				Expected expected = (Expected)theEObject;
				T result = caseExpected(expected);
				if (result == null) result = caseConnection(expected);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.GLOBAL_OUTGOING: {
				GlobalOutgoing globalOutgoing = (GlobalOutgoing)theEObject;
				T result = caseGlobalOutgoing(globalOutgoing);
				if (result == null) result = caseConnection(globalOutgoing);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.GLOBAL_INCOMING: {
				GlobalIncoming globalIncoming = (GlobalIncoming)theEObject;
				T result = caseGlobalIncoming(globalIncoming);
				if (result == null) result = caseConnection(globalIncoming);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case Vespucci_model_2011_06_01Package.VIOLATION: {
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
	 * Returns the result of interpreting the object as an instance of '<em>Shapes Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shapes Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShapesDiagram(ShapesDiagram object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shape</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shape</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShape(Shape object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dummy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dummy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDummy(Dummy object) {
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

} //Vespucci_model_2011_06_01Switch
