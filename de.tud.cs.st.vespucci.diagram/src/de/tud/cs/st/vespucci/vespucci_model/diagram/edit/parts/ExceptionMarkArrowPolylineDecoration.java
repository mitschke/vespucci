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
public class ExceptionMarkArrowPolylineDecoration extends PolygonDecoration {
	
	private static final int ARROW_WIDTH = 7;
	private static final int ARROW_HEIGHT = 6;
	private static final int ARROW_MARGIN_RIGHT = 10;
	
	private static final int LINE_XPOS = -27;
	private static final int LINE_Y1 = -5;
	private static final int LINE_Y2 = 2;
	
	private static final int DOT_POS_X = -28;
	private static final int DOT_POS_Y = 5;
	private static final int DOT_RADIUS = 2;
	
	private Transform transform = new Transform();
	private Point location = new Point();

	public ExceptionMarkArrowPolylineDecoration() {
		transform.setScale(1.0);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		graphics.setLineWidth(1);		
		graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		
		paintQuestionMark(graphics);
		
		paintArrow(graphics);
	}
	
	@Override
	public Rectangle getBounds() {
		final Rectangle boundsWithoutArrowAndQM = super.getBounds();
		
		// location may be null at application startup
		if (location == null) {
			return boundsWithoutArrowAndQM;
		}
		
		int heightDiff = 0;
		int yDiff = 0;
		
		if (!boundsWithoutArrowAndQM.contains(location)) {
			// Arrow not visible => correct that
			
			if (location.y < boundsWithoutArrowAndQM.y) {
				heightDiff += boundsWithoutArrowAndQM.y - location.y;				
				yDiff = -heightDiff;
			} else {
				heightDiff += location.y - boundsWithoutArrowAndQM.getBottom().y;
			}
		}
		
		return new Rectangle(
				boundsWithoutArrowAndQM.x,
				boundsWithoutArrowAndQM.y + yDiff,
				boundsWithoutArrowAndQM.width + ARROW_WIDTH + ARROW_MARGIN_RIGHT,
				boundsWithoutArrowAndQM.height + heightDiff);
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
		
		transform.setRotation(angle);
	}
	
	private void paintQuestionMark(Graphics graphics) {
		Point lineStartPoint = new Point(LINE_XPOS, LINE_Y1);
		Point lineEndPoint = new Point(LINE_XPOS, LINE_Y2);

		graphics.drawLine(transform.getTransformed(lineStartPoint), transform.getTransformed(lineEndPoint));
		
		Point dotPoint = new Point(DOT_POS_X, DOT_POS_Y);
		Point dotPointTransl = transform.getTransformed(dotPoint);
		
		graphics.fillRectangle(new Rectangle(dotPointTransl.x, dotPointTransl.y, DOT_RADIUS, DOT_RADIUS));
	}

	private void paintArrow(Graphics graphics) {
		PointList arrowPoints = new PointList(3);
		arrowPoints.addPoint(transform.getTransformed(new Point(-ARROW_WIDTH, ARROW_HEIGHT/2)));
		arrowPoints.addPoint(transform.getTransformed(new Point(0, 0)));
		arrowPoints.addPoint(transform.getTransformed(new Point(-ARROW_WIDTH, -ARROW_HEIGHT/2)));
		
		graphics.drawPolyline(arrowPoints);
	}
	
}
