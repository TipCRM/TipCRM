FROM hub.c.163.com/library/java:8-jre
MAINTAINER neilwan 1051750377@qq.com
ADD tipcrm.jar /var/tipcrm/tipcrm.jar
ADD application.yml /var/tipcrm/application.yml
ENV LOG_HOME /logs/tipcrm
ENV LOG_LEVEL WARN
WORKDIR /var/tipcrm
EXPOSE 80
EXPOSE 443
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
CMD java -jar tipcrm.jar --spring.config.location=application.yml
