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

The Cache
---------


