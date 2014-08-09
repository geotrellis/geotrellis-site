.. _geotiffreader:
.. _`GeoTiff Reader`:

GeoTiff Reader
==============

GeoTrellis incorperates a GeoTiff reader which efficiently reads GeoTiffs and allows the user to access both individual metadata about the GeoTiff and also convert the GeoTiff to a raster and the belonging extent.

GeoTiff Structure
-----------------

A GeoTiff file is essentially a TIFF 6.0 compliant file which, in addition to the TIFF 6.0 specification requirements, also contains geographical data. A TIFF file contains multiple image file directories (from here on called IFDs), and each IFD represents an image. The IFD contains both the raw image data and metadata, such as the dimension of the image and which type of pixels are used etcetera. The geographical data is represented by a custom tag which is called the GeoKeyDirectory, this tag contains information about the extent of the raster, how it is calculated and which projection should be used to map this raster to the earth.

Reader Functionality
--------------------

The reader supports the full TIFF 6.0 specification but has no support for JPEG decompression. The current compressions which are supported are:

 - LZW
 - PackBits
 - ZLib
 - Huffman Run Length Encoding (RLE)
 - CCITT Group 3
 - CCITT Group 4

The reader takes an ``ByteBuffer`` as it's constructor argument and when done reading it returns a ``GeoTiff`` which contains a ``Vector`` of IFDs.

From these IFDs you can call ``toRaster`` to get a ``Tile`` and the ``Extent``. These you can later use with GeoTrellis other functionality.

A simple read from the file system and extracting the raster and the extent looks like this:

.. code-block:: scala

    val filePath = "path/to/my/geotiff.tif"
    val source = Source.fromFile(filePath)(Codec.ISO8859)

    val geoTiff = GeoTiffReader(source).read
    source.close

    geoTiff.imageDirectories.foreach { ifd =>
        val (tile, extent) = ifd.toRaster
        // Do something with tile and extent.
    }

The reader reads the NODATA value from the TIFF tag 42113, which GDAL and ESRI uses.

Further Reading
---------------

 - `TIFF 6.0 Specification`_
 - `GeoTiff Specification`_
 - `GDAL NODATA Tag Specification`_
.. _`TIFF 6.0 Specification`: https://partners.adobe.com/public/developer/en/tiff/TIFF6.pdf
.. _`GeoTiff Specification`: http://landsathandbook.gsfc.nasa.gov/pdfs/geotiff_spec.pdf
.. _`GDAL NODATA TAG Specification`: http://www.awaresystems.be/imaging/tiff/tifftags/gdal_nodata.html
