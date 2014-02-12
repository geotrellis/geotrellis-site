.. _local:

Local Operations
================

Local operations process each raster cell individually, without considering other cells’ values. When combining multiple rasters, a local operation will map the value for each input’s ``(col, row)`` cell to a single value in the output raster’s ``(col, row)`` cell. These properties allow GeoTrellis to parallelize local operations over tiles easily.

Below is each local operation with a brief explination of it's use. Fore more information, see the Scala docs for the individual operation.

Mapping and Combining
---------------------

These local operations take functions as parameters and allows the user to specify any sort of local transformation of cells:

localMap
  Transforms each integer cell value to another value by a function.

localMapDouble
  Transforms each double cell value to another value by a function.

localMapIfSet
  Transforms each integer cell value to another value by a function, if the value is not NoData.

localMapIfSetDouble
  Transforms each double cell value to another value by a function, if the value is not NoData.

localDualMap
  Map the values of a each cell to a new value; if the type of the raster is a double type, map using the double function, otherwise map using the integer function.

localCombine
  Combines the integer cell values of two or more rasters.

localCombineDouble
  Combines the double cell values of two or more rasters.


Specific Operations
-------------------

These operations perform one specific transformation or combination of values.

localAdd
  Add the value of each cell to a constant value or to the matching cells of of another raster.

  *Operator: +*

localSubtract
  Subtract the value of each cell to a constant value or to the matching cells of of another raster.

  *Operator: -*

localMultiply
  Multiply the value of each cell to a constant value or to the matching cells of of another raster.

  *Operator: **

localDivide
  Divide the value of each cell to a constant value or to the matching cells of of another raster.

  *Operator: /*

localMin
  Take the minimum value of each cell between a sequence of rasters.

localMax
  Take the maximum value of each cell between a sequence of rasters.

localMean
  Take the mean value of each cell of a sequence of rasters.

localMask
  Masks this raster based on cell values of the second raster.

localInverseMask
  Masks this raster based on cell values of the second raster.

mask
  Mask this raster based off of a polygon.

localDefined
  Maps an integer typed Raster to 1 if the cell value is not NODATA, otherwise 0.

localUndefined
  Maps an integer typed Raster to 0 if the cell value is not NODATA, otherwise 1.

localPow
  Pow each value of the raster by a constant value.

localSqrt
  Take the square root each value in a raster.

localRound
  Round the values of a Raster.

localLog
  Computes the Log of a Raster.

localFloor
  Takes the Floor of each raster cell value.

localCeil
  Takes the Ceiling of each raster cell value.

localNegate
  Negate (multiply by -1) each value in a raster.

  *Operator: uniary - (-raster)*

localNot
  Get the negation of this raster source.

localAnd
  And the values of each cell in each raster.

localOr
  Or the values of each cell in each raster.

localXor
  Xor the values of each cell in each raster.

localEqual
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are equal, else 0.

localUnequal
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are not equal, else 0.

localGreater
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are greater than the next raster, else 0.

localLess
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are less than the next raster, else 0.

localGreaterOrEqual
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are greater than or equal to the next raster, else 0.

localLessOrEqual
  Returns a Raster with data of TypeBit, where cell values equal 1 if the corresponding cell valued of the rasters are less than or equal to the next raster, else 0.

localIf
  Transform values of a raster based on conditionals.

localMajority
  Assigns to each cell the value within the given rasters that is the most numerous.

localMinority
  Assigns to each cell the value within the given rasters that is the nth least numerous.

localVariety
  Variety gives the count of unique values at each location in a set of Rasters.

.. _Operations on a Sequence of RasterSources:

Operations on a Sequence of RasterSources
-----------------------------------------

exponentiate
  Raises each cell value to the power of the next raster, from left to right

localAdd
  Adds all the rasters in the sequence

localSubtract
  Takes the difference of the rasters in the sequence from left to right

difference
  Takes the difference of the rasters in the sequence from left to right (same as localSubtract)

localDivide
  Divides the rasters in the sequence from left to right

localMultiply
  Takes the product of the rasters in the sequence

product
  Takes the product of the rasters in the sequence (same as localMultiply)

max
  Takes the max of each cell value

min
  Takes the min of each cell value

and
  Takes the logical And of each cell value

or
  Takes the logical Or of each cell value

xor
  Takes the logical Xor of each cell value
