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
package de.tud.cs.st.vespucci.vespucci_model.util;

import de.tud.cs.st.vespucci.vespucci_model.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage
 * @generated
 */
public class Vespucci_modelValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Vespucci_modelValidator INSTANCE = new Vespucci_modelValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "de.tud.cs.st.vespucci.vespucci_model";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_modelValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return Vespucci_modelPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case Vespucci_modelPackage.SHAPES_DIAGRAM:
				return validateShapesDiagram((ShapesDiagram)value, diagnostics, context);
			case Vespucci_modelPackage.SHAPE:
				return validateShape((Shape)value, diagnostics, context);
			case Vespucci_modelPackage.EMPTY:
				return validateEmpty((Empty)value, diagnostics, context);
			case Vespucci_modelPackage.ENSEMBLE:
				return validateEnsemble((Ensemble)value, diagnostics, context);
			case Vespucci_modelPackage.CONNECTION:
				return validateConnection((Connection)value, diagnostics, context);
			case Vespucci_modelPackage.NOT_ALLOWED:
				return validateNotAllowed((NotAllowed)value, diagnostics, context);
			case Vespucci_modelPackage.OUTGOING:
				return validateOutgoing((Outgoing)value, diagnostics, context);
			case Vespucci_modelPackage.INCOMING:
				return validateIncoming((Incoming)value, diagnostics, context);
			case Vespucci_modelPackage.IN_AND_OUT:
				return validateInAndOut((InAndOut)value, diagnostics, context);
			case Vespucci_modelPackage.EXPECTED:
				return validateExpected((Expected)value, diagnostics, context);
			case Vespucci_modelPackage.GLOBAL_OUTGOING:
				return validateGlobalOutgoing((GlobalOutgoing)value, diagnostics, context);
			case Vespucci_modelPackage.GLOBAL_INCOMING:
				return validateGlobalIncoming((GlobalIncoming)value, diagnostics, context);
			case Vespucci_modelPackage.VIOLATION:
				return validateViolation((Violation)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateShapesDiagram(ShapesDiagram shapesDiagram, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(shapesDiagram, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateShape(Shape shape, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(shape, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(shape, diagnostics, context);
		if (result || diagnostics != null) result &= validateShape_ViolationOnly(shape, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ViolationOnly constraint of '<em>Shape</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SHAPE__VIOLATION_ONLY__EEXPRESSION = "self.targetConnections->forAll(x : Connection, y : Connection | (x <> y and x.source = y.source and x.target = y.target and x.oclIsTypeOf(Violation)) implies y.oclIsTypeOf(Violation))";

	/**
	 * Validates the ViolationOnly constraint of '<em>Shape</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateShape_ViolationOnly(Shape shape, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(Vespucci_modelPackage.Literals.SHAPE,
				 shape,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ViolationOnly",
				 SHAPE__VIOLATION_ONLY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEmpty(Empty empty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(empty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(empty, diagnostics, context);
		if (result || diagnostics != null) result &= validateShape_ViolationOnly(empty, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnsemble(Ensemble ensemble, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ensemble, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(ensemble, diagnostics, context);
		if (result || diagnostics != null) result &= validateShape_ViolationOnly(ensemble, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnection(Connection connection, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(connection, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNotAllowed(NotAllowed notAllowed, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(notAllowed, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOutgoing(Outgoing outgoing, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(outgoing, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIncoming(Incoming incoming, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(incoming, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInAndOut(InAndOut inAndOut, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(inAndOut, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExpected(Expected expected, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(expected, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGlobalOutgoing(GlobalOutgoing globalOutgoing, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(globalOutgoing, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGlobalIncoming(GlobalIncoming globalIncoming, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(globalIncoming, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateViolation(Violation violation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(violation, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //Vespucci_modelValidator
