.. _rasterlayers:

Raster Layer Types
==================

RasterLayers represent raster data in the :ref:`Catalog`. They consist of a metadata JSON file, and depending on the raster layer type, one or more data files.

Common Metadata
---------------

layer
  the ``layer`` property of the metadata JSON gives the name of layer, which can be used to reference the layer in Scala code.

.. _layer-cache:

cache
  the ``cache`` property will tell GeoTrellis whether or not to cache the raw byte information for the raster layer. This property does not apply to GeoTiff raster layers.

See the section on :ref:`ARG metadata` for more information.

Single Tile ARGs
----------------

A single tile ARG consists only of one JSON metadata file and a ``.arg`` file for the binary data of the raster. See the section on :ref:`ARGs <arg>` for more information.

.. _tiledrasterlayer:

Tiled ARGs
----------

TODO

ARGs read from a URL
--------------------

TODO

GeoTiff
-------

TODO
