#!/bin/bash

cd docker
echo "Building APP_VERSION=`git describe --tags` ..."
docker build --build-arg "APP_VERSION=`git describe --tags`" -t="stepniewski.tech/requests-logger:`git describe --tags`" .

echo "Saving docker image to tar.gz file..."
docker save stepniewski.tech/logger:`git describe --tags` | gzip -c > target/logger-`git describe --tags`.tar.gz

echo "Image ready. To copy it to earth paste cmd:"
echo -e "   \e[92m\e[1m scp docker/target/requests-logger-`git describe --tags`.tar.gz wheely@earth.stepniewski.tech:~ \\e[033;0m"
