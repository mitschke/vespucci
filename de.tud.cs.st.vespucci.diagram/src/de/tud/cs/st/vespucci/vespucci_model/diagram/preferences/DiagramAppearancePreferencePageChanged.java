/*
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
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.preferences;

/******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    
 * Copied class - changes by BenjaminL
 ****************************************************************************/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.AbstractEnumerator;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.Properties;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.properties.internal.l10n.DiagramUIPropertiesMessages;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.ColoursAndFontsAndLineStylesPropertySection;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.JumpLinkStatus;
import org.eclipse.gmf.runtime.notation.JumpLinkType;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * @author dlander, nbalaba
 * 
 *         Appearance properties
 */
public class DiagramAppearancePreferencePageChanged extends
		ColoursAndFontsAndLineStylesPropertySection {

	protected static final String REVERSE_JUMP_LINKS_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_ReverseJumpLinksLabel_Text;

	private static final String ROUTER_OPTIONS_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_RouterOptionsLabel_Text;

	protected static final String AVOID_OBSTACLES_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_AvoidObstaclesLabel_Text;

	protected static final String CLOSEST_DISTANCE_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_ClosestDistanceLabel_Text;

	protected static final String LINE_ROUTER_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_LineRouterLabel_Text;

	protected static final String SMOOTHNESS_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_SmoothnessLabel_Text;

	protected static final String JUMP_LINKS_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_JumpLinksLabel_Text;

	protected static final String JUMP_LINK_TYPE_NAME_LABEL = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_JumpLinkTypeLabel_Text;

	private static final String JUMP_LINKS_GROUP_NAME = DiagramUIPropertiesMessages.ConnectionAppearanceDetails_JumpLinkGroupLabel_Text;

	// radio button widgets cache with a button as a value and abstract
	// enumeration literal as a key
	protected Map buttons = new HashMap();

	private Button avoidObstaclesButton;

	private Button closestDistanceButton;

	private Button reverseJumpLinksButton;

	/**
	 * Transfer data to model
	 */
	private void updateModel(final String szCmd, final String szID,
			final Object val) {
		if (isReadOnly()) {
			refresh();
			return;
		}

		ArrayList commands = new ArrayList();

		Iterator it = getInput().iterator();

		while (it.hasNext()) {
			final ConnectionEditPart ep = (ConnectionEditPart) it.next();

			Resource res = ((View) ep.getModel()).eResource();

			commands.add(createCommand(szCmd, res, new Runnable() {

				public void run() {
					ENamedElement element = PackageUtil.getElement(szID);
					if (element instanceof EStructuralFeature)
						ep.setStructuralFeatureValue(
								(EStructuralFeature) element, val);
				}
			}));
		}

		executeAsCompositeCommand(szCmd, commands);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.
	 * AbstractNotationPropertiesSection
	 * #initializeControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void initializeControls(Composite parent) {
		composite = getWidgetFactory().createFlatFormComposite(parent);
		FormLayout layout = (FormLayout) composite.getLayout();
		layout.spacing = 3;

		Composite groups = getWidgetFactory().createComposite(composite);
		groups.setLayout(new GridLayout(2, false));
		createFontsAndColorsGroups(groups);
		colorsAndFontsGroup.setLayoutData(new GridData(
				GridData.VERTICAL_ALIGN_BEGINNING));
		createConnectionPropertyGroups(groups);
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.ui.properties.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.gmf.runtime.common.ui.properties.TabbedPropertySheetPage)
	 */
	public void createConnectionPropertyGroups(Composite groups) {

		// routing
		Group routing = getWidgetFactory().createGroup(groups,
				ROUTER_OPTIONS_LABEL);
		routing.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		routing.setLayoutData(data);

		// line router
		createRadioGroup(
				routing,
				Routing.VALUES.iterator(),
				Properties.ID_ROUTING,
				DiagramUIPropertiesMessages.AppearanceDetails_LineRouterCommand_Text,
				LINE_ROUTER_NAME_LABEL, 3);

		// router options
		createRouterOptionsGroup(routing);
	}

	/**
	 * Create router options group
	 * 
	 * @param groups
	 *            - aprent composite
	 */
	protected void createRouterOptionsGroup(Composite groups) {

		Composite routerOptionsGroup = getWidgetFactory().createComposite(
				groups);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		routerOptionsGroup.setLayoutData(data);
		routerOptionsGroup.setLayout(new GridLayout(2, false));

		avoidObstaclesButton = getWidgetFactory().createButton(
				routerOptionsGroup, AVOID_OBSTACLES_NAME_LABEL, SWT.CHECK);
		avoidObstaclesButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				updateModel(
						DiagramUIPropertiesMessages.AppearanceDetails_AvoidObstaclesCommand_Text,
						Properties.ID_AVOIDOBSTRUCTIONS,
						Boolean.valueOf(avoidObstaclesButton.getSelection()));
			}
		});

		closestDistanceButton = getWidgetFactory().createButton(
				routerOptionsGroup, CLOSEST_DISTANCE_NAME_LABEL, SWT.CHECK);

		closestDistanceButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				updateModel(
						DiagramUIPropertiesMessages.AppearanceDetails_ClosestDistanceCommand_Text,
						Properties.ID_CLOSESTDISTANCE,
						Boolean.valueOf(closestDistanceButton.getSelection()));
			}
		});
	}

	/**
	 * Create and return a group of radio buttons representing a property
	 * 
	 * @param parent
	 *            - patrent compopsite
	 * @return - a last control created for this group
	 */
	protected void createRadioGroup(Composite parent, Iterator iterator,
			final Object propertyId, final String commandName,
			String propertyName, int rows) {

		Group group = getWidgetFactory().createGroup(parent, propertyName);
		group.setLayout(new GridLayout(rows, true));
		GridData data = new GridData(GridData.FILL_BOTH);// GridData.FILL_HORIZONTAL
															// |
		group.setLayoutData(data);

		Button radioButton = null;
		for (Iterator e = iterator; e.hasNext();) {
			AbstractEnumerator literal = (AbstractEnumerator) e.next();
			String propertyValueName = translate(literal);

			radioButton = getWidgetFactory().createButton(group,
					propertyValueName, SWT.RADIO);
			radioButton.setData(literal);
			buttons.put(literal, radioButton);
			radioButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent event) {
					setPropertyValue(event, propertyId, commandName);
				}
			});

			if (isReadOnly())
				radioButton.setEnabled(false);
		}

	}

	/**
	 * Returns the translated string representing the connection appearance
	 * properties. This is not a generic method; it needs to be updated if it is
	 * to handle a new property.
	 * 
	 * @param literal
	 *            the enumerator of literals
	 * @return the translated string
	 */
	private String translate(AbstractEnumerator literal) {

		if (JumpLinkType.SEMICIRCLE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksType_SemiCircle;
		} else if (JumpLinkType.SQUARE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksType_Square;
		} else if (JumpLinkType.CHAMFERED_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksType_Chamfered;
		} else if (JumpLinkStatus.NONE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksStatus_None;
		} else if (JumpLinkStatus.ALL_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksStatus_All;
		} else if (JumpLinkStatus.BELOW_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksStatus_Below;
		} else if (JumpLinkStatus.ABOVE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_JumplinksStatus_Above;
		} else if (Smoothness.NONE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_Smoothness_SmoothNone;
		} else if (Smoothness.NORMAL_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_Smoothness_SmoothNormal;
		} else if (Smoothness.LESS_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_Smoothness_SmoothLess;
		} else if (Smoothness.MORE_LITERAL.equals(literal)) {
			return DiagramUIMessages.PropertyDescriptorFactory_Smoothness_SmoothMore;
		} else if (Routing.MANUAL_LITERAL.equals(literal)) {
			return DiagramUIMessages.ConnectionAppearancePropertySection_Router_Manual;
		} else if (Routing.RECTILINEAR_LITERAL.equals(literal)) {
			return DiagramUIMessages.ConnectionAppearancePropertySection_Router_Rectilinear;
		} else if (Routing.TREE_LITERAL.equals(literal)) {
			return DiagramUIMessages.ConnectionAppearancePropertySection_Router_Tree;
		}

		assert false : "No translated string available."; //$NON-NLS-1$
		return ""; //$NON-NLS-1$

	}

	/**
	 * @param event
	 */
	protected void setPropertyValue(SelectionEvent event,
			final Object propertyId, String commandName) {

		ArrayList commands = new ArrayList();
		Iterator it = getInput().iterator();
		final Button button = (Button) event.getSource();

		while (it.hasNext()) {
			final IGraphicalEditPart ep = (IGraphicalEditPart) it.next();

			commands.add(createCommand(commandName,
					((View) ep.getModel()).eResource(), new Runnable() {

						public void run() {
							if (propertyId instanceof String) {
								ENamedElement element = PackageUtil
										.getElement((String) propertyId);
								if (element instanceof EStructuralFeature)
									ep.setStructuralFeatureValue(
											(EStructuralFeature) element,
											button.getData());
							}

						}
					}));
		}

		executeAsCompositeCommand(commandName, commands);

	}

	public void refresh() {
		if (!isDisposed()) {
			try {
				executeAsReadAction(new Runnable() {

					public void run() {

						// Deselect all the radio buttons;
						// the appropriate radio buttons will be properly
						// selected below
						for (Iterator i = buttons.keySet().iterator(); i
								.hasNext();) {
							Button radioButton = (Button) buttons.get(i.next());
							radioButton.setSelection(false);
						}

						// Update display from model
						ConnectionEditPart obj = (ConnectionEditPart) getSingleInput();

						if (!avoidObstaclesButton.isDisposed()) {
							Boolean val = (Boolean) obj
									.getStructuralFeatureValue(NotationPackage.eINSTANCE
											.getRoutingStyle_AvoidObstructions());
							avoidObstaclesButton.setSelection(val
									.booleanValue());
						}

						if (!closestDistanceButton.isDisposed()) {
							Boolean val = (Boolean) obj
									.getStructuralFeatureValue(NotationPackage.eINSTANCE
											.getRoutingStyle_ClosestDistance());
							closestDistanceButton.setSelection(val
									.booleanValue());
						}

						Button button = (Button) buttons
								.get(obj.getStructuralFeatureValue(NotationPackage.eINSTANCE
										.getRoutingStyle_JumpLinkStatus()));
						if (button != null)
							button.setSelection(true);

						button = (Button) buttons
								.get(obj.getStructuralFeatureValue(NotationPackage.eINSTANCE
										.getRoutingStyle_JumpLinkType()));
						if (button != null)
							button.setSelection(true);

						// determine if tree routing is supported
						Button treeRoutingButton = (Button) buttons
								.get(Routing.TREE_LITERAL);
						if (treeRoutingButton != null)
							treeRoutingButton
									.setEnabled(obj instanceof ITreeBranchEditPart);

						button = (Button) buttons
								.get(obj.getStructuralFeatureValue(NotationPackage.eINSTANCE
										.getRoutingStyle_Routing()));
						if (button != null)
							button.setSelection(true);

						button = (Button) buttons
								.get(obj.getStructuralFeatureValue(NotationPackage.eINSTANCE
										.getRoutingStyle_Smoothness()));
						if (button != null)
							button.setSelection(true);

					}
				});
			} catch (Exception e) {
				IStatus iStat = new Status(Status.ERROR,
						VespucciDiagramEditorPlugin.ID,
						"Couldn't refresh diagram (appearance)");
				StatusManager.getManager().handle(iStat, StatusManager.SHOW);
				StatusManager.getManager().handle(iStat, StatusManager.LOG);
			}
		}
	}
}