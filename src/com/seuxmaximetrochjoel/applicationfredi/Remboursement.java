package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe m�tier pour les remboursements.
 * @author Jo�l Troch
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
	 * @param idAssociation ID de l'association dans la base de donn�es auquel est li� le remboursement.
	 * @param annee Ann�e du remboursement.
	 * @param doitRembourser Doit-on rembourser pour cette ann�e ?
	 */
	public Remboursement(long idAssociation, int annee, long doitRembourser) {
		this.idAssociation = idAssociation;
		this.annee = annee;
		this.doitRembourser = doitRembourser;
	}
	
	/**
	 * R�cup�re l'ID de l'association du remboursement dans la base de donn�es.
	 * @return ID de l'association du remboursement.
	 */
	public long getIdAssociation() {
		return this.idAssociation;
	}
	
	/**
	 * R�cup�re l'ann�e du remboursement.
	 * @return Ann�e du remboursement.
	 */
	public int getAnnee() {
		return this.annee;
	}
	
	/**
	 * R�cup�re si le remboursement doit �tre rembours� pour cette ann�e.
	 * @return Doit effectuer le remboursement.
	 */
	public long getDoitRembourser() {
		return this.doitRembourser;
	}
}
