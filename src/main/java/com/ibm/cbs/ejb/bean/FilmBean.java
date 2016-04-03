package com.ibm.cbs.ejb.bean;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ibm.cbs.ejb.entities.Booking;
import com.ibm.cbs.ejb.entities.Film;

@Stateless(name = "Film")
@LocalBean
public class FilmBean {

	Logger logger = Logger.getLogger(FilmBean.class.getName());
	
	@PersistenceContext(name = "CinemaBookingSystem")
	private EntityManager entityMgr;
	
	public Film getFilm(Long filmId) {
		logger.log(Level.INFO, "film bean / getFilm - start.");
		
		Query findFilm = entityMgr.createNamedQuery("findFilm");
		findFilm.setParameter("filmId", filmId);
		Collection<Film> films = findFilm.getResultList();

		if (!films.isEmpty()) {
			Film film = (Film) films.toArray()[0];
			return film;
		} 		
		
		return null;
	}
	
	public Collection<Film> getAllFilms() {
		
		logger.log(Level.INFO, "film bean / getAllFilms - start.");
		logger.log(Level.INFO, "em :: " + entityMgr);
		
		Query findAllFilms = entityMgr.createNamedQuery("findAllFilms");
		Collection<Film> films = findAllFilms.getResultList();
		logger.log(Level.INFO, films.toString());
		
		return films;
	}
}
