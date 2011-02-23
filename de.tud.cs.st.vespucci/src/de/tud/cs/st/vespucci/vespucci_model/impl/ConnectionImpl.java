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
package de.tud.cs.st.vespucci.vespucci_model.impl;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#isTemp <em>Temp</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#getOriginalSource <em>Original Source</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl#getOriginalTarget <em>Original Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionImpl extends EObjectImpl implements Connection {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Shape source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Shape target;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "[all]";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isTemp() <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemp()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TEMP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTemp() <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemp()
	 * @generated
	 * @ordered
	 */
	protected boolean temp = TEMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOriginalSource() <em>Original Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalSource()
	 * @generated
	 * @ordered
	 */
	protected Shape originalSource;

	/**
	 * The cached value of the '{@link #getOriginalTarget() <em>Original Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalTarget()
	 * @generated
	 * @ordered
	 */
	protected Shape originalTarget;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Vespucci_modelPackage.Literals.CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (Shape)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Vespucci_modelPackage.CONNECTION__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Shape newSource) {
		Shape oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Shape)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Vespucci_modelPackage.CONNECTION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Shape newTarget) {
		Shape oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTemp() {
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemp(boolean newTemp) {
		boolean oldTemp = temp;
		temp = newTemp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__TEMP, oldTemp, temp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape getOriginalSource() {
		if (originalSource != null && originalSource.eIsProxy()) {
			InternalEObject oldOriginalSource = (InternalEObject)originalSource;
			originalSource = (Shape)eResolveProxy(oldOriginalSource);
			if (originalSource != oldOriginalSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE, oldOriginalSource, originalSource));
			}
		}
		return originalSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetOriginalSource() {
		return originalSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalSource(Shape newOriginalSource) {
		Shape oldOriginalSource = originalSource;
		originalSource = newOriginalSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE, oldOriginalSource, originalSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape getOriginalTarget() {
		if (originalTarget != null && originalTarget.eIsProxy()) {
			InternalEObject oldOriginalTarget = (InternalEObject)originalTarget;
			originalTarget = (Shape)eResolveProxy(oldOriginalTarget);
			if (originalTarget != oldOriginalTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET, oldOriginalTarget, originalTarget));
			}
		}
		return originalTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetOriginalTarget() {
		return originalTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalTarget(Shape newOriginalTarget) {
		Shape oldOriginalTarget = originalTarget;
		originalTarget = newOriginalTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET, oldOriginalTarget, originalTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case Vespucci_modelPackage.CONNECTION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case Vespucci_modelPackage.CONNECTION__NAME:
				return getName();
			case Vespucci_modelPackage.CONNECTION__TEMP:
				return isTemp();
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				if (resolve) return getOriginalSource();
				return basicGetOriginalSource();
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				if (resolve) return getOriginalTarget();
				return basicGetOriginalTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				setSource((Shape)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__TARGET:
				setTarget((Shape)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__NAME:
				setName((String)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__TEMP:
				setTemp((Boolean)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				setOriginalSource((Shape)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				setOriginalTarget((Shape)newValue);
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
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				setSource((Shape)null);
				return;
			case Vespucci_modelPackage.CONNECTION__TARGET:
				setTarget((Shape)null);
				return;
			case Vespucci_modelPackage.CONNECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Vespucci_modelPackage.CONNECTION__TEMP:
				setTemp(TEMP_EDEFAULT);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				setOriginalSource((Shape)null);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				setOriginalTarget((Shape)null);
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
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				return source != null;
			case Vespucci_modelPackage.CONNECTION__TARGET:
				return target != null;
			case Vespucci_modelPackage.CONNECTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Vespucci_modelPackage.CONNECTION__TEMP:
				return temp != TEMP_EDEFAULT;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				return originalSource != null;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				return originalTarget != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", temp: ");
		result.append(temp);
		result.append(')');
		return result.toString();
	}

} //ConnectionImpl
