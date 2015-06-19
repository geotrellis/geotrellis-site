.. _Getting Started:

Getting Started
===============

Introduction
------------

GeoTrellis is a geospatial data processing engine for high performance applications that work with large geospatial data set. It works by supporting computation and data storage on a distributed cluster of machines.  GeoTrellis is written in Scala and relies on Spark.  It can also leverage HDFS, Accumulo, and S3 storage.  This is a framework project and is not aimed at end users.  If you are interested in either modifying or contributing to the project, you will likely need to learn Scala, and, to make significant contributions, learn about Spark.  Here are some resources:

- Coursera:  Functional Programming Principles with Scala - https://www.coursera.org/course/progfun
- Coursera:  Principles of Reactive Programming - https://www.coursera.org/course/reactive
- Book:  Programming in Scala: A Comprehensive Step-by-Step Guide, 2nd Edition, by Martin Odersky, Lex Spoon, and Bill Venners 
- Book:  Advanced Analytics with Spark: Patterns for Learning from Data at Scale, by Sandy Ryza, Uri Laserson, Sean Owen
- Book:  Learning Spark: Lightning-Fast Big Data Analysis, by Holden Karau, Andy Konwinski, Patrick Wendell


Dependencies
------------
.. _adding-dependencies:

We publish the jars of the current release version of GeoTrellis, so you can include GeoTrellis in an SBT project by adding the following to your build.sbt file:

.. code-block:: scala

  scalaVersion := "2.10.3"
  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis" % "0.9.0"

We also publish `automated snapshot artifacts`__ based on recent development.  

__ https://oss.sonatype.org/content/repositories/snapshots/com/azavea/geotrellis/geotrellis_2.10/

We publish a few additional libraries that you might choose to include:

- :ref:`geotrellis-services-0.9.0`: Utility functionality for creating web services that use GeoTrellis.
- :ref:`geotrellis-jetty-0.9.0`: Utilities for creating `jetty`__ based projects with GeoTrellis.
- :ref:`geotrellis-geotools-0.9.0`: GeoTools integration with GeoTrellis, including GeoTiff loading

__ http://www.eclipse.org/jetty/

Example project
---------------

The following demo project is a great place to start. It's a spray-based web application that
uses GeoTrellis to do a weighted overlay and zonal summary over land raster data for a project
that was completed for the University of Chattanooga at Tennessee:

`Chattanooga Demo`__

__ https://github.com/geotrellis/geotrellis-chatta-demo
