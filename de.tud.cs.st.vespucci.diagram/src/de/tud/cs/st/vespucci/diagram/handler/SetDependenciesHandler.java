/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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

package de.tud.cs.st.vespucci.diagram.handler;

import java.util.Arrays;

/**
 * Handler for the "Set Dependency"-command issued from the "Toggle Dependency" menu.<br>
 * "Toggle Dependency" is found in the popup-menu for constraints (connections in editor) under
 * "Edit Constraint".
 * 
 * @author Alexander Weitzmann
 * @version 0.3
 */
public class SetDependenciesHandler extends ToggleDependenciesSuperHandler {
	/**
	 * Creates a new string-array, with same strings as given original, but with the "transformString" added. If
	 * given transformString is already present, it will not be added again i.e. the original will be returned.<br>
	 * 
	 * @param original
	 *            The original array to be copied.
	 * @param transformString
	 *            String to add.
	 * @return The given array with transformString added, if setString was not already present.
	 */
	@Override
	String[] transformedCopy(String[] original, String transformString) {
		// Determine if string is present.
		for(String str: original){
			if(str.equals(transformString)){
				return original;
			}
		}
		// Add setString
		String[] result = Arrays.copyOf(original, original.length + 1);
		result[original.length] = transformString;
		
		return result;
	}

}
