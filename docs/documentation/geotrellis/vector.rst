.. _vector:

Working with Vector Data
========================

Even though rasters are the main focus of GeoTrellis, there is functionality for working with vector/feature data. GeoTrellis doesn't provide underlying vector data types or operations; the open source java library `Java Topology Suite (JTS)`__ has done a great job of providing solid types and operations for dealing with vector data. GeoTrellis provides some wrappers around the base Geometry types to provide functionality dealing with features (geometries combined with data), and ways to use vector and raster data together.

There are plans for the 0.10 release of GeoTrellis to greatly improve the handling of vector data in Scala; namely, to provide a more complete and idiomatic scala wrapper around JTS functionality so there is a natural Scala solution for dealing with geometries and features.

__ http://www.vividsolutions.com/jts/JTSHome.htm 

Rasterization
-------------

Rasterization of vector data is the process of converting vectors into rasters. There's a few different ways for you to rasterize your vector data using GeoTrellis. 

TODO

Interpolation
-------------

Interpolation is the process of taking point data and using it to interpolate the values of all the cells in a raster.

TODO

Kernel Density
--------------

The Kernel Density operation is a method for smoothing values of point data across a raster using an estimated probability density function. It's a geostatistical method that a full explination of is beyond the scope of this documentation; please refer to the `Wikipedia article`__ for more information, or ask for more resources on the mailing list or IRC.

GeoTrellis implements the kernel density operation through the ``VectorToRaster.kernelDensity`` method.

__ http://en.wikipedia.org/wiki/Kernel_density_estimation

GeoJSON
-------

GeoTrellis provides utility objects that read and write GeoJson from GeoTrellis feature objects. See the :ref:`GeoJSON` section of *Reading and Writing Data* for more information.
