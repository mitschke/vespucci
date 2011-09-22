/******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 ****************************************************************************/
package de.tud.cs.st.vespucci.vespucci_model.diagram.sheet;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.common.ui.util.StatusLineUtil;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.gmf.runtime.diagram.ui.properties.views.TextChangeHelper;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
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
import org.osgi.framework.FrameworkUtil;

import de.tud.cs.st.vespucci.io.KeywordReader;

/**
 * <p>A Changed Copy of AbstractBasicTextPropertySection
 * (org.eclipse.gmf.runtime.diagram
 * .ui.properties.sections.AbstractBasicTextPropertySection)</p>
 * 
 * <p><i>Reviewd by Thomas Schulz, Alexander Weitzmann (Aug. 16, 2011)</i><br/>
 * <i>Reviewed by Dominic Scheurer (Sept. 18, 2011)</i></p>
 *
 * Original version by:
 * 
 * @author Natalia Balaba
 * 
 *         Changed by:
 * @author Malte Viering
 * @author Benjamin Lück
 * @author Dominic Scheurer
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public abstract class ChangedAbstractBasicTextPropertySection extends AbstractModelerPropertySection {

	private static final ResourceBundle PLUGIN_RES_BUNDLE = ResourceBundle.getBundle("plugin");

	private static final int QUERY_TAB_HEIGHT_SHIFT = 35;

	private static final int QUERY_TAB_WIDTH_SHIFT = 45;

	private static final int START_HEIGHT = 15;

	/** styled text widget to display and set value of the property */
	private MarkableStyledText styledTextWidget;

	/** parent parent ... parent composite for the size of the text field */
	private Composite scrolledParent;

	private Composite sectionComposite;

	/**
	 * Preference-store of the java source viewer. Used for highlighting and
	 * text-settings for query.
	 */
	private static final IPreferenceStore SRC_VIEWER_PREFS = PreferenceConstants.getPreferenceStore();

	Listener resizeLinstener = new Listener() {
		@Override
		public void handleEvent(final Event e) {
			updateHeight();
		}
	};

	/**
	 * A helper to listen for events that indicate that a text field has been
	 * changed.
	 */
	private final TextChangeHelper textChangeListener = new TextChangeHelper() {

		private boolean textModified = false;

		/**
		 * Keywords to be marked
		 */
		private final String[] keywords = KeywordReader.readAndParseResourceFile(FrameworkUtil.getBundle(getClass())
				.getSymbolicName(), PLUGIN_RES_BUNDLE.getString("queryKeywordsFile"));

		/**
		 * Pattern to be used to match strings in query including the single quotes
		 */
		private static final String STRING_PATTERN = "'.+?'";

		/**
		 * Performs text highlighting of the query properties tab.
		 * 
		 * @author Dominic Scheurer
		 * @author Alexander Weitzmann
		 */
		private void doSyntaxHighlighting() {
			startNonUserChange();

			// first, set everything to black and normal
			resetStyle();

			// highlight bracket, depending on caret
			highlightBrackets();

			// highlight keywords
			highlightKeywords();

			// highlight strings
			highlightStrings();

			finishNonUserChange();
		}

		/**
		 * Returns a StyleRange that surrounds the character at the given
		 * position with a rectangle.
		 * 
		 * @param position
		 *            The position of the bracket to be highlighted.
		 * @return A StyleRange that highlights bracket at given position
		 */
		private StyleRange getBracketStyle(final int position) {
			final StyleRange bracketStyle = styledTextWidget.getStyleRangeAtOffset(position);

			// surround with rectangle
			bracketStyle.borderStyle = SWT.BORDER_SOLID;

			return bracketStyle;
		}

		/**
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		@Override
		public void handleEvent(final Event event) {
			switch (event.type) {
				case SWT.KeyDown:
					doSyntaxHighlighting();

					textModified = true;
					if (event.character == SWT.CR) {
						getPropertyValueString();
					}
					break;
				case SWT.FocusOut:
					textChanged((Control) event.widget);
					break;
				case SWT.FocusIn:
					doSyntaxHighlighting();
					break;
				case SWT.MouseDown:
					doSyntaxHighlighting();
					break;
				default:
					break;
			}
		}

		/**
		 * Highlights the corresponding bracket relative to caret position
		 */
		private void highlightBrackets() {

			final int offset = styledTextWidget.getCaretOffset();
			// Check if caret is at first position
			// => no bracket highlighting
			if (offset == 0) {
				return;
			}

			final int size = styledTextWidget.getCharCount();

			final char currentChar = styledTextWidget.getText().charAt(offset - 1);

			// mark closing or opening bracket
			markCorrespondingBrackets(offset, size, currentChar);

		}

		/*
		 * Brackets in strings will be ignored (purpose of: textWidget.isPositionMarked(i))
		 */
		private void markCorrespondingBrackets(final int offset, final int size, final char currentChar) {
			if (currentChar == '(' && !styledTextWidget.isPositionMarked(offset - 1)) {
				markCorrespondingClosingBracket(offset, size);
			} else if (currentChar == ')' && offset > 1 && !styledTextWidget.isPositionMarked(offset - 1)) {
				markCorrespondingOpeningBracket(offset);
			}
		}

		private void markCorrespondingClosingBracket(final int offset, final int size) {
			int furtherOpeningBrackets = 0;
			for (int i = offset; i < size; i++) {
				if (styledTextWidget.getText().charAt(i) == '(' && !styledTextWidget.isPositionMarked(i)) {
					furtherOpeningBrackets++;
				}

				if (styledTextWidget.getText().charAt(i) == ')' && furtherOpeningBrackets == 0 && !styledTextWidget.isPositionMarked(i)) {
					// highlight corresponding bracket
					styledTextWidget.setStyleRange(getBracketStyle(i));
					return;
				}

				if (styledTextWidget.getText().charAt(i) == ')' && !styledTextWidget.isPositionMarked(i)) {
					furtherOpeningBrackets--;
				}
			}
		}

		private void markCorrespondingOpeningBracket(final int offset) {
			int furtherClosingBrackets = 0;
			for (int i = offset - 2; i >= 0; i--) {
				if (styledTextWidget.getText().charAt(i) == ')' && !styledTextWidget.isPositionMarked(i)) {
					furtherClosingBrackets++;
				}

				if (styledTextWidget.getText().charAt(i) == '(' && furtherClosingBrackets == 0 && !styledTextWidget.isPositionMarked(i)) {
					// highlight corresponding bracket
					styledTextWidget.setStyleRange(getBracketStyle(i));
					return;
				}

				if (styledTextWidget.getText().charAt(i) == '(' && !styledTextWidget.isPositionMarked(i)) {
					furtherClosingBrackets--;
				}
			}
		}

		/**
		 * Highlights all keywords and strings.
		 */
		private void highlightKeywords() {
			final String targetText = styledTextWidget.getText();
			Pattern keywordPattern = null;
			Matcher keywordMatcher = null;

			final StyleRange styleRange = prepareKeywordsStyleRange();

			// highlight keywords
			for (final String str : keywords) {
				keywordPattern = Pattern.compile(String.format("\\b%s\\b", str));
				keywordMatcher = keywordPattern.matcher(targetText);

				while (keywordMatcher.find()) {
					styleRange.start = keywordMatcher.start();
					styleRange.length = keywordMatcher.end() - keywordMatcher.start();

					styledTextWidget.setStyleRange(styleRange);
				}
			}
		}

		private StyleRange prepareKeywordsStyleRange() {
			final boolean bold = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_KEYWORD_BOLD);
			final boolean italic = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_KEYWORD_ITALIC);

			final StyleRange styleRange = new StyleRange();
			styleRange.fontStyle = SWT.NORMAL;
			if (bold) {
				styleRange.fontStyle |= SWT.BOLD;
			}
			if (italic) {
				styleRange.fontStyle |= SWT.ITALIC;
			}
			styleRange.strikeout = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_KEYWORD_STRIKETHROUGH);
			styleRange.underline = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_KEYWORD_UNDERLINE);
			styleRange.foreground = JavaUI.getColorManager().getColor(PreferenceConstants.EDITOR_JAVA_KEYWORD_COLOR);
			return styleRange;
		}

		/**
		 * Highlights all strings
		 */
		private void highlightStrings() {
			final Matcher stringMatcher = Pattern.compile(STRING_PATTERN, Pattern.DOTALL).matcher(styledTextWidget.getText());

			final StyleRange styleRange = prepareStringsStyleRange();

			// remove all string-markings for {@link #highlightBrackets}
			styledTextWidget.unmarkAllPositions();

			// highlight strings
			while (stringMatcher.find()) {
				styleRange.start = stringMatcher.start() + 1;
				styleRange.length = stringMatcher.end() - styleRange.start - 1;

				styledTextWidget.setStyleRange(styleRange);

				// mark string-locations for {@link #highlightBrackets}
				for (int i = stringMatcher.start(); i < stringMatcher.end(); ++i) {
					styledTextWidget.markPosition(i);
				}
			}
		}

		private StyleRange prepareStringsStyleRange() {
			final boolean bold = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_STRING_BOLD);
			final boolean italic = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_STRING_ITALIC);

			final StyleRange styleRange = new StyleRange();
			styleRange.fontStyle = SWT.NORMAL;
			if (bold) {
				styleRange.fontStyle |= SWT.BOLD;
			}
			if (italic) {
				styleRange.fontStyle |= SWT.ITALIC;
			}
			styleRange.strikeout = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_STRING_STRIKETHROUGH);
			styleRange.underline = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_STRING_UNDERLINE);
			styleRange.foreground = JavaUI.getColorManager().getColor(PreferenceConstants.EDITOR_STRING_COLOR);
			return styleRange;
		}

		/**
		 * Resets the whole text style in the StyledText component to its "normal" state.
		 */
		private void resetStyle() {
			final Color foreground = JavaUI.getColorManager().getColor(PreferenceConstants.EDITOR_JAVA_DEFAULT_COLOR);
			final Color background = EditorsUI.getSharedTextColors().getColor(
					PreferenceConverter.getColor(EditorsUI.getPreferenceStore(), AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND));

			final boolean bold = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_DEFAULT_BOLD);
			final boolean italic = SRC_VIEWER_PREFS.getBoolean(PreferenceConstants.EDITOR_JAVA_DEFAULT_ITALIC);

			int fontstyle = SWT.NORMAL;
			if (bold) {
				fontstyle |= SWT.BOLD;
			}
			if (italic) {
				fontstyle |= SWT.ITALIC;
			}

			final StyleRange normalStyle = new StyleRange(0, styledTextWidget.getCharCount(), foreground, background, fontstyle);
			styledTextWidget.setStyleRange(normalStyle);
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
	 * @return - the default implementation returns contents of the text widget
	 *         as a new value for the property. Subclasses can could be
	 *         override.
	 */
	protected final Object computeNewPropertyValue() {
		return getTextWidget().getText();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse
	 * .swt.widgets.Composite,
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
		data.height = START_HEIGHT;
		st.setLayoutData(data);

		st.setBackground(EditorsUI.getSharedTextColors().getColor(
				PreferenceConverter.getColor(EditorsUI.getPreferenceStore(), AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)));

		if (isReadOnly()) {
			st.setEditable(false);
		}

		return st;
	}

	/**
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
		styledTextWidget = createTextWidget(sectionComposite);
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
		return textChangeListener;
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
	 * @return - array of strings where each describes a property name one per
	 *         property. The strings will be used to calculate common indent
	 *         from the left
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
		return styledTextWidget;
	}

	private int getWidth() {
		return scrolledParent.getSize().x - QUERY_TAB_WIDTH_SHIFT;
	}

	/**
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
		if (styledTextWidget != null) {
			styledTextWidget.setText(getPropertyValueString());
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
		for (Object o : getEObjectList()) {
			final EObject next = (EObject) o;
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