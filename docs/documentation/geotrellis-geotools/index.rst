.. _geotrellis-geotools:

geotrellis-geotools
===================

The *geotrellis-geotools* project houses functionality for reading GeoTiff and ShapeFiles using the `GeoTools`__ library. GeoTools is an open source Java library that provides tools for geospatial data. It's a large dependency, so we have it in a separate project so that you only need to include it as a dependency for your project if you need it. The *geotrellis-geotools* library adds functionality to the ``geotrellis.data`` package for reading the ShapeFile and GeoTIFF formats.

__ http://www.geotools.org/

Including *geotrellis-geotools* into your project
-------------------------------------------------

The geotrellis-geotools project is an additional dependency, so you will have to add it to your *build.sbt*:

.. code-block:: scala

  scalaVersion := "2.10.3"
  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis-geotools" % "0.9.0"
  resolvers += "Geotools" at "http://download.osgeo.org/webdav/geotools/"

Notice that we need to add the GeoTools repository to the resolvers in order to find those dependencies.


Reading GeoTIFFs
----------------

There are two ways one could use the GeoTIFF reading capability in *geotrellis-geotools*: either read GeoTIFF's directly into a ``Raster`` type, or to give the GeoTrellis :ref:`Catalog` the ability to read GeoTIFFs. Due to the lack of heavy use of the latter use case, we recommend using the GeoTiffReader object directly if having the GeoTIFFs be in the catalog isn't a hard requirement. One thing about reading GeoTIFFs directly (as opposed to converting them to ARGs) is that reading them can be quite slow; if you are reading quite a bit and not using caching, and are warping the raster on read (only reading a specific extent, possibly resampled), things will end up being very slow. Converting GeoTIFFs to ARG rasters is definitely the preferred way to deal with GeoTIFF raster data.

To read a GeoTIFF file into a ``Raster``, you would use the following:

.. includecode:: code/GeoToolsExamples.scala
   :snippet: geoTiffReader-read

To add the ability to read GeoTiffs as a :ref:`RasterLayer <rasterlayers>` in the catalog, you'll need
to add this piece of code before the catalog searches for rasters:

.. includecode:: code/GeoToolsExamples.scala
   :snippet: add-to-catalog

Reading ShapeFiles
------------------

There's some wrapper code around reading GeoTool's ``SimpleFeature`` type out of ShapeFiles, and for reading GeoTrellis ``Point[T]`` features out of ShapeFiles. The code is very minimal, but a good start if you want to work with shapefiles. The best way to see how this is done is to `read the code`__.

__ https://github.com/geotrellis/geotrellis/blob/0.9/geotools/src/main/scala/geotrellis/data/ShapeFileReader.scala
