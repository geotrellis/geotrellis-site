Contributing
============

We value all kinds of contributions from the community, not just actual code. Maybe the easiest and yet very good way of helping us improve *geotrellis* is to ask questions, voice concerns or propose improvements on the :ref:`Mailing List`.

If you do like to contribute actual code in the form of bug fixes, new features or other patches this page gives you more info on how to do it.


Building GeoTrellis
-------------------

Since *geotrellis* is open-source and hosted on github you can easily build it yourself.

Here is how:

1. Install SBT_ (the master branch is currently built with SBT_ 0.12.3).
2. Check out the *geotrellis* source code from the `github repository`_. Pick the branch corresponding to the version
   you are targeting (check the :ref:`Current Versions` chapter for more info on this).
3. Run ``sbt test`` to compile the suite and run all tests.

Style Guide
-----------

We try to follow the `Scala Style Guide`__ as closely as possible, although you will see some variations throughout the codebase. When in doubt, follow that guide.

__ http://docs.scala-lang.org/style/

git Branching Model
-------------------

The *geotrellis* team follows the "standard" practice of using the ``master`` branch as main integration branch,
with WIP- and feature branches branching of it. The rule is to keep the ``master`` branch always "in good shape",
i.e. having it compile and test cleanly.

Additionally we maintain release branches for older and possibly future releases.


git Commit Messages
-------------------

We follow the "imperative present tense" style for commit messages (more info here__)

__ http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html

Issue Tracking
--------------

Currently the *geotrellis* team uses the `Issues Page`_ of the projects `github repository`_ for issue management.
If you find a bug and would like to report it please go there and create an issue.

If you are unsure, whether the problem you've found really is a bug please ask on the :ref:`Mailing List` first.

Pull Requests
-------------

If you'd like to submit a code contribution please fork the `github repository`_ and `send us pull request`_
against the ``master`` branch (or the respective release branch, depending on what version your patch is targeting).
Please keep in mind that we might ask you to go through some iterations of discussion and refinements before merging and
that you will need have signed a CLA first!

Contributing documentation
--------------------------

The site, i.e. what you see here at http://geotrellis.io, is built with `spray`__ and is modified from the documentation of
that project. It uses sphinx_ to generate
documentation from `reStructured text`_ files located in the ``docs`` folder of the `geotrellis-site`__ repository checkout. This is a separate repository from the core GeoTrellis code.
The documentation is served from the ``site`` sub-project of the sbt build. If you want to contribute documentation make sure you can
build and view the site locally. Follow these instructions to get the site project working locally:

* Install sphinx_  (in Debian / Ubuntu install the ``python-sphinx`` package, for OS/X see this `mailing list thread`_).
* Find the path of ``sphinx-build`` (in Ubuntu it's probably ``/usr/bin/sphinx-build``)
* Set the SPHINX_PATH environment variable to that path.
* Run sbt
* In the sbt shell use ``project site`` to change into the site project.
* Use ``compile`` to build the site, this will take some time when running for the first time (~ 1 - 3 minutes).
* Use ``re-start`` [1]_ to start the local site server.
* Browse to http://localhost:8080
* Use ``~re-start`` in sbt to let it monitor changes to the documentation sources automatically.
* Edit the documentation files inside the ``docs`` subdirectory. After saving a file, it will be automatically
  picked up by sbt, then it will be regenerated and be available in the browser after ~ 1 - 5 seconds with a refresh
  of the page.

.. [1] ``re-start`` is a task from the sbt-revolver_ plugin which starts a project in the background while you can
       still use the sbt shell for other tasks.
.. _sphinx: http://sphinx-doc.org/
.. _`reStructured text`: http://docutils.sourceforge.net/docs/user/rst/quickref.html
.. _`mailing list thread`: https://groups.google.com/d/msg/spray-user/x2PJUYkn1Vs/JxhT_rRoJS0J
.. _sbt-revolver: https://github.com/spray/sbt-revolver

__ http://spray.io
__ http://github.com/geotrellis/geotrellis-site

.. _`our CLA`:

Contributor License Agreement (CLA)
-----------------------------------

Contributions to the project, no matter what kind, are always very welcome.
Everybody who contributes code to GeoTrellis is going to be asked to sign a Contribute License
Agreement (CLA). In order the provide a proper legal foundation for this we need to ask you to sign `our CLA`_, which is a direct
adaptation of the `Apache Foundation's Individual Contributor License Agreement V2.0`__.
This particular agreement has been used by other software projects
in addition to Apache and is generally accepted as reasonable within the open source community.

__ http://www.apache.org/licenses/icla.txt

There are a couple ways to get the CLA to us:

- Download a copy of the CLA:
  `Individual Contributor License Agreement`_ (PDF). If you're working on GeoTrellis as an individual, this is the form you should use. 
  `Corporate Contributor License Agreement`_ (PDF). If you're working on GeoTrellis as part of your job, you and your employer will need to download and sign this form. More details are in this FAQ.

- Print out the CLA and sign it.

- Send the CLA to Azavea by:

  - Scanning and emailing the document to `cla -at- azavea -dot- com`
  - Faxing a copy to +1-215-925-2600.
  - Mailing a hardcopy to:
    Azavea, 340 N 12th St, Suite 402, Philadelphia, PA 19107 U.S.A.


CLA FAQ
-------

I want to contribute. Do I need to sign anything to get started?
  Every contributor of non-trivial amounts of code (more than just a line or two) to GeoTrellis will
  be required to sign such a document. If somebody is unable to sign the document, their contribution
  (whether it be code, documentation or language translations) will not be accepted to the source code
  repository.

Why is a signed CLA required?
  Being able to make a clear statement about the origins of the code is very important as GeoTrellis
  is adopted by large organizations that are necessarily cautious about adopting products with unknown
  origins. We would like to see GeoTrellis used and distributed as widely as possible and in order to
  do this with confidence, we need to be sure about the origins of the code and documentation. The CLA
  ensures that once you have provided a contribution, you cannot try to withdraw permission for its use
  at a later date. People and companies can therefore use the project, confident that they will not be
  asked to stop using pieces of the code at a later date.

  The license agreement is a legal document in which you state you are entitled to contribute the code,
  documentation or translation to GeoTrellis and are willing to have it used in distributions and
  derivative works. This means that should there be any kind of legal issue in the future as to the origins
  and ownership of any particular piece of code, we will have has the necessary forms on file from the
  contributor(s) saying they were permitted to make this contribution.

  Finally, consolidation of licensing of the code enables the future possibility of relicensing the whole
  code base should that become desirable to the community. Without the CLAs, Azavea would have to
  contact and obtain permission from every single contributor before the new license could be applied.
  This has happened on other open source projects, and we want to learn from these past issues.

Can I submit patches without having signed the CLA?
  No. We will be asking all new contributors and patch submitters to sign before they submit anything
  substantial. Trivial patches like spelling fixes or missing words in the documentation won't require an
  agreement, since anybody could do those. However, anything of substance will require a CLA.

Can I withdraw permission to use my contributions at a later date?
  No. That’s kind of the point. This protects the whole community, enabling both Azavea and
  downstream users of the project to rely on it. Once you make a contribution, you are saying we can
  use that piece of code forever. You can, however, stop your participation in the project at any time,
  but you cannot rescind your previous assignments or grants with respect to your prior contributions.

If I sign, will my code be used?
  Not all contributions will be used or incorporated into the code for the project. The decision to
  incorporate the code or not is at the discretion of the project technical lead.

Am I giving away the copyright to my contributions?
  No. This is a license agreement, not a copyright assignment. You still maintain the full copyright
  for your contributions. You are only providing a license to Azavea to distribute your code within the
  GeoTrellis project.

What about if I do GeoTrellis development as part of my job?
  If any of your contributions to GeoTrellis are created as part of your employment, it may be owned
  by your employer. In that case, your employer, or somebody able to represent the company (usually
  a VP or higher) needs to sign the corporate version of the Contributor Licensing Agreement in order
  for that contribution to be accepted into GeoTrellis. They will need to include the names of the
  developers (you and any others who may contribute from your employer) who are able to submit
  contributions on behalf of the employer. That list can be updated as new people are employed or
  others leave.

  You may still sign an individual CLA, whether or not your employer signs one. Not all the work you
  do will necessarily belong to your employer, and we still need permission to license your individual
  contributions. If you have signed an individual CLA, but not a corporate one, be very careful about
  submitting contributions you have made. We cannot accept anything that you do not have the rights
  to license in the first place or anything that includes code that belongs to your employer. Similarly, if
  you are a consultant who may be creating GeoTrellis patches as part of a job, make sure you and
  your employer understand who owns the rights to the code. Please only submit work to which you
  own the rights. The CLA is a legal declaration by you that you have the right to grant such a license for
  your contributions. It is up to you to make sure that is true.

Are Contributor Agreements like this common?
  Yes, many open source communities and projects use Contributor License Agreements, including the
  Apache Software Foundation, the Open Geospatial Foundation and the Eclipse Foundation.

.. _Individual Contributor License Agreement: http://geotrellis.github.com/files/2012_04_04-GeoTrellis-Open-Source-Contributor-Agreement-Individual.pdf?raw=true
.. _Corporate Contributor License Agreement: http://geotrellis.github.com/files/2012_04_04-GeoTrellis-Open-Source-Contributor-Agreement-Corporate.pdf?raw=true

.. _SBT: http://www.scala-sbt.org/
.. _issues page: https://github.com/geotrellis/geotrellis/issues
.. _github repository: https://github.com/geotrellis/geotrellis/
.. _send us pull request: https://help.github.com/articles/creating-a-pull-request
