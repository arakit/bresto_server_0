package test;

import java.awt.geom.Point2D;

/* 各種計算 */

public class Support {
	Boolean PointExploded(Point2D.Double point) {
		return (Double.isNaN(point.x) || Double.isNaN(point.y) );
	}
	
	Point2D.Double PointAdd(Point2D.Double a, Point2D.Double b) {
		Point2D.Double r = new Point2D.Double(a.x + b.x, a.y + b.y);
		return r;
	}
	
	Point2D.Double PointSubtract(Point2D.Double a, Point2D.Double b) {
		Point2D.Double res = new Point2D.Double(a.x - b.x, a.y - b.y);
		return res;
	}
	
	Point2D.Double PointMultiply(Point2D.Double a, Point2D.Double b) {
		Point2D.Double res = new Point2D.Double(a.x * b.x, a.y * b.y);
		return res;
	}
	
	Point2D.Double PointScale(Point2D.Double point, double scale) {
		Point2D.Double res = new Point2D.Double(point.x * scale, point.y * scale);
		return res;
	}
	
	Point2D.Double PointDivide(Point2D.Double point, double n) {
		Point2D.Double res = new Point2D.Double(point.x / n, point.y / n);
		return res;
	}
	
	double PointDistance(Point2D.Double start, Point2D.Double finish) {
		double xdelta = finish.x - start.x;
		double ydelta = finish.y - start.y;
		return Math.sqrt(xdelta * xdelta + ydelta * ydelta);
	}
	
	double PointMagnitude(Point2D.Double point) {
		double m = Math.sqrt( (point.x * point.x) + (point.y * point.y) );
		return m;
	}
	
	Point2D.Double PointNormal(Point2D.Double point) {
		Point2D.Double res = new Point2D.Double(point.x, point.y);
		return res;
	}
	
	Point2D.Double PointNormalize(Point2D.Double point) {
		Point2D.Double res = PointDivide(point, PointMagnitude(point));
		return res;
	}
	
	Point2D.Double PointRandom(double radius) {
		double targetRadius = (radius > 0) ? radius : 0.0;
		Point2D.Double res = new Point2D.Double( 2 * targetRadius * (Math.random() - 0.5), 2 * targetRadius * (Math.random() - 0.5) );
		return res;
	}
	
	Point2D.Double PointNearPoint(Point2D.Double center_pt, double radius) {
		double targetRadius = (radius > 0) ? radius : 0.0;
		double x = center_pt.x;
		double y = center_pt.y;
		double d = targetRadius * 2;
		
		Point2D.Double res = new Point2D.Double(x - targetRadius + Math.random() * d, y - targetRadius + Math.random() * d);
		return res;
	}
	
	/* 円の半径を求める
	 * 入力された文字列が円の内側に入るようにするため、文字列の四つの頂点から外接円を求めた
	 */
	double CircleRadius(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3, Point2D.Double p4) {
		double res;
		double A, B, C, D;
		A = this.PointDistance(p1, p2); B = this.PointDistance(p2, p3); C = this.PointDistance(p3, p4); D = this.PointDistance(p4, p1);
		res = Math.sqrt(((A * B + C * D) * (A * C + B * D) * (A * D + B * C)) / ((-A + B + C + D) * (A - B + C + D) * (A + B - C + D) * (A + B + C - D)));
		return res;
	}
}
