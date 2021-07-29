package elements;

import primitives.*;

/**
 * class that calculate the camera light
 * 
 * @author David&Yishai
 *
 */
public class AmbientLight extends Light {
	/**
	 * ctor calculate the intensity by multiply iA with kA
	 * 
	 * @param iA Color
	 * @param kA double
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));
	}
/**
 * default ctor that initialize intensity to black color
 */
	public AmbientLight() {
		super(Color.BLACK);
	}

}
