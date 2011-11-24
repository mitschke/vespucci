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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
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
import org.eclipse.swt.graphics.Color;

import de.tud.cs.st.vespucci.diagram.dnd.EditDropPolicy;

/**
 * @generated
 */
public class Empty2EditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3003;

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
	public Empty2EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.Empty2ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
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
		return primaryShape = new EmptyFigure();
	}

	/**
	 * @generated
	 */
	public EmptyFigure getPrimaryShape() {
		return (EmptyFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyName2EditPart) {
			((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyName2EditPart) childEditPart)
					.setLabel(getPrimaryShape().getFigureEmptyNameFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyName2EditPart) {
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
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(20, 20);
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
				.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyName2EditPart.VISUAL_ID));
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
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
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
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		} else if (relationshipType == de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Violation_4009) {
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_2002);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Empty_3003);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public class EmptyFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureEmptyNameFigure;

		/**
		 * @generated
		 */
		public EmptyFigure() {
			this.setBackgroundColor(ColorConstants.lightGray);
			this.setPreferredSize(new Dimension(getMapMode().DPtoLP(20),
					getMapMode().DPtoLP(20)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureEmptyNameFigure = new WrappingLabel();
			fFigureEmptyNameFigure.setText("");

			this.add(fFigureEmptyNameFigure);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureEmptyNameFigure() {
			return fFigureEmptyNameFigure;
		}

	}

}
