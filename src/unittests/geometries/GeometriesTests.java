package unittests.geometries;

import static org.junit.Assert.*;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import geometries.*;
import primitives.*;

import org.junit.Test;

/**
 * Testing Geometries class 
 * @author David&Yishai
 *
 */
public class GeometriesTests {

	/**
	 * Test method for
	 * {@link geometries.Geometries#findGeoIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindGeoIntersections() {
		Geometries geo = new Geometries();
		Sphere sp = new Sphere(new Point3D(20, 30, 40), 10);
		Triangle tri = new Triangle(new Point3D(29, 17, 65), new Point3D(22, 36, 48), new Point3D(52, 57, 55));
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(20, 20, 40));
		geo.add(sp);
		geo.add(tri);
		// only one intersection of ray with sphere in this range
		assertEquals(" only one intersection of ray with sphere in this range",
				List.of(new GeoPoint(sp, new Point3D(20, 20, 40))), geo.findGeoIntersections(ray, 57));
		// two intersection of ray with sphere in this range
		assertEquals(" two intersection of ray with sphere in this range", 2,
				geo.findGeoIntersections(ray, 57.2).size());
		// no intersection of ray with sphere in this range
		assertNull("no intersection of ray with sphere in this range", geo.findGeoIntersections(ray, 48));
		//two intersection of ray with sphere and one with triangle in this range
		assertEquals("two intersection of ray with sphere and one with triangle in this range", 3,
				geo.findGeoIntersections(ray, 71).size());
	}

}
