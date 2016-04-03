package com.ibm.cbs.ejb.entities;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="Booking")
@NamedQueries({ 
	@NamedQuery(name = "findBooking", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"), 
	@NamedQuery(name = "findBookingsForUser", query = "SELECT b FROM Booking b WHERE b.user = :user")
})
public class Booking {
//	private static final long serialVersionUID = 1L;

	@Column(name="bookingId") 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@Column(name="date")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="filmId", referencedColumnName="filmId")
    private Film film;
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName="userId")
    private User user;

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}