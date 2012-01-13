/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl;

import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Dummy;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Expected;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalIncoming;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalOutgoing;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Incoming;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Factory;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Violation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Vespucci_model_2011_06_01PackageImpl extends EPackageImpl implements Vespucci_model_2011_06_01Package {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shapesDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shapeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dummyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ensembleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notAllowedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outgoingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass incomingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inAndOutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expectedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalOutgoingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalIncomingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass violationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Vespucci_model_2011_06_01PackageImpl() {
		super(eNS_URI, Vespucci_model_2011_06_01Factory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link Vespucci_model_2011_06_01Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Vespucci_model_2011_06_01Package init() {
		if (isInited) return (Vespucci_model_2011_06_01Package)EPackage.Registry.INSTANCE.getEPackage(Vespucci_model_2011_06_01Package.eNS_URI);

		// Obtain or create and register package
		Vespucci_model_2011_06_01PackageImpl theVespucci_model_2011_06_01Package = (Vespucci_model_2011_06_01PackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Vespucci_model_2011_06_01PackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Vespucci_model_2011_06_01PackageImpl());

		isInited = true;

		// Create package meta-data objects
		theVespucci_model_2011_06_01Package.createPackageContents();

		// Initialize created meta-data
		theVespucci_model_2011_06_01Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVespucci_model_2011_06_01Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Vespucci_model_2011_06_01Package.eNS_URI, theVespucci_model_2011_06_01Package);
		return theVespucci_model_2011_06_01Package;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShapesDiagram() {
		return shapesDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getShapesDiagram_Shapes() {
		return (EReference)shapesDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShape() {
		return shapeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getShape_SourceConnections() {
		return (EReference)shapeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getShape_TargetConnections() {
		return (EReference)shapeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShape_Name() {
		return (EAttribute)shapeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShape_Description() {
		return (EAttribute)shapeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShape_Query() {
		return (EAttribute)shapeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDummy() {
		return dummyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnsemble() {
		return ensembleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnsemble_Shapes() {
		return (EReference)ensembleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnection() {
		return connectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnection_Source() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnection_Target() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnection_Name() {
		return (EAttribute)connectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnection_Temp() {
		return (EAttribute)connectionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnection_OriginalSource() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnection_OriginalTarget() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNotAllowed() {
		return notAllowedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOutgoing() {
		return outgoingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncoming() {
		return incomingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInAndOut() {
		return inAndOutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpected() {
		return expectedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalOutgoing() {
		return globalOutgoingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalIncoming() {
		return globalIncomingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getViolation() {
		return violationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_model_2011_06_01Factory getVespucci_model_2011_06_01Factory() {
		return (Vespucci_model_2011_06_01Factory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		shapesDiagramEClass = createEClass(SHAPES_DIAGRAM);
		createEReference(shapesDiagramEClass, SHAPES_DIAGRAM__SHAPES);

		shapeEClass = createEClass(SHAPE);
		createEReference(shapeEClass, SHAPE__SOURCE_CONNECTIONS);
		createEReference(shapeEClass, SHAPE__TARGET_CONNECTIONS);
		createEAttribute(shapeEClass, SHAPE__NAME);
		createEAttribute(shapeEClass, SHAPE__DESCRIPTION);
		createEAttribute(shapeEClass, SHAPE__QUERY);

		dummyEClass = createEClass(DUMMY);

		ensembleEClass = createEClass(ENSEMBLE);
		createEReference(ensembleEClass, ENSEMBLE__SHAPES);

		connectionEClass = createEClass(CONNECTION);
		createEReference(connectionEClass, CONNECTION__SOURCE);
		createEReference(connectionEClass, CONNECTION__TARGET);
		createEAttribute(connectionEClass, CONNECTION__NAME);
		createEAttribute(connectionEClass, CONNECTION__TEMP);
		createEReference(connectionEClass, CONNECTION__ORIGINAL_SOURCE);
		createEReference(connectionEClass, CONNECTION__ORIGINAL_TARGET);

		notAllowedEClass = createEClass(NOT_ALLOWED);

		outgoingEClass = createEClass(OUTGOING);

		incomingEClass = createEClass(INCOMING);

		inAndOutEClass = createEClass(IN_AND_OUT);

		expectedEClass = createEClass(EXPECTED);

		globalOutgoingEClass = createEClass(GLOBAL_OUTGOING);

		globalIncomingEClass = createEClass(GLOBAL_INCOMING);

		violationEClass = createEClass(VIOLATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dummyEClass.getESuperTypes().add(this.getShape());
		ensembleEClass.getESuperTypes().add(this.getShape());
		notAllowedEClass.getESuperTypes().add(this.getConnection());
		outgoingEClass.getESuperTypes().add(this.getConnection());
		incomingEClass.getESuperTypes().add(this.getConnection());
		inAndOutEClass.getESuperTypes().add(this.getConnection());
		expectedEClass.getESuperTypes().add(this.getConnection());
		globalOutgoingEClass.getESuperTypes().add(this.getConnection());
		globalIncomingEClass.getESuperTypes().add(this.getConnection());
		violationEClass.getESuperTypes().add(this.getConnection());

		// Initialize classes and features; add operations and parameters
		initEClass(shapesDiagramEClass, ShapesDiagram.class, "ShapesDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShapesDiagram_Shapes(), this.getShape(), null, "shapes", null, 0, -1, ShapesDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(shapeEClass, Shape.class, "Shape", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShape_SourceConnections(), this.getConnection(), null, "sourceConnections", null, 0, -1, Shape.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getShape_TargetConnections(), this.getConnection(), null, "targetConnections", null, 0, -1, Shape.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShape_Name(), ecorePackage.getEString(), "name", null, 0, 1, Shape.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShape_Description(), ecorePackage.getEString(), "description", "<description>", 0, 1, Shape.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShape_Query(), ecorePackage.getEString(), "query", "empty", 0, 1, Shape.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dummyEClass, Dummy.class, "Dummy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ensembleEClass, Ensemble.class, "Ensemble", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnsemble_Shapes(), this.getShape(), null, "shapes", null, 0, -1, Ensemble.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionEClass, Connection.class, "Connection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnection_Source(), this.getShape(), null, "source", null, 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_Target(), this.getShape(), null, "target", null, 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnection_Name(), ecorePackage.getEString(), "name", "all", 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnection_Temp(), ecorePackage.getEBoolean(), "temp", "false", 0, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_OriginalSource(), this.getShape(), null, "originalSource", null, 0, -1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_OriginalTarget(), this.getShape(), null, "originalTarget", null, 0, -1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notAllowedEClass, NotAllowed.class, "NotAllowed", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(outgoingEClass, Outgoing.class, "Outgoing", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(incomingEClass, Incoming.class, "Incoming", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(inAndOutEClass, InAndOut.class, "InAndOut", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(expectedEClass, Expected.class, "Expected", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(globalOutgoingEClass, GlobalOutgoing.class, "GlobalOutgoing", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(globalIncomingEClass, GlobalIncoming.class, "GlobalIncoming", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(violationEClass, Violation.class, "Violation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //Vespucci_model_2011_06_01PackageImpl
