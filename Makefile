# No spaces in STACK_NAME
export STACK_NAME := GT-SITE
export AWS_DEFAULT_REGION := us-east-1

# Docker image of benchmarking service
export GIT_COMMIT := $(if ${GIT_COMMIT},${GIT_COMMIT},$(shell git rev-parse --short HEAD))

clean:
	rm -rf ./build
	docker-compose down
	sudo rm -rf nginx/_site/
	docker-compose run --rm gtsite-service clean

deploy:
	terraform remote config \
		-backend="s3" \
		-backend-config="region=us-east-1" \
		-backend-config="bucket=aws-state" \
		-backend-config="key=geotrellis-site/GT-SITE.tfstate" \
		-backend-config="encrypt=true"
	terraform apply \
		-var 'stack_name=${STACK_NAME}' \
		-var 'service_image=${SERVICE_IMG}:${GIT_COMMIT}' \
		-var 'static_image=${STATIC_IMG}:${GIT_COMMIT}' \
		./deployment
	terraform remote push

destroy:
	terraform remote config \
		-backend="s3" \
		-backend-config="region=us-east-1" \
		-backend-config="bucket=aws-state" \
		-backend-config="key=geotrellis-site/GT-SITE.tfstate \
		-backend-config="encrypt=true"
	terraform destroy -force \
		-var 'stack_name=${STACK_NAME}' \
		-var 'service_image=NA' \
		-var 'static_image=NA' \
		./deployment
	terraform remote push
