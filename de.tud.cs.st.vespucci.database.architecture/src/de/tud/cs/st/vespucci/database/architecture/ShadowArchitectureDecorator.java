package de.tud.cs.st.vespucci.database.architecture;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

/**
 * A Decorator that will filter the shadow file path out of the name of the architecture model 
 * @author Ralf Mitschke
 */
public class ShadowArchitectureDecorator implements IArchitectureModel{

	private IArchitectureModel delegate;
	
	private IPath shadowLocation;

	public ShadowArchitectureDecorator(IArchitectureModel delegate, IPath shadowLocation) {
		this.delegate = delegate;
		this.shadowLocation = shadowLocation;
	}

	/**
	 * @return
	 * @see de.tud.cs.st.vespucci.model.IArchitectureModel#getEnsembles()
	 */
	public Set<IEnsemble> getEnsembles() {
		return delegate.getEnsembles();
	}

	/**
	 * @return
	 * @see de.tud.cs.st.vespucci.model.IArchitectureModel#getConstraints()
	 */
	public Set<IConstraint> getConstraints() {
		return delegate.getConstraints();
	}

	/**
	 * @return
	 * @see de.tud.cs.st.vespucci.model.IArchitectureModel#getName()
	 */
	public String getName() {
		IPath delegatePath = Path.fromPortableString(delegate.getName());
		IPath actualPath = delegatePath.removeFirstSegments(delegatePath.matchingFirstSegments(shadowLocation));
		return actualPath.makeAbsolute().toPortableString();
	}
}
