package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private final String requeteCreationTableAssociation = "CREATE TABLE association(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nom TEXT NOT NULL);";
	private final String requeteCreationTableDeplacement = "CREATE TABLE deplacement(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date DATE NOT NULL, motif TEXT NOT NULL, intituleTrajet TEXT NOT NULL, nbKm FLOAT NOT NULL, montantPeage FLOAT NOT NULL, montantRepas FLOAT NOT NULL, montantHebergement FLOAT NOT NULL, idAssociation INTEGER NOT NULL, FOREIGN KEY(idAssociation) REFERENCES association(_id));";
	private final String requeteCreationTableUtilisateur = "CREATE TABLE utilisateur(nom TEXT PRIMARY KEY NOT NULL, prenom TEXT NOT NULL, adresse TEXT NOT NULL, ville TEXT NOT NULL, cp TEXT NOT NULL);";
	
	// ====================================================================================================
	// CONSTRUCTEUR
	// ====================================================================================================
	
	public BDHelper(Context context) {
		super(context, "appliFrediBDD.db", null, 1);
	}
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	public void onCreate(SQLiteDatabase bdd) {
		bdd.execSQL(requeteCreationTableAssociation);
		bdd.execSQL(requeteCreationTableDeplacement);
		bdd.execSQL(requeteCreationTableUtilisateur);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		/* TODO : Si on a le temps, faire en sorte de conserver les données lors d'une mise à jour de la base
		 * de données. Ca serait frustant pour l'utilisateur de perdre ses données à cause de ça.
		 */
		bdd.execSQL("DROP TABLE deplacement;");
		bdd.execSQL("DROP TABLE association;");
		bdd.execSQL("DROP TABLE utilisateur;");
		onCreate(bdd);
	}
}
