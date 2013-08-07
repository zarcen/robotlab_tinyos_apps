OscilloscopeAcc:
-加速度計
-註：即讀取ADC0~2，但特別注意要附加二段程式於OscilloscopeC和OscilloscopeAppC，使ADC3成為Vcc供電。


OscilloscopeADC:
-註：預設讀取ADC0，可自行修改OscilloscopeAppC讀取其他ADC port。


OscilloscopeADCALL:
-預設讀取ADC全部port，除了二顆光度sensor的ADC4~5
-註：即只讀取ADC0~3，ADC6~7。


OscilloscopeTemperature:
-溫度計(Taroko內建才行)
-註：溫溼度非讀取ADC port(請查TinyOS的SensirionSht11C)


OscilloscopeHumid:
-溼度計(Taroko內建才行)
-註：溫溼度非讀取ADC port(請查TinyOS的SensirionSht11C)


OscilloscopeLight:
-光度計(Taroko內建才行)
-註1：預設讀取ADC4可見光，另一顆不用，需要用時再改為ADC5。
-註2：程式段時實體化HamamatsuS1087ParC()即是讀取ADC4。


OscilloscopePressure:
-壓力條x3
-註1：預設讀取ADCALL~請取前3個channel(即ADC0~2)。
-註2：少於3條或多於3條亦可使用，請自行於gateway設定filter讀取channel data。
-註3：只有一條壓力sensor時，可改為使用OscilloscopeADC。


OscilloscopeSound:
-聲音sensor
-註：預設讀取ADC0，可用OscilloscopeADC代替。


OscilloscopeTHL_ADCALL:
-溫、溼、光、ADC0~6
-註：ADC7無讀取，為了符合標準TinyOS channel。


Edited by 宗翰
    E-mail: justwinding@gmail.com
    MSN:    justwinding@msn.com

Edited by 韋辰
    E-mail: zarcen@gmail.com
