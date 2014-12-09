package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe métier pour les associations.
 * @author Joël Troch
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
	 * @param id ID de l'association dans la base de données.
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
	 * Récupère l'ID de l'association dans la base de données.
	 * @return ID de l'association.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Récupère le nom.
	 * @return Nom de l'association.
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Récupère l'adresse.
	 * @return Adresse de l'association.
	 */
	public String getAdresse() {
		return this.adresse;
	}
	
	/**
	 * Récupère la ville.
	 * @return Ville de l'association.
	 */
	public String getVille() {
		return this.ville;
	}
	
	/**
	 * Récupère le code postal.
	 * @return Code postal de l'association.
	 */
	public String getCP() {
		return this.cp;
	}
}
