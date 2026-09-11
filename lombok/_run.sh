#!/usr/bin/env sh

set -eu

script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

if [ "$#" -lt 1 ]; then
    printf '사용법: %s <main-class> [args...]\n' "$0" >&2
    printf '먼저 %s/_build.sh를 실행해 주세요.\n' "$script_dir" >&2
    exit 1
fi

main_class=$1
shift

classes_dir="$script_dir/target/classes"
dependency_dir="$script_dir/target/dependency"

if [ ! -d "$classes_dir" ]; then
    printf '컴파일 결과가 없습니다. 먼저 %s/_build.sh를 실행해 주세요.\n' "$script_dir" >&2
    exit 1
fi

classpath="$classes_dir:$dependency_dir/*"

exec java -cp "$classpath" "$main_class" "$@"
