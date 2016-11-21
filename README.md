# geotrellis-site

This project runs the [GeoTrellis](http://geotrellis.io) website.  It is
composed of two pieces:

1. A static content container with nginx
2. A Spray server running GeoTrellis operations

## Running Locally
You need [Docker](https://www.docker.com/) to build and run the website.
Once you have it, running the entire site is easy:

```console
> make start
```

This will expose `localhost:8080` to listen for HTTP requests.

## Deploying

> NOTE: Requires terraform 0.7.5 or greater  

Deploying on AWS should be relatively simple as well Note that, because
the terraform state is stored on S3, any updates can be applied without
having to tear down the previous deployment. Keep in mind, too, that
containers are tagged according to the SHA of the commit from which they
come - to ensure that ECS picks up the updated container, verify that all
changes have been committed.

```console
> make deploy
```

