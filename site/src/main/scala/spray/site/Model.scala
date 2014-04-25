package spray.site

import geotrellis._
import geotrellis.source._

/**
 * Model for GeoTrellis demo app
 */
object Model {
  def weightedOverlay(
    layers: Iterable[String],
    weights: Iterable[Int],
    rasterExtent: Option[RasterExtent]): RasterSource =
    layers
      .zip(weights)
      .map {
        case (layer, weight) ⇒
          val rs = rasterExtent match {
            case Some(re) ⇒ RasterSource(layer, re)
            case None     ⇒ RasterSource(layer)
          }
          rs.convert(TypeShort)
            .localMap { x ⇒ if (x > 0) x else NODATA }
            .localMultiply(weight)
      }
      .localAdd.cached
}
