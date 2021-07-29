package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for light in type of point light
 * 
 * @author David&Yishai
 *
 */
public class PointLight extends Light implements LightSource {

	private Point3D position;
	private double kC = 1, kL = 0, kQ = 0;

	/**
	 * ctor for PointLight class
	 * 
	 * @param _intensity the light intensity color
	 * @param _position  the light position point
	 */
	public PointLight(Color _intensity, Point3D _position) {
		super(_intensity);
		this.position = _position;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dSquare = position.distanceSquared(p);
		double d = position.distance(p);
		return getIntensity().reduce(kC + kL * d + kQ * dSquare);
	}

	@Override
	public Vector getL(Point3D p) {
		if (p.equals(position))
			return null;
		return p.subtract(position).normalize();
	}

	/**
	 * setter for kC
	 * 
	 * @param kC light coefficient
	 * @return the class object
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * setter for kL
	 * 
	 * @param kL light coefficient
	 * @return the class object
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * setter for kQ
	 * 
	 * @param kQ light coefficient
	 * @return the class object
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public double getDistance(Point3D p) {
		return position.distance(p);
	}

}
