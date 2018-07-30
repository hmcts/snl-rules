resource "random_string" "username" {
  length  = 16
  special = false
}

resource "random_string" "password" {
  length      = 16
  special     = false
  min_lower   = 2
  min_numeric = 2
}

resource "azurerm_network_interface" "rulesengine-nic1" {
  name                      = "${var.name}-nic1"
  location                  = "${var.location}"
  resource_group_name       = "${var.resource_group}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${var.resource_group}/providers/Microsoft.Network/virtualNetworks/core-infra-vnet-snlperf/subnets/core-infra-subnet-2-snlperf"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_interface" "rulesengine-nic2" {
  name                      = "${var.name}-nic2"
  location                  = "${var.location}"
  resource_group_name       = "${var.resource_group}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${var.resource_group}/providers/Microsoft.Network/virtualNetworks/core-infra-vnet-snlperf/subnets/core-infra-subnet-2-snlperf"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_interface" "rulesengine-nic3" {
  name                      = "${var.name}-nic3"
  location                  = "${var.location}"
  resource_group_name       = "${var.resource_group}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg1.id}"

  ip_configuration {
    name                          = "IPConfiguration"
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${var.resource_group}/providers/Microsoft.Network/virtualNetworks/core-infra-vnet-snlperf/subnets/core-infra-subnet-2-snlperf"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm01" {
  name                  = "${var.name}01"
  location              = "${var.location}"
  resource_group_name   = "${var.resource_group}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic1.id}"]
  vm_size               = "Standard_DS1_v2"

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
    admin_username = "${random_string.username.result}"
    admin_password = "${random_string.password.result}"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm02" {
  name                  = "${var.name}02"
  location              = "${var.location}"
  resource_group_name   = "${var.resource_group}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic2.id}"]
  vm_size               = "Standard_DS1_v2"

  storage_os_disk {
    name              = "${var.name}02-storage"
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
    computer_name  = "${var.name}02"
    admin_username = "${random_string.username.result}"
    admin_password = "${random_string.password.result}"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm03" {
  name                  = "${var.name}03"
  location              = "${var.location}"
  resource_group_name   = "${var.resource_group}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic3.id}"]
  vm_size               = "Standard_DS1_v2"

  storage_os_disk {
    name              = "${var.name}03-storage"
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
    computer_name  = "${var.name}03"
    admin_username = "${random_string.username.result}"
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
  resource_group_name = "core-infra-snlperf"
}
