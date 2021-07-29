package unittests.elements;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import elements.*;
import primitives.*;
import geometries.*;

/**
 * Tests for camera intersections with sphere ,Plane ,Triangle
 * 
 * @author David&Yishai
 *
 */
public class CameraIntegrationTest {

	/**
	 * 
	 * @param camera   camera object to test is intersections
	 * @param geo      the shape that the camera intersect into
	 * @param expected the number of expected intersections
	 * @param output
	 */
	private void assertIntersections(Camera camera, Intersectable geo, int expected, String output) {
		int count = 0;
		camera.setViewPlaneSize(3, 3).setDistance(1);
		List<Point3D> intersections;
		for (int i = 0; i < 3; i++) { // calculating all the camera interactions with the geometric shape
			for (int j = 0; j < 3; j++) {
				intersections = geo.findIntersections(camera.constructRayThroughPixel(3, 3, j, i));
				if (intersections != null)
					count += intersections.size();
			}
		}
		assertEquals(output, expected, count);
	}

	/**
	 * Integration tests of Camera Ray construction with Ray-Sphere,Plane,Triangle
	 * Intersections
	 */
	@Test
	public void cameraIntegrations() {
		Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
		Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));

///////////////////////////////////--Sphere Tests-- //////////////////////////////////

		// TC01: only one pixel interact the sphere twice (2 points)
		assertIntersections(cam1, new Sphere(new Point3D(0, 0, -3), 1), 2, "ERROR - TC01");

		// TC02: all the pixels interact the sphere twice (18 points)
		assertIntersections(cam2, new Sphere(new Point3D(0, 0, -2.5), 2.5), 18, "ERROR - TC02");

		// TC03: Medium sphere (10 points)
		assertIntersections(cam2, new Sphere(new Point3D(0, 0, -2), 2), 10, "ERROR - TC03");

		// TC04: the camera is Inside the sphere (9 points)
		assertIntersections(cam2, new Sphere(new Point3D(0, 0, -2), 4), 9, "ERROR - TC04");

		// TC05: the camera is after the sphere (0 points)
		assertIntersections(cam1, new Sphere(new Point3D(0, 0, 1), 0.5), 0, "ERROR - TC05");

///////////////////////////////////--Plane Tests-- //////////////////////////////////

		// TC06: Plane against camera (9 points)
		assertIntersections(cam1, new Plane(new Point3D(0, 0, -5), new Vector(0, 0, 1)), 9, "ERROR - TC06");

		// TC07: Plane with small angle (9 points)
		assertIntersections(cam1, new Plane(new Point3D(0, 0, -5), new Vector(0, 1, 2)), 9, "ERROR - TC07");

		// TC08: Plane parallel to lower rays (6 points)
		assertIntersections(cam1, new Plane(new Point3D(0, 0, -5), new Vector(0, 1, 1)), 6, "ERROR - TC08");

///////////////////////////////////--Triangle Tests-- //////////////////////////////////

		// TC09: small triangle (1 point)
		assertIntersections(cam1, new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2)),
				1, "ERROR - TC09");

		// TC10: medium triangle (2 points)
		assertIntersections(cam1, new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2)),
				2, "ERROR - TC10");
	}

}
