# geotrellis-site

This project runs the [GeoTrellis](http://geotrellis.io) website.  It is
composed of two pieces:

1. A static content container with nginx
2. A Spray server running GeoTrellis operations


## Getting Started


### Requirements

- Vagrant 1.9+
- VirtualBox 5.0+
- AWS CLI 1.11+

***TL;DR***
```bash
$ ./scripts/setup
$ vagrant ssh
$ ./scripts/server
```

A virtual machine is used to encapsulate Docker dependencies. `docker-compose` is used within the VM to manage running the application and developing against it.

Vagrant is used to manage VirtualBox provisioning and configuration. Geotrellis Site follows the approach outlined [here](https://githubengineering.com/scripts-to-rule-them-all/) ("Scripts to Rule Them All") to have as consistent a development experience as possible. Use `scripts/setup` to provision a virtual machine. During provisioning `docker` and `docker-compose` will be installed on the guest machine. Once the VM is provisioned, run `scripts/server` to start a service that listens for HTTP requests on [http://localhost:8080](http://localhost:8080).


```bash
$ ./scripts/setup
$ vagrant ssh
$ ./scripts/server
```

## Scripts

Helper scripts are located in the `./scripts` directory at the root of this project. These scripts are designed to encapsulate and perform commonly used actions such as starting a development server, accessing a development console, or running tests.

| Script Name             | Purpose                                                      |
|-------------------------|--------------------------------------------------------------|
| `setup`                 | Provisions a vagrant VM										 |
| `server`                | Starts a development server                                  |
| `console`               | Gives access to a running container via `docker-compose run` |
| `test`                  | Runs tests for project                           			 |
| `cibuild`               | Invoked by CI server and makes use of `test`.                |
| `cipublish`             | Publish container images to container image repositories.    |



## Deploying

> NOTE: Requires terraform 0.9.3 or greater.

Deploying on AWS should be relatively simple as well. Note that, because
the terraform state is stored on S3, any updates can be applied without
having to tear down the previous deployment. Keep in mind, too, that
containers are tagged according to the SHA of the commit from which they
come - to ensure that ECS picks up the updated container, verify that all
changes have been committed.

```console
> make deploy
```

