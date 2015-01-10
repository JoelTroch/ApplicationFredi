package com.seuxmaximetrochjoel.applicationfredi;

/**
 * Classe m�tier pour les motifs.
 * @author Jo�l Troch
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
	 * @param id ID du motif dans la base de donn�es.
	 * @param libelle Libell� du motif.
	 */
	public Motif(long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	/**
	 * R�cup�re l'ID du motif dans la base de donn�es.
	 * @return ID du motif.
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * R�cup�re le libell� du motif.
	 * @return Libell� du motif.
	 */
	public String getLibelle() {
		return this.libelle;
	}
}
