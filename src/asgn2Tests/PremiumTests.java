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

import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * <p>Junit Test for Premium</p>
 * 
 * @author Fong
 * @version 1.0
 */
public class PremiumTests {
	/** Booking Time */
	private static final int BOOKING_TIME = 5;
	
	/** Confirmation Time */
	private static final int CONFIRMATION_TIME = 8;
	
	/** Departure Time */
	private static final int DEPARTURE_TIME = 10;
	
	/** New Premium class objects */
	private Premium _premium;

	/** Get Passenger PassID variables */
	private static final String CONSTRUCTOR_PASS_ID = "P:3";
	private static final String NORMAL_PASS_ID = "P:1";
	private static final String UPGRADE_PASS_ID = "J(U):1";
	
	/**
	 * Setup Passenger of premium class.
	 * 
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws PassengerException {
		_premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);
	}

	/**
	 * <p>Message for no seats, not tested</p>
	 * Test method for {@link asgn2Passengers.Premium#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * <p>Confirms upgrades function is working.</p>
	 * Test method for {@link asgn2Passengers.Premium#upgrade()}.
	 * 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		_premium.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		assertEquals(NORMAL_PASS_ID, _premium.getPassID());
		assertEquals(UPGRADE_PASS_ID, _premium.upgrade().getPassID());
		assertEquals(true, _premium.upgrade().isConfirmed());
	}

	/**
	 * <p>Confirms new Business default constructor is working.</p>
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	@Test
	public void testPremiumDefaultConstructor() throws PassengerException {
		Premium _testPremium = new Premium(BOOKING_TIME, DEPARTURE_TIME);
		assertEquals(CONSTRUCTOR_PASS_ID, _testPremium.getPassID());
		assertEquals(true, _testPremium.isNew());
		assertEquals(false, _testPremium.isConfirmed());
		assertEquals(false, _testPremium.isQueued());
		assertEquals(false, _testPremium.isFlown());
		assertEquals(false, _testPremium.isRefused());
		assertEquals(BOOKING_TIME, _testPremium.getBookingTime());
		assertEquals(0, _testPremium.getEnterQueueTime());
		assertEquals(0, _testPremium.getExitQueueTime());
		assertEquals(0, _testPremium.getConfirmationTime());
		assertEquals(DEPARTURE_TIME, _testPremium.getDepartureTime());
		assertEquals(false, _testPremium.wasConfirmed());
		assertEquals(false, _testPremium.wasQueued());
	}

	/**
	 * <p>Protected method.</p>
	 * Test method for {@link asgn2Passengers.Premium#Premium()}.
	 */
	@Test
	public void testPremiumSimpleConstructorDontTest() {
		assertTrue(true);
	}

}
