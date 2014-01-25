.. _Datasource:
.. _Datasources:

DataSource
==========

The DataSource architecture allows developers to specify the loading and transforming of data as a series of method calls, without doing any actual work until needed. This method is similar to using `Futures`__ in scala to do asynchronous work; you're describing the work that *will happen* once the Future completes. It is also a lot like a collection, in that it contains a sequence of elements that can be mapped over. It also can construct those elements into a final value, using the ``.get`` or ``.run`` methods.

__ http://docs.scala-lang.org/overviews/core/futures.html

DataSource has two type parameters: ``DataSource[T,V]``. The first type parameter, ``T``, is the type of the elements that are the underlying sequence of the DataSource. The second type parameter, ``V``, is the type of the value returned if ``.get`` or ``.run`` would be called, or the type of the ``ValueSource`` that would be returned if the default implementation of ``.converge`` was called.

Every data source can be thought of in two ways: it is a collection of individual data elements, and it also represents the entire collection or something that can be built from those elements.

What does that mean? Imagine a data source -- like a raster -- that can be divided up into smaller chunks. A 10k by 10k raster (a grid of 10k columns & 10k rows) can be divided into, for example, 100 tiles that are each 1000x1000 rasters.

When possible, all transformations are defined as work on each chunk -- so each chunk can be executed in parallel. Each transformation that can happen on independent tiles will produce an independent tile -- on a different machine, if distributed -- and produces (such as a histogram in the example above) independent results.

In the example above, the first operations are actually transformations on individual tiles -- and when we ask for a histogram, we in fact have a chunked up histogram distributed across our cluster. The individual elements are histograms of each tile. The histogram data source represents a single histogram as well, as GeoTrellis knows how to combine the individual histograms if necessary. But it's best to avoid the combination step unless necessary. When you need to specifically act on the combined value, the converge() method will combine the data source on a single machine -- and you can continue to chain transformations on that converged data product.

CanBuildFrom architecture
-------------------------

The DataSource architecture borrows heavily from the Scala collections library implementation, which uses a builder pattern to allow for methods written at the top level of the heirarchy to return subtypes. This allows us to do things like, if you map over a ``ValueSource[T]`` with a function ``T => Raster``, you will be returned a ``RasterSource``. It also allows ``RasterSource`` functions that map to a raster (i.e. ``localAdd``) to return a ``RasterSource`` with the same ``RasterDefinition`` as the previous ``RasterSource``, while still using the ``.map`` function written in ``DataSourceLike``. For a great article on the pattern used for this, see Martin Ordersky and Lex Spoon's article on `the architecture of scala collections`__.

__ http://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html

A data source is similar to a scala collection such as Seq or List -- it can be thought of as an ordered sequence of elements of a particular type, and each element can be transformed individually. A data source is also similar to a Future, in that it represents a computation that will happen in the future -- and a data pipeline can be created by providing functions to transform the result of a previous transformation.

A data source, however, has one critical additional piece of functionality. If a data source can be thought of like a Seq[E], where E is the type of the element in its sequence, then what is the type of the Future -- what kind of value does it return? A data source extends the trait DataSource[E,V] where E is the kind of element in its sequence and V is the kind of value it returns. In a simple case, a datasource might be of type DataSource[E,Seq[E]] -- it is a sequence of elements of type E, and overall it returns a sequence of those results. But, for example, a RasterSource -- which represents a raster layer which can be transformed through a variety of map algebra or raster operations -- extends the type DataSource[Raster,Raster], because the elements are individual tiles that make up a single raster dataset. The overall result is the whole raster that can be built from combining those rasters. The overall result might never be built, but the datasource will pass along additional information necessary to produce its value or result from its sequence.

ValueSource
-----------

A value source is a DataSource with only 

RasterSource
------------

