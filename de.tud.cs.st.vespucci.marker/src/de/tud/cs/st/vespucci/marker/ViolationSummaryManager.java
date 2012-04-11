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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

/**
 * Explicit implementation of DataViewObserver for IViolationSummarys.
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
public class ViolationSummaryManager extends DataViewObserver<IViolationSummary>{

	private Map<IViolationSummary, IMarker> marks;
	private DescriptionGenerator descGen;

	public ViolationSummaryManager(){
		marks = new HashMap<IViolationSummary, IMarker>();
		descGen = DescriptionGenerator.getInstance();
	}

	private String createViolationSummaryDescription(IViolationSummary element) {
		return descGen.getDescription(element);
	}

	@Override
	public void added(IViolationSummary element) {
		if (!marks.containsKey(element)){
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(Path.fromPortableString(element.getDiagramFile()));
			IMarker marker = MarkingUtilities.markIFile(file, createViolationSummaryDescription(element), IMarker.PRIORITY_NORMAL);
			if (marker != null){
				marks.put(element, marker);
			}
		}
	}

	@Override
	public void deleted(IViolationSummary element) {
		if (marks.containsKey(element)){
			IMarker marker = marks.get(element);
			MarkingUtilities.deleteMarker(marker);
			marks.remove(element);
		}
	}

}
