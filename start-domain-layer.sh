#!/bin/bash
# ドメイン層（REST API + gRPCクライアント）を起動するスクリプト

cd "$(dirname "$0")"
./gradlew :domain-layer:bootRun

