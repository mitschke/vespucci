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
 * A representation of the model object '<em><b>Shape</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getSourceConnections <em>Source Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getTargetConnections <em>Target Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getName <em>Name</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getDescription <em>Description</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getQuery <em>Query</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape()
 * @model
 * @generated
 */
public interface Shape extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Connections</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Connections</em>' containment reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape_SourceConnections()
	 * @model containment="true"
	 * @generated
	 */
	EList<Connection> getSourceConnections();

	/**
	 * Returns the value of the '<em><b>Target Connections</b></em>' containment reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Connection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Connections</em>' containment reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape_TargetConnections()
	 * @model containment="true"
	 * @generated
	 */
	EList<Connection> getTargetConnections();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>"<description>"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape_Description()
	 * @model default="<description>"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Query</b></em>' attribute.
	 * The default value is <code>"empty"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query</em>' attribute.
	 * @see #setQuery(String)
	 * @see de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package#getShape_Query()
	 * @model default="empty"
	 * @generated
	 */
	String getQuery();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape#getQuery <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query</em>' attribute.
	 * @see #getQuery()
	 * @generated
	 */
	void setQuery(String value);

} // Shape
