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

import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * Implementation of an IArchitectureModelAdapter
 * @author RobertCibulla
 *
 */
public class ArchitectureModelAdapter implements IArchitectureModel {
	
	/**
	 * The adaptee
	 */
	private ArchitectureModel adaptee;

	/**
	 * the {@link IEnsemble} contained by the {@link ArchitectureModelAdapter}
	 */
	private Set<IEnsemble> iEnsembles;
	
	/**
	 * the {@link IConstraint}'s contained by the {@link ArchitectureModelAdapter}
	 */
	private Set<IConstraint> iConstraints;
	
	/**
	 * Constructor
	 * @param adaptee
	 */
	public ArchitectureModelAdapter(ArchitectureModel adaptee){
		super();
		this.adaptee = adaptee;
	}
	
	public Set<IEnsemble> getEnsembles() {
		return iEnsembles;
	}

	public Set<IConstraint> getConstraints() {
		return iConstraints;
	}

	public String getName() {
		if(EcoreUtil.getURI(adaptee).segmentCount() > 1)
			return EcoreUtil.getURI(adaptee).segment(1);
		return null;
	}

	/**
	 * @return the adaptee
	 */
	public ArchitectureModel getAdaptee() {
		return adaptee;
	}
	
	public void init(){
		initIEnsembles();
		initIConstraints();
		for(IEnsemble ens : iEnsembles){
			((EnsembleAdapter)ens).init();
		}
		for(IConstraint cons : iConstraints){
			((ConstraintAdapter)cons).init();
		}
	}
	

	/**
	 * initialize iEnsembles
	 */
	private void initIEnsembles() {
		Set<IEnsemble> iEnsembles = new HashSet<IEnsemble>();
		for (AbstractEnsemble ens : ((ArchitectureModel) adaptee).getEnsembles()) {
			IEnsemble i_ens;
			if ((i_ens = AdapterRegistry.getEnsembleAdapter(ens)) != null) {
				iEnsembles.add(i_ens);
			}
		}
		this.iEnsembles = iEnsembles;
	}
	
	/**
	 * initialize iConstraints
	 */
	private void initIConstraints() {
		Set<IConstraint> iConstraints = new HashSet<IConstraint>();
		for (Connection con : adaptee.getConnections()){
			IConstraint i_cons;
			if((i_cons = AdapterRegistry.getConstraintAdapter(con)) != null){
				iConstraints.add(i_cons);
			}
		}
		this.iConstraints = iConstraints;
	}
}

