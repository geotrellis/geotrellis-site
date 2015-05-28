.. _geotrellis-services-0.9.0:

geotrellis-services
===================

The *geotrellis-services* project provides functionality that is common to writing raster based services using GeoTrellis.


Including *geotrellis-services* into your project
-------------------------------------------------

The geotrellis-services project is an additional dependency, so you will have to add it to your *build.sbt*:

.. code-block:: scala

  scalaVersion := "2.10.3"
  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis-services" % "0.9.0"


Catalog services
----------------

This class provides basic functionality for sending information about the catalog to a client. An example of its use `is in the admin site`__.

__ https://github.com/geotrellis/geotrellis/blob/0.9/admin/src/main/scala/geotrellis/admin/AdminServiceActor.scala#L33

Color Ramps
-----------

This provides functionality for working with ColorRamps, including keyed json to send to the client for making additional requests, `as seen here in the admin project`__, as well as providing a mapping from the key to the GeoTrellis ColorRamp type associated with it.

__ https://github.com/geotrellis/geotrellis/blob/0.9/admin/src/main/scala/geotrellis/admin/AdminServiceActor.scala#L36

Layer services
--------------

The LayerService object provides functions to get information about individual layers, such as the bounding box, getting the class breaks for a layer, and rendering the layer based on query parameters. An example of its use is `in the admin project, for rendering a raster layer`__.

__ https://github.com/geotrellis/geotrellis/blob/0.9/admin/src/main/scala/geotrellis/admin/AdminServiceActor.scala#L79
