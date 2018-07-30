module "rulesengine" {
  source         = "infrastructure"
  name           = "rulesengine"
  resource_group = "${var.resource_group}"
  subscription_id   = "${var.subscription_id}"
}
