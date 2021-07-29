package unittests.renderer;

import static org.junit.Assert.fail;

import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.*;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;

/**
 * Test rendering a basic image
 * 
 * @author David&Yishai N.B(Dan)
 */
public class RenderTests {
	private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setDistance(100) //
			.setViewPlaneSize(500, 500);

	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50), //
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up
																													// left
						.setEmission(new Color(java.awt.Color.GREEN)),
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down
																													// left
						.setEmission(new Color(java.awt.Color.RED)),
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down
																													// right
						.setEmission(new Color(java.awt.Color.BLUE)));

		ImageWriter imageWriter = new ImageWriter("Color render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.WHITE));
		render.writeToImage();
	}

	/**
	 * Produce a scene with basic 3D model and render it into a jpeg image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {

		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1))//
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
																													// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
																														// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
																													// right

		ImageWriter imageWriter = new ImageWriter("base render test 2", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("testXmlFile.xml");
			doc.getDocumentElement().normalize();
			NodeList sceneList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
			for (int i = 0; i < sceneList.getLength(); ++i) {// loop of all the Scene Elements
				Node sceneNode = sceneList.item(i);
				if (sceneNode.getNodeType() == Node.ELEMENT_NODE) {// if the sceneNode is type of Node Element
					Element element = (Element) sceneNode;// convert sceneNode to Element
					// value of the attribute from string to double array
					double[] arr = stringToDouble(element.getAttribute("background-color"));
					// create a new Color with the list value
					Color background = new Color(arr[0], arr[1], arr[2]);
					arr = stringToDouble( // get the Elements in type of string and convert it to double array
							((Element) element.getElementsByTagName("ambient-light").item(0)).getAttribute("color"));
					// create a new Color with the list value
					Color light = new Color(arr[0], arr[1], arr[2]);
					// set the fields of scene. background and ambient Light
					scene.setBackground(background).setAmbientLight(new AmbientLight(light, 1));

					// get the NodeList of sphere
					NodeList temp = element.getElementsByTagName("sphere");
					int size = temp.getLength(); // get the size of elements in sphere
					for (int j = 0; j < size; ++j) { // loop on all the sphere elements
						NamedNodeMap node = temp.item(j).getAttributes();
						// get the radius of sphere
						double radius = Double.parseDouble(node.getNamedItem("radius").getNodeValue());
						// get the center point of sphere
						arr = stringToDouble(node.getNamedItem("center").getNodeValue());
						// add the sphere to the field geometries of scene
						scene.geometries.add(new Sphere(new Point3D(arr[0], arr[1], arr[2]), radius));
					}
					// get the NodeList of triangles
					temp = element.getElementsByTagName("triangle"); // get the size of elements in triangles
					size = temp.getLength();
					for (int j = 0; j < size; ++j) { // loop on all the triangle elements
						NamedNodeMap node = temp.item(j).getAttributes();
						arr = stringToDouble(node.getNamedItem("p0").getNodeValue());
						Point3D p0 = new Point3D(arr[0], arr[1], arr[2]);
						arr = stringToDouble(node.getNamedItem("p1").getNodeValue());
						Point3D p1 = new Point3D(arr[0], arr[1], arr[2]);
						arr = stringToDouble(node.getNamedItem("p2").getNodeValue());
						Point3D p2 = new Point3D(arr[0], arr[1], arr[2]);
						// add the triangle to the field geometries of scene
						scene.geometries.add(new Triangle(p0, p1, p2));
					}
				}
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}

		ImageWriter imageWriter = new ImageWriter("Xml render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	/**
	 * 
	 * @param str the string we want to convert
	 * @return Array of string converted to double
	 */
	private double[] stringToDouble(String str) {
		return Arrays.stream(str.split(" ")).mapToDouble(Double::parseDouble).toArray();
	}
}