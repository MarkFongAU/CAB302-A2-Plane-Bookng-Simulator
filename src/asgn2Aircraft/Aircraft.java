/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Aircraft;


import java.util.ArrayList;
import java.util.List;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;
import asgn2Simulators.Log;

/**
 * The <code>Aircraft</code> class provides facilities for modelling a commercial jet 
 * aircraft with multiple travel classes. New aircraft types are created by explicitly 
 * extending this class and providing the necessary configuration information. 
 * 
 * In particular, <code>Aircraft</code> maintains a collection of currently booked passengers, 
 * those with a Confirmed seat on the flight. Queueing and Refused bookings are handled by the 
 * main {@link asgn2Simulators.Simulator} class. 
 *   
 * The class maintains a variety of constraints on passengers, bookings and movement 
 * between travel classes, and relies heavily on the asgn2Passengers hierarchy. Reports are 
 * also provided for logging and graphical display. 
 * 
 * @author Fong
 * @version 1.0
 */
public abstract class Aircraft {

	protected int firstCapacity;
	protected int businessCapacity;
	protected int premiumCapacity;
	protected int economyCapacity;
	protected int capacity;
		
	protected int numFirst;
	protected int numBusiness;
	protected int numPremium; 
	protected int numEconomy; 

	protected String flightCode;
	protected String type; 
	protected int departureTime; 
	protected String status;
	protected List<Passenger> seats;

	/**
	 * Constructor sets flight info and the basic size parameters. 
	 * 
	 * @param flightCode <code>String</code> containing flight ID 
	 * @param departureTime <code>int</code> scheduled departure time
	 * @param first <code>int</code> capacity of First Class 
	 * @param business <code>int</code> capacity of Business Class 
	 * @param premium <code>int</code> capacity of Premium Economy Class 
	 * @param economy <code>int</code> capacity of Economy Class 
	 * @throws AircraftException if isNull(flightCode) OR (departureTime <=0) OR ({first,business,premium,economy} <0)
	 */
	public Aircraft(String flightCode,int departureTime, int first, int business, int premium, int economy) throws AircraftException {
		//Catch exception
		String msg="";
		if(flightCode == "" || flightCode == null ){
			msg = "FlightCode cannot be null.";
			throw new AircraftException(msg);
		}
		if(departureTime <=0 ){
			msg = "Departure Time cannot be null.";
			throw new AircraftException(msg);
		}
		if(first < 0 || business < 0 || premium < 0 || economy < 0 ){
			msg = "Class capacity cannot be null.";
			throw new AircraftException(msg);
		}
		
		//Set aircraft information
		//Lots here 
		this.status = "";
		this.flightCode = flightCode;
		this.type = "";
		this.departureTime = departureTime;
		seats = new ArrayList<Passenger>();

		// Set capacity of each class
		this.firstCapacity = first;
		this.businessCapacity = business;
		this.premiumCapacity = premium;
		this.economyCapacity = economy;
		this.capacity = first + business + premium + economy;

		// Set number of passengers in each class to 0
		this.numFirst = 0;
		this.numBusiness = 0;
		this.numPremium = 0;
		this.numEconomy = 0;
	}
	
	/**
	 * Method to remove passenger from the aircraft - passenger must have a confirmed 
	 * seat prior to entry to this method.   
	 *
	 * @param p <code>Passenger</code> to be removed from the aircraft 
	 * @param cancellationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. See {@link asgn2Passengers.Passenger#cancelSeat(int)}
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	public void cancelBooking(Passenger p,int cancellationTime) throws PassengerException, AircraftException {
		//Catch exception
		String msg="";
		if(!p.isConfirmed()){ 
			msg = "Passenger has no confirmed seat.";
			throw new PassengerException(msg);
		}
		if(!seats.contains(p)){
			msg = "Passenger " + p.getPassID() + "did not confirm seat in the aircraft.";
			throw new AircraftException(msg);
		}
		
		//Change passenger status
		p.cancelSeat(cancellationTime);
		
		//Remove passenger from seat
		seats.remove(p);
		
		//Stuff here
		this.status += Log.setPassengerMsg(p,"C","N");
		//Stuff here
		// Remove number of seats of passenger 's class in aircraft
		String[] strSeperate = p.getPassID().split(":");
		String flightClass = strSeperate[0]; // F,J,P,Y class
		if (flightClass.equals("F")) {
			this.numFirst--;
		} else if (flightClass.equals("J")) {
			this.numBusiness--;
		} else if (flightClass.equals("P")) {
			this.numPremium--;
		} else if (flightClass.equals("Y")) {
			this.numEconomy--;
		}
	}

	/**
	 * Method to add a Passenger to the aircraft seating. 
	 * Precondition is a test that a seat is available in the required fare class
	 * 
	 * @param p <code>Passenger</code> to be added to the aircraft 
	 * @param confirmationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	public void confirmBooking(Passenger p,int confirmationTime) throws AircraftException, PassengerException { 
		//Catch exception
		String msg="";
		if(!seatsAvailable(p)){
			msg = noSeatsAvailableMsg(p);
			throw new AircraftException(msg);
		}
		
		//Change passenger status
		p.confirmSeat(confirmationTime, this.departureTime);
		
		//Add passenger to seat
		seats.add(p);
		
		//Stuff here
		this.status += Log.setPassengerMsg(p,"N/Q","C");
		//Stuff here
		//Add number of seats of passenger 's class in aircraft
		String[] strSeperate = p.getPassID().split(":");
		String flightClass = strSeperate[0]; //F,J,P,Y class
		if(flightClass.equals("F")){
			this.numFirst++;
		}else if(flightClass.equals("J")){
			this.numBusiness++;
		}else if(flightClass.equals("P")){
			this.numPremium++;
		}else if(flightClass.equals("Y")){
			this.numEconomy++;
		}
	}
	
	/**
	 * State dump intended for use in logging the final state of the aircraft. (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of final aircraft state 
	 */
	public String finalState() {
		String str = aircraftIDString() + " Pass: " + this.seats.size() + "\n";
		for (Passenger p : this.seats) {
			str += p.toString() + "\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple status showing whether aircraft is empty
	 * 
	 * @return <code>boolean</code> true if aircraft empty; false otherwise 
	 */
	public boolean flightEmpty() {
		if(seats.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Simple status showing whether aircraft is full
	 * 
	 * @return <code>boolean</code> true if aircraft full; false otherwise 
	 */
	public boolean flightFull() {
		if(seats.size() >= this.capacity){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Method to finalise the aircraft seating on departure. 
	 * Effect is to change the state of each passenger to Flown. 
	 * departureTime parameter allows for rescheduling 
	 * 
	 * @param departureTime <code>int</code> actual departureTime from simulation  
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * See {@link asgn2Passengers.Passenger#flyPassenger(int)}. 
	 */
	public void flyPassengers(int departureTime) throws PassengerException { 
		String msg="";
		for(Passenger p:seats){
			if(!p.isConfirmed()){
				msg = "not in confirmed state.";
				throw new PassengerException("Passenger " + p.getPassID() + ": " + msg);
			}
			p.flyPassenger(departureTime);
			this.status += Log.setPassengerMsg(p,"C","F");
		}
	}
	
	/**
	 * Method to return an {@link asgn2Aircraft.Bookings} object containing the Confirmed 
	 * booking status for this aircraft. 
	 * 
	 * @return <code>Bookings</code> object containing the status.  
	 */
	public Bookings getBookings() {
		return new Bookings(numFirst, numBusiness, numPremium, numEconomy, getNumPassengers(), capacity- getNumPassengers());
	}
	
	/**
	 * Simple getter for number of confirmed Business Class passengers
	 * 
	 * @return <code>int</code> number of Business Class passengers 
	 */
	public int getNumBusiness() {
		return this.numBusiness;
	}
	
	
	/**
	 * Simple getter for number of confirmed Economy passengers
	 * 
	 * @return <code>int</code> number of Economy Class passengers 
	 */
	public int getNumEconomy() {
		return this.numEconomy;
	}

	/**
	 * Simple getter for number of confirmed First Class passengers
	 * 
	 * @return <code>int</code> number of First Class passengers 
	 */
	public int getNumFirst() {
		return this.numFirst;
	}

	/**
	 * Simple getter for the total number of confirmed passengers 
	 * 
	 * @return <code>int</code> number of Confirmed passengers 
	 */
	public int getNumPassengers() {
		return this.numFirst + this.numBusiness + this.numPremium + this.numEconomy;
	}
	
	/**
	 * Simple getter for number of confirmed Premium Economy passengers
	 * 
	 * @return <code>int</code> number of Premium Economy Class passengers
	 */
	public int getNumPremium() {
		return this.numPremium;
	}
	
	/**
	 * Method to return an {@link java.util.List} object containing a copy of 
	 * the list of passengers on this aircraft. 
	 * 
	 * @return <code>List<Passenger></code> object containing the passengers.  
	 */
	public List<Passenger> getPassengers() {
		List<Passenger> newCopy = new ArrayList<Passenger>();
		for(Passenger p:seats){
			newCopy.add(p);
		}
		return newCopy;
	}
	
	/**
	 * Method used to provide the current status of the aircraft for logging. (Supplied) 
	 * Uses private status <code>String</code>, set whenever a transition occurs. 
	 *  
	 * @return <code>String</code> containing current aircraft state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.seats.size() + "::"
		+ "F:" + this.numFirst + "::J:" + this.numBusiness 
		+ "::P:" + this.numPremium + "::Y:" + this.numEconomy; 
		str += this.status;
		this.status="";
		return str+"\n";
	}
	
	/**
	 * Simple boolean to check whether a passenger is included on the aircraft 
	 * 
	 * @param p <code>Passenger</code> whose presence we are checking
	 * @return <code>boolean</code> true if isConfirmed(p); false otherwise 
	 */
	public boolean hasPassenger(Passenger p) {
		if(p.isConfirmed()){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * State dump intended for logging the aircraft parameters (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of initial aircraft parameters 
	 */ 
	public String initialState() {
		return aircraftIDString() + " Capacity: " + this.capacity 
				+ " [F: " 
				+ this.firstCapacity + " J: " + this.businessCapacity 
				+ " P: " + this.premiumCapacity + " Y: " + this.economyCapacity
				+ "]";
	}
	
	/**
	 * Given a Passenger, method determines whether there are seats available in that 
	 * fare class. 
	 *   
	 * @param p <code>Passenger</code> to be Confirmed
	 * @return <code>boolean</code> true if seats in Class(p); false otherwise
	 */
	public boolean seatsAvailable(Passenger p) {		
		boolean checkSeats = false;

		// Search if a seat is available in the fare class the passenger has chosen
		if (p instanceof First) {
			if (firstCapacity - numFirst > 0) {
				checkSeats = true;
			} else {
				checkSeats = false;
			}
		} else if (p instanceof Business) {
			if (businessCapacity - numBusiness > 0) {
				checkSeats = true;
			} else {
				checkSeats = false;
			}
		} else if (p instanceof Premium) {
			if (premiumCapacity - numPremium > 0) {
				checkSeats = true;
			} else {
				checkSeats = false;
			}
		} else if (p instanceof Economy) {
			if (economyCapacity - numEconomy > 0) {
				checkSeats = true;
			} else {
				checkSeats = false;
			}
		}
		return checkSeats;
	}

	/* 
	 * (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return aircraftIDString() + " Count: " + this.seats.size() 
				+ " [F: " + numFirst
				+ " J: " + numBusiness 
				+ " P: " + numPremium 
				+ " Y: " + numEconomy 
			    + "]";
	}


	/**
	 * Method to upgrade Passengers to try to fill the aircraft seating. 
	 * Called at departureTime. Works through the aircraft fare classes in 
	 * descending order of status. No upgrades are possible from First, so 
	 * we consider Business passengers (upgrading if there is space in First), 
	 * then Premium, upgrading to fill spaces already available and those created 
	 * by upgrades to First), and then finally, we do the same for Economy, upgrading 
	 * where possible to Premium.  
	 */
	public void upgradeBookings() { 
		// Seat index
		int seatIndex = 0;

		// Upgrade from Business to First
		for (Passenger p : seats) {
			if (numFirst < firstCapacity) {
				if (p instanceof Business) {
					seats.set(seatIndex, p.upgrade());
					this.numBusiness--;
					this.numFirst++;
					this.status += Log.setUpgradeMsg(p);
				}
			}
			seatIndex++;
		}
		seatIndex = 0;

		// Upgrade from Premium to Business
		for (Passenger p : seats) {
			if (numBusiness < businessCapacity) {
				if (p instanceof Premium) {
					seats.set(seatIndex, p.upgrade());
					this.numPremium--;
					this.numBusiness++;
					this.status += Log.setUpgradeMsg(p);
				}
			}
			seatIndex++;
		}
		seatIndex = 0;
		
		// Upgrade from Economy to Premium
		for (Passenger p : seats) {
			if (numPremium < premiumCapacity) {
				if (p instanceof Economy) {
					seats.set(seatIndex, p.upgrade());
					this.numEconomy--;
					this.numPremium++;
					this.status += Log.setUpgradeMsg(p);
				}
			}
			seatIndex++;
		}
	}

	/**
	 * Simple String method for the Aircraft ID 
	 * 
	 * @return <code>String</code> containing the Aircraft ID 
	 */
	private String aircraftIDString() {
		return this.type + ":" + this.flightCode + ":" + this.departureTime;
	}


	//Various private helper methods to check arguments and throw exceptions, to increment 
	//or decrement counts based on the class of the Passenger, and to get the number of seats 
	//available in a particular class


	//Used in the exception thrown when we can't confirm a passenger 
	/** 
	 * Helper method with error messages for failed bookings
	 * @param p Passenger seeking a confirmed seat
	 * @return msg string failure reason 
	 */
	private String noSeatsAvailableMsg(Passenger p) {
		String msg = "";
		return msg + p.noSeatsMsg(); 
	}
}

