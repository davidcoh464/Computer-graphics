package unittests.renderer;

//import static primitives.Util.alignZero;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class MultiTriangleInCircle {
	private static final Material mat = new Material().setkD(0.5).setkS(0.5).setnShininess(60);
	// private static final double pi = Math.PI;

	@Test
	public void MultiTrianglestest() {
		Camera camera = new Camera(new Point3D(0, 0, 5), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(4.5, 4.5).setDistance(5);
		Scene scene = new Scene("Test scene");// .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),
												// 0.2)); //
		scene.background = new Color(10, 100, 100);
		Vector addP2 = new Vector(-0.02, 0, 0);
		Vector addP3 = new Vector(0, -0.02, 0);
		Point3D t1, t2, t3;
		// double r = 2;
		for (int i = 1; i < 10000; ++i) { // creates multiple triangles
//			if (i % 1000 == 0)
//				r -= 0.2;
			t1 = t2 = t3 = randomPointIncircle();
			t2 = t2.add(addP2);
			t3 = t3.add(addP3);
			scene.geometries.add(new Triangle(t1, t2, t3).setEmission(randomColor()).setMaterial(mat));
		}
		scene.geometries.add(new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(10, 100, 100))
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));

		scene.geometries.createBox();
		scene.geometries.callMakeTree();
		camera.moveForth(1);
		camera.rotateX(45);
	    //camera.moveUp(0.5);

		scene.lights.add(new SpotLight(new Color(300, 300, 300), new Point3D(-200, -160, 300), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		// scene.lights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(0,
		// 0, -1)));

		ImageWriter imageWriter = new ImageWriter("MultiTrianglesFlatX1", 800, 800);
		Render render = new Render() //
				.setCamera(camera) //
				.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)).setMultithreading(3).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * gives a random point in circle when the radius is one and the center is point
	 * ZERO
	 * 
	 * @return random 3D point in circle
	 */
	private Point3D randomPointIncircle() {
//		double r = Math.sqrt(Math.random()); // multiply by radius(1 in our case)
//		double theta = Math.random() * 2 * pi;
//		double x = alignZero(r * Math.cos(theta)); // add to center coord x (0 in our case)
//		double y = alignZero(r * Math.sin(theta)); // add to center coord y (0 in our case)

		double x = 0, y = 0, z = 0;
		while (true) {
			x = Util.random(-1, 1);
			y = Util.random(-1, 1);
			if (x * x + y * y <= 1)
				break;
		}
		return new Point3D(x, y, z);
	}

//	/**
//	 * gives a random point in circle when the radius is one and the center is point
//	 * ZERO
//	 * 
//	 * @param r the current radius
//	 * @return random 3D point in circle
//	 */
//	private Point3D randomPointIncircle(double r) {
//		double theta = Math.random() * 2 * pi;
//		double x = r * Math.cos(theta); // add to center coord x (0 in our case)
//		double y = r * Math.sin(theta); // add to center coord y (0 in our case)
//		return new Point3D(x, y, 0);
//	}

	/**
	 * Gives a random color of rgb
	 * 
	 * @return random color
	 */
	private Color randomColor() {
		double r = Util.random(0, 255);
		double g = Util.random(0, 255);
		double b = Util.random(0, 255);
		return new Color(r, g, b);
	}

}
