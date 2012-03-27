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
package de.tud.cs.st.vespucci.model.adapters;

import java.util.HashSet;
import java.util.Set;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * Implementation of an IEnsembleAdapter
 * @author Robert Cibulla
 *
 */
public class EnsembleAdapter implements IEnsemble {
	
	/**
	 * The adaptee
	 */
	private AbstractEnsemble adaptee;
	
	/**
	 * The inner IEnsembles
	 */
	private Set<IEnsemble> innerEnsembles;
	
	/**
	 * The parent
	 */
	private IEnsemble parent;
	
	/**
	 * The source IConstraints
	 */
	private Set<IConstraint> sourceConnections;
	
	/**
	 * The target IConstraints
	 */
	private Set<IConstraint> targetConnections;

	/**
	 * Constructor
	 * @param adaptee
	 */
	public EnsembleAdapter(AbstractEnsemble adaptee) {
		super();
		this.adaptee = adaptee;
	}
	
	public IEnsemble getParent() {
		return parent;
	}

	public String getDescription() {
		return adaptee.getDescription();
	}

	public String getName() {
		return adaptee.getName();
	}

	public String getQuery() {
		return adaptee.getQuery();
	}

	public Set<IConstraint> getSourceConnections() {
		return sourceConnections;
	}

	public Set<IConstraint> getTargetConnections() {
		return targetConnections;
	}

	public Set<IEnsemble> getInnerEnsembles() {
		return innerEnsembles;
	}

	/**
	 * Getter for the adaptee
	 * @return the adaptee
	 */
	public AbstractEnsemble getAdaptee() {
		return adaptee;
	}
	
	/**
	 * Initialize this object
	 */
	protected void init(){
		initInnerIEnsembles();
		initParent();
		initSourceConnections();
		initTargetConnections();
		
		//Initialize all inner ensembles:
		if(innerEnsembles != null){
			for(IEnsemble inner_ens : innerEnsembles){
				((EnsembleAdapter)inner_ens).init();
			}
		}
	}
	
	/**
	 * initialize innerEnsembles
	 */
	private void initInnerIEnsembles(){
		Set<IEnsemble> innerEnsembles = new HashSet<IEnsemble>();
		if(adaptee instanceof Ensemble){
			for(AbstractEnsemble ens : ((Ensemble)adaptee).getEnsembles()){
				IEnsemble i_ens;
				if((i_ens = AdapterRegistry.getEnsembleAdapter(ens)) != null){
					innerEnsembles.add(i_ens);
				}
			}
		}
		this.innerEnsembles = innerEnsembles;
	}
	
	/**
	 * set parent
	 */
	private void initParent() {
		if (adaptee.eContainer() instanceof AbstractEnsemble) {
			this.parent = AdapterRegistry
					.getEnsembleAdapter((AbstractEnsemble) adaptee.eContainer());
		} else
			this.parent = null;
	}
	
	/**
	 * initialize sourceConnections
	 */
	private void initSourceConnections(){
		Set<IConstraint> sourceConnections = new HashSet<IConstraint>();
		for(Connection con: adaptee.getSourceConnections()){
			IConstraint i_cons;
			if((i_cons = AdapterRegistry.getConstraintAdapter(con)) != null){
				sourceConnections.add(i_cons);
			}
		}
		this.sourceConnections = sourceConnections;
	}
	
	/**
	 * initialize sourceConnections
	 */
	private void initTargetConnections(){
		Set<IConstraint> targetConnections = new HashSet<IConstraint>();
		for(Connection con: adaptee.getTargetConnections()){
			IConstraint i_cons;
			if((i_cons = AdapterRegistry.getConstraintAdapter(con)) != null){
				targetConnections.add(i_cons);
			}
		}
		this.targetConnections = targetConnections;
	}
}
