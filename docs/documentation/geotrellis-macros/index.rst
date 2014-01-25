.. _geotrellis-macros:

geotrellis-macros
=================

The *geotrellis-macro* project holds macros for the goetrellis project. Macros must be defined in a separate project, since to perform macro expansion, the compiler needs a macro implementation in executable form.

The *geotrellis-macro* jar is pulled in as dependency from the core *geotrellis* jar, so you won't have to worry about pulling this dependency into your project manually.

Checking for NoData
-------------------

isNoData \ isData - These functions are for checking if a data type is NoData or not. See :ref:`NoData` for an explination of what NoData means. Using a macro for this check allows for different overloaded versions of the functions to inline their specific NoData check for the different datatypes; for example, 

.. includecode:: code/MacroExamples.scala
   :snippet: noData-examples

Future Macro work
-----------------

We hope to add more macros to this project in order to take advantage of the API and optimization improvements that can be gained from this newer feature of the Scala language. If you have ideas about how macros could help, let us know.  
