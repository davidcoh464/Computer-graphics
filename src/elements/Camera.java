
package elements;

import primitives.*;

/**
 * class that represent a camera. We look at the camera as if it have an only
 * one point of view
 * 
 * @author David&Yishai
 *
 */
public class Camera {

	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	static final double PIdiv180 = Math.PI / 180.0;

	/**
	 * 
	 * @param p0  the start position of the camera
	 * @param vTo the direction of the camera
	 * @param vUp the orthogonal vector to vTo
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {

		if (!Util.isZero(vUp.dotProduct(vTo))) { // if the vectors vUp and vTo not orthogonal
			throw new IllegalArgumentException("the vectors vUp and vTo must be orthogonal");
		}
		this.p0 = p0;
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();
		this.vRight = this.vTo.crossProduct(this.vUp).normalized();
	}

	/**
	 * 
	 * @param width  the width of the view plane
	 * @param height the height of the view plane
	 * @return return the class object
	 */
	public Camera setViewPlaneSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * 
	 * @param distance distance from p0
	 * @return the class object
	 */
	public Camera setDistance(double distance) {

		this.distance = distance;
		return this;
	}

	/**
	 * calculate the location of the ray in the view plane
	 * 
	 * @param nX number of columns in the view plane
	 * @param nY number of rows in the view plane
	 * @param j  The wanted column in the matrix
	 * @param i  The wanted row in the matrix
	 * @return the ray from the camera to view plane
	 */

	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		try {
			Point3D pCenter = p0.add(vTo.scale(distance));
			double rY = height / nY;
			double rX = width / nX;
			// amount of pixels to move in y axis from pCenter to i
			double yI = (i - (nY - 1) / 2d) * rY;
			// amount of pixels to move in x axis from pCenter to j
			double xJ = (j - (nX - 1) / 2d) * rX;
			Point3D pIJ = pCenter; // set the value of pIJ to the center
			if (!Util.isZero(xJ))
				pIJ = pIJ.add(vRight.scale(xJ));
			if (!Util.isZero(yI))
				pIJ = pIJ.add(vUp.scale(-yI));
			return new Ray(p0, pIJ.subtract(p0)); // return v = pIJ.subtract(p0) and the ctor of ray do the
													// normalization
		} catch (Exception e) { // if we get the zero vector
			return null;
		}
	}

	/**
	 * 
	 * Rotates the camera around x in the given angle
	 * 
	 * @param angle the angle in degrees to turn in around x
	 */
	public void rotateX(double angle) {
		angle = angle * PIdiv180;
		this.p0 = this.p0.rotateX(angle);
		this.vUp = this.vUp.rotateX(angle).normalize();
		this.vTo = this.vTo.rotateX(angle).normalize();
		if (!Util.isZero(vUp.dotProduct(vTo))) { // if the vectors vUp and vTo not orthogonal
			throw new IllegalArgumentException("the vectors vUp and vTo must be orthogonal");
		}
		this.vRight = this.vTo.crossProduct(this.vUp).normalized();
	}

	/**
	 * 
	 * Rotates the camera around y in the given angle
	 * 
	 * @param angle the angle in degrees to turn in around y
	 */
	public void rotateY(double angle) {
		angle = angle * PIdiv180;
		this.p0 = this.p0.rotateY(angle);
		this.vUp = this.vUp.rotateY(angle).normalize();
		this.vTo = this.vTo.rotateY(angle).normalize();
		if (!Util.isZero(vUp.dotProduct(vTo))) { // if the vectors vUp and vTo not orthogonal
			throw new IllegalArgumentException("the vectors vUp and vTo must be orthogonal");
		}
		this.vRight = this.vTo.crossProduct(this.vUp).normalize();
	}

	/**
	 * 
	 * Rotates the camera around z in the given angle
	 * 
	 * @param angle the angle in degrees to turn in around z
	 */
	public void rotateZ(double angle) {
		angle = angle * PIdiv180;
		this.p0 = this.p0.rotateZ(angle);
		this.vUp = this.vUp.rotateZ(angle).normalize();
		this.vTo = this.vTo.rotateZ(angle).normalize();
		if (!Util.isZero(vUp.dotProduct(vTo))) { // if the vectors vUp and vTo not orthogonal
			throw new IllegalArgumentException("the vectors vUp and vTo must be orthogonal");
		}
		this.vRight = this.vTo.crossProduct(this.vUp).normalize();
	}

	/**
	 * move the camera position right in the number of moves
	 * 
	 * @param moves steps to move right
	 */
	public void moveRight(double moves) {
		this.p0 = p0.add(vRight.scale(moves));
	}

	/**
	 * move the camera position left in the number of moves
	 * 
	 * @param moves steps to move left
	 */
	public void moveLeft(double moves) {
		this.p0 = p0.add(vRight.scale(-moves));
	}

	/**
	 * move the camera position up in the number of moves
	 * 
	 * @param moves steps to move up
	 */
	public void moveUp(double moves) {
		this.p0 = this.p0.add(vUp.scale(moves));
	}

	/**
	 * move the camera position down in the number of moves
	 * 
	 * @param moves steps to move down
	 */
	public void moveDown(double moves) {
		this.p0 = this.p0.add(vUp.scale(-moves));
	}

	/**
	 * move the camera position forth in the number of moves
	 * 
	 * @param moves steps to move forth
	 */
	public void moveForth(double moves) {
		this.p0 = this.p0.add(vTo.scale(moves));
	}

	/**
	 * move the camera position back in the number of moves
	 * 
	 * @param moves steps to move back
	 */
	public void moveBack(double moves) {
		this.p0 = this.p0.add(vTo.scale(-moves));
	}

}
