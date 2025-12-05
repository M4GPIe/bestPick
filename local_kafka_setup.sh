#!/usr/bin/env bash

CONTAINER_NAME="kafka-kraft"
IMAGE_NAME="confluentinc/cp-kafka:8.1.1"  # puedes cambiar a :latest si quieres

# Elimina contenedor previo si existe
if docker ps -a --format '{{.Names}}' | grep -Eq "^${CONTAINER_NAME}\$"; then
  echo "Eliminando contenedor existente: ${CONTAINER_NAME}"
  docker rm -f "${CONTAINER_NAME}"
fi

echo "Lanzando Kafka en KRaft (sin ZooKeeper)..."

docker run -d \
  --name="${CONTAINER_NAME}" \
  -h kafka-kraft \
  -p 9092:9092 \
  -p 9101:9101 \
  -e KAFKA_NODE_ID=1 \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP='CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT' \
  -e KAFKA_ADVERTISED_LISTENERS='PLAINTEXT://kafka-kraft:29092,PLAINTEXT_HOST://localhost:9092' \
  -e KAFKA_JMX_PORT=9101 \
  -e KAFKA_JMX_HOSTNAME=localhost \
  -e KAFKA_PROCESS_ROLES='broker,controller' \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  -e KAFKA_CONTROLLER_QUORUM_VOTERS='1@kafka-kraft:29093' \
  -e KAFKA_LISTENERS='PLAINTEXT://kafka-kraft:29092,CONTROLLER://kafka-kraft:29093,PLAINTEXT_HOST://0.0.0.0:9092' \
  -e KAFKA_INTER_BROKER_LISTENER_NAME='PLAINTEXT' \
  -e KAFKA_CONTROLLER_LISTENER_NAMES='CONTROLLER' \
  -e CLUSTER_ID='MkU3OEVBNTcwNTJENDM2Qk' \
  -e KAFKA_AUTO_CREATE_TOPICS_ENABLE='true' \
  "${IMAGE_NAME}"

echo "Kafka (KRaft) levantado en localhost:9092 con auto.create.topics.enable=true"
