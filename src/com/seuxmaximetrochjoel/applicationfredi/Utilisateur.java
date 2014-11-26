package com.seuxmaximetrochjoel.applicationfredi;

public class Utilisateur {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	@SuppressWarnings("unused")
	private String nom;
	@SuppressWarnings("unused")
	private String prenom;
	@SuppressWarnings("unused")
	private String adresse;
	@SuppressWarnings("unused")
	private String ville;
	@SuppressWarnings("unused")
	private String cp;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Utilisateur(String nom, String prenom, String adresse, String ville, String cp) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.ville = ville;
		this.cp = cp;
	}
}
