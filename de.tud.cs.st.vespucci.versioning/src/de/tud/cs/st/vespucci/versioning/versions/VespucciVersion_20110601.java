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

package de.tud.cs.st.vespucci.versioning.versions;

import org.eclipse.emf.common.util.URI;

/**
 * The second version of Vespucci, since June 1st, 2011.
 * 
 * @author Dominic Scheurer
 */
public class VespucciVersion_20110601 extends VespucciVersionTemplate {
	
	/** URI to the QVTO transformation code for the model part */
	private final URI MODEL_TRANSF_URI = URI.createURI(
		"platform:/plugin/de.tud.cs.st.vespucci.versioning/transformations/migrate_v0_to_v1.model.qvto");
	
	/** URI to the QVTO transformation code for the diagram part */
	private final URI NOTATION_TRANSF_URI = URI.createURI(
		"platform:/plugin/de.tud.cs.st.vespucci.versioning/transformations/migrate_v0_to_v1.notation.qvto");
	
	@Override
	public String getNamespace() {
		return "http://vespucci.editor/2011-06-01";
	}

	@Override
	public VespucciVersionTemplate getPredecessor() {
		return new VespucciVersion_V0();
	}

	@Override
	public URI getModelQvtoUri() {
		return MODEL_TRANSF_URI;
	}

	@Override
	public URI getDiagramQvtoUri() {
		return NOTATION_TRANSF_URI;
	}

}
