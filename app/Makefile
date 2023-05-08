FORMAT ?= stylish

run-dist:
	./build/install/app/bin/app -f=${FORMAT} $(FILE1) $(FILE2)

.PHONY: build
build:
	./gradlew installDist
	./gradlew checkstyleMain
	./gradlew test

report:
	./gradlew test

installDist:
	./gradlew installDist