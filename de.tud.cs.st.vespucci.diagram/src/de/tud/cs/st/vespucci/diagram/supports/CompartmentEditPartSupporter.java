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
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.impl.ConnectorImpl;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.swt.graphics.Color;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * Collapsement supporter for EnsembleEditPart
 * 
 * @author a_vovk
 * 
 */
public class CompartmentEditPartSupporter {

	// ------------------------------------------//
	public static final Color TMP_CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.red;

	public static final Color CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.black;

	private ShapeCompartmentEditPart compartmentToSupport;

	private ShapeNodeEditPart editPartOfCompartment;

	private List<EditPart> compartmentChildren;

	// ------------------------------------------//

	public CompartmentEditPartSupporter(
			ShapeCompartmentEditPart compartmentToSupport) {
		this.compartmentToSupport = compartmentToSupport;

	}

	/**
	 * Update connections after EditPart was collapsed/opened
	 * 
	 * @param event
	 *            collapsement event
	 */
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

	/**
	 * Collapse edit part
	 */
	private void collapseEditPart() {
		Set<ConnectionEditPart> outisdeConnections = getConnections(this.compartmentToSupport);
		for (ConnectionEditPart i : outisdeConnections) {
			EdgeImpl edge = (EdgeImpl) i.getModel();

			// it's for undo/redo operations
			if (i.getSource().getModel() != edge.getSource()
					|| i.getTarget().getModel() != edge.getTarget())
				return;

			if (compartmentChildren.contains(i.getSource())) {
				edge.setSource((ShapeImpl) this.editPartOfCompartment
						.getModel());
				Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				EList<Shape> oSources = con.getOriginalSource();
				oSources.add(con.getSource());
				con.setSource((Shape) ((ShapeImpl) this.editPartOfCompartment
						.getModel()).getElement());

			} else {
				edge.setTarget((ShapeImpl) this.editPartOfCompartment
						.getModel());
				Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				EList<Shape> oTargets = con.getOriginalTarget();
				oTargets.add(con.getTarget());
				con.setTarget((Shape) ((ShapeImpl) this.editPartOfCompartment
						.getModel()).getElement());
			}
		}
	}

	/**
	 * Get view for corresponding model element
	 * 
	 * @param editPart search in this editPart
	 * @param shapeToFind model element
	 * @return
	 */
	private NodeImpl getViewFromModel(EditPart editPart, Shape shapeToFind) {
		List<EditPart> editParts = EPService
				.getAllShapesInSideCompartment(editPart);
		for (EditPart i : editParts) {
			if (i.getModel() instanceof NodeImpl) {
				NodeImpl shapeImpl = (NodeImpl) i.getModel();
				Shape element = (Shape) shapeImpl.getElement();
				if (element.equals(shapeToFind)) {
					return shapeImpl;
				}
			}
		}
		return null;
	}

	/**
	 * Open edit part and restore the connections
	 */
	private void openEditPart() {

		Set<ConnectionEditPart> connections = this.getAllConnections();

		for (ConnectionEditPart i : connections) {
			EdgeImpl edgeToRestore = (EdgeImpl) i.getModel();
			Connection con = (Connection) edgeToRestore.getElement();

			if (con.isTemp()) {
				if (edgeToRestore.getSource() == this.editPartOfCompartment
						.getModel() && (!con.getOriginalSource().isEmpty())) {
					EList<Shape> oSources = con.getOriginalSource();
					Shape source = oSources.remove(oSources.size() - 1);

					NodeImpl shapeImpl = getViewFromModel(
							this.compartmentToSupport, source);

					edgeToRestore.setSource(shapeImpl);

					con.setSource(source);
				} else if (edgeToRestore.getTarget() == this.editPartOfCompartment
						.getModel() && (!con.getOriginalTarget().isEmpty())) {

					EList<Shape> oTargets = con.getOriginalTarget();
					Shape target = oTargets.remove(oTargets.size() - 1);

					NodeImpl shapeImpl = getViewFromModel(
							this.compartmentToSupport, target);
					edgeToRestore.setTarget(shapeImpl);

					con.setTarget(target);

				}
				if (con.getOriginalSource().isEmpty()
						&& con.getOriginalTarget().isEmpty()) {
					con.setTemp(false);
				}

			}

		}

	}

	/**
	 * Get all connections that goes in/out from ePart editPart(all
	 * children connections from this editPart)
	 * @param ePart 
	 * @return
	 */
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

		return filterConnectionsFromConnectorImpl(connections);

	}

	/**
	 * Get all connections that belong to this editPart
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getAllConnections() {
		Set<ConnectionEditPart> connections = new HashSet<ConnectionEditPart>();
		connections.addAll(this.editPartOfCompartment.getSourceConnections());
		connections.addAll(this.editPartOfCompartment.getTargetConnections());
		return filterConnectionsFromConnectorImpl(connections);

	}

	/**
	 * Filter connections for EdgeImpl: delete ConnectorImpl
	 * 
	 * @param connections
	 *            connections to filter
	 * @return filtered connections
	 */
	private Set<ConnectionEditPart> filterConnectionsFromConnectorImpl(
			Set<ConnectionEditPart> connections) {
		Set<ConnectionEditPart> out = new HashSet<ConnectionEditPart>();
		for (ConnectionEditPart i : connections) {
			if (!(i.getModel() instanceof ConnectorImpl)) {
				out.add(i);
			}
		}
		return out;
	}

}
