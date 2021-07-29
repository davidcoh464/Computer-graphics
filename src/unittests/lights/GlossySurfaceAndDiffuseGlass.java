package unittests.lights;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class GlossySurfaceAndDiffuseGlass {

	/**
	 * Produce a picture of a spheres that reflected on glossy surface
	 */
	@Test
	public void glossySurfaceTest() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, -50, 20), new Vector(0, 3, -1), new Vector(0, 1, 3))
				.setViewPlaneSize(140, 100).setDistance(400);
		scene.geometries.add(
				new Sphere(new Point3D(-3.25, 8, 3), 3).setEmission(new Color(10, 100, 100))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100)).setkR(0.5)),
				new Sphere(new Point3D(3.25, 8, 3), 3).setEmission(new Color(10, 100, 100)).setMaterial(
						(new Material().setkD(0.5).setkS(0.5).setnShininess(100)).setkR(0.5).setkMatteG(0.4)),
				new Sphere(new Point3D(0, 0, 1.5), 1.5).setEmission(new Color(129, 129, 129))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100))),
				new Sphere(new Point3D(3, 1, 1.5), 1.5).setEmission(new Color(0, 129, 129))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100))),
				new Sphere(new Point3D(-3, 1, 1.5), 1.5).setEmission(new Color(129, 129, 0))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100))),
				new Sphere(new Point3D(6, 2, 1.5), 1.5).setEmission(new Color(54, 144, 182))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100))),
				new Sphere(new Point3D(-6, 2, 1.5), 1.5).setEmission(new Color(31, 142, 156))
						.setMaterial((new Material().setkD(0.5).setkS(0.5).setnShininess(100))),
				new Polygon(new Point3D(0, 9, 1), new Point3D(0, 6.5, 1), new Point3D(0, 6.5, 4), new Point3D(0, 9, 4))
						.setEmission(new Color(10, 100, 100))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
				new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(10, 100, 100))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
		scene.lights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(0, 0, -1)));
		scene.geometries.createBox();
		scene.geometries.callMakeTree();
		Render render = new Render() //
				.setImageWriter(new ImageWriter("glossySurface", 1800, 1800)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene, 100)).setMultithreading(3).setDebugPrint();
		;
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of 5 mirrors that transfrent with a diffused glass on some
	 * level for each mirror
	 */
	@Test
	public void diffusedGlassTest() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(30, -100, 10), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(70, 70).setDistance(100);

		Point3D a = new Point3D(0, 0, 0);
		Point3D b = new Point3D(11.8, 0, 0);
		Point3D c = new Point3D(11.8, 0, 20);
		Point3D d = new Point3D(0, 0, 20);
		Point3D startSp = new Point3D(8, 10, 6);

		Vector addPoint = new Vector(12, 0, 0);
		Vector addSp = new Vector(14, 0, 0);
		for (int i = 0; i < 5; ++i) {
			scene.geometries.add( //
					new Polygon(a, b, c, d) //
							.setEmission(new Color(15, 15, 15)) //
							.setMaterial(
									new Material().setkD(0).setkS(0.3).setnShininess(100).setkT(1).setkMatteD(i * 1)));
			a = a.add(addPoint);
			b = b.add(addPoint);
			c = c.add(addPoint);
			d = d.add(addPoint);
		}
		for (int i = 0; i < 4; ++i) {
			scene.geometries.add( //
					new Sphere(startSp, 6) //
							.setEmission(new Color(212, 235, 212)) //
							.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
			startSp = startSp.add(addSp);
		}
		scene.geometries.add( //
				// table
				new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)) //
						.setEmission(new Color(255, 200, 255)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
				new Plane(new Point3D(0, 100, 0), new Vector(0, 1, 0)) //
						.setEmission(new Color(180, 245, 245)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)));
		scene.lights.add( //
				new DirectionalLight(new Color(1000, 600, 0), new Vector(12, -50, -30)));
		scene.geometries.createBox();
		scene.geometries.callMakeTree();
		Render render = new Render() //
				.setImageWriter(new ImageWriter("diffusedGlass", 1000, 1000)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene, 100)).setMultithreading(3).setDebugPrint();
		render.renderImage();
		render.writeToImage();

	}
}
