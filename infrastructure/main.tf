locals {
  app_full_name = "${var.product}-${var.component}"

  // Specifies the type of environment. var.env is replaced by pipline
  // to i.e. pr-102-snl so then we need just aat used here
  envInUse = "${(var.env == "preview" || var.env == "spreview") ? "aat" : var.env}"

  // Shared Resources
  vaultName = "${var.raw_product}-${local.envInUse}"
  sharedResourceGroup = "${var.raw_product}-shared-${local.envInUse}"
}
module "snl-rules" {
  source               = "git@github.com:hmcts/moj-module-webapp"
  product              = "${var.product}-${var.component}"
  location             = "${var.location}"
  env                  = "${var.env}"
  ilbIp                = "${var.ilbIp}"
  is_frontend          = false
  subscription         = "${var.subscription}"
  additional_host_name = "${var.external_host_name}"
  capacity             = "1"
  appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
  common_tags          = "${var.common_tags}"

  app_settings = {
    # REDIS_HOST                   = "${module.redis-cache.host_name}"
    # REDIS_PORT                   = "${module.redis-cache.redis_port}"
    # REDIS_PASSWORD               = "${module.redis-cache.access_key}"
    # RECIPE_BACKEND_URL = "http://snl-recipe-backend-${var.env}.service.${data.terraform_remote_state.core_apps_compute.ase_name[0]}.internal"

    SNL_S2S_JWT_SECRET = "${data.azurerm_key_vault_secret.s2s_jwt_secret.value}"
  }
}

#region Shared Key Vault
data "azurerm_key_vault" "snl-shared-vault" {
  name = "${local.vaultName}"
  resource_group_name = "${local.sharedResourceGroup}"
}

data "azurerm_key_vault_secret" "s2s_jwt_secret" {
  name      = "s2s-jwt-secret"
  vault_uri = "${data.azurerm_key_vault.snl-shared-vault.vault_uri}"
}

#endregion
