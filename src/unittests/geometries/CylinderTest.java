package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;
import geometries.*;
import primitives.*;

public class CylinderTest {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
	 * 
	 * Test to getNormal method to get normal to Finite Cylinder, and the Test check
	 * validation of function
	 */
	@Test
	public void testGetNormal() {

		Cylinder cylinder = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 1, 1);
		// ============ Equivalence Partitions Tests ==============
		Point3D point = new Point3D(1, 0, 0.5);
		assertEquals(new Vector(new Point3D(1, 0, 0)), cylinder.getNormal(point));
		// =============== Boundary Values Tests ==================
		point = new Point3D(1, 0, 0);
		assertEquals(new Vector(new Point3D(0, 0, 1)), cylinder.getNormal(point)); // if t = 0
	}

	
}
