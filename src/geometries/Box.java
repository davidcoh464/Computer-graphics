package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * 
 * @author David&Yishai
 *
 */
public abstract class Box implements Intersectable {

	protected boolean boudingVolume = false; // turn off the bouding volume
	protected boolean infinite = false;
	protected double minX, minY, minZ, maxX, maxY, maxZ;
	protected Point3D middlePoint;

	/**
	 * creating boxes for all shapes in the geometries list and setting the bounding
	 * to be true
	 */
	public void createBox() {
		boudingVolume = true;
		setBox();
		middlePoint = getMiddlePoint();
	}

	/**
	 * abstract function to sat boxes for all shapes
	 */
	protected abstract void setBox();

	/**
	 * check if ray intersect with the box
	 *
	 * @param ray the ray
	 * @return true if intersect
	 */
	public boolean isIntersect(Ray ray) {
		Point3D head = ray.dir.head;
		Point3D p = ray.p0;
		double temp; // for swapping
		double dirX = head.x.coord, dirY = head.y.coord, dirZ = head.z.coord;
		double origX = p.x.coord, origY = p.y.coord, origZ = p.z.coord;

		// Min/Max starts with X
		double tXmin = (minX - origX) / dirX, tXmax = (maxX - origX) / dirX;
		if (tXmin > tXmax) { // swap between tmax and tmin
			temp = tXmin;
			tXmin = tXmax;
			tXmax = temp;
		}
		if (tXmax < 0)
			return false;

		double tYmin = (minY - origY) / dirY, tYmax = (maxY - origY) / dirY;
		if (tYmin > tYmax) { // swap between tmax and tmin
			temp = tYmin;
			tYmin = tYmax;
			tYmax = temp;
		}
		if (tYmax < 0)
			return false;

		if ((tXmin > tYmax) || (tYmin > tXmax))// if the min of some coord bigger from max of other
			return false;
		if (tYmin > tXmin) // the absolute min between y,x is tXmin
			tXmin = tYmin;
		if (tYmax < tXmax)// the absolute max between y,x is tXmax
			tXmax = tYmax;

		double tZmin = (minZ - origZ) / dirZ, tZmax = (maxZ - origZ) / dirZ;
		if (tZmin > tZmax) { // swap between tmax and tmin
			temp = tZmin;
			tZmin = tZmax;
			tZmax = temp;
		}
		if (tZmax < 0)
			return false;

		return tXmin <= tZmax && tZmin <= tXmax;// if xMin > zMax || xMax < zMin return false
	}

	/**
	 * List of Intersection points found in tests Find intersections list between
	 * giving ray and geometries shapes in a bound box. the returned list can be
	 * null or empty if there is no intersections or there is no geometries shape in
	 * Geometries.
	 * 
	 * @param ray         the ray
	 * @param maxDistance maximum distance to geometry for intersetion
	 * @return the list of Points 3D Intersections or a empty list in case the ray
	 *         is not in the box.
	 */
	public List<GeoPoint> findIntsersectionsBound(Ray ray, double maxDistance) {
		return boudingVolume && !isIntersect(ray) ? null : findGeoIntersections(ray, maxDistance);
	}

	/**
	 * @return the middle point of a BVH box.
	 */
	public Point3D getMiddlePoint() {
		return new Point3D((maxX + minX) / 2.0, (maxY + minY) / 2.0, (maxZ + minZ) / 2.0);
	}

}
