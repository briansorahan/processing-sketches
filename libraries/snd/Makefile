.PHONY: all backup clean

include project.conf

nullstring :=
space := $(nullstring) #

JAVADOCS_DIR = reference
LIBRARY_DIR = library
SOURCE_DIR = src

# detect existing library packages
LIBRARY_PACKAGES = $(shell find $(LIBRARY_DIR)/* -maxdepth 0 -type d)
# detect existing source packages
SOURCE_PACKAGES = $(shell find $(SOURCE_DIR)/* -maxdepth 0 -type d)
# strip the leading directory name
SOURCE_PACKAGES_STRIPPED = $(subst $(SOURCE_DIR)/,$(nullstring),$(SOURCE_PACKAGES))
# source files
SOURCE_FILES = $(shell find $(SOURCE_DIR) -type f -name '*.java')
# compilation targets
CLASS_FILES = $(patsubst %.java,%.class,$(SOURCE_FILES))
# name of output jar file
JAR = $(LIBRARY_DIR)/$(PROJECT_NAME).jar
JARARGS = cf $(JAR) -C $(LIBRARY_DIR) $(SOURCE_PACKAGES_STRIPPED)
# javac flags
JFLAGS = -extdirs $(PROCESSING_CORE) -d $(LIBRARY_DIR)

all: backup $(JAR)

$(JAR): $(CLASS_FILES)
	jar $(JARARGS)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

backup:
	-rm -rf $(PWD).bak
	-cp -r $(PWD) $(PWD).bak

clean:
	-rm -rf $(LIBRARY_PACKAGES)
	-rm -rf $(JAVADOCS_DIR)/*
	-rm -rf $(JAR)

foo:
	@echo $(SOURCE_FILES)
	@echo $(SOURCE_PACKAGES)
	@echo $(CLASS_FILES)
