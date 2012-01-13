/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl;

import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Shape;
import de.tud.cs.st.vespucci.vespucci_model_2011_06_01.Vespucci_model_2011_06_01Package;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ensemble</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model_2011_06_01.impl.EnsembleImpl#getShapes <em>Shapes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnsembleImpl extends ShapeImpl implements Ensemble {
	/**
	 * The cached value of the '{@link #getShapes() <em>Shapes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShapes()
	 * @generated
	 * @ordered
	 */
	protected EList<Shape> shapes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnsembleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Vespucci_model_2011_06_01Package.Literals.ENSEMBLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Shape> getShapes() {
		if (shapes == null) {
			shapes = new EObjectContainmentEList<Shape>(Shape.class, this, Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES);
		}
		return shapes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES:
				return ((InternalEList<?>)getShapes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES:
				return getShapes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES:
				getShapes().clear();
				getShapes().addAll((Collection<? extends Shape>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES:
				getShapes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Vespucci_model_2011_06_01Package.ENSEMBLE__SHAPES:
				return shapes != null && !shapes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EnsembleImpl
