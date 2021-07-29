package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * this class represents a Cylinder shape that exten from tube shape
 * 
 * @author David&Yishai
 *
 */
public class Cylinder extends Tube {
	double height;

	/**
	 * ctor for cylinder class
	 * 
	 * @param axisRay
	 * @param radius
	 * @param height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	@Override
	public String toString() {
		return "height=" + height + super.toString();
	}

	/**
	 * Get Normal to Cylinder by the point3D
	 *
	 * @param p point in the cylinder
	 * @return normal vector
	 **/
	@Override
	public Vector getNormal(Point3D p) {
		Point3D getP0 = axisRay.p0; // p0
		Vector getDir = axisRay.dir; // v

		// projection of P-O on the ray:
		double t;
		try {
			t = p.subtract(getP0).dotProduct(getDir); // (p-p0) * v
		} catch (IllegalArgumentException e) { // if (p - p0) == 0
			return getDir;
		}

		// if the point is at a base
		if (Util.isZero(t) || Util.isZero(height - t))
			return getDir;

		// point is outside
		getP0 = getP0.add(getDir.scale(t)); // p0 + v*t
		return p.subtract(getP0).normalize(); // normalize of (p - p0)
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		Point3D centerP = axisRay.p0;
		Vector cylinderDir = axisRay.dir;
		List<GeoPoint> intersectios = super.findGeoIntersections(ray, maxDistance);
		List<GeoPoint> toReturn = null;
		// Check if there are intersections with the bottum of cylinder and/or the top
		// cylinder
		Plane buttomCap = new Plane(centerP, cylinderDir);
		Point3D pointAtTop = new Point3D(centerP.add(cylinderDir.scale(height)));
		Plane topCap = new Plane(pointAtTop, cylinderDir);
		List<GeoPoint> intsB = buttomCap.findGeoIntersections(ray, maxDistance);
		List<GeoPoint> intsT = topCap.findGeoIntersections(ray, maxDistance);
		if (intsT != null) {
			GeoPoint topInter = intsT.get(0);
			double d = Util.alignZero(topInter.point.distance(pointAtTop) - radius);
			if (d < 0) {
				// intersect the top
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				topInter.geometry = this;
				toReturn.add(topInter);
			}
		}
		if (intsB != null) {
			GeoPoint bInter = intsB.get(0);
			double d = Util.alignZero(bInter.point.distance(centerP) - radius);
			if (d < 0) {
				// intersect the buttom
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				bInter.geometry = this;
				toReturn.add(bInter);
			}
		}
		if (toReturn != null && toReturn.size() == 2) // The maximum intersection points are 2
			return toReturn;
		if (intersectios == null) {
			return toReturn;
		}
		// In this point We knows that we got minimum 1 intersection point from the
		// tube.
		// check if intersection point(s) of tube relevant also for the cylinder
		GeoPoint gPoint = intersectios.get(0);
		gPoint.geometry = this;
		intsT = topCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir), maxDistance);
		intsB = buttomCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), maxDistance);
		if (intsT != null && intsB != null) {
			if (toReturn == null)
				toReturn = new LinkedList<GeoPoint>();
			toReturn.add(gPoint);
		}
		if (intersectios.size() == 2) {
			gPoint = intersectios.get(1);
			gPoint.geometry = this;
			intsT = topCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir), maxDistance);
			intsB = buttomCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), maxDistance);
			if (intsT != null && intsB != null) {
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				toReturn.add(gPoint);
			}
		}
		return toReturn;
	}

	@Override
	protected void setBox() {
		Point3D center = axisRay.p0;
		maxX = minX = center.x.coord;
		maxY = minY = center.y.coord;
		maxZ = minZ = center.z.coord;
		Point3D centerHeight = this.axisRay.getPoint(height);
		double centerHeightX = centerHeight.x.coord;
		double centerHeightY = centerHeight.y.coord;
		double centerHeightZ = centerHeight.z.coord;
		if (minX > centerHeightX)
			minX = centerHeightX;
		if (minY > centerHeightY)
			minY = centerHeightY;
		if (minZ > centerHeightZ)
			minZ = centerHeightZ;
		minX -= radius;
		minY -= radius;
		minZ -= radius;
		if (maxX < centerHeightX)
			maxX = centerHeightX;
		if (maxY < centerHeightY)
			maxY = centerHeightY;
		if (maxZ < centerHeightZ)
			maxZ = centerHeightZ;
		maxX += radius;
		maxY += radius;
		maxZ += radius;
	}

}
