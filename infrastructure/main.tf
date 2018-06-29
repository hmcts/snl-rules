
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
  name                      = "${var.name}-nic${count.index}"
  location                  = "${var.location}"
  resource_group_name       = "${var.resource_group}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "/subscriptions/bf308a5c-0624-4334-8ff8-8dca9fd43783/resourceGroups/core-infra-snl/providers/Microsoft.Network/virtualNetworks/core-infra-vnet-snl/subnets/core-infra-subnet-3-snl"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm1" {
  name                  = "${var.name}${count.index}"
  location              = "${var.location}"
  resource_group_name   = "${var.resource_group}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic1.id}"]
  vm_size               = "Standard_DS1_v2"

  storage_os_disk {
    name              = "${var.name}${count.index}-storage"
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
    computer_name  = "${var.name}${count.index}"
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
  name                = "${var.name}${count.index}-nsg"
  location            = "${var.location}"
  resource_group_name = "core-infra-snl"
}