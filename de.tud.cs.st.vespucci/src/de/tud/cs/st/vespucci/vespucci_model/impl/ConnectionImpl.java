/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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
package de.tud.cs.st.vespucci.vespucci_model.impl;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.io.ValidDependenciesReader;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connection</b></em>'. <!--
 * end-user-doc -->
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
	
	// TODO change model, so dependencies are an attribute.
	
	/**
	 * Checks if given name is valid as dependency.
	 * 
	 * @param newName
	 *            Name of Connection-Dependency to be checked.
	 * @return True, if given name is valid; false otherwise.
	 */
	private static boolean checkConnName(final String newName) {
		final String[] newNameSplit = newName.split(", ");
		boolean valid = false;

		// check all dependencies
		for (final String newNamePart : newNameSplit) {
			// remove whitespace
			// newNamePart = newNamePart.replaceAll("\\s", "");
			// probe for all valid names
			valid = false;
			for (final String validName : connNames) {
				if (validName.equals(newNamePart)) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Convenient method to get the sad-file, currently used.
	 * 
	 * @return The resource associated with currently active file in the editor.
	 */
	private static IResource getResource() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return null;
		}
		final IWorkbenchWindow workbenchwindow = workbench.getActiveWorkbenchWindow();
		if (workbenchwindow == null) {
			return null;
		}
		final IWorkbenchPage workbenchpage = workbenchwindow.getActivePage();
		if (workbenchpage == null) {
			return null;
		}
		final IEditorPart editor = workbenchpage.getActiveEditor();

		final IEditorInput input = editor.getEditorInput();
		if (!(input instanceof IFileEditorInput)) {
			return null;
		}
		return ((IFileEditorInput) input).getFile();
	}

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!--end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Shape source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!--end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Shape target;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "[all]";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc -->
	 * Represents dependencies and will be displayed as label of the connection in the diagram <!--
	 * end-user-doc
	 * -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isTemp() <em>Temp</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc
	 * -->
	 * 
	 * @see #isTemp()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TEMP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTemp() <em>Temp</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc
	 * -->
	 * 
	 * @see #isTemp()
	 * @generated
	 * @ordered
	 */
	protected boolean temp = TEMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOriginalSource() <em>Original Source</em>}' reference
	 * list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOriginalSource()
	 * @generated
	 * @ordered
	 */
	protected EList<Shape> originalSource;

	/**
	 * The cached value of the '{@link #getOriginalTarget() <em>Original Target</em>}' reference
	 * list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOriginalTarget()
	 * @generated
	 * @ordered
	 */
	protected EList<Shape> originalTarget;

	/**
	 * Valid dependencies for a constraint
	 */
	private static String[] connNames;

	/**
	 * Problem-marker indicating a invalid dependency.
	 */
	private IMarker invalidDepMarker;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionImpl() {
		super();
		connNames = new ValidDependenciesReader().getKeywords();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Shape basicGetTarget() {
		return target;
	}
	
	/**
	 * <!-- begin-user-doc --> Added dependencies-array <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				if (resolve) {
					return getSource();
				}
				return basicGetSource();
			case Vespucci_modelPackage.CONNECTION__TARGET:
				if (resolve) {
					return getTarget();
				}
				return basicGetTarget();
			case Vespucci_modelPackage.CONNECTION__NAME:
				return getName();
			case Vespucci_modelPackage.CONNECTION__TEMP:
				return isTemp();
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				return getOriginalSource();
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				return getOriginalTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
				return originalSource != null && !originalSource.isEmpty();
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				return originalTarget != null && !originalTarget.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
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
				getOriginalSource().clear();
				getOriginalSource().addAll((Collection<? extends Shape>)newValue);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				getOriginalTarget().clear();
				getOriginalTarget().addAll((Collection<? extends Shape>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Vespucci_modelPackage.Literals.CONNECTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Vespucci_modelPackage.CONNECTION__SOURCE:
				setSource((Shape) null);
				return;
			case Vespucci_modelPackage.CONNECTION__TARGET:
				setTarget((Shape) null);
				return;
			case Vespucci_modelPackage.CONNECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Vespucci_modelPackage.CONNECTION__TEMP:
				setTemp(TEMP_EDEFAULT);
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE:
				getOriginalSource().clear();
				return;
			case Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET:
				getOriginalTarget().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Shape> getOriginalSource() {
		if (originalSource == null) {
			originalSource = new EObjectResolvingEList<Shape>(Shape.class, this, Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE);
		}
		return originalSource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Shape> getOriginalTarget() {
		if (originalTarget == null) {
			originalTarget = new EObjectResolvingEList<Shape>(Shape.class, this, Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET);
		}
		return originalTarget;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTemp() {
		return temp;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(final String newName) {
		// delete obsolete problem marker, indicating invalid dependency
		if (invalidDepMarker != null) {
			try {
				invalidDepMarker.delete();
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// check given dependency and add problem-marker if invalid.
		final IResource resource = getResource();
		if (!checkConnName(newName)) {
			if (resource == null) {
				// should be unreachable
				return;
			}
			try {
				// create new marker
				invalidDepMarker = resource.createMarker(IMarker.PROBLEM);
				invalidDepMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_LOW);
				invalidDepMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				invalidDepMarker.setAttribute(IMarker.MESSAGE, String.format(
						"Dependency \"%s\" for constraint is invalid.", newName));
				invalidDepMarker.setAttribute(IMarker.LOCATION, String.format("\"%s\" --> \"%s\"", source.getName(),
						target.getName()));
			} catch (final CoreException e) {
				// nothing to do
			}
		}
		// set new name
		final String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(Shape newSource) {
		Shape oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(Shape newTarget) {
		Shape oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTemp(boolean newTemp) {
		boolean oldTemp = temp;
		temp = newTemp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.CONNECTION__TEMP, oldTemp, temp));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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

} // ConnectionImpl
