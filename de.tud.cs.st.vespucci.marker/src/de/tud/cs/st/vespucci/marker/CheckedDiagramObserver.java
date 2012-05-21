package de.tud.cs.st.vespucci.marker;

import java.util.Set;

import org.eclipse.core.resources.IFile;

import de.tud.cs.st.vespucci.view.checked_diagrams.ICheckedDiagramsObserver;

public class CheckedDiagramObserver implements ICheckedDiagramsObserver {

	@Override
	public void updateCheckedDiagrams(Set<IFile> diagramFiles) {
		Marker.setCurrentCheckedDiagrams(diagramFiles);
	}

}
