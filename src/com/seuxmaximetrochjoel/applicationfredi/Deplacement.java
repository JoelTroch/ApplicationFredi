package com.seuxmaximetrochjoel.applicationfredi;

public class Deplacement {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long id;
	private long idAssociation;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Deplacement(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
