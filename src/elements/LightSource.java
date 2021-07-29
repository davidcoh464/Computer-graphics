package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * An interface for all the types of light , which must implement its methods
 * 
 * @author David&Yishai
 *
 */
public interface LightSource {
	/**
	 * Get intensity of LightSource in point
	 *
	 * @param p point
	 * @return a new color of Intensity in point
	 **/
	public Color getIntensity(Point3D p);

	/**
	 * getter for the distance between the light source to point
	 * 
	 * @param p point
	 * @return distance between the light source to point
	 */
	public double getDistance(Point3D p);

	/**
	 * Get direction vector from light source to point
	 *
	 * @param p point
	 * @return a new direction vector
	 **/
	public Vector getL(Point3D p);

}
