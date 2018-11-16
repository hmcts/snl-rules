locals {
  resource_group      = "${var.product}-${var.component}-${var.env}"
  vnet_name           = "core-infra-vnet-${var.env}"
  vnet_resource_group = "core-infra-${var.env}"
  vm_name             = "${local.resource_group}-vm"
}

resource "random_string" "passwd" {
  length      = 16
  special     = false
  min_lower   = 2
  min_numeric = 2
}

resource "azurerm_resource_group" "rulesengine-rg" {
  name     = "${local.resource_group}"
  location = "${var.location}"

  tags = "${merge(var.common_tags,
    map("lastUpdated", "${timestamp()}")
  )}"
}

resource "azurerm_network_security_group" "rulesengine-nsg" {
  name                = "${local.resource_group}-nsg"
  location            = "${var.location}"
  resource_group_name = "${azurerm_resource_group.rulesengine-rg.name}"

  tags = "${merge(var.common_tags,
    map("lastUpdated", "${timestamp()}")
  )}"
}

resource "azurerm_network_interface" "rulesengine-nic" {
  name                      = "${local.resource_group}-nic"
  location                  = "${var.location}"
  resource_group_name       = "${azurerm_resource_group.rulesengine-rg.name}"
  network_security_group_id = "${azurerm_network_security_group.rulesengine-nsg.id}"

  ip_configuration {
    name                          = "${local.resource_group}-ipconfig"
    subnet_id                     = "/subscriptions/${var.subscription_id}/resourceGroups/${local.vnet_resource_group}/providers/Microsoft.Network/virtualNetworks/${local.vnet_name}/subnets/${var.rules_engine_subnet}"
    private_ip_address_allocation = "Dynamic"
  }

  tags = "${merge(var.common_tags,
    map("lastUpdated", "${timestamp()}")
  )}"
}

resource "azurerm_virtual_machine" "rulesengine-vm" {
  name                  = "${local.vm_name}"
  location              = "${var.location}"
  resource_group_name   = "${azurerm_resource_group.rulesengine-rg.name}"
  network_interface_ids = ["${azurerm_network_interface.rulesengine-nic.id}"]
  vm_size               = "${var.vm_size}"

  identity = {
    type = "SystemAssigned"
  }

  storage_os_disk {
    name              = "${local.vm_name}-storage"
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
    computer_name  = "${local.vm_name}"
    admin_username = "${var.username}"
    admin_password = "${random_string.passwd.result}"
  }

  os_profile_linux_config {
    disable_password_authentication = false
  }

  delete_os_disk_on_termination = true
  delete_data_disks_on_termination = true

  # Register a DNS entry for the VM
  provisioner "local-exec" {
    command = "bash -e ${path.module}/createDns.sh '${local.vm_name}' '${path.module}' '${azurerm_network_interface.rulesengine-nic.private_ip_address}' '${var.consul}'"
  }

  tags = "${merge(var.common_tags,
    map("lastUpdated", "${timestamp()}")
  )}"
}
