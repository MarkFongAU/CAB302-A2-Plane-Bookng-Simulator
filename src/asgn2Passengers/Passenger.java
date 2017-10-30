/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;


/**
 * Passenger is an abstract class specifying the basic state of an airline passenger,  
 * and providing methods to set and access that state. A passenger is created upon booking, 
 * at which point the reservation is confirmed, or the passenger goes on to a waiting list. 
 * If the waiting list is full, then the passenger is either bumped to the next available 
 * service or lost altogether from the system. A passenger lost from the system is recorded 
 * and their fare level used in the calculation of lost revenue.<br><br>
 * 
 * Passengers have the following states and permitted transitions:<br>
 * State: New - on creation; immediately transition to {Confirmed,Queued,Refused}
 * <ul>
 * <li>{@link #queuePassenger(int, int)}  | New -> Queued</li>
 * <li>{@link #confirmSeat(int,int)} | New -> Confirmed</li>
 * <li>{@link #refusePassenger(int)} | New -> Refused</li> 
 * </ul>
 * <br>
 * State: Queued - on central waiting list to see if seat available. 
 * Transitions to {Confirmed,Refused}
 * <ul>
 * <li>{@link #confirmSeat(int,int)} | Queued -> Confirmed; up until departureTime</li>
 * <li>{@link #refusePassenger(int)} | Queued -> Refused; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Confirmed - seat confirmed on requested flight. Transitions to {New,Flown}
 * <ul>
 * <li>{@link #cancelSeat(int)}   | Confirmed -> New; up until departureTime</li>
 * <li>{@link #flyPassenger(int)} | Confirmed -> Flown; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Refused - final state - no transitions permitted<br> 
 * State: Flown - final state - no transitions permitted<br>
 * <br>
 * {@link asgn2Passengers.PassengerException} is thrown if the state is inappropriate: see method javadoc for details. 
 * The method javadoc also indicates the constraints on the time and other parameters.<br><br>
 * 
 * @author Ryan
 * @version 1.0
 */
public abstract class Passenger {

	private static int index = 0;
	protected String passID;
	protected boolean newState; 
	protected boolean confirmed;
	protected boolean inQueue;
	protected boolean flown;
	protected boolean refused;
	protected int bookingTime;
	protected int enterQueueTime;
	protected int exitQueueTime;
	protected int confirmationTime;
	protected int departureTime; 
	
	private boolean wasConfirmed = false;
	private boolean wasInQueue = false;
	
	/**
	 * Passenger Constructor 
	 * On creation, Passenger is in a New state. Subsequent processing transitions to a 
	 * confirmed reservation, queued, or the booking is refused if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight. 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	public Passenger(int bookingTime, int departureTime) throws PassengerException  {
		// Stuff here
		String msg = "";
		if (bookingTime < 0) {
			msg = "Booking time cannot be less than 0.";
			throw new PassengerException(msg);
		}
		if (departureTime <= 0) {
			msg = "Departure time cannot be less than 0.";
			throw new PassengerException(msg);
		}
		if (departureTime < bookingTime) {
			msg = "Cannot book after departure";
			throw new PassengerException(msg);
		}
		this.passID = "" + Passenger.index;
		Passenger.index++;
		// Stuff here
		this.newState = true;
		this.confirmed = false;
		this.inQueue = false;
		this.flown = false;
		this.refused = false;
		this.bookingTime = bookingTime;
		this.enterQueueTime = 0;
		this.exitQueueTime = 0;
		this.confirmationTime = 0;
		this.departureTime = departureTime;
		this.wasConfirmed = false;
		this.wasInQueue = false;
	}
	
	/**
	 * Simple no-argument constructor to support {@link #upgrade()}
	 */
	protected Passenger() {

	}
	
	/**
	 * Transition passenger to New<br>
	 * PRE: isConfirmed(this)<br>
	 * POST: isNew(this) AND this.getBookingTime() == cancellationTime<br>
	 * <ul>
	 * <li>cancelSeat: Confirmed -> New; up until departureTime</li>
	 * </ul>
	 * 
	 * Cancellation returns booking to New state; Booking time is adjusted to this 
	 * cancellation time and Passenger is processed as a new booking. Cannot guarantee 
	 * either a new confirmed seat or a place in the queue. DepartureTime remains the same 
	 * initially, adjusted by subsequent method calls. 
	 *  
	 * @param cancellationTime <code>int</code> holding cancellation time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (cancellationTime < 0) OR (departureTime < cancellationTime)
	 */
	public void cancelSeat(int cancellationTime) throws PassengerException {
		String msg="";
		if(this.isNew()){
			msg = "still in new state.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isQueued()){
			msg = "still in queue.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isRefused()){
			msg = "has been refused.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isFlown()){
			msg = "has flown.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(cancellationTime < 0){
			msg = "cancellation time cannot be less than 0.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.departureTime < cancellationTime){
			msg = "cannot cancel after departure.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		//State change
		if(this.isConfirmed()){
			this.confirmed = false;
			this.wasConfirmed = true;
			this.newState = true;
			this.bookingTime = cancellationTime;
		}
	}

	/**
	 * Transition passenger to Confirmed seat<br>
	 * PRE: isNew(this) OR inQueue(this)<br>
	 * POST: isConfirmed(this) AND this.getConfirmationTime() == confirmationTime AND <br>
	 * 	     this.getDepartureTime() == departureTime<br>
	 * <ul>
	 * <li>confirmSeat: New -> Confirmed</li>
	 * <li>confirmSeat: Queued -> Confirmed; up until departureTime</li> 
	 * <li>if isQueued(this), then POST: this.getExitQueueTime() == confirmationTime</li>
	 * </ul>
	 * 
	 * @param confirmationTime <code>int</code> day when seat is confirmed
	 * @param departureTime <code>int</code> day flight is scheduled to depart 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	public void confirmSeat(int confirmationTime, int departureTime) throws PassengerException {
		String msg="";
		if(this.isConfirmed()){
			msg = "has already confirmed flight seat.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isRefused()){
			msg = "has been refused.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isFlown()){
			msg = "has already flown.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(confirmationTime < 0){
			msg = "confirmation time cannot be less than 0.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(departureTime < confirmationTime){
			msg = "cannot confirm after departure.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		//State change
		if(this.isNew()){
			this.newState = false;
			this.confirmed = true;
			this.confirmationTime = confirmationTime;
			this.departureTime = departureTime;
		}else if(this.isQueued()){
			this.inQueue = false;
			this.wasInQueue = true;
			this.confirmed = true;
			this.confirmationTime = confirmationTime;
			this.departureTime = departureTime;
			this.exitQueueTime= confirmationTime;
		}
	}

	/**
	 * Transition passenger to Flown<br>
	 * PRE: isConfirmed(this)<br>
	 * POST: isFlown(this) AND this.getDepartureTime() == departureTime<br> 
	 * <ul>
	 * <li>flyPassenger:Confirmed -> Flown; finalised on departureTime</li>
	 * </ul>
	 *  
	 * @param departureTime <code>int</code> holding actual departure time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (departureTime <= 0)
	 */
	public void flyPassenger(int departureTime) throws PassengerException {
		String msg="";
		if(this.isNew()){
			msg = "still in new state.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isQueued()){
			msg = "still in queue.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isRefused()){
			msg = "has been refused.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isFlown()){
			msg = "has already flown.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(departureTime <= 0){
			msg = "Departure time cannot be less than 0.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		//State change
		if(this.isConfirmed()){
			this.confirmed = false;
			this.wasConfirmed = true;
			this.flown = true;
			this.departureTime = departureTime;
		}
	}

	/**
	 * Simple getter for the booking time 
	 * 
	 * @return the bookingTime
	 */
	public int getBookingTime() {
		return this.bookingTime;
	}

	/**
	 * Simple getter for the confirmation time
	 * Note: result may be 0 prior to confirmation 
	 * 
	 * @return the confirmationTime
	 */
	public int getConfirmationTime() {
		return this.confirmationTime;
	}

	/**
	 * Simple getter for the departureTime
	 * 
	 * @return the departureTime
	 */
	public int getDepartureTime() {
		return this.departureTime;
	}
	
	/**
	 * Simple getter for the queue entry time
	 * 
	 * @return the enterQueueTime
	 */
	public int getEnterQueueTime() {
		return this.enterQueueTime;
	}

	/**
	 * Simple getter for the queue exit time
	 * 
	 * @return the exitQueueTime
	 */
	public int getExitQueueTime() {
		return this.exitQueueTime;
	}

	/**
	 * Simple getter for the Passenger ID
	 * 
	 * @return the passID
	 */
	public String getPassID() {
		return this.passID;
	}

	/**
	 * Boolean status indicating whether Passenger has a Confirmed seat
	 * 
	 * @return <code>boolean</code> true if Confirmed state; false otherwise 
	 */
	public boolean isConfirmed() {
		if(this.confirmed){
			return true;
		}
		else{
			return false;
		}
	}
		
	/**
	 * Boolean status indicating whether Passenger has flown
	 * 
	 * @return <code>boolean</code> true if Flown state; false otherwise 
	 */
	public boolean isFlown() {
		if(this.flown){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Boolean status indicating whether Passenger is New
	 * 
	 * @return <code>boolean</code> true if New state; false otherwise 
	 */
	public boolean isNew() {
		if(this.newState){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Boolean status indicating whether Passenger is currently Queued
	 * 
	 * @return <code>boolean</code> true if Queued state; false otherwise 
	 */
	public boolean isQueued() {
		if(this.inQueue){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Boolean status indicating whether Passenger is Refused
	 * 
	 * @return <code>boolean</code> true if Refused state; false otherwise 
	 */
	public boolean isRefused() {
		if(this.refused){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Abstract helper method to allow specialised "no seats" message (Supplied) 
	 * 
	 * @return <code>String</code> containing "no seats available" warning message
	 */
	public abstract String noSeatsMsg();
	
	/**
	 * Transition passenger to Queued<br>
	 * PRE: isNew(this)<br>
	 * POST: isQueued(this) AND this.getEnterQueueTime() == queueTime AND <br>
	 * this.getDepartureTime == departureTime<br>
	 * <ul>
	 * <li>queuePassenger: New -> Queued</li>
	 * </ul>
	 * 
	 * Queue on booking if seat is not available. Intended departureTime may be set here if 
	 * required. Queuing ceases on confirmation or when the departure time is reached. 
	 * 
	 * @param queueTime <code>int</code> holding queue entry time 
	 * @param departureTime <code>int</code> holding intended departure time 
	 * @throws PassengerException if isQueued(this) OR isConfirmed(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (queueTime < 0) OR (departureTime < queueTime)
	 */
	public void queuePassenger(int queueTime, int departureTime) throws PassengerException {
		String msg="";
		if(this.isQueued()){
			msg = "still in queue.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isConfirmed()){
			msg = "has already confirmed flight seat.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isRefused()){
			msg = "has been refused";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isFlown()){
			msg = "has already flown";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(queueTime < 0){
			msg = "Queue time cannot be less than 0.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(departureTime < queueTime){
			msg = "cannot queue after departure.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		//State change
		if(this.isNew()){
			this.newState = false;
			this.inQueue = true;
			this.enterQueueTime = queueTime;
			this.departureTime = departureTime;
		}
	}
	
	/**
	 * Transition passenger to Refused<br>
	 * PRE: isNew(this) OR isQueued(this) <br>
	 * POST: isRefused(this)
	 * <ul>
	 * <li>refusePassenger:New -> Refused</li> 
     * <li>refusePassenger:Queued -> Refused; finalised on departureTime</li> 
     * <li>if isQueued(this), then POST: this.getExitQueueTime() == refusalTime</li>
     * </ul>
     * 
	 * @param refusalTime <code>int</code> holding refusal time  
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this) 
	 * 			OR (refusalTime < 0) OR (refusalTime < bookingTime)
	 */
	public void refusePassenger(int refusalTime) throws PassengerException {
		String msg="";
		if(this.isConfirmed()){
			msg = "has already confirmed flight seat.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isRefused()){
			msg = "has been refused";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(this.isFlown()){
			msg = "has already flown";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(refusalTime < 0){
			msg = "Refusal time cannot be less than 0.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		if(refusalTime < bookingTime){
			msg = "cannot book after been refused.";
			throw new PassengerException("Passenger " + this.passID + ": " + msg);
		}
		//State change
		if(this.isNew()){
			this.newState = false;
			this.refused = true;
		}else if(this.isQueued()){
			this.inQueue = false;
			this.wasInQueue = true;
			this.exitQueueTime = refusalTime;
			this.refused = true;
		}
	}
	
	/* (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "passID: " + passID + "\nBT: " + this.bookingTime; 
		//Queuing Information - duration may not be accurate if multiple queuing events 
		if (this.wasQueued()) {
			str += "\nExitQ:" + this.exitQueueTime; 
			int queueTime = (this.exitQueueTime - this.bookingTime);
			str += " QT: " + queueTime; 
		} else {
			str += "\nNotQ";
		}
		
		if (this.isFlown()) {
			str+= "\nConfT: " + this.confirmationTime; 
			str+= " Flew: " + this.departureTime; 
		} else if (this.wasConfirmed()) {
			str+= "\nConfT: " + this.confirmationTime;
			str+= " NotFlown";
		}	
		return str;
	}

	/**
	 * Method to create new Passenger instance based on upgrade to a higher fare class.
	 * Transition rules are specific to each fare class
	 * 
	 * @return <code>Passenger</code> of the upgraded fare class 
	 */
	public abstract Passenger upgrade();

	/**
	 * Boolean status indicating whether passenger was ever confirmed
	 * 
	 * @return <code>boolean</code> true if was Confirmed state; false otherwise
	 */
	public boolean wasConfirmed() {
		if(wasConfirmed){
			return true;
		} else{
			return false;
		}
	}

	/**
	 * Boolean status indicating whether passenger was ever queued
	 * 
	 * @return <code>boolean</code> true if was Queued state; false otherwise
	 */
	public boolean wasQueued() {
		if(wasInQueue){
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * Helper method to copy state to facilitate {@link #upgrade()}
	 * 
	 * @param <code>Passenger</code> state to transfer
	 */
	protected void copyPassengerState(Passenger p) {
		this.passID = p.passID;
		this.newState = p.newState;
		this.confirmed = p.confirmed;
		this.inQueue = p.inQueue;
		this.flown = p.flown;
		this.refused = p.refused;
		this.bookingTime = p.bookingTime;
		this.enterQueueTime = p.enterQueueTime;
		this.exitQueueTime = p.exitQueueTime;
		this.confirmationTime = p.confirmationTime;
		this.departureTime = p.departureTime;
		this.wasConfirmed = p.wasConfirmed;
		this.wasInQueue = p.wasInQueue;
	}
	
	//Various private helper methods to check arguments and throw exceptions
	
}
