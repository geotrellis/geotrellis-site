.. _srs:

Spatial Reference Systems
=========================

GeoTrellis provides a simple translation utility for converting between Latitude and Longitude coordinates and Web Mercator (WGS 84 Datum ESPG:4326 and Spherical Mercator EPSG:900913). You can transform JTS geometries and x and y coordinates between the two systems:

.. includecode:: code/Srs.scala
   :snippet: transform-example
