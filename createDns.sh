#!/usr/bin/env bash

domain=$1
rg=$2
uri=$3
ip=$4
subscription=$5

echo "-----------------------"

echo "domain = $domain"
echo "rg = $rg"
echo "uri = $uri"
echo "ip = $ip"
echo "subscription = $subscription"

pwd
echo "-----------------------"
#get ip of consul

echo "Setting Azure CLI to run on $subscription subscription account"
$(env AZURE_CONFIG_DIR=/opt/jenkins/.azure-$subscription az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID)
$(env AZURE_CONFIG_DIR=/opt/jenkins/.azure-$subscription az account set --subscription $AZURE_SUBSCRIPTION_ID)

consul=$(env AZURE_CONFIG_DIR=/opt/jenkins/.azure-$subscription az vmss nic list --resource-group $rg --vmss-name consul-server --query "[0].ipConfigurations[0].privateIpAddress")

consul=$(echo "$consul" | sed -e 's/^"//' -e 's/"$//')

echo "consul = $consul"

echo "-----------------------"

tmp_dir=$(mktemp -d)
cp "${uri}/consul.json" "$tmp_dir"
sed -i -e "s/serviceId/$domain/g" "$tmp_dir/consul.json"
sed -i -e "s/serviceName/$domain/g" "$tmp_dir/consul.json"
sed -i -e "s/serviceIp/$ip/g" "$tmp_dir/consul.json"

curl -T "$tmp_dir/consul.json" "http://${consul}:8500/v1/agent/service/register"
