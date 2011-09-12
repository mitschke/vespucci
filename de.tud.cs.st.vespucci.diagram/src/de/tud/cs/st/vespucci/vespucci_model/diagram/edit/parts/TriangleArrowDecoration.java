package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts;

import java.awt.Color;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.jface.resource.DeviceResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class TriangleArrowDecoration extends PolygonDecoration {
	
	private static final int TRIANGLE_WIDTH = 20;
	private static final int TRIANGLE_HEIGHT = 20;
	private static final int TRIANGLE_RIGHT_MARGIN = 10;
	
	private Transform transform = new Transform();
	private Point location = new Point();

	public TriangleArrowDecoration() {
		transform.setScale(1.0);
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		graphics.setLineWidth(1);
		
		graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		
		Point rectangleStartPoint = new Point(-27, -5);
		Point rectangleStartTranslPoint = transform.getTransformed(rectangleStartPoint);
		Point rectangleEndPoint = new Point(-27, 2);
		Point rectangleEndTranslPoint = transform.getTransformed(rectangleEndPoint);

		graphics.drawLine(rectangleStartTranslPoint, rectangleEndTranslPoint);
		
		Point ovalStartPoint = new Point(-28, 5);
		Point ovalTranslPoint = transform.getTransformed(ovalStartPoint);
		
		graphics.fillRectangle(new Rectangle(ovalTranslPoint.x, ovalTranslPoint.y, 2, 2));
		
		PointList arrowPoints = new PointList(3);
		arrowPoints.addPoint(transform.getTransformed(new Point(-7, 3)));
		arrowPoints.addPoint(transform.getTransformed(new Point(0, 0)));
		arrowPoints.addPoint(transform.getTransformed(new Point(-7, -3)));
		
		graphics.drawPolyline(arrowPoints);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(super.getBounds().x, super.getBounds().y, super.getBounds().width + 17, super.getBounds().height);
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
	
	private PointList getAbsolutePointList(PointList relativePointList) {
		int assignedClipLeft = getBounds().getTopLeft().x;
		int assignedClipTop = getBounds().getTopLeft().y;
		
		int[] absolutePoints = new int[relativePointList.size() * 2];
		
		int cnt = -1;
		for (int coordinate : relativePointList.toIntArray()) {
			absolutePoints[++cnt] =
					cnt % 2 == 0 ?
							coordinate + assignedClipLeft :
							coordinate + assignedClipTop;
		}
		
		return new PointList(absolutePoints);
	}
}
