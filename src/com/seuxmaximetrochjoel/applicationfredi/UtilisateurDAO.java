package com.seuxmaximetrochjoel.applicationfredi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UtilisateurDAO {
	
	private SQLiteDatabase bdd;
	private BDHelper bddHelper;
	
	public UtilisateurDAO(Context context) {
		bddHelper = new BDHelper(context);
	}
	
	public void open() throws SQLException {
		bdd = bddHelper.getWritableDatabase();
	}
	
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table utilisateur.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM utilisateur;");
		bddHelper.close();
	}
	
	public void createUtilisateur(String nom) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("nom", nom);
		bdd.insert("utilisateur", null, valeurs);
	}
	
	public Utilisateur getUtilisateur() {
		// LIMIT 1 recupère que le premier enregistrement (il n'est pas censé en avoir d'autres)
		Cursor curseur = bdd.rawQuery("SELECT * FROM utilisateur LIMIT 1", null);
		// On assume par défaut que l'utilisateur ne s'est pas encore enregistré
		Utilisateur utilisateur = null;
		// Il y a un enregistrement, alors on le prend en compte
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			utilisateur = cursorToUtilisateur(curseur);
		}
		curseur.close();
		return utilisateur;
	}
	
	private Utilisateur cursorToUtilisateur(Cursor curseur) {
		return new Utilisateur(curseur.getString(0));
	}
}
