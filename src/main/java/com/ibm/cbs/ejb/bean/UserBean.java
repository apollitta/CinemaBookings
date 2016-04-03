package com.ibm.cbs.ejb.bean;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ibm.cbs.ejb.entities.Film;
import com.ibm.cbs.ejb.entities.User;

@Stateless(name = "User")
@LocalBean
public class UserBean {

	Logger logger = Logger.getLogger(FilmBean.class.getName());
	
	@PersistenceContext(name = "CinemaBookingSystem")
	private EntityManager entityMgr;

	public User getUser(Long userId) {
		logger.log(Level.INFO, "film bean / getUser - start.");
		
		Query findUser = entityMgr.createNamedQuery("findUser");
		findUser.setParameter("userId", userId);
		Collection<User> users = findUser.getResultList();
		
		if (!users.isEmpty()) {
			User user = (User) users.toArray()[0];
			return user;
		}
		
		return null;
	}
}
