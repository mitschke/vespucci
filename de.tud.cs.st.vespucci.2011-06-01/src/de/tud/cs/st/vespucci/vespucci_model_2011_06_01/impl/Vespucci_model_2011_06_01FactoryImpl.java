/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl;

import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Vespucci_model_2011_06_01FactoryImpl extends EFactoryImpl implements Vespucci_model_2011_06_01Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Vespucci_model_2011_06_01Factory init() {
		try {
			Vespucci_model_2011_06_01Factory theVespucci_model_2011_06_01Factory = (Vespucci_model_2011_06_01Factory)EPackage.Registry.INSTANCE.getEFactory("http://vespucci.editor/2011-06-01"); 
			if (theVespucci_model_2011_06_01Factory != null) {
				return theVespucci_model_2011_06_01Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Vespucci_model_2011_06_01FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_model_2011_06_01FactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case Vespucci_model_2011_06_01Package.SHAPES_DIAGRAM: return createShapesDiagram();
			case Vespucci_model_2011_06_01Package.SHAPE: return createShape();
			case Vespucci_model_2011_06_01Package.DUMMY: return createDummy();
			case Vespucci_model_2011_06_01Package.ENSEMBLE: return createEnsemble();
			case Vespucci_model_2011_06_01Package.CONNECTION: return createConnection();
			case Vespucci_model_2011_06_01Package.NOT_ALLOWED: return createNotAllowed();
			case Vespucci_model_2011_06_01Package.OUTGOING: return createOutgoing();
			case Vespucci_model_2011_06_01Package.INCOMING: return createIncoming();
			case Vespucci_model_2011_06_01Package.IN_AND_OUT: return createInAndOut();
			case Vespucci_model_2011_06_01Package.EXPECTED: return createExpected();
			case Vespucci_model_2011_06_01Package.GLOBAL_OUTGOING: return createGlobalOutgoing();
			case Vespucci_model_2011_06_01Package.GLOBAL_INCOMING: return createGlobalIncoming();
			case Vespucci_model_2011_06_01Package.VIOLATION: return createViolation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShapesDiagram createShapesDiagram() {
		ShapesDiagramImpl shapesDiagram = new ShapesDiagramImpl();
		return shapesDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape createShape() {
		ShapeImpl shape = new ShapeImpl();
		return shape;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dummy createDummy() {
		DummyImpl dummy = new DummyImpl();
		return dummy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ensemble createEnsemble() {
		EnsembleImpl ensemble = new EnsembleImpl();
		return ensemble;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connection createConnection() {
		ConnectionImpl connection = new ConnectionImpl();
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotAllowed createNotAllowed() {
		NotAllowedImpl notAllowed = new NotAllowedImpl();
		return notAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Outgoing createOutgoing() {
		OutgoingImpl outgoing = new OutgoingImpl();
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Incoming createIncoming() {
		IncomingImpl incoming = new IncomingImpl();
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InAndOut createInAndOut() {
		InAndOutImpl inAndOut = new InAndOutImpl();
		return inAndOut;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expected createExpected() {
		ExpectedImpl expected = new ExpectedImpl();
		return expected;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalOutgoing createGlobalOutgoing() {
		GlobalOutgoingImpl globalOutgoing = new GlobalOutgoingImpl();
		return globalOutgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalIncoming createGlobalIncoming() {
		GlobalIncomingImpl globalIncoming = new GlobalIncomingImpl();
		return globalIncoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Violation createViolation() {
		ViolationImpl violation = new ViolationImpl();
		return violation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_model_2011_06_01Package getVespucci_model_2011_06_01Package() {
		return (Vespucci_model_2011_06_01Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Vespucci_model_2011_06_01Package getPackage() {
		return Vespucci_model_2011_06_01Package.eINSTANCE;
	}

} //Vespucci_model_2011_06_01FactoryImpl
