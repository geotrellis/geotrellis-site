---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: home
permalink: "/"
page_class: p-home
partials_location: "_home/"

# NO EDITING ABOVE THIS LINE
# # # # # #

seo_description: "GeoTrellis can help build web applications that work with raster data. It provides a set of functionality to aid the translation of queries into GeoTrellis operations, that load raster data, operate on your data, and render the results of those operations in a format useful to return to the client."
title: "Home"

# # # # # #
# Page copy

# intro section
intro: 
  blurb: "**GeoTrellis** is a geographic data processing engine for high performance applications."
  features:
    - title: I/O
      blurb: "GeoTrellis provides data types for working with rasters in the Scala language, as well as fast reading and writing of these data types to disk."
    - title: Operations
      blurb: "GeoTrellis provides a number of operations to manipulate raster data, including cropping/warping, Map Algebra operations, and rendering operations, as well as vector to raster operations such as Kernel Density and vectorization of raster data."
    - title: Web Service Utilities
      blurb: "GeoTrellis includes a set of utilities to help developers create useful, high performing web services that load and manipulate raster data."
# intro section

# demos section
demos:
  title: "Demos"
  blurb: "We are always figuring out new ways to leverage the powerful processing power of GeoTrellis. The source code for demos can be found on our [GitHub account](http://github.com/geotrellis)."

# demos section

# projects section
projects:
  title: "Projects that use GeoTrellis"
  blurb: "Explore applications that leverage the high performance geoprocessing capabilities of GeoTrellis."
  
# projects section

# developing section
developing:
  title: "Developing with GeoTrellis"
  blurb: "We have an active Gitter channel, where we can help you get started and answer questions you may have about using GeoTrellis in your project!"
  url: https://gitter.im/geotrellis/geotrellis
  image: ./assets/images/developing-code.svg
  image-alt: "GeoTrellis code in two overlapping terminal windows."
# developing section

# call-to-action section
cta-blurb: "Think GeoTrellis could help your organization, but not sure where to start? Let’s talk."
# call-to-action section

---

{% include {{ page.partials_location }}home.html %}
