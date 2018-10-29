#!/usr/bin/env bash

domain=$1
path=$2
ip=$3
consul=$4

echo "domain = $domain"
echo "path = $path"
echo "ip = $ip"
echo "consul = $consul"

tmp_dir=$(mktemp -d)
cp "${path}/consul.json" "$tmp_dir"
sed -i -e "s/serviceId/$domain/g" "$tmp_dir/consul.json"
sed -i -e "s/serviceName/$domain/g" "$tmp_dir/consul.json"
sed -i -e "s/serviceIp/$ip/g" "$tmp_dir/consul.json"

curl -T "$tmp_dir/consul.json" "http://${consul}:8500/v1/agent/service/register"
