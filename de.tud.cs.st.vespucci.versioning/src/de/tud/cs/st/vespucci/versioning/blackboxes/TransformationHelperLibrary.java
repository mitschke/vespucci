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

package de.tud.cs.st.vespucci.versioning.blackboxes;

import java.util.HashMap;

import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

/**
 * @author Dominic Scheurer
 * @version 1.0
 * 
 * Class which encapsulates static methods for usage in QVT
 * transformations. Serves as a bridge between model and diagram
 * parts of the files being transformed, remembering the model
 * parts and returning them on demand.
 */
public class TransformationHelperLibrary {
	/**
	 * Maps the original model shapes to the resulting ones after the
	 * transformation.
	 */
	private static HashMap<Shape, Shape> modelShapes = new HashMap<Shape, Shape>();
	
	/**
	 * Maps the original model connections to the resulting ones after the
	 * transformation.
	 */
	private static HashMap<Connection, Connection> connections = new HashMap<Connection, Connection>();
	
	/**
	 * Stores the ShapesDiagram model element for usage as reference in
	 * the diagram part.
	 */
	private static ShapesDiagram shapesDiagram;
	
	/**
	 * Standard constructor. This is needed by QVTO, although
	 * this class only contains static members (which is also
	 * mandatory according to QVTO limitations).<br>
	 * Must not be used!
	 */
	public TransformationHelperLibrary() {
	}
	
	/**
	 * Remembers a ShapesDiagram (model element).
	 * 
	 * @param self The shapesDiagram to remember.
	 */
	@Operation(contextual=true)
	public static void remember(ShapesDiagram self) {
		shapesDiagram = (ShapesDiagramImpl)self;
	}
	
	/**
	 * Remembers that <code>result</code> is the transformation result
	 * of <code>self</code>.
	 * 
	 * @param result The resulting Shape (after transformation)
	 * @param self The original Shape
	 */
	@Operation(contextual=true)
	public static void remember(Shape result, Shape self) {
		modelShapes.put(self, result);
	}
	
	/**
	 * Remembers that <code>result</code> is the transformation result
	 * of <code>self</code>.
	 * 
	 * @param result The resulting Connection (after transformation)
	 * @param self The original Connection
	 */
	@Operation(contextual=true)
	public static void remember(Connection result, Connection self) {
		connections.put(self, result);
	}
	
	/**
	 * Returns the remembered ShapesDiagram (top model element)
	 * 
	 * @param self Not needed; seems to be QVTO limitation that this is there
	 * @return Remembered ShapesDiagram
	 */
	@Operation(contextual=true)
	public static ShapesDiagram getRememberedShapesDiagram(Object self) {
		return shapesDiagram;
	}
	
	/**
	 * Returns the remembered transformation result of <code>self</code>
	 * 
	 * @param self The original Shape before the transformation
	 * @return The remembered transformation result
	 */
	@Operation(contextual=true)
	public static Shape getRememberedShape(Object self) {
		return modelShapes.get(self);
	}
	
	/**
	 * Returns the remembered transformation result of <code>self</code>
	 * 
	 * @param self The original Connection before the transformation
	 * @return The remembered transformation result
	 */
	@Operation(contextual=true)
	public static Connection getRememberedConnection(Object self) {
		return connections.get((Connection)self);
	}
}
