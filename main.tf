
locals {
  vaultName              = "${var.product}-rules-engine"
}

resource "random_string" "username" {
  length = 16
  special = false
}

resource "random_string" "password" {
  length = 16
  special = false
  min_lower = 2
  min_numeric = 2
}

resource "azurerm_network_interface" "rulesengine-nic1" {
  name                      = "rulesengine-nic1"
  location                  = "${var.location}"
  resource_group_name       = "core-infra-snl"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "resource/subscriptions/bf308a5c-0624-4334-8ff8-8dca9fd43783/resourceGroups/core-infra-snl/providers/Microsoft.Network/virtualNetworks/core-infra-vnet-snl-rules/subnets/core-infra-subnet-0-snl"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm1" {
  name                  = "${var.env}-rulesengine1"
  location              = "${var.location}"
  resource_group_name   = "core-infra-snl"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic1.id}"]
  vm_size               = "Standard_DS1_v2"

  storage_os_disk {
    name              = "${var.env}-rulesengine1"
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
    computer_name  = "${var.env}-rulesengine1"
    admin_username = "${random_string.username.result}"
    admin_password = "${random_string.password.result}"
//    admin_password = "xohpheetahl2aeXa"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_security_group" "rulesengine-nsg1" {
  name                = "${var.product_name}-${var.env}-rulesengine1-nsg"
  location            = "${var.location}"
  resource_group_name = "core-infra-snl"
}