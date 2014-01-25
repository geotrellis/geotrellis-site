.. _geotrellis-admin:

geotrellis-admin
================

The *geotrellis-admin* project is a web application that you can use to view rasters inside of a :ref:`GeoTrellis catalog <Catalog>`. To run the viewer, switch to the admin subproject and run:

.. code-block:: console

  bash > ./sbt
  > project admin
  > run

The catalog used to serve rasters is located in the *admin/src/main/resources/application.conf* in the ``geotrellis.catalog`` setting. The port is also configurable, but the default is ``8880``.

Viewing Rasters
---------------

If you visit ``http://localhost:8880/``, you'll see the contents of the catalog on the left hand side.

**Note:** The application will only place rasters with extents in the Web Mercator (EPSG:3857) projection 
correctly on the map. Otherwise, the rasters *might* show up in the wrong location and of the wrong size, or they might not be viewable. If you need to see the raster through the *geotrellis-admin* project, you can use GDAL (*gdalwarp*) to reproject the raster and create a version of the raster in Web Mercator projection.

Checking Values
---------------

If you click the mouse on the raster, there will be a value view that comes up in the *Layer Info* tab of the left hand panel. This is useful for determining raster values at specific locations.

The *Layer Info* tab will also supply some basic information about the raster.
