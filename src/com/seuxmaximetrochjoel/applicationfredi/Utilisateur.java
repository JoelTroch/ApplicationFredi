package com.seuxmaximetrochjoel.applicationfredi;

public class Utilisateur {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private String nom;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Utilisateur(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
}