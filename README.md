# geotrellis-site

The landing page for the GeoTrellis project.

## Requirements

- Vagrant 2.1+
- VirtualBox 5.2+

## Getting Started

From your workstation, execute the following command to bring up a Vagrant virtual machine with all of the necessary dependencies installed:

```bash
$ ./scripts/setup
```

Next, login to the Vagrant virtual machine and launch the Jekyll services:

```bash
$ vagrant ssh
vagrant@vagrant-ubuntu-trusty-64:/vagrant$ ./scripts/server
Starting vagrant_jekyll_1_1a6c4b153900 ... done
Attaching to vagrant_jekyll_1_1a6c4b153900
jekyll_1_1a6c4b153900 | Configuration file: /usr/src/app/_config.yml
jekyll_1_1a6c4b153900 |             Source: /usr/src/app
jekyll_1_1a6c4b153900 |        Destination: /usr/src/app/_site
jekyll_1_1a6c4b153900 |  Incremental build: disabled. Enable with --incremental
jekyll_1_1a6c4b153900 |       Generating...
jekyll_1_1a6c4b153900 |        Jekyll Feed: Generating feed for posts
jekyll_1_1a6c4b153900 |                     done in 0.406 seconds.
jekyll_1_1a6c4b153900 |  Auto-regeneration: enabled for '/usr/src/app'
jekyll_1_1a6c4b153900 | LiveReload address: http://0.0.0.0:35729
jekyll_1_1a6c4b153900 |     Server address: http://0.0.0.0:4000/
jekyll_1_1a6c4b153900 |   Server running... press ctrl-c to stop.
```

In order to get console access to the container running Jekyll, use:

```bash
$ ./scripts/console
```

## URLs

In order to access the content served by Jekyll, and the LiveReload endpoint, use the following links:

| Service | URL                                              |
| ------- | ------------------------------------------------ |
| Jekyll  | [`http://localhost:4000`](http://localhost:4000) |
