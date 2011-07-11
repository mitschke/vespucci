/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische Universität Darmstadt
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Engineering Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.supports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * Provide common services, methods extracting information from edit part.
 * 
 * @author Tam-Minh Nguyen
 * @author Michael Eichberg (minor bug fixes, added TODOs)
 * @author BenjaminL errorlog ausgaben hinzugefügt
 */
public class EPService {

    static public String getEditPartName(Object ep) {
	try {
	    if (ep instanceof DummyEditPart || ep instanceof Dummy2EditPart) {
	    	return "empty";
	    }
	    //
	    if (ep instanceof ShapeNodeEditPart) {
			ShapeNodeEditPart ePart = (ShapeNodeEditPart) ep;
			Shape shape = (Shape) ePart.resolveSemanticElement();
			String s = shape.getName();
	
			// TODO BenjaminL: ERROR STRINGS ANLEGEN
			if (s.length() == 0) {
				IStatus iStat = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
						"No name for an ensemble is not allowed");
				StatusManager.getManager().handle(iStat, StatusManager.SHOW);
				StatusManager.getManager().handle(iStat, StatusManager.LOG);
			    return "no-name";
			}
			return s;
	    } else {
	    	IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
					"Couldn't resolve ensemble name from a non ShapeNode", new Exception("Couldn't resolve ensemble name from a non ShapeNode"));
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			return "non-editpart";
		    }
		} catch (Exception e) {
			IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
					"Couldn't resolve ensemble name", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		    return "no_name";
		}
    }

    /**
     * @param ep
     * @return ep query if ep is an editpart.
     */
    static public String getEditPartQuery(Object ep) {
	// TODO See getEditPartName
	try {
	    if (ep instanceof DummyEditPart || ep instanceof Dummy2EditPart) {
	    	return "empty";
	    }
	    //
	    if (ep instanceof ShapeNodeEditPart) {
			ShapeNodeEditPart ePart = (ShapeNodeEditPart) ep;
			Shape shape = (Shape) ePart.resolveSemanticElement();
			String s = shape.getQuery();
	
			if (s.length() == 0) {
				IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
						"Query shouldn't be empty");
				StatusManager.getManager().handle(is, StatusManager.SHOW);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			    return "empty";
			}
			return s;
	    } else {
	    	IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
					"Couldn't resolve ensemble name from a non EditPart", new Exception("Couldn't resolve ensemble name from a non EditPart"));
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
	    	return "non-editpart";
	    }
	} catch (Exception e) {
	    IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
				"Couldn't resolve ensemble", new Exception("Couldn't resolve ensemble"));
		StatusManager.getManager().handle(is, StatusManager.SHOW);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	    return "empty";
	}
    }

    /**
     * @param ePart
     * @return all shapes, children, contained by ePart recursively.
     */
    @SuppressWarnings("unchecked")
    static public List<EditPart> getAllShapesInSideCompartment(EditPart ePart) {
	List<EditPart> shapes = new ArrayList<EditPart>();

	shapes.addAll(ePart.getChildren());

	if (ePart instanceof GraphicalEditPart) {
	    for (Object i : ((GraphicalEditPart) ePart).getChildren()) {
		// System.out.println("\tePart child: " + getEditPartName(i) + "///" + i);
		shapes.addAll(getAllShapesInSideCompartment((EditPart) i));
	    }
	}

	return shapes;
    }

    /**
     * @param list
     * @return list of connection editpart connected to and from input shape list.
     */
    @SuppressWarnings("unchecked")
    static public Set<ConnectionEditPart> getAllConnectionsToAndFromShapeList(List<EditPart> list) {
	Set<ConnectionEditPart> conList = new HashSet<ConnectionEditPart>();

	for (Object o : list) {
	    if (o instanceof GraphicalEditPart) {
		conList.addAll(((GraphicalEditPart) o).getSourceConnections());
		conList.addAll(((GraphicalEditPart) o).getTargetConnections());
	    }
	}
	// conList.removeAll(tmpConnections);
	return conList;
    }

    /**
     * @param con
     * @return connection facts: name, selectable/active or not.
     * @category Debugging help method.
     */
    static public String getConnectionFact(ConnectionEditPart con) {
	return "!Connection: " + EPService.getEditPartName(con.getSource()) + "-"
		+ EPService.getEditPartName(con.getTarget()) + "/selectable: " + con.isSelectable()
		+ "/active: " + con.isActive()

	;
    }

    static public boolean checkIfOriginalConnection(ConnectionEditPart cep) {
	return !org.eclipse.draw2d.ColorConstants.red.equals(cep.getFigure().getForegroundColor());
    }

    static public boolean checkIfConnectionNoSource(ConnectionEditPart con) {
	return ((con.getSource() instanceof DummyEditPart) || (con.getSource() instanceof Dummy2EditPart));
    }

    static public boolean checkIfConnectionNoTarget(ConnectionEditPart con) {
	return ((con.getTarget() instanceof DummyEditPart) || (con.getTarget() instanceof Dummy2EditPart));
    }

    static public EditPart getCommpartmentEditPart(EditPart ep) {
	if (ep instanceof EnsembleEditPart) {
	    EnsembleEditPart eep = (EnsembleEditPart) ep;
	    return eep
		    .getChildBySemanticHint(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
			    .getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID));
	}
	if (ep instanceof Ensemble2EditPart) {
	    Ensemble2EditPart eep = (Ensemble2EditPart) ep;
	    return eep
		    .getChildBySemanticHint(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
			    .getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID));
	}
	return null;
    }

    /**
     * store mouse position on each right click helping in creating new Module from context menu.
     */
    static public Point RECENT_MOUSE_RIGHT_CLICK_POSITION = new Point(0, 0);

    /**
     * @param connection
     *            editpart.
     * @return classifier as string.
     */
    static public String getConnectionClassifier(ConnectionEditPart con) {
	try {
	    Connection con_model = (Connection) con.resolveSemanticElement();
	    String s = con_model.getName();

	    if (s.length() == 0) {
	    	s = "all";
	    }
	    return s;
	} catch (Exception e) {
		IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
				"Couldn't resolve connection classifier", new Exception("Couldn't resolve connection classifier"));
		StatusManager.getManager().handle(is, StatusManager.LOG);
	    return "all";
	}
    }

}
