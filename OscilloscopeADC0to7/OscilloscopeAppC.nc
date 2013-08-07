/*
 * Copyright (c) 2006 Intel Corporation
 * All rights reserved.
 *
 * This file is distributed under the terms in the attached INTEL-LICENSE     
 * file. If you do not find these files, copies can be found by writing to
 * Intel Research Berkeley, 2150 Shattuck Avenue, Suite 1300, Berkeley, CA, 
 * 94704.  Attention:  Intel License Inquiry.
 */

/**
 * Oscilloscope demo application. Uses the demo sensor - change the
 * new DemoSensorC() instantiation if you want something else.
 *
 * See README.txt file in this directory for usage instructions.
 *
 * @author David Gay
 */

configuration OscilloscopeAppC { }
implementation
{
  components OscilloscopeC, MainC, ActiveMessageC, LedsC,
    new TimerMilliC(), new ADC0C() as Sensor0, new ADC1C() as Sensor1,
    new ADC2C() as Sensor2, new ADC3C() as Sensor3,
    new ADC4C() as Sensor4, new ADC5C() as Sensor5,
    new ADC6C() as Sensor6, new ADC7C() as Sensor7, 
    new AMSenderC(AM_OSCILLOSCOPE), new AMReceiverC(AM_OSCILLOSCOPE);

  OscilloscopeC.Boot -> MainC;
  OscilloscopeC.RadioControl -> ActiveMessageC;
  OscilloscopeC.AMSend -> AMSenderC;
  OscilloscopeC.Receive -> AMReceiverC;
  OscilloscopeC.Timer -> TimerMilliC;
  OscilloscopeC.ReadADC0 -> Sensor0;
  OscilloscopeC.ReadADC1 -> Sensor1;
  OscilloscopeC.ReadADC2 -> Sensor2;
  OscilloscopeC.ReadADC3 -> Sensor3;
  OscilloscopeC.ReadADC4 -> Sensor4;
  OscilloscopeC.ReadADC5 -> Sensor5;
  OscilloscopeC.ReadADC6 -> Sensor6;
  OscilloscopeC.ReadADC7 -> Sensor7;
  OscilloscopeC.Leds -> LedsC;

  
}

