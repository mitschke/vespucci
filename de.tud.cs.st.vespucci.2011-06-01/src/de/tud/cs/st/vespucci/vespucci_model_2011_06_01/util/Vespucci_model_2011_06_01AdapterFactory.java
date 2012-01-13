/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01.util;

import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package
 * @generated
 */
public class Vespucci_model_2011_06_01AdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Vespucci_model_2011_06_01Package modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_model_2011_06_01AdapterFactory() {
		if (modelPackage == null) {
			modelPackage = Vespucci_model_2011_06_01Package.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Vespucci_model_2011_06_01Switch<Adapter> modelSwitch =
		new Vespucci_model_2011_06_01Switch<Adapter>() {
			@Override
			public Adapter caseShapesDiagram(ShapesDiagram object) {
				return createShapesDiagramAdapter();
			}
			@Override
			public Adapter caseShape(Shape object) {
				return createShapeAdapter();
			}
			@Override
			public Adapter caseDummy(Dummy object) {
				return createDummyAdapter();
			}
			@Override
			public Adapter caseEnsemble(Ensemble object) {
				return createEnsembleAdapter();
			}
			@Override
			public Adapter caseConnection(Connection object) {
				return createConnectionAdapter();
			}
			@Override
			public Adapter caseNotAllowed(NotAllowed object) {
				return createNotAllowedAdapter();
			}
			@Override
			public Adapter caseOutgoing(Outgoing object) {
				return createOutgoingAdapter();
			}
			@Override
			public Adapter caseIncoming(Incoming object) {
				return createIncomingAdapter();
			}
			@Override
			public Adapter caseInAndOut(InAndOut object) {
				return createInAndOutAdapter();
			}
			@Override
			public Adapter caseExpected(Expected object) {
				return createExpectedAdapter();
			}
			@Override
			public Adapter caseGlobalOutgoing(GlobalOutgoing object) {
				return createGlobalOutgoingAdapter();
			}
			@Override
			public Adapter caseGlobalIncoming(GlobalIncoming object) {
				return createGlobalIncomingAdapter();
			}
			@Override
			public Adapter caseViolation(Violation object) {
				return createViolationAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram <em>Shapes Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram
	 * @generated
	 */
	public Adapter createShapesDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape
	 * @generated
	 */
	public Adapter createShapeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Dummy <em>Dummy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Dummy
	 * @generated
	 */
	public Adapter createDummyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble <em>Ensemble</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble
	 * @generated
	 */
	public Adapter createEnsembleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection
	 * @generated
	 */
	public Adapter createConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.NotAllowed <em>Not Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.NotAllowed
	 * @generated
	 */
	public Adapter createNotAllowedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Outgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Outgoing
	 * @generated
	 */
	public Adapter createOutgoingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Incoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Incoming
	 * @generated
	 */
	public Adapter createIncomingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.InAndOut <em>In And Out</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.InAndOut
	 * @generated
	 */
	public Adapter createInAndOutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Expected <em>Expected</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Expected
	 * @generated
	 */
	public Adapter createExpectedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalOutgoing <em>Global Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalOutgoing
	 * @generated
	 */
	public Adapter createGlobalOutgoingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalIncoming <em>Global Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalIncoming
	 * @generated
	 */
	public Adapter createGlobalIncomingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Violation <em>Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Violation
	 * @generated
	 */
	public Adapter createViolationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //Vespucci_model_2011_06_01AdapterFactory
