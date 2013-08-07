import net.tinyos.message.*;
import net.tinyos.util.*;
import java.io.*;
import java.sql.*;
import pmv.*;

public class Oscilloscope implements MessageListener
{
	MoteIF mote;
	//\\ added to collect data and save in database
	PMVDB db;
	int sample_counter = 0;	//\\ to record data less frequently

	DataType[] columnType = { DataType.INTEGER, DataType.DOUBLE, DataType.DOUBLE, DataType.DATE, 
				  DataType.TIME, DataType.CHAR, DataType.DOUBLE, DataType.DOUBLE, 
				  DataType.DOUBLE };

	/* Main entry point */
	void run() {
		mote = new MoteIF(PrintStreamMessenger.err);
		mote.registerListener(new OscilloscopeMsg(), this);
	}
	
	public synchronized void messageReceived(int dest_addr, 
			Message msg) {
		java.util.Date utilDate;
		Date date;
		Time time;
		double temp;
		double rh; //humidity
		double rh_true;	// with temp compensate
		double ideal_temp;
		String place = "R310";
		Object[] rowInstance = new Object[8]; // column number - 1(needn't count id)
		rowInstance[4] = place;
		double BIAS = 8; // by experiment: 8 C
		PMVCalculate pmv = new PMVCalculate();
		double[] pmvAns;

		if (msg instanceof OscilloscopeMsg) {
			/* control sample period */
			sample_counter = (sample_counter + 1) % 4;
			OscilloscopeMsg omsg = (OscilloscopeMsg)msg;
			int[] in= omsg.get_readings();
			//data.update(omsg.get_id(), omsg.get_count(), omsg.get_readings());
			/* record a row about every 10 second */
			if(sample_counter == 0 && omsg.get_id() == 1){
				temp = -39.6 + 0.01 * in[0] - BIAS;
				System.out.println("received temperature: " + temp);
				rh = -4 + 0.0405 * in[1] - 2.8e-6 * in[1] * in[1];
				rh_true = (temp - 25) * (0.01 + 0.00008 * in[1]) + rh;
				System.out.println("received RH and RH_true: " + rh + " " + rh_true);
				System.out.println("light raw: " + in[2]);
				utilDate = new java.util.Date();
				date = new Date(utilDate.getTime());
				time = new Time(utilDate.getTime());
				rowInstance[0] = temp;
				rowInstance[1] = rh_true;
				rowInstance[2] = date;
				rowInstance[3] = time;
				pmv.setTa(temp);
				pmv.setTr(temp); // supposed t_a = t_r
				pmv.setRh(rh_true);
				pmvAns = pmv.getPMVandPPD();
				rowInstance[5] = pmvAns[0];
				rowInstance[6] = pmvAns[1];
				rowInstance[7] = pmv.getMostFitTemp();
				db.insertTable(rowInstance, columnType);
			}
		}
	}

	public static void main(String[] args) {
		Oscilloscope me = new Oscilloscope();	
		//PMVDB(int numColumns, String tablename, String fields, String fwithType)
		String columnName = "(id, temperature, humidity, date, time, place, PMV, PPD, ideal_temperature)";
		String[] columnStr = { "id", "temperature", "humidity", "date",
		       		       "time", "place", "PMV", "PPD", 
				       "ideal_temperature" };
		String colWithType = "(id INTEGER identity, temperature DOUBLE, humidity DOUBLE, date DATE,"
	       			     + " time TIME, place CHAR(30), PMV DOUBLE, PPD DOUBLE, ideal_temperature DOUBLE)";
		// TableName : SensorData, PMVData, ForDemo, ForDemo2
		me.db = new PMVDB(9, "ForDemo2", columnName, colWithType);
		me.run();
		
		/* don't use the following line; otherwise lose the whole table*/
		me.db.dropTable();
		/* use at first time create the TABLE */
		me.db.createTable();
		me.db.showByTable(me.db.getSelectAllSQL(), columnStr, me.columnType);
		

	}
}
