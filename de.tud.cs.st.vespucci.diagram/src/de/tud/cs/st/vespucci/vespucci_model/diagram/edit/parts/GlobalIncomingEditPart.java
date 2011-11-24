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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.diagram.supports.CompartmentEditPartSupporter;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.impl.ConnectionImpl;

/**
 * @generated
 */
public class GlobalIncomingEditPart extends ConnectionNodeEditPart implements
		ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4006;

	/**
	 * @generated
	 */
	public GlobalIncomingEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies.GlobalIncomingItemSemanticEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingNameEditPart) {
			((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingNameEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureGlobalIncomingNameFigure());
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
		super.addChildVisual(childEditPart, index);
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingNameEditPart) {
			return true;
		}
		return false;
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
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */

	protected Connection createConnectionFigure() {
		return new GlobalIncomingFigure();
	}

	/**
	 * @generated
	 */
	public GlobalIncomingFigure getPrimaryShape() {
		return (GlobalIncomingFigure) getFigure();
	}

	/**
	 * @generated NOT
	 */
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);
		if (notification.getFeature() instanceof EReferenceImpl) {
			EReferenceImpl ref = (EReferenceImpl) notification.getFeature();
			if (ref.getFeatureID() == Vespucci_modelPackage.CONNECTION__ORIGINAL_SOURCE
					|| ref.getFeatureID() == Vespucci_modelPackage.CONNECTION__ORIGINAL_TARGET) {
				if (((ConnectionImpl) ((View) this.getModel()).getElement())
						.isTemp()) {
					this.getFigure().setForegroundColor(
							CompartmentEditPartSupporter.TMP_CONNECTION_COLOR);
				} else {
					this.getFigure().setForegroundColor(
							CompartmentEditPartSupporter.CONNECTION_COLOR);
				}
			}

		}

		if (notification.getFeature() instanceof EAttributeImpl) {
			EAttributeImpl eai = (EAttributeImpl) notification.getFeature();
			if (eai.getFeatureID() == Vespucci_modelPackage.CONNECTION__TEMP) {
				if (notification.getNewBooleanValue()) {
					this.getFigure().setForegroundColor(
							CompartmentEditPartSupporter.TMP_CONNECTION_COLOR);
				} else {
					this.getFigure().setForegroundColor(
							CompartmentEditPartSupporter.CONNECTION_COLOR);
				}

			}

		}
	}

	/**
	 * @generated
	 */
	public class GlobalIncomingFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureGlobalIncomingNameFigure;

		/**
		 * @generated
		 */
		public GlobalIncomingFigure() {
			this.setForegroundColor(ColorConstants.black);

			createContents();
			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureGlobalIncomingNameFigure = new WrappingLabel();
			fFigureGlobalIncomingNameFigure.setText("all");

			this.add(fFigureGlobalIncomingNameFigure);

		}

		/**
		 * @generated NOT
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			df.setFill(true);
			df.setBackgroundColor(ColorConstants.white);
			PointList pl = new PointList();
			pl.addPoint(getMapMode().DPtoLP(-10), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-10), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(-11), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(-13), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(-15), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(-17), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(-18), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(-18), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(-17), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(-15), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(-13), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(-11), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(-10), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(-10), getMapMode().DPtoLP(0));

			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-7), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-7), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			df.setTemplate(pl);
			// Changed by Dominic Scheurer: Scale from 7/3 to 1/1
			df.setScale(getMapMode().DPtoLP(1), getMapMode().DPtoLP(1));
			return df;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureGlobalIncomingNameFigure() {
			return fFigureGlobalIncomingNameFigure;
		}

	}

}
