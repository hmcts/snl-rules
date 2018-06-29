module "rulesengine01" {
  source         = "infrastructure"
  name           = "rulesengine01"
  resource_group = "${var.resource_group}"
  subscription   = "${var.subscription}"
}

module "rulesengine02" {
  source         = "infrastructure"
  name           = "rulesengine02"
  resource_group = "${var.resource_group}"
  subscription   = "${var.subscription}"
}

module "rulesengine03" {
  source         = "infrastructure"
  name           = "rulesengine03"
  resource_group = "${var.resource_group}"
  subscription   = "${var.subscription}"
}
