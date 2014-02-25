.. _`arg`:
.. _`Azavea Raster Grid format (ARG)`:

Azavea Raster Grid format (ARG)
===============================

The ARG (Azavea Raster Graphics) format is a simple way to encode raster data. A raster encoded in ARG comprises two files: foo.json and foo.arg. The JSON file contains all metadata about the raster, including name, data type, resolution, and the geographic extent. The ARG file contains the actual raster data (a two-dimension grid of numbers). It's important to know that both files are required.

.. _ARG metadata:

Metadata
--------

Here is a sample JSON metadata file. Every key seen here is required. Other keys are allowed but will be ignored.

.. code-block:: javascript

  {
    "layer": "philly rainfall",
    "type": "arg",
    "datatype": "int8",
    "xmin": -8507736.525864778,
    "ymin": 4847928.144104313,
    "xmax": -8457736.525864778,
    "ymax": 4897928.144104313,
    "cellwidth": 10.0,
    "cellheight": 10.0,
    "rows": 5000,
    "cols": 5000
  }

The ``layer`` key provides the name of the raster as a string. The type must be set to a valid type that GeoTrellis can read, such as ``arg`` or ``tiled``. See the documentation for :ref:`rasterlayers` for a complete list of layer types.

The geographic area covered by the raster is given by the points (``xmin``, ``ymin``) and (``xmax``, ``ymax``). ``xmin`` is the western edge of the raster and ``ymin`` is the southern edge. Together these values form the "lower left point" of the rectangle. ``xmax`` is the eastern edge of the raster and ``ymax`` is the northern edge. Together these values for the "upper right point" of the rectangle.

The coordinate system for these points is unspecified. While the web mercator projection is often used, it is not required.

The resolution of the raster is provided by ``cellwidth`` and ``cellheight``, which describe how much geographic area is covered by a single pixel's width and height, respectively. In general, these values should be the same when the output will be rendered with square pixels, although this is not enforced.

Geographic values like ``xmin`` and ``cellwidth`` are interpreted as 64-bit floating point values. Thus, some slight rounding error may accumulate when doing calculations with these values. For this reason, the dimensions of the raster are also provided: the width of the raster is given in columns (``cols``) and the height of the raster is given in ``rows``. Both values must be positive integers.

The ``datatype`` parameter communicates the width (in bytes) of each cell, as well as how to interpret the value. It must be one of the following:

.. cssclass:: table-striped

=============== =============== ======== ========= =============== ===================
datatype        bytes per cell  min      max       no data value   Scala no data value
=============== =============== ======== ========= =============== ===================
int8            1               -127     127       -128            Byte.MinValue
int16           2               -32,767  32,767    -32,767         Short.MinValue 
int32           4               |I32MIN| |I32MAX|  -2 :sup:`31`     Int.MinValue
float32         32              |FMIN|   |FMAX|    NaN             Float.NaN
float64         64              |DMIN|   |DMAX|    NaN             Float.NaN
=============== =============== ======== ========= =============== ===================

.. |I32MIN| replace:: \ -2 :sup:`31+1` \
.. |I32MAX| replace:: \ 2 :sup:`31-1` \
.. |FMIN| replace:: \ -10 :sup:`38.5` \
.. |FMAX| replace:: \ 10 :sup:`38.5` \
.. |DMIN| replace:: \ -10 :sup:`308.2` \
.. |DMAX| replace:: \ 10 :sup:`308.2` \

VERSION 1
There are three specific keys which are not directly used by GeoTrellis but which could be used by other clients: ``epsg``, ``xskew`` and ``yskew``.

The ``epsg`` attribute gives the coordinate system of the data. When absent, the coordinate system is assumed to be Web Mercator (i.e. "3785").

The ``xskew`` and ``yskew`` attributes support rotated rasters. When unspecified their values are assumed to be "0.0". GeoTrellis does not currently support rasters whose skew attributes are not zero (although they are allowed by the ARG format).

Data
----

The ARG file contains every cell value, starting with the upper-left cell (Northwest corner) and ending with the lower-right cell (Southeast corner). Notice that this is different than how the geographic extent is represented.

There is no header information in an ARG file, no checksum, and no form of compression. Each cell uses the same amount of space, so the total size of an ARG file is always equal to the number of cells times the size of each cell. For example, a 40x40 raster will have 1600 cells, so at 4 bytes per cell will use 1600 x 4 = 6400 bytes of space, or 6.4K.

Sometimes this kind of file is called a "raw file".

All data is stored in network byte order (big-endian). That is, a 32-bit integer with the value 1 would be represented as ``0x00000001``.

Notice that without the accompanying JSON file there is no way to know what geographic area a raster pertains to, or even the correct dimensions of the raster.

Examples
--------

.. code-block:: python

    #!/usr/bin/env python

    import struct, sys

    # given fmt and nodata, encodes a value as bytes
    def pack(fmt, nodata, value):
        if value is None: value = nodata
        return struct.pack(fmt, value)

    # packs the given values together as bytes
    def encode(fmt, nodata, values):
        chunks = [pack(fmt, nodata, v) for v in values]
        return ''.join(chunks)

    # translates the bytes "\x12\x13" into "0x1213"
    def show(s):
        chunks = ["%02x" % ord(c) for c in s]
        return '0x' + ''.join(chunks)

    # None means "no data"
    tests = [
        {'formats': [('int8', '>b', -(1<<7)),
                     ('int16', '>h', -(1<<15)),
                     ('int32', '>i', -(1<<31)),
                     ('int64', '>q', -(1<<63))],
         'data': [None, 2, -3, -4]},
        {'formats': [('uint8', '>B', (1<<8)-1),
                     ('uint16', '>H', (1<<16)-1),
                     ('uint32', '>I', (1<<32)-1),
                     ('uint64', '>Q', (1<<64)-1)],
         'data': [None, 2, 3, 4]},
        {'formats': [('float32', '>f', float('nan')),
                     ('float64', '>d', float('nan'))],
         'data': [None, 1.1, -20.02, 300.003]},
    ]

    print "2x2 raster values:"
    for d in tests:
        print "  data: %r" % d['data']
        for (name, fmt, nodata) in d['formats']:
            bytes = encode(fmt, nodata, d['data'])
            print "  %-7s %s" % (name, show(bytes))
            print ""

    print "nodata values:"
    for d in tests:
        for (name, fmt, nodata) in d['formats']:
            nd = pack(fmt, nodata, nodata)
            print "  %-7s %s (%s)" % (name, show(nd), nodata)
            print ""

