src=src/
build=build/

all:
	@echo "Please use make commands only"

compile:
	if [ ! -d "build" ]; then mkdir build; fi
	javac $(src)classes/*.java -d $(build)
	javac $(src)Main.java -d $(build) -cp $(build)

execute:
	java -cp $(build) Main