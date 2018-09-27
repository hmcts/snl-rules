
output "password" {
  sensitive     = true
  value         = "${random_string.passwd.result}"
}
