variable "location" {
  default = "UK South"
}

variable "resource_group" {}
variable "subscription_id" {}

variable "name" {
  default     = "iaas-vnet-sandbox-rulesengine"
}

variable "virtual_network_name" {
  default     = "iaas-vnet-sandbox"
}

variable "virtual_network_resource_group" {
  default     = "iaas-infra-sandbox"
}

variable "virtual_network_subnet" {
  default     = "snl-rulesengine"
}

variable "username" {
  default     = "snladmin"
}

