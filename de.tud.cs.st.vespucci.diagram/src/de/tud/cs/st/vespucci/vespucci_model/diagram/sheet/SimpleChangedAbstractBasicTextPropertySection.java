/******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 ****************************************************************************/
package de.tud.cs.st.vespucci.vespucci_model.diagram.sheet;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.common.ui.util.StatusLineUtil;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.gmf.runtime.diagram.ui.properties.views.TextChangeHelper;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.tud.cs.st.vespucci.io.KeywordReader;

/**
 * A Changed Copy of AbstractBasicTextPropertySection (org.eclipse.gmf.runtime.diagram
 * .ui.properties.sections.AbstractBasicTextPropertySection)
 * 
 * 
 * 
 * Original version by:
 * 
 * @author natalia balaba
 * 
 *         Changed by:
 * @author MalteV
 * @author BenjaminL
 * @author DominicS
 * @author Alexander Weitzmann
 */
public abstract class SimpleChangedAbstractBasicTextPropertySection extends AbstractModelerPropertySection {

	private final int QUERY_TAB_HEIGHT_SHIFT = 35;

	private final int QUERY_TAB_WIDTH_SHIFT = 45;

	// styled text widget to display and set value of the property
	private MarkableStyledText textWidget;

	// parent parent ... parent composite for the size of the textfield
	private Composite scrolledParent;

	private Composite sectionComposite;

	private final int startHeight = 15;

	/**
	 * Preference-store of the java source viewer. Used for highlighting and text-settings for query.
	 */
	private static final IPreferenceStore srcViewerPrefs = PreferenceConstants.getPreferenceStore();

	Listener resizeLinstener = new Listener() {
		@Override
		public void handleEvent(final Event e) {
			updateHeight();
		}
	};

	/**
	 * A helper to listen for events that indicate that a text field has been changed.
	 */
	private final TextChangeHelper listener = new TextChangeHelper() {
		private boolean textModified = false;
		
		/**
		 * Pattern to be used to match strings in query including the single quotes
		 */
		private static final String STRING_PATTERN = "'.+?'";
		
		/**
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		@Override
		public void handleEvent(final Event event) {
			switch (event.type) {
			case SWT.KeyDown:
				textModified = true;
				if (event.character == SWT.CR) {
					getPropertyValueString();
				}
				break;
			case SWT.FocusOut:
				textChanged((Control) event.widget);
				break;
			case SWT.FocusIn:
				textChanged((Control) event.widget);
				break;
			case SWT.MouseDown:
				
				break;
			default:
				break;
			}
		}

		@Override
		public void startListeningTo(final Control control) {
			control.addListener(SWT.FocusOut, this);
			control.addListener(SWT.Modify, this);
			control.addListener(SWT.FocusIn, this);
			control.addListener(SWT.MouseDown, this);
		}

		@Override
		public void textChanged(final Control control) {
			if (textModified) {
				// clear error message
				final IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
				StatusLineUtil.outputErrorMessage(part, StringStatics.BLANK);

				setPropertyValue(control);
				textModified = false;
			}
		}
	};

	/**
	 * @return - the default implementation returns contents of the text widget as a new value for the property. Subclasses can
	 *         could be override.
	 */
	protected final Object computeNewPropertyValue() {
		return getTextWidget().getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse .swt.widgets.Composite,
	 * org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public final void createControls(final Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		doCreateControls(parent, aTabbedPropertySheetPage);
	}

	/**
	 * Instantiate a text widget
	 * 
	 * @param parent
	 *            - parent composite
	 * @return - a text widget to display and edit the property
	 */
	protected final MarkableStyledText createTextWidget(final Composite parent) {
		getSectionComposite().getSize();

		final MarkableStyledText st = new MarkableStyledText(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		final Font userFont = JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
		st.setFont(userFont);

		final FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.height = startHeight;
		st.setLayoutData(data);

		st.setBackground(EditorsUI.getSharedTextColors().getColor(
				PreferenceConverter.getColor(EditorsUI.getPreferenceStore(), AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)));

		if (isReadOnly()) {
			st.setEditable(false);
		}

		return st;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	@Override
	public final void dispose() {
		stopTextWidgetEventListener();
		super.dispose();
	}

	/**
	 * Creates the GUI <code>Control</code> for this text property section
	 * 
	 * @param parent
	 *            parent <code>Composite</code>
	 * @param aTabbedPropertySheetPage
	 *            <code>TabbedPropertySheetPage</code>
	 * @see org.eclipse.gmf.runtime.common.ui.properties.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.gmf.runtime.common.ui.properties.TabbedPropertySheetPage)
	 */
	public final void doCreateControls(final Composite parent, final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		sectionComposite = getWidgetFactory().createFlatFormComposite(parent);
		textWidget = createTextWidget(sectionComposite);
		scrolledParent = parent;

		while (true) {
			// TODO: There must be a nicer way for redraw the text widget then
			// to say the first scrolledcomposite .layout()
			// the scrolledParent should be "main" composite for the query tab
			if (scrolledParent instanceof ScrolledComposite) {
				break;
			}
			if (scrolledParent.getParent() == null) {
				break;
			}
			scrolledParent = scrolledParent.getParent();

		}

		scrolledParent.addListener(SWT.Resize, resizeLinstener);
		updateHeight();
		startTextWidgetEventListener();
	}

	private int getHeight() {
		return scrolledParent.getSize().y - QUERY_TAB_HEIGHT_SHIFT;
	}

	/**
	 * @return Returns the listener.
	 */
	protected final TextChangeHelper getListener() {
		return listener;
	}

	/**
	 * @return - title of the command which will be executed to set the property
	 */
	protected abstract String getPropertyChangeCommandName();

	/**
	 * @return - name of the property to place in the label widget
	 */
	protected abstract String getPropertyNameLabel();

	/**
	 * returns as an array the property name
	 * 
	 * @return - array of strings where each describes a property name one per property. The strings will be used to calculate
	 *         common indent from the left
	 */
	protected final String[] getPropertyNameStringsArray() {
		return new String[] { getPropertyNameLabel() };
	}

	/**
	 * @return - string representation of the property value
	 */
	protected abstract String getPropertyValueString();

	/**
	 * @return Returns the sectionComposite.
	 */
	public final Composite getSectionComposite() {
		return sectionComposite;
	}

	/**
	 * @return Returns the textWidget.
	 */
	protected final StyledText getTextWidget() {
		return textWidget;
	}

	private int getWidth() {
		return scrolledParent.getSize().x - QUERY_TAB_WIDTH_SHIFT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public final void refresh() {
		getListener().startNonUserChange();
		try {
			executeAsReadAction(new Runnable() {

				@Override
				public void run() {
					refreshUI();
				}
			});
		} finally {
			getListener().finishNonUserChange();
		}
	}

	/**
	 * Refresh UI body - refresh will surround this with read action block
	 */
	protected final void refreshUI() {
		if (textWidget != null) {
			textWidget.setText(getPropertyValueString());
		}
	}

	/**
	 * User pressed Enter key after editing text field - update the model
	 * 
	 * @param control
	 *            <code>Control</code>
	 */
	protected final synchronized void setPropertyValue(final Control control) {
		final Object value = computeNewPropertyValue();
		final ArrayList<ICommand> commands = new ArrayList<ICommand>();
		for (final Iterator<?> it = getEObjectList().iterator(); it.hasNext();) {
			final EObject next = (EObject) it.next();
			commands.add(createCommand(getPropertyChangeCommandName(), next, new Runnable() {

				@Override
				public void run() {
					setPropertyValue(next, value);
				}

			}));
		}
		executeAsCompositeCommand(getPropertyChangeCommandName(), commands);
		refresh();
	}

	/**
	 * Set property value for the given object
	 * 
	 * @param object
	 *            - owner of the property
	 * @param value
	 *            - new value
	 */
	protected abstract void setPropertyValue(EObject object, Object value);

	public final void setScrolledParent(final Composite scrolledParent) {
		this.scrolledParent = scrolledParent;
	}

	/**
	 * Start listening to the text widget events
	 */
	protected final void startTextWidgetEventListener() {
		if (!isReadOnly()) {
			getListener().startListeningTo(getTextWidget());
			getListener().startListeningForEnter(getTextWidget());
		}
	}

	/**
	 * Stop listening to text widget events
	 */
	protected final void stopTextWidgetEventListener() {
		if (!isReadOnly()) {
			getListener().stopListeningTo(getTextWidget());
		}
	}

	/**
	 * calculates the new size of the widget and updates it
	 */
	private void updateHeight() {
		if (getTextWidget() != null && !getTextWidget().isDisposed()) {
			getTextWidget().setVisible(false);
			scrolledParent.setVisible(false);
			final FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(0, 0);
			data.height = getHeight();
			data.width = getWidth();
			getTextWidget().setLayoutData(data);
			final int HEIGHTS_SCROLLLINE = 30;
			Composite com = getSectionComposite();
			// TODO: there must be a nice way to update the heights and widths
			// of the textwidget and its parents
			while (true) {

				if (com instanceof ScrolledComposite) {
					break;
				}
				if (com.getParent() == null) {
					break;
				}
				com.setSize(getWidth(), getHeight() + HEIGHTS_SCROLLLINE);
				com = com.getParent();

			}
			com.layout();
			getTextWidget().setVisible(true);
			scrolledParent.setVisible(true);
		}
	}
}