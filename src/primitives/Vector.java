package primitives;

/**
 * Vector class is used to define a vector in a 3D and do some arithmetic
 * operations on this vector
 * 
 * @author David&Yishai
 *
 */
public class Vector {
	public Point3D head;

	/**
	 * ctor that get one parameter that represent a point in 3D
	 * 
	 * @param a represent a point in 3D
	 */
	public Vector(Point3D a) {
		head = new Point3D(a.x, a.y, a.z);
		if (head.equals(Point3D.ZERO)) {
			throw new IllegalArgumentException("the vector can't be zero");
		}
	}

	/**
	 * ctor that gets three coordinates that represent a point in 3D
	 * 
	 * @param coord_x the first Coordinate
	 * @param coord_y the second Coordinate
	 * @param coord_z the third Coordinate
	 */
	public Vector(Coordinate coord_x, Coordinate coord_y, Coordinate coord_z) {
		head = new Point3D(coord_x, coord_y, coord_z);
	}

	/**
	 * ctor that gets three numbers that represent a point in 3D
	 * 
	 * @param coord_x the first Coordinate
	 * @param coord_y the second Coordinate
	 * @param coord_z the third Coordinate
	 */
	public Vector(double coord_x, double coord_y, double coord_z) {
		Point3D p = new Point3D(coord_x, coord_y, coord_z);
		if (p.equals(Point3D.ZERO)) {
			throw new IllegalArgumentException("the vector can't be zero");
		}
		this.head = p;
	}

	/**
	 * add vector to point 3D and return the new vector value
	 * 
	 * @param other the vector to add
	 * @return the new vector value
	 */
	public Vector add(Vector other) {
		return new Vector(head.x.coord + other.head.x.coord, head.y.coord + other.head.y.coord,
				head.z.coord + other.head.z.coord);
	}

	/**
	 * subtract point from a given vector and return the new vector value
	 * 
	 * @param other the vector to subtract
	 * @return the new vector value
	 */
	public Vector subtract(Vector other) {
		return new Vector(head.x.coord - other.head.x.coord, head.y.coord - other.head.y.coord,
				head.z.coord - other.head.z.coord);
	}

	/**
	 * Scalar doubling, multiple scalar with class point and return the new vector
	 * value
	 * 
	 * @param other Scalar number for multiplication
	 * @return the new vector value
	 */
	public Vector scale(double num) {
		return new Vector(num * head.x.coord, num * head.y.coord, num * head.z.coord);
	}

	/**
	 * Vector multiplication between vector and dot
	 * 
	 * @param other vector for multiplication
	 * @return the new vector value
	 */
	public Vector crossProduct(Vector other) {
		return new Vector(head.y.coord * other.head.z.coord - head.z.coord * other.head.y.coord,
				head.z.coord * other.head.x.coord - head.x.coord * other.head.z.coord,
				head.x.coord * other.head.y.coord - head.y.coord * other.head.x.coord);
	}

	/**
	 * Scalar doubling between point and vector, mult vector with class point and
	 * return scalar number
	 * 
	 * @param other vector for multiplication
	 * @return scalar value, sum of the Scalar multiplication
	 */
	public double dotProduct(Vector other) {
		return head.x.coord * other.head.x.coord + head.y.coord * other.head.y.coord
				+ head.z.coord * other.head.z.coord;
	}

	/**
	 * Calculates the length of the class point as vector
	 * 
	 * @return the length of the vector Squared
	 */
	public double lengthSquared() {
		return head.x.coord * head.x.coord + head.y.coord * head.y.coord + head.z.coord * head.z.coord;
	}

	/**
	 * Calculates the length of the class point as vector
	 * 
	 * @return the length of the vector
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	@Override
	public String toString() {
		return "head= " + head;
	}

	/**
	 * normalize the class point by dividing in its length and return its value
	 * 
	 * @return the normalized vector
	 */
	public Vector normalize() {
		Vector normal = new Vector(head.x.coord / length(), this.head.y.coord / this.length(),
				this.head.z.coord / this.length());
		this.head = normal.head;
		return this;
	}

	/**
	 * normalize the class point by dividing in its length but the point does not
	 * change
	 * 
	 * @return the normalized vector
	 */
	public Vector normalized() {
		Vector x = new Vector(this.head);
		return x.normalize();
	}

	/**
	 * This function return a normal Vector to "this" vector
	 * 
	 * @return normalized normal vector
	 */
	public Vector createNormal() {
		int min = 1;
		double x = head.x.coord, y = head.y.coord, z = head.z.coord;
		double minCoor = x > 0 ? x : -x;
		if (Math.abs(y) < minCoor) { // |y|<|x|
			minCoor = y > 0 ? y : -y;
			min = 2;
		}
		if (Math.abs(z) < minCoor) { // |z|<|x|,|y|
			min = 3;
		}
		switch (min) {
		case 1: { // |x|<|y|,|z|
			return new Vector(0, -z, y).normalize();
		}
		case 2: { // |y|<|x|,|z|
			return new Vector(-z, 0, x).normalize();
		}
		case 3: { // |z|<|x|,|y|
			return new Vector(y, -x, 0).normalize();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + min);
		}
	}

	/**
	 * Rotates the given point around the X-axis by the amount of angle sent
	 * 
	 * @param angle the angle in radian to turn
	 * @return the rotated vector
	 */
	public Vector rotateX(double angle) {
		return new Vector(head.rotateX(angle));
	}

	/**
	 * Rotates the given point around the Y-axis by the amount of angle sent
	 * 
	 * @param angle the angle in radian to turn
	 * @return the rotated vector
	 */
	public Vector rotateY(double angle) {
		return new Vector(head.rotateY(angle));
	}

	/**
	 * Rotates the given point around the Z-axis by the amount of angle sent
	 * 
	 * @param angle the angle in radian to turn
	 * @return the rotated vector
	 */
	public Vector rotateZ(double angle) {
		return new Vector(head.rotateZ(angle));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return head.equals(other.head);
	}

	public Point3D getHead() {
		return head;
	}
}