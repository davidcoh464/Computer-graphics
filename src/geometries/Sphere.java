package geometries;

import java.util.List;

import primitives.*;

/**
 * this class is used to represent a Sphere shape in 3D
 * 
 * @author David&Yishai
 *
 */
public class Sphere extends Geometry {
	Point3D center;
	double radius;

	public Sphere(Point3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "center= " + center + ", radius=" + radius;
	}

	/**
	 * calculate the normal to sphere in specific point
	 */
	public Vector getNormal(Point3D point) {
		return (point.subtract(center)).normalize(); // normalized of (p-O)
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sphere other = (Sphere) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		Point3D p0 = ray.p0;
		Vector v = ray.dir;
		if (center.equals(p0)) {
			if (Util.alignZero(radius - maxDistance) <= 0)
				return List.of(new GeoPoint(this, ray.getPoint(radius)));
			return null;
		}
		Vector u = center.subtract(p0);
		double tm = Util.alignZero(u.dotProduct(v));
		double length = Util.alignZero(u.lengthSquared() - tm * tm); // length square of u reduce square of tm
		if (length < 0)
			return null;
		double d = Math.sqrt(length);
		if (d > radius)
			return null;
		double th = Math.sqrt(radius * radius - d * d);
		if (Util.isZero(th)) // if the ray is tangent to the sphere
			return null;
		double t1 = tm + th;
		double t2 = tm - th;
		if (Util.alignZero(t1) > 0 && (Util.alignZero(t1 - maxDistance) <= 0 && Util.alignZero(t2) > 0
				&& !Util.isZero(t1 - t2) && (Util.alignZero(t2 - maxDistance) <= 0)))
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
		if (Util.alignZero(t1) > 0 && (Util.alignZero(t1 - maxDistance) <= 0)) {
			return List.of(new GeoPoint(this, ray.getPoint(t1))); // p1 = p0 + t1 * v
		}
		if (Util.alignZero(t2) > 0 && !Util.isZero(t1 - t2) && (Util.alignZero(t2 - maxDistance) <= 0)) {
			return List.of(new GeoPoint(this, ray.getPoint(t2))); // p2 = p0 + t2 * v
		}
		return null;
	}

	@Override
	public void setBox() {
		minX = center.x.coord - radius;
		minY = center.y.coord - radius;
		minZ = center.z.coord - radius;
		maxX = center.x.coord + radius;
		maxY = center.y.coord + radius;
		maxZ = center.z.coord + radius;
	}
}
