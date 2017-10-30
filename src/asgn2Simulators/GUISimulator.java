/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import java.io.IOException;

import asgn2Aircraft.Bookings;

/**
 * The <code>GUISimulator</code> class provides facilities for the simulation of the bookings of an airline.
 * 
 * @author Fong
 * @version 1.0
 */

@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	//GUI Width and height
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	//GUI Components
	private static JFrame runConfigurationException;
	private JTabbedPane tabPane;
	private JPanel pnlNorth, pnlInput, pnlDisplay, pnlWest, pnlEast, pnlInputSimulation, pnlInputFareClass, pnlInputOperation, pnlTextArea, pnlProgressChart, pnlSummaryChart;
	private JButton btnRunSimulator, btnShowChart;
	private JTextField txtfieldRNGSeed, txtfieldDailyMean, txtfieldQueueSize, txtfieldCancellation, txtfieldFirst,
			txtfieldBusiness, txtfieldPremium, txtfieldEconomy;
	private JLabel lblRNGSeed, lblDailyMean, lblQueueSize, lblCancellation, lblFirst, lblBusiness, lblPremium,
			lblEconomy, lblSimulation, lblFareClass, lblOperation;
	private JTextArea areaDisplay;
	
	//CMD Simulation Inputs
	private static String cmdFlag;
	private static int cmdSeed;
	private static int cmdMaxQueueSize;
	private static double cmdMeanDailyBookings;
	private static double cmdSdDailyBookings;
	private static double cmdFirstProb;
	private static double cmdBusinessProb;
	private static double cmdPremiumProb;
	private static double cmdEconomyProb;
	private static double cmdCancelProb;
	private static double cmdTotalProb;

	private int seed;
	private int maxQueueSize;
	private double meanDailyBookings;
	private double sdDailyBookings;
	private double firstProb;
	private double businessProb;
	private double premiumProb;
	private double economyProb;
	private double cancelProb;
	private double totalProb;
	
	//Simulation Counts
    private int[] countsFirst = new int[Constants.DURATION + 1];
    private int[] countsBusiness = new int[Constants.DURATION + 1];
    private int[] countsPremium = new int[Constants.DURATION + 1];
    private int[] countsEconomy = new int[Constants.DURATION + 1];
    private int[] countsTotal = new int[Constants.DURATION + 1];
    private int[] countsAvailable = new int[Constants.DURATION + 1];
    private int[] countsInQueue = new int[Constants.DURATION + 1];
    private int[] countsRefused = new int[Constants.DURATION + 1];
	
	/**
	 * Constructor for GUISimulator 
	 * 
	 * @param title <code>String</code> Application Name
	 * @throws HeadlessException
	 */
	public GUISimulator(String title) throws HeadlessException {
		super(title);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createGUI();
	}

	/**
	 * Method to create the components of the GUI
	 */
	private void createGUI() {
		// SET UP JFRAME
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ADD NEW BORDERLAYOUT
		setLayout(new BorderLayout());

		// CREATE MAIN TABPANE
		tabPane = createTabPane();
				
		// CREATE MAIN PANELS
		pnlNorth = createPanel(Color.LIGHT_GRAY);
		pnlInput = createPanel(Color.LIGHT_GRAY);
		pnlWest = createPanel(Color.LIGHT_GRAY);
		pnlEast = createPanel(Color.LIGHT_GRAY);
		pnlDisplay = createPanel(Color.LIGHT_GRAY);
		pnlTextArea = createPanel(Color.WHITE);
		pnlProgressChart = createPanel(Color.WHITE);
		pnlSummaryChart = createPanel(Color.WHITE);
		
		// CREATE INPUT PANELS
		pnlInputSimulation = createPanel(Color.LIGHT_GRAY);
		pnlInputFareClass = createPanel(Color.LIGHT_GRAY);
		pnlInputOperation = createPanel(Color.LIGHT_GRAY);

		// CREATE BIG LABEL, LABEL, TEXT FIELD AND ADD TO PNLINPUTSIMULATION
		lblSimulation = createBigLabel("Simulation");
		lblRNGSeed = createLabel("RNG Seed");
		lblDailyMean = createLabel("Daily Mean");
		lblQueueSize = createLabel("Queue Size");
		lblCancellation = createLabel("Cancellation");
		txtfieldRNGSeed = createTextField("RNG Seed", Integer.toString(cmdSeed));
		txtfieldDailyMean = createTextField("Daily Mean", Double.toString(cmdMeanDailyBookings));
		txtfieldQueueSize = createTextField("Queue Size", Integer.toString(cmdMaxQueueSize));
		txtfieldCancellation = createTextField("Cancellation", Double.toString(cmdCancelProb));
		
		// CREATE BIG LABEL, LABEL, TEXT FIELD AND ADD TO PNLINPUTFARECLASS
		lblFareClass = createBigLabel("Fare Classes");
		lblFirst = createLabel("First");
		lblBusiness = createLabel("Business");
		lblPremium = createLabel("Premium");
		lblEconomy = createLabel("Economy");
		txtfieldFirst = createTextField("First", Double.toString(cmdFirstProb));
		txtfieldBusiness = createTextField("Business", Double.toString(cmdBusinessProb));
		txtfieldPremium = createTextField("Premium", Double.toString(cmdPremiumProb));
		txtfieldEconomy = createTextField("Economy", Double.toString(cmdEconomyProb));
		
		// CREATE BIG LABEL, BUTTONS AND ADD TO PNLINPUTOPERATION
		lblOperation = createBigLabel("Operation");
		btnRunSimulator = createButton("Run Simulator");
		btnShowChart = createButton("Show Chart");
		btnShowChart.setEnabled(false);
		
		// CREATE TEXT AREA AND ADD TO PNLTEXTAREA
		areaDisplay = createTextArea();
		JScrollPane scroll = new JScrollPane ( areaDisplay );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		pnlTextArea.setLayout(new BorderLayout());
		pnlTextArea.add(scroll, BorderLayout.CENTER);
		
		pnlProgressChart.setLayout(new BorderLayout());
		pnlSummaryChart.setLayout(new BorderLayout());

		// ADD TABPANE TO PNLDISPLAY
		tabPane.addTab("Progress Log", null, pnlTextArea, "Display Progress Log");
		tabPane.addTab("Progress Chart", null, pnlProgressChart, "Display Progress Chart");
		tabPane.addTab("Summary Chart", null, pnlSummaryChart, "Display Summary Chart");
		pnlDisplay.setLayout(new BorderLayout());
		pnlDisplay.add(tabPane);
		
		// BUILD THE INPUT PANEL
		layoutInputPanel();
		
		// BUILD THE SIMULATION PANEL
		layoutSimulationPanel();

		// BUILD THE FARE CLASSES PANEL
		layoutFareClassPanel();

		// BUILD THE OPERATION PANEL
		layoutOperationPanel();

		// ADD PANELS TO BORDERLAYOUT
		this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);
		this.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		this.getContentPane().add(pnlInput, BorderLayout.SOUTH);
		this.getContentPane().add(pnlWest, BorderLayout.WEST);
		this.getContentPane().add(pnlEast, BorderLayout.EAST);
		
		// REPAINT THE JFRAME
		this.repaint();

		// MAKE JFRAME VISIBLE
		this.setVisible(true);
	}
	
	/**
	 * Helper Method to create JPanel
	 * 
	 * @param c <code>Color</code> Panel color
	 * @return panel <code>JPanel</code> new panel
	 */
	private JPanel createPanel(Color c) {
		// Create a JPanel object and store it in a local variable
		JPanel panel = new JPanel();

		// Set the background color to that passed in c
		panel.setBackground(c);

		// Return the JPanel object
		return panel;
	}
	
	/**
	 * Helper Method to create JTabbedPane
	 * 
	 * @return tabbedPane <code>JTabbedPane</code> new tabbedpane
	 */
	private JTabbedPane createTabPane(){
		// Create a JTabbedPane object and store it in a local variable
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Return the JPanel object
		return tabbedPane;	
	}
	
	/**
	 * Helper Method to create JButton
	 * 
	 * @param str <code>String</code> Text in the Button
	 * @return button <code>JButton</code> new button
	 */
	private JButton createButton(String str) {
		// Create a JButton object and store it in a local variable
		// Set the button text to that passed in str
		JButton button = new JButton(str);
		
		// Add the frame as an actionListener
		button.addActionListener(this);
		
		// Return the JButton object
		return button;
	}
	
	/**
	 * Helper Method to create JTextField
	 * 
	 * @param str <code>String</code> Name of JtextField
	 * @param text <code>String</code> Text in the JTextField
	 * @return textField <code>JTextField</code> new textfield
	 */
	private JTextField createTextField(String str, String text){
		// Create a TextField object
		JTextField textField = new JTextField(text);
		textField.setName(str);
		textField.setEditable(true);
		textField.setFont(new Font("Arial", Font.BOLD, 18));
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return textField;
	}
	
	/**
	 * Helper Method to create JLabel
	 * 
	 * @param str <code>String</code> Text in the JLabel
	 * @return label <code>JLabel</code> new label
	 */
	private JLabel createLabel(String str){
		// Create a Label object
		JLabel label = new JLabel(str);
		label.setFocusable(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setBorder(BorderFactory.createEmptyBorder());
		return label;
	}
	
	/**
	 * Helper Method to create JLabel (Big)
	 * 
	 * @param str <code>String</code> Text in the JLabel
	 * @return label <code>JLabel</code> new label
	 */
	private JLabel createBigLabel(String str){
		// Create a Label object
		JLabel label = new JLabel(str);
		label.setFocusable(true);
		label.setAlignmentX(LEFT_ALIGNMENT);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return label;
	}
	
	/**
	 * Helper Method to create JTextArea
	 * 
	 * @return textArea <code>JTextArea</code> new textarea
	 */
	private JTextArea createTextArea() {
		// Create a TextArea object
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.BOLD, 16));
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return textArea;
	}

	/**
	 * Helper Method to build the layout for the Input panel
	 */
	private void layoutInputPanel() {
		// ASSIGN A NEW GRIDBAG LAYOUT TO INPUT PNL
		GridBagLayout layout = new GridBagLayout();
		pnlInput.setLayout(layout);

		// SET GRIDBAG CONSTRAINTS
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 100;
		constraints.weighty = 100;

		// ADD BUTTONS TO PANEL USING CONSTRAINTS
		int paddingTop = 0;
		int paddingLeft = 50;
		int paddingBottom = 0;
		int paddingRight = 0;
		addToPanel(pnlInput, pnlInputSimulation, constraints, 0, 0, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInput, pnlInputFareClass, constraints,  1, 0, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInput, pnlInputOperation, constraints,  2, 0, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
	}
	
	/**
	 * Helper Method to build the layout for the Simulation panel
	 */
	private void layoutSimulationPanel() {
		// ASSIGN A NEW GRIDBAG LAYOUT TO PNLBIN
		GridBagLayout layout = new GridBagLayout();
		pnlInputSimulation.setLayout(layout);

		// SET GRIDBAG CONSTRAINTS
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 100;
		constraints.weighty = 100;

		// ADD COMPONENTS TO PANEL USING CONSTRAINTS
		int paddingTop = 0;
		int paddingLeft = 0;
		int paddingBottom = 0;
		int paddingRight = 20;
		addToPanel(pnlInputSimulation, lblSimulation, constraints,        0, 0, 0, 0, 2, 1, paddingTop, paddingLeft, 10, paddingRight);
		
		addToPanel(pnlInputSimulation, lblRNGSeed, constraints,           0, 1, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, txtfieldRNGSeed, constraints,      1, 1, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, lblDailyMean, constraints,         0, 2, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, txtfieldDailyMean, constraints,    1, 2, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, lblQueueSize, constraints,         0, 3, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, txtfieldQueueSize, constraints,    1, 3, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, lblCancellation, constraints,      0, 4, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputSimulation, txtfieldCancellation, constraints, 1, 4, 100, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
	}
		
	/**
	 * Helper Method to build the layout for the for the FareClasses panel
	 */
	private void layoutFareClassPanel() {
		// ASSIGN A NEW GRIDBAG LAYOUT TO PNLBIN
		GridBagLayout layout = new GridBagLayout();
		pnlInputFareClass.setLayout(layout);

		// SET GRIDBAG CONSTRAINTS
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 100;
		constraints.weighty = 100;

		// ADD COMPONENTS TO PANEL USING CONSTRAINTS
		int paddingTop = 0;
		int paddingLeft = 0;
		int paddingBottom = 0;
		int paddingRight = 20;
		addToPanel(pnlInputFareClass, lblFareClass, constraints,     0, 0, 0, 0, 4, 1, paddingTop, paddingLeft, 10, paddingRight);
		
		addToPanel(pnlInputFareClass, lblFirst, constraints, 	     1, 1, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, txtfieldFirst, constraints,    2, 1, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, lblBusiness, constraints,      1, 2, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, txtfieldBusiness, constraints, 2, 2, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, lblPremium, constraints, 		 1, 3, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, txtfieldPremium, constraints,  2, 3, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, lblEconomy, constraints, 		 1, 4, 0, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputFareClass, txtfieldEconomy, constraints,  2, 4, 100, 0, 1, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
	}
	
	/**
	 * Helper Method to build the layout for the for the Operation panel
	 */
	private void layoutOperationPanel() {
		// ASSIGN A NEW GRIDBAG LAYOUT TO PNLBIN
		GridBagLayout layout = new GridBagLayout();
		pnlInputOperation.setLayout(layout);

		// SET GRIDBAG CONSTRAINTS
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 100;
		constraints.weighty = 100;

		// ADD COMPONENTS TO PANEL USING CONSTRAINTS
		int paddingTop = 0;
		int paddingLeft = 0;
		int paddingBottom = 0;
		int paddingRight = 50;
		addToPanel(pnlInputOperation, lblOperation, constraints,    0, 0, 0, 0, 4, 1, paddingTop, paddingLeft, 10, paddingRight);
		addToPanel(pnlInputOperation, btnRunSimulator, constraints, 1, 1, 0, 0, 2, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
		addToPanel(pnlInputOperation, btnShowChart, constraints,    1, 2, 0, 0, 2, 1, paddingTop, paddingLeft, paddingBottom, paddingRight);
	}

	/**
	 * Helper Method to place the components in the panels
	 * 
	 * @param jp <code>JPanel</code> Panel ID
	 * @param c <code>Component</code> Component to be added into the panel
	 * @param constraints <code>GridBagConstraints</code> Constraints of the component to be added into the panel
	 * @param x <code>int</code> Gridx
	 * @param y <code>int</code> Gridy
	 * @param padx <code>int</code> Ipadx, inner padding x
	 * @param pady <code>int</code> Ipady, inner padding y
	 * @param w <code>int</code> Gridwidth
	 * @param h <code>int</code> Gridheight
	 * @param top <code>int</code> Top padding
	 * @param left <code>int</code> Left padding
	 * @param bottom <code>int</code> Bottom padding
	 * @param right <code>int</code> Right padding
	 */
	private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y,int padx, int pady, int w, int h, int top, int left, int bottom, int right ) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.ipadx = padx;
		constraints.ipady = pady;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		constraints.insets = new Insets(top, left, bottom, right);
		jp.add(c, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Get event source
		Object src = e.getSource();

		if (src == btnRunSimulator) {
			//Run Aircraft Booking Simulator 
			try {
				runSimulation();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//Create the Booking Chart
			ChartPanel demo = new ChartPanel("Aicraft Booking Progress Chart", countsFirst, countsBusiness, countsPremium, countsEconomy, countsTotal, countsAvailable );
			pnlProgressChart.removeAll();
			pnlProgressChart.add(demo.getChart(),BorderLayout.CENTER);
			pnlProgressChart.revalidate();
			btnShowChart.setEnabled(true);
			
		} else if (src == btnShowChart) {
			//Create the Booking Chart
			ChartPanel demo = new ChartPanel("Aicraft Booking Summary Charts", countsInQueue, countsRefused );
			pnlSummaryChart.removeAll();
			pnlSummaryChart.add(demo.getChart(),BorderLayout.CENTER);
			pnlSummaryChart.revalidate();
			btnShowChart.setEnabled(false);
		} 
	}
	
	/**
	 * Helper returning Log Time format for filename
	 * @return filename String yyyyMMdd_HHmmss
	 */
	private String getLogTime() {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		return timeLog;
	}
	
	/**
	 * Helper Method to check if string is able to parse to Double
	 * 
	 * @param input <code>String</code> Input String to be parsed to Double
	 * @return parsable <code>boolean</code> true if parsable, false if not parsable
	 */
	private static boolean isParsableDouble(String input){
	    boolean parsable = true;
	    try{
	    	Double.parseDouble(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	
	/**
	 * Helper Method to check if string is able to parse to Int
	 * 
	 * @param input <code>String</code> Input String to be parsed to Int
	 * @return parsable <code>boolean</code> true if parsable, false if not parsable
	 */
	private static boolean isParsableInt(String input){
	    boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	
	/**
	 * Main Simulation Loop
	 * @throws IOException if the log files or BufferedWriters cannot be created
	 */
	private void runSimulation() throws IOException{
		// Catch Input Exceptions
		try {
			//Check empty input
			String str = " is empty.";
			if (txtfieldRNGSeed.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldRNGSeed.getName() + str );
			}else if(txtfieldDailyMean.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldDailyMean.getName() + str );
			}else if(txtfieldQueueSize.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldQueueSize.getName() + str);
			}else if(txtfieldCancellation.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldCancellation.getName() + str);
			}else if(txtfieldFirst.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldFirst.getName() + str);
			}else if(txtfieldBusiness.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldBusiness.getName() + str);
			}else if(txtfieldPremium.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldPremium.getName() + str);
			}else if(txtfieldEconomy.getText().isEmpty()){
				throw new IllegalArgumentException(txtfieldEconomy.getName() + str);
			}
			
			//Check if input is parsable
			str = " invalid input.";
			if(!isParsableInt(txtfieldRNGSeed.getText())){
				throw new IllegalArgumentException(txtfieldRNGSeed.getName() + str);
			}else if(!isParsableInt(txtfieldQueueSize.getText())){
				throw new IllegalArgumentException(txtfieldQueueSize.getName() + str);
			}else if(!isParsableDouble(txtfieldDailyMean.getText())){
				throw new IllegalArgumentException(txtfieldDailyMean.getName() + str);
			}else if(!isParsableDouble(txtfieldFirst.getText())){
				throw new IllegalArgumentException(txtfieldFirst.getName() + str);
			}else if(!isParsableDouble(txtfieldBusiness.getText())){
				throw new IllegalArgumentException(txtfieldBusiness.getName() + str);
			}else if(!isParsableDouble(txtfieldPremium.getText())){
				throw new IllegalArgumentException(txtfieldPremium.getName() + str);
			}else if(!isParsableDouble(txtfieldEconomy.getText())){
				throw new IllegalArgumentException(txtfieldEconomy.getName() + str);
			}else if(!isParsableDouble(txtfieldCancellation.getText())){
				throw new IllegalArgumentException(txtfieldCancellation.getName() + str);
			}
			
			//Parse string to Int or Double
			seed = Integer.parseInt(txtfieldRNGSeed.getText());
			maxQueueSize = Integer.parseInt(txtfieldQueueSize.getText());
			meanDailyBookings = Double.parseDouble(txtfieldDailyMean.getText());
			sdDailyBookings = 0.33*meanDailyBookings;
			firstProb = Double.parseDouble(txtfieldFirst.getText());
			businessProb = Double.parseDouble(txtfieldBusiness.getText());
			premiumProb = Double.parseDouble(txtfieldPremium.getText());
			economyProb = Double.parseDouble(txtfieldEconomy.getText());
			cancelProb = Double.parseDouble(txtfieldCancellation.getText());	
			totalProb = firstProb + businessProb + premiumProb + economyProb;
			
			//Check Input Probability
			if(totalProb != 1.00){
				str = " invalid probability. The total of First, Business, Premium, and Economy probabilities must be exactly 1.";
				throw new IllegalArgumentException(txtfieldFirst.getName() + str);
			}else if(cancelProb < 0.00 || cancelProb > 1.00){
				str = " invalid probability. Cancel probability must be within 0.00 to 1.00";
				throw new IllegalArgumentException(txtfieldCancellation.getName() + str);
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Booking Simulation: Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		 
		//Create Simulator
		Simulator s = null; 
		Log l = null;
		try {
			s = new Simulator(seed, maxQueueSize, meanDailyBookings, sdDailyBookings, firstProb, businessProb, premiumProb, economyProb, cancelProb);
			l = new Log();
		} catch (SimulationException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Booking Simulation: Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		//Run the simulation 
		try {
			s.createSchedule();

			//Initial Entry to TextArea and Writer
			l.initialEntry(s);
			areaDisplay.setText( getLogTime() + ": Start of Simulation\n");
			areaDisplay.append(s.toString() + "\n");
			String capacities = s.getFlights(Constants.FIRST_FLIGHT).initialState();
			areaDisplay.append(capacities);
			
			//Main simulation loop 
			for (int time=0; time<=Constants.DURATION; time++) {
				s.resetStatus(time); 
				s.rebookCancelledPassengers(time); 
				s.generateAndHandleBookings(time);
				s.processNewCancellations(time);
				if (time >= Constants.FIRST_FLIGHT) {
					s.processUpgrades(time);
					s.processQueue(time);
					s.flyPassengers(time);
					s.updateTotalCounts(time);
					//Flight entries to Writer
					l.logFlightEntries(time, s);
				} else {
					s.processQueue(time);
				}
				
				//Log Entry to TextArea and Writer
				boolean flying = (time >= Constants.FIRST_FLIGHT);
				areaDisplay.append(s.getSummary(time, flying));
				
				if (flying) {
					Flights flights = s.getFlights(time);
					Bookings counts = flights.getCurrentCounts();
					countsFirst[time] = counts.getNumFirst();
					countsBusiness[time] = counts.getNumBusiness();
					countsPremium[time] = counts.getNumPremium();
					countsEconomy[time] = counts.getNumEconomy();
					countsTotal[time] = counts.getTotal();
					countsAvailable[time] = counts.getAvailable();
				} 
				countsInQueue[time]= s.numInQueue();
				countsRefused[time] = s.numRefused();
				
				l.logQREntries(time, s);
				l.logEntry(time,s);
			}
			s.finaliseQueuedAndCancelledPassengers(Constants.DURATION); 
			
			//Finalize to TextArea and Writer
			String time = getLogTime(); 
			areaDisplay.append("\n" + time + ": End of Simulation\n");
			areaDisplay.append(s.finalState());
			
			l.logQREntries(Constants.DURATION, s);
			l.finalise(s);

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, e2.getMessage(), "Booking Simulation: Error", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	/**
	 * Main method to start the GUISimulator
	 * 
	 * @param args Arguments to the simulation 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		final int NUM_ARGS = 10; 

		try {
			switch (args.length) {
				//If Case 3: TEN arguments : A single flag like 每gui or 每nogui + NINE simulator arguments
				case NUM_ARGS: {			
					cmdFlag = args[0];
					//Catch input exceptions
					String str = "Invalid input for ";
					if(!isParsableInt(args[1])){
						throw new IllegalArgumentException(str + "Seed");
					}else if(!isParsableInt(args[2])){
						throw new IllegalArgumentException(str + "Max Queue Size");
					}else if(!isParsableDouble(args[3])){
						throw new IllegalArgumentException(str + "Mean Daily Booking");
					}else if(!isParsableDouble(args[4])){
						throw new IllegalArgumentException(str + "SD Daily Booking");
					}else if(!isParsableDouble(args[5])){
						throw new IllegalArgumentException(str + "First Probability");
					}else if(!isParsableDouble(args[6])){
						throw new IllegalArgumentException(str + "Business Probability");
					}else if(!isParsableDouble(args[7])){
						throw new IllegalArgumentException(str + "Premium Probability");
					}else if(!isParsableDouble(args[8])){
						throw new IllegalArgumentException(str + "Economy Probability");
					}else if(!isParsableDouble(args[9])){
						throw new IllegalArgumentException(str + "Cancellation Probability");
					}
					
					//Parse string to int/double
					cmdSeed = Integer.parseInt(args[1]);
					cmdMaxQueueSize = Integer.parseInt(args[2]);
					cmdMeanDailyBookings = Double.parseDouble(args[3]);
					cmdSdDailyBookings = Double.parseDouble(args[4]);
					cmdFirstProb = Double.parseDouble(args[5]);
					cmdBusinessProb = Double.parseDouble(args[6]);
					cmdPremiumProb = Double.parseDouble(args[7]);
					cmdEconomyProb = Double.parseDouble(args[8]);
					cmdCancelProb = Double.parseDouble(args[9]);
					cmdTotalProb = cmdFirstProb + cmdBusinessProb + cmdPremiumProb + cmdEconomyProb;
					
					//Catch wrong SD
					if(cmdSdDailyBookings != 0.33* cmdMeanDailyBookings){
						throw new IllegalArgumentException("SD Daily Booking should be 0.33 * Mean Daily Booking");
					}
					
					//Check Input Probability
					if(cmdTotalProb != 1.00){
						str = "Invalid probability. The total of First, Business, Premium, and Economy probabilities must be exactly 1.";
						throw new IllegalArgumentException(str);
					}else if(cmdCancelProb < 0.00 || cmdCancelProb > 1.00){
						str = "Invalid probability. Cancel probability must be within 0.00 to 1.00";
						throw new IllegalArgumentException(str);
					}
					
					if(cmdFlag.compareTo("-gui") == 0){
						//Run the gui
						SwingUtilities.invokeLater(new GUISimulator("Aircraft Booking Simulator"));
					}else if(cmdFlag.compareTo("-nogui") == 0){
						//Run the simulation no gui
						Simulator s = new Simulator(cmdSeed, cmdMaxQueueSize, cmdMeanDailyBookings, cmdSdDailyBookings, cmdFirstProb, cmdBusinessProb, cmdPremiumProb, cmdEconomyProb, cmdCancelProb); 
						Log l = new Log();
						
						SimulationRunner sr = new SimulationRunner(s,l);
						try {
							sr.runSimulation();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(runConfigurationException, e.getMessage(), "Run Configuration Booking Simulation: Error", JOptionPane.ERROR_MESSAGE);
							System.exit(-1);
						} 
					}else {
						throw new SimulationException("Flag must be -gui or -nogui.");
					}
					break;
				}
				
				//If Case 1: NO arguments : GUI + Default parameters
				case 0: {
					cmdFlag = "";
					cmdSeed = Constants.DEFAULT_SEED;
					cmdMaxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
					cmdMeanDailyBookings = Constants.DEFAULT_DAILY_BOOKING_MEAN;
					cmdSdDailyBookings = Constants.DEFAULT_DAILY_BOOKING_SD;
					cmdFirstProb = Constants.DEFAULT_FIRST_PROB;
					cmdBusinessProb = Constants.DEFAULT_BUSINESS_PROB;
					cmdPremiumProb = Constants.DEFAULT_PREMIUM_PROB;
					cmdEconomyProb = Constants.DEFAULT_ECONOMY_PROB;
					cmdCancelProb = Constants.DEFAULT_CANCELLATION_PROB;
					SwingUtilities.invokeLater(new GUISimulator("Aircraft Booking Simulator"));	
					break;
				}
				
				//If Case 2: Single string argument: A single flag like 每gui or 每nogui or similar to give the choice of the GUI OR  ORIGINAL App  + Default Parameters
				case 1: {
					cmdFlag = args[0];
					
					if(cmdFlag.compareTo("-gui") == 0){
						//Run the gui
						cmdSeed = Constants.DEFAULT_SEED;
						cmdMaxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
						cmdMeanDailyBookings = Constants.DEFAULT_DAILY_BOOKING_MEAN;
						cmdSdDailyBookings = Constants.DEFAULT_DAILY_BOOKING_SD;
						cmdFirstProb = Constants.DEFAULT_FIRST_PROB;
						cmdBusinessProb = Constants.DEFAULT_BUSINESS_PROB;
						cmdPremiumProb = Constants.DEFAULT_PREMIUM_PROB;
						cmdEconomyProb = Constants.DEFAULT_ECONOMY_PROB;
						cmdCancelProb = Constants.DEFAULT_CANCELLATION_PROB;
						SwingUtilities.invokeLater(new GUISimulator("Aircraft Booking Simulator"));	
					}else if(cmdFlag.compareTo("-nogui") == 0){
						//Run the simulation no gui
						Simulator s = new Simulator(); 
						Log l = new Log();
						
						SimulationRunner sr = new SimulationRunner(s,l);
						try {
							sr.runSimulation();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(runConfigurationException, e.getMessage(), "Run Configuration Booking Simulation: Error", JOptionPane.ERROR_MESSAGE);
							System.exit(-1);
						} 
					}else {
						throw new SimulationException("Flag must be -gui or -nogui.");
					}
					break;
				}
				default: {
					throw new SimulationException("\n" + "To run the simulator, the following condition must be fulfill: " + "\n"
						+ "Case 1: NO arguments" + "\n" + "Case 2: Single string argument: A single flag 每gui or 每nogui"
						+ "\n" + "Case 3: TEN arguments : A single flag 每gui or 每nogui + NINE simulator arguments in the order of " + "\n" + "seed,maxQueueSize,meanBookings,sdBookings,firstProb,businessProb,premiumProb,economyProb,cancelProb");
				}
			}
		} catch (SimulationException | IllegalArgumentException | IOException e1) {
			JOptionPane.showMessageDialog(runConfigurationException, e1.getMessage(), "Run Configuration Booking Simulation: Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}
