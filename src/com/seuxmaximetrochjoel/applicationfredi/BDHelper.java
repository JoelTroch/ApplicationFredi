package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private final String requeteCreationTableAssociation = "CREATE TABLE association(_id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, nom TEXT NOT NULL);";
	private final String requeteCreationTableUtilisateur = "CREATE TABLE utilisateur(nom TEXT PRIMARY KEY NOT NULL);";
	
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
		bdd.execSQL(requeteCreationTableUtilisateur);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		/* TODO : Si on a le temps, faire en sorte de conserver les donn�es lors d'une mise � jour de la base
		 * de donn�es. Ca serait frustant pour l'utilisateur de perdre ses donn�es � cause de �a.
		 */
		bdd.execSQL("DROP TABLE association;");
		bdd.execSQL("DROP TABLE utilisateur;");
		onCreate(bdd);
	}
}
