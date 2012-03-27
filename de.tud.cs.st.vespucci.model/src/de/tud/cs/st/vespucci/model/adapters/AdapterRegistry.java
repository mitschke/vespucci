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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.model.util.AdapterDiagramFilter;
import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming;
import de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.Violation;

/**
 * The AdapterRegistry
 * 
 * @author Robert Cibulla
 * 
 */
public class AdapterRegistry {
	private static IArchitectureModel architectureModelAdapter;
	private static Map<AbstractEnsemble, IEnsemble> ensembleMap = new HashMap<AbstractEnsemble, IEnsemble>();
	private static Map<Connection, IConstraint> constraintMap = new HashMap<Connection, IConstraint>();
	private static boolean isFiltered = false;
	private static View sDiagram;
	/**
	 * initialize registry
	 * 
	 * @param rootModel
	 * @param diagram
	 *            set if you want to filter the model for a specific diagram,
	 *            else null.
	 */
	public static void initRegistry(ArchitectureModel rootModel, View diagram) {
		architectureModelAdapter = null;
		ensembleMap.clear();
		constraintMap.clear();
		initModel(rootModel);
		sDiagram = diagram;
		if (sDiagram == null) {
			initEnsembles(rootModel, false);
			initConstraints(rootModel, false);
		} else {
			isFiltered = true;
			AdapterDiagramFilter.init(sDiagram);
			initEnsembles(rootModel, true);
			initConstraints(rootModel, true);
		}
	}

	/**
	 * initialize the architectureModel adapter
	 * 
	 * @param rootModel
	 */
	private static void initModel(ArchitectureModel rootModel) {
		architectureModelAdapter = new ArchitectureModelAdapter(rootModel);
	}

	/**
	 * recursively initialize ensembleMap without filter
	 * 
	 * @param rek
	 */
	private static void initEnsembles(EObject rek, boolean isFiltered) {
		if (rek instanceof ArchitectureModel) {
			List<AbstractEnsemble> ensembles;
			//determine filtered or unfiltered ensembles
			if(!isFiltered){
				ensembles = ((ArchitectureModel) rek)
						.getEnsembles();
			} else {
				ensembles = AdapterDiagramFilter.getEnsemblesFromDiagram(((ArchitectureModel) rek)
						.getEnsembles());
			}
			for (AbstractEnsemble ens : ensembles) {
				ensembleMap.put(ens, new EnsembleAdapter(ens));
				initEnsembles(ens, isFiltered);
			}
		} else if (rek instanceof Ensemble) {
			for (AbstractEnsemble ens : ((Ensemble) rek).getEnsembles()) {
				ensembleMap.put(ens, new EnsembleAdapter(ens));
				initEnsembles(ens, isFiltered);
			}
		} else
			return;
	}
	
	/**
	 * initialize constraintMap without filter
	 * 
	 * @param architectureModel
	 */
	private static void initConstraints(ArchitectureModel architectureModel, boolean isFiltered) {
		List<Connection> connections;
		if(!isFiltered){
			connections = architectureModel.getConnections();
		} else {
			connections = AdapterDiagramFilter.getConnectionsFromDiagram(architectureModel.getConnections());
		}
		for (Connection con : connections) {
			if(con instanceof Violation){
				constraintMap.put(con, new DocumentedViolationAdapter(
						(Violation) con));
			} else if(con instanceof Expected){
				constraintMap.put(con, new ExpectedAdapter((Expected) con));
			} else if(con instanceof GlobalIncoming){
				constraintMap.put(con, new GlobalIncomingAdapter(
						(GlobalIncoming) con));
			} else if (con instanceof GlobalOutgoing){
				constraintMap.put(con, new GlobalOutgoingAdapter(
						(GlobalOutgoing) con));
			} else if(con instanceof InAndOut){
				constraintMap.put(con, new InAndOutAdapter((InAndOut) con));
			} else if(con instanceof Incoming){
				constraintMap.put(con, new IncomingAdapter((Incoming) con));
			} else if(con instanceof NotAllowed){
				constraintMap.put(con, new NotAllowedAdapter((NotAllowed) con));
			} else if(con instanceof Outgoing){
				constraintMap.put(con, new OutgoingAdapter((Outgoing) con));
			} else continue;
		}
	}

	/**
	 * get the {@link EnsembleAdapter} for the given {@link AbstractEnsemble}
	 * 
	 * @param abs_ens
	 * @return IEnsemble - the adapter for the given AbstractEnsemble
	 */
	public static IEnsemble getEnsembleAdapter(AbstractEnsemble abs_ens) {
		return ensembleMap.get(abs_ens);
	}

	/**
	 * get {@link IConstraint} for the given {@link Connection}
	 * 
	 * @param con
	 * @return
	 */
	public static IConstraint getConstraintAdapter(Connection con) {
		return constraintMap.get(con);
	}

	/**
	 * get the {@link ArchitectureModelAdapter}
	 * 
	 * @return
	 */
	public static IArchitectureModel getArchitectureModelAdapter() {
		return architectureModelAdapter;
	}
}
