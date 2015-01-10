package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe responsable de la cr�ation et mise � jour de la base de donn�es.
 * Elle h�rite de la classe "SQLiteOpenHelper" d'Android.
 * @author Jo�l Troch
 */
public class BDHelper extends SQLiteOpenHelper {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private final String requeteCreationTableAssociation = "CREATE TABLE association("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "nom TEXT NOT NULL, "
			+ "adresse TEXT NOT NULL, "
			+ "ville TEXT NOT NULL, "
			+ "cp TEXT NOT NULL);";
	
	private final String requeteCreationTableDeplacement = "CREATE TABLE deplacement("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "date DATE NOT NULL, "
			+ "motif TEXT NOT NULL, "
			+ "intituleTrajet TEXT NOT NULL, "
			+ "nbKm FLOAT NOT NULL, "
			+ "montantPeage FLOAT NOT NULL, "
			+ "montantRepas FLOAT NOT NULL, "
			+ "montantHebergement FLOAT NOT NULL, "
			+ "idAssociation INTEGER NOT NULL, "
			+ "FOREIGN KEY(idAssociation) REFERENCES association(_id));";
	
	private final String requeteCreationTableMotif = "CREATE TABLE motif("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "libelle TEXT NOT NULL);";
	
	private final String requeteCreationTableUtilisateur = "CREATE TABLE utilisateur("
			+ "nom TEXT PRIMARY KEY NOT NULL, "
			+ "prenom TEXT NOT NULL, "
			+ "adresse TEXT NOT NULL, "
			+ "ville TEXT NOT NULL, "
			+ "cp TEXT NOT NULL, "
			+ "tutoAssociationsFait BOOL NOT NULL, "
			+ "tutoDeplacementsFait BOOL NOT NULL, "
			+ "tutoMotifsFait BOOL NOT NULL);";
	
	// ====================================================================================================
	// CONSTRUCTEUR
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe BDHelper.
	 * @param context Contexte Android.
	 */
	public BDHelper(Context context) {
		super(context, "appliFrediBDD.db", null, 1);
	}
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Surchage de la m�thode "onCreate" de la classe "SQLiteOpenHelper" d'Android.
	 * Cette proc�dure �v�nementielle est appel�e lors de la cr�ation de la base de donn�es pour la premi�re
	 * utilisation.
	 * @param SQLiteDatabase Base de donn�es concern�e.
	 */
	@Override
	public void onCreate(SQLiteDatabase bdd) {
		bdd.execSQL(requeteCreationTableAssociation);
		bdd.execSQL(requeteCreationTableDeplacement);
		bdd.execSQL(requeteCreationTableMotif);
		bdd.execSQL(requeteCreationTableUtilisateur);
		
		// Cr�ation des motifs par d�faut
		bdd.execSQL("INSERT INTO motif VALUES(1, 'Voyage');");
		bdd.execSQL("INSERT INTO motif VALUES(2, 'Comp�tition');");
		bdd.execSQL("INSERT INTO motif VALUES(3, 'Entra�nement');");
		bdd.execSQL("INSERT INTO motif VALUES(4, 'Rencontre amicale');");
		bdd.execSQL("INSERT INTO motif VALUES(5, 'R�union');");
	}
	
	/**
	 * Surchage de la m�thode "onUpgrade" de la classe "SQLiteOpenHelper" d'Android.
	 * Cette proc�dure �v�nementielle est appel�e lorsque qu'une mise � jour de la structure de la base de
	 * donn�es est n�cessaire. Elle peut �tre appel�e lors d'une mise � jour de l'application.
	 * @param SQLiteDatabase Base de donn�es concern�e.
	 * @param ancienneVersion Ancienne version de la base de donn�es.
	 * @param nouvelleVersion Nouvelle version de la base de donn�es.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		// Pas de mise � jour � faire pour le moment.
	}
}
