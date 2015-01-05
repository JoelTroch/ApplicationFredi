package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe métier pour les déplacements.
 * @author Joël Troch
 */
public class Deplacement {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long id;
	private int dateJour, dateMois, dateAnnee;
	private String motif, intituleTrajet;
	private float nbKm, montantPeage, montantRepas, montantHebergement;
	private long idAssociation;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe Deplacement.
	 * @param id ID du déplacement dans la base de données.
	 * @param dateJour Jour auquel s'est déroulé le déplacement.
	 * @param dateMois Mois auquel s'est déroulé le déplacement.
	 * @param dateAnnee Année auquel s'est déroulé le déplacement.
	 * @param motif Motif du déplacement.
	 * @param intituleTrajet Intitulé du trajet du déplacement.
	 * @param nbKm Nombre de kilomètres parcouru lors du déplacement.
	 * @param montantPeage Montant du (des) péage(s) lors du déplacement.
	 * @param montantRepas Montant du (des) repas lors du déplacement.
	 * @param montantHebergement Montant de ou des hébergements lors du déplacement.
	 * @param idAssociation ID de l'association dans la base de données auquel est lié le déplacement.
	 */
	public Deplacement(long id, int dateJour, int dateMois, int dateAnnee, String motif, String intituleTrajet,
			float nbKm, float montantPeage, float montantRepas, float montantHebergement, long idAssociation) {
		this.id = id;
		this.dateJour = dateJour;
		this.dateMois = dateMois;
		this.dateAnnee = dateAnnee;
		this.motif = motif;
		this.intituleTrajet = intituleTrajet;
		this.nbKm = nbKm;
		this.montantPeage = montantPeage;
		this.montantRepas = montantRepas;
		this.montantHebergement = montantHebergement;
		this.idAssociation = idAssociation;
	}
	
	/**
	 * Récupère l'ID du déplacement dans la base de données.
	 * @return ID du déplacement.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Récupère le jour auquel s'est déroulé le déplacement.
	 * @return Jour auquel s'est déroulé le déplacement.
	 */
	public int getDateJour() {
		return this.dateJour;
	}
	
	/**
	 * Récupère le mois auquel s'est déroulé le déplacement.
	 * @return Mois auquel s'est déroulé le déplacement.
	 */
	public int getDateMois() {
		return this.dateMois;
	}
	
	/**
	 * Récupère l'année auquel s'est déroulé le déplacement.
	 * @return Année auquel s'est déroulé le déplacement.
	 */
	public int getDateAnnee() {
		return this.dateAnnee;
	}
	
	/**
	 * Récupère le motif.
	 * @return Motif du déplacement.
	 */
	public String getMotif() {
		return this.motif;
	}
	
	/**
	 * Récupère l'intitulé du trajet.
	 * @return Intitulé du trajet du déplacement.
	 */
	public String getIntituleTrajet() {
		return this.intituleTrajet;
	}
	
	/**
	 * Récupère le nombre de kilomètres effectués.
	 * @return Nombre de kilomètres effectués lors du déplacement.
	 */
	public float getNbKm() {
		return this.nbKm;
	}
	
	/**
	 * Récupère le montant du (des) péage(s).
	 * @return Montant du (des) péage(s) lors du déplacement.
	 */
	public float getMontantPeage() {
		return this.montantPeage;
	}
	
	/**
	 * Récupère le montant du (des) repas.
	 * @return Montant du (des) repas lors du déplacement.
	 */
	public float getMontantRepas() {
		return this.montantRepas;
	}
	
	/**
	 * Récupère le montant de ou des hébergements.
	 * @return Montant de ou des hébergements lors du déplacement.
	 */
	public float getMontantHebergement() {
		return this.montantHebergement;
	}
	
	/**
	 * Récupère l'ID de l'association auquel est lié le déplacement.
	 * @return ID de l'association concernée.
	 */
	public long getIdAssociation() {
		return this.idAssociation;
	}
}
