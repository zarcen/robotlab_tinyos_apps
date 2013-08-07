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
		   new TimerMilliC(), new SensirionSht11C() as SensorTandH,
		   new ADC4C() as ADC4, new ADC0C() as ADC0,
		   new ADC0C() as ADC0_1, new ADC0C() as ADC0_2,
		   new ADC0C() as ADC0_3, new ADC0C() as ADC0_4,
		   new ADC0C() as ADC0_5, new ADC0C() as ADC0_6,
		   new AMSenderC(AM_OSCILLOSCOPE), new AMReceiverC(AM_OSCILLOSCOPE);

	OscilloscopeC.Boot -> MainC;
	OscilloscopeC.RadioControl -> ActiveMessageC;
	OscilloscopeC.AMSend -> AMSenderC;
	OscilloscopeC.Receive -> AMReceiverC;
	OscilloscopeC.Timer -> TimerMilliC;
	OscilloscopeC.ReadChannel1 -> SensorTandH.Temperature;
	OscilloscopeC.ReadChannel2 -> SensorTandH.Humidity;
	OscilloscopeC.ReadChannel3 -> ADC4;
	OscilloscopeC.ReadChannel4 -> ADC0;
	OscilloscopeC.ReadChannel5 -> ADC0_1;
	OscilloscopeC.ReadChannel6 -> ADC0_2;
	OscilloscopeC.ReadChannel7 -> ADC0_3;
	OscilloscopeC.ReadChannel8 -> ADC0_4;
	OscilloscopeC.ReadChannel9 -> ADC0_5;
	OscilloscopeC.ReadChannel10 -> ADC0_6;
	OscilloscopeC.Leds -> LedsC;

}

