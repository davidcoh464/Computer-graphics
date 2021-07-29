package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import geometries.*;
import primitives.*;
import org.junit.Test;

public class TriangleTests {

	@Test
	public void testfindIntersections() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(1, 1, 0), new Point3D(2, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01:Ray is intersecting in triangle (1 Points)
		Ray ray1 = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 0, -1));
		assertEquals(List.of(new Point3D(1, 0.5, 0)), tr.findIntersections(ray1));
		// TC02:Ray is not intersecting with triangle and is parallel to the edge (0
		// Points)
		Ray ray2 = new Ray(new Point3D(0.5, -0.5, 1), new Vector(0, 0, -1));
		assertNull(tr.findIntersections(ray2));
		// TC03:Ray is not intersecting with triangle and is parallel to the vertex (0
		// Points)
		Ray ray3 = new Ray(new Point3D(Math.sqrt(2) - 1, 0.5, 1), new Vector(0, 0, -1));
		assertNull(tr.findIntersections(ray3));

		// =============== Boundary Values Tests ==================
		
		// TC04:Ray is intersecting with triangle on edge (0 Points)
		Ray ray4 = new Ray(new Point3D(0.5, 0.5, 1), new Vector(new Point3D(0, 0, -1)));
		assertNull(tr.findIntersections(ray4));

		// TC05:Ray is intersecting with triangle on vertex (0 Points)
		Ray ray5 = new Ray(new Point3D(1, 1, 1), new Vector(new Point3D(0, 0, -1)));
		assertNull(tr.findIntersections(ray5));

		// TC06:Ray is intersecting with the continuation of the edge (0 Points)
		Ray ray6 = new Ray(new Point3D(2, 2, 1), new Vector(new Point3D(0, 0, -1)));
		assertNull(tr.findIntersections(ray6));
	}

}
