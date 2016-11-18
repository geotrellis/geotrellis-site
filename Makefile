# No spaces in STACK_NAME
export STACK_NAME := GT-SITE
export AWS_DEFAULT_REGION := us-east-1

# Docker image of benchmarking service
export TAG := latest
export SERVICE_IMG := quay.io/geotrellis/gtsite-service
export STATIC_IMG := quay.io/geotrellis/gtsite-static


clean:
	rm -rf service/srv/target
	rm -rf ./build
	docker-compose down

build:
	docker-compose build
	touch ./build

start: build
	docker-compose up

stop:
	docker-compose down

publish: build
	docker push ${SERVICE_IMG}:${TAG}
	docker push ${STATIC_IMG}:${TAG}

deploy: build
	terraform apply \
		-state="deployment/${STACK_NAME}.tfstate" \
		-var 'stack_name=${STACK_NAME}' \
		-var 'service_image=${SERVICE_IMG}' \
		-var 'static_image=${STATIC_IMG}' \
		./deployment

destroy:
	terraform destroy -force \
		-state="deployment/${STACK_NAME}.tfstate" \
		-var 'stack_name=${STACK_NAME}' \
		-var 'service_image=NA' \
		-var 'static_image=NA' \
		./deployment

