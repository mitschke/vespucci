/* License (BSD Style License):
 * Copyright (c) 2011
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Technology Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.change.listener;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.change.observation.ChangeDeltaVisitor;
import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;

/**
 * Listener that implements the IResourceChangeListener interface. This listener
 * notifies clients of changes in eclipse class files and vespucci diagrams
 * 
 * @author Ralf Mitschke
 */
public class ResourceChangeListener implements IResourceChangeListener {

	private ChangeDeltaVisitor visitor = null;

	public ResourceChangeListener(VespucciChangeProvider changeProvider) {
		visitor = new ChangeDeltaVisitor(changeProvider);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// POST_CHANGE:
		// indicating an after-the-fact report of creations, deletions, and
		// modifications
		// to one or more resources expressed as a hierarchical resource delta
		// as returned by getDelta.
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;
		
		IResourceDelta delta = event.getDelta();
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unexpected core exception", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
	}

}
