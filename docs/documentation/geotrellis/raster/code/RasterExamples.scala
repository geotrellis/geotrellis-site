package docs

//# raster-map
import geotrellis._
import geotrellis.source._

object RasterExamples {
//# get-values
  val raster:Raster = ???

  // Gets the Int value from column 2, row 3
  val v1:Int = raster.get(2,3) 

  // Gets the Double value from column 4, row 5
  val v2:Double = raster.getDouble(4,5) 
//#

//# foreach-values
  // Counts the number of values above 100
  var countInt = 0
  raster.foreach { z:Int => if(z > 100) countInt += 1 } 

  // Counts the number of values above 12.5
  var countDouble = 0
  raster.foreach { z:Double => if(z > 12.5) countDouble += 1 } 
//#

//# map-values
  // Adds 1 to the Int values of this raster
  val mappedRaster1:Raster = 
    raster.map { z:Int => z + 1 }

  // Adds 0.1 to the Double values of this raster
  val mappedRaster2:Raster = 
    raster.mapDouble { z:Double => z + 0.1 }
//#

//# combine-values
  val raster2:Raster = ???

  // Adds the Int values of the two rasters together.
  val combinedRaster1:Raster = 
    raster.combine(raster2) { (z1,z2) => z1 + z2 }

  // Adds the Double values of the two rasters together.
  val combinedRaster2:Raster = 
    raster.combineDouble(raster2) { (z1,z2) => z1 + z2 }
//#

//# dual-versions
  val dualMapped = 
    raster.dualMap { z:Int => z + 1 } 
                   { z:Double => z + 1.5 }

  val dualCombined = 
    raster.dualCombine(raster2) { (z1: Int,z2: Int) => 
      z1 + z2 
    } { (z1: Double, z2: Double) => 
      z1 + z2
    }
//#

//# int-double-get
  val intValue:Int = raster.get(10,10)
  val doubleValue:Double = raster.getDouble(10,10)

  intValue == doubleValue.toInt
//#

//# int-double-isNoData
  var isTrue = true
  for(col <- 0 until raster.cols;
      rows <- 0 until raster.rows) {
    val i = raster.get(col,row)
    val d = raster.getDouble(col,row)
    isTrue &= (isNoData(i) == isNoData(d))
  }
  isTrue
//#

}
//#
