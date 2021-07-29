package geometries;

import primitives.*;

/**
 * abstract class that all that inherited from him must implement its abstract
 * methods
 * 
 * @author David&Yishai
 *
 */
public abstract class Geometry extends Box {
	public Color emission = Color.BLACK;
	public Material material = new Material();

	/**
	 * setter for emission feild
	 * 
	 * @param emission the value to set
	 * @return the class object
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * setter for material feild with deep copy
	 * 
	 * @param material object in type of Material to set
	 * @return the class object
	 */
	public Geometry setMaterial(Material material) {
		this.material.setkD(material.kD).setkR(material.kR).setkS(material.kS).setkT(material.kT)
				.setnShininess(material.nShininess).setkMatteD(material.kMatteD).setkMatteG(material.kMatteG);
		return this;
	}

	/**
	 * find the normal to the point that receive
	 * 
	 * @param point the point to check it's normal
	 * @return vector that normal to the received point
	 */
	public abstract Vector getNormal(Point3D point);

}
