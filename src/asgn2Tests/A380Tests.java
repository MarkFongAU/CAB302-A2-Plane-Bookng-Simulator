/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * <p>Junit Test for Aircraft</p>
 * 
 * @author Ryan
 * @version 1.0
 */
public class A380Tests {
	/** Flight Code */
	private static final String FLIGHT_CODE = "CX123";
	private static final String FLIGHT_CODE_NULL = null;
	private static final String FLIGHT_CODE_EMPTY = "";
	
	/** Departure Time */
	private static final int DEPARTURE_TIME = 10;
	private static final int DEPARTURE_TIME_ZERO = 0;
	private static final int DEPARTURE_TIME_NEGATIVE = -1;
	
	/** Capacity of First Class */
	private static final int FIRST_POSITIVE = 10;
	private static final int FIRST_ZERO = 0;
	private static final int FIRST_ONE = 1;
	private static final int FIRST_NEGATIVE = -1;
	
	/** Capacity of Business Class */
	private static final int BUSINESS_POSITIVE = 10;
	private static final int BUSINESS_ZERO = 0;
	private static final int BUSINESS_ONE = 1;
	private static final int BUSINESS_NEGATIVE = -1;
	
	/** Capacity of Premium Class */
	private static final int PREMIUM_POSITIVE = 10;
	private static final int PREMIUM_ZERO = 0;
	private static final int PREMIUM_ONE = 1;
	private static final int PREMIUM_NEGATIVE = -1;
	
	/** Capacity of Economy Class */
	private static final int ECONOMY_POSITIVE = 10;
	private static final int ECONOMY_ZERO = 0;
	private static final int ECONOMY_ONE = 1;
	private static final int ECONOMY_NEGATIVE = -1;
	
	/** Total Number of passengers, 1 for each class */
	private static final int NUM_OF_PASSENGERS = 4;
	
	/** Cancellation Time */
	private static final int CANCELLATION_TIME = 8;
	
	/** Confirmation Time */
	private static final int CONFIRMATION_TIME = 8;
	
	/** Current Time */
	private static final int CURRENT_TIME = 6;
	
	/** Booking Time */
	private static final int BOOKING_TIME = 5;
	
	/** Upgrade Class variables */
	private static final String BUSINESS_TO_FIRST_AFTER = "F(U):161";
	private static final String BUSINESS_TO_FIRST_BEFORE = "J:161";
	private static final String PREMIUM_TO_BUSINESS_AFTER = "J(U):82";
	private static final String PREMIUM_TO_BUSINESS_BEFORE = "P:82";
	private static final String ECONOMY_TO_PREMIUM_AFTER = "P(U):43";
	private static final String ECONOMY_TO_PREMIUM_BEFORE = "Y:43";
	
	/** Get Passenger variables */
	private static final String GET_PASSENGER = "J:17";
	
	/** New A380 objects */
	private A380 _A380;
	
	/** New First class objects */
	private First _first;
	
	/** New Business class objects */
	private Business _business;	
	
	/** New Premium class objects */
	private Premium _premium;
	
	/** New Economy class objects */
	private Economy _economy;
	
	/** New Passenger List */
	private List<Passenger> listBeforeUpgrade;
	private List<Passenger> listAfterUpgrade;
	
	/**
	 * Setup Aircraft and Passengers.
	 * 
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @throws AircraftException if invalid parameters. 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpAircraftAndPassengers() throws AircraftException, PassengerException {
		_A380 = new A380(FLIGHT_CODE,DEPARTURE_TIME);
		_first = new First(BOOKING_TIME, DEPARTURE_TIME);
		_business = new Business(BOOKING_TIME, DEPARTURE_TIME);
		_premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);
		_economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);
	}

	/**
	 * <p>Catch NULL Flight Code.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters. 
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionNullFlightCode() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE_NULL, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch Empty Flight Code.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionEmptyFlightCode() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE_EMPTY, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}

	/**
	 * <p>Catch 0 Departure Time.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionDepartureTimeZero() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME_ZERO, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch negative Departure Time.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionDepartureTimeNegative() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME_NEGATIVE, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch negative First Class Capacity.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionFirstClassCapacityNegative() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_NEGATIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch negative Business Class Capacity.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionBusinessClassCapacityNegative() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_NEGATIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch negative Premium Class Capacity.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionPremiumClassCapacityNegative() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_NEGATIVE, ECONOMY_POSITIVE);
	}
	
	/**
	 * <p>Catch negative Economy Class Capacity.</p>
	 * Test method for {@link asgn2Aircraft.A380#A380(java.lang.String, int)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test(expected = AircraftException.class)
	public void testA380ExceptionEconomyClassCapacityNegative() throws AircraftException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_NEGATIVE);
	}
	
	/**
	 * <p>Catch passenger not confirmed when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test(expected = PassengerException.class)
	public void testA380CancelBookingExceptionPassengerNotConfirmed() throws AircraftException, PassengerException {
		_A380.cancelBooking(_first, CANCELLATION_TIME);
	}
	
	/**
	 * <p>Catch passenger not in aircraft seating when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test(expected = AircraftException.class)
	public void testA380CancelBookingExceptionPassengerNotInAircraftSeating() throws AircraftException, PassengerException {
		_first.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		_A380.cancelBooking(_first, CANCELLATION_TIME);
	}
	
	/**
	 * <p>Confirms passenger first class aircraft seating is removed when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test
	public void testA380CancelBookingRemoveFirstClass() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumFirst());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumFirst());
		
		_A380.cancelBooking(_first, CANCELLATION_TIME);
		assertEquals(0, _A380.getNumFirst());
	}
	
	/**
	 * <p>Confirms passenger business class aircraft seating is removed when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test
	public void testA380CancelBookingRemoveBusinessClass() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumBusiness());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumBusiness());
		
		_A380.cancelBooking(_business, CANCELLATION_TIME);
		assertEquals(0, _A380.getNumBusiness());
	}
	
	/**
	 * <p>Confirms passenger premium class aircraft seating is removed when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test
	public void testA380CancelBookingRemovePremiumClass() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumPremium());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumPremium());
		
		_A380.cancelBooking(_premium, CANCELLATION_TIME);
		assertEquals(0, _A380.getNumPremium());
	}
	
	/**
	 * <p>Confirms passenger economy class aircraft seating is removed when cancel booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#cancelBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. 
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	@Test
	public void testA380CancelBookingRemoveEconomyClass() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumEconomy());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumEconomy());
		
		_A380.cancelBooking(_economy, CANCELLATION_TIME);
		assertEquals(0, _A380.getNumEconomy());
	}
	
	/**
	 * <p>Catch passenger seat not available when confirm booking</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#confirmBooking(asgn2Passengers.Passenger, int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test(expected = AircraftException.class)
	public void testA380ConfirmBookingExceptionSeatNotAvailable() throws AircraftException, PassengerException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_ONE, BUSINESS_ONE, PREMIUM_ONE, ECONOMY_ONE);
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		
		//5th booking
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
	}
	
	/**
	 * <p>Confirms finalState function is working.</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#finalState()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.   
	 */
	@Test
	public void testA380FinalState() throws AircraftException, PassengerException {
		
		assertEquals("A380:"+FLIGHT_CODE+":"+DEPARTURE_TIME+" Pass: 0\n\n", _A380.finalState());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		String str = "A380:"+FLIGHT_CODE+":"+DEPARTURE_TIME+" Pass: 4" + "\n" 
				+ "passID: F:164" + "\n" + "BT: "+BOOKING_TIME + "\n" + "NotQ" + "\n"
				+ "passID: J:165" + "\n" + "BT: "+BOOKING_TIME + "\n" + "NotQ" + "\n"
				+ "passID: P:166" + "\n" + "BT: "+BOOKING_TIME + "\n" + "NotQ" + "\n"
				+ "passID: Y:167" + "\n" + "BT: "+BOOKING_TIME + "\n" + "NotQ" + "\n"
				+ "\n";
		
		assertEquals(str, _A380.finalState());
	}

	/**
	 * Confirms aircraft is an empty flight.
	 * Test method for {@link asgn2Aircraft.Aircraft#flightEmpty()}.
	 */
	@Test
	public void testFlightEmpty() {
		assertEquals(true, _A380.flightEmpty());
	}
	
	/**
	 * Confirms aircraft is not an empty flight.
	 * Test method for {@link asgn2Aircraft.Aircraft#flightEmpty()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.   
	 */
	@Test
	public void testFlightNotEmpty() throws AircraftException, PassengerException {
		assertEquals(true, _A380.flightEmpty());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		assertEquals(false, _A380.flightEmpty());
	}

	/**
	 * Confirms aircraft is a full flight.
	 * Test method for {@link asgn2Aircraft.Aircraft#flightFull()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.   
	 */
	@Test
	public void testFlightFull() throws AircraftException, PassengerException {
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_ONE, BUSINESS_ONE, PREMIUM_ONE, ECONOMY_ONE);
		assertEquals(false, _A380.flightFull());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		assertEquals(false, _A380.flightFull());
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		assertEquals(false, _A380.flightFull());
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		assertEquals(false, _A380.flightFull());
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(true, _A380.flightFull());
	}

	/**
	 * <p>Catch passenger who is going to fly after cancel seat</p>
	 * Test method for {@link asgn2Aircraft.Aircraft#flyPassengers(int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.  
	 */
	@Test(expected = PassengerException.class)
	public void testA380FlyPassengersExceptionFlyWithoutConfirmSeat() throws AircraftException, PassengerException {
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_first.cancelSeat(CANCELLATION_TIME);
		_A380.flyPassengers(DEPARTURE_TIME);		
	}
	
	/**
	 * Confirms flyPassenger function is working.
	 * Test method for {@link asgn2Aircraft.Aircraft#flyPassengers(int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.  
	 */
	@Test
	public void testA380FlyPassengers() throws AircraftException, PassengerException {
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		assertEquals(false, _first.isFlown());
		_A380.flyPassengers(DEPARTURE_TIME);	
		assertEquals(true, _first.isFlown());
	}

	/**
	 * Confirms Number of bookings of the flight.
	 * Test method for {@link asgn2Aircraft.Aircraft#getBookings()}.
	 * 
     * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.   
	 */
	@Test
	public void testGetNumBookings() throws AircraftException, PassengerException {
		Bookings _bookings =  _A380.getBookings();
		assertEquals(0, _bookings.getTotal());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		Bookings _bookings1 =  _A380.getBookings();
		assertEquals(NUM_OF_PASSENGERS, _bookings1.getTotal());
	}

	/**
	 * Test method for {@link asgn2Aircraft.Aircraft#getNumBusiness()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.  
	 */
	@Test
	public void testGetNumBusiness() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumBusiness());
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumBusiness());	
	}

	/**
	 * Confirms Number of Economy class Seats taken.
	 * Test method for {@link asgn2Aircraft.Aircraft#getNumEconomy()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.  
	 */
	@Test
	public void testGetNumEconomy() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumEconomy());
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumEconomy());	
	}

	/**
	 * Confirms Number of First class Seats taken.
	 * Test method for {@link asgn2Aircraft.Aircraft#getNumFirst()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testGetNumFirst() throws PassengerException, AircraftException {
		assertEquals(0, _A380.getNumFirst());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumFirst());	
	}

	/**
	 * Confirms Number of Passenger in aircraft.
	 * Test method for {@link asgn2Aircraft.Aircraft#getNumPassengers()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testGetNumPassengers() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumPassengers());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(NUM_OF_PASSENGERS, _A380.getNumPassengers());	
	}

	/**
	 * Confirms Number of Premium class Seats taken.
	 * Test method for {@link asgn2Aircraft.Aircraft#getNumPremium()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testGetNumPremium() throws AircraftException, PassengerException {
		assertEquals(0, _A380.getNumPremium());
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		assertEquals(1, _A380.getNumPremium());	
	}

	/**
	 * Confirms getPassengers function is working.
	 * Test method for {@link asgn2Aircraft.Aircraft#getPassengers()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testGetPassengers() throws AircraftException, PassengerException {
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		assertEquals(CURRENT_TIME+"::1::F:0::J:1::P:0::Y:0|J:N/Q>C|\n", _A380.getStatus(CURRENT_TIME));
		
		List<Passenger> list = _A380.getPassengers();
		assertEquals(GET_PASSENGER, list.get(0).getPassID());
	}

	/**
	 * Confirms getStatus function is working.
	 * Test method for {@link asgn2Aircraft.Aircraft#getStatus(int)}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testGetStatus() throws AircraftException, PassengerException {
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		assertEquals(CURRENT_TIME+"::1::F:0::J:1::P:0::Y:0|J:N/Q>C|\n", _A380.getStatus(CURRENT_TIME));
	}
	
	/**
	 * Confirms toString function is working.
	 * Test method for {@link asgn2Aircraft.Aircraft#toString()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. 
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class.  
	 */
	@Test
	public void testToString() throws AircraftException, PassengerException {
		assertEquals("A380:"+FLIGHT_CODE+":"+DEPARTURE_TIME+" Count: 0 [F: 0 J: 0 P: 0 Y: 0]", _A380.toString());
		_A380.confirmBooking(_first, CONFIRMATION_TIME);
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals("A380:"+FLIGHT_CODE+":"+DEPARTURE_TIME+" Count: 4 [F: 1 J: 1 P: 1 Y: 1]", _A380.toString());	
		
	}

	/**
	 * Confirms aircraft has passenger.
	 * Test method for {@link asgn2Aircraft.Aircraft#hasPassenger(asgn2Passengers.Passenger)}.
	 * 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	@Test
	public void testHasPassenger() throws PassengerException {
		_first.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		assertEquals(true, _A380.hasPassenger(_first));
	}
	
	/**
	 * Confirms aircraft has no passenger.
	 * Test method for {@link asgn2Aircraft.Aircraft#hasPassenger(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testHasNoPassenger() {
		assertEquals(false, _A380.hasPassenger(_first));
	}

	/**
	 * Confirms aircraft capacity
	 * Test method for {@link asgn2Aircraft.Aircraft#initialState()}.
	 */
	@Test
	public void testInitialState() {
		assertEquals("A380:"+FLIGHT_CODE+":"+DEPARTURE_TIME+" Capacity: 484 [F: 14 J: 64 P: 35 Y: 371]", _A380.initialState());
	}

	/**
	 * Confirms First class seats are available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testFirstClassSeatsAvailable(){
		assertEquals(true, _A380.seatsAvailable(_first));
	}
	
	/**
	 * Confirms First class seats are not available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test
	public void testFirstClassSeatsNotAvailable() throws AircraftException{
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_ZERO, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
		assertEquals(false, _A380.seatsAvailable(_first));
	}

	/**
	 * Confirms Business class seats are available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testBusinessClassSeatsAvailable(){
		assertEquals(true, _A380.seatsAvailable(_business));
	}

	/**
	 * Confirms Business class seats are not available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test
	public void testBusinessClassSeatsNotAvailable() throws AircraftException{
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_ZERO, PREMIUM_POSITIVE, ECONOMY_POSITIVE);
		assertEquals(false, _A380.seatsAvailable(_business));
	}
	
	/**
	 * Confirms Premium class seats are available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testPremiumClassSeatsAvailable(){
		assertEquals(true, _A380.seatsAvailable(_premium));
	}
	
	/**
	 * Confirms Premium class seats are not available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test
	public void testPremiumClassSeatsNotAvailable() throws AircraftException{
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_ZERO, ECONOMY_POSITIVE);
		assertEquals(false, _A380.seatsAvailable(_premium));
	}
	
	/**
	 * Confirms Economy class seats are available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testEconomyClassSeatsAvailable(){
		assertEquals(true, _A380.seatsAvailable(_economy));
	}
	
	/**
	 * Confirms Economy class seats are not available
	 * Test method for {@link asgn2Aircraft.Aircraft#seatsAvailable(asgn2Passengers.Passenger)}.
	 * 
	 * @throws AircraftException if invalid parameters.
	 */
	@Test
	public void testEconomyClassSeatsNotAvailable() throws AircraftException{
		_A380 = new A380(FLIGHT_CODE, DEPARTURE_TIME, FIRST_POSITIVE, BUSINESS_POSITIVE, PREMIUM_POSITIVE, ECONOMY_ZERO);
		assertEquals(false, _A380.seatsAvailable(_economy));
	}
	
	/**
	 * Confirms class upgrade from Business to First
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testBusinessToFirstUpgradeBookings() throws PassengerException, AircraftException {
		_A380.confirmBooking(_business, CONFIRMATION_TIME);
		assertEquals(CURRENT_TIME+"::1::F:0::J:1::P:0::Y:0|J:N/Q>C|\n", _A380.getStatus(CURRENT_TIME));
		
		listBeforeUpgrade = _A380.getPassengers();
		assertEquals(BUSINESS_TO_FIRST_BEFORE, listBeforeUpgrade.get(0).getPassID());
		
		_A380.upgradeBookings();
		assertEquals(CURRENT_TIME+"::1::F:1::J:0::P:0::Y:0|U:J>F|\n", _A380.getStatus(CURRENT_TIME));
		
		listAfterUpgrade = _A380.getPassengers();
		assertEquals(BUSINESS_TO_FIRST_AFTER, listAfterUpgrade.get(0).getPassID());
	}
	
	/**
	 * Confirms class upgrade from Premium to Business
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testPremiumtoBusinessUpgradeBookings() throws PassengerException, AircraftException {
		_A380.confirmBooking(_premium, CONFIRMATION_TIME);
		assertEquals(CURRENT_TIME+"::1::F:0::J:0::P:1::Y:0|P:N/Q>C|\n", _A380.getStatus(CURRENT_TIME));
		
		listBeforeUpgrade = _A380.getPassengers();
		assertEquals(PREMIUM_TO_BUSINESS_BEFORE, listBeforeUpgrade.get(0).getPassID());
		
		_A380.upgradeBookings();
		assertEquals(CURRENT_TIME+"::1::F:0::J:1::P:0::Y:0|U:P>J|\n", _A380.getStatus(CURRENT_TIME));
		
		listAfterUpgrade = _A380.getPassengers();
		assertEquals(PREMIUM_TO_BUSINESS_AFTER, listAfterUpgrade.get(0).getPassID());
	}
	
	/**
	 * Confirms class upgrade from Economy to Business
	 * Test method for {@link asgn2Aircraft.Aircraft#upgradeBookings()}.
	 * 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	@Test
	public void testEconomytoPremiumUpgradeBookings() throws PassengerException, AircraftException {
		_A380.confirmBooking(_economy, CONFIRMATION_TIME);
		assertEquals(CURRENT_TIME+"::1::F:0::J:0::P:0::Y:1|Y:N/Q>C|\n", _A380.getStatus(CURRENT_TIME));
		
		listBeforeUpgrade = _A380.getPassengers();
		assertEquals(ECONOMY_TO_PREMIUM_BEFORE, listBeforeUpgrade.get(0).getPassID());
		
		_A380.upgradeBookings();
		assertEquals(CURRENT_TIME+"::1::F:0::J:0::P:1::Y:0|U:Y>P|\n", _A380.getStatus(CURRENT_TIME));
		
		listAfterUpgrade = _A380.getPassengers();
		assertEquals(ECONOMY_TO_PREMIUM_AFTER, listAfterUpgrade.get(0).getPassID());
	}
}
