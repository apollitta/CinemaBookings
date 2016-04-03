package com.ibm.cbs.ejb.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="User")
@NamedQueries({ 
	@NamedQuery(name = "findUser", query = "SELECT u FROM User u WHERE u.userId = :userId"), 
})
public class User {
//	private static final long serialVersionUID = 1L;
	
	@Column(name="userId") 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name="fullName")
	private String fullName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}