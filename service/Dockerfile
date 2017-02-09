# To match the `jekyll` image
FROM openjdk:8-jre

ADD srv/target/scala-2.10/site.jar /srv/
ADD srv/data/ /srv/data/

EXPOSE 9090

CMD java -jar /srv/site.jar
