module "rulesengine01" {
  source         = "infrastructure"
  name           = "rulesengine01"
  resource_group = "${var.resource_group}"
  subscription_id   = "${var.subscription_id}"
}
