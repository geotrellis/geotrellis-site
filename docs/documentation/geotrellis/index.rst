.. _geotrellis:

geotrellis
==========

The core *geotrellis* module holds the functionality for reading, writing, and operating on raster types.

.. toctree::
   
   raster/index
   engine
   datasource
   io/index
   operations/index
   rendering
   vector
   srs

..
   .. toctree::

      dependencies
      installation
      configuration
      http-server
      http-client/index
      common-behavior
      examples

..
   geotrellis
    - Rasters
     - Note about Rasters vs RasterSources
     - Data Types
     - NoData
     - RasterData
       - Constant Rasters
       - Bit Rasters
     - Function on Raster (map, combine)
       - get vs getDouble, map vs mapDouble, dualMap, etc
     - Warping
     - Cropped Raster and TiledRaster
    - Akka execution engine
     - Op's
     - Server [breifly mention the Catalog, link]
     - Actor system
     - Operation Flow example
     - Executing operations directly
    - DataSource
     - DataSource
       - CanBuildFrom
     - ValueSource
     - RasterSource
    - IO
     - Catalog, DataStores, LayerId
     - Layers
       - Single ARG
       - Tiled ARGs
       - ARG Urls
     - ARG
     - GeoJSON
    - Raster Operations
     - Local
     - Focal
     - Global
     - Zonal
     - Statistics
       - Histogram
       - Class Breaks
    - Rendering
     - Rendering to PNG
     - Color ramps
     - Color maps
    - Working with Vector Data
     - Rasterization
     - Interpolation
     - Kernel Density
     - GeoJSON [refer to IO section]
    - Spatial Reference Systems
     - Web Mercator <-> Lat Long
    - Network
     - Graph structure
     - Running shortest path
     - GeoTrellis Transit
