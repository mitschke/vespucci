/******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;

/**
 * EditPolicy for creating new ensembles in a Vespucci-diagram from java
 * elements.
 * 
 * @author Ralf Mitschke
 * @author Malte Viering
 */
public final class JavaElementDiagramDropPolicy extends CreationEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy#getCommand
	 * (org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request))
			return null;
		return null;
	}

	/**
	 * This class understands request of the type REQ_DROPNEWENSEMBLE
	 */
	@Override
	public boolean understandsRequest(final Request request) {
		return IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE.equals(request
				.getType());
	}

}
