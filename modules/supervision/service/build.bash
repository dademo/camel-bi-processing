#!/bin/bash
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.
#

set -e

DOCKER_EXEC=/usr/bin/podman
DOCKER_TAGS=(
  "dademo.fr/supervision/service:latest"
  "dademo.fr/supervision/service:0"
  "dademo.fr/supervision/service:0.0"
  "dademo.fr/supervision/service:0.0.1"
)

SCRIPT_LOCATION="$(realpath "$(dirname "${BASH_SOURCE[0]}")")"
GRADLEW_PATH="${SCRIPT_LOCATION}/../../../gradlew"
GRADLE_TASK="modules:supervision:service:bootJar"
TARGET_DIR="build/target"

# Building app
pushd "$(dirname "${GRADLEW_PATH}")"
bash "./gradlew" "${GRADLE_TASK}"
popd

# Preparing build
[ -d "${SCRIPT_LOCATION}/${TARGET_DIR}" ] && rm -rf "${SCRIPT_LOCATION:?}/${TARGET_DIR}"
mkdir -pv "${SCRIPT_LOCATION}/${TARGET_DIR}/dependency"
cp -v "${SCRIPT_LOCATION}/Dockerfile" "${TARGET_DIR}/"
find "${SCRIPT_LOCATION}/build/libs" -not -name '*-plain.jar' -name '*.jar' \
  -exec cp -v {} "${TARGET_DIR}/dependency" \;

# Building image
##_ ARGS
_TAGS=""
for TAG in "${DOCKER_TAGS[@]}"
do
  _TAGS="${_TAGS} -t ${TAG}"
done
##_ CMD
"${DOCKER_EXEC}" build ${_TAGS} --build-arg "JAR_FILE=${TARGET_DIR}/dependency"/*.jar .