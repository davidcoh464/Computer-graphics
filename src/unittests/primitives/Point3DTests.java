package unittests.primitives;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Point3D;
import primitives.Vector;

public class Point3DTests {

	@Test
	public void testAdd() {
		Point3D p1 = new Point3D(1, 1, 2.1);
		Point3D val = p1.add(new Vector(1, 2.67, 1));
		assertEquals(new Point3D(2, 3.67, 3.1), val);
	}

	@Test
	public void testSubtract() {
		Point3D p1 = new Point3D(1, 1, 2.1);
		Vector val = p1.subtract(new Point3D(1, 2.67, 1));
		assertEquals(new Vector(0, -1.67, 1.1), val);
	}

	@Test
	public void testDistanceSquared() {
		Point3D p1 = new Point3D(3, 4, 5);
		double val = p1.distanceSquared(new Point3D(1, 2, 7));
		assertEquals(12, val, 0.0001);
	}

	@Test
	public void testDistance() {
		Point3D p1 = new Point3D(3, 4, 5);
		double val = p1.distance(new Point3D(1, 2, 7));
		assertEquals(Math.sqrt(12), val, 0.0001);
	}

}
