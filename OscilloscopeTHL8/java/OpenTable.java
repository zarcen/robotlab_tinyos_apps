import java.sql.*;
import pmv.*;

public class OpenTable {
	PMVDB db;
	public static void main(String[] args) {
		OpenTable t = new OpenTable();
		String columnName = "(id, temperature, humidity, date, time, place, PMV, PPD, ideal_temperature)";
		String[] columnStr = { "id", "temperature", "humidity", "date",
		       		       "time", "place", "PMV", "PPD", 
				       "ideal_temperature" };
		String colWithType = "(id INTEGER identity, temperature DOUBLE, humidity DOUBLE, date DATE,"
	       			     + " time TIME, place CHAR(30), PMV DOUBLE, PPD DOUBLE, ideal_temperature DOUBLE)";
		DataType[] columnType = { DataType.INTEGER, DataType.DOUBLE, DataType.DOUBLE, DataType.DATE, 
				  DataType.TIME, DataType.CHAR, DataType.DOUBLE, DataType.DOUBLE, 
				  DataType.DOUBLE };
		t.db = new PMVDB(9, "ForDemo", columnName, colWithType);
		
		/* don't use the following line; otherwise lose the whole table*/
		//db.dropTable();
		/* use at first time create the TABLE */
		t.db.showByTable(t.db.getSelectAllSQL(), columnStr, columnType);
	}
}
