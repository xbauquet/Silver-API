FROM openjdk:8

MAINTAINER  Xavier Bauquet <xavier.bauquet@gmail.com>

RUN mkdir /root/workdir/

RUN apt-get update
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Below command will run without cache
# http://dev.im-bot.com/docker-select-caching/
ARG CACHEBUST=1

COPY ./api/ /root/workdir/api/
WORKDIR /root/workdir/api/
RUN ./gradlew clean shadowJar

EXPOSE 4567

CMD java -jar ./build/libs/api-*-all.jar
