package primitives;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

/**
 * 
 * @author david&yishai
 *
 */
public class Ray {
	private static final double DELTA = 0.1;
	public Point3D p0;
	public Vector dir;

	@Override
	public String toString() {
		return "p0=" + p0 + ", dir= " + dir;
	}

	/**
	 * 
	 * @param point  point of start for ray
	 * @param vector direction vector for ray
	 */
	public Ray(Point3D point, Vector vector) {
		p0 = point;
		dir = vector.normalized();
	}

	/**
	 * Instantiates a new Ray and moves the point by 0.1 in the normal direction
	 *
	 * @param point     point in 3D
	 * @param direction the direction
	 * @param n         vector normal to point
	 */
	public Ray(Point3D point, Vector direction, Vector n) {
		this(point, direction);
		Vector delta = n.scale(Util.alignZero(n.dotProduct(dir)) > 0 ? DELTA : -DELTA);// n*delta
		p0 = point.add(delta);// point + (n*delta)
	}

	/**
	 * 
	 * @param t number to multiply the vector with
	 * @return the point of start + (direction vector * t)
	 */
	public Point3D getPoint(double t) {
		if (Util.isZero(t))
			return p0;
		return p0.add(dir.scale(t));
	}

	/**
	 * this function generat beam of rays when radius is bigger our beam spread on
	 * more area
	 * 
	 * @param n         normal vector of the point where beam start
	 * @param radius    radius of virtual circle
	 * @param distance  Distance between The intersetion point to the virtual circle
	 * @param numOfRays The number of the rays of the beam
	 * @return beam of rays
	 */
	public List<Ray> generateBeam(Vector n, double radius, double distance, int numOfRays) {
		List<Ray> rays = new LinkedList<Ray>();
		rays.add(this);// Includeing the main ray
		if (numOfRays == 1 || isZero(radius))// The component (glossy surface /diffuse glass) is turned off
			return rays;
		Vector nX = dir.createNormal();
		Vector nY = dir.crossProduct(nX);
		Point3D centerCircle = this.getPoint(distance); // p0+(v*distance)
		Point3D randomPoint;
		double x, y; // , r, theta, pi = Math.PI;
		double nv = alignZero(n.dotProduct(dir));
		for (int i = 1; i < numOfRays; ++i) {
			while (true) {
				x = Util.alignZero(Util.random(-radius, radius));
				y = Util.alignZero(Util.random(-radius, radius));
				if (x * x + y * y <= radius * radius)
					break;
			}
			randomPoint = centerCircle; // pc
			if (x != 0)
				randomPoint = randomPoint.add(nX.scale(x));// pc+(nX*x)
			if (y != 0)
				randomPoint = randomPoint.add(nY.scale(y));// pc+(nY*y)
			Vector tPoint = randomPoint.subtract(p0);// random point inside the circle subtract the start point
			double nt = alignZero(n.dotProduct(tPoint));
			if (nv * nt > 0) {// sign(nv) == sing(nt)
				rays.add(new Ray(p0, tPoint));
			}
		}
		return rays;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return this.p0.equals(other.p0) && this.dir.equals(other.dir);
	}

	/**
	 * 
	 * @param lstPoint list of intersections points
	 * @return the closes point of intersection to the ray start point
	 */
	public Point3D findClosestPoint(List<Point3D> lstPoint) {
		Point3D closesPoint = null;
		double minDistance = Double.MAX_VALUE;
		for (Point3D point : lstPoint) {
			double distance = p0.distance(point);
			if (distance < minDistance) {
				minDistance = distance;
				closesPoint = point;
			}
		}
		return closesPoint;
	}

	/**
	 * 
	 * @param lstGeoPoint list of intersections points
	 * @return the closes geoPoint of intersection to the ray start point
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lstGeoPoint) {
		GeoPoint closesPoint = null;
		double minDistance = Double.MAX_VALUE;
		for (GeoPoint geoPoint : lstGeoPoint) {
			double distance = p0.distance(geoPoint.point);
			if (distance < minDistance) {
				minDistance = distance;
				closesPoint = geoPoint;
			}
		}
		return closesPoint;
	}
}
