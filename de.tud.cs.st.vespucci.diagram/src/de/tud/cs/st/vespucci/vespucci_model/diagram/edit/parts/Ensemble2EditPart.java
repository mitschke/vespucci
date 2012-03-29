/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import de.tud.cs.st.vespucci.diagram.dnd.IJavaElementDropConstants;
import de.tud.cs.st.vespucci.diagram.dnd.JavaElementEnsembleDropPolicy;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.VespucciCanonicalEditPolicy;

/**
 * @generated
 */
public class Ensemble2EditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3004;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public Ensemble2EditPart(View view) {
		super(view);
	}

	/**
	 * @generated not
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.Ensemble2ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(IJavaElementDropConstants.REQ_DROP_EXTEND_ENSEMBLE,
				new JavaElementEnsembleDropPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child
						.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new EnsembleFigure();
	}

	/**
	 * @generated
	 */
	public EnsembleFigure getPrimaryShape() {
		return (EnsembleFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart) {
			((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart) childEditPart)
					.setLabel(getPrimaryShape().getFigureEnsembleNameFigure());
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescription2EditPart) {
			((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescription2EditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureEnsembleDescriptionFigure());
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) {
			IFigure pane = getPrimaryShape()
					.getFigureEnsembleCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart) {
			IFigure pane = getPrimaryShape()
					.getFigureEnsembleDescriptionCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart) {
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescription2EditPart) {
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) {
			IFigure pane = getPrimaryShape()
					.getFigureEnsembleCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart) {
			IFigure pane = getPrimaryShape()
					.getFigureEnsembleDescriptionCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		if (editPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) {
			return getPrimaryShape().getFigureEnsembleCompartmentFigure();
		}
		if (editPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart) {
			return getPrimaryShape()
					.getFigureEnsembleDescriptionCompartmentFigure();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(40, 40);
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSource() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(8);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForTarget(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnTarget() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(8);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007);
		types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForSource(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2005);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2006);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3004);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3005);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public class EnsembleFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureEnsembleNameFigure;
		/**
		 * @generated
		 */
		private WrappingLabel fFigureEnsembleDescriptionFigure;
		/**
		 * @generated
		 */
		private WrappingLabel fFigureEnsembleQueryFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fFigureEnsembleCompartmentFigure;

		/**
		 * @generated
		 */
		private RectangleFigure fFigureEnsembleDescriptionCompartmentFigure;

		/**
		 * @generated
		 */
		public EnsembleFigure() {

			BorderLayout layoutThis = new BorderLayout();
			this.setLayoutManager(layoutThis);

			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(8),
					getMapMode().DPtoLP(8)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureEnsembleNameFigure = new WrappingLabel();
			fFigureEnsembleNameFigure.setText("");

			this.add(fFigureEnsembleNameFigure, BorderLayout.TOP);

			fFigureEnsembleQueryFigure = new WrappingLabel();
			fFigureEnsembleQueryFigure.setText("");

			this.add(fFigureEnsembleQueryFigure);

			RectangleFigure ensembleDescriptionCompartmentContainerFigure0 = new RectangleFigure();

			this.add(ensembleDescriptionCompartmentContainerFigure0,
					BorderLayout.CENTER);

			BorderLayout layoutEnsembleDescriptionCompartmentContainerFigure0 = new BorderLayout();
			ensembleDescriptionCompartmentContainerFigure0
					.setLayoutManager(layoutEnsembleDescriptionCompartmentContainerFigure0);

			fFigureEnsembleDescriptionCompartmentFigure = new RectangleFigure();

			ensembleDescriptionCompartmentContainerFigure0.add(
					fFigureEnsembleDescriptionCompartmentFigure,
					BorderLayout.TOP);

			BorderLayout layoutFFigureEnsembleDescriptionCompartmentFigure = new BorderLayout();
			fFigureEnsembleDescriptionCompartmentFigure
					.setLayoutManager(layoutFFigureEnsembleDescriptionCompartmentFigure);

			WrappingLabel ensemblePlaceholderLabelFigure2 = new WrappingLabel();
			ensemblePlaceholderLabelFigure2.setText(" ");

			ensemblePlaceholderLabelFigure2
					.setFont(ENSEMBLEPLACEHOLDERLABELFIGURE2_FONT);

			ensemblePlaceholderLabelFigure2.setMaximumSize(new Dimension(
					getMapMode().DPtoLP(10), getMapMode().DPtoLP(0)));

			fFigureEnsembleDescriptionCompartmentFigure.add(
					ensemblePlaceholderLabelFigure2, BorderLayout.BOTTOM);

			fFigureEnsembleDescriptionFigure = new WrappingLabel();
			fFigureEnsembleDescriptionFigure.setText("");

			fFigureEnsembleDescriptionCompartmentFigure.add(
					fFigureEnsembleDescriptionFigure, BorderLayout.TOP);

			fFigureEnsembleCompartmentFigure = new RectangleFigure();
			fFigureEnsembleCompartmentFigure
					.setBackgroundColor(FFIGUREENSEMBLECOMPARTMENTFIGURE_BACK);
			fFigureEnsembleCompartmentFigure.setMinimumSize(new Dimension(
					getMapMode().DPtoLP(20), getMapMode().DPtoLP(25)));

			ensembleDescriptionCompartmentContainerFigure0.add(
					fFigureEnsembleCompartmentFigure, BorderLayout.CENTER);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureEnsembleNameFigure() {
			return fFigureEnsembleNameFigure;
		}

		/**
		 * @generated NOT
		 * @author DominicS
		 * 
		 * Changed to support multiline descriptions
		 */
		public WrappingLabel getFigureEnsembleDescriptionFigure() {
			fFigureEnsembleDescriptionFigure.setTextWrap(true);
			return fFigureEnsembleDescriptionFigure;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureEnsembleQueryFigure() {
			return fFigureEnsembleQueryFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getFigureEnsembleCompartmentFigure() {
			return fFigureEnsembleCompartmentFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getFigureEnsembleDescriptionCompartmentFigure() {
			return fFigureEnsembleDescriptionCompartmentFigure;
		}

	}

	/**
	 * @generated
	 */
	static final Font ENSEMBLEPLACEHOLDERLABELFIGURE2_FONT = new Font(
			Display.getCurrent(), Display.getDefault().getSystemFont()
					.getFontData()[0].getName(), 5, SWT.NORMAL);

	/**
	 * @generated
	 */
	static final Color FFIGUREENSEMBLECOMPARTMENTFIGURE_BACK = new Color(null,
			175, 238, 238);

}
