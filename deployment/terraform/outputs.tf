output "service_elb_ip" {
  value = "${aws_elb.gtsite.dns_name}"
}
