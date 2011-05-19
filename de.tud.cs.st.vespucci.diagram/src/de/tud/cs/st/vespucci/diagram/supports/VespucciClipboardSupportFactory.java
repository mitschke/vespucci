package de.tud.cs.st.vespucci.diagram.supports;

import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;

public class VespucciClipboardSupportFactory extends PasteChildOperation {

	protected VespucciClipboardSupportFactory(
			PasteChildOperation mainChildPasteProcess) {
		super(mainChildPasteProcess);
	}

}
