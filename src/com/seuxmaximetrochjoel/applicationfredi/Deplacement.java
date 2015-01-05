package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe m�tier pour les d�placements.
 * @author Jo�l Troch
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
	 * @param id ID du d�placement dans la base de donn�es.
	 * @param dateJour Jour auquel s'est d�roul� le d�placement.
	 * @param dateMois Mois auquel s'est d�roul� le d�placement.
	 * @param dateAnnee Ann�e auquel s'est d�roul� le d�placement.
	 * @param motif Motif du d�placement.
	 * @param intituleTrajet Intitul� du trajet du d�placement.
	 * @param nbKm Nombre de kilom�tres parcouru lors du d�placement.
	 * @param montantPeage Montant du (des) p�age(s) lors du d�placement.
	 * @param montantRepas Montant du (des) repas lors du d�placement.
	 * @param montantHebergement Montant de ou des h�bergements lors du d�placement.
	 * @param idAssociation ID de l'association dans la base de donn�es auquel est li� le d�placement.
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
	 * R�cup�re l'ID du d�placement dans la base de donn�es.
	 * @return ID du d�placement.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * R�cup�re le jour auquel s'est d�roul� le d�placement.
	 * @return Jour auquel s'est d�roul� le d�placement.
	 */
	public int getDateJour() {
		return this.dateJour;
	}
	
	/**
	 * R�cup�re le mois auquel s'est d�roul� le d�placement.
	 * @return Mois auquel s'est d�roul� le d�placement.
	 */
	public int getDateMois() {
		return this.dateMois;
	}
	
	/**
	 * R�cup�re l'ann�e auquel s'est d�roul� le d�placement.
	 * @return Ann�e auquel s'est d�roul� le d�placement.
	 */
	public int getDateAnnee() {
		return this.dateAnnee;
	}
	
	/**
	 * R�cup�re le motif.
	 * @return Motif du d�placement.
	 */
	public String getMotif() {
		return this.motif;
	}
	
	/**
	 * R�cup�re l'intitul� du trajet.
	 * @return Intitul� du trajet du d�placement.
	 */
	public String getIntituleTrajet() {
		return this.intituleTrajet;
	}
	
	/**
	 * R�cup�re le nombre de kilom�tres effectu�s.
	 * @return Nombre de kilom�tres effectu�s lors du d�placement.
	 */
	public float getNbKm() {
		return this.nbKm;
	}
	
	/**
	 * R�cup�re le montant du (des) p�age(s).
	 * @return Montant du (des) p�age(s) lors du d�placement.
	 */
	public float getMontantPeage() {
		return this.montantPeage;
	}
	
	/**
	 * R�cup�re le montant du (des) repas.
	 * @return Montant du (des) repas lors du d�placement.
	 */
	public float getMontantRepas() {
		return this.montantRepas;
	}
	
	/**
	 * R�cup�re le montant de ou des h�bergements.
	 * @return Montant de ou des h�bergements lors du d�placement.
	 */
	public float getMontantHebergement() {
		return this.montantHebergement;
	}
	
	/**
	 * R�cup�re l'ID de l'association auquel est li� le d�placement.
	 * @return ID de l'association concern�e.
	 */
	public long getIdAssociation() {
		return this.idAssociation;
	}
}
