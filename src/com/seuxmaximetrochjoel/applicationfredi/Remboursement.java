package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe métier pour les remboursements.
 * @author Joël Troch
 */
public class Remboursement {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long idAssociation;
	private int annee;
	private long doitRembourser;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe Remboursement.
	 * @param idAssociation ID de l'association dans la base de données auquel est lié le remboursement.
	 * @param annee Année du remboursement.
	 * @param doitRembourser Doit-on rembourser pour cette année ?
	 */
	public Remboursement(long idAssociation, int annee, long doitRembourser) {
		this.idAssociation = idAssociation;
		this.annee = annee;
		this.doitRembourser = doitRembourser;
	}
	
	/**
	 * Récupère l'ID de l'association du remboursement dans la base de données.
	 * @return ID de l'association du remboursement.
	 */
	public long getIdAssociation() {
		return this.idAssociation;
	}
	
	/**
	 * Récupère l'année du remboursement.
	 * @return Année du remboursement.
	 */
	public int getAnnee() {
		return this.annee;
	}
	
	/**
	 * Récupère si le remboursement doit être remboursé pour cette année.
	 * @return Doit effectuer le remboursement.
	 */
	public long getDoitRembourser() {
		return this.doitRembourser;
	}
}
