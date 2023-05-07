run-dist:
	./build/install/app/bin/app $(FILE1) $(FILE2)

.PHONY: build
build:
	./gradlew installDist
	./gradlew checkstyleMain
	./gradlew test

report:
	./gradlew jacocoTestReport
