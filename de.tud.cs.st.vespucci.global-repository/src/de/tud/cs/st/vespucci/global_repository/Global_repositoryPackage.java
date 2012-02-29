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
 */
package de.tud.cs.st.vespucci.global_repository;

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
 * @see de.tud.cs.st.vespucci.global_repository.Global_repositoryFactory
 * @model kind="package"
 * @generated
 */
public interface Global_repositoryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "global_repository";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://global-repository";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "vespucci";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Global_repositoryPackage eINSTANCE = de.tud.cs.st.vespucci.global_repository.impl.Global_repositoryPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tud.cs.st.vespucci.global_repository.impl.GlobalRepositoryImpl <em>Global Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tud.cs.st.vespucci.global_repository.impl.GlobalRepositoryImpl
	 * @see de.tud.cs.st.vespucci.global_repository.impl.Global_repositoryPackageImpl#getGlobalRepository()
	 * @generated
	 */
	int GLOBAL_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Ensembles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_REPOSITORY__ENSEMBLES = 0;

	/**
	 * The number of structural features of the '<em>Global Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_REPOSITORY_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link de.tud.cs.st.vespucci.global_repository.GlobalRepository <em>Global Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Repository</em>'.
	 * @see de.tud.cs.st.vespucci.global_repository.GlobalRepository
	 * @generated
	 */
	EClass getGlobalRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tud.cs.st.vespucci.global_repository.GlobalRepository#getEnsembles <em>Ensembles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ensembles</em>'.
	 * @see de.tud.cs.st.vespucci.global_repository.GlobalRepository#getEnsembles()
	 * @see #getGlobalRepository()
	 * @generated
	 */
	EReference getGlobalRepository_Ensembles();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Global_repositoryFactory getGlobal_repositoryFactory();

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
		 * The meta object literal for the '{@link de.tud.cs.st.vespucci.global_repository.impl.GlobalRepositoryImpl <em>Global Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tud.cs.st.vespucci.global_repository.impl.GlobalRepositoryImpl
		 * @see de.tud.cs.st.vespucci.global_repository.impl.Global_repositoryPackageImpl#getGlobalRepository()
		 * @generated
		 */
		EClass GLOBAL_REPOSITORY = eINSTANCE.getGlobalRepository();

		/**
		 * The meta object literal for the '<em><b>Ensembles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_REPOSITORY__ENSEMBLES = eINSTANCE.getGlobalRepository_Ensembles();

	}

} //Global_repositoryPackage
