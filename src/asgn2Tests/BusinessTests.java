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

import asgn2Passengers.Business;
import asgn2Passengers.PassengerException;

/**
 * <p>Junit Test for Business</p>
 * 
 * @author Fong
 * @version 1.0
 */
public class BusinessTests {
	/** Booking Time */
	private static final int BOOKING_TIME = 5;
	
	/** Confirmation Time */
	private static final int CONFIRMATION_TIME = 8;
	
	/** Departure Time */
	private static final int DEPARTURE_TIME = 10;
	
	/** New Business class objects */
	private Business _business;

	/** Get Passenger PassID variables */
	private static final String CONSTRUCTOR_PASS_ID = "J:2";
	private static final String NORMAL_PASS_ID = "J:3";
	private static final String UPGRADE_PASS_ID = "F(U):3";
	
	/**
	 * Setup Passenger of business class.
	 * 
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws PassengerException {
		_business = new Business(BOOKING_TIME, DEPARTURE_TIME);
	}

	/**
	 * <p>Message for no seats, not tested</p>
	 * Test method for {@link asgn2Passengers.Business#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsgDontTest() {
		assertTrue(true);
	}

	/**
	 * <p>Confirms upgrades function is working.</p>
	 * Test method for {@link asgn2Passengers.Business#upgrade()}.
	 * 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		_business.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		assertEquals(NORMAL_PASS_ID, _business.getPassID());
		assertEquals(UPGRADE_PASS_ID, _business.upgrade().getPassID());
		assertEquals(true, _business.upgrade().isConfirmed());
	}

	/**
	 * <p>Confirms new Business default constructor is working.</p>
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	@Test
	public void testBusinessDefaultConstructor() throws PassengerException {
		Business _testBusiness = new Business(BOOKING_TIME, DEPARTURE_TIME);
		assertEquals(CONSTRUCTOR_PASS_ID, _testBusiness.getPassID());
		assertEquals(true, _testBusiness.isNew());
		assertEquals(false, _testBusiness.isConfirmed());
		assertEquals(false, _testBusiness.isQueued());
		assertEquals(false, _testBusiness.isFlown());
		assertEquals(false, _testBusiness.isRefused());
		assertEquals(BOOKING_TIME, _testBusiness.getBookingTime());
		assertEquals(0, _testBusiness.getEnterQueueTime());
		assertEquals(0, _testBusiness.getExitQueueTime());
		assertEquals(0, _testBusiness.getConfirmationTime());
		assertEquals(DEPARTURE_TIME, _testBusiness.getDepartureTime());
		assertEquals(false, _testBusiness.wasConfirmed());
		assertEquals(false, _testBusiness.wasQueued());
	}

	/**
	 * <p>Protected method.</p>
	 * Test method for {@link asgn2Passengers.Business#Business()}.
	 */
	@Test
	public void testBusinessSimpleConstructorDontTest() {
		assertTrue(true);
	}

}
