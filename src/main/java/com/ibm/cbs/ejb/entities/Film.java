package com.ibm.cbs.ejb.entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="Film")
@NamedQueries({ 
	@NamedQuery(name = "findFilm", query = "SELECT f FROM Film f WHERE f.filmId = :filmId"),
	@NamedQuery(name = "findAllFilms", query = "SELECT f FROM Film f")
})
public class Film implements Serializable {
//	private static final long serialVersionUID = 1L;
	
	@Column(name="filmId") 
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long filmId;

	@Column(name="title") 
	private String title;

	@Column(name="showtime")
	private Time showtime;
	
	public Long getFilmId() {
		return filmId;
	}
	
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Time getShowtime() {
		return showtime;
	}

	public void setShowtime(Time showtime) {
		this.showtime = showtime;
	}
}