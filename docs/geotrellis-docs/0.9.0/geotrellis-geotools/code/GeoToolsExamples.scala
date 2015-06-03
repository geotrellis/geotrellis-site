package doc

import geotrellis._

object GeoToolsExamples {
//# geoTiffReader-read
  import geotrellis.data._

  val path = "/fake/path/to/geotiff.tif"
  val raster:Raster = GeoTiff.readRaster(path)
//#

//# add-to-catalog
  GeoTiffRasterLayerBuilder.addToCatalog()
//#


}

