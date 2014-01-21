.. _resources:

Resources
=========

Scaladocs
---------

If you want to dive directly into the code, you can find *Scaladocs*, the Scala API documentation, for the latest version of the project `here`__.  You can also track the current development of GeoTrellis `at our github repository`__.

__ http://geotrellis.github.com/scaladocs/latest/index.html#geotrellis.package
__ http://github.com/geotrellis/geotrellis

Mailing list and IRC
--------------------

You can find us on IRC at **#geotrellis on freenode**, or join the `geotrellis-user mailing list`__.  We're always interested in what you're working on, if we can help, and any feedback you might have.  If you'd like to contact us directly, send an email to Rob at remanuele@azavea.com.

__ https://groups.google.com/group/geotrellis-user

Adding GeoTrellis to your Project
---------------------------------

We publish the jars of current release version of GeoTrellis, so you can include GeoTrellis in an SBT project
by adding the following to your build.sbt file:

.. code-block:: scala

  scalaVersion := "2.10.3"

  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis" % "0.9.0-RC1"

We also publish `automated snapshot artifacts`__ based on recent development.  

__ https://oss.sonatype.org/content/repositories/snapshots/com/azavea/geotrellis/geotrellis_2.10/

We publish a few additional libraries that you might choose to include:

- geotrellis-geotools: GeoTools integration with GeoTrellis, including GeoTiff loading
- geotrellis-tasks: Tasks for interacting with raster data, designed to be used on the command line


Template Project
----------------

We have provided a sample project that provides a template for creating a
geoprocessing web service with GeoTrellis. It is a blank slate for your own
development that provides an environment set up with the
necessary dependencies in place, making it a little easier to get started.

The project loads GeoTrellis as a library, includes some basic configuration,
and has a very simple "hello world" web service in place for you to edit.
The template contains `step-by-step instructions`__.

__ https://github.com/geotrellis/geotrellis.g8

If you are a first time GeoTrellis user, you can use this template with the
tutorial included in this documentation to start exploring how to build your
own geoprocessing service.

Demonstration project
---------------------

The `/demo directory in the github repository`__ includes code from the `web service tutorial`__ and a
demonstration project that provides a REST service that performs a geoprocessing operation and returns a
PNG image to the user.

__ https://github.com/geotrellis/geotrellis/tree/master/demo
__ tutorials/webservice.html


Blog articles
-------------
  | `Developing a Kernel Density Service With GeoTrellis`__
  | `Viewing Raster Data with GeoTrellis 0.8.1`__

__ http://www.azavea.com/blogs/labs/2013/03/developing-a-kernel-density-service-with-geotrellis/>
__ http://www.azavea.com/blogs/labs/2013/04/viewing-raster-data-with-geotrellis-0-8-1/
