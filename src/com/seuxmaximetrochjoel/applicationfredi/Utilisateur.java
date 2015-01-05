package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe métier pour l'utilisateur.
 * @author Joël Troch
 */
public class Utilisateur {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	@SuppressWarnings("unused")
	private String nom, prenom, adresse, ville, cp;
	private long tutoAssociationsFait, tutoDeplacementsFait;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe Utilisateur.
	 * @param nom Nom de l'utilisateur.
	 * @param prenom Prénom de l'utilisateur.
	 * @param adresse Adresse de l'utilisateur.
	 * @param ville Ville de l'utilisateur.
	 * @param cp Code postal de l'utilisateur.
	 * @param tutoAssociationsFait Etat du tutoriel sur la manipulation des associations.
	 * @param tutoDeplacementsFait Etat du tutoriel sur la manipulation des déplacements.
	 */
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
	
	/**
	 * Récupère l'état du tutoriel sur la manipulation des associations.
	 * @return 1 si le tutoriel est fait, 0 dans le cas contraire.
	 */
	public long getTutoAssociationsFait() {
		return this.tutoAssociationsFait;
	}
	
	/**
	 * Récupère l'état du tutoriel sur la manipulation des déplacements.
	 * @return 1 si le tutoriel est fait, 0 dans le cas contraire.
	 */
	public long getTutoDeplacementsFait() {
		return this.tutoDeplacementsFait;
	}
}
