.PHONY: all backup

all: backup
	cd resources && ant

backup:
	-rf -rf $(PWD).bak
	-cp -r $(PWD) $(PWD).bak