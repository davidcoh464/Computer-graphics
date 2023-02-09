# Computer-graphics
## The project deals with creating images from geometric shapes.

### The primitives package contains:

 three-dimensional dot defined by three coordinates,
 Vector that contains a three-dimensional dot and adds to it the functionality of a vector,
 Ray that contains a starting point and a direction vector,
 Color defined by the three colors: red, green, blue.

### Geometries package contains:

 triangle, polygon, plane, sphere, cylinder, tube -When each of these shapes implement 
 Intersectable so we can know if the camera's ray cuts these shapes and at each point of
 intersection the color of the camera in the same pixel will change to the color of the shape.

### The package of elements contains:

the camera and the different types of lights based on Fong's model:
 Directional Light that simulates the light coming from the sun, 
Point Light that simulates the light coming from the lamp,
Spot Light that simulates the light coming from a flashlight,
each light has a different realization. On the resulting image.


### In the renderer package we get to the final production of the image:

The camera class sends the ray of each pixel to Ray Tracer and checks its 
intersection with some geometric shape in the 3D space and defines its color accordingly,
 there is also a calculation to check if there is a shadow of another shape and also
 adds to the color of the shape The existing lights in his environment


## Sample output images

### Connected spheres with different radii and colors, and rotated polygons:

![alt text](https://github.com/davidcoh464/Computer-graphics/blob/master/images/lotsOfShapesBonus.png)

### Different levels of transparency:

![alt text](https://github.com/davidcoh464/Computer-graphics/blob/master/images/diffusedGlass.png)

### Circles of triangles

![alt text](https://github.com/davidcoh464/Computer-graphics/blob/master/images/MultiTrianglesFlat2.png)

### A mirror with several levels of brightness

![alt text](https://github.com/davidcoh464/Computer-graphics/blob/master/images/glossySurface.png)


### Teapot from several angles

![alt text](https://github.com/davidcoh464/Computer-graphics/blob/master/images/teapot%20gif.gif)

