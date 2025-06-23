#!/usr/bin/env bash
# Use this script to test if a given TCP host/port are available

set -e

HOST="$1"
PORT="$2"
shift 2
CMD="$@"

echo "Waiting for $HOST:$PORT..."

while ! nc -z "$HOST" "$PORT"; do
  sleep 10
done

echo "$HOST:$PORT is available - executing command"
exec $CMD
