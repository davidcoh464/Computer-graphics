package renderer;

import primitives.*;
import scene.*;
/**
 * 
 * @author david&yishai
 *
 */
public abstract class RayTracerBase {

	protected Scene scene; 
	/**
	 * 
	 * @param ray  type of Ray 
	 * @return the color of the point
	 */
    abstract Color traceRay(Ray ray);
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}
	
	
}
