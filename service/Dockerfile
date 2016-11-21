# To match the `jekyll` image
FROM openjdk:8-jre

RUN apt-get update -y && apt-get install -y wget unzip

WORKDIR /usr/local/src
COPY . /usr/local/src

# Unpack compressed Hillshade data
RUN unzip srv/data/hillshade/hills.zip
RUN mv hills.arg srv/data/hillshade/

# Bundle the server
RUN ./sbt "project srv" assembly

EXPOSE 9090

CMD java -jar srv/target/scala-2.10/site.jar
