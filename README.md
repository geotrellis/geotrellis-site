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
vagrant@vagrant:/vagrant$ ./scripts/server
Starting vagrant_jekyll_1 ... done
Attaching to vagrant_jekyll_1
jekyll_1  | Configuration file: /usr/src/app/_config.yml
jekyll_1  |             Source: /usr/src/app
jekyll_1  |        Destination: /usr/src/app/_site
jekyll_1  |  Incremental build: disabled. Enable with --incremental
jekyll_1  |       Generating...
jekyll_1  |                     done in 1.599 seconds.
jekyll_1  |  Auto-regeneration: enabled for '/usr/src/app'
jekyll_1  |         ** ERROR: directory is already being watched! **
jekyll_1  |
jekyll_1  |         Directory: /usr/src/app/deployment/ansible/roles/azavea.docker/examples/roles/azavea.docker
jekyll_1  |
jekyll_1  |         is already being watched through: /usr/src/app/deployment/ansible/roles/azavea.docker
jekyll_1  |
jekyll_1  |         MORE INFO: https://github.com/guard/listen/wiki/Duplicate-directory-errors
jekyll_1  |     Server address: http://0.0.0.0:4000/
jekyll_1  | LiveReload address: http://0.0.0.0:35729
jekyll_1  |   Server running... press ctrl-c to stop.
jekyll_1  |         LiveReload: Browser connected
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
