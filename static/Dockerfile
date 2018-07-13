FROM ruby:2.3

# `jekyll` requires a JS runtime.
RUN apt-get update && apt-get install -y nodejs rsync

RUN mkdir -p /build /handoff
WORKDIR /build

COPY Gemfile /build/
COPY Gemfile.lock /build/
RUN bundle install
