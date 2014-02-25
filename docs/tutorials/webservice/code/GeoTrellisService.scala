package tutorial

//# Imports
import geotrellis._
import geotrellis.source.{ValueSource, RasterSource}
import geotrellis.process.{Error, Complete}
import geotrellis.render.ColorRamps
import geotrellis.statistics.Histogram

import spray.routing.{ExceptionHandler, HttpService}
import spray.http.MediaTypes
import spray.http.StatusCodes.InternalServerError
import spray.util.LoggingContext
//#

trait GeoTrellisService extends HttpService {

  def rootRoute = pingRoute ~ rasterRoute ~ analyzeRoute

  def pingRoute = path("ping") {
    get {
      complete("ping!?")
    }
  }

    //# RasterRoute  
    def rasterRoute =     
    pathPrefix("raster" / Segment) { slug =>
      //Construct an object with instructions to fetch the raster
      val raster: RasterSource = RasterSource(slug)

      path("draw") {
        get {
          respondWithMediaType(MediaTypes.`image/png`) {
            complete {
              //Cunstruct an object that knows how to build a PNG
              val png: ValueSource[Png] = raster.renderPng(ColorRamps.BlueToRed)

              //Perform the operations leading to this result
              png.run match {
                case Complete(img, hist) =>
                  println(hist)
                  img
                case Error(msg, hist) =>
                  println(hist)
                  throw new RuntimeException(msg)
              }
            }
          }
        }
      } 
      //#
      ~
      path("mask") {
        get{
          parameter('cutoff.as[Int]) { cutoff =>
            respondWithMediaType(MediaTypes.`image/png`) {
              complete{
                val mask = raster.localMap{ x => if (x > cutoff) 1 else NODATA }
                mask.renderPng(ColorRamps.BlueToRed).get
              }
            }
          }
        }
      } 
      ~
      path("stats") {
        get {
          respondWithMediaType(MediaTypes.`application/json`) {
            complete {
              val histogramSource: ValueSource[Histogram] = raster.histogram()
              //No processing has been done yet
              val histogram = histogramSource.get
              val stats = histogram.generateStatistics()
              s"{mean: ${stats.mean}, histogram: ${histogram.toJSON} }"
            }
          }
        }
      }
    }
    
  //# AnalyzeRoute
  def analyzeRoute =
    pathPrefix("analyze") {
      parameter('cutoff.as[Int]) { cutoff =>
        val incomePerCapRaster = RasterSource("SBN_inc_percap")
        val farmMarketRaster = RasterSource("SBN_farm_mkt")
        val farmMarketMaskRaster = farmMarketRaster.localMap{ x => if (x > cutoff) 1 else NODATA }

        val result = incomePerCapRaster *  farmMarketMaskRaster

        path("draw") {
          respondWithMediaType(MediaTypes.`image/png`) {
            complete {
              result.renderPng(ColorRamps.BlueToRed).get
            }
          }
        } ~
        path("stats") {
          get {
            respondWithMediaType(MediaTypes.`application/json`) {
              complete {
                val histogramSource: ValueSource[Histogram] = result.histogram()
                //No processing has been done yet
                val histogram = histogramSource.run match {
                  case Complete(histogram, hist) =>
                    histogram
                  case Error(msg, hist) =>
                    throw new RuntimeException(msg)
                }
                val stats = histogram.generateStatistics()
                s"{mean: ${stats.mean}, histogram: ${histogram.toJSON} }"
              }
            }
          }
        }
      }
    }
  //#Analyze Route
  //# ExceptionHandler
  implicit def TutorialExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: Exception =>
        requestUri { uri =>
          complete(InternalServerError, s"Exception: ${e.getMessage}" )
        }
    }
  //#
}