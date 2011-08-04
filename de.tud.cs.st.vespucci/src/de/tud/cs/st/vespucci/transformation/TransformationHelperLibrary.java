package de.tud.cs.st.vespucci.transformation;

import java.util.HashMap;

import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

public class TransformationHelperLibrary {
	private static HashMap<Shape, Shape> modelShapes = new HashMap<Shape, Shape>();
	private static HashMap<Connection, Connection> connections = new HashMap<Connection, Connection>();
	private static ShapesDiagram shapesDiagram;
	
	@Operation(contextual=true)
	public static void remember(Object self) 
	{
		if (self.getClass().equals(ShapesDiagramImpl.class))
		{
			shapesDiagram = (ShapesDiagramImpl)self;
		}
	}
	
	@Operation(contextual=true)
	public static void remember(Shape result, Shape self) 
	{
		modelShapes.put(self, result);
	}
	
	@Operation(contextual=true)
	public static void remember(Connection result, Connection self)
	{
		connections.put(self, result);
	}
	
	@Operation(contextual=true)
	public static ShapesDiagram getRememberedShapesDiagram(Object self) 
	{
		return shapesDiagram;
	}
	
	@Operation(contextual=true)
	public static Shape getRememberedShape(Object self) 
	{
		return modelShapes.get(self);
	}
	
	@Operation(contextual=true)
	public static Connection getRememberedConnection(Object self) 
	{
		return connections.get((Connection)self);
	}
}
