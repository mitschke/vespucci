package de.tud.cs.st.vespucci.transformation;

import java.util.Deque;
import java.util.LinkedList;

import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

public class TransformationHelperLibrary {
	private static Deque<Shape> ensembles = new LinkedList<Shape>();
	private static Deque<Connection> connections = new LinkedList<Connection>();
	private static ShapesDiagram shapesDiagram;
	
	@Operation(contextual=true)
	public static void remember(Object self) 
	{
		if (self.getClass().equals(EnsembleImpl.class))
		{
			ensembles.addLast((EnsembleImpl)self);
		}
		else if (self.getClass().equals(ShapesDiagramImpl.class))
		{
			shapesDiagram = (ShapesDiagramImpl)self;
		}
		else if (Connection.class.isAssignableFrom(self.getClass()))
		{
			connections.addLast((Connection)self);
		}
	}
	
	
	@Operation(contextual=true)
	public static ShapesDiagram getRememberedShapesDiagram(Object self) 
	{
		return shapesDiagram;
	}
	
	@Operation(contextual=true)
	public static Shape getNextRememberedShape(Object self) 
	{
		return ensembles.pollFirst();
	}
	
	@Operation(contextual=true)
	public static Connection getNextRememberedConnection(Object self) 
	{
		return connections.pollFirst();
	}
}
