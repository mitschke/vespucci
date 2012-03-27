/** License (BSD Style License):
 *  Copyright (c) 2011
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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;

/**
 * Provide common services, methods extracting information from edit part.
 * 
 * @author Tam-Minh Nguyen
 * @author Michael Eichberg - minor bug fixes, added TODOs
 * @author Benjamin Lück - errors are now stored in the error log
 */
public class EditPartService {
	/**
	 * @param ePart
	 * @return all shapes, children, contained by ePart recursively.
	 */
	@SuppressWarnings("unchecked")
	public static List<EditPart> getAllShapesInSideCompartment(final EditPart ePart) {
		final List<EditPart> shapes = new ArrayList<EditPart>();

		shapes.addAll(ePart.getChildren());

		if (ePart instanceof GraphicalEditPart) {
			for (final Object i : ((GraphicalEditPart) ePart).getChildren()) {
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
	public static Set<ConnectionEditPart> getAllConnectionsToAndFromShapeList(final List<EditPart> list) {
		final Set<ConnectionEditPart> conList = new HashSet<ConnectionEditPart>();

		for (final Object o : list) {
			if (o instanceof GraphicalEditPart) {
				conList.addAll(((GraphicalEditPart) o).getSourceConnections());
				conList.addAll(((GraphicalEditPart) o).getTargetConnections());
			}
		}
		return conList;
	}
	
	/**
	 * store mouse position on each right click helping in creating new Module from context menu.
	 */
	private static Point RECENT_MOUSE_RIGHT_CLICK_POSITION = new Point(0, 0);

	/**
	 * 
	 * @param position
	 */
	public static void setRecentRightClickPos(Point position) {
		RECENT_MOUSE_RIGHT_CLICK_POSITION = position;
	}

	/**
	 * 
	 * @return Returns the last right click position.
	 */
	public static Point getRecentRightClickPos() {
		return RECENT_MOUSE_RIGHT_CLICK_POSITION;
	}
}
