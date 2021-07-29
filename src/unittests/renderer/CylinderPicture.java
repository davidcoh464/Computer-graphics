package unittests.renderer;

import java.util.List;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.*;

public class CylinderPicture {

	@Test
	/**
	 * test that create thousands geometries(spheres, polygons, cylinders)
	 */
	public void testMaster() {
		Scene scene = new Scene("test2_1");
		Camera camera = new Camera(new Point3D(150, 0, 250), new Vector(1, 0, -0.1), new Vector(0.1, 0, 1))
				.setViewPlaneSize(1000, 1000).setDistance(550);
		scene.setBackground(Color.BLACK);
		final int NUM = 100;
		Material mat = new Material().setkD(0.4).setkS(0.7).setnShininess(100);
		Color color = new Color(java.awt.Color.darkGray);
		// floor
		for (int i = 1; i <= NUM - 50; ++i) {
			scene.geometries
					.add(new Cylinder(new Ray(new Point3D(0, i * 20 - 500, -300), new Vector(1, 0, 0)), 10, 1800)
							.setEmission(color).setMaterial(mat));
		}
		// wall back buttom
		for (int i = 1; i <= NUM - 40; ++i) {
			scene.geometries
					.add(new Cylinder(new Ray(new Point3D(1700, i * 20 - 600, -300), new Vector(0, 0, 1)), 10, 400)
							.setEmission(color).setMaterial(mat));
		}
		// wall back top right
		for (int i = 1; i <= NUM - 79; ++i) {
			scene.geometries
					.add(new Cylinder(new Ray(new Point3D(1700, i * 20 - 440, 100), new Vector(0, 0, 1)), 10, i * 20)
							.setEmission(color).setMaterial(mat));
		}
		// wall back top left
		for (int i = 1; i <= NUM - 78; ++i) {
			scene.geometries.add(
					new Cylinder(new Ray(new Point3D(1700, i * 20 - 20, 100), new Vector(0, 0, 1)), 10, 420 - i * 18)
							.setEmission(color).setMaterial(mat));
		}
		// wall right
		for (int i = 1; i <= NUM; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point3D(i * 20, -500, -300), new Vector(0, 0, 1)), 10, 400)
					.setEmission(color).setMaterial(mat));
		}
		// wall left
		for (int i = 1; i <= NUM; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point3D(i * 20, 500, -300), new Vector(0, 0, 1)), 10, 400)
					.setEmission(color).setMaterial(mat));
		}
		// roof right
		for (int i = 1; i <= NUM; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point3D(i * 20, -500, 100), new Vector(0, 1.5, 1)), 10, 600)
					.setEmission(color).setMaterial(mat));
		}
		// roof left
		for (int i = 1; i <= NUM; ++i) {
			scene.geometries.add(new Cylinder(new Ray(new Point3D(i * 20, 500, 100), new Vector(0, -1.5, 1)), 10, 600)
					.setEmission(color).setMaterial(mat));
		}
		// mirrors
		mat.setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5);
		for (int i = 0; i <= 15; ++i)
			for (int j = 1; j <= 44; ++j) {
				scene.geometries.add(new Polygon(new Point3D(0, 520 - j * 19, -300 + i * 25),
						new Point3D(0, 520 - j * 19, -250 + i * 25), new Point3D(0, 350 - j * 19, -250 + i * 25),
						new Point3D(0, 350 - j * 19, -300 + i * 25)).setEmission(Color.BLACK).setMaterial(mat));
			}
		// chandelier
		mat = new Material().setkD(0.3).setkS(0.7).setkT(0).setnShininess(100);
		scene.geometries.add((new Cylinder(new Ray(new Point3D(400, 0, 700), new Vector(0, 0, -1)), 3, 450))
				.setEmission(Color.BLACK).setMaterial(mat));
		Ray mainRay = new Ray(new Point3D(400, 0, 250), new Vector(0, 0, -1));
		List<Ray> rays = mainRay.generateBeam(new Vector(0, 0, -1), 150, 100, 500);
		System.out.println(rays.size());
		Point3D randomPoint;
		mat = new Material().setkD(0.5).setkS(0.5).setnShininess(100);
		Material mat2 = new Material().setkD(1).setkS(0).setnShininess(100).setkT(0.7);
		color = new Color(java.awt.Color.GRAY);
		for (Ray ray : rays) {
			scene.geometries.add((new Cylinder(ray, 1, 96)).setEmission(Color.BLACK).setMaterial(mat));
			randomPoint = ray.getPoint(100);
			Sphere spheree2 = new Sphere(randomPoint, 8);
			scene.geometries.add(spheree2.setEmission(color).setMaterial(mat2));
		}
		// System.out.println(geometries..size());
		scene.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0));
		scene.lights.add(new PointLight(new Color(200, 110, 13), new Point3D(200, 0, 200)).setkC(1).setkL(0.0000001)
				.setkQ(0.000001));
		scene.lights.add(new PointLight(new Color(200, 110, 13), new Point3D(700, 0, 200)).setkC(1).setkL(0.0000001)
				.setkQ(0.000001));
		scene.lights.add(new PointLight(new Color(200, 110, 13), new Point3D(1200, 0, 200)).setkC(1).setkL(0.0000001)
				.setkQ(0.000001));
		scene.lights.add(new SpotLight(new Color(400, 684, 364), new Point3D(40, 0, 150), new Vector(0, 0, -1), 10)
				.setkC(1).setkL(0.0000001).setkQ(0.000001));
		scene.geometries.createBox();
		scene.geometries.callMakeTree();
		camera.moveBack(800);
		ImageWriter imageWriter = new ImageWriter("FinalPicture8", 1000, 1000);
		Render render = new Render() //
				.setCamera(camera) //
				.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setMultithreading(3).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

}
