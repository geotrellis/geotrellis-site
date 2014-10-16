.. _geotrellis-jetty:

geotrellis-jetty
================

The *geotrellis-jetty* project provides utility code for working with `Jetty`__ as a web server, as well as a jetty-based internal server that can be easily configured and run.

__ http://www.eclipse.org/jetty/

Including *geotrellis-jetty* into your project
-------------------------------------------------

The geotrellis-jetty project is an additional dependency, so you will have to add it to your *build.sbt*:

.. code-block:: scala

  scalaVersion := "2.10.3"
  libraryDependencies += "com.azavea.geotrellis" %% "geotrellis-jetty" % "0.9.0"


Using GeoTrellis with Jetty
---------------------------

The general pattern of using GeoTrellis with Jetty is to have the ``main`` function call the ``run`` method of the object ``WebRunner``, as in the example below. 

.. includecode:: code/JettyExamples.scala
   :snippet: mainExample

The WebRunner creates an embedded Jetty server that is configured based on some settings in the ``application.conf``.


Configuration
-------------

These are the main configuration settings that effect the WebRunner:

geotrellis.host
  This is the host that will be used to set up the server (usually localhost or 0.0.0.0)

geotrellis.port
  This is the port that the server will be listening on.

geotrellis.rest-package
  This includes the package names that Jetty should search for services. The annotations on classes are how Jetty finds and routes services. Multiple packages can be searched; they should be separated by semicolons.

There are some other configuration settings to fine tune Jetty:

geotrellis.jetty.corePoolSize
  Specifies the minimum number of jetty threads.

geotrellis.jetty.maximumPoolSize
  Specifies the maximum number of jetty threads.

geotrellis.jetty.keepAliveMilliseconds
   The max time a thread can remain idle.

These next settings are for serving static content through the embedded Jetty server, as opposed to setting up something like nGinX to serve static context and proxy pass to the Jetty server only for web service calls:

geotrellis.server.serve-static
  Set to "yes" to have Jetty serve static content

geotrellis.server.static-path
  Path to the static content to be served.

Other settings include:

geotrellis.rest-prefix
  Set this to a prefix so that all annotated paths for services will be prefixed by a path. This is useful to separate out service calls from static path calls, which is necessary if the Jetty server is serving static content. **Note:** This defaults to "/gt".

geotrellis.include-gt-services
  Set this to "yes" to have some :ref:`default services <Services>` to be included in the service package, including a service to Catalog information and ColorRamp information.

Response utils
--------------

There's some wrapper classes around returning a Jetty Response, such as ``OK.json(str).allowCORS`` and ``ERROR(message)``. `See the code`__ for more information.

__ https://github.com/geotrellis/geotrellis/blob/0.9/jetty/src/main/scala/geotrellis/jetty/Response.scala

.. _Services:

Services
--------

The default services that are included using the `geotrellis.include-gt-services` not only provide useful functionality, but are good examples of writing Jetty services; `here is the code`__.

CatalogService
  This service will return information about the GeoTrellis catalog. It takes no parameters.

Color
  This returns color ramp information that can be used to pass keys into other services, for painting rasters with the GeoTrellis :ref:`default color ramps <Color Ramps>`.

__ https://github.com/geotrellis/geotrellis/tree/0.9/jetty/src/main/scala/geotrellis/jetty/services

