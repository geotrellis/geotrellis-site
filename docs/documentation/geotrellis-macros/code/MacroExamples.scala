package doc

import geotrellis._

object MacroExamples {
//# noData-examples
  val raster:Raster = ???

  val i:Int = r.get(0,0)
  val d:Double = r.getDouble(0,0)

  if(isNoData(i)) { println("Is a NoData value.") }
  if(isData(i)) { println("Is a data value.") }
  if(isNoData(d)) { println("Is a NoData value.") }
  if(isData(d)) { println("Is a data value.") }
//#
}

