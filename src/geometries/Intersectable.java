package geometries;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import primitives.*;

/**
 * interface for intersectable shapes
 * 
 * @author David&Yishai
 *
 */
public interface Intersectable {
	/**
	 * find all the intersections of ray with the geometry
	 * 
	 * @param ray ray to check for intersections
	 * @return list of all the intersections
	 */
	default List<Point3D> findIntersections(Ray ray) {
		List<GeoPoint> geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	/**
	 * find all the intersections of ray with the geometry
	 * 
	 * @param ray ray to check for intersections
	 * @return list of all intersections with the geometry that intersected it
	 */
	default List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
	/**
	 *  find all the intersections of ray with the geometry
	 * @param ray ray to check for intersections
	 * @param maxDistance 
	 * @return list of all intersections with the geometry that intersected it
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

	/**
	 * intarnel static class that combain geometry ith point
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/**
		 * ctor for geoPoint class
		 * 
		 * @param geo some geometric
		 * @param p   point 3D
		 */
		public GeoPoint(Geometry geo, Point3D p) {
			geometry = geo;
			point = p;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint other = (GeoPoint) obj;
			return Objects.equals(this.geometry, other.geometry) && this.point.equals(other.point);
		}

	}
}
