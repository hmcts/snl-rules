#!/usr/bin/env bash

domain=$1
uri=$2
ip=$3
consul=$4

echo "domain = $domain"
echo "uri = $uri"
echo "ip = $ip"
echo "consul = $consul"

register_dns () {
  domain=$1
  uri=$2
  ip=$3

  tmp_dir=$(mktemp -d)
  cp "${uri}/consul.json" "$tmp_dir"
  sed -i -e "s/serviceId/$domain/g" "$tmp_dir/consul.json"
  sed -i -e "s/serviceName/$domain/g" "$tmp_dir/consul.json"
  sed -i -e "s/serviceIp/$ip/g" "$tmp_dir/consul.json"

  curl -T "$tmp_dir/consul.json" "http://${consul}:8500/v1/agent/service/register"
}

register_dns $domain $uri ip
