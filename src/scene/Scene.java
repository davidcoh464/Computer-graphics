package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Box;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author David&Yishai
 *
 */
public class Scene {

	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 0.0);
	public Geometries geometries = new Geometries();
	public List<LightSource> lights = new LinkedList<LightSource>();

	/**
	 * 
	 * @param _name the value to put in the field name
	 */
	public Scene(String _name) {
		this.name = _name;
	}

	/**
	 * 
	 * @param name the value to put in the field name
	 * @return the class object
	 */
	public Scene setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @param background the value to put in the field background
	 * @return the class object
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 *  set the lights colection
	 * @param lights list of lights
	 * @return the class object
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights.addAll(lights);
		return this;
	}

	/**
	 * 
	 * @param ambientLight the value to put in the field ambientLight
	 * @return  the class object
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * 
	 * @param geo intersectable shape
	 * @return a Scene performance with the new shape in the fields geometries
	 */
	public Scene addGeometry(Box geo) {
		geometries.add(geo);
		return this;
	}

}
