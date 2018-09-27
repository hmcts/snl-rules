
output "password" {
  value = "${random_string.passwd.result}"
}
