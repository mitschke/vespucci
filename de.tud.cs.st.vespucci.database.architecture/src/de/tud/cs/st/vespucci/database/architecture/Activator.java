package de.tud.cs.st.vespucci.database.architecture;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.database.architecture"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private ArchitectureDatabaseProvider databaseProvider;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	/**
	 * @return the databaseProvider
	 */
	public ArchitectureDatabaseProvider getDatabaseProvider() {
		return databaseProvider;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		databaseProvider = new ArchitectureDatabaseProvider();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		databaseProvider = null;
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
