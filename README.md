geotrellis-site
===============

This project runs the site you see at http://geotrellis.io, is built with spray and is modified from the documentation of that project. It uses sphinx to generate documentation from reStructured text files located in the docs folder of the geotrellis-site repository checkout. The documentation is served from the site sub-project of the sbt build. If you want to contribute documentation make sure you can build and view the site locally. Follow these instructions to get the site project working locally:

Install sphinx (in Debian / Ubuntu install the python-sphinx package, for OS/X see this mailing list thread).
Find the path of sphinx-build (in Ubuntu it’s probably /usr/bin/sphinx-build)
Set the SPHINX_PATH environment variable to that path.
Run sbt
In the sbt shell use project site to change into the site project.
Use compile to build the site, this will take some time when running for the first time (~ 1 - 3 minutes).
Use re-start or run to start the local site server.

***NOTE: You must unzip the hills.zip in `site/data/hillshade` in order for the hillshade demo to work (the .arg file is larger than github allows for commit)***

Note:	re-start is a task from the sbt-revolver plugin which starts a project in the background while you can still use the sbt shell for other tasks.

Browse to http://localhost:8080
Use ~re-start in sbt to let it monitor changes to the documentation sources automatically.
Edit the documentation files inside the docs subdirectory. After saving a file, it will be automatically picked up by sbt, then it will be regenerated and be available in the browser after ~ 1 - 5 seconds with a refresh of the page.

Many thanks to [spray.io](http://spray.io) for the codebase, from which all of the implementation was derived.
