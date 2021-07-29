package primitives;

/**
 * class for the lights material
 * 
 * @author David&Yishai
 *
 */
public class Material {

	public double kS = 0.0, kD = 0.0, kT = 0.0, kR = 0.0, kMatteD = 0.0, kMatteG = 0.0;
	public int nShininess = 0;

	/**
	 * setter for kS feild
	 * 
	 * @param kS
	 * @return the class object
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * setter for kD feild
	 * 
	 * @param kD
	 * @return the class object
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * setter for nShininess feild
	 * 
	 * @param nShininess
	 * @return the class object
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

	/**
	 * setter for kT feild
	 * 
	 * @param kT
	 * @return the class object
	 */
	public Material setkT(double kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * setter for kR feild
	 * 
	 * @param kR
	 * @return the class object
	 */
	public Material setkR(double kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * setter for kS feild
	 * 
	 * @param kS
	 * @return the class object
	 */
	public Material setkMatteD(double kMatteD) {
		this.kMatteD = kMatteD;
		return this;
	}

	/**
	 * setter for kS feild
	 * 
	 * @param kS
	 * @return the class object
	 */
	public Material setkMatteG(double kMatteG) {
		this.kMatteG = kMatteG;
		return this;
	}

}
