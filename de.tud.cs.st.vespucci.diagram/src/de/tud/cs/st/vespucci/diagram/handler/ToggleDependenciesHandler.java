/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
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
 * Handler for the "Toggle Dependency"-command.<br>
 * "Toggle Dependency" is found in the popup-menu for constraints (connections in editor) under
 * "Edit Constraint".
 * 
 * @author Alexander Weitzmann
 * @version 0.2
 */
public class ToggleDependenciesHandler extends ToggleDependenciesSuperHandler {

	/**
	 * Creates a new string-array, with same strings as given original, but the "transform-string". If
	 * given transform-string is present, it will be removed, otherwise it will be added.<br><br>
	 * <b>NOTE:</b> Only the first found instance of given toggleString will be toggled, even if there
	 * are more - e.g. if the dependency is named "all, create, all" and "all" needs to be toggled
	 * (or removed in this case), then only the first "all" will be deleted!
	 * 
	 * @param original
	 *            The original array to be copied.
	 * @param transformString
	 *            String to add or remove.
	 * @return Toggled string-array. That is: Same array, but with given toggle-string removed or
	 *         added.
	 */
	@Override
	String[] transformedCopy(String[] original, String transformString) {
		boolean stringAbsent = true;

		// Determine if string is present. If so, 'toggleIndex' will be corresponding index.
		int toggleIndex = 0;
		while (toggleIndex < original.length) {
			if (original[toggleIndex].equals(transformString)) {
				stringAbsent = false;
				break;
			}
			++toggleIndex;
		}

		// Represents all dependencies after toggling;
		String[] result;
		if (stringAbsent) {
			// append string
			result = Arrays.copyOf(original, original.length + 1);
			result[original.length] = transformString;
		} else {
			// remove string
			result = new String[original.length - 1];
			for (int i = 0; i < result.length; ++i) {
				if (i >= toggleIndex) {
					// skip string to be toggled
					result[i] = original[i + 1];
				} else {
					result[i] = original[i];
				}
			}
		}
		return result;
	}

}
