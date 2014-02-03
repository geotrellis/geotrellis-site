.. _local:

Local Operations
================

Local operations process each raster cell individually, without considering other cells’ values. When combining multiple rasters, a local operation will map the value for each input’s ``(col, row)`` cell to a single value in the output raster’s ``(col, row)`` cell. These properties allow GeoTrellis to parallelize local operations over tiles easily.

Below is each local operation with a brief explination of it's use. Fore more information, see the Scala docs for the individual operation.

Mapping and Combining
---------------------

These local operations take functions as parameters and allows the user to specify any sort of local transformation of cells:

localMap
  TODO

localMapDouble
  TODO

localMapIfSet
  TODO

localMapIfSetDouble
  TODO

localDualMap
  TODO

localDualMapDouble
  TODO

localCombine
  TODO

localCombineDouble
  TODO


Specific Operations
-------------------

These operations perform one specific transformation or combination of values.

localAdd
  Stuff

  *Operator: +*

localSubtract
  Stuff

  *Operator: -*

localMultiply
  Stuff

  *Operator: **

localDivide
  Stuff

  *Operator: /*

localMin
  Stuff

localMax
  Stuff

localMean
  Stuff

localMask
  Stuff

localInverseMask
  Stuff

mask
  Stuff

localDefined
  Stuff

localUndefined
  Stuff

localPow
  Stuff

localSqrt
  Stuff

localRound
  Stuff

localLog
  Stuff

localFloot
  Stuff

localCeil
  Stuff

localNegate
  Stuff

  *Operator: uniary - (-raster)*

localNot
  Stuff

localAnd
  Stuff

localOr
  Stuff

localXor
  Stuff

localEqual
  Stuff

localUnequal
  Stuff

localGreater
  Stuff

localLess
  Stuff

localGreaterOrEqual
  Stuff

localLessOrEqual
  Stuff

localIf
  Stuff, overloads

localMajority
  TODO

localMinority
  TODO

localVariety
  Variety gives the count of unique values at each location in a set of Rasters.



.. _Operations on a Sequence of RasterSources:

Operations on a Sequence of RasterSources
-----------------------------------------

TODO
