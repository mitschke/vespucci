package de.tud.cs.st.vespucci.transformation;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

public class TransformationHelperLibrary {
	private static Deque<Shape> ensembles = new LinkedList<Shape>();
	private static HashMap<Connection, Connection> connections = new HashMap<Connection, Connection>();
	private static HashMap<ShapeImpl, ShapeImpl> notationShapes = new HashMap<ShapeImpl, ShapeImpl>();
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
	}
	
	@Operation(contextual=true)
	public static void remember(Connection result, Connection self)
	{
		connections.put(self, result);
	}
	
	@Operation(contextual=true)
	public static void rememberShape(Object result, Object self)
	{
		notationShapes.put((ShapeImpl)self, (ShapeImpl)result);
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
	public static Connection getRememberedConnection(Object self) 
	{
		return connections.get((Connection)self);
	}
	
	@Operation(contextual=true)
	public static Object getRememberedNotationShape(Object self) 
	{
		return notationShapes.get((ShapeImpl)self);
	}
}
