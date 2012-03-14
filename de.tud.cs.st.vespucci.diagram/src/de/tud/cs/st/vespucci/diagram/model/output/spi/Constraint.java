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

import org.eclipse.emf.common.util.EList;

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
		return ConversionUtils.createEnsemble(getInternalSource());
	}

	@Override
	public IEnsemble getTarget() {
		return ConversionUtils.createEnsemble(getInternalTarget());
	}

	private Shape getInternalSource() {
		if (!connection.isTemp())
			return connection.getSource();
		
		// a temp connection has a list of original sources; don't know why a
		// list but it seems to work like a history
		EList<Shape> originalSources = connection.getOriginalSource();
		// we can be temp but use the concrete soure and only remember the original targets
		if(originalSources.size() == 0)
			return connection.getSource();
		return originalSources.get(originalSources.size() - 1);
	}

	private Shape getInternalTarget() {
		if (!connection.isTemp())
			return connection.getTarget();
		// a temp connection has a list of original targets; don't know why a
		// list but it seems to work like a history
		EList<Shape> originalTargets = connection.getOriginalTarget();
		// we can be temp but use the concrete target and only remember the original sources
		if(originalTargets.size() == 0)
			return connection.getTarget();
		return originalTargets.get(originalTargets.size() - 1);
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

			Shape source = getInternalSource();
			if (source != null)
				result = prime
						* result
						+ ((source.getName() == null) ? 0 : source.getName()
								.hashCode());
			Shape target = getInternalSource();
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
			return other.connection == null;
		}

		// compare kinds
		if (getDependencyKind() == null && other.getDependencyKind() != null)
			return false;
		if (getDependencyKind() != null
				&& !getDependencyKind().equals(other.getDependencyKind()))
			return false;

		// compare source names
		if (getInternalSource() == null && other.getInternalSource() != null)
			return false;
		if (getInternalSource() != null) {
			if (getInternalSource().getName() == null
					&& other.getInternalSource().getName() != null)
				return false;
			if (getInternalSource().getName() != null
					&& !getInternalSource().getName().equals(
							other.getInternalSource().getName()))
				return false;
		}

		// compare target names
		if (getInternalTarget() == null && other.getInternalTarget() != null)
			return false;
		if (getInternalTarget() != null) {
			if (getInternalTarget().getName() == null
					&& other.getInternalTarget().getName() != null)
				return false;
			if (getInternalTarget().getName() != null
					&& !getInternalTarget().getName().equals(
							other.getInternalTarget().getName()))
				return false;
		}
		return true;
	}

}
