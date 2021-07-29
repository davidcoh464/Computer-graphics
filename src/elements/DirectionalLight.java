package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for light in type of Directional
 * 
 * @author David&Yishai
 *
 */
public class DirectionalLight extends Light implements LightSource {

	private Vector direction;

	/**
	 * ctor for DirectionalLight
	 * 
	 * @param _intensity the light intensity
	 * @param _direction the light source direction vector
	 */
	public DirectionalLight(Color _intensity, Vector _direction) {
		super(_intensity);
		direction = _direction.normalize();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity();
	}

	@Override
	public Vector getL(Point3D p) {
		return direction;
	}

	@Override
	public double getDistance(Point3D p) {
		return Double.POSITIVE_INFINITY;
	}

}
