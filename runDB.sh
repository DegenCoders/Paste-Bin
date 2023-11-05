#!/bin/bash
set -e

docker-compose -f $PWD/docker-compose.yml down
docker-compose -f $PWD/docker-compose.yml up -d

# Wait for Cassandra to start
until docker-compose -f $PWD/docker-compose.yml exec cassandra cqlsh -e 'show version'; do
  echo "Cassandra is not yet available, retrying in 10 seconds..."
  sleep 10
done

# Once Cassandra is running, execute initialization script
docker-compose -f $PWD/docker-compose.yml exec cassandra cqlsh -f /scripts/init.cql

echo "Initialization completed."
