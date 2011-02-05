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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry;
import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

/**
 * This's a compartment helper class. Its provided methods to help updating
 * (draw-, undrawing) necessary indirect connections between compartments, when
 * the compartment collapsed. Those additional connections will be drawn in red.
 * 
 * The help methods receive from each compartment object their collapsed/opened
 * events and appropriate params.
 * 
 * @date 03.15.2010
 * @author Tam-Minh Nguyen
 * @author a_vovk
 */

public class CompartmentEditPart_Supporter {
	// current compartment editpart, which raised COLLAPSED/OPENED event
	ShapeCompartmentEditPart ePart;
	// Ensemble editpart contains above compartment editpart
	ShapeNodeEditPart parent;
	// current state of compartment editpart
	boolean collapsed;
	// all shapes in diagram
	List<EditPart> shapeList;
	// all connections in diagram
	Set<ConnectionEditPart> conList;

	// connenctions set by user
	Set<ConnectionEditPart> realConList;
	// temp connections set by Vespucci (red colored)
	Set<ConnectionEditPart> tmpConList;

	public CompartmentEditPart_Supporter() {
	}

	/**
	 * Be called by EnsembleCompartmentXEditPart to handle connection drawing
	 * when compartment collapsed or opened.
	 * 
	 * @param ePart
	 *            : selected compartment
	 * @param event
	 */
	public void updateConnections(ShapeCompartmentEditPart ePart,
			Notification event) {

		// init
		this.ePart = ePart;
		this.parent = (ShapeNodeEditPart) this.ePart.getParent();

		this.shapeList = EPService.getAllShapesInSideCompartment(this.ePart);

		this.realConList = this.getRealConnections(this.shapeList);
		this.tmpConList = this.getTempConnections(this.shapeList);

		// get feature
		Object feature = event.getFeature();

		if (NotationPackage.eINSTANCE.getDrawerStyle_Collapsed()
				.equals(feature)) {
			// always remove temp connections
			// temp connection with one end outside compartment will be removed
			this.removeConnectionsList(this.tmpConList);

			if (event.getNewBooleanValue() == true) {
				collapseEditPart();
			} else {
				openEditPart();
			}

		}
	}

	private void openEditPart() {
		this.collapsed = false;
		//
		// create new temp connections, its end is unselectable
		for (ConnectionEditPart c : this.realConList) {
			EditPart awayEnd = this.getAwayEndEditPart(c);
			EditPart homeEnd = this.getHomeEndEditPart(c);
			// name
			String name = ((Connection) c.resolveSemanticElement()).getName();
			//
			EditPart source = c.getSource();
			EditPart target = c.getTarget();

			if (awayEnd.isSelectable() == false) {
				// add new connection
				if (this.shapeList.contains(source)) {
					// target is awayEnd
					target = this.getSelectableEditPart(target);
					//
					source = this.getDrawableBackTrack(source);
				} else if (this.shapeList.contains(c.getTarget())) {
					// source is awayEnd
					source = this.getSelectableEditPart(source);
					//
					target = this.getDrawableBackTrack(target);
				}
				
				
				// draw
				this.drawConnection(VespucciElementTypes
						.getElementType(VespucciVisualIDRegistry.getVisualID(c
								.getPrimaryView())), source, target,
						org.eclipse.draw2d.ColorConstants.red, name);
			} else {// awayEnd is selectable
				EditPart newHomeEnd = this.getDrawableBackTrack(homeEnd);
				//
				if (homeEnd.equals(newHomeEnd) == false && newHomeEnd != null) {
					// add new connection
					if (this.shapeList.contains(source)) {
						// target is awayEnd
						// target = this.getSelectableEditPart(target);
						//
						source = this.getDrawableBackTrack(source);
					} else if (this.shapeList.contains(c.getTarget())) {
						// source is awayEnd
						// source = this.getSelectableEditPart(source);
						//
						target = this.getDrawableBackTrack(target);
					}
					// draw
					this.drawConnection(VespucciElementTypes
							.getElementType(VespucciVisualIDRegistry
									.getVisualID(c.getPrimaryView())), source,
							target, org.eclipse.draw2d.ColorConstants.red, name);
				} 
			}

		}// end for
	}

	/**
	 * Collapse Compartment Edit Part and create temporary
	 * "red connection lines"
	 */
	private void collapseEditPart() {
		this.collapsed = true;

		for (ConnectionEditPart c : this.realConList) {
			// name
			String name = ((Connection) c.resolveSemanticElement()).getName();
			// add new connection
			EditPart source = c.getSource();
			EditPart target = c.getTarget();
			if (shapeList.contains(source)) {
				source = this.parent;
				target = this.getSelectableEditPart(target);
			} else if (shapeList.contains(target)) {
				source = this.getSelectableEditPart(source);
				target = parent;
			} else {
				IStatus wrongConnectionException = new Status(
						Status.ERROR,
						VespucciDiagramEditorPlugin.ID,
						"WrongConnectionException",
						new Exception(
								"ShapeList or Connection is wrong initialized: "
										+ "ShapeList of children compartments doesn't contains"
										+ "the source/target of a connection, it's probably bug "
										+ "in CompartmetnEditPart_Supporter.java"));
				StatusManager.getManager().handle(wrongConnectionException,
						StatusManager.LOG);
				StatusManager.getManager().handle(wrongConnectionException,
						StatusManager.LOG);
			}
			if (source == target)
				continue;

			this.drawConnection(VespucciElementTypes
					.getElementType(VespucciVisualIDRegistry.getVisualID(c
							.getPrimaryView())), source, target,
					org.eclipse.draw2d.ColorConstants.red, name);
		}
	}

	/**
	 * @param ep
	 * @return parent of the highest collaped compartment. Important by creating
	 *         temp connection.
	 */
	private EditPart getDrawableBackTrack(EditPart ep) {

		if (ep instanceof EnsembleEditPart) {
			System.err.println("\t-- GOT U WRONG1");
			return null;
		} else if (ep instanceof Ensemble2EditPart) {
			// direct child
			if (this.ePart.getChildren().contains(ep)) {
				return ep;
			} else {
				Ensemble2EditPart c2ep = (Ensemble2EditPart) ep;

				EditPart flagEditPart = null;
				while (this.ePart.getChildren().contains(c2ep) == false) {
					EnsembleEnsembleCompartment2EditPart ccc2ep = (EnsembleEnsembleCompartment2EditPart) c2ep
							.getParent();
					// OPENED
					//int i = 1;
					if (ccc2ep.isCollapsed() == true) {
						// find the highest collaped compartment
						flagEditPart = ccc2ep;
					}
					//
					c2ep = (Ensemble2EditPart) ccc2ep.getParent();
				}

				if (flagEditPart != null) {
					return flagEditPart.getParent();
				} else {
					// System.err.println("\t-- GOT U WRONG2");
					return ep;
				}
			}
		}
		//
		System.err.println("\t-- GOT U WRONG3");
		return null;
	}

	/**
	 * @param shapeList
	 * @return list of real connections to and from input shape list. Real
	 *         connections set by user.
	 */
	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getRealConnections(List<EditPart> shapeList) {
		Set<ConnectionEditPart> tmpList = new HashSet<ConnectionEditPart>();
		Set<ConnectionEditPart> conList = new HashSet<ConnectionEditPart>();

		// all connections from inside
		for (Object o : shapeList) {
			if (o instanceof GraphicalEditPart) {
				tmpList.addAll(((GraphicalEditPart) o).getSourceConnections());
				tmpList.addAll(((GraphicalEditPart) o).getTargetConnections());
			}
		}

		// no need to get connection from dad, WHY???
		// all connections from dad
		tmpList.addAll(this.parent.getSourceConnections());
		tmpList.addAll(this.parent.getTargetConnections());

		// check if tmp connection
		for (ConnectionEditPart c : tmpList) {
			// check if connection has one end outside of compartment
			if (shapeList.contains(c.getSource())
					^ shapeList.contains(c.getTarget())) {
				// if (!this.checkIfTempConnection(c)) {
				if (EPService.checkIfOriginalConnection(c)) {
					conList.add(c);
				}
			}
		}

		//
		return conList;
	}

	/**
	 * @param shapeList
	 * @return list of temp connections to and from input shape list.
	 */
	@SuppressWarnings("unchecked")
	private Set<ConnectionEditPart> getTempConnections(List<EditPart> shapeList) {
		Set<ConnectionEditPart> tmpList = new HashSet<ConnectionEditPart>();
		Set<ConnectionEditPart> tmpList2 = new HashSet<ConnectionEditPart>();

		// all connections from inside
		for (Object o : shapeList) {
			if (o instanceof GraphicalEditPart) {
				tmpList.addAll(((GraphicalEditPart) o).getSourceConnections());
				tmpList.addAll(((GraphicalEditPart) o).getTargetConnections());
			}
		}

		// all connections from dad
		tmpList.addAll(this.parent.getSourceConnections());
		tmpList.addAll(this.parent.getTargetConnections());

		// check if tmp connection
		for (ConnectionEditPart c : tmpList) {
			if (EPService.checkIfOriginalConnection(c) == false) {
				tmpList2.add(c);
			}
		}

		//
		return tmpList2;
	}

	/**
	 * @param cep
	 * @return Editpart at one end of ConnectionEditPart is not contained by
	 *         current selected compartment.
	 */
	private EditPart getAwayEndEditPart(ConnectionEditPart cep) {
		// get away end
		EditPart awe = null;
		if (this.shapeList.contains(cep.getSource())) {
			awe = cep.getTarget();
		} else if (this.shapeList.contains(cep.getTarget())) {
			awe = cep.getSource();
		}
		return awe;
	}

	/**
	 * @param cep
	 * @return Editpart at one end of ConnectionEditPart contained by current
	 *         selected compartment.
	 */
	private EditPart getHomeEndEditPart(ConnectionEditPart cep) {
		// get away end
		EditPart awe = null;
		if (this.shapeList.contains(cep.getSource())) {
			awe = cep.getSource();
		} else if (this.shapeList.contains(cep.getTarget())) {
			awe = cep.getTarget();
		}
		return awe;
	}

	/*
	 * *
	 * 
	 * @param conList
	 * 
	 * @category Debugging help method.
	 * 
	 * private void testConList(Set<ConnectionEditPart> conList) {
	 * System.out.println("-TEST CON LIST - " +
	 * EPService.getEditPartName(this.parent)); for (ConnectionEditPart c :
	 * conList) { System.out.println("\t" + EPService.getConnectionFact(c)); } }
	 */

	/**
	 * if its compartment is collapsed. Helpful to get drawable editpart.
	 */
	public boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * @return selectable editpart of input editpart. Parent editpart will be
	 *         returned if editpart is unselectable.
	 */
	private EditPart getSelectableEditPart(EditPart ep) {
		EditPart rep = ep;

		while (rep.isSelectable() == false) {
			rep = rep.getParent();
		}

		return rep;
	}

	/**
	 * Called when Compartment collapsed or opened! Temp connection with one end
	 * outside compartment will be removed.
	 */
	private void removeConnectionsList(Set<ConnectionEditPart> list) {
		if (list == null)
			return;
		for (ConnectionEditPart c : list) {
			if (!(this.shapeList.contains(c.getSource()) && this.shapeList
					.contains(c.getTarget()))) {
				this.eraseConnection(c);
			}
		}
		list.clear();
	}

	/**
	 * Add temp connection between two EditParts, red colored.
	 */
	private void drawConnection(IElementType conType, EditPart source,
			EditPart target, Color color, String name) {

		CreateConnectionViewRequest conReq = CreateViewRequestFactory
				.getCreateConnectionRequest(conType,
						this.ePart.getDiagramPreferencesHint());

		org.eclipse.gef.commands.Command createConnectionCommand = CreateConnectionViewRequest
				.getCreateCommand(conReq, source, target);

		this.ePart.getDiagramEditDomain().getDiagramCommandStack()
				.execute(createConnectionCommand);

		this.ePart.refresh();
		// this.ePart.forceRefreschConnection();

		source.refresh();
		target.refresh();
		//
		int size = ((ShapeNodeEditPart) source).getSourceConnections().size();
		ConnectionEditPart connect = (ConnectionEditPart) ((ShapeNodeEditPart) source)
				.getSourceConnections().get(size - 1);

		// name
		((Connection) connect.resolveSemanticElement()).setName(name);

		connect.getFigure().setForegroundColor(color);
		connect.getFigure().repaint();

		Connection ci = (Connection) connect.resolveSemanticElement();
		ci.setTemp(true);
	}

	private void eraseConnection(ConnectionEditPart con) {
		DestroyElementRequest request2 = new DestroyElementRequest(
				con.resolveSemanticElement(), true);
		Command command = con
				.getCommand(new EditCommandRequestWrapper(request2));

		this.ePart.getDiagramEditDomain().getDiagramCommandStack()
				.execute(command);
	}
}
