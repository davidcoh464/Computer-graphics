package renderer;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;
import static primitives.Util.*;

import java.util.List;

import elements.LightSource;

/**
 * the class help the render to process the image by the ray intersection with
 * the geometries
 * 
 * @author David&Yishai
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;
	private static final double DISTANCE = 10.0;
	private int numOfRays;

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	/**
	 * 
	 * @param light    the light source
	 * @param l        vector from the light source to point
	 * @param n        normal to the point
	 * @param geopoint point and geometry
	 * @return If there are no intersection return 1 else return the product of all
	 *         intersections' kt
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Point3D point = geopoint.point;
		Ray lightRay = new Ray(point, lightDirection, n);// ray from point to light source +- n*delta
		List<GeoPoint> intersections = scene.geometries. // find all intersection between the light and the point
				findGeoIntersections(lightRay, light.getDistance(point));
		if (intersections == null)
			return 1.0;
		double ktr = 1.0;
		for (GeoPoint gp : intersections) { // multiply ktr in all intersections' kt
			ktr *= gp.geometry.material.kT;
			if (ktr < MIN_CALC_COLOR_K)
				return 0.0;
		}
		return ktr;
	}

	/**
	 * find the closest geoPoint of intersection
	 * 
	 * @param ray ray from the pixel in camera
	 * @return the closest point to the ray from all the intersections
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null; // if there are no intersections
		return ray.findClosestGeoPoint(intersections);// find the closest point to the ray from all the
														// intersections
	}

	/**
	 * calculate the pixel color by adding the geometry color and the light effects
	 * 
	 * @param geopoint intersection point and geometry
	 * @param ray      ray from the pixel in camera
	 * @return the point color
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * calculate the pixel color
	 * 
	 * @param geopoint intersection point and geometry
	 * @param ray      ray from the pixel in camera
	 * @param level    the depth of recursion
	 * @param k        stoping condetion of recursion
	 * @return the pixel color
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = geopoint.geometry.emission;
		color = color.add(calcLocalEffects(geopoint, ray, k));
		return 1 == level ? color : color.add(calcGlobalEffects(geopoint, ray, level, k));
	}

	/**
	 * recursive functions for calculating the transparency and reflection on the
	 * point
	 * 
	 * @param geopoint intersection point and geometry
	 * @param inRay    ray from the pixel in camera
	 * @param level    the depth of recursion
	 * @param k        stoping condetion of recursion
	 * @return the point color
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, double k) {
		if (level == 1)
			return Color.BLACK;
		Color color = Color.BLACK;
		Geometry geometry = geopoint.geometry;
		Material material = geometry.material;
		Point3D point = geopoint.point;
		Vector n = geometry.getNormal(point);
		double kr = material.kR, kkr = k * kr, kMG = alignZero(material.kMatteG);
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, point, inRay);
			color = color.add(calcBeamColor(n, reflectedRay, level, kr, kkr, kMG));
		}
		double kt = material.kT, kkt = k * kt, kMD = alignZero(material.kMatteD);
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, point, inRay);
			color = color.add(calcBeamColor(n, refractedRay, level, kt, kkt, kMD));
		}
		return color;
	}

	/**
	 * move the ray point by +- n*delta = 0.1
	 * 
	 * @param n     the normal vector to point
	 * @param point
	 * @param inRay
	 * @return new ray moving +- n*delta
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		return new Ray(point, inRay.dir, n);
	}

	/**
	 * calculate the reflected ray
	 * 
	 * @param n     the normal vector to point
	 * @param point
	 * @param inRay
	 * @return new ray with direction of the reflected ray +- n*delta
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.dir;
		Vector r = v.subtract(n.scale(2 * (n.dotProduct(v)))); // v-(n(2*n*v))
		return new Ray(point, r, n);
	}

	/**
	 * calculate all the lights effects on the intersection point
	 * 
	 * @param intersection intersection point and geometry
	 * @param ray          ray from the pixel in camera
	 * @return the calculated color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Point3D point = intersection.point;
		Geometry geometry = intersection.geometry;
		Vector v = ray.dir;
		Vector n = geometry.getNormal(point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		Material material = geometry.material;
		int nShininess = material.nShininess;
		double kd = material.kD, ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) { // add to the color the sum of all lights effect
			Vector l = lightSource.getL(point);
			double nl = alignZero(n.dotProduct(l));
			if (Util.alignZero(nl * nv) > 0) { // sign(nl) == sing(nv)
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity)
							.add(calcSpecular(ks, l, n, v, nShininess, lightIntensity)));
				}
			}
		}
		return color;
	}

	/**
	 * calculate by the formula: Specular = kS*((-v*r)^nSh)*lightIntensity
	 * 
	 * @param ks
	 * @param l
	 * @param n
	 * @param v
	 * @param nShininess
	 * @param lightIntensity
	 * @return the color of Specular effects
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r;
		try {
			r = l.subtract(n.scale(2 * (l.dotProduct(n)))); // r = l - 2 *( l*n)*n
		} catch (Exception e) {
			return Color.BLACK;
		}
		double vDotR = v.scale(-1).dotProduct(r); // -v*r
		if (alignZero(vDotR) <= 0) { // if the angle is more than 90 degrees
			return Color.BLACK;
		}
		vDotR = Math.pow(vDotR, nShininess); // (-v*r)^nSh
		return lightIntensity.scale(ks * vDotR);
	}

	/**
	 * calculate by the formula: Diffusive = kD*|l*n|*lightIntensity
	 * 
	 * @param kd
	 * @param l
	 * @param n
	 * @param lightIntensity
	 * @return the color of Diffusive effects
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(kd * (Math.abs(l.dotProduct(n))));// light*(kd*|l*n|)
	}

	/**
	 * this function calculate the color of point with the help of beam
	 * 
	 * @param n      The normal vector of the point where beam start
	 * @param refRay reflected/refracted ray
	 * @param level  The level of recursiun
	 * @param k      kt/kr
	 * @param kk     kkt/kkr
	 * @param radius radius/kMatte ,when radius is bigger the mattiut is more
	 *               intense
	 * @return The color
	 */
	private Color calcBeamColor(Vector n, Ray refRay, int level, double k, double kk, double radius) {
		if (findClosestIntersection(refRay) == null)
			return Color.BLACK;
		Color addColor = Color.BLACK;
		List<Ray> rays = refRay.generateBeam(n, radius, DISTANCE, numOfRays);
		for (Ray ray : rays) {
			GeoPoint refPoint = findClosestIntersection(ray);
			if (refPoint != null) {
				addColor = addColor.add(calcColor(refPoint, ray, level - 1, kk).scale(k));
			}
		}
		int size = rays.size();
		return size > 1 ? addColor.reduce(size) : addColor;// divide the add color by rays size
	}

	/**
	 * ctor that call other ctor to initialize scene and numOfRays
	 * 
	 * @param _scene
	 */
	public RayTracerBasic(Scene _scene) {
		this(_scene, 1);
	}

	/**
	 * ctor that call super to initialize super scene feild
	 * 
	 * @param _scene
	 * @param _numOfRays
	 */
	public RayTracerBasic(Scene _scene, int _numOfRays) {
		super(_scene);
		this.numOfRays = _numOfRays;
	}

}
