package unittests.primitives;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.*;
import primitives.*;

/**
 * Testing for Ray class
 * 
 * @author David&Yishai
 *
 */
public class RayTests {

	/**
	 * Test method for
	 * {@link primitives.Ray#findClosestPoint(List<primitives.Point3D>)}.
	 */
	@Test
	public void findClosestPointTest() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(1, 1, 0));
		// TC01 find the closest point from an empty list of points
		assertNull(ray.findClosestPoint(List.of()));
		// TC02 find the closest point when the closest point is in the first place in
		// the list
		Point3D point = ray.findClosestPoint(List.of(new Point3D(1, 1, 0), new Point3D(2, 2, 0), new Point3D(3, 3, 0)));
		assertEquals(new Point3D(1, 1, 0), point);
		// TC03 find the closest point when the closest point is in the last place in
		// the list
		point = ray.findClosestPoint(List.of(new Point3D(3, 3, 0), new Point3D(2, 2, 0), new Point3D(1, 1, 0)));
		assertEquals(new Point3D(1, 1, 0), point);
	}

	/**
	 * Test method for
	 * {@link primitives.Ray#findClosestGeoPoint(List<primitives.GeoPoint>)}.
	 */
	@Test
	public void findClosestGeoPointTest() {

		Ray ray = new Ray(Point3D.ZERO, new Vector(1, 1, 0));
		Point3D point1 = new Point3D(0, 0, 0);
		Point3D point2 = new Point3D(1, 0, 4);
		Point3D point3 = new Point3D(0, 1, 3);
		Sphere sphere = new Sphere(point1, 2);
		Triangle triangle = new Triangle(point1, point2, point3);
		Plane plane = new Plane(point1, point2, point3);
		// TC01 find the closest point from an empty list of geoPoints
		assertNull("TC01 empty list of geoPoints", ray.findClosestGeoPoint(List.of()));
		// TC02 find the closest point when the closest point is in the first place in
		// the list
		GeoPoint geoPoint = ray.findClosestGeoPoint(List.of(new GeoPoint(triangle, new Point3D(1, 1, 0)),
				new GeoPoint(sphere, new Point3D(2, 2, 0)), new GeoPoint(plane, new Point3D(3, 3, 0))));
		assertEquals("TC02 closest geoPoint is in the first place in the list", new GeoPoint(triangle, new Point3D(1, 1, 0)),
				geoPoint);
		// TC03 find the closest point when the closest geoPoint is in the last place in
		// the list
		geoPoint = ray.findClosestGeoPoint(List.of(new GeoPoint(triangle, new Point3D(3, 3, 0)),
				new GeoPoint(sphere, new Point3D(2, 2, 0)), new GeoPoint(plane, new Point3D(1, 1, 0))));
		assertEquals("TC03 closest geoPoint is in the last place in the list",new GeoPoint(plane, new Point3D(1, 1, 0)), geoPoint);

	}

}
