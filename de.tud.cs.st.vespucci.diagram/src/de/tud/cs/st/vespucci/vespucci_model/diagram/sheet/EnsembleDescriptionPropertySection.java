/*
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;

/**
 * <p>Properties tab showing the Ensemble description property.</p>
 * <p><i>Reviewed by Thomas Schulz (Aug. 26 - Sept. 5, 2011)</i><br/>
 * <i>Reviewed by Dominic Scheurer (Sept. 18, 2011)</i></p>
 * 
 * @author Theo Kischka
 * @generated NOT
 */
public class EnsembleDescriptionPropertySection extends SimpleChangedAbstractBasicTextPropertySection {

	/**
	 * URL of the Vespucci model namespace.
	 */
	private static final String VESPUCCI_NAMESPACE_URL = ResourceBundle.getBundle("plugin").getString(
			"vespucci_modelNamespaceURI");

	/**
	 * @generated
	 */
	public IPropertySource getPropertySource(final Object object) {
		if (object instanceof IPropertySource) {
			return (IPropertySource) object;
		}
		final AdapterFactory af = getAdapterFactory(object);
		if (af != null) {
			final IItemPropertySource ips = (IItemPropertySource) af.adapt(object, IItemPropertySource.class);
			if (ips != null) {
				return new PropertySource(object, ips);
			}
		}
		if (object instanceof IAdaptable) {
			return (IPropertySource) ((IAdaptable) object).getAdapter(IPropertySource.class);
		}
		return null;
	}

	/**
	 * Modify/unwrap selection.
	 * 
	 * @generated
	 */
	protected Object transformSelection(final Object selected) {
		return selected;
	}

	/**
	 * @generated
	 */
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		if (selection.isEmpty() || false == selection instanceof StructuredSelection) {
			super.setInput(part, selection);
			return;
		}
		final StructuredSelection structuredSelection = ((StructuredSelection) selection);
		final ArrayList<Object> transformedSelection = new ArrayList<Object>(structuredSelection.size());
		for (final Iterator<?> it = structuredSelection.iterator(); it.hasNext();) {
			final Object r = transformSelection(it.next());
			if (r != null) {
				transformedSelection.add(r);
			}
		}
		super.setInput(part, new StructuredSelection(transformedSelection));
	}

	/**
	 * @generated
	 */
	protected AdapterFactory getAdapterFactory(final Object object) {
		if (getEditingDomain() instanceof AdapterFactoryEditingDomain) {
			return ((AdapterFactoryEditingDomain) getEditingDomain()).getAdapterFactory();
		}
		final TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(object);
		if (editingDomain != null) {
			return ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory();
		}
		return null;
	}

	/**
	 * @generated NOT
	 * @return Empty String.
	 */
	@Override
	protected String getPropertyNameLabel() {
		return "";
	}

	/**
	 * @generated NOT
	 * @return "ApplicationDescriptionChangeCommand"
	 */
	@Override
	protected String getPropertyChangeCommandName() {
		return "ApplicationDescriptionChangeCommand";
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void setPropertyValue(final EObject object, final Object value) {
		if (object instanceof Shape) {
			final EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage(VESPUCCI_NAMESPACE_URL);
			final Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;

			object.eSet(vesPackage.getShape_Description(), value);
		}
	}

	/**
	 * @generated NOT
	 * @return Returns the property value string.
	 */
	@Override
	protected String getPropertyValueString() {
		final EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage(VESPUCCI_NAMESPACE_URL);
		final Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;

		if (eObject instanceof EnsembleImpl) {
			getSectionComposite().setVisible(true);
			return (String) eObject.eGet(vesPackage.getShape_Description());
		} else {
			getSectionComposite().setVisible(false);
			throw new VespucciUnexpectedException(String.format("[%s] is not an ensemble.", eObject));
		}

	}

}
