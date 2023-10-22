FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

USER root

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

ADD wildfly-entrypoint.sh /opt/jboss/wildfly/customization/wildfly-entrypoint.sh

CMD /opt/jboss/wildfly/customization/wildfly-entrypoint.sh
