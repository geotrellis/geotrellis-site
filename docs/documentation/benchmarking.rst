.. _benchmarking:

geotrellis-benchmarking
=======================

Writing performance critical code means testing that performance in a consistent way. The benchmarking project of GeoTrellis uses code built around `Google Caliper`__. There's some utility functionality baked into ``OperationBenchmark`` for quickly writing benchmarks. Here's an example of some benchmark code:

.. code-block:: scala

  /** Result: Array.fill is really slow and should not be used */
  object ArrayFill extends BenchmarkRunner(classOf[ArrayFill])
  class ArrayFill extends OperationBenchmark {

    @Param(Array("256"))
    var size:Int = 0

    def timeScalaArrayFillBytes(reps:Int) = run(reps)(scalaArrayFillBytes)
    def scalaArrayFillBytes = {
      val arr = Array.fill[Byte](size*size)(byteNODATA)
    }

    def timeJavaArraysFillBytes(reps:Int) = run(reps)(javaArraysFillBytes)
    def javaArraysFillBytes = {
      val arr = Array.ofDim[Byte](size*size)
      java.util.Arrays.fill(arr,byteNODATA)
    }

    def timeFillerBytes(reps:Int) = run(reps)(fillerBytes)
    def fillerBytes = {
      Array.ofDim[Byte](size*size).fill(byteNODATA)
    }

    def timeScalaArrayFillFloats(reps:Int) = run(reps)(scalaArrayFillFloats)
    def scalaArrayFillFloats = {
      val arr = Array.fill[Float](size*size)(Float.NaN)
    }

    def timeJavaArraysFillFloats(reps:Int) = run(reps)(javaArraysFillFloats)
    def javaArraysFillFloats = {
      val arr = Array.ofDim[Float](size*size)
      java.util.Arrays.fill(arr,Float.NaN)
    }

    def timeFillerFloats(reps:Int) = run(reps)(fillerFloats)
    def fillerFloats = {
      Array.ofDim[Float](size*size).fill(Float.NaN)
    }

    def timeScalaArrayFillDoubles(reps:Int) = run(reps)(scalaArrayFillDoubles)
    def scalaArrayFillDoubles = {
      val arr = Array.fill[Double](size*size)(Double.NaN)
    }

    def timeJavaArraysFillDoubles(reps:Int) = run(reps)(javaArraysFillDoubles)
    def javaArraysFillDoubles = {
      val arr = Array.ofDim[Double](size*size)
      java.util.Arrays.fill(arr,Double.NaN)
    }

    def timeFillerDoubles(reps:Int) = run(reps)(fillerDoubles)
    def fillerDoubles = {
      Array.ofDim[Double](size*size).fill(Double.NaN)
    }
  }


running the benchmark will yield the following output:

.. code-block:: console

  [info] Running geotrellis.benchmark.ArrayFill 
  [info]  0% Scenario{vm=java, trial=0, benchmark=ScalaArrayFillBytes, size=256} 179106.06 ns; σ=8395.59 ns @ 10 trials
  [info] 11% Scenario{vm=java, trial=0, benchmark=JavaArraysFillBytes, size=256} 16813.83 ns; σ=156.18 ns @ 5 trials
  [info] 22% Scenario{vm=java, trial=0, benchmark=FillerBytes, size=256} 16745.91 ns; σ=42.52 ns @ 3 trials
  [info] 33% Scenario{vm=java, trial=0, benchmark=ScalaArrayFillFloats, size=256} 283825.19 ns; σ=2468.73 ns @ 4 trials
  [info] 44% Scenario{vm=java, trial=0, benchmark=JavaArraysFillFloats, size=256} 59689.51 ns; σ=6246.77 ns @ 10 trials
  [info] 56% Scenario{vm=java, trial=0, benchmark=FillerFloats, size=256} 68530.37 ns; σ=2796.47 ns @ 10 trials
  [info] 67% Scenario{vm=java, trial=0, benchmark=ScalaArrayFillDoubles, size=256} 305318.64 ns; σ=17978.87 ns @ 10 trials
  [info] 78% Scenario{vm=java, trial=0, benchmark=JavaArraysFillDoubles, size=256} 147511.33 ns; σ=674.77 ns @ 3 trials
  [info] 89% Scenario{vm=java, trial=0, benchmark=FillerDoubles, size=256} 129371.78 ns; σ=3507.40 ns @ 10 trials
  [info] 
  [info]             benchmark    us linear runtime
  [info]   ScalaArrayFillBytes 179.1 =================
  [info]   JavaArraysFillBytes  16.8 =
  [info]           FillerBytes  16.7 =
  [info]  ScalaArrayFillFloats 283.8 ===========================
  [info]  JavaArraysFillFloats  59.7 =====
  [info]          FillerFloats  68.5 ======
  [info] ScalaArrayFillDoubles 305.3 ==============================
  [info] JavaArraysFillDoubles 147.5 ==============
  [info]         FillerDoubles 129.4 ============


To run the benchmark, you issue the 'run' command in sbt while in the benchmark subproject. Each benchmark will be associated with a number; select the benchmark you want to run, and then check the output.

__ http://code.google.com/p/caliper/
