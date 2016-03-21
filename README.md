# geotrellis-site

This project runs the [GeoTrellis](http://geotrellis.io) website. The website is powered by [Spray](http://spray.io/), and was derived from Spray's own documentation. It also uses [Sphinx](http://sphinx-doc.org/) to generate web pages from [reStructuredText](http://docutils.sourceforge.net/rst.html) files located in the [docs](docs/) subdirectory.

The documentation is served from the site sub-project of the `sbt` build. If you want to contribute to the documentation, use the steps below to build and view the site locally.

## Usage

First, ensure that you have [Vagrant](https://www.vagrantup.com/) 1.5+ and [Ansible](http://docs.ansible.com/intro_installation.html) 1.4+ installed. From there, run the following commands to start a local development server:

```bash
$ git clone https://github.com/geotrellis/geotrellis-site.git
$ cd geotrellis-site
$ vagrant up
$ open http://localhost:8080
```

After Ansible finishes provisioning the virtual machine, wait a few minutes for `sbt` to pull down dependencies and build the site (~1-3 minutes). If you see a `502 Bad Gateway` error, that usually means that `sbt` is not finished yet.

Ensure that you only edit the files inside the [docs](docs/) subdirectory. After saving a file, the changes will automatically be picked up by `sbt`.

## Deployment

[Packer](http://packer.io) is used to create EC2 AMIs using the same Ansible playbooks for provisioning a development environment. Ansible handles installing Packer inside of the Vagrant virtual machine, so if you've already spun that up, just provide your AWS credentials and execute Packer:

```bash
$ vagrant ssh
vagrant@geotrellis-site:~$ AWS_PROFILE="geotrellis-site" packer build /opt/geotrellis-site/deployment/packer/template.js
```

If successful, this should generate an EC2 AMI of the GeoTrellis site. Launching an instance of the AMI (we typically use `m3.large` instances) should yield a functioning clone of [geotrellis.io](http://geotrellis.io).

Many thanks to [spray.io](http://spray.io) for the codebase, from which all of the implementation was derived.
