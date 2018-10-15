variable "location" {
  default = "UK South"
}

variable "product" {
  type        = "string"
  default     = "snl"
  description = "Product name"
}

variable "component" {
  type        = "string"
  default     = "rules"
  description = "Component name"
}

variable "env" {
  type        = "string"
  description = "(Required) The environment in which to deploy the application infrastructure."
}

variable "subscription_id" {}

variable "vnet_subnet" {
  type        = "string"
  default     = "snl-rules-engine"
  description = "Name of the subnet in which to deploy the VM."
}

variable "username" {
  default     = "snladmin"
  description = "Name of the admin user for the VM."
}

variable "vm_size" {
  default = "Standard_E2s_v3"
  description = "Size of the VM to be created."
}
