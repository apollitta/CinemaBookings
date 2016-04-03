package com.ibm.cbs.ejb.bean;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ibm.cbs.ejb.entities.Booking;
import com.ibm.cbs.ejb.entities.Film;
import com.ibm.cbs.ejb.entities.User;

@Stateless(name = "Booking")
@LocalBean
public class BookingBean {

	Logger logger = Logger.getLogger(FilmBean.class.getName());
	
	@PersistenceContext(name = "CinemaBookings")
	private EntityManager entityMgr;
	
	public Booking registerBooking(User user, Film film, java.sql.Date date) {
		
		logger.log(Level.INFO, "approval bean / Register Approval - start."); 

		Booking booking = new Booking();
		booking.setDate(date);
		booking.setUser(user);
		booking.setFilm(film);
		booking.setBookingId(2L);
//		
		entityMgr.persist(booking);
		logger.log(Level.INFO, "approval bean / Register Approval - finish.");
		return booking;
	} 
	
	public Booking deleteBooking(Long bookingId) {
		
		Booking booking = getBooking(bookingId);
		
		if (booking != null) {
			entityMgr.remove(booking);
		}
		
		return booking;
	}
	
	public Booking getBooking(Long bookingId) {
		
		Query findBooking = entityMgr.createNamedQuery("findBooking");
		findBooking.setParameter("bookingId", bookingId);
		Collection<Booking> bookings = findBooking.getResultList();
		
		if (!bookings.isEmpty()) {
			Booking user = (Booking) bookings.toArray()[0];
			return user;
		}
		
		return null;
	}
	
	public Collection<Booking> getBookings(User user) {
		
		Query findBookingsForUser = entityMgr.createNamedQuery("findBookingsForUser");
		findBookingsForUser.setParameter("user", user);
		Collection<Booking> bookings = findBookingsForUser.getResultList();
		
		return bookings;
	}
}
