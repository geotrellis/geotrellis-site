.. _catalog:

The Catalog
===========

The Catalog is what gives the GeoTrellis :ref:`Server` easy access to your raster data. It's encoded on disk by a JSON file that specifies a number of data stores. Seperating out raster data by different data stores allows you to key a layer off of two levels: the DataStore name and the Layer name. This is useful for having the same raster data in different forms, for instance different projections, or tiled vs untiled.

You can tell the ``geotrellis.GeoTrellis`` object to use your catalog by a setting in your ``application.conf``:

.. code-block:: console
  
  geotrellis.catalog = "/path/to/catalog.json"

A catalog json looks like this:

.. code-block:: javascript

    {
        "catalog": "Catalog of Test Data",
        "stores": [
            {
                "store": "test:fs",
                "params": {
                    "type": "fs",
                    "path": "data",
                    "cacheAll": "no"
                }
            },
            {
                "store": "test:fs2",
                "params": {
                    "type": "fs",
                    "path": "data2",
                    "cacheAll": "no"
                }
            },
            {
                "store": "sbn:fs",
                "params": {
                    "type": "fs",
                    "path": "sbn",
                    "cacheAll": "no"
                }
            }
        ]
    }

See the :ref:`Server` section on more information about how to set the catalog on a GeoTrellis Server.

DataStore
---------

Each of the ``store`` entries in the above example JSON represents a DataStore. A DataStore is essentially points to a directory with raster data. The raster data can be a variety of :ref:`layer types <Layer Types>`.

The Cache
---------

The Catalog has a cache that can load up the raw byte data for a raster, which makes loading of raster data much faster (at the expense of having to hold large raster data in memory). You can specify a ``cacheAll`` setting on the DataStore in the catalog, which will cause GeoTrellis to cache every layer in that DataStore. Alternatively, you can you set :ref:`the cache property <layer-cache>` on the individual layer to have finer control over which layers get cached. 
