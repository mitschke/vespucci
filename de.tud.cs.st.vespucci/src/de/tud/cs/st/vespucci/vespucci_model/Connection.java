/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitï¿½t Darmstadt
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
 *     Universitï¿½t Darmstadt nor the names of its contributors may be used to 
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
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getSource <em>Source</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getName <em>Name</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#isTemp <em>Temp</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getOriginalSource <em>Original Source</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getOriginalTarget <em>Original Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection()
 * @model
 * @generated
 */
public interface Connection extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Shape)
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_Source()
	 * @model
	 * @generated
	 */
	Shape getSource();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Shape value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Shape)
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_Target()
	 * @model
	 * @generated
	 */
	Shape getTarget();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Shape value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"[all]"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_Name()
	 * @model default="[all]"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Connection#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Temp</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temp</em>' attribute.
	 * @see #setTemp(boolean)
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_Temp()
	 * @model default="false"
	 * @generated
	 */
	boolean isTemp();

	/**
	 * Sets the value of the '{@link de.tud.cs.st.vespucci.vespucci_model.Connection#isTemp <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temp</em>' attribute.
	 * @see #isTemp()
	 * @generated
	 */
	void setTemp(boolean value);

	/**
	 * Returns the value of the '<em><b>Original Source</b></em>' reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model.Shape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Source</em>' reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_OriginalSource()
	 * @model
	 * @generated
	 */
	EList<Shape> getOriginalSource();

	/**
	 * Returns the value of the '<em><b>Original Target</b></em>' reference list.
	 * The list contents are of type {@link de.tud.cs.st.vespucci.vespucci_model.Shape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Target</em>' reference list.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage#getConnection_OriginalTarget()
	 * @model
	 * @generated
	 */
	EList<Shape> getOriginalTarget();

} // Connection
