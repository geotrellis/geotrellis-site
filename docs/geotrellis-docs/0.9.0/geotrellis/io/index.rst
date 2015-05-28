.. _io-0.9.0:

Reading and Writing Data
========================

There are a number of formats that GeoTrellis provides utilities for reading from and writing to. The ARG format is how GeoTrellis reads and writes raster data. GeoTrellis can also read GeoTiffs with the :ref:`geotrellis-geotools-0.9.0` project, though reading ARGs is faster and preferred from a performance perspective. There is a :ref:`Catalog <Catalog-0.9.0>` that can index raster data for easy access by your application. Also, there is the ability to read and write GeoJSON.

.. toctree::

   arg
   rasterlayers
   catalog
   geojson
