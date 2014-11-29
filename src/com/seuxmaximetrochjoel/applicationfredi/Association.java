package com.seuxmaximetrochjoel.applicationfredi;

public class Association {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long id;
	private String nom;
	private String adresse;
	private String ville;
	private String cp;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Association(long id, String nom, String adresse, String ville, String cp) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.ville = ville;
		this.cp = cp;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getAdresse() {
		return this.adresse;
	}
	
	public String getVille() {
		return this.ville;
	}
	
	public String getCP() {
		return this.cp;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public void setCP(String cp) {
		this.cp = cp;
	}
}
