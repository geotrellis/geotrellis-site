.. _Rendering-0.9.0:

Rendering
=========

.. includecode:: code/Render.scala
   :snippet: render-png

Rendering to PNG
----------------

Color Ramps
-----------

GeoTrellis provides a set of color ramps (a list of colors) from which to choose. These are provided to ease the transition from developer to cartographer. However, you need not feel constrained by these and can use your own color palettes as well. There are many good resources online for selecting color ramps.

Color Maps
----------

Rendering Images from a Raster
------------------------------

GeoTrellis will render raster data as an image (in png format) for display on a web map. It’s time to put on your cartographer’s cap and consider how best to represent your data on the map.

Some rasters only have a few unique values. For example, a raster representing a rank between 1 and 5 or a landcover raster in which each value is a different kind of land use (e.g. 11 for water or 40 for forest). In these cases, it makes sense to assign each value its own color. But when raster values represents counts, measurements, or ratios, there are usually many distinct values. One common way to represent raster data visually on a map is to divide up the values of the data into different categories called classes, and then represent each class on the map with its own color. Each class has a lower and upper limit that define what values fall within it. For example, a class might contain values between 0 and 10. We call these limits class breaks.

When rendering a raster in GeoTrellis, you can either use one of the automated classification schemes or manually determine the class breaks. For example, if you were creating a map of average household income, you might want one of the classes to be defined as below the federal poverty guidelines (manual classification) or organized into several equal intervals between the minimum and maximum (using the built-in linear breaks classification scheme).

Classification
--------------

GeoTrellis is able to generate two types of class breaks: quantile and linear. By default, GeoTrellis will create quantile class breaks for your raster. The intuitive idea of a quantile break is that there should be an equal number of cells in each class. For example, if most cells in our income map have incomes below the national average, we would generate more classes for that range of values and only a few classes for higher income ranges, even though those ranges will have a bigger difference between the low and high limits. This classification scheme is particularly good at emphasizing highlights or hotspots where values are particularly high or low.

You can also request linear breaks (or “equal interval” breaks). Linear breaks simply divide up the values between the lowest value and the highest value, with the difference between the low and high limit being the same for each class. For example, if we want 5 linear breaks between 0 and 50, the classes would be 0-10, 10-20, 20-30, 30-40, and 40-50.

There are other classification schemes that GeoTrellis does not currently support, such as Natural breaks (Jenks) and Standard Deviation, but we intend to implement them in the future. Let us know if you have a need.

.. _Color Ramps-0.9.0:

Color Ramps
-----------

GeoTrellis provides a set of color ramps (a list of colors) from which to choose. These are provided to ease the transition from developer to cartographer. However, you need not feel constrained by these and can use your own color palettes as well. There are many good resources online for selecting color ramps.

Usage
-----

.. code-block:: scala

  import geotrellis._
  import geotrellis.source._
  import geotrellis.render.ColorRamps

  val rasterSource:RasterSource = ???

  rasterSource.renderPng(ColorRamps.BlueToOrange)

Diverging Color Schemes
-----------------------

.. image:: images/01_blue-to-orange.png
  :align: left

.. image:: images/02_green-to-orange.png
  :align: left

.. image:: images/03_blue-to-red.png
  :align: left

.. image:: images/04_green-to-red-orange.png
  :align: left

From left to right:

Blue to Orange
  An 11-step diverging color ramp from blue to gray to orange.
  The gray critical class in the middle clearly shows a median or zero value. Example uses include temperature, climate, elevation, or other color ranges where it is necessary to distinguish categories with multiple hues.

Blue to Red
  A 10-step diverging color ramp from blue to red.
  Example uses include elections and politics, voter swing, climate or temperature, or other color ranges where it is necessary to distinguish categories with multiple hues.
 
Green to Red-Orange
  A 10-step diverging color ramp from green to red-orange.
  Example uses include elections and politics, voter swing, climate or temperature, or other color ranges where it is necessary to distinguish categories with multiple hues.
 
Green to Orange
  A 13-step diverging color ramp from green to orange.
  Example uses include elevation, relief maps, topography, or other color ranges where it is necessary to distinguish categories with multiple hues.

Sequential Color Schemes
------------------------

.. image:: images/05_light-to-dark-sunset.png
  :align: left

.. image:: images/06_light-to-dark-green.png
  :align: left

.. image:: images/07_yellow-to-red-heatmap.png
  :align: left

.. image:: images/08_blue-to-yellow-to-red-heatmap.png
  :align: left

.. image:: images/09_dark-red-to-yellow-heatmap.png
  :align: left

.. image:: images/10_purple-to-dark-purple-to-white-heatmap.png
  :align: left

From left to right:

Light to Dark - Sunset
  An 11-step sequential color ramp showing intensity from light to dark.
  This color ramp is perfect for showing density where it is critical to highlight very different values with bold colors at the higher, darker end of the ramp. Example uses include population density, accessibility, or ranking.

Light to Dark - Green
  A basic 8-step sequential color ramp showing light to dark in shades of green.
  Example uses include density, ordered data, ranking, or any map where darker colors represent higher data values and lighter colors represent lower data values, generally.


Yellow to Red - Heatmap
  An 8-step sequential heatmap from yellow to dark red.
  Great for heatmaps on a light basemap where the hottest values are more opaque or dark. Also useful for sequential color ranges where the lowest value is the median or zero value.

Blue to Yellow to Red Spectrum - Heatmap
  An 11-step heatmap from blue to yellow to red.
  Great for showing a wide range of values with clear differences in hue.

Dark Red to Yellow-White - Heatmap
  A 10-step sequential heatmap from dark red to yellow to white.
  Great for heatmaps where the hottest values should look more vibrant or intense.

Light Purple to Dark Purple To White
  An 8-step sequential heatmap to show intensity with shades of purple with white as the "hottest" value.
  Great for light or gray basemaps, or where the highest value needs to be called out visually.

Qualitative or Categorical Schemes
----------------------------------

.. image:: images/11_bold-land-use-qualitative.png
  :align: left

.. image:: images/12_muted-terrain-qualitative.png
  :align: left

From left to right:

Bold Land Use
  An 8-hue qualitative scheme used to show a clear difference in categories that are unordered or very different.
  Example uses include zoning, land use, land cover, or maps where all categories or groups are equal in visual strength/magnitude.

Muted Terrain
  An 8-hue qualitative scheme used to show different kinds of map topology or features.
  This is generally used to show landforms, terrain, and topology.

Customizing Color Ramps
-----------------------

You can create your own color ramp with a list of RBG hex color values.

.. includecode:: code/Render.scala
   :snippet: customize-color-ramps-rgb

By default, GeoTrellis will generate a number of classes to match the number of colors in the color ramp. You can ask GeoTrellis to generate a new ramp by generating a requested number of new breaks using an existing color ramp as a guide. The first and last colors will be the first and last colors from the existing color ramp, and the rest will be interpolated. For example, given a color ramp of two colors, red and yellow, a request for 5 colors would return Red, Yellowish-Red, Orange, Reddish-Yellow, Yellow.

.. includecode:: code/Render.scala
   :snippet: customize-color-ramps-other

There are many online and offline resources for generating color palettes for cartography including:

- `ColorBrewer 2.0`__
- `Cartographer’s Toolkit: Colors, Typography, Patterns`__, by Gretchen N. Peterson
- `Designing Better Maps`__, by Cynthia A. Brewer
- `Designed Maps: A Sourcebook`__, by Cynthia A. Brewer

__ http://colorbrewer2.org/js/
__ http://www.amazon.com/Cartographers-Toolkit-Colors-Typography-Patterns/dp/0615467946
__ http://www.amazon.com/Designing-Better-Maps-Guide-Users/dp/1589480899/
__ http://www.amazon.com/Designed-Maps-Sourcebook-GIS-Users/dp/1589481607/

RGBA vs RGB values
------------------

One way to represent a color is as an RGB hex value, as often seen in CSS or graphics programs. For example, the color red is represented by #FF0000 (or, in scala, 0xFF0000).

Internally to GeoTrellis, colors are represented as RGBA values, which includes a value for transparency. These can be represented with 8 instead of 6 hex characters (with the alpha opacity value being the last two charcters) such as 0xFF0000FF for opaque red. When using the programming interface, just be sure to keep the distinction in mind and, when using RGB values, be sure to use the utility methods that convert them into RGBA values (such as ``ColorRamp.createWithRGBColors``).
