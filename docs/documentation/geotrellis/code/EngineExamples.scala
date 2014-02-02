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
}
