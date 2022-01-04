#Socket program makefile
# Segomotso Petlele
# 12 April 2021

JAVAC=/usr/bin/javac

.SUFFIXES: .java .class

.java.class:
	$(JAVAC) $<
        
classes: RecieverThread.class ChatBox.class

default: $(CLASSES)

clean:
	rm *.class