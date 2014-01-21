.. _engine

Akka Execution Engine
=====================

The Akka execution engine is what takes `Operations`_ and :ref:`DataSources`, executes them, and returns their computed values. The way it executes DataSources is by asking the source what the operation to compute it's value is, so the internals of the engine only deal with Operations.

Operations
----------

A geoprocessing model in GeoTrellis is composed of smaller geoprocessing operations with well-defined inputs and outputs. The next section describes how to create your own operations, but it is usually better to compose an operation out of existing operations if that is possible. The following is a list of some of the operations available. Operations in italics are planned for the future.

The GeoTrellis naming convention for operations namespaces every operation within a single package, and we commonly refer to the operation with the package name in the format package.operation. For example, data loading operations are in the io package, and so the LoadRaster operation is referred to as io.LoadRaster.

Server
------

The Server is the interface into the execution engine. It is what contains the Akka Actor system. It's also what contains the :ref:`Catalog` that contains the data accessible to GeoTrellis by LayerId. There should only be one server per instance of an application using GeoTrellis. There is a default system in geotrellis.GeoTrellis that is configured based on your ``application.conf``; this system is also imported as an implicit value when importing ``geotrellis._``.

Actors
------

ServerActor
  This actor yada yada

Worker
  This actor handles the execution of a base case Operation, or if the Operation has sub-Operations (operations in the case class's parameters), it will delegate to the StepAggregator.

StepAggregator
  This actor yada yada

Operation Flow Example
----------------------

As an example of how Operations are executed, imagine we had a ValueSource that was created by loading up a tiled raster into a RasterSource, and then ``.minMax`` was called on it. The ValueSource would give the Server it's operation, which would be a composition of the following Operations: an Operation to load each tile would be executed in parallel. 

MapOp1 (Converge)
        ├──────────Collect
        │             ├─────MapOp1 (RasterSource map)
        │             │                 ├──────────────MapOp1
        │             │                 │                 ├─────LoadRasterDefinition
        │             │                 │                 │               ├────────────Literal
        │             │                 │                 │               │               └───Result: LayerId (in 0 ms)
        │             │                 │                 │               └──────────Result: RasterDefinition (in 1 ms)
        │             │                 │                 └───Result: Vector(LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile,LoadTile) (in 3 ms)
        │             │                 └────────────Result: Vector(Op1,Op1,Op1,Op1,Op1,Op1,Op1,Op1,Op1,Op1,Op1,Op1) (in 4 ms)
        │             ├───┬─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 4 ms)
        │             │   │  └─Result: 2922 (in 17 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 5 ms)
        │             │   │  └─Result: 3411 (in 17 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 6 ms)
        │             │   │  └─Result: 2455 (in 16 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 8 ms)
        │             │   │  └─Result: 3250 (in 19 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 14 ms)
        │             │   │  └─Result: 4402 (in 25 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 15 ms)
        │             │   │  └─Result: 2946 (in 25 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 14 ms)
        │             │   │  └─Result: 2874 (in 25 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 15 ms)
        │             │   │  └─Result: 2817 (in 26 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 2 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 16 ms)
        │             │   │  └─Result: 2859 (in 27 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 0 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 3 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 16 ms)
        │             │   │  └─Result: 2231 (in 27 ms)
        │             │   ├─Op1
        │             │   │  ├───LoadTile
        │             │   │  │       ├────┬─Literal
        │             │   │  │       │    │    └───Result: LayerId (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 1 (in 0 ms)
        │             │   │  │       │    ├─Literal
        │             │   │  │       │    │    └───Result: 3 (in 0 ms)
        │             │   │  │       │    └─Literal
        │             │   │  │       │         └───Result: None$ (in 0 ms)
        │             │   │  │       └────Result: ArrayRaster (in 17 ms)
        │             │   │  └─Result: 2245 (in 28 ms)
        │             │   └─Op1
        │             │      ├───LoadTile
        │             │      │       ├────┬─Literal
        │             │      │       │    │    └───Result: LayerId (in 0 ms)
        │             │      │       │    ├─Literal
        │             │      │       │    │    └───Result: 2 (in 0 ms)
        │             │      │       │    ├─Literal
        │             │      │       │    │    └───Result: 3 (in 0 ms)
        │             │      │       │    └─Literal
        │             │      │       │         └───Result: None$ (in 0 ms)
        │             │      │       └────Result: ArrayRaster (in 18 ms)
        │             │      └─Result: 2640 (in 28 ms)
        │             └───Result: List(Integer,Integer,Integer,Integer,Integer,Integer,Integer,Integer,Integer,Integer,Integer,Integer) (in 37 ms)
        └────────Result: 2231 (in 38 ms)
