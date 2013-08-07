#include "Msp430Adc12.h"

generic configuration Taroko_ADCconfigC() {
	provides {
					
	interface Read<uint16_t> as ReadADC0;
	interface Read<uint16_t> as ReadADC1;
	interface Read<uint16_t> as ReadADC2;
	interface Read<uint16_t> as ReadADC3;
	interface Read<uint16_t> as ReadADC4;
	interface Read<uint16_t> as ReadADC5;
	interface Read<uint16_t> as ReadADC6;
	interface Read<uint16_t> as ReadADC7;
	interface ReadStream<uint16_t> as ReadStreamADC0;
	interface ReadStream<uint16_t> as ReadStreamADC1;
	interface ReadStream<uint16_t> as ReadStreamADC2;
	interface ReadStream<uint16_t> as ReadStreamADC3;
	interface ReadStream<uint16_t> as ReadStreamADC4;
	interface ReadStream<uint16_t> as ReadStreamADC5;
	interface ReadStream<uint16_t> as ReadStreamADC6;
	interface ReadStream<uint16_t> as ReadStreamADC7;
	interface Resource as ResourceADC0;
	interface Resource as ResourceADC1;
	interface Resource as ResourceADC2;
	interface Resource as ResourceADC3;
	interface Resource as ResourceADC4;
	interface Resource as ResourceADC5;
	interface Resource as ResourceADC6;
	interface Resource as ResourceADC7;
	interface ReadNow<uint16_t> as ReadNowADC0;
	interface ReadNow<uint16_t> as ReadNowADC1;
	interface ReadNow<uint16_t> as ReadNowADC2;
	interface ReadNow<uint16_t> as ReadNowADC3;
	interface ReadNow<uint16_t> as ReadNowADC4;
	interface ReadNow<uint16_t> as ReadNowADC5;
	interface ReadNow<uint16_t> as ReadNowADC6;
	interface ReadNow<uint16_t> as ReadNowADC7;

  
	}
}
implementation {

	components new AdcReadClientC() as AdcRC0;
	components new AdcReadClientC() as AdcRC1;
	components new AdcReadClientC() as AdcRC2;
	components new AdcReadClientC() as AdcRC3;
	components new AdcReadClientC() as AdcRC4;
	components new AdcReadClientC() as AdcRC5;
	components new AdcReadClientC() as AdcRC6;
	components new AdcReadClientC() as AdcRC7;	
  
	ReadADC0 = AdcRC0;
	ReadADC1 = AdcRC1;
	ReadADC2 = AdcRC2;
	ReadADC3 = AdcRC3;
	ReadADC4 = AdcRC4;
	ReadADC5 = AdcRC5;
	ReadADC6 = AdcRC6;
	ReadADC7 = AdcRC7;

	components new AdcReadStreamClientC() as AdcRSC0;
	components new AdcReadStreamClientC() as AdcRSC1;
	components new AdcReadStreamClientC() as AdcRSC2;
	components new AdcReadStreamClientC() as AdcRSC3;
	components new AdcReadStreamClientC() as AdcRSC4;
	components new AdcReadStreamClientC() as AdcRSC5;
	components new AdcReadStreamClientC() as AdcRSC6;
	components new AdcReadStreamClientC() as AdcRSC7;

	ReadStreamADC0 = AdcRSC0;
	ReadStreamADC1 = AdcRSC1;
	ReadStreamADC2 = AdcRSC2;
	ReadStreamADC3 = AdcRSC3;
	ReadStreamADC4 = AdcRSC4;
	ReadStreamADC5 = AdcRSC5;
	ReadStreamADC6 = AdcRSC6;
	ReadStreamADC7 = AdcRSC7;

	components Taroko_ADCconfigP;

	AdcRC0.AdcConfigure -> Taroko_ADCconfigP.ADC0;
	AdcRC1.AdcConfigure -> Taroko_ADCconfigP.ADC1;
	AdcRC2.AdcConfigure -> Taroko_ADCconfigP.ADC2;
	AdcRC3.AdcConfigure -> Taroko_ADCconfigP.ADC3;
	AdcRC4.AdcConfigure -> Taroko_ADCconfigP.ADC4;
	AdcRC5.AdcConfigure -> Taroko_ADCconfigP.ADC5;
	AdcRC6.AdcConfigure -> Taroko_ADCconfigP.ADC6;
	AdcRC7.AdcConfigure -> Taroko_ADCconfigP.ADC7;
	AdcRSC0.AdcConfigure -> Taroko_ADCconfigP.ADC0;
	AdcRSC1.AdcConfigure -> Taroko_ADCconfigP.ADC1;
	AdcRSC2.AdcConfigure -> Taroko_ADCconfigP.ADC2;
	AdcRSC3.AdcConfigure -> Taroko_ADCconfigP.ADC3;
	AdcRSC4.AdcConfigure -> Taroko_ADCconfigP.ADC4;
	AdcRSC5.AdcConfigure -> Taroko_ADCconfigP.ADC5;
	AdcRSC6.AdcConfigure -> Taroko_ADCconfigP.ADC6;
	AdcRSC7.AdcConfigure -> Taroko_ADCconfigP.ADC7;

	components new AdcReadNowClientC() as ARNC0;
	components new AdcReadNowClientC() as ARNC1;
	components new AdcReadNowClientC() as ARNC2;
	components new AdcReadNowClientC() as ARNC3;
	components new AdcReadNowClientC() as ARNC4;
	components new AdcReadNowClientC() as ARNC5;
	components new AdcReadNowClientC() as ARNC6;
	components new AdcReadNowClientC() as ARNC7;

	ResourceADC0 = ARNC0;
	ResourceADC1 = ARNC1;
	ResourceADC2 = ARNC2;
	ResourceADC3 = ARNC3;
	ResourceADC4 = ARNC4;
	ResourceADC5 = ARNC5;
	ResourceADC6 = ARNC6;
	ResourceADC7 = ARNC7;
	ReadNowADC0 = ARNC0;
	ReadNowADC1 = ARNC1;
	ReadNowADC2 = ARNC2;
	ReadNowADC3 = ARNC3;
	ReadNowADC4 = ARNC4;
	ReadNowADC5 = ARNC5;
	ReadNowADC6 = ARNC6;
	ReadNowADC7 = ARNC7;

	ARNC0.AdcConfigure -> Taroko_ADCconfigP.ADC0;
	ARNC1.AdcConfigure -> Taroko_ADCconfigP.ADC1;
	ARNC2.AdcConfigure -> Taroko_ADCconfigP.ADC2;
	ARNC3.AdcConfigure -> Taroko_ADCconfigP.ADC3;
	ARNC4.AdcConfigure -> Taroko_ADCconfigP.ADC4;
	ARNC5.AdcConfigure -> Taroko_ADCconfigP.ADC5;
	ARNC6.AdcConfigure -> Taroko_ADCconfigP.ADC6;
	ARNC7.AdcConfigure -> Taroko_ADCconfigP.ADC7;
}

