GEN=OscilloscopeMsg.java Constants.java

PMVMODULE=./pmv/*.java

all: oscilloscope.jar

oscilloscope.jar: Oscilloscope.class
	jar cf $@ *.class

OscilloscopeMsg.java: ../Oscilloscope.h
	mig -target=null -java-classname=OscilloscopeMsg java ../Oscilloscope.h oscilloscope -o $@

Constants.java: ../Oscilloscope.h
	ncg -target=null -java-classname=Constants java ../Oscilloscope.h NREADINGS DEFAULT_INTERVAL -o $@

Oscilloscope.class: $(wildcard *.java) $(GEN) $(PMVMODULE)
	javac *.java ./pmv/*.java

clean:
	rm -f *.class $(GEN)

veryclean: clean
	rm oscilloscope.jar

do:
	java net.tinyos.sf.SerialForwarder -comm serial@COM4:telosb&
	./run
