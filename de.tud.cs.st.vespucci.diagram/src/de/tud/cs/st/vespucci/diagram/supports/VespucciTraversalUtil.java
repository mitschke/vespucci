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
package de.tud.cs.st.vespucci.diagram.supports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * Util class to provide traversal filters for Connections and Ensembles.
 * @author Robert Cibulla
 *
 */
public class VespucciTraversalUtil {
	
	
	
	/**
	 * Filters given List of Connections and returns only those existing in the Diagram.
	 * example usage: VespucciTraversalUtil.getConnectionsFromDiagram(ensembleXXX.getSourceConnections, diagram)
	 * @param conns
	 * @param diagram
	 * @return
	 */
	public static List getConnectionsFromDiagram(EList<Connection> conns, Diagram diagram){
		List<Connection> result = new ArrayList<Connection>();
		for(Connection con : conns){
			if(isConnectionInDiagram(con, diagram))
				result.add(con);
		}
		return result;
	}
	
	/**
	 * TODO: make recursive!
	 * @param shapes
	 * @param diagram
	 * @return
	 */
	public static List getEnsemblesFromDiagram(EList<Shape> shapes, Diagram diagram){
		List<Shape> result = new ArrayList<Shape>();
		for(Shape shp : shapes){
			if(!isEnsembleInDiagram(shp, diagram))
				result.add(shp);
		}
		return result;
	}
	
	/**
	 * 
	 * @param o
	 * @param diagram
	 * @return
	 */
	public static boolean isEnsembleInDiagram(EObject o, Diagram diagram){
		for(Object e : diagram.getChildren()){
			if(e instanceof View){
				if(((View) e).getElement().equals(o))
					return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Checks if given Connection is in the current diagram.
	 * @param o
	 * @param diagram
	 * @return
	 */
	public static boolean isConnectionInDiagram(EObject o, Diagram diagram){
		for(Object e : diagram.getEdges()){
			if(e instanceof Edge)
				if(((Edge) e).getElement().equals(o))
					return true;
		}
		return false;
	}

}
