FROM adoptopenjdk/openjdk11:centos

ENV REFRESHED_AT 2021-10-31

RUN set -eux; \
    yum -y update; \
    mv /etc/localtime /etc/localtime.bak && ln -s /usr/share/zoneinfo/Europe/Warsaw /etc/localtime; \
    yum -y install zip unzip wget curl less nano bind-utils net-tools traceroute iproute lsof psmisc whereis;

RUN set -eux; \
    curl -L https://www.scala-sbt.org/sbt-rpm.repo > sbt-rpm.repo && mv sbt-rpm.repo /etc/yum.repos.d/ && yum -y install sbt; \
    mkdir app; cd app;

WORKDIR /app

EXPOSE 9000
CMD ["sbt", "-Dsbt.boot.directory=/.sbt/", "-Dsbt.ivy.home=/.sbt/", "-Divy.home=/.sbt/", "-J-Xms512M", "-J-Xmx1024M", "-J-Xss1M", "clean", "run"]
