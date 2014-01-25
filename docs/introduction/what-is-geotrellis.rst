.. _what-is-geotrellis:

What is *geotrellis*?
=====================

GeoTrellis is a high performance geoprocessing engine and programming toolkit. The goal of the project is to transform user interaction with geospatial data by bringing the power of geospatial analysis to real time, interactive web applications.

GeoTrellis was designed to solve three core problems, with a focus on raster processing:

- Creating scalable, high performance geoprocessing web services
- Creating distributed geoprocessing services that can act on large data sets
- Parallelizing geoprocessing operations to take full advantage of multi-core architecture

GeoTrellis is published through `sonatype`__ to maven central as a :ref:`number of jars <adding-dependencies>` that you can add as dependencies to your project.

__ http://www.sonatype.org/central

Features
--------

- GeoTrellis is designed to help a developer create simple, standard REST services that return the results of geoprocessing models.
- Like an RDBS that can optimize queries, GeoTrellis will automatically parallelize and optimize your geoprocessing models where possible.
- In the spirit of the object-functional style of Scala, it is easy to both create new operations and compose new operations with existing operations.

Philosophy
----------

The rapid increase in both imagery and other geospatial data has increased the complexity of creating responsive and scalable geospatial applications.  The GeoTrellis library aims to provide the highest possible performance, while taking advantage of Scala's ability to construct programmer-friendly APIs.  Raster data sets can easily have millions or billions of values, and performing operations on them is often a very computationally expensive task. We believe that through performance-minded code and techniques such as parallelization, raster data can become critical pieces in high performance applications. We also believe in creating beautiful code, and that programming is a joy that can be increased with well-developed libraries.
