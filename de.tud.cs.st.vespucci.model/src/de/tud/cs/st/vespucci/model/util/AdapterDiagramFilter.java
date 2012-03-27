/*
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
package de.tud.cs.st.vespucci.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Connection;

/**
 * @author Robert Cibulla
 *
 */
public class AdapterDiagramFilter {
	static HashMap<String, String> ensembleRegistry = new HashMap<String, String>();
	static HashMap<String, String> connectionRegistry = new HashMap<String, String>();

	/**
	 * Initialize both connection- and ensembleRegisty
	 * 
	 * @param diagram
	 */
	public static void init(View diagram) {
		connectionRegistry.clear();
		ensembleRegistry.clear();
		initConnectionRegistry(diagram);
		initEnsembleRegistry(diagram);
	}

	/**
	 * Initializes the connection registry
	 * 
	 * @param diagram
	 */
	private static void initConnectionRegistry(View diagram) {
		if (diagram instanceof Diagram) {
			for (Object edge : ((Diagram) diagram).getEdges()) {
				if (edge instanceof Edge
						&& ((Edge) edge).getElement() instanceof Connection) {
					connectionRegistry.put(
							((Connection) ((Edge) edge).getElement()).getKey(),
							"ok");
				}
			}
		}
	}

	/**
	 * Initializes the ensemble registry
	 * 
	 * @param diagram
	 */
	private static void initEnsembleRegistry(View diagram) {
		if (diagram instanceof Diagram) {
			for (Object ensemble : ((Diagram) diagram).getChildren()) {
				if (ensemble instanceof View
						&& ((View) ensemble).getElement() instanceof AbstractEnsemble) {
					ensembleRegistry.put(
							((AbstractEnsemble) ((View) ensemble).getElement()).getKey(),
							"ok");
				}
			}
		}
	}

	/**
	 * Filters given List of Connections and returns only those existing in the
	 * Diagram. example usage:
	 * AdapterDiagramFilter.getConnectionsFromDiagram(ensembleXXX
	 * .getSourceConnections, diagram)
	 * 
	 * @param conns
	 * @param diagram
	 * @return
	 */
	public static List<Connection> getConnectionsFromDiagram(
			EList<Connection> conns) {
		List<Connection> result = new ArrayList<Connection>();
		for (Connection con : conns) {
			if (connectionRegistry.get(con.getKey()) != null)
				result.add(con);
		}
		return result;
	}

	/**
	 * Filters given List of Ensembles and returns only those existing in the
	 * Diagram.
	 * 
	 * @param ensembles
	 * @param diagram
	 * @return
	 */
	public static List<AbstractEnsemble> getEnsemblesFromDiagram(EList<AbstractEnsemble> ensembles) {
		List<AbstractEnsemble> result = new ArrayList<AbstractEnsemble>();
		for (AbstractEnsemble ens : ensembles) {
			if (ensembleRegistry.get(ens.getKey()) != null)
				result.add(ens);
		}
		return result;
	}

}
