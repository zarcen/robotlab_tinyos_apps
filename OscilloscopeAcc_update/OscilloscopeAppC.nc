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
  components OscilloscopeC, MainC, ActiveMessageC, LedsC, HplMsp430GeneralIOC,
    new TimerMilliC(), new ADC0C() as AccX, new ADC1C() as AccY, new ADC2C() as AccZ, 
    new AMSenderC(AM_OSCILLOSCOPE), new AMReceiverC(AM_OSCILLOSCOPE);

  OscilloscopeC.Boot -> MainC;
  OscilloscopeC.RadioControl -> ActiveMessageC;
  OscilloscopeC.AMSend -> AMSenderC;
  OscilloscopeC.Receive -> AMReceiverC;
  OscilloscopeC.Timer -> TimerMilliC;
OscilloscopeC.InfoGio -> HplMsp430GeneralIOC.Port63;
  //OscilloscopeC.Read -> Sensor;
  OscilloscopeC.ReadChannel1 -> AccX;
  OscilloscopeC.ReadChannel2 -> AccY;
  OscilloscopeC.ReadChannel3 -> AccZ;
  OscilloscopeC.Leds -> LedsC;

  
}

