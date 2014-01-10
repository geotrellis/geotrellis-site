.. _Current Versions:

Current Versions
================

0.9.0
-----

This version targets Scala 2.10.x and Akka 2.2.x.


1.0/1.1/1.2
-----------

Since the Scala universe has shifted from Scala 2.9 to Scala 2.10 the next *spray* release is a triple release,
targeting both Scala and three Akka versions at the same time.

Currently the first release candidate for *spray* 1.0/1.1/1.2 is available, please choose |1.0|, |1.1| or
|1.2| depending on what Scala/Akka version you are targeting.

.. rst-class:: wide

- | *spray* |1.0| is built against Scala 2.9.3 and Akka 2.0.5.
  | It's sources live in the `release/1.0`_ branch of the *spray* repository.

- | *spray* |1.1| is built against Scala 2.10.3 and Akka 2.1.4.
  | It's sources live in the `release/1.1`_ branch of the *spray* repository.

- | *spray* |1.2| is built against Scala 2.10.3 and Akka 2.2.3.
  | It's sources live in the `release/1.2`_ branch of the *spray* repository.
  | (Please note that Akka 2.2.3 or later is *required*, earlier Akka versions will *not* work!)

For information about where to find the *spray* artifacts please check out the :ref:`maven-repo` chapter.

.. |1.0| replace:: **1.0-RC4**
.. |1.1| replace:: **1.1-RC4**
.. |1.2| replace:: **1.2-RC4**


Nightly Builds
--------------

If you'd like to have access to the most recent changes and additions without having to build *spray* yourself you can
rely on the nightly builds, which we are currently publishing for the `release/1.0`_, `release/1.1`_ and `release/1.2`_
branches of the *spray* repository. Every day shortly past midnight UTC a new build is made available unless the
respective branch has not seen any new commits since the last build.

In order to help you identify the exact commit from which a build is cut every artifact directory includes a
``commit-<version>.html`` file containing the commit hash with a link to the commit on github.

Nightly builds are available from the http://nightlies.spray.io repository.

.. _akka: http://akka.io
.. _github wiki: https://github.com/geotrellis/geotrellis/wiki
.. _master: https://github.com/geotrellis/geotrellis
.. _release/0.8.2: https://github.com/geotrellis/geotrellis/tree/0.8.2
.. _release/0.9.0: https://github.com/geotrellis/geotrellis/tree/0.9.0-RC1
