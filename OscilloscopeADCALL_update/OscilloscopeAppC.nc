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
    new TimerMilliC(), new Taroko_ADCconfigC() as Sensor, 
    new AMSenderC(AM_OSCILLOSCOPE), new AMReceiverC(AM_OSCILLOSCOPE);

  OscilloscopeC.Boot -> MainC;
  OscilloscopeC.RadioControl -> ActiveMessageC;
  OscilloscopeC.AMSend -> AMSenderC;
  OscilloscopeC.Receive -> AMReceiverC;
  OscilloscopeC.Timer -> TimerMilliC;
  OscilloscopeC.ReadADC0 -> Sensor.ReadADC0;
  OscilloscopeC.ReadADC1 -> Sensor.ReadADC1;
  OscilloscopeC.ReadADC2 -> Sensor.ReadADC2;
  OscilloscopeC.ReadADC3 -> Sensor.ReadADC3;
  OscilloscopeC.ReadADC3 -> Sensor.ReadADC4;
  OscilloscopeC.ReadADC3 -> Sensor.ReadADC5;
  OscilloscopeC.ReadADC6 -> Sensor.ReadADC6;
  OscilloscopeC.ReadADC7 -> Sensor.ReadADC7;
  OscilloscopeC.Leds -> LedsC;

  
}

