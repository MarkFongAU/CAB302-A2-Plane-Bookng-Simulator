/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;

/**
 * @author Ryan
 * @version 1.0
 */
public class Business extends Passenger {

	/**
	 * Business Constructor (Partially Supplied) 
	 * Passenger is created in New state, later given a Confirmed Business Class reservation, 
	 * Queued, or Refused booking if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight.  
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @see asgnPassengers.Passenger#Passenger(int,int)
	 */
	public Business(int bookingTime, int departureTime) throws PassengerException {
		//Stuff here
		super(bookingTime, departureTime);
		this.passID = "J:" + this.passID;
	}
	
	/**
	 * Simple constructor to support {@link asgn2Passengers.Passenger#upgrade()} in other subclasses
	 */
	protected Business() {
		super();
	}
	
	@Override
	public String noSeatsMsg() {
		return "No seats available in Business";
	}

	@Override
	public Passenger upgrade() {
		First upgrade = new First();
		upgrade.copyPassengerState(this);
		String[] strSeperate = upgrade.passID.split(":");
		String index = strSeperate[1]; //Index
		upgrade.passID = "F(U):" + index;
		return upgrade;
	}
}
