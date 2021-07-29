package geometries;

import static primitives.Util.*;
import java.util.List;
import primitives.*;

/**
 * this class is used to represent a tube shape in 3D
 * 
 * @author David&Yishai
 *
 */
public class Tube extends Geometry {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tube other = (Tube) obj;
		if (axisRay == null) {
			if (other.axisRay != null)
				return false;
		} else if (!axisRay.equals(other.axisRay))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}

	protected Ray axisRay;
	protected double radius;

	/**
	 * ctor that get to values ray and double
	 * 
	 * @param axisRay ray that have point of start and vector for direction
	 * @param radius
	 */
	public Tube(Ray axisRay, double radius) {
		this.axisRay = axisRay;
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "axisRay= " + axisRay + ", radius=" + radius;
	}

	/**
	 * get point and calculate the normal to it
	 * 
	 * @param p point in 3D of which we want the normal
	 * @return the normal to the sending point
	 */
	public Vector getNormal(Point3D p) {
		Vector getDir = axisRay.dir; // v
		Point3D getP0 = axisRay.p0; // p0
		double t;
		try {
			t = p.subtract(getP0).dotProduct(getDir); // t = v * (p - p0)
		} catch (IllegalArgumentException e) { // if (p - p0) == 0 the normal is v
			return getDir;
		}

		if (!Util.isZero(t)) { // if t == 0 the normal is just normalize(p - p0)
			// projection of P - P0 on the ray:
			getP0 = getP0.add(getDir.scale(t)); // O = p0 + (t*v)
		}
		return p.subtract(getP0).normalize(); // normalized vector of (p-O)
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		Point3D p0 = ray.p0;
		Point3D pa = axisRay.p0;
		Vector v = ray.dir;
		Vector va = axisRay.dir;
		Vector daltaVec = null;
		try {
			daltaVec = p0.subtract(pa);
		} catch (Exception e) {
			// the ray of tube and ray that candidate to intersect are the same
			if (v.equals(va) || v.equals(va.scale(-1)))
				return null;
		}
		Vector temp1, temp2;
		double _scale = alignZero(v.dotProduct(va));
		try {
			temp1 = v.subtract(va.scale(_scale));
		} catch (Exception e) {
			if (_scale == 0) {
				// v is ortogonal to va
				temp1 = v;
			} else {
				// v and va are parallal
				return null;
			}
		}
		double a, b, c, discriminant;
		a = alignZero(temp1.lengthSquared()); // if temp1 is null, already returned null
		if (daltaVec == null || isZero(daltaVec.dotProduct(va)))
			temp2 = daltaVec;
		else {
			try {
				temp2 = daltaVec == null ? temp2 = null
						: daltaVec.subtract(va.scale(alignZero(daltaVec.dotProduct(va))));
			} catch (IllegalArgumentException e) {
				// va is equal to dalta vector by scale
				temp2 = null;
			}
		}
		b = temp2 == null ? 0d : 2 * (alignZero(temp1.dotProduct(temp2)));
		c = temp2 == null ? -radius * radius : alignZero(temp2.lengthSquared() - radius * radius);
		discriminant = alignZero(b * b - 4 * a * c);
		if (discriminant < 0)
			// no intersection
			return null;
		if (discriminant == 0) {
			// in this case we got one solution - means maximum one intersection
			// if The ray starting outside and we got only one solution that means is a
			// tangens point - no intersection
			if (p0.distance(pa) > radius)
				return null;
			double t = alignZero((-1 * b) / (2 * a));// a = temp1, so in this point, a for sure not zero

			if (t <= 0) {
				// no intersection(the intersection in the opposite direction)
				// or (if t=0) the ray start at the tube and we have only tangent point
				return null;
			}
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		}
		// at this point we know that we have two solutions we need to choose the
		// relevant(s)(positive ones)
		discriminant = Math.sqrt(discriminant);
		double solution_1 = alignZero(((-b) + discriminant) / (2 * a));
		double solution_2 = alignZero((-b) - discriminant) / (2 * a);
		double dis1 = alignZero(solution_1 - maxDistance);
		double dis2 = alignZero(solution_2 - maxDistance);
		if (solution_1 > 0 && dis1 <= 0 && solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)), new GeoPoint(this, ray.getPoint(solution_2)));
		}
		if (solution_1 > 0 && dis1 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)));
		}
		if (solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_2)));
		}
		return null;
	}

	@Override
	protected void setBox() {
		minX = minY = minZ = Double.NEGATIVE_INFINITY;
		maxX = maxY = maxZ = Double.POSITIVE_INFINITY;
		infinite = true;
	}

}
