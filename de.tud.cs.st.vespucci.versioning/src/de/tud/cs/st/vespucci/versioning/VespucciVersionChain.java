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

package de.tud.cs.st.vespucci.versioning;

import java.util.Arrays;
import java.util.TreeSet;

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
	TreeSet<VespucciVersionTemplate> versionChain = null;
	
	/**
	 * Stores the Singleton instance of this class.
	 */
	private static VespucciVersionChain INSTANCE = null;
	
	/**
	 * Initializes the version chain.
	 */
	private VespucciVersionChain() {
		versionChain = new TreeSet<VespucciVersionTemplate>();
		
		versionChain.add(VespucciVersionTemplate.NEWEST_VERSION);
		while (versionChain.first().hasPredecessor()) {
			versionChain.add(versionChain.first().getPredecessor());
		}
	}
	
	/**
	 * @return The Singleton instance of this class.
	 */
	public static VespucciVersionChain getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VespucciVersionChain();
		}
		
		return INSTANCE;
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
	
	/**
	 * @param originalVersion The version for which to find the successor.
	 * @return The successor of the given version of null if there is none.
	 */
	public VespucciVersionTemplate getNextVersion(VespucciVersionTemplate originalVersion) {
		VespucciVersionTemplate[] listChain = new VespucciVersionTemplate[versionChain.size()];
		listChain = versionChain.toArray(listChain);
		
		int versionPosition = Arrays.binarySearch(listChain, originalVersion);
		if (versionPosition < listChain.length - 1 &&
			versionPosition >= 0) {
			return listChain[versionPosition + 1];
		}
		
		return null;
	}
	
	/**
	 * @param file The file for which to return the next version
	 * @return The successor of the given version of null if there is none.
	 */
	public VespucciVersionTemplate getNextVersionOfFile(IFile file) {
		return getNextVersion(getVersionOfFile(file));
	}
}
