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

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class Constraint implements IConstraint {

	private de.tud.cs.st.vespucci.vespucci_model.Connection connection;

	public Constraint(de.tud.cs.st.vespucci.vespucci_model.Connection connection) {
		this.connection = connection;
	}

	@Override
	public String getDependencyKind() {
		return connection.getName();
	}

	@Override
	public IEnsemble getSource() {
		return ConversionUtils.createEnsemble(connection.getSource());
	}

	@Override
	public IEnsemble getTarget() {
		return ConversionUtils.createEnsemble(connection.getTarget());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Constraint(" + getSource().getName() + ", "
				+ getTarget().getName() + ")";
	}

	Shape source = connection.getSource();

	Shape target = connection.getSource();

	String kind = connection.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (connection != null) {

			String kind = connection.getName();

			result = prime * result + ((kind == null) ? 0 : kind.hashCode());

			Shape source = connection.getSource();
			if (source != null)
				result = prime
						* result
						+ ((source.getName() == null) ? 0 : source.getName()
								.hashCode());
			Shape target = connection.getSource();
			if (target != null)
				result = prime
						* result
						+ ((target.getName() == null) ? 0 : target.getName()
								.hashCode());
		}
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
		Constraint other = (Constraint) obj;
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else {
			Shape source = connection.getSource();
			Shape target = connection.getSource();
			String kind = connection.getName();
			if (kind == null) {
				if (other.kind != null)
					return false;
			} else if (!kind.equals(other.kind))
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.getName().equals(other.source.getName()))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.getName().equals(other.target.getName()))
				return false;
		}
		return true;
	}

}
