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
package de.tud.cs.st.vespucci.global_repository.impl;

import de.tud.cs.st.vespucci.global_repository.GlobalRepository;
import de.tud.cs.st.vespucci.global_repository.Global_repositoryFactory;
import de.tud.cs.st.vespucci.global_repository.Global_repositoryPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

import de.tud.cs.st.vespucci.vespucci_model.impl.Vespucci_modelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Global_repositoryPackageImpl extends EPackageImpl implements Global_repositoryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalRepositoryEClass = null;

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
	 * @see de.tud.cs.st.vespucci.global_repository.Global_repositoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Global_repositoryPackageImpl() {
		super(eNS_URI, Global_repositoryFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link Global_repositoryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Global_repositoryPackage init() {
		if (isInited) return (Global_repositoryPackage)EPackage.Registry.INSTANCE.getEPackage(Global_repositoryPackage.eNS_URI);

		// Obtain or create and register package
		Global_repositoryPackageImpl theGlobal_repositoryPackage = (Global_repositoryPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Global_repositoryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Global_repositoryPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		Vespucci_modelPackageImpl theVespucci_modelPackage = (Vespucci_modelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(Vespucci_modelPackage.eNS_URI) instanceof Vespucci_modelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(Vespucci_modelPackage.eNS_URI) : Vespucci_modelPackage.eINSTANCE);

		// Create package meta-data objects
		theGlobal_repositoryPackage.createPackageContents();
		theVespucci_modelPackage.createPackageContents();

		// Initialize created meta-data
		theGlobal_repositoryPackage.initializePackageContents();
		theVespucci_modelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGlobal_repositoryPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Global_repositoryPackage.eNS_URI, theGlobal_repositoryPackage);
		return theGlobal_repositoryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalRepository() {
		return globalRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalRepository_Ensembles() {
		return (EReference)globalRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Global_repositoryFactory getGlobal_repositoryFactory() {
		return (Global_repositoryFactory)getEFactoryInstance();
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
		globalRepositoryEClass = createEClass(GLOBAL_REPOSITORY);
		createEReference(globalRepositoryEClass, GLOBAL_REPOSITORY__ENSEMBLES);
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

		// Obtain other dependent packages
		Vespucci_modelPackage theVespucci_modelPackage = (Vespucci_modelPackage)EPackage.Registry.INSTANCE.getEPackage(Vespucci_modelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(globalRepositoryEClass, GlobalRepository.class, "GlobalRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalRepository_Ensembles(), theVespucci_modelPackage.getEnsemble(), null, "ensembles", null, 0, -1, GlobalRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Global_repositoryPackageImpl
