.. _Rendering:

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

Color Ramps
-----------

GeoTrellis provides a set of color ramps (a list of colors) from which to choose. These are provided to ease the transition from developer to cartographer. However, you need not feel constrained by these and can use your own color palettes as well. There are many good resources online for selecting color ramps.

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
