package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {
	
	private final String requeteCreationTableUtilisateur = "CREATE TABLE utilisateur(nom VARCHAR(32) NOT NULL);";
	
	public BDHelper(Context context) {
		super(context, "appliFrediBDD.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase bdd) {
		bdd.execSQL(requeteCreationTableUtilisateur);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		/* TODO : Si on a le temps, faire en sorte de conserver les données lors d'une mise à jour de la base
		 * de données. Ca serait frustant pour l'utilisateur de perdre ses données à cause de ça.
		 */
		onCreate(bdd);
	}
}
