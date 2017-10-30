/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;


import java.awt.Color;
import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.*;

/**
 * The <code>ChartPanel</code> class provides facilities for the charting of the simulation of the bookings of an airline.
 * 
 * @author Ryan
 * @version 1.0
 */
public class ChartPanel {

	/**
	 * Chart Information
	 */
    private static final int COUNT = Constants.DURATION;
    private JFreeChart chart;
    
    /**
	 * Constructor to create charting for Progress Chart
	 * 
	 * @param title <code>String</code> Chart Title
	 * @param countsFirst <code>int[]</code> number of passengers in the First Class 
	 * @param countsBusiness <code>int[]</code> number of passengers in the Business Class 
	 * @param countsPremium <code>int[]</code> number of passengers in the Premium Economy
	 * @param countsEconomy <code>int[]</code> number of passengers in the Economy Class
	 * @param countsTotal <code>int[]</code> total number of passengers
	 * @param countsAvailable <code>int[]</code> total number of seats available
	 */
    public ChartPanel(final String title, int[] countsFirst, int[] countsBusiness, int[] countsPremium, int[] countsEconomy, int[] countsTotal, int[] countsAvailable) {
    	// Create Chart DataSet
		final XYDataset dataset = createDataset1(countsFirst, countsBusiness, countsPremium, countsEconomy, countsTotal, countsAvailable);
        
		// Generate Chart
        chart = createChart(dataset, title);
        
        // Alter color of plotted series
        XYPlot plot = chart.getXYPlot();
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.BLACK); 
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(1, Color.BLUE);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(2, Color.CYAN);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(3, Color.GRAY);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(4, Color.GREEN);
        plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(5, Color.RED);
    }
    
    /**
	 * Constructor to create charting for Summary Chart
	 * 
	 * @param title <code>String</code> Chart Title
	 * @param countsInQueue <code>int[]</code> number of passengers in queue
	 * @param countsRefused <code>int[]</code> number of passengers in refused  
	 */
	public ChartPanel(final String title, int[] countsInQueue, int[] countsRefused) {
		// Create Chart DataSet
		final XYDataset dataset = createDataset2(countsInQueue, countsRefused);

		// Generate Chart
		chart = createChart(dataset, title);

		// Alter color of plotted series
		XYPlot plot = chart.getXYPlot();
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.BLACK);
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(1, Color.RED);
    }
	
	/**
	 * Method to return created Chart to the GUISimulator
	 * 
	 * @return chart <code>org.jfree.chart.ChartPanel</code>
	 */
	public org.jfree.chart.ChartPanel getChart(){
		return new org.jfree.chart.ChartPanel(chart);
	}

	/**
	 * Helper Method to create JFreeChart DataSet for Progress chart
	 * 
	 * @param countsFirst <code>int[]</code> number of passengers in the First Class 
	 * @param countsBusiness <code>int[]</code> number of passengers in the Business Class 
	 * @param countsPremium <code>int[]</code> number of passengers in the Premium Economy
	 * @param countsEconomy <code>int[]</code> number of passengers in the Economy Class
	 * @param countsTotal <code>int[]</code> total number of passengers
	 * @param countsAvailable <code>int[]</code> total number of seats available
	 * @return dataset <code>XYSeriesCollection</code> new dataset
	 */
    private XYDataset createDataset1(int[] countsFirst, int[] countsBusiness, int[] countsPremium, int[] countsEconomy, int[] countsTotal, int[] countsAvailable) {
        //Create dataset and series
    	XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries first = new XYSeries("First");
        XYSeries business = new XYSeries("Business");
        XYSeries premium = new XYSeries("Premium");
        XYSeries economy = new XYSeries("Economy");
        XYSeries total = new XYSeries("Total");
        XYSeries seatAvailable = new XYSeries("Seats available");
     
        //Place passenger and seat counts into series
        for(int i=Constants.FIRST_FLIGHT; i<=COUNT;i++){
        	first.add(i,countsFirst[i]);
        	business.add(i,countsBusiness[i]);
        	premium.add(i,countsPremium[i]);
        	economy.add(i,countsEconomy[i]);
        	total.add(i,countsTotal[i]);
        	seatAvailable.add(i,countsAvailable[i]);
        }

        //Add series into dataset
        dataset.addSeries(first);
        dataset.addSeries(business);
        dataset.addSeries(premium);
        dataset.addSeries(economy);
        dataset.addSeries(total);
        dataset.addSeries(seatAvailable);
     
        return dataset;
    }
    
    /**
	 * Helper Method to create JFreeChart DataSet for Summary chart
	 * 
	 * @param countsInQueue <code>int[]</code> number of passengers in queue
	 * @param countsRefused <code>int[]</code> number of passengers in refused  
	 * @return dataset <code>XYSeriesCollection</code> new dataset
	 */
	private XYDataset createDataset2(int[] countsInQueue, int[] countsRefused) {
		// Create dataset and series
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries queue = new XYSeries("Queue Size");
		XYSeries refused = new XYSeries("Passengers Refused");

		// Place passenger and seat counts into series
		for (int i = 0; i <= COUNT; i++) {
			queue.add(i, countsInQueue[i]);
			refused.add(i, countsRefused[i]);
		}

		// Add series into dataset
		dataset.addSeries(queue);
		dataset.addSeries(refused);

		return dataset;
	}
    
    /**
	 * Helper Method to create JFreeChart
	 * 
	 * @param dataset <code>XYDataset</code> Chart dataset
	 * @param title <code>int[]</code> Chart title
	 * @return result <code>JFreeChart</code> new JFreeChart
	 */
    private JFreeChart createChart(final XYDataset dataset, String title) {
        final JFreeChart result = ChartFactory.createXYLineChart(title, "Time", "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
}
