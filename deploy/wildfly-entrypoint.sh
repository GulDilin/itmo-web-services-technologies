MAVEN_REPO=https://repo1.maven.org/maven2
POSTGRES_DRIVER_VERSION=42.6.0
POSTGRES_JAR_NAME=postgresql-$POSTGRES_DRIVER_VERSION.jar

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=standalone

DB_URL=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME

echo "JBOSS_HOME  : " $JBOSS_HOME
echo "JBOSS_CLI   : " $JBOSS_CLI
echo "JBOSS_MODE  : " $JBOSS_MODE

echo "DB_JNDI_NAME  : " $DB_JNDI_NAME
echo "DB_HOST       : " $DB_HOST
echo "DB_PORT       : " $DB_PORT
echo "DB_NAME       : " $DB_NAME
echo "DB_URL        : " $DB_URL

echo "=> Current dir" `dirname "$0"`

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Download ${POSTGRES_JAR_NAME}"
wget "${MAVEN_REPO}/org/postgresql/postgresql/${POSTGRES_DRIVER_VERSION}/${POSTGRES_JAR_NAME}"

echo "=> Write datasource commands"
echo "module add --name=org.postgres --resources=${POSTGRES_JAR_NAME} --dependencies=javax.api,javax.transaction.api" >> commands.cli
echo '/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)' >> commands.cli
echo "data-source add --jndi-name=${DB_JNDI_NAME} --name=${DB_NAME} --connection-url=${DB_URL} --driver-name=postgres --user-name=${DB_USERNAME} --password=${DB_PASSWORD}" >> commands.cli

echo "=> Written commands:"
cat commands.cli

$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
$JBOSS_CLI -c --file=`dirname "$0"`/commands.cli

echo "=> Shutting down WildFly"
$JBOSS_CLI -c ":shutdown"

echo "=> Restarting WildFly"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0
