module "vnet" {
  source                = "git::git@github.com:hmcts/moj-module-vnet?ref=increase_subnets"
  name                  = "${var.product_name}"
  location              = "${var.location}"
  address_space         = "${cidrsubnet("${var.root_address_space}", 6, "${var.netnum}")}"
  source_range          = "${cidrsubnet("${var.root_address_space}", 6, "${var.netnum}")}"
  env                   = "${replace(var.env, "-packer", "" )}"
  lb_private_ip_address = "${cidrhost(cidrsubnet(cidrsubnet("${var.root_address_space}", 6, "${var.netnum}"),6,1),-2)}"
  subnet_count          = "16"
  subnet_prefix_length  = "6"
}