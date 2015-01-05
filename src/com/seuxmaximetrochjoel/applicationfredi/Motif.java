package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe métier pour les motifs.
 * @author Joël Troch
 */
public class Motif {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long id;
	private String libelle;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe Motif.
	 * @param id ID du motif dans la base de données.
	 * @param libelle Libellé du motif.
	 */
	public Motif(long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	/**
	 * Récupère l'ID du motif dans la base de données.
	 * @return ID du motif.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Récupère le libellé du motif.
	 * @return Libellé du motif.
	 */
	public String getLibelle() {
		return this.libelle;
	}
}
