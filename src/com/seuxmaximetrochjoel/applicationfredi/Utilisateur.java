package com.seuxmaximetrochjoel.applicationfredi;

public class Utilisateur {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private String nom;
	private String prenom;
	private String adresse;
	private String ville;
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
	
	public String getNom() {
		return this.nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
}
