package com.seuxmaximetrochjoel.applicationfredi;


public class Deplacement {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private long id;
	private int dateJour;
	private int dateMois;
	private int dateAnnee;
	private String motif;
	private String intituleTrajet;
	private float nbKm;
	private float montantPeage;
	private float montantRepas;
	private float montantHebergement;
	private long idAssociation;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public Deplacement(long id, int dateJour, int dateMois, int dateAnnee, String motif, String intituleTrajet,
			float nbKm, float montantPeage, float montantRepas, float montantHebergement) {
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
		this.idAssociation = -1;
	}
	
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
	
	public long getId() {
		return this.id;
	}
	
	public int getDateJour() {
		return this.dateJour;
	}
	
	public int getDateMois() {
		return this.dateMois;
	}
	
	public int getDateAnnee() {
		return this.dateAnnee;
	}
	
	public String getMotif() {
		return this.motif;
	}
	
	public String getIntituleTrajet() {
		return this.intituleTrajet;
	}
	
	public float getNbKm() {
		return this.nbKm;
	}
	
	public float getMontantPeage() {
		return this.montantPeage;
	}
	
	public float getMontantRepas() {
		return this.montantRepas;
	}
	
	public float getMontantHebergement() {
		return this.montantHebergement;
	}
	
	public long getIdAssociation() {
		return this.idAssociation;
	}
}
