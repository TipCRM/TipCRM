FROM hub.c.163.com/library/java:8-jre
MAINTAINER neilwan 1051750377@qq.com
ADD tipcrm.jar /var/tipcrm/tipcrm.jar
ADD application.yml /var/tipcrm/application.yml
WORKDIR /var/tipcrm
EXPOSE 80
EXPOSE 443
CMD java -jar tipcrm.jar --spring.config.location=application.yml
