.. _python-geotrellis-0.9.0:

python-geotrellis
=================

*python-geotrellis* is a python package for GeoTrellis data tasks, such as converting rasters into ARG format, tiling ARG rasters, and managing the :ref:`catalog <Catalog-0.9.0>`.

Installation
------------

To install *python-geotrellis*, use pip:

.. code-block:: console

  sudo pip install python-geotrellis

GDAL Bindings
-------------

The *python-geotrellis* package requires the `GDAL`__ bindings be installed. You can find information on installing the GDAL python bindings `here`__. We recommend building gdal from source with the ``--with-python`` option.

__ http://www.gdal.org/
__ https://pypi.python.org/pypi/GDAL/


gtloader
--------

The *python-geotrellis* package installs the *gtloader* module and executable script. 
The gtloader script is a utility for working with raster data. It uses GDAL to read
rasters of `a wide array of formats`__ and convert those rasters into the :ref:`Azavea Raster Grid format (ARG)-0.9.0`.

__ http://www.gdal.org/formats_list.html

*gtloader info*
---------------

You can get information about an arg file with the the ``info`` subcommand.
For instance, if we wanted to get information about `the arg at src/test/resources/data/aspect.json`__, we could use:

__ https://github.com/geotrellis/geotrellis/blob/0.9/src/test/resources/data/aspect.json

.. code-block:: console
  
  > gtloader info src/test/resources/data/aspect.json
  cellwidth: 10.0
  cols: 979
  xskew: 0
  yskew: 0
  xmin: 557815.0
  ymin: 5107985.0
  cellheight: 10.0
  rows: 1400
  ymax: 5121985.0
  datatype: float32
  epsg: 26710
  xmax: 567605.0
  type: arg
  >

Note: info currently does not support tiled rasters.

*gtloader convert* command
--------------------------

The ``convert`` subcommand is what you can use to both convert rasters of different formats into ARG files, or to convert rasters of either ARG or GDAL-readable format into a :ref:`tiled ARG raster <tiledrasterlayer-0.9.0>`.

Here are some of the options for using the *convert* subcommand:

--cols-per-tile COLS_PER_TILE     The number of pixels in the width of each tiled raster being created.
--rows-per-tile ROWS_PER_TILE     The number of pixels in the height of each tiled raster being created.
-t TYPE                           The :ref:`raster type <Raster Type-0.9.0>` of the output raster. One of: bit,int8,int16,int32,float32,float64
-n NAME                           Name of the output raster. The name used to
                                  identify the raster when it is stored in the catalog.
                                  If not supplied, it will be the file name of the source raster,
                                  unless the source is an ARG.
-b BAND                           The band of a multi-band raster to read from.
--clobber                         Clobber the file if it already exists.
--no-verify                       Don't verify input data falls in a given range (just truncate)

The raster being created will only be a tiled raster if --cols-per-tile COLS_PER_TILE and --rows-per-tile ROWS_PER_TILE are present.

The second-to-last argument is the input file. If the output raster is not tiled, the last argument will be the output file name. If the output raster is tiled, then the last argument should specify the directory which the tiled raster will be created in.

For example, if we wanted to convert `the GeoTIFF at src/test/resources/slope.tif`__, we could use the *convert* command:

.. code-block:: console

  > gtloader convert -n slope src/test/resources/slope.tif slope.json
  NOTICE: Loading raster with width 979, height 1400
  >

This will produce two files, slope.arg and slope.json. The data type of the ARG will be ``float32``; this is because the GeoTIFF file has type float32 (you can check this with ``gdalinfo``).

If we want to create a Double raster version that was comprised of 256 x 256 tiles, we could run the command with the rows and columns per tile:

.. code-block:: console

  > gtloader convert -n slope -t float64 --cols-per-tile 256 --rows-per-tile 256 src/test/resources/slope.tif .
  NOTICE: Loading raster with width 979, height 1400
  Tile 1/24 (4%)
  Tile 2/24 (8%)
  Tile 3/24 (12%)
  Tile 4/24 (16%)
  Tile 5/24 (20%)
  Tile 6/24 (25%)
  Tile 7/24 (29%)
  Tile 8/24 (33%)
  Tile 9/24 (37%)
  Tile 10/24 (41%)
  Tile 11/24 (45%)
  Tile 12/24 (50%)
  Tile 13/24 (54%)
  Tile 14/24 (58%)
  Tile 15/24 (62%)
  Tile 16/24 (66%)
  Tile 17/24 (70%)
  Tile 18/24 (75%)
  Tile 19/24 (79%)
  Tile 20/24 (83%)
  Tile 21/24 (87%)
  Tile 22/24 (91%)
  Tile 23/24 (95%)
  Tile 24/24 (100%)
  Tile conversion completed.
  > 

__ https://github.com/geotrellis/geotrellis/blob/0.9/src/test/resources/slope.tif

*gtloader convert-all* command
------------------------------

The ``convert-all`` is used to convert a set of rasters in a directory into ARGs. This command has mostly the same options as the *convert* command, but adds one option:

-e EXT                   The extention of the files in the input directory to consider for conversion.

The input and output arguments are directories.

*gtloader catalog* commands
---------------------------

The ``catalog`` subcommand has a set of subcommands for working with a :ref:`GeoTrellis catalog <Catalog-0.9.0>`.

*gtloader catalog update*
  This command updates the catalog information.

*gtloader catalog list*
  This command lists the datastores of a catalog.

*gtloader catalog add-dir*
  This command will add a directory to the catalog

*gtloader catalog create*
  This command will create a new catalog.
