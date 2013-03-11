.PHONY: all backup clean

include project.conf

JAVADOCS_DIR = reference
LIBRARY_DIR = library
SOURCE_DIR = src
LIBRARY_PACKAGES = $(shell find $(LIBRARY_DIR)/* -maxdepth 0 -type d)
SOURCE_PACKAGES = $(shell find $(SOURCE_DIR)/* -maxdepth 0 -type d)
SOURCE_FILES = $(shell find $(SOURCE_DIR) -type f -name '*.java')
JAR = $(PROJECT_NAME).jar
JFLAGS = -extdirs $(PROCESSING_CORE) -d $(LIBRARY_DIR)

all: backup $(JAR)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

backup:
	-rm -rf $(PWD).bak
	-cp -r $(PWD) $(PWD).bak

clean:
	-rm -rf $(LIBRARY_PACKAGES)
	-rm -rf $(JAVADOCS_DIR)/*

foo:
	@echo $(SOURCE_FILES)