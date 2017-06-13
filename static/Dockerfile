FROM ruby:2.3

# `jekyll` requires a JS runtime.
RUN apt-get update && apt-get install -y node

# Built assets remain on the host filesystem.
WORKDIR /build

# Build the static portions of the site.
CMD bundle install && jekyll build && mv _site/ /handoff
