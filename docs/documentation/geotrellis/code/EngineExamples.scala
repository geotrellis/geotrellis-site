package doc

object EngineExamples {
  //# catalog-manual-config
  import geotrellis._
  import geotrellis.source._

  GeoTrellis.init(GeoTrellisConfig("/path/to/catalog.json"))

  // Now you can use the implicit server val and have
  // it use /path/to/catalog.json as it's catalog.
  RasterSource("layer").run match {
    case process.Complete(raster, history) => println("Layer was in the catalog")
    case process.Error(msg,trace) => println(s"Error: $msg")
  }
  //#
}
