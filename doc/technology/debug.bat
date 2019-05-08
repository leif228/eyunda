set JPDA_ADDRESS=8000
set JPAD_TRANSPORT=dt_socket
SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8899
startup
