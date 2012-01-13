/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shapes Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.ShapesDiagram#getShapes <em>Shapes</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShapesDiagram()
 * @model
 * @generated
 */
public interface ShapesDiagram extends EObject {
	/**
	 * Returns the value of the '<em><b>Shapes</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shapes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shapes</em>' containment reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShapesDiagram_Shapes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Shape> getShapes();

} // ShapesDiagram
