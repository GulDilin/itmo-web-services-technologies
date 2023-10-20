MAVEN_REPO=https://repo1.maven.org/maven2
POSTGRES_DRIVER_VERSION=42.6.0
POSTGRES_JAR_NAME=postgresql-$POSTGRES_DRIVER_VERSION.jar

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=standalone

DB_URL=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME

CUR_DIR=$(dirname "$0")

echo "JBOSS_HOME  : " $JBOSS_HOME
echo "JBOSS_CLI   : " $JBOSS_CLI
echo "JBOSS_MODE  : " $JBOSS_MODE

echo "DB_JNDI_NAME  : " $DB_JNDI_NAME
echo "DB_HOST       : " $DB_HOST
echo "DB_PORT       : " $DB_PORT
echo "DB_NAME       : " $DB_NAME
echo "DB_URL        : " $DB_URL

echo "=> Current dir" $CUR_DIR

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

#POSTGRES_JAR_PATH=$CUR_DIR/$POSTGRES_JAR_NAME
#echo "=> Download ${POSTGRES_JAR_NAME} to ${POSTGRES_JAR_PATH}"
#wget "${MAVEN_REPO}/org/postgresql/postgresql/${POSTGRES_DRIVER_VERSION}/${POSTGRES_JAR_NAME}" -P $CUR_DIR
#ls $POSTGRES_JAR_PATH


echo "=> Write datasource commands"
COMMANDS_FILE_PATH=$(dirname "$0")/commands.cli
#echo "module add --name=org.postgres --resources=${POSTGRES_JAR_PATH} --dependencies=jakarta.api,jakarta.transaction.api" >> $COMMANDS_FILE_PATH
#echo '/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)' >> $COMMANDS_FILE_PATH
#echo "data-source add --jndi-name=${DB_JNDI_NAME} --name=${DB_NAME} --connection-url=${DB_URL} --driver-name=postgres --user-name=${DB_USERNAME} --password=${DB_PASSWORD}" >> $COMMANDS_FILE_PATH
echo '/subsystem=webservices:write-attribute(name=wsdl-host,value=host.docker.internal)' >> $COMMANDS_FILE_PATH
echo '/subsystem=webservices:write-attribute(name=modify-wsdl-address,value=true)' >> $COMMANDS_FILE_PATH

echo "=> Written commands:"
cat $COMMANDS_FILE_PATH

$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
$JBOSS_CLI -c --file=$COMMANDS_FILE_PATH

echo "=> Shutting down WildFly"
$JBOSS_CLI -c ":shutdown"

echo "=> Restarting WildFly"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0
