
MODULE = files

JAVA  =  file_listImpl.java regular_fileImpl.java directoryImpl.java  Serveur.java Client.java Client1.java
IDL   = files.idl

CLASS = $(JAVA:%.java=classes/$(MODULE)/%.class) 


all: subdir idl src


src:	$(CLASS)
idl:	$(IDL:.idl=.jacorb)
	



############################################
## Do not change anything after this line
############################################

classes/$(MODULE)/%.class : %.java
	javac -d classes  $<

####

.SUFFIXES:
.SUFFIXES:      .idl .jacorb

.idl.jacorb:
	idl -d generated  $<
	javac -d classes generated/$(MODULE)/*.java
	touch $*.jacorb 

####

clean::
	rm -rf core *.jacorb *.ref 
	rm -rf classes generated

####

subdir:
	if [ ! -d classes ]; then \
	   mkdir classes;  \
	fi;
	if [ ! -d generated ]; then \
	   mkdir generated;  \
	fi;


