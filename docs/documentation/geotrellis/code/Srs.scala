package docs

//# transform-example
import geotrellis.util._
import com.vividsolutions.jts.{geom => jts}

object TransformExample {
  def toWebMercator(p:jts.Polygon): jts.Polygon = 
    srs.LatLng.transform(p, srs.WebMercator)

  def toLatLng(g:jts.Geometry): jts.Geometry =
    srs.WebMercator.transform(g, srs.LatLng)

  def pointToWebMercator(x:Double, y:Double): (Double,Double) =
    srs.LatLng.transform(x, y, srs.WebMercator)
}
//#
