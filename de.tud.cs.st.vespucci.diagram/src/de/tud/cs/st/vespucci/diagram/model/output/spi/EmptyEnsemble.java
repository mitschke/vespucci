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
package de.tud.cs.st.vespucci.diagram.model.output.spi;

import java.util.HashSet;
import java.util.Set;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;

public class EmptyEnsemble implements IEnsemble {

	private static Set<IEnsemble> noEnsembles = new HashSet<IEnsemble>(0);

	private static String emptyEnsembleName = "@EmptyEnsembleName";

	private static String emptyQuery = "empty";

	private Dummy emptyEnsemble;

	private Set<IConstraint> sourceConnection;

	private Set<IConstraint> targetConnection;

	public EmptyEnsemble(Dummy emptyEnsemble) {
		this.emptyEnsemble = emptyEnsemble;
	}

	@Override
	public IEnsemble getParent() {
		return null;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getName() {
		return emptyEnsembleName;
	}

	@Override
	public String getQuery() {
		return emptyQuery;
	}

	@Override
	public Set<IEnsemble> getInnerEnsembles() {
		return noEnsembles;
	}

	@Override
	public Set<IConstraint> getSourceConnections() {
		if (sourceConnection == null) {
			sourceConnection = ConversionUtils.getIConnections(emptyEnsemble
					.getSourceConnections());
		}
		return sourceConnection;
	}

	@Override
	public Set<IConstraint> getTargetConnections() {
		if (targetConnection == null) {
			targetConnection = ConversionUtils.getIConnections(emptyEnsemble
					.getTargetConnections());
		}
		return targetConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmptyEnsemble";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * emptyEnsembleName.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
