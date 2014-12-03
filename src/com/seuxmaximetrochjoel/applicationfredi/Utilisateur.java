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
	private long tutoAssociationsFait;
	private long tutoDeplacementsFait;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Utilisateur(String nom, String prenom, String adresse, String ville, String cp,
			long tutoAssociationsFait, long tutoDeplacementsFait) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.ville = ville;
		this.cp = cp;
		this.tutoAssociationsFait = tutoAssociationsFait;
		this.tutoDeplacementsFait = tutoDeplacementsFait;
	}
	
	public long getTutoAssociationsFait() {
		return this.tutoAssociationsFait;
	}
	
	public long getTutoDeplacementsFait() {
		return this.tutoDeplacementsFait;
	}
	
	public void setTutoAssociationsFait() {
		this.tutoAssociationsFait = 1;
	}
	
	public void setTutoDeplacementsFait() {
		this.tutoDeplacementsFait = 1;
	}
}
