clean:
	rm -rf ./build
	docker-compose down
	sudo rm -rf nginx/_site/
	docker-compose run --rm gtsite-service clean
