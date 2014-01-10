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

RasterExtent
------------

Extent
------

Warping
-------

Warping is the term used to describe changing the resolution of the raster (the columns and the rows) as well as changing the extent of the raster (cropping). Warping rasters is as easy as passing the new raster extent to the ``warp`` function:

.. includecode:: code/RasterExamples.scala
   :snippet: warp-example

This code takes the raster's existing RasterExtent, crops it to the lower-left corner, and changes the resolution to 256x256.
