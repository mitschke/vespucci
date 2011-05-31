package de.tud.cs.st.Lyrebird.recorder;



import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IStartup;

import de.tud.cs.st.Lyrebird.recorder.changeListener.ResourceChangeListener;

/**
 * This class register a ResourceChangeListener on the workspace
 * 
 * @author Malte V
 */
public class StartUp implements IStartup {
	public static final String PLUGIN_ID = "SAEClassFileLogger";
	@Override
	public void earlyStartup() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new ResourceChangeListener();
		workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE); //63

	}

}
