/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
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
 *   - Neither the name of the Software Engineering Group or Technische 
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

package de.tud.cs.st.vespucci.versioning;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;

import de.tud.cs.st.vespucci.versioning.versions.VespucciVersionTemplate;

/**
 * Builds the version chain from the Vespucci versions modeled
 * in the package de.tud.cs.st.vespucci.versioning.versions.
 * 
 * @author Dominic Scheurer
 */
public class VespucciVersionChain {
	/**
	 * Stores the ordered list of Versions.
	 */
	List<VespucciVersionTemplate> versionChain = null;
	
	/**
	 * Default constructor which initializes the version chain.
	 */
	public VespucciVersionChain() {
		versionChain = new LinkedList<VespucciVersionTemplate>();
		
		versionChain.add(VespucciVersionTemplate.CURRENT_VERSION);
		while (versionChain.get(0).getPredecessor() != null) {
			versionChain.add(0, versionChain.get(0).getPredecessor());
		}
	}
	
	/**
	 * @param file File to check.
	 * @return The Vespucci version of this file.
	 */
	public VespucciVersionTemplate getVersionOfFile(IFile file) {
		for (VespucciVersionTemplate version : versionChain) {
			if (version.fileIsOfThisVersion(file)) {
				return version;
			}
		}
		
		return null;
	}
}
