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
package de.tud.cs.st.vespucci.vespucci_model;

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
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getSourceConnections <em>Source Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getTargetConnections <em>Target Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getName <em>Name</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getDescription <em>Description</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getQuery <em>Query</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getDiagramReference <em>Diagram Reference</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getEnsembleReference <em>Ensemble Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='notAllowedOnly'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot notAllowedOnly='self.targetConnections->forAll(x : Connection, y : Connection | (x <> y and x.source = y.source and x.target = y.target and x.oclIsTypeOf(NotAllowed)) implies y.oclIsTypeOf(NotAllowed))'"
 * @generated
 */
public interface Shape extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Connections</b></em>' reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model.Connection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Connections</em>' reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_SourceConnections()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList<Connection> getSourceConnections();

	/**
	 * Returns the value of the '<em><b>Target Connections</b></em>' reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model.Connection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Connections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Connections</em>' reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_TargetConnections()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
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
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getName <em>Name</em>}' attribute.
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
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_Description()
	 * @model default="<description>"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getDescription <em>Description</em>}' attribute.
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
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_Query()
	 * @model default="empty"
	 * @generated
	 */
	String getQuery();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getQuery <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query</em>' attribute.
	 * @see #getQuery()
	 * @generated
	 */
	void setQuery(String value);

	/**
	 * Returns the value of the '<em><b>Diagram Reference</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram#getShapes <em>Shapes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Reference</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Reference</em>' container reference.
	 * @see #setDiagramReference(ShapesDiagram)
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_DiagramReference()
	 * @see de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram#getShapes
	 * @model opposite="shapes" transient="false"
	 * @generated
	 */
	ShapesDiagram getDiagramReference();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Shape#getDiagramReference <em>Diagram Reference</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Reference</em>' container reference.
	 * @see #getDiagramReference()
	 * @generated
	 */
	void setDiagramReference(ShapesDiagram value);

	/**
	 * Returns the value of the '<em><b>Ensemble Reference</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tud.cs.st.vespucci.vespucci_model.Ensemble#getShapes <em>Shapes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ensemble Reference</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ensemble Reference</em>' container reference.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getShape_EnsembleReference()
	 * @see de.tud.cs.st.vespucci.vespucci_model.Ensemble#getShapes
	 * @model opposite="shapes" transient="false" changeable="false"
	 * @generated
	 */
	Ensemble getEnsembleReference();

} // Shape
