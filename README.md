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
Deploying on AWS should be relatively simple as well (note that terraform
is required in addition to docker for this step):

```console
> make deploy
```

