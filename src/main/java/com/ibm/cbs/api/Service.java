package com.ibm.cbs.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.ibm.cbs.ejb.bean.*;
import com.ibm.cbs.ejb.entities.*;
import com.ibm.json.java.JSONObject;

@Stateless
@Path("/")
public class Service {

	Logger logger = Logger.getLogger(Service.class.getName());
	
	@EJB(name = "Film")
	FilmBean filmBean;
	
	@EJB(name = "Booking")
	BookingBean bookingBean;
	
	@EJB(name = "User")
	UserBean userBean;

	/**
	 * Gets all films with their showtimes
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/films")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response queryFilms() throws Exception {
		logger.log(Level.INFO, "Executing the films API");
		
		Collection<Film> films = filmBean.getAllFilms();
		logger.log(Level.INFO, films.toString());
		Gson gson = new Gson();
		String filmsGson = gson.toJson(films);
				
		return Response.ok(filmsGson).build();
	};
	
	/**
	 * Cancels the booking
	 * 
	 * @param bookingIdstr
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/booking")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response queryCancelBooking(@QueryParam("bookingId") String bookingIdstr) throws Exception {
		logger.log(Level.INFO, "Executing the cancelBooking API");
	
		Long bookingId = Long.parseLong(bookingIdstr);
		Booking booking = bookingBean.deleteBooking(bookingId);
		
		if (booking == null) {
			JsonObject response = Json.createObjectBuilder().add("comment", "Booking not found").build();
			return Response.status(Status.NOT_FOUND).entity(response).build();
		}
		
		return Response.ok().build();
	}
	
	/**
	 * Gets all the bookings for a user (including past ones)
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/bookings")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response queryGetBookings(@QueryParam("userId") Long userId) throws Exception {
		logger.log(Level.INFO, "Executing the getBookings API");
	
		User user = userBean.getUser(userId);
		if (user == null) {
			JsonObject response = Json.createObjectBuilder().add("comment", "User could not be found").build();
			return Response.status(Status.NOT_FOUND).entity(response).build();
		}
		
		Collection<Booking> bookings = bookingBean.getBookings(user);
		
		return Response.ok(bookings).build();
	}
	
	/**
	 * Books a show for a user
	 * 
	 * @param inputJsonObj
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/book")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response queryBook(JSONObject inputJsonObj) throws Exception {
		logger.log(Level.INFO, "Executing the book API");
		
		Integer userIdInt = (Integer) inputJsonObj.get("userId");
		Integer filmIdInt = (Integer) inputJsonObj.get("filmId");
		Long userId = Long.valueOf(userIdInt);
		Long filmId = Long.valueOf(filmIdInt);
		
		User user = userBean.getUser(userId);
		Film film = filmBean.getFilm(filmId);
		if (user == null) {
			JsonObject response = Json.createObjectBuilder().add("comment", "User could not be found").build();
			return Response.status(Status.NOT_FOUND).entity(response).build();
		}
		if (film == null) {
			JsonObject response = Json.createObjectBuilder().add("comment", "Film could not be found").build();
			return Response.status(Status.NOT_FOUND).entity(response).build();
		}
		
		String dateStr = (String) inputJsonObj.get("date");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		java.util.Date date = format.parse(dateStr);
		
		if (date.before(new java.util.Date())) {
			JsonObject response = Json.createObjectBuilder().add("comment", "Date needs to be a future date").build();
			return Response.status(Status.BAD_REQUEST).entity(response).build();
		}
		 
		Booking booking = bookingBean.registerBooking(user, film, new java.sql.Date(date.getTime()));
		
		return Response.ok(booking).build();
	}
	
	/**
	 * Gets the user by user Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
//	@GET
//	@Path("/user")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response queryUser(@QueryParam("userId") Long userId) throws Exception {
//		logger.log(Level.INFO, "Executing the getUser API");
//	
//		User user = userBean.getUser(userId);	
//		
//		if (user == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		
//		return Response.ok(user).build();
//	}
}