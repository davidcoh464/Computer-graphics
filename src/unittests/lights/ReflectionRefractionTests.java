package unittests.lights;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -50), 50) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
				new Sphere(new Point3D(0, 0, -50), 25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));
		scene.geometries.createBox();
		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point3D(-950, -900, -1000), 400) //
						.setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));
		scene.geometries.createBox();
		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));
		scene.geometries.createBox();
		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by two spot light ,pyramid,
	 * prism , spheres inside of pyramid and prism
	 */
	@Test
	public void multipleShapes() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		// points for pyramid
		Point3D pointG = new Point3D(-95, -40, 100);
		Point3D pointI = new Point3D(-80, 40, 100);
		Point3D pointJ = new Point3D(-20, 0, 100);
		Point3D pointK = new Point3D(-65, 0, 200);
		// points for prism
		Point3D pointA = new Point3D(-25, -97, -100);
		Point3D pointB = new Point3D(93, -78, -45);
		Point3D pointC = new Point3D(75, 75, -130);
		Point3D pointM = new Point3D(-55.77, -69.27, -43.57);
		Point3D pointN = new Point3D(62.23, -50.27, 11.43);
		Point3D pointO = new Point3D(44.23, 102.73, -73.57);

		Material material = new Material().setkD(0.5).setkS(0.5).setnShininess(60);
		scene.geometries.add( //
				// Two triangles to the base of the image
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(material.setkR(0.5)).setEmission(new Color(java.awt.Color.DARK_GRAY)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(material) //
						.setEmission(new Color(java.awt.Color.DARK_GRAY)), //
				// Pyramid from 4 triangles
				new Triangle(pointG, pointI, pointJ).setMaterial(material.setkT(0.5).setkR(0)), //
				new Triangle(pointG, pointJ, pointK).setMaterial(material) //
						.setEmission(new Color(java.awt.Color.BLUE)), //
				new Triangle(pointG, pointI, pointK).setMaterial(material) //
						.setEmission(new Color(java.awt.Color.PINK)), //
				new Triangle(pointI, pointJ, pointK).setMaterial(material)//
						.setEmission(new Color(java.awt.Color.CYAN)), //
				// Prism from 3 rectangle and two triangle one on top and one down
				new geometries.Polygon(pointC, pointO, pointN, pointB).setMaterial(material.setkT(0)) //
						.setEmission(new Color(java.awt.Color.PINK)),
				new geometries.Polygon(pointB, pointN, pointM, pointA).setMaterial(material) //
						.setEmission(new Color(java.awt.Color.PINK)),
				new geometries.Polygon(pointA, pointM, pointO, pointC).setMaterial(material) //
						.setEmission(new Color(java.awt.Color.PINK)),
				new Triangle(pointM, pointN, pointO).setMaterial(material.setkT(0.85).setkMatteD(0.5)) //
						.setEmission(new Color(java.awt.Color.GREEN)),
				new Triangle(pointA, pointB, pointC).setMaterial(material) //
						.setEmission(new Color(java.awt.Color.GREEN)),
				// Spheres inside the prism /(mansera)
				new Sphere(new Point3D(35, 8, -55), 17) //
						.setEmission(new Color(java.awt.Color.CYAN)) //
						.setMaterial(material.setkR(0).setkT(0)),
				new Sphere(new Point3D(50, -22, -45), 16) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(material),
				new Sphere(new Point3D(20, -22, -50), 16) //
						.setEmission(new Color(java.awt.Color.BLACK)) //
						.setMaterial(material),
				new Sphere(new Point3D(50, -50, -25), 14) //
						.setEmission(new Color(java.awt.Color.LIGHT_GRAY)) //
						.setMaterial(material),
				new Sphere(new Point3D(20, -50, -30), 14) //
						.setEmission(new Color(java.awt.Color.GRAY)) //
						.setMaterial(material),
				// sphere inside the pyramid
				new Sphere(new Point3D(-70, 0, 50), 12) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(material));
		scene.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-200, -160, 300), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(75, 75, -150), new Vector(-136, -96, 69)) //
				.setkL(0.00001).setkQ(0.000005));
		scene.geometries.createBox();
		ImageWriter imageWriter = new ImageWriter("multipleShapes", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene)).setMultithreading(3).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void lotsOfShapesBonus() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		Point3D point = new Point3D(-5, 25, 0);
		Vector v = new Vector(0, 0, 25);
		// Two triangles to the base of the image
		scene.geometries.add(
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0)) //
				, new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0)) //
		);

		Color color = new Color(0, 0, 80);
		Color colorAdd = new Color(1, 2, 1);
		Material material = new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.85);
		// Creates 35 connected spheres
		for (int i = 1; i < 26; ++i) {
			scene.geometries.add(new Sphere(point, 30 - i).setEmission(color).setMaterial(material));
			point = point.add(v);
			if (i == 25) {
				for (int j = 1; j < 6; ++j) {
					scene.geometries
							.add(new Sphere(point, 30 - i).setEmission(new Color(10, 20, 100)).setMaterial(material));
					point = point.add(new Vector(0, 0, 10));
				}
			}
			color = color.add(colorAdd);
		}
		// 4 spheres for the end
		for (int i = 0; i < 4; ++i) {
			scene.geometries
					.add(new Sphere(point, 4 - i).setEmission(new Color(java.awt.Color.RED)).setMaterial(material));
			point = point.add(new Vector(0, 0, 10));
		}

		// Moving squares
		Point3D pointE = new Point3D(60, -10, 0);
		Point3D pointF = new Point3D(10, -10, 0);
		Point3D pointG = new Point3D(10, -60, 0);
		Point3D pointH = new Point3D(60, -60, 0);
		Point3D pointHead = new Point3D(35, -35, 150);
		Vector moveE = new Vector(0, -3, 10);
		Vector moveF = new Vector(3, 0, 10);
		Vector moveG = new Vector(0, 3, 10);
		Vector moveH = new Vector(-3, 0, 10);

		material.setkT(0).setkR(0.6);
		color = new Color(0, 0, 80);
		colorAdd = new Color(10, 10, 5);
		// Creates 10 movinigs squares
		for (int i = 0; i < 10; ++i) {
			scene.geometries.add(
					new geometries.Polygon(pointG, pointH, pointE, pointF).setMaterial(material).setEmission(color));
			if (i == 9)// dont move the points
				break;
			pointE = pointE.add(moveE);
			pointF = pointF.add(moveF);
			pointG = pointG.add(moveG);
			pointH = pointH.add(moveH);
			color = color.add(colorAdd);
		}
		// pyramid on the last square
		scene.geometries.add(
				new geometries.Triangle(pointG, pointH, pointHead).setMaterial(material)
						.setEmission(new Color(java.awt.Color.PINK)),
				new geometries.Triangle(pointG, pointF, pointHead).setMaterial(material)
						.setEmission(new Color(java.awt.Color.RED)),
				new geometries.Triangle(pointE, pointF, pointHead).setMaterial(material)
						.setEmission(new Color(java.awt.Color.PINK)),
				new geometries.Triangle(pointE, pointH, pointHead).setMaterial(material)
						.setEmission(new Color(java.awt.Color.RED)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));
		scene.geometries.createBox();
		// scene.geometries.callMakeTree();
		ImageWriter imageWriter = new ImageWriter("lotsOfShapesBonus", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene)).setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}
}
