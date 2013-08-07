package pmv;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;

import javax.swing.*; 
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;

public class SeriesPlot extends JFrame { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Connection con = null; // Database objects

	protected Statement stat = null;
	protected ResultSet rs = null;
	protected PreparedStatement pst = null;
	protected String tablename;

	public SeriesPlot(String tablename){ 
		this.tablename = tablename;	
			//���UDB driver
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

	public void plotAll() {
		//�B�J 1. �إ� Dataset
		XYDataset dataset = setDataset_All();
		//�B�J 2. �إ� TimeSeriesChart
		JFreeChart chart = createChart(dataset);
		//�B�J 3. �N�ҫإߪ� TimeSeriesChart ��b ChartPanel �W,�ó]�w�j�p 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1000, 590));
		//�B�J 4. �N Panel ��b JFrame �� ContentPane �W�B��z�e��,��� JFrame
		getContentPane().add(chartPanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void plotPMV() {
		//�B�J 1. �إ� Dataset
		XYDataset dataset = setDataset_PMV();
		//�B�J 2. �إ� TimeSeriesChart
		JFreeChart chart = createChart(dataset);
		//�B�J 3. �N�ҫإߪ� TimeSeriesChart ��b ChartPanel �W,�ó]�w�j�p 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1000, 590));
		//�B�J 4. �N Panel ��b JFrame �� ContentPane �W�B��z�e��,��� JFrame
		getContentPane().add(chartPanel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

	protected void closeDB() {
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

	private XYDataset setDataset_All() { 
		//�إ߲Ĥ@�ո��
		TimeSeries timeseries1 = new TimeSeries("Temperature");
		//�إ߲ĤG�ո�� 
		TimeSeries timeseries2 = new TimeSeries("Ideal Temperature");
		//�إ߲ĤT�ո��
		TimeSeries timeseries3 = new TimeSeries("PMV");
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("SELECT temperature, date, time, PMV, ideal_temperature FROM " + this.tablename);
			int second;
			int minute;
			int hour;
			int day;
			int month;
			int year;
			double temp;
			double pmv;
			double ideal_temp;
			int i = 0;
			while (rs.next()) {
				i++;
				Calendar c = Calendar.getInstance();
				c.setTime(rs.getTime("time"));
				second = c.get(Calendar.SECOND);
				minute = c.get(Calendar.MINUTE);
				hour = c.get(Calendar.HOUR);
				c.setTime(rs.getDate("date"));
				day = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);
				year = c.get(Calendar.YEAR);
				temp = rs.getDouble("temperature");
				pmv = rs.getDouble("PMV");
				ideal_temp = rs.getDouble("ideal_temperature");
				timeseries1.add(new Second(second, minute, hour, day, month + 1, year), temp);
				timeseries2.add(new Second(second, minute, hour, day, month + 1, year), ideal_temp);
				timeseries3.add(new Second(second, minute, hour, day, month + 1, year), pmv);
			}	
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :" + e.toString());
		} finally {
			closeDB();
		}		
		//�N�o��ո�Ʃ�� TimeSeriesCollection �� 
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries1);
		timeseriescollection.addSeries(timeseries2);
		timeseriescollection.addSeries(timeseries3);
		return timeseriescollection;
	}

	private XYDataset setDataset_PMV() { 
		TimeSeries timeseries1 = new TimeSeries("Hot Bound");
		TimeSeries timeseries2 = new TimeSeries("Cold Bound");
		TimeSeries timeseries3 = new TimeSeries("PMV");
		try {
			stat = con.createStatement();
			rs = stat.executeQuery("SELECT time, date, PMV FROM " + this.tablename);
			int second;
			int minute;
			int hour;
			int day;
			int month;
			int year;
			double pmv;
			int i = 0;
			while (rs.next()) {
				i++;
				Calendar c = Calendar.getInstance();
				c.setTime(rs.getTime("time"));
				second = c.get(Calendar.SECOND);
				minute = c.get(Calendar.MINUTE);
				hour = c.get(Calendar.HOUR);
				c.setTime(rs.getDate("date"));
				day = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);
				year = c.get(Calendar.YEAR);
				pmv = rs.getDouble("PMV");
				timeseries1.add(new Second(second, minute, hour, day, month + 1, year), 3);
				timeseries2.add(new Second(second, minute, hour, day, month + 1, year), -3);
				timeseries3.add(new Second(second, minute, hour, day, month + 1, year), pmv);
			}	
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :" + e.toString());
		} finally {
			closeDB();
		}		
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries1);
		timeseriescollection.addSeries(timeseries2);
		timeseriescollection.addSeries(timeseries3);
		return timeseriescollection;
	}

	private JFreeChart createChart(XYDataset xydataset) { 
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart( 
				"PMV & Temperature",
				"time","value(Celsius / ASHRAE Comfort Scale)",xydataset,
				true, true, false);
		//���T�ХX�C�Ӹ�ƶ� 
		XYPlot plt = jfreechart.getXYPlot();
		org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = plt.getRenderer();
		
		// ��U�ӽu���C��
		xyitemrenderer.setSeriesPaint(2, java.awt.Color.BLACK);
		
		if(xyitemrenderer instanceof StandardXYItemRenderer){ 
			StandardXYItemRenderer standardxyitemrenderer = 
				(StandardXYItemRenderer)xyitemrenderer; 
			standardxyitemrenderer.setPlotImages(true); 
		}
		//�]�w�ɶ��b������榡 
		DateAxis dateaxis = (DateAxis)plt.getDomainAxis();
		dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		return jfreechart;
	}	

}


