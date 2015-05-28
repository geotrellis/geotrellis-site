.. _Raster:

Rasters
=======

The ``Raster`` data type is the core type in GeoTrellis that represents a grid of values covering an extent of area. Rasters allow you to get values out of them by column and row:

.. includecode:: code/RasterExamples.scala
   :snippet: get-values

You can iterate over the values of a raster using the ``foreach`` methods:

.. includecode:: code/RasterExamples.scala
   :snippet: foreach-values

Rasters also have ``map`` which allows you to transform the value of each cell by a function:

.. includecode:: code/RasterExamples.scala
   :snippet: map-values

You can also combine the cells of one raster with another to create a new raster,
the rasters represent the same area and resolution:

.. includecode:: code/RasterExamples.scala
   :snippet: combine-values

You may have noticed that there are two versions of each of these functions: one to work with Int values, and one to work with Double values. This is because Rasters are backed by an underlying array of primitive values. If that array is integer based, then it is much faster to work with the integer valued functions. If the primitive values are floating point, then you must use the Double version of the functions. Another option is to use the 'dual' versions of the ``map`` and ``combine``:

.. includecode:: code/RasterExamples.scala
   :snippet: dual-versions

The dual versions will determine if the raster or rasters being operated on are all integer based, in which case it will use the integer function. Otherwise it will use the double valued functions.

.. _Raster Type:

Raster Types
------------

Rasters can have a number of data types for the backing array. You can check the ``rasterType`` property of Raster to find the the type. You can also check the ``isFloat`` property to see if the raster has a floating point-based type.

Here are the possible values returned by ``rasterType``:

``TypeBit``
  Bit rasters are backed with an Byte array, but contain 8 values per byte: each cell is either value
  0 (`NoData`_) or 1. This means that TypeBit rasters are much smaller in memory and on disk than
  the other raster types. Mapping bit rasters is also very fast, since the map can only be
  an identity, an inverse, or a constant.

``TypeByte``, ``TypeShort``, ``TypeInt``
  These types represent rasters containing a subset of the set :math:`\mathbb{Z}` of integers.

``TypeFloat``, ``TypeDouble``
  These types represent rasters containing a subset of the set :math:`\mathbb{Q}` of rationals.
  They are backed by floating point primitive values. You must use the ``Double`` version of 
  raster methods and operations, where they are available, in order to preserve the fractional
  parts of the values. Rasters of these types can be used with integer value methods and operations;
  however their values will be rounded in accordance to the Scala ``toInt`` function. For example:

  .. includecode:: code/RasterExamples.scala
     :snippet: int-double-get

  will hold true, except for the case of `NoData`_. This statement:

  .. includecode:: code/RasterExamples.scala
     :snippet: int-double-isNoData

  will always evaluate to ``true`` for any raster.


Performance considerations
--------------------------

The type of the raster can greatly impact the performance of operations on the raster. For
instance, consider the following benchmarking code, (similar to the RasterMap.scala code 
contained within the :ref:`benchmarking` project)::

  def timeRasterMapInt(reps:Int) = run(reps)(rasterMapInt)
  def rasterMapInt = intRaster map { i => i * 2 }

  def timeRasterMapByte(reps:Int) = run(reps)(rasterMapByte)
  def rasterMapByte = raster map { i => i * 2 }

  def timeRasterMapDouble(reps:Int) = run(reps)(rasterMapDouble)
  def rasterMapDouble = raster.mapDouble { d => d * 2.0 }

These benchmarks against TypeInt, TypeByte, and TypeDouble rasters yield the following 
results on an example workstation when run with various size 
rasters (where the size is the length of one dimension in a square raster)::

  [info] size       benchmark    us linear runtime
  [info]  256       RasterMap   508 =
  [info]  256   RasterMapByte   537 =
  [info]  256 RasterMapDouble   860 =
  [info]  512       RasterMap  2119 =
  [info]  512   RasterMapByte  2020 =
  [info]  512 RasterMapDouble  3465 =
  [info] 1024       RasterMap  8282 ====
  [info] 1024   RasterMapByte  8403 ====
  [info] 1024 RasterMapDouble 14158 =======
  [info] 2048       RasterMap 34001 ==================
  [info] 2048   RasterMapByte 34020 ==================
  [info] 2048 RasterMapDouble 56196 ==============================

As you can see, there is a performance cost to using floating point values to rasters, so be 
wary, and use :math:`\mathbb{Z}`\-based rasters whenever possible.

.. _NoData:

NoData
------

Conceptually, NoData values in a raster represent cells which do not contain any data. Having a NoData value allows there to be a representation of not just values, but the absence of values as well. Often times when rendering rasters, you will want regions with NoData values to be transparent, to visually represent that there is no data; consider a raster that describes values inside of a complex city border. While the Extent_ of the raster is rectangular, the boundry containing the data is a complex polygon. So the Extent could be a box that encloses the border polygon, and all cell values outside of that border polygon could be set to NoData.

NoData values are represented differently for each `Raster Type`_:

:TypeBit: ``0:Byte``
:TypeByte: ``Byte.MinValue`` (``byteNODATA`` in code)
:TypeShort: ``Short.MinValue`` (``shortNODATA`` in code)
:TypeInt: ``Int.MinValue`` (``NODATA`` in code)
:TypeFloat: ``Float.NaN``
:TypeDouble: ``Double.NaN``

If you are getting or setting values in a Raster or base RasterData, remember that .get, .getDouble, .set, and .setDouble return Ints and Doubles, so you'd really only be checking for or setting ``NODATA`` or ``Double.NaN``.

RasterData
----------

The RasterData type represents the data of a raster without the consideration of how that data is spatially located. It can be considered a grid of columns and rows, and you can use RasterData to iterate over the cell values, select certain values, and transform one grid into another.

The ``RasterData`` only contains information about the number of columns (member ``cols``) and the number of rows (member ``rows``) of the grid, as well as the RasterType (member ``getType``).

The ``RasterData`` trait represents immutable raster data. There is an implementating trait, ``MutableRasterData``, that represents RasterData that can be written to. This includes all of the base implementations of RasterData that are backed by an Array. These core data types use a single dimensional array to hold all cell values. There is an implementation of one of these RasterData types for each RasterType. For instance, a ``FloatArrayRasterData`` is backed by an ``Array[Float]``. The ``.get(col,row)`` methods are implemented by using the equation ``cols * row + col`` to translate from grid coordinates to array index. The exception to this rule is ``BitArrayRasterData``, which is backed by an ``Array[Byte]``, and uses bitwise operations to extract and set values of single bits within each byte. This makes the ``BitArrayRasterData`` much more compact than the next smallest ``MutableRasterData`` type, the ``ByteArrayRasterData``.

RasterExtent
------------

The ``RasterExtent`` is what defines the spatial association of the data contained in the cells of the raster to areas in space. While the ``RasterData`` only has information about the columns and rows of the grid, the RasterExtent describes the bounding box of the area that this grid covers (represented with an ``Extent``, and the width and height of the cells of the grid (which can be computed with the Extent, the number of columns and the number of rows). In fact, a Raster can be thought of as a wrapped tuple of (RasterData,RasterExtent), though this simplification breaks down when considering ``TileRaster`` and ``CroppedRaster``.

Extent
------

The ``Extent`` object is simply a case class that contains ``xmin``, ``ymin``, ``xmax``, and ``ymax`` values that represent the bounds of the area for which a raster covers. It does not contain any inherent information about which spatial reference system is being used for those values. For instance, if a Raster were to cover a part of Philadelphia, PA, USA, and the spatial reference system being used was latitude-longitude coordinates (ESPG:4326), then the extent might look like this:

.. code-block:: scala

  Extent(-75.211,39.928,-75.146,39.978)

If we were working in Web Mercator (EPSG:3857), it might look like this:

.. code-block:: scala

  Extent(-8372453.456,4855608.477,-8365230.157,4862755.339)

Relationship of Grid Coordinates and Map Coordinates
----------------------------------------------------

The Raster extent has two coordinate concepts involved: map coordinates and grid
coordinates. Map coordinates are what the ``Extent`` class uses, and specifies points
using an X coordinate and a Y coordinate. The X coordinate is oriented along west to east
such that the larger the X coordinate, the more eastern the point. The Y coordinate is
along south to north such that the larger the Y coordinate, the more northern the point.

This contrasts with the grid coordinate system. The grid coordinate system does not
actually reference points on the map, but instead a cell of the raster that represents
values for some square area of the map. The column axis is similar in that the number
gets larger as one goes from west to east; however, the row axis is inverted from map coordinates:
as the row number increases, the cell is heading south. The top row is labeled as 0, and the next
1, so that the highest indexed row is the southern most row of the raster.
A cell has a height and a width that is in terms of map units. You can think of it as each cell
is itself an extent, with width cellwidth and height cellheight. When a cell needs
to be represented or thought of as a point, the center of the cell will be used.
So when gridToMap is called, what is returned is the center point, in map coordinates.

Map points are considered to be 'inside' the cell based on these rules:

- If the point is inside the area of the cell, it is included in the cell.
- If the point lies on the north or west border of the cell, it is included in the cell.
- If the point lies on the south or east border of the cell, it is not included in the cell, it is included in the next southern or eastern cell, respectively.

Note that based on these rules, the eastern and southern borders of an Extent are not actually
considered to be part of the RasterExtent.

Warping
-------

Warping is the term used to describe changing the resolution of the raster (the columns and the rows) as well as changing the extent of the raster (cropping). Warping rasters is as easy as passing the new raster extent to the ``warp`` function:

.. includecode:: code/RasterExamples.scala
   :snippet: warp-example

This code takes the raster's existing RasterExtent, crops it to the lower-left corner, and changes the resolution to 256x256.
