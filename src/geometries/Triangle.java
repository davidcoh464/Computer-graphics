package geometries;

import static primitives.Util.isZero;

import java.util.List;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * this class represent a triangle in a 3D that inherited from a Polygon shape
 * 
 * @author David&Yishai
 *
 */
public class Triangle extends Polygon {

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * ctor that get three points
	 * 
	 * @param point_a first point
	 * @param point_b second point
	 * @param point_c third point
	 */
	public Triangle(Point3D point_a, Point3D point_b, Point3D point_c) {
		super(point_a, point_b, point_c);
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> intersections = plane.findGeoIntersections(ray, maxDistance);
		// First of all, check if there is a point of intersection with the plane
		if (intersections == null)
			return null;
		Vector v = ray.dir;
		Point3D p0 = ray.p0;
		Vector v1 = vertices.get(0).subtract(p0);
		Vector v2 = vertices.get(1).subtract(p0);
		Vector v3 = vertices.get(2).subtract(p0);
		
		double t = v.dotProduct(v1.crossProduct(v2).normalize());
		if (isZero(t)) {
			return null;
		}
		boolean sign = t > 0;
		t = v.dotProduct(v2.crossProduct(v3).normalize());
		if (isZero(t) || sign != (t > 0)) {
			return null;
		}
		t = v.dotProduct(v3.crossProduct(v1).normalize());
		if (isZero(t) || sign != (t > 0)) {
			return null;
		}
		intersections.get(0).geometry = this;
		return intersections;
	}

}
