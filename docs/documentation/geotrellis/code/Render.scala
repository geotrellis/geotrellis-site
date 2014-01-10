package docs

//# render-png
import geotrellis._
import geotrellis.source._
import geotrellis.render._

object Render {
  val raster = RasterSource("test-raster")

  val png1:Png = raster.renderPng(ColorRamps.BlueToRed).get
}
//#
