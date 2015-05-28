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

object CustomizeColorRamps {
  //# customize-color-ramps-rgb
  import geotrellis.render._

  // Generate a color ramp with 
  // red (#FF0000), green (#00FF00), and blue (#0000FF)
  val ramp =
    ColorRamp.createWithRGBColors(0xFF0000, 0x00FF00, 0x0000FF)
  //#

  //# customize-color-ramps-other
  // Create a color ramp with red and yellow
  val redYellowRamp = 
    ColorRamp.createWithRGBColors(0xFF0000, 0xFFFF00)
  val newRamp = 
    redYellowRamp.interpolate(5)

  // Create a 15 class ramp from an existing ramp
  val fifteenColors = 
    ColorRamps.BlueToOrange.interpolate(15)
  //#

}

