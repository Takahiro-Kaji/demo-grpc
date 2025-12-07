#!/bin/bash
# データ層（gRPCサーバ）を起動するスクリプト

cd "$(dirname "$0")"
./gradlew :data-layer:bootRun

