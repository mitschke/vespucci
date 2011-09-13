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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

/**
 * Decoration which paints an arrow pointing outwards and a question
 * mark which was originally meant to be painted into a triangle having been
 * manually constructed using the setTemplate method as in
 * {@link de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.WarningEditPart#createTargetDecoration}
 * 
 * @author Dominic Scheurer
 */
public class WarningDecoration extends PolygonDecoration {

	private static final int LINE_XPOS = 5;
	private static final int LINE_Y1 = -5;
	private static final int LINE_Y2 = 2;

	private static final int DOT_POS_X = 5;
	private static final int DOT_POS_Y = 4;
	
	private static final Point TRIANGLE_P1 = new Point(15, 8);
	private static final Point TRIANGLE_P2 = new Point(5, -9);
	private static final Point TRIANGLE_P3 = new Point(-5, 8);

	private Transform transform = new Transform();
	private Point location = new Point();

	public WarningDecoration() {
		transform.setScale(1.0);
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setLineWidth(1);
		graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

		paintTriangle(graphics);

		paintExclamationMark(graphics);
	}

	@Override
	public Rectangle getBounds() {
		int xLeft = Integer.MAX_VALUE, xRight = 0, yTop = Integer.MAX_VALUE, yBottom = 0;
		PointList trianglePoints = createTrianglePoints();

		if (trianglePoints.size() < 3) {
			return super.getBounds(); // May happen at diagram startup
		}

		for (int i = 0; i < trianglePoints.size(); i++) {
			Point trianglePoint = trianglePoints.getPoint(i);
			int x = trianglePoint.x;
			int y = trianglePoint.y;

			if (x < xLeft) {
				xLeft = x;
			}

			if (x > xRight) {
				xRight = x;
			}

			if (y < yTop) {
				yTop = y;
			}

			if (y > yBottom) {
				yBottom = y;
			}
		}

		return new Rectangle(xLeft - 1, yTop - 1, xRight - xLeft + 2, yBottom - yTop + 2);
	}

	@Override
	public void setReferencePoint(Point ref) {
		super.setReferencePoint(ref);

		Point pt = Point.SINGLETON;
		pt.setLocation(ref);
		pt.negate().translate(location);
		setRotation(Math.atan2(pt.y, pt.x));
	}

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);

		location.setLocation(p);
		transform.setTranslation(p.x, p.y);
	}

	@Override
	public void setRotation(double angle) {
		super.setRotation(angle);

		// Triangle shall not be turned upside down
		if (Math.abs(angle) >= Math.PI / 2) {
			if (angle > 0) {
				angle -= Math.PI;
			} else {
				angle += Math.PI;
			}
		}

		transform.setRotation(angle);
	}

	private PointList createTrianglePoints() {
		PointList trianglePoints = new PointList();
		
		if (transform == null) {
			// Transform object may be null at application startup...
			return trianglePoints;
		}

		trianglePoints.addPoint(transform.getTransformed(TRIANGLE_P1));
		trianglePoints.addPoint(transform.getTransformed(TRIANGLE_P2));
		trianglePoints.addPoint(transform.getTransformed(TRIANGLE_P3));
		trianglePoints.addPoint(transform.getTransformed(TRIANGLE_P1));

		return trianglePoints;
	}

	private void paintTriangle(Graphics graphics) {
		graphics.setBackgroundColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		graphics.fillPolygon(createTrianglePoints());
		graphics.drawPolygon(createTrianglePoints());
	}

	private void paintExclamationMark(Graphics graphics) {
		Point lineStartPoint = new Point(LINE_XPOS, LINE_Y1);
		Point lineEndPoint = new Point(LINE_XPOS, DOT_POS_Y + 2);

		graphics.drawLine(transform.getTransformed(lineStartPoint), transform.getTransformed(lineEndPoint));

		graphics.setBackgroundColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		Point separatorTopLeftPoint = new Point(LINE_XPOS - 2, LINE_Y2);
		Point separatorBottomRightPoint = new Point(LINE_XPOS + 2, DOT_POS_Y);

		PointList separatorPoints = new PointList(5);

		separatorPoints.addPoint(transform.getTransformed(separatorTopLeftPoint));
		separatorPoints.addPoint(transform.getTransformed(new Point(separatorBottomRightPoint.x, separatorTopLeftPoint.y)));
		separatorPoints.addPoint(transform.getTransformed(separatorBottomRightPoint));
		separatorPoints.addPoint(transform.getTransformed(new Point(separatorTopLeftPoint.x, separatorBottomRightPoint.y)));
		separatorPoints.addPoint(transform.getTransformed(separatorTopLeftPoint));

		graphics.fillPolygon(separatorPoints);
	}

}
