package de.tud.cs.st.vespucci.diagram.processing;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;

public class Adapted {

	@SuppressWarnings("unchecked")
	public static <A> A getAdapted(Object adaptable, Class<A> targetClass) {

		A target = null;

		if (targetClass.isInstance(adaptable)) {
			return (A) adaptable;
		}

		if (adaptable instanceof IAdaptable) {
			target = (A) ((IAdaptable) adaptable).getAdapter(targetClass);
		}

		if (target == null) {
			IAdapterManager manager = Platform.getAdapterManager();
			target = (A) manager.getAdapter(adaptable, targetClass);
		}

		return target;
	}

}
