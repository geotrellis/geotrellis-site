# geotrellis-site

This project runs the [GeoTrellis](http://geotrellis.io) website.  It is
composed of two parts:

- A Jekyll-based static content site, and;
- A Spray server running GeoTrellis operations

## Usage

You need [Docker](https://www.docker.com/) to build and run the website.
Once you have it, running the entire site is easy:

```console
> docker-compose up
```

This will expose `localhost:8080` to listen for HTTP requests.
