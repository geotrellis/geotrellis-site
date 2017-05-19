data "terraform_remote_state" "gtsite" {
    backend = "s3"
    config {
        bucket = "aws-state"
        key = "geotrellis-site/GT-SITE.tfstate"
        region = "us-east-1"
    }
}
