/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package
 * @generated
 */
public interface Vespucci_model_2011_06_01Factory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Vespucci_model_2011_06_01Factory eINSTANCE = de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01FactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Shapes Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shapes Diagram</em>'.
	 * @generated
	 */
	ShapesDiagram createShapesDiagram();

	/**
	 * Returns a new object of class '<em>Shape</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shape</em>'.
	 * @generated
	 */
	Shape createShape();

	/**
	 * Returns a new object of class '<em>Dummy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dummy</em>'.
	 * @generated
	 */
	Dummy createDummy();

	/**
	 * Returns a new object of class '<em>Ensemble</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ensemble</em>'.
	 * @generated
	 */
	Ensemble createEnsemble();

	/**
	 * Returns a new object of class '<em>Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection</em>'.
	 * @generated
	 */
	Connection createConnection();

	/**
	 * Returns a new object of class '<em>Not Allowed</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Not Allowed</em>'.
	 * @generated
	 */
	NotAllowed createNotAllowed();

	/**
	 * Returns a new object of class '<em>Outgoing</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Outgoing</em>'.
	 * @generated
	 */
	Outgoing createOutgoing();

	/**
	 * Returns a new object of class '<em>Incoming</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Incoming</em>'.
	 * @generated
	 */
	Incoming createIncoming();

	/**
	 * Returns a new object of class '<em>In And Out</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>In And Out</em>'.
	 * @generated
	 */
	InAndOut createInAndOut();

	/**
	 * Returns a new object of class '<em>Expected</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expected</em>'.
	 * @generated
	 */
	Expected createExpected();

	/**
	 * Returns a new object of class '<em>Global Outgoing</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Outgoing</em>'.
	 * @generated
	 */
	GlobalOutgoing createGlobalOutgoing();

	/**
	 * Returns a new object of class '<em>Global Incoming</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Incoming</em>'.
	 * @generated
	 */
	GlobalIncoming createGlobalIncoming();

	/**
	 * Returns a new object of class '<em>Violation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Violation</em>'.
	 * @generated
	 */
	Violation createViolation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Vespucci_model_2011_06_01Package getVespucci_model_2011_06_01Package();

} //Vespucci_model_2011_06_01Factory
