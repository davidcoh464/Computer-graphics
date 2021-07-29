package unittests.geometries;

import geometries.*;
import primitives.*;
import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class PlanTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		try {
			new Plane(new Point3D(1, 0, 0), new Point3D(2, 0, 0), new Point3D(3, 0, 0));
			fail("this points create a line not a plane");
		} catch (Exception e) {
			// if the ctor send an error like it's should do
		}
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 * 
	 */
	@Test
	public void GetNormal() {
		Plane v = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
		double root_3 = -1 / Math.sqrt(3);
		assertEquals(new Vector(root_3, root_3, root_3), v.getNormal());
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Plane v = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
		double root_3 = -1 / Math.sqrt(3);
		assertEquals(new Vector(root_3, root_3, root_3), v.getNormal(new Point3D(1, 1, 1)));
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersection(primitives.Ray)}.
	 */
	@Test
	public void findIntersection() {
		Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// T1: Ray intersect the plane (1 points)
		Ray ray1 = new Ray(new Point3D(0, 0, 1), new Vector(1, 0, -1));
		LinkedList<Point3D> list1 = new LinkedList<>();
		list1.add(new Point3D(1, 0, 0));
		assertEquals(list1, plane.findIntersections(ray1));

		// T2: Ray does not intersect the plane (0 points)
		Ray ray2 = new Ray(new Point3D(0, 0, 1), new Vector(-1, 0, -1));
		assertNull(plane.findIntersections(ray2));

		// =============== Boundary Values Tests ==================

		// T3: Ray is included in the plane (0 point)
		Ray ray3 = new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1));
		assertNull(plane.findIntersections(ray3));

		// T4: Ray is parallel to the plane (0 point)
		Ray ray4 = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 2));
		assertNull(plane.findIntersections(ray4));

		// T5: Ray is orthogonal to the plane and start before the plane (1 point)
		Ray ray5 = new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0));
		LinkedList<Point3D> list5 = new LinkedList<>();
		list5.add(new Point3D(1, 0, 0));
		assertEquals(list5, plane.findIntersections(ray5));

		// T6: Ray is orthogonal to the plane and start in the plane (0 point)
		Ray ray6 = new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0));
		assertNull(plane.findIntersections(ray6));

		// T7: Ray is orthogonal to the plane and start after the plane (0 point)
		Ray ray7 = new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0));
		assertNull(plane.findIntersections(ray7));

		// T8:Ray is neither orthogonal nor parallel to and begins at the plane
		// (p0 is in the plane, but not the ray) (0 point)
		Ray ray8 = new Ray(new Point3D(1, 1, 0), new Vector(1, 1, 0));
		assertNull(plane.findIntersections(ray8));

		// T9: Ray is neither orthogonal nor parallel to the plane and begins in
		// the same point which appears as reference point in the plane (Q0) (0 point)
		Ray ray9 = new Ray(new Point3D(1, 0, 0), new Vector(1, 1, 0));
		assertNull(plane.findIntersections(ray9));
	}
}
