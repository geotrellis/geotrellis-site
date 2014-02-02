.. _operations:

Operations
==========

Manipulation and processing of raster data is a large part of the GeoTrellis library. The approach taken to the organization of raster operations is in line with `C. Dana Tomlin’s Map Algebra`__, as detailed in the book GIS and Cartographic Modeling. Map Algebra breaks focal operations into three main categories:

__ http://www.amazon.com/GIS-Cartographic-Modeling-Dana-Tomlin/dp/158948309X

- :ref:`local`: Local operations calculate resulting raster cell values based on the value at the same cell location in one or more input rasters.
- :ref:`focal`: Focal operations calculate resulting raster cell values based on the values in a defined in a neighborhood around the same cell location in one or more input rasters.
- :ref:`zonal`: Zonal operations calcuate resulting raster cell values based on the values associated with that cell’s zone in one or more input rasters.

There's also some operation categories that GeoTrellis recognizes:

- :ref:`global`: Global operaitons are operations that need all information from the whole entire raster.
- :ref:`statistics`: Statistics operations compute statistics from raster values.

*Note:* For each Operation that takes an input Raster and returns a Raster, the data type of the output Raster will be the same as the input Raster, unless otherwise noted.

How operations are implemented
------------------------------

Raster operations are accessed through methods on the :ref:`RasterSource` type, or through methods on a sequence of :ref:`RasterSources <Operations on a Sequence of RasterSources>`.

Double vs Int
-------------

asdf

.. toctree::

   local
   focal
   global
   zonal
   statistics
   hydrology
