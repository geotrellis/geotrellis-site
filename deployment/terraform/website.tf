#
# ECS Resources
#

# Template for container definition, allows us to inject environment
data "template_file" "ecs_website_task" {
  template = "${file("${path.module}/task-definitions/site.json")}"

  vars {
    service_image = "${var.aws_account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/gtsite-service:${var.image_version}"
    static_image  = "${var.aws_account_id}.dkr.ecr.${var.aws_region}.amazonaws.com/gtsite-nginx:${var.image_version}"
  }
}

# Allows resource sharing among multiple containers
resource "aws_ecs_task_definition" "website" {
  family                = "${var.environment}Website"
  container_definitions = "${data.template_file.ecs_website_task.rendered}"
}

module "website_ecs_service" {
  source = "github.com/azavea/terraform-aws-ecs-web-service?ref=0.1.0"

  name                = "Website"
  vpc_id              = "${data.terraform_remote_state.core.vpc_id}"
  public_subnet_ids   = ["${data.terraform_remote_state.core.public_subnet_ids}"]
  access_log_bucket   = "${data.terraform_remote_state.core.logs_bucket_id}"
  access_log_prefix   = "ALB"
  port                = "8080"
  ssl_certificate_arn = "${var.ssl_certificate_arn}"

  cluster_name                   = "${data.terraform_remote_state.core.container_instance_name}"
  task_definition_id             = "${aws_ecs_task_definition.website.family}:${aws_ecs_task_definition.website.revision}"
  desired_count                  = "${var.website_ecs_desired_count}"
  min_count                      = "${var.website_ecs_min_count}"
  max_count                      = "${var.website_ecs_max_count}"
  deployment_min_healthy_percent = "${var.website_ecs_deployment_min_percent}"
  deployment_max_percent         = "${var.website_ecs_deployment_max_percent}"
  container_name                 = "gtsite-static"
  container_port                 = "8080"

  project     = "Geotrellis Website"
  environment = "${var.environment}"
}
