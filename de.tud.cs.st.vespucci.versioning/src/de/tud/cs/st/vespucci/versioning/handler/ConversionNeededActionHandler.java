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

package de.tud.cs.st.vespucci.versioning.handler;

import java.util.Map;

import org.eclipse.core.resources.IFile;

import de.tud.cs.st.vespucci.proxy.AbstractActionHandler;
import de.tud.cs.st.vespucci.proxy.IActionHandler;
import de.tud.cs.st.vespucci.versioning.VespucciVersionChain;

/**
 * Action handler to determine whether or not an sad file needs
 * a conversion (i.e. is of an older than the current version).
 * 
 * @author Dominic Scheurer
 */
public class ConversionNeededActionHandler extends AbstractActionHandler implements IActionHandler {

	/**
	 * @param variables Expects a key "file" with an IFile object, pointing to an sad file.
	 * @return true if the given file is of an old version.
	 */
	@Override
	public Object run(Map<String, ? extends Object> variables) {
		VespucciVersionChain versionChain = VespucciVersionChain.getInstance();
		
		if (variables.get("file") != null &&
			(variables.get("file") instanceof IFile)) {
			IFile file = (IFile)variables.get("file");
			return !versionChain.getVersionOfFile(file).isNewestVersion();
		} else {
			throw new IllegalArgumentException(
					"run method of ConversionNeededActionHandler exptects " +
					"the given variables Map to contain a key \"file\" pointing " +
					"to an sad IFile");
		}
	}

}
