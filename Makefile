src=src/
build=build/

all:
	@echo "Please use make commands only"

compile:
	javac $(src)classes/*.java -d $(build)
	javac $(src)Main.java -d $(build) -cp $(build)

execute:
	java -cp $(build) Main