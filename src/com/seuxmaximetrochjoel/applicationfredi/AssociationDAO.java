package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AssociationDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private SQLiteDatabase bdd;
	private BDHelper bddHelper;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public AssociationDAO(Context context) {
		bddHelper = new BDHelper(context);
	}
	
	public void open(Boolean lectureSeule) throws SQLException {
		bdd = lectureSeule ? bddHelper.getReadableDatabase() : bddHelper.getWritableDatabase();
	}
	
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table association.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM association;");
		bddHelper.close();
	}
	
	public long createAssociation(String nom, String adresse, String ville, String cp) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("nom", nom);
		valeurs.put("adresse", adresse);
		valeurs.put("ville", ville);
		valeurs.put("cp", cp);
		long resultat = bdd.insert("association", null, valeurs);
		return resultat;
	}
	
	public int deleteAssociationById(long id) {
		return bdd.delete("association", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	public ArrayList<Association> getAllAssociations() {
		Cursor curseur = bdd.rawQuery("SELECT * FROM association", null);
		ArrayList<Association> liste = new ArrayList<Association>();
		curseur.moveToFirst();
		while (!curseur.isAfterLast()) {
			liste.add(cursorToAssociation(curseur));
			curseur.moveToNext();
		}
		curseur.close();
		return liste;
	}
	
	public Association getAssociationById(long id) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM association WHERE _id = ?", new String[] { String.valueOf(id) });
		Association association = null;
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			association = cursorToAssociation(curseur);
		}
		curseur.close();
		return association;
	}
	
	public Association getAssociationByName(String nom) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM association WHERE nom = ?", new String[] { nom });
		Association association = null;
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			association = cursorToAssociation(curseur);
		}
		curseur.close();
		return association;
	}
	
	public long updateAssociation(long id, String nom, String adresse, String ville, String cp) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("nom", nom);
		valeurs.put("adresse", adresse);
		valeurs.put("ville", ville);
		valeurs.put("cp", cp);
		long resultat = bdd.update("association", valeurs, "_id = ?", new String[] { String.valueOf(id) });
		return resultat;
	}
	
	private Association cursorToAssociation(Cursor curseur) {
		return new Association(curseur.getLong(0), curseur.getString(1), curseur.getString(2), curseur.getString(3), curseur.getString(4));
	}
}
