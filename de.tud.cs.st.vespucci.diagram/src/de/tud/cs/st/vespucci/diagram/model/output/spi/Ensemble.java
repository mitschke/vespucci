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

import org.eclipse.emf.ecore.EObject;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * 
 * @author Patrick Gottschaemmer
 * @author Olav Lenz
 */
public class Ensemble implements IEnsemble {

	private de.tud.cs.st.vespucci.vespucci_model.Shape shape;

	private Set<IEnsemble> innerEnsemble;
	private Set<IConstraint> sourceConnection;
	private Set<IConstraint> targetConnection;

	public Ensemble(de.tud.cs.st.vespucci.vespucci_model.Shape ensemble) {
		this.shape = ensemble;
	}

	@Override
	public Set<IEnsemble> getInnerEnsembles() {
		if (innerEnsemble == null) {
			innerEnsemble = getInnerEnsemble();
		}
		return innerEnsemble;
	}

	private Set<IEnsemble> getInnerEnsemble() {
		if (shape instanceof de.tud.cs.st.vespucci.vespucci_model.Ensemble) {
			de.tud.cs.st.vespucci.vespucci_model.Ensemble ensemble = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) shape;

			Set<IEnsemble> connections = new HashSet<IEnsemble>();
			for (de.tud.cs.st.vespucci.vespucci_model.Shape shape : ensemble
					.getShapes()) {
				connections.add(ConversionUtils.createEnsemble(shape));
			}
			return connections;
		}
		return new HashSet<IEnsemble>();
	}

	@Override
	public String getDescription() {
		return shape.getDescription();
	}

	@Override
	public String getName() {
		return shape.getName();
	}

	@Override
	public String getQuery() {
		return shape.getQuery();
	}

	@Override
	public Set<IConstraint> getSourceConnections() {
		if (sourceConnection == null) {
			sourceConnection = ConversionUtils.getIConnections(shape
					.getSourceConnections());
		}
		return sourceConnection;
	}

	@Override
	public Set<IConstraint> getTargetConnections() {
		if (targetConnection == null) {
			targetConnection = ConversionUtils.getIConnections(shape
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
		return "Ensemble(" + getName() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		//result = prime * result + ((innerEnsemble == null) ? 0 : innerEnsemble.hashCode());
		if (shape != null && getName() == null)
			System.out.println("help");
		result = prime * result + ((shape == null) ? 0 : getName().hashCode());
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
		if (!IEnsemble.class.isAssignableFrom(obj.getClass()))
			return false;
		IEnsemble other = (IEnsemble) obj;
		if (other.getName() == null)
			return false;
		if (!other.getName().equals(this.shape.getName()))
			return false;
		//if (other.getInnerEnsembles() == null)
		//	return false;
		//if (!other.getInnerEnsembles().equals(this.getInnerEnsembles()))
		//	return false;
		return true;
	}

	@Override
	public IEnsemble getParent() {
		EObject container = shape.eContainer();
		if( container == null)
			return null;
		return (container instanceof Shape) ? ConversionUtils.createEnsemble((Shape)container) : null;
	}

}