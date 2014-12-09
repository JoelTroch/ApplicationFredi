package com.seuxmaximetrochjoel.applicationfredi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UtilisateurDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private SQLiteDatabase bdd;
	private BDHelper bddHelper;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public UtilisateurDAO(Context context) {
		bddHelper = new BDHelper(context);
	}
	
	public void open(Boolean lectureSeule) throws SQLException {
		bdd = lectureSeule ? bddHelper.getReadableDatabase() : bddHelper.getWritableDatabase();
	}
	
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table utilisateur.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM utilisateur;");
		bddHelper.close();
	}
	
	public long createUtilisateur(String nom, String prenom, String adresse, String ville, String cp) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("nom", nom);
		valeurs.put("prenom", prenom);
		valeurs.put("adresse", adresse);
		valeurs.put("ville", ville);
		valeurs.put("cp", cp);
		valeurs.put("tutoAssociationsFait", 0);
		valeurs.put("tutoDeplacementsFait", 0);
		long resultat = bdd.insert("utilisateur", null, valeurs);
		return resultat;
	}
	
	public Utilisateur getUtilisateur() {
		Cursor curseur = bdd.rawQuery("SELECT * FROM utilisateur", null);
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
	
	public long setTutoAssociationsFait() {
		ContentValues valeurs = new ContentValues();
		valeurs.put("tutoAssociationsFait", 1);
		long resultat = bdd.update("utilisateur", valeurs, null, null);
		return resultat;
	}
	
	public long setTutoDeplacementsFait() {
		ContentValues valeurs = new ContentValues();
		valeurs.put("tutoDeplacementsFait", 1);
		long resultat = bdd.update("utilisateur", valeurs, null, null);
		return resultat;
	}
	
	private Utilisateur cursorToUtilisateur(Cursor curseur) {
		return new Utilisateur(curseur.getString(0), curseur.getString(1), curseur.getString(2),
				curseur.getString(3), curseur.getString(4), curseur.getLong(5), curseur.getLong(6));
	}
}
