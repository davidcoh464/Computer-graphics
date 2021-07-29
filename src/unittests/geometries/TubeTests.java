package unittests.geometries;

import static org.junit.Assert.*;
import primitives.*;
import geometries.*;

import org.junit.Test;

public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// test 1
		Ray ray = new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0));
		Tube tube = new Tube(ray, 0);
		Vector val = tube.getNormal(new Point3D(2, 3, 4));
		double root_20 = Math.sqrt(20); // the expected length
		assertEquals(new Vector(0, 2 / root_20, 4 / root_20), val);
		// test 2
		ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		tube = new Tube(ray, 1);
		val = tube.getNormal(new Point3D(1, 0, 0));
		assertEquals(new Vector(1, 0, 0), val);
	}

}
