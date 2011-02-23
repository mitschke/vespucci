/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische Universit�t Darmstadt
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
 *    Universit�t Darmstadt nor the names of its contributors may be used to 
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.swt.graphics.Color;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * 
 * @author a_vovk
 * 
 */
public class CompartmentEditPartSupporter {

	// ------------------------------------------//
	private static final Color TMP_CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.red;

	private static final Color CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.black;

	private ShapeCompartmentEditPart compartmentToSupport;

	private ShapeNodeEditPart editPartOfCompartment;

	private List<EditPart> compartmentChildren;

	// ------------------------------------------//

	public CompartmentEditPartSupporter(
			ShapeCompartmentEditPart compartmentToSupport) {
		this.compartmentToSupport = compartmentToSupport;

	}

	public void updateConnections(Notification event) {

		this.editPartOfCompartment = (ShapeNodeEditPart) this.compartmentToSupport
				.getParent();

		this.compartmentChildren = EPService
				.getAllShapesInSideCompartment(this.compartmentToSupport);

		if (event.getNewBooleanValue() == true) {
			collapseEditPart();
		} else {
			openEditPart();
		}

	}

	private void collapseEditPart() {
		Set<ConnectionEditPart> outisdeConnections = getConnections(this.compartmentToSupport);
		for (ConnectionEditPart i : outisdeConnections) {
			EdgeImpl edge = (EdgeImpl) i.getModel();
			if (compartmentChildren.contains(i.getSource())) {
				edge.setSource((ShapeImpl) this.editPartOfCompartment
						.getModel());
				Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				con.setOriginalSource(con.getSource());
				con.setSource((Shape) ((ShapeImpl) this.editPartOfCompartment
						.getModel()).getElement());

			} else {
				edge.setTarget((ShapeImpl) this.editPartOfCompartment
						.getModel());
				Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				con.setOriginalTarget(con.getTarget());
				con.setTarget((Shape) ((ShapeImpl) this.editPartOfCompartment
						.getModel()).getElement());
			}
			i.getFigure().setForegroundColor(TMP_CONNECTION_COLOR);
		}
	}

	private ShapeImpl getViewFromModel(EditPart editPart, Shape shapeToFind) {
		List<EditPart> editParts = EPService
				.getAllShapesInSideCompartment(editPart);
		for (EditPart i : editParts) {
			if (i.getModel() instanceof ShapeImpl) {
				ShapeImpl shapeImpl = (ShapeImpl) i.getModel();
				Shape element = (Shape) shapeImpl.getElement();
				if (element.equals(shapeToFind)) {
					return shapeImpl;
				}
			}
		}
		return null;
	}

	private void openEditPart() {

		Set<ConnectionEditPart> connections = this.getAllConnections();

		for (ConnectionEditPart i : connections) {
			EdgeImpl edgeToRestore = (EdgeImpl) i.getModel();
			Connection con = (Connection) edgeToRestore.getElement();

			if (con.isTemp()) {
				if (edgeToRestore.getSource() == this.editPartOfCompartment
						.getModel() && (con.getOriginalSource() != null)) {
					ShapeImpl shapeImpl = getViewFromModel(
							this.compartmentToSupport, con.getOriginalSource());
					edgeToRestore.setSource(shapeImpl);

					con.setSource(con.getOriginalSource());
					con.setOriginalSource(null);

				} else if (edgeToRestore.getTarget() == this.editPartOfCompartment
						.getModel() && (con.getOriginalTarget() != null)) {
					ShapeImpl shapeImpl = getViewFromModel(
							this.compartmentToSupport, con.getOriginalTarget());
					edgeToRestore.setTarget(shapeImpl);
					con.setTarget(con.getOriginalTarget());

					con.setOriginalTarget(null);
				}
				if (con.getOriginalSource() == null
						&& con.getOriginalTarget() == null) {
					con.setTemp(false);
					i.getFigure().setForegroundColor(CONNECTION_COLOR);
				}

			}

		}

	}

	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getConnections(
			ShapeCompartmentEditPart ePart) {
		List<EditPart> children = EPService
				.getAllShapesInSideCompartment(ePart);

		Set<ConnectionEditPart> tmpConnections = new HashSet<ConnectionEditPart>();
		Set<ConnectionEditPart> connections = new HashSet<ConnectionEditPart>();

		// all connections from inside
		for (EditPart o : children) {
			if (o instanceof GraphicalEditPart) {
				tmpConnections.addAll(((GraphicalEditPart) o)
						.getSourceConnections());
				tmpConnections.addAll(((GraphicalEditPart) o)
						.getTargetConnections());
			}
		}

		children.add(editPartOfCompartment);
		for (ConnectionEditPart c : tmpConnections) {
			// check if connection has one end outside of compartment
			if (children.contains(c.getSource())
					^ children.contains(c.getTarget())) {
				connections.add(c);
			}
		}
		return connections;

	}

	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getAllConnections() {
		Set<ConnectionEditPart> connections = new HashSet<ConnectionEditPart>();
		connections.addAll(this.editPartOfCompartment.getSourceConnections());
		connections.addAll(this.editPartOfCompartment.getTargetConnections());
		return connections;

	}

	// public void cleanModelOfAConnection(EdgeImpl edge, Notification event) {
	// Connection con = (Connection)edge.getElement();
	//
	// if()
	//
	// con.setOriginalTarget(null);
	// if(con.getOriginalSource() == null && con.getOriginalTarget() == null){
	// con.setTemp(false);
	// i.getFigure().setForegroundColor(CONNECTION_COLOR);
	// }
	//
	//
	// }

}
