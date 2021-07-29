package unittests.renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
	private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(150, 150) //
			.setDistance(1000);
	private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200) //
			.setDistance(1000);

	private static Geometry triangle1 = new Triangle( //
			new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
	private static Geometry triangle2 = new Triangle( //
			new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
	private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

	/**
	 * Produce a picture of a multiple lights Sphere
	 */
	@Test
	public void multipleLightsSphere() {
		scene1.geometries.add(sphere);
		// Directional Light
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 300), new Vector(78, -27, -86)));
		// Point Light
		scene1.lights.add(new PointLight(new Color(400, 300, 300), new Point3D(53, 0, 0)).setkC(1).setkL(0.00001)
				.setkQ(0.000001));
		// Spot Light
		scene1.lights.add(new SpotLight(new Color(300, 200, 200), new Point3D(0, -49, 0), new Vector(8, 88, -81))
				.setkC(1).setkL(0.00001).setkQ(0.00000001));
		ImageWriter imageWriter = new ImageWriter("multipleLightsSphere", 500, 500);
		Render render = new Render().setImageWriter(imageWriter).setCamera(camera2) // view by camera2
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a multiple lights Triangle
	 */
	@Test
	public void multipleLightsTriangle() {
		scene1.geometries.add(triangle1);
		scene1.geometries.add(triangle2);
		// Directional Light
		scene1.lights.add(new DirectionalLight(new Color(300, 150, 100), new Vector(-15, 41, -33)));
		// Point Light
		scene1.lights.add(new PointLight(new Color(420, 250, 250), new Point3D(60, -35, -117)).setkC(1).setkL(0.00001)
				.setkQ(0.000001));
		// Spot Light
		scene1.lights.add(new SpotLight(new Color(420, 250, 250), new Point3D(64, 38, -117), new Vector(-14, -38, -33))
				.setkC(1).setkL(0.00001).setkQ(0.000001));
		ImageWriter imageWriter = new ImageWriter("multipleLightsTriangle", 500, 500);
		Render render = new Render().setImageWriter(imageWriter).setCamera(camera2) // view by camera2
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a multiple lights with two triangles and sphere
	 */
	@Test
	public void multipleLightsTriangleAndSphere() {
		scene1.geometries.add(triangle1);
		scene1.geometries.add(triangle2);
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(new Color(300, 75, 75), new Vector(-15, 41, -33)));
		scene1.lights.add(new DirectionalLight(new Color(300, 75, 75), new Vector(78, -27, -86)));
		scene1.lights.add(new PointLight(new Color(300, 150, 150), new Point3D(60, -35, -117)).setkC(1).setkL(0.00001)
				.setkQ(0.000001));
		scene1.lights.add(new SpotLight(new Color(300, 150, 150), new Point3D(64, 38, -117), new Vector(-14, -38, -33))
				.setkC(1).setkL(0.00001).setkQ(0.000001));
		ImageWriter imageWriter = new ImageWriter("multipleLightsTriangleAndSphere", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

		ImageWriter imageWriter = new ImageWriter("sphereDirectional", 500, 500);
		Render render = new Render().setImageWriter(imageWriter).setCamera(camera1)// view by camera1
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50)).setkC(1).setkL(0.00001)
				.setkQ(0.000001));
		ImageWriter imageWriter = new ImageWriter("spherePoint", 500, 500);
		Render render = new Render().setImageWriter(imageWriter) //
				.setCamera(camera1) // view by camera1
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2))
				.setkC(1).setkL(0.00001).setkQ(0.00000001));
		ImageWriter imageWriter = new ImageWriter("sphereSpot", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
				triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
		scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

		ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)), //
				triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
		scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)).setkC(1).setkL(0.0005)
				.setkQ(0.0005));
		ImageWriter imageWriter = new ImageWriter("trianglesPoint", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
				triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
		scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1))
				.setkC(1).setkL(0.0001).setkQ(0.000005));
		ImageWriter imageWriter = new ImageWriter("trianglesSpot", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene2));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a narrow spot light
	 */
	@Test
	public void sphereSpotSharp() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2), 5)
				.setkC(1).setkL(0.000005).setkQ(0.00000025));
		ImageWriter imageWriter = new ImageWriter("sphereSpotSharp", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesSpotSharp() {
		scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
				triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
		scene2.lights.add(new SpotLight(new Color(800, 400, 400), new Point3D(10, -10, -130), new Vector(-2, -2, -1), 5)
				.setkC(1).setkL(0.00005).setkQ(0.0000025));
		ImageWriter imageWriter = new ImageWriter("trianglesSpotSharp", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene2));
		render.renderImage();
		render.writeToImage();
	}

}
