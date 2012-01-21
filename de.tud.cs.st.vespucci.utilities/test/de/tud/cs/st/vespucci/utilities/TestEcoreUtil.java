package de.tud.cs.st.vespucci.utilities;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class TestEcoreUtil {

	@Test
	public void loadVespucciModel() throws IOException {
		ShapesDiagram model = (ShapesDiagram) EcoreUtil.loadVespucciModel("test-file.sad");
		
		assertEquals(2, model.getShapes().size());
		
		assertEquals("A", model.getShapes().get(0).getName());
		assertEquals("B", model.getShapes().get(1).getName());
		assertEquals(1, model.getShapes().get(0).getTargetConnections().size()); 
		
	}
	
}
