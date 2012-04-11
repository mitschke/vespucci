/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.marker;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Receive IViolationViews and delegate them to ViolationManager and
 * ViolationSummaryManger, which mark and manage IViolations and IViolationSummary
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
public class Marker implements IResultProcessor {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";

	protected static void processException(Exception e) {
		final IStatus is = new Status(IStatus.ERROR, Marker.PLUGIN_ID,
				e.getMessage(), e);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}

	private ViolationManager violationManager;
	private ViolationSummaryManager violationSummaryManager;

	public Marker() {
		violationManager = new ViolationManager();
		violationSummaryManager = new ViolationSummaryManager();
	}

	@Override
	public void processResult(Object result, IFile file) {
		IViolationView violationView = Util.adapt(result, IViolationView.class);
		IProject project = file.getProject();

		long start = System.nanoTime();
		if (violationView != null) {
			violationManager.setView(violationView, project);
			violationSummaryManager
					.setView(violationView.getSummaryView(), project);
		}
		long taken = System.nanoTime() - start;
		System.out
				.println("time to create markers       : "
						+ TimeUnit.SECONDS.convert(taken, TimeUnit.NANOSECONDS)
						+ " ms");

	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationView.class.equals(resultClass);
	}

}
