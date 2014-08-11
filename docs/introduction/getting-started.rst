.. _Getting Started:

Getting Started
===============

.. _adding-dependencies:

We publish the jars of the current release version of GeoTrellis, so you can include GeoTrellis in an SBT project by adding the following to your build.sbt file:

.. code-block:: scala

  scalaVersion := "2.10.3"
  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis" % "0.9.0"

We also publish `automated snapshot artifacts`__ based on recent development.  

__ https://oss.sonatype.org/content/repositories/snapshots/com/azavea/geotrellis/geotrellis_2.10/

We publish a few additional libraries that you might choose to include:

- :ref:`geotrellis-services`: Utility functionality for creating web services that use GeoTrellis.
- :ref:`geotrellis-jetty`: Utilities for creating `jetty`__ based projects with GeoTrellis.
- :ref:`geotrellis-geotools`: GeoTools integration with GeoTrellis, including GeoTiff loading

__ http://www.eclipse.org/jetty/

Example project
---------------

The following demo project is a great place to start. It's a spray-based web application that
uses GeoTrellis to do a weighted overlay and zonal summary over land raster data for a project
that was completed for the University of Chattanooga at Tennessee:

`Chattanooga Demo`__

__ https://github.com/geotrellis/geotrellis-chatta-demo
