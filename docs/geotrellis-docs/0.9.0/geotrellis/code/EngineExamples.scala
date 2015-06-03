package doc

object EngineExamples {
 {
  //# implicit-server-ds
  import geotrellis._
  import geotrellis.source._

  val ds:DataSource[Int,Seq[Int]] = ???
  val result:Seq[Int] = ds.get
  //#
 }

 {
  //# explicit-server-ds
  import geotrellis._
  import geotrellis.source._

  val ds:DataSource[Int,Seq[Int]] = ???
  val result:Seq[Int] = GeoTrellis.server.get(ds)
  //#
 }

  import geotrellis._
  import geotrellis.source._

 {
  //# run-method-datasource
  val ds:DataSource[Int,Seq[Int]] = ???
  ds.run match {
    case process.Complete(result, history) =>
      // Process result here
    case process.Error(message, history) =>
      // Handle error here
  }
  //#
 }

 {
  //# run-method-op
  val op:Op[Seq[Int]] = ???
  GeoTrellis.run(op) match {
    case process.Complete(result, history) =>
      // Process result here
    case process.Error(message, history) =>
      // Handle error here
  }
  //#
 }
 {
  //# catalog-manual-config
  import geotrellis._
  import geotrellis.source._

  GeoTrellis.init(GeoTrellisConfig("/path/to/catalog.json"))

  // Now you can use the implicit server val and have
  // it uses /path/to/catalog.json as its catalog.
  RasterSource("layer").run match {
    case process.Complete(raster, history) => println("Layer was in the catalog")
    case process.Error(msg,trace) => println(s"Error: $msg")
  }
  //#
 }
}
