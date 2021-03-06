output "frontend_deployment_endpoint" {
  value = "${module.snl-rules.gitendpoint}"
}

output "sharedResourceGroup" {
  value = "${local.sharedResourceGroup}"
}

output "shared_vault_uri" {
  value = "${data.azurerm_key_vault.snl-shared-vault.vault_uri}"
}

output "vaultUri" {
  value = "${data.azurerm_key_vault.snl-shared-vault.vault_uri}"
}

output "vaultName" {
  value = "${local.vaultName}"
}
