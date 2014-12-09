package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe responsable de la création et mise à jour de la base de données.
 * Elle hérite de la classe "SQLiteOpenHelper" d'Android.
 * @author Joël Troch
 *
 */
public class BDHelper extends SQLiteOpenHelper {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private final String requeteCreationTableAssociation = "CREATE TABLE association("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nom TEXT NOT NULL, adresse TEXT NOT NULL,"
			+ "ville TEXT NOT NULL, cp TEXT NOT NULL);";
	
	private final String requeteCreationTableDeplacement = "CREATE TABLE deplacement("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date DATE NOT NULL, motif TEXT NOT NULL,"
			+ "intituleTrajet TEXT NOT NULL, nbKm FLOAT NOT NULL, montantPeage FLOAT NOT NULL,"
			+ "montantRepas FLOAT NOT NULL, montantHebergement FLOAT NOT NULL, idAssociation INTEGER NOT NULL,"
			+ "FOREIGN KEY(idAssociation) REFERENCES association(_id));";
	
	private final String requeteCreationTableUtilisateur = "CREATE TABLE utilisateur(nom TEXT PRIMARY KEY NOT NULL,"
			+ "prenom TEXT NOT NULL, adresse TEXT NOT NULL, ville TEXT NOT NULL, cp TEXT NOT NULL,"
			+ "tutoAssociationsFait BOOL NOT NULL, tutoDeplacementsFait BOOL NOT NULL);";
	
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
	 * Surchage de la méthode "onCreate" de la classe "SQLiteOpenHelper" d'Android.
	 * Cette procédure événementielle est appelée lors de la création de la base de données pour la première
	 * utilisation.
	 * @param SQLiteDatabase Base de données concernée.
	 */
	@Override
	public void onCreate(SQLiteDatabase bdd) {
		bdd.execSQL(requeteCreationTableAssociation);
		bdd.execSQL(requeteCreationTableDeplacement);
		bdd.execSQL(requeteCreationTableUtilisateur);
	}
	
	/**
	 * Surchage de la méthode "onUpgrade" de la classe "SQLiteOpenHelper" d'Android.
	 * Cette procédure événementielle est appelée lorsque qu'une mise à jour de la structure de la base de
	 * données est nécessaire. Elle peut être appelée lors d'une mise à jour de l'application.
	 * @param SQLiteDatabase Base de données concernée.
	 * @param ancienneVersion Ancienne version de la base de données.
	 * @param nouvelleVersion Nouvelle version de la base de données.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		// Pas de mise à jour à faire pour le moment.
	}
}
