/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage
 * @generated
 */
public interface Vespucci_modelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Vespucci_modelFactory eINSTANCE = de.tud.cs.st.vespucci.vespucci_model.impl.Vespucci_modelFactoryImpl.init();

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
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Vespucci_modelPackage getVespucci_modelPackage();

} //Vespucci_modelFactory
