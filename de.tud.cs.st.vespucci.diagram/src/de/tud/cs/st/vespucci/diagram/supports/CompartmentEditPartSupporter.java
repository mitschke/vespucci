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
 * Collapsement supporter for EnsembleEditPart.
 * 
 * @author Artem Vovk
 */
public class CompartmentEditPartSupporter {

	/**
	 * Color for connection pointing to or from collapsed ensembles.
	 */
	public static final Color TMP_CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.red;

	/**
	 * Default color for connections.
	 */
	public static final Color CONNECTION_COLOR = org.eclipse.draw2d.ColorConstants.black;

	private final ShapeCompartmentEditPart compartmentToSupport;

	private ShapeNodeEditPart editPartOfCompartment;

	private List<EditPart> compartmentChildren;

	/**
	 * 
	 * @param compartmentToSupport
	 *            The ensemble for which collapsement support will be provided.
	 */
	public CompartmentEditPartSupporter(final ShapeCompartmentEditPart compartmentToSupport) {
		this.compartmentToSupport = compartmentToSupport;

	}

	/**
	 * Update connections after EditPart was collapsed/opened.
	 * 
	 * @param collapseEvent
	 */
	public void updateConnections(final Notification collapseEvent) {

		editPartOfCompartment = (ShapeNodeEditPart) compartmentToSupport.getParent();

		compartmentChildren = EditPartService.getAllShapesInSideCompartment(compartmentToSupport);

		if (collapseEvent.getNewBooleanValue()) {
			collapseEditPart();
		} else {
			openEditPart();
		}

	}

	/**
	 * Collapse edit part
	 */
	private void collapseEditPart() {
		final Set<ConnectionEditPart> childrenConnections = getChildrenConnections();
		final Set<ConnectionEditPart> outsideConnections = excludeInternConnections(childrenConnections);
		final Set<ConnectionEditPart> edges = filterForDependencyConstraints(outsideConnections);

		for (final ConnectionEditPart outConnection : edges) {
			final EdgeImpl edge = (EdgeImpl) outConnection.getModel();

			// it's for undo/redo operations
			if (outConnection.getSource().getModel() != edge.getSource()
					|| outConnection.getTarget().getModel() != edge.getTarget()) {
				return;
			}

			if (compartmentChildren.contains(outConnection.getSource())) {
				// readjust connection and update original source history
				// readjust at source
				edge.setSource((ShapeImpl) editPartOfCompartment.getModel());
				final Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				final EList<Shape> oSources = con.getOriginalSource();
				oSources.add(con.getSource());
				con.setSource((Shape) ((ShapeImpl) editPartOfCompartment.getModel()).getElement());
			} else {
				// readjust at target
				edge.setTarget((ShapeImpl) editPartOfCompartment.getModel());
				final Connection con = (Connection) edge.getElement();
				con.setTemp(true);
				final EList<Shape> oTargets = con.getOriginalTarget();
				oTargets.add(con.getTarget());
				con.setTarget((Shape) ((ShapeImpl) editPartOfCompartment.getModel()).getElement());
			}
		}
	}

	/**
	 * @return Returns view for corresponding model element.
	 * 
	 * @param containerEditPart
	 *            Edit part containing the given shape.
	 * @param shapeOfNode
	 *            Model shape of the view to be searched and returned.
	 */
	private static NodeImpl getViewFromModel(final EditPart containerEditPart, final Shape shapeOfNode) {
		final List<EditPart> editParts = EditPartService.getAllShapesInSideCompartment(containerEditPart);
		for (final EditPart i : editParts) {
			if (i.getModel() instanceof NodeImpl) {
				final NodeImpl shapeImpl = (NodeImpl) i.getModel();
				final Shape element = (Shape) shapeImpl.getElement();
				if (element.equals(shapeOfNode)) {
					return shapeImpl;
				}
			}
		}
		return null;
	}

	/**
	 * Opens the edit part and restores the connections.
	 */
	private void openEditPart() {

		final Set<ConnectionEditPart> connections = getAllConnections();

		for (final ConnectionEditPart i : connections) {
			final EdgeImpl edgeToRestore = (EdgeImpl) i.getModel();
			final Connection con = (Connection) edgeToRestore.getElement();

			if (con.isTemp()) {
				if (edgeToRestore.getSource() == editPartOfCompartment.getModel() && (!con.getOriginalSource().isEmpty())) {
					// readjust source
					final EList<Shape> oSources = con.getOriginalSource();
					final Shape source = oSources.remove(oSources.size() - 1);

					final NodeImpl shapeImpl = getViewFromModel(compartmentToSupport, source);

					edgeToRestore.setSource(shapeImpl);

					con.setSource(source);
				} else if (edgeToRestore.getTarget() == editPartOfCompartment.getModel() && (!con.getOriginalTarget().isEmpty())) {
					// readjust target
					final EList<Shape> oTargets = con.getOriginalTarget();
					final Shape target = oTargets.remove(oTargets.size() - 1);

					final NodeImpl shapeImpl = getViewFromModel(compartmentToSupport, target);
					edgeToRestore.setTarget(shapeImpl);

					con.setTarget(target);

				}
				if (con.getOriginalSource().isEmpty() && con.getOriginalTarget().isEmpty()) {
					// connection is points direct from original source to original target, thus it's not temporal
					// anymore.
					con.setTemp(false);
				}
			}
		}
	}

	/**
	 * @return Returns all connections from the children.
	 */
	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getChildrenConnections() {
		final List<EditPart> children = EditPartService.getAllShapesInSideCompartment(compartmentToSupport);

		final Set<ConnectionEditPart> childrenConnections = new HashSet<ConnectionEditPart>();

		// all connections from inside
		for (final EditPart o : children) {
			if (o instanceof GraphicalEditPart) {
				childrenConnections.addAll(((GraphicalEditPart) o).getSourceConnections());
				childrenConnections.addAll(((GraphicalEditPart) o).getTargetConnections());
			}
		}
		return childrenConnections;
	}

	/**
	 * 
	 * @param connections
	 * @return Returns the given connections without intra-connections, i.e. connections between children or connections
	 *         between child and parent.
	 */
	private Set<ConnectionEditPart> excludeInternConnections(final Set<ConnectionEditPart> connections) {
		final List<EditPart> internalParts = EditPartService.getAllShapesInSideCompartment(compartmentToSupport);

		// connections from children to its parent are also internal
		internalParts.add(editPartOfCompartment);

		for (final ConnectionEditPart c : connections) {
			// check if connection has one end outside of compartment
			if (internalParts.contains(c.getSource()) ^ internalParts.contains(c.getTarget())) {
				connections.add(c);
			}
		}

		return connections;
	}

	/**
	 * @return Returns all connections that belong to this editPart. Connections of children are not included.
	 */
	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getAllConnections() {
		final Set<ConnectionEditPart> connections = new HashSet<ConnectionEditPart>();
		connections.addAll(this.editPartOfCompartment.getSourceConnections());
		connections.addAll(this.editPartOfCompartment.getTargetConnections());
		return filterForDependencyConstraints(connections);
	}

	/**
	 * Filter connections for EdgeImpl; Subclass ConnectorImpl will be left out.
	 * 
	 * @param connections
	 *            Connections to be filtered.
	 * @return Returns filtered connections.
	 */
	private static Set<ConnectionEditPart> filterForDependencyConstraints(final Set<ConnectionEditPart> connections) {
		final Set<ConnectionEditPart> result = new HashSet<ConnectionEditPart>();
		for (final ConnectionEditPart conn : connections) {
			final EdgeImpl edge = (EdgeImpl) conn.getModel();
			if (edge.getElement() != null && edge.getElement() instanceof Connection) {
				result.add(conn);
			}	
		}
		return result;
	}

}
