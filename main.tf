module "rulesengine01" {
  source         = "infrastructure"
  name           = "rulesengine01"
  resource_group = "${var.resource_group}"
  subscription_id   = "${var.subscription_id}"
}

module "rulesengine02" {
  source         = "infrastructure"
  name           = "rulesengine02"
  resource_group = "${var.resource_group}"
  subscription_id   = "${var.subscription_id}"
}

module "rulesengine03" {
  source         = "infrastructure"
  name           = "rulesengine03"
  resource_group = "${var.resource_group}"
  subscription_id   = "${var.subscription_id}"
}
