package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe m�tier pour les associations.
 * @author Jo�l Troch
 *
 */
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
	
	/**
	 * Constructeur de la classe Association.
	 * @param id ID de l'association dans la base de donn�es.
	 * @param nom Nom de l'association.
	 * @param adresse Adresse de l'association.
	 * @param ville Ville de l'association.
	 * @param cp Code postal de l'association.
	 */
	public Association(long id, String nom, String adresse, String ville, String cp) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.ville = ville;
		this.cp = cp;
	}
	
	/**
	 * R�cup�re l'ID de l'association dans la base de donn�es.
	 * @return ID de l'association.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * R�cup�re le nom.
	 * @return Nom de l'association.
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * R�cup�re l'adresse.
	 * @return Adresse de l'association.
	 */
	public String getAdresse() {
		return this.adresse;
	}
	
	/**
	 * R�cup�re la ville.
	 * @return Ville de l'association.
	 */
	public String getVille() {
		return this.ville;
	}
	
	/**
	 * R�cup�re le code postal.
	 * @return Code postal de l'association.
	 */
	public String getCP() {
		return this.cp;
	}
}
