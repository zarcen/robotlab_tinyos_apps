package pmv;

//import org.hsqldb.jdbcDriver;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class PMVDB implements ActionListener{
	private Connection con = null; // Database objects

	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	private int numColumns;
	private String fields;
	private String tablaname;
	private String fieldsWithType;
	private String constraints;

	private String dropdbSQL;	// = "DROP TABLE User1 IF EXISTS ";
	private String createdbSQL;	// = "CREATE TABLE User1 (id INTEGER identity, temperature DOUBLE, humidity DOUBLE)";
	private String insertdbSQL;	// = "insert into User1(id,temperature,humidity) values(?,?,?)";
	private String selectMaxId;	// = "select (nvl(max(id),0)+1) as max_id FROM User1";
	private String selectSQL;	// = "select * from User1 ";
	private String deleteSQL;	// = "delete from User1 where humidity > ";

	private String refreshBufferArg1;
	private String[] refreshBufferArg2;
	private DataType[] refreshBufferArg3;
	JFrame frame;
	PlotLisener plot;
	public PMVDB() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:database",
					"SA", "");

		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}
	}

	public PMVDB(int numColumns, String tablename, String fields, String fwithType) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:database",
					"SA", "");
			this.setNumColumns(numColumns);
			this.setTableName(tablename);
			this.setFields(fields);
			this.setFieldsWithType(fwithType);
			this.setDropSQL(this.getTableName());
			this.setCreateSQL(this.getTableName(), this.getFieldsWithType());
			this.setInsertSQL(this.getTableName(), this.getFields(), this.getNumColumns());
			this.setSelectAllSQL(this.getTableName());
			this.setSelectMaxIdSQL(this.getTableName());
			plot = new PlotLisener(this.getTableName());
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}
	}

	public int getNumColumns() {
		return this.numColumns;
	}

	public String getFields() {
		return this.fields;
	}

	public String getTableName() {
		return this.tablaname;
	}

	public String getFieldsWithType() {
		return this.fieldsWithType;
	}

	public String getConstraints() {
		return this.constraints;
	}

	public void setNumColumns(int x) {
		this.numColumns = x;
	}

	public void setFields(String s) {
		this.fields = s;
	}

	public void setTableName(String s) {
		this.tablaname = s;
	}

	public void setFieldsWithType(String s) {
		this.fieldsWithType = s;
	}

	public void setConstraints(String s) {
		this.constraints = s;
	}

	public void setDropSQL(String tablename) {
		// example : "DROP TABLE User1 IF EXISTS "
		this.dropdbSQL = "DROP TABLE " + tablename + " IF EXISTS ";
	}

	public void setCreateSQL(String tablename, String fields) {
		// example : "CREATE TABLE User1 (id INTEGER identity, temperature DOUBLE, humidity DOUBLE)"
		this.createdbSQL = "CREATE TABLE "+ tablename + " " + fields;
	}

	public void setInsertSQL(String tablename, String fields, int numValues) {
		// example : "INSERT INTO User1(id,temperature,humidity) values(?,?,?)"
		String s = "INSERT INTO " + tablename + fields;
		String t = " values(";
		for(int i = 0; i < numValues; i++) {
			if(i == numValues - 1){
				t += "?)";
				break;
			}
			t += "?,";			
		}
		this.insertdbSQL =  s + t;
	}

	public void setSelectMaxIdSQL(String tablename) {
		// example : "SELECT (nvl(max(id),0)+1) AS max_id FROM User1"
		this.selectMaxId = "SELECT (nvl(max(id),0)+1) AS max_id FROM " + tablename;
	}

	public void setSelectAllSQL(String tablename) {
		// example : "SELECT * FROM User1 "
		this.selectSQL = "SELECT * FROM " + tablename;
	}

	public String getSelectAllSQL() {
		return this.selectSQL;
	}

	public void setDeleteSQL(String tablename, String constraints) {
		// example : "DELETE FROM User1 where humidity > "
		this.deleteSQL = "DELETE FROM " + tablename + " " + constraints;
	}

	public static java.sql.Connection getConnection() {
		java.sql.Connection con = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:file:database",
					"SA", "");

		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}
		return con;
	}

	public void createTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(createdbSQL);

			// con.prepareStatement("GRANT select ON User1 TO public;"+
			// "GRANT all ON User1 TO public;"
			// ).executeUpdate();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		} finally {
			close();
		}
	}

	public void insertTable(Object[] values, DataType[] type) {
		try {
			pst = con.prepareStatement(selectMaxId);
			rs = pst.executeQuery();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt("max_id");
			}
			pst = con.prepareStatement(insertdbSQL);
			pst.setInt(1, id);
			for(int i = 1; i < type.length; i++){
				switch(type[i]){
					case INTEGER:
						pst.setInt(i + 1, (Integer)values[i - 1]);
						break;
					case DOUBLE:
						pst.setDouble(i + 1,(Double)values[i - 1]);
						break;
					case CHAR:
						pst.setString(i + 1, (String)values[i - 1]);
						break;
					case DATE:
						pst.setDate(i + 1, (Date)values[i - 1]);
						break;
					case TIME:
						pst.setTime(i + 1, (Time)values[i - 1]);
						break;
				}	
			}
			/*pst.setInt(1, id);
			  pst.setDouble(2, temp);
			  pst.setDouble(3, humid);*/

			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("InsertDB Exception :" + e.toString());
		} finally {
			close();
		}
	}

	public void dropTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(dropdbSQL);
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			close();
		}
	}

	/* print to STDOUT */
	public void selectTable(String[] columnName, DataType[] type) {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectSQL);
			for(int i = 0; i < columnName.length; i++){
				System.out.print(columnName[i]);
				if(i != columnName.length -1)
					System.out.print("\t");
				else
					System.out.println("");
			}
			while (rs.next()) {
				for(int i = 0; i < columnName.length; i++){
					switch(type[i]){
						case INTEGER:
							System.out.print(rs.getInt(columnName[i]));
							break;
						case DOUBLE:
							System.out.print(rs.getDouble(columnName[i]));
							break;
						case CHAR:
							System.out.print(rs.getString(columnName[i]));
							break;
						case DATE:
							System.out.print(rs.getDate(columnName[i]));
							break;
						case TIME:
							System.out.print(rs.getTime(columnName[i]));
							break;
					}
					if(i != columnName.length - 1)
						System.out.print("\t");
					else
						System.out.println("");
				}
			}
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :" + e.toString());
		} finally {
			close();
		}
	}

	public void delete() {
		try{
			stat = con.createStatement();
			stat.executeUpdate(deleteSQL);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteDB Exception :" + e.toString());
		} finally {
			close();
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

	public void shutdown() {

		try {
			stat = con.createStatement();
			stat.execute("SHUTDOWN");
			// if there are no other open connection
		} catch (java.sql.SQLException e) {
			System.out.println(e.toString());
		} finally {
			close();
			if (con != null) {
				try {
					con.close();
				} catch (java.sql.SQLException e) {
					System.out.println(e.toString());
				}
				con = null;
			}

		}
	}
	public void saveToFile(String filename) {

	}

	public void showByTable(String query, String[] columnName, DataType[] type) {
		int numRows = 0;
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(query);

			/* JTable(int numRows, int numColumns) */
			/* DefaultTableModel(Object[] columnName, int rowCount) */
			JTable table = new JTable(1,this.getNumColumns());
			DefaultTableModel tmodel = new DefaultTableModel(columnName, 1);
			table.setModel(tmodel);
			while (rs.next()) {
				numRows++;
				tmodel.setRowCount(numRows);
				table.setModel(tmodel);
				/* setValueAt(Object aValue, int row, int column)  */
				for(int i = 0; i < columnName.length; i++) {
					switch(type[i]){
						case INTEGER:
							table.setValueAt(rs.getInt(columnName[i]),numRows-1,i);
							break;
						case DOUBLE:
							double num = rs.getDouble(columnName[i]);
                					num = Math.round(num*10000.0) / 10000.0;
                					table.setValueAt(num,numRows-1,i);
							break;
						case CHAR:
							table.setValueAt(rs.getString(columnName[i]),numRows-1,i);
							break;
						case DATE:
							table.setValueAt(rs.getDate(columnName[i]),numRows-1,i);
							break;
						case TIME:
							table.setValueAt(rs.getTime(columnName[i]),numRows-1,i);
							break;
					}
				}
			}
			/* Buffer args for refresh */
			refreshBufferArg1 = query;
			refreshBufferArg2 = columnName;
			refreshBufferArg3 = type;    

			JScrollPane pane = new JScrollPane (table);
			JButton button = new JButton("Refresh");
			JButton button1 = new JButton("Graph");
			button.addActionListener(this);
			button1.addActionListener(plot);
			JPanel panel = new JPanel (new GridLayout(0,1));
			panel.setPreferredSize (new Dimension (1200,600));
			button.setPreferredSize (new Dimension (600,50));
			button1.setPreferredSize (new Dimension (600,50));
			panel.setBackground (Color.black);
			panel.add (pane);

			frame = new JFrame ("Result Table");

			frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(panel,BorderLayout.NORTH);
			frame.getContentPane().add(button,BorderLayout.EAST);
			frame.getContentPane().add(button1,BorderLayout.WEST);

			frame.pack();
			frame.setSize(frame.getPreferredSize());
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (SQLException e) {
			System.out.println("showTable Exception :" + e.toString());
		} finally {
			close();
		}
	}


	@Override
		public void actionPerformed(ActionEvent a) {
			// TODO Auto-generated method stub
			/* re-register db driver */
			try {
				Class.forName("org.hsqldb.jdbcDriver");
				con = DriverManager.getConnection("jdbc:hsqldb:file:database",
						"SA", "");

			} catch (ClassNotFoundException e) {
				System.out.println("DriverClassNotFound :" + e.toString());
			} catch (SQLException x) {
				System.out.println("Exception :" + x.toString());
			}

			/* refresh table info */
			frame.dispose();
			this.showByTable(refreshBufferArg1, refreshBufferArg2, refreshBufferArg3);
		}


	public static void collectData() {
		java.util.Date utilD = new java.util.Date();
		Date date = new Date(utilD.getTime());
		Time time = new Time(utilD.getTime());

		String place = "Home";
		double defaultTemp = 30.0;
		double defaultHumid = 65.0;
		Object[] o1 = {defaultTemp, defaultHumid, date, time, place};
		DataType[] t = {DataType.INTEGER, DataType.DOUBLE, DataType.DOUBLE, DataType.DATE, DataType.TIME, DataType.CHAR};
		String[] columnName = {"id", "temperature", "humidity", "date", "time", "place"};
		//String constraint = "where temperature > 40";

		//PMVDB(int numColumns, String tablename, String fields, String fwithType)
		PMVDB test = new PMVDB(5, "SensorData", "(id, temperature, humidity, date, time, place)",
				"(id INTEGER identity, temperature DOUBLE, humidity DOUBLE, date DATE, time TIME, place CHAR(30))");

		test.dropTable();
		/*test.createTable();
		  for(int i = 0 ;i < 50; i++){
		  test.insertTable(o1, t);
		  test.insertTable(o2, t);
		  }

		  test.setDeleteSQL("SensorData", constraint);

		  test.delete();

		  test.selectTable(columnName,t);
		  test.showByTable(test.selectSQL, columnName, t);


		  test.shutdown();
		  */
	}
}


