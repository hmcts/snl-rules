module "rulesengines" {
  source = "../infrastructure"
  name = "rulesengine"
  resource_group = "${var.resource_group}"
  count = 1
}