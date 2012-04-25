package de.tud.cs.st.vespucci.view.checked_diagrams;
import java.util.Set;

import org.eclipse.core.resources.IFile;

/**
 * An interface for declaring a update method for checked diagram IFiles 
 * See ExtensionPoint de.tud.cs.st.vespucci.view.checked_diagrams.checkedDiagramsObserver
 * 
 * @author Patrick Gottschaemmer
 * @author Olav Lenz
 */

public interface ICheckedDiagramsObserver {
	
	public void updateCheckedDiagrams(Set<IFile> diagramFiles);

}
