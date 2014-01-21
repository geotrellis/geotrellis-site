.. _local:

Local Operations
================

Local operations process each raster cell individually, without considering other cells’ values. When combining multiple rasters, a local operation will map the value for each input’s ``(col, row)`` cell to a single value in the output raster’s ``(col, row)`` cell. These properties allow GeoTrellis to parallelize local operations over tiles easily.

