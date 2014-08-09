.. _io:

Reading and Writing Data
========================

There are a number of formats that GeoTrellis provides utilities for reading from and writing to. The ARG format is how GeoTrellis reads and write raster data (although with the :ref:`geotrellis-geotools` project, you can also read GeoTiff, though reading ARGs is faster and preferred). There is a :ref:`Catalog <Catalog>` that can index raster data for easy access by your application. Also there is the ability to read and write GeoJSON.

.. toctree::

   arg
   rasterlayers
   catalog
   geojson
   geotiffreader
