# Must be 2.3.x for version bounds set in the Gemfile to be accepted.
FROM ruby:2.3.3

# `jekyll` requires a JS runtime.
RUN apt-get update && apt-get install -y node

# Built assets remain on the host filesystem.
VOLUME /build
WORKDIR /build

# Build the static portions of the site.
CMD bundle install && jekyll build
