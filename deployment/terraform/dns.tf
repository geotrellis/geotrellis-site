#
# Public DNS resources
#

resource "aws_route53_record" "origin" {
  zone_id = "${data.aws_route53_zone.external.id}"
  name    = "origin.${data.aws_route53_zone.external.name}"
  type    = "A"

  alias {
    name                   = "${lower(module.website_ecs_service.lb_dns_name)}"
    zone_id                = "${module.website_ecs_service.lb_zone_id}"
    evaluate_target_health = true
  }
}

resource "aws_route53_record" "site" {
  zone_id = "${data.aws_route53_zone.external.id}"
  name    = "${data.aws_route53_zone.external.name}"
  type    = "A"

  alias {
    name                   = "${aws_cloudfront_distribution.site.domain_name}"
    zone_id                = "${aws_cloudfront_distribution.site.hosted_zone_id}"
    evaluate_target_health = false
  }
}

resource "aws_route53_record" "site_www_subdomain" {
  zone_id = "${data.aws_route53_zone.external.id}"
  name    = "www.${data.aws_route53_zone.external.name}"
  type    = "A"

  alias {
    name                   = "${aws_cloudfront_distribution.site.domain_name}"
    zone_id                = "${aws_cloudfront_distribution.site.hosted_zone_id}"
    evaluate_target_health = false
  }
}

resource "aws_route53_record" "docs" {
  zone_id = "${data.aws_route53_zone.external.id}"
  name    = "docs.${data.aws_route53_zone.external.name}"
  type    = "A"

  alias {
    name                   = "${aws_cloudfront_distribution.docs.domain_name}"
    zone_id                = "${aws_cloudfront_distribution.docs.hosted_zone_id}"
    evaluate_target_health = false
  }
}
