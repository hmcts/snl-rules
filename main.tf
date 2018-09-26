variable "name" {
  default     = "iaas-vnet-sandbox-rulesengine"
}

variable "username" {
  default     = "admin"
}

resource "random_string" "password" {
  length      = 16
  special     = false
  min_lower   = 2
  min_numeric = 2
}

resource "azurerm_resource_group" "rulesengine-rg" {
  name                      = "${var.resource_group}"
  location                  = "${var.location}"

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_interface" "rulesengine-nic1" {
  name                      = "${var.name}-nic1"
  location                  = "${var.location}"
  resource_group_name       = "${azurerm_resource_group.rulesengine-rg.name}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${azurerm_resource_group.rulesengine-rg.name}/providers/Microsoft.Network/virtualNetworks/iaas-vnet-sandbox/subnets/snl-rulesengine"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm01" {
  name                  = "${var.name}01"
  location              = "${var.location}"
  resource_group_name   = "${azurerm_resource_group.rulesengine-rg.name}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic1.id}"]
  vm_size               = "Standard_E2s_v3"

  storage_os_disk {
    name              = "${var.name}01-storage"
    caching           = "ReadWrite"
    create_option     = "FromImage"
    managed_disk_type = "Standard_LRS"
  }

  storage_image_reference {
    publisher = "OpenLogic"
    offer     = "CentOS"
    sku       = "7.3"
    version   = "latest"
  }

  os_profile {
    computer_name  = "${var.name}01"
    admin_username = "${var.username}"
    admin_password = "${random_string.password.result}"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_security_group" "rulesengine-nsg1" {
  name                = "${var.name}-nsg"
  location            = "${var.location}"
  resource_group_name = "${azurerm_resource_group.rulesengine-rg.name}"
}
