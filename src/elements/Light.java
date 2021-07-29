package elements;

import primitives.Color;

/**
 * Abstract class for represent all source of light
 * 
 * @author David&Yishai
 *
 */
abstract class Light {

	private Color intensity;

	/**
	 * ctor for Light class
	 * 
	 * @param _intensity the light intensity color
	 */
	protected Light(Color _intensity) {
		this.intensity = _intensity;
	}

	/**
	 * getter for the intensity field
	 * 
	 * @return the intensity color
	 */
	public Color getIntensity() {
		return intensity;
	}

}
