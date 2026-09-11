#!/usr/bin/env sh

set -eu

script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

exec "$script_dir/mvnw" -f "$script_dir/pom.xml" clean package "$@"
