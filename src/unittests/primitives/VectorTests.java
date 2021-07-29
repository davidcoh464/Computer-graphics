package unittests.primitives;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author David&Yishai
 */

public class VectorTests {
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */

	@Test
	public void testAdd() {
		Vector x = new Vector(1, 2, 3);
		Vector val = x.add(x);
		assertEquals(new Vector(2, 4, 6), val);
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */

	@Test
	public void testSubtract() {
		Vector x = new Vector(2, 4, 6);
		Vector sub = new Vector(1, 2, 3);
		Vector val = x.subtract(sub);
		assertEquals(sub, val);
		try {
			x.subtract(x);
			fail("the vector can't be zero");
		} catch (Exception e) {
			// the method should return error
		}
	}

	/**
	 * Test method for {@link primitives.Vector#scale(primitives.Vector)}.
	 */

	@Test
	public void testScale() {
		Vector x = new Vector(1, 2, 3);
		Vector val = x.scale(2);
		assertEquals(new Vector(2, 4, 6), val);
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */

	@Test
	public void testCrossProduct() {
		Vector x = new Vector(1, 0, 0);
		Vector val = x.crossProduct(new Vector(0, 1, 0));
		// ============ Equivalence Partitions Tests ==============
		assertEquals(new Vector(0, 0, 1), val);
		// =============== Boundary Values Tests ==================
		// test zero vector from cross-product of the same vector
		try {
			x.crossProduct(x);
			fail("the vector can't be zero");
		} catch (Exception e) {
			// the method should return error
		}
		// Dan tests
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);

		// ============ Equivalence Partitions Tests ==============
		Vector v3 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v3);

		// Test that length of cross-product is proper (orthogonal vectors taken for
		// simplicity)
		assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

		// Test cross-product result orthogonality to its operands
		assertTrue("crossProduct() result is not orthogonal to 1st operand", Util.isZero(vr.dotProduct(v1)));
		assertTrue("crossProduct() result is not orthogonal to 2nd operand", Util.isZero(vr.dotProduct(v3)));

		// =============== Boundary Values Tests ==================
		// test zero vector from cross-product of co-lined vectors
		try {
			v1.crossProduct(v2);
			fail("crossProduct() for parallel vectors does not throw an exception");
		} catch (Exception e) {
		}

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */

	@Test
	public void testDotProduct() {
		Vector x = new Vector(1, 2, 3);
		double val = x.dotProduct(x);
		assertEquals(14, val, 0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared(primitives.Vector)}.
	 */

	@Test
	public void testLengthSquared() {
		Vector x = new Vector(1, 2, 3);
		double val = x.lengthSquared();
		assertEquals(14, val, 0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#length(primitives.Vector)}.
	 */

	@Test
	public void testLength() {
		Vector x = new Vector(1, 2, 3);
		double val = x.length();
		assertEquals(Math.sqrt(14), val, 0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#normalize(primitives.Vector)}.
	 */

	@Test
	public void testNormalize() {
		Vector x = new Vector(1, 1, 1);
		x.normalize();
		double root_3 = 1 / Math.sqrt(3);
		assertEquals(new Vector(root_3, root_3, root_3), x);
	}

	/**
	 * Test method for {@link primitives.Vector#normalized(primitives.Vector)}.
	 */

	@Test
	public void testNormalized() {
		Vector x = new Vector(1, 1, 1);
		Vector y = x.normalized();
		double root_3 = 1 / Math.sqrt(3);
		// ============ Equivalence Partitions Tests ==============
		assertEquals(new Vector(root_3, root_3, root_3), y);
		// =============== Boundary Values Tests ==================
		// test that check if the vector does not change
		assertEquals(new Vector(1, 1, 1), x);
	}
}
