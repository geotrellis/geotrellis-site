.. _engine:

Akka Execution Engine
=====================

The Akka execution engine is what takes :ref:`Operations <Ops>` and :ref:`DataSources`, executes them, and returns their computed values. There is no type ``Engine``, the phrase Akka Execution Engine simply refers to the process of how DataSources and Operations are executed. Creating DataSources and Operations only describes the work that should be done; that work is not done until it is executed against the engine. The interface for executing DataSources or Operations against the engine is to run it through the :ref:`Server` instance.

.. _Server:

Server
------

The Server is the interface into the execution engine. It is what contains the Akka Actor system. It's also what contains the :ref:`Catalog` that contains the data accessible to GeoTrellis by LayerId. There should only be one server per instance of an application using GeoTrellis. There is a default system in ``geotrellis.GeoTrellis`` that is configured based on your ``application.conf``; this system is also imported as an implicit value when importing ``geotrellis._``. This provides a convenience for running DataSources: the ``.run`` and ``.get`` methods on DataSource take an implicit ``Server`` parameter that it executes itself through. So these two code blocks are equivalent: 

.. includecode:: code/EngineExamples.scala
   :snippet: implicit-server-ds

.. includecode:: code/EngineExamples.scala
   :snippet: explicit-server-ds


The setting in your ``application.conf`` for setting the catalog is:

.. code-block:: console

   geotrellis.catalog = "/path/to/catalog.json"

Modifying the Server configuration in code
------------------------------------------

If you need to, you can set up the catalog in the ``GeoTrellis.server`` manually through code. You do this by calling the ``init`` function on the ``GeoTrellis`` object before the server is used:

.. includecode:: code/EngineExamples.scala
   :snippet: catalog-manual-config

OperationResults
----------------

Along with calling the ``.get`` method to execute a DataSource or Operation, you can use the ``.run`` methods:

.. includecode:: code/EngineExamples.scala
   :snippet: run-method-datasource

.. includecode:: code/EngineExamples.scala
   :snippet: run-method-op

This allows you to handle errors with a `Try[T]-style`__ error handling. It will also return a ``History`` instance that gives you information on how the Operations were executed in the engine. See the :ref:`operation flow example` for an example of printing out a History instance to the console.

__ http://www.scala-lang.org/api/current/index.html#scala.util.Try

.. _Ops: 

Operations
----------

A geoprocessing model in GeoTrellis is composed of smaller geoprocessing operations with well-defined inputs and outputs. The next section describes how to create your own operations, but it is usually better to compose an operation out of existing operations if that is possible. The following is a list of some of the operations available. Operations in italics are planned for the future.

The GeoTrellis naming convention for operations namespaces every operation within a single package, and we commonly refer to the operation with the package name in the format package.operation. For example, data loading operations are in the io package, and so the LoadRaster operation is referred to as io.LoadRaster.

Actor Diagram
-------------

The following diagram shows the structure of the actor system which evaluates the Operations. The :ref:`Server` in the diagram is ``geotrellis.process.Server``, it will kick off the request and ``Await`` the ``PositionedResult``.

.. image:: images/akka-execution-engine.png

Akka Notation
^^^^^^^^^^^^^

.. image:: images/akka-notation.png

.. _operation flow example:

Operation Flow Example
----------------------

As an example of how Operations are executed, imagine we had a ValueSource that was created by loading up a tiled raster into a RasterSource, and then ``.minMax`` was called on it. The ValueSource would give the Server its operation, which would be a composition of the following Operations; an Operation to load each tile would be executed in parallel. 

.. code-block:: console

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
