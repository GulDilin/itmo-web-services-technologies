JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=standalone

CUR_DIR=$(dirname "$0")

echo "JBOSS_HOME  : " $JBOSS_HOME
echo "JBOSS_CLI   : " $JBOSS_CLI
echo "JBOSS_MODE  : " $JBOSS_MODE

echo "=> Current dir" $CUR_DIR

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Write datasource commands"
COMMANDS_FILE_PATH=$(dirname "$0")/commands.cli
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
