/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.PassengerException;

/**
 * <p>Junit Test for Economy</p>
 * 
 * @author Fong
 * @version 1.0
 */
public class EconomyTests {
	/** Booking Time */
	private static final int BOOKING_TIME = 5;
	
	/** Confirmation Time */
	private static final int CONFIRMATION_TIME = 8;
	
	/** Departure Time */
	private static final int DEPARTURE_TIME = 10;
	
	/** New Economy class objects */
	private Economy _economy;

	/** Get Passenger PassID variables */
	private static final String CONSTRUCTOR_PASS_ID = "Y:2";
	private static final String NORMAL_PASS_ID = "Y:0";
	private static final String UPGRADE_PASS_ID = "P(U):0";

	/**
	 * Setup Passenger of premium class.
	 * 
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws PassengerException {
		_economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);
	}

	/**
	 * <p>Message for no seats, not tested</p>
	 * Test method for {@link asgn2Passengers.Economy#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * <p>Confirms upgrades function is working.</p>
	 * Test method for {@link asgn2Passengers.Economy#upgrade()}.
	 * 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		_economy.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		assertEquals(NORMAL_PASS_ID, _economy.getPassID());
		assertEquals(UPGRADE_PASS_ID, _economy.upgrade().getPassID());
		assertEquals(true, _economy.upgrade().isConfirmed());
	}

	/**
	 * <p>Confirms new Business default constructor is working.</p>
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	@Test
	public void testEconomyDefaultConstructor() throws PassengerException {
		Economy _testEconomy = new Economy(BOOKING_TIME, DEPARTURE_TIME);
		assertEquals(CONSTRUCTOR_PASS_ID, _testEconomy.getPassID());
		assertEquals(true, _testEconomy.isNew());
		assertEquals(false, _testEconomy.isConfirmed());
		assertEquals(false, _testEconomy.isQueued());
		assertEquals(false, _testEconomy.isFlown());
		assertEquals(false, _testEconomy.isRefused());
		assertEquals(BOOKING_TIME, _testEconomy.getBookingTime());
		assertEquals(0, _testEconomy.getEnterQueueTime());
		assertEquals(0, _testEconomy.getExitQueueTime());
		assertEquals(0, _testEconomy.getConfirmationTime());
		assertEquals(DEPARTURE_TIME, _testEconomy.getDepartureTime());
		assertEquals(false, _testEconomy.wasConfirmed());
		assertEquals(false, _testEconomy.wasQueued());
	}

}
