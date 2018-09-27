resource "random_string" "passwd" {
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
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${var.virtual_network_resource_group}/providers/Microsoft.Network/virtualNetworks/${var.virtual_network_name}/subnets/${var.virtual_network_subnet}"
    private_ip_address_allocation = "dynamic"
  }

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_virtual_machine" "rulesengine-vm01" {
  name                  = "${var.name}-vm01"
  location              = "${var.location}"
  resource_group_name   = "${azurerm_resource_group.rulesengine-rg.name}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic1.id}"]
  vm_size               = "Standard_E2s_v3"

  storage_os_disk {
    name              = "${var.name}-vm01-storage"
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
    computer_name  = "${var.name}-vm01"
    admin_username = "${var.username}"
    admin_password = "${random_string.passwd.result}"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  delete_os_disk_on_termination = true
  delete_data_disks_on_termination = true

  tags {
    environment = "rulesengine"
  }
}

resource "azurerm_network_security_group" "rulesengine-nsg1" {
  name                = "${var.name}-nsg"
  location            = "${var.location}"
  resource_group_name = "${azurerm_resource_group.rulesengine-rg.name}"
}
