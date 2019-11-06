#!/bin/bash

APP_ENV='pro'

APP_NAME=$(ls *.jar)

APP_COUNT=$(ls *.jar | wc -l)

if [[ ${APP_COUNT} -ne 1 ]]; then
    echo 'The directory file under the directory is not one, please check, and will exit.'
    ls -lrt *.jar
    exit 1
fi

if [ ! -d ./logs ];then
    mkdir ./logs
fi

PROCESS_NUM=$(jps -lv | grep ${APP_NAME} | grep -v grep | grep ${APP_ENV} | awk '{print $1}')
if [[ ${PROCESS_NUM} -gt 0 ]];then
    echo -e "${APP_NAME} is already running ..."
    echo -e "Stopping the ${APP_NAME} ..."
    jps -lv | grep ${APP_NAME} | grep -v grep | grep ${APP_ENV} | awk '{print $1}' | xargs kill -9
    sleep 10
    echo -e "${APP_NAME} is stoped ..."
fi

echo -e "Starting the ${APP_NAME} ..."

if [[ -f /data/skywalking-agent/skywalking-agent.jar ]]; then
   JAVA_AGENT=" -javaagent:/data/skywalking-agent/skywalking-agent.jar -DSW_AGENT_NAME=${APP_NAME%.*} -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=10.8.202.153:11800 "
fi

JAVA_MEM_OPTS=" -server -Xms2g -Xmx2g -Xmn1g  -XX:+UseParallelGC "

JAVA_JAR_OPTS=" -jar -Dspring.profile=${APP_ENV} -Dapollo.meta=http://10.8.202.153:8097 -Ddiscovery.enabled=true"

nohup java ${JAVA_MEM_OPTS} ${JAVA_AGENT}  ${JAVA_JAR_OPTS}  ./${APP_NAME} >/dev/null 2>&1 &

echo "${APP_NAME} start sucess!"