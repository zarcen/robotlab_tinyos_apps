package pmv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlotLisener implements ActionListener {
	SeriesPlot plot;
	String tablename; // the supervised table
	private static int listenFlag = 0;

	public PlotLisener(String s) {
		tablename = s;
	}

	@Override
		public void actionPerformed(ActionEvent arg0) {
			if (listenFlag == 0) {
				listenFlag++;
				plot = new SeriesPlot(tablename);
				plot.plotAll();
			}
			else if ( listenFlag == 1) {
				listenFlag++;
				plot.dispose();
				plot = new SeriesPlot(tablename);
				plot.plotPMV();
			}
			else {
				listenFlag = 0;
				//this.setVisible(false);
				plot.dispose();
			}
		}
}
