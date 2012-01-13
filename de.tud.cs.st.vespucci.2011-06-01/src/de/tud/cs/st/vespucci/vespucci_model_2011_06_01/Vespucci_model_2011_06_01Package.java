/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Factory
 * @model kind="package"
 * @generated
 */
public interface Vespucci_model_2011_06_01Package extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "vespucci_model_2011_06_01";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://vespucci.editor/2011-06-01";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Vespucci_model_2011_06_01Package eINSTANCE = de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapesDiagramImpl <em>Shapes Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapesDiagramImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getShapesDiagram()
	 * @generated
	 */
	int SHAPES_DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Shapes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPES_DIAGRAM__SHAPES = 0;

	/**
	 * The number of structural features of the '<em>Shapes Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPES_DIAGRAM_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapeImpl <em>Shape</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapeImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getShape()
	 * @generated
	 */
	int SHAPE = 1;

	/**
	 * The feature id for the '<em><b>Source Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE__SOURCE_CONNECTIONS = 0;

	/**
	 * The feature id for the '<em><b>Target Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE__TARGET_CONNECTIONS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE__QUERY = 4;

	/**
	 * The number of structural features of the '<em>Shape</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.DummyImpl <em>Dummy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.DummyImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getDummy()
	 * @generated
	 */
	int DUMMY = 2;

	/**
	 * The feature id for the '<em><b>Source Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY__SOURCE_CONNECTIONS = SHAPE__SOURCE_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Target Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY__TARGET_CONNECTIONS = SHAPE__TARGET_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY__NAME = SHAPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY__DESCRIPTION = SHAPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY__QUERY = SHAPE__QUERY;

	/**
	 * The number of structural features of the '<em>Dummy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DUMMY_FEATURE_COUNT = SHAPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.EnsembleImpl <em>Ensemble</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.EnsembleImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getEnsemble()
	 * @generated
	 */
	int ENSEMBLE = 3;

	/**
	 * The feature id for the '<em><b>Source Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__SOURCE_CONNECTIONS = SHAPE__SOURCE_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Target Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__TARGET_CONNECTIONS = SHAPE__TARGET_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__NAME = SHAPE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__DESCRIPTION = SHAPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__QUERY = SHAPE__QUERY;

	/**
	 * The feature id for the '<em><b>Shapes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE__SHAPES = SHAPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ensemble</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENSEMBLE_FEATURE_COUNT = SHAPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ConnectionImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__NAME = 2;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TEMP = 3;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ORIGINAL_SOURCE = 4;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ORIGINAL_TARGET = 5;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.NotAllowedImpl <em>Not Allowed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.NotAllowedImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getNotAllowed()
	 * @generated
	 */
	int NOT_ALLOWED = 5;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Not Allowed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_ALLOWED_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.OutgoingImpl <em>Outgoing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.OutgoingImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getOutgoing()
	 * @generated
	 */
	int OUTGOING = 6;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Outgoing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTGOING_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.IncomingImpl <em>Incoming</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.IncomingImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getIncoming()
	 * @generated
	 */
	int INCOMING = 7;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Incoming</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCOMING_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.InAndOutImpl <em>In And Out</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.InAndOutImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getInAndOut()
	 * @generated
	 */
	int IN_AND_OUT = 8;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>In And Out</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_AND_OUT_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ExpectedImpl <em>Expected</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ExpectedImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getExpected()
	 * @generated
	 */
	int EXPECTED = 9;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Expected</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPECTED_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalOutgoingImpl <em>Global Outgoing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalOutgoingImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getGlobalOutgoing()
	 * @generated
	 */
	int GLOBAL_OUTGOING = 10;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Global Outgoing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OUTGOING_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalIncomingImpl <em>Global Incoming</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalIncomingImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getGlobalIncoming()
	 * @generated
	 */
	int GLOBAL_INCOMING = 11;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Global Incoming</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_INCOMING_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ViolationImpl <em>Violation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ViolationImpl
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getViolation()
	 * @generated
	 */
	int VIOLATION = 12;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__TARGET = CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__TEMP = CONNECTION__TEMP;

	/**
	 * The feature id for the '<em><b>Original Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__ORIGINAL_SOURCE = CONNECTION__ORIGINAL_SOURCE;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION__ORIGINAL_TARGET = CONNECTION__ORIGINAL_TARGET;

	/**
	 * The number of structural features of the '<em>Violation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIOLATION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram <em>Shapes Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shapes Diagram</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram
	 * @generated
	 */
	EClass getShapesDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram#getShapes <em>Shapes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Shapes</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram#getShapes()
	 * @see #getShapesDiagram()
	 * @generated
	 */
	EReference getShapesDiagram_Shapes();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shape</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape
	 * @generated
	 */
	EClass getShape();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getSourceConnections <em>Source Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Connections</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getSourceConnections()
	 * @see #getShape()
	 * @generated
	 */
	EReference getShape_SourceConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getTargetConnections <em>Target Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Target Connections</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getTargetConnections()
	 * @see #getShape()
	 * @generated
	 */
	EReference getShape_TargetConnections();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getName()
	 * @see #getShape()
	 * @generated
	 */
	EAttribute getShape_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getDescription()
	 * @see #getShape()
	 * @generated
	 */
	EAttribute getShape_Description();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getQuery()
	 * @see #getShape()
	 * @generated
	 */
	EAttribute getShape_Query();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Dummy <em>Dummy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dummy</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Dummy
	 * @generated
	 */
	EClass getDummy();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble <em>Ensemble</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ensemble</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble
	 * @generated
	 */
	EClass getEnsemble();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble#getShapes <em>Shapes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Shapes</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble#getShapes()
	 * @see #getEnsemble()
	 * @generated
	 */
	EReference getEnsemble_Shapes();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Target();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getName()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#isTemp <em>Temp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temp</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#isTemp()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Temp();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getOriginalSource <em>Original Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Original Source</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getOriginalSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_OriginalSource();

	/**
	 * Returns the meta object for the reference list '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getOriginalTarget <em>Original Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Original Target</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection#getOriginalTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_OriginalTarget();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.NotAllowed <em>Not Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Not Allowed</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.NotAllowed
	 * @generated
	 */
	EClass getNotAllowed();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Outgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Outgoing</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Outgoing
	 * @generated
	 */
	EClass getOutgoing();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Incoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Incoming</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Incoming
	 * @generated
	 */
	EClass getIncoming();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.InAndOut <em>In And Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>In And Out</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.InAndOut
	 * @generated
	 */
	EClass getInAndOut();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Expected <em>Expected</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expected</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Expected
	 * @generated
	 */
	EClass getExpected();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalOutgoing <em>Global Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Outgoing</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalOutgoing
	 * @generated
	 */
	EClass getGlobalOutgoing();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalIncoming <em>Global Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Incoming</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.GlobalIncoming
	 * @generated
	 */
	EClass getGlobalIncoming();

	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Violation <em>Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Violation</em>'.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Violation
	 * @generated
	 */
	EClass getViolation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Vespucci_model_2011_06_01Factory getVespucci_model_2011_06_01Factory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapesDiagramImpl <em>Shapes Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapesDiagramImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getShapesDiagram()
		 * @generated
		 */
		EClass SHAPES_DIAGRAM = eINSTANCE.getShapesDiagram();

		/**
		 * The meta object literal for the '<em><b>Shapes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHAPES_DIAGRAM__SHAPES = eINSTANCE.getShapesDiagram_Shapes();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapeImpl <em>Shape</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ShapeImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getShape()
		 * @generated
		 */
		EClass SHAPE = eINSTANCE.getShape();

		/**
		 * The meta object literal for the '<em><b>Source Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHAPE__SOURCE_CONNECTIONS = eINSTANCE.getShape_SourceConnections();

		/**
		 * The meta object literal for the '<em><b>Target Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHAPE__TARGET_CONNECTIONS = eINSTANCE.getShape_TargetConnections();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE__NAME = eINSTANCE.getShape_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE__DESCRIPTION = eINSTANCE.getShape_Description();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE__QUERY = eINSTANCE.getShape_Query();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.DummyImpl <em>Dummy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.DummyImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getDummy()
		 * @generated
		 */
		EClass DUMMY = eINSTANCE.getDummy();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.EnsembleImpl <em>Ensemble</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.EnsembleImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getEnsemble()
		 * @generated
		 */
		EClass ENSEMBLE = eINSTANCE.getEnsemble();

		/**
		 * The meta object literal for the '<em><b>Shapes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENSEMBLE__SHAPES = eINSTANCE.getEnsemble_Shapes();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ConnectionImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET = eINSTANCE.getConnection_Target();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__NAME = eINSTANCE.getConnection_Name();

		/**
		 * The meta object literal for the '<em><b>Temp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__TEMP = eINSTANCE.getConnection_Temp();

		/**
		 * The meta object literal for the '<em><b>Original Source</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__ORIGINAL_SOURCE = eINSTANCE.getConnection_OriginalSource();

		/**
		 * The meta object literal for the '<em><b>Original Target</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__ORIGINAL_TARGET = eINSTANCE.getConnection_OriginalTarget();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.NotAllowedImpl <em>Not Allowed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.NotAllowedImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getNotAllowed()
		 * @generated
		 */
		EClass NOT_ALLOWED = eINSTANCE.getNotAllowed();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.OutgoingImpl <em>Outgoing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.OutgoingImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getOutgoing()
		 * @generated
		 */
		EClass OUTGOING = eINSTANCE.getOutgoing();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.IncomingImpl <em>Incoming</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.IncomingImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getIncoming()
		 * @generated
		 */
		EClass INCOMING = eINSTANCE.getIncoming();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.InAndOutImpl <em>In And Out</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.InAndOutImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getInAndOut()
		 * @generated
		 */
		EClass IN_AND_OUT = eINSTANCE.getInAndOut();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ExpectedImpl <em>Expected</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ExpectedImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getExpected()
		 * @generated
		 */
		EClass EXPECTED = eINSTANCE.getExpected();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalOutgoingImpl <em>Global Outgoing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalOutgoingImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getGlobalOutgoing()
		 * @generated
		 */
		EClass GLOBAL_OUTGOING = eINSTANCE.getGlobalOutgoing();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalIncomingImpl <em>Global Incoming</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.GlobalIncomingImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getGlobalIncoming()
		 * @generated
		 */
		EClass GLOBAL_INCOMING = eINSTANCE.getGlobalIncoming();

		/**
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ViolationImpl <em>Violation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.ViolationImpl
		 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.Vespucci_model_2011_06_01PackageImpl#getViolation()
		 * @generated
		 */
		EClass VIOLATION = eINSTANCE.getViolation();

	}

} //Vespucci_model_2011_06_01Package
