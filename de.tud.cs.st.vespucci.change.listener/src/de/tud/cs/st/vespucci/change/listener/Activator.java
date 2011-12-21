package de.tud.cs.st.vespucci.change.listener;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.change.listener"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	protected ResourceChangeListener changeListener;
	
	protected VespucciChangeProvider changeProvider;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * @return the changeProvider
	 */
	public VespucciChangeProvider getChangeProvider() {
		return changeProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		changeProvider = new VespucciChangeProvider();
		changeListener = new ResourceChangeListener(changeProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		changeProvider = null;
		changeListener = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
