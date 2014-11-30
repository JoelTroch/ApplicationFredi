package com.seuxmaximetrochjoel.applicationfredi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DeplacementDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private SQLiteDatabase bdd;
	private BDHelper bddHelper;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	public DeplacementDAO(Context context) {
		bddHelper = new BDHelper(context);
	}
	
	public void open() throws SQLException {
		bdd = bddHelper.getWritableDatabase();
	}
	
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table deplacement.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM deplacement;");
		bddHelper.close();
	}
	
	public long createDeplacement(int dateJour, int dateMois, int dateAnnee, String motif, String intituleTrajet,
			float nbKm, float montantPeage, float montantRepas, float montantHebergement, long idAssociation) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("date", dateAnnee + "-" + dateMois + "-" + dateJour);
		valeurs.put("motif", motif);
		valeurs.put("intituleTrajet", intituleTrajet);
		valeurs.put("nbKm", nbKm);
		valeurs.put("montantPeage", montantPeage);
		valeurs.put("montantRepas", montantRepas);
		valeurs.put("montantHebergement", montantHebergement);
		valeurs.put("idAssociation", idAssociation);
		long resultat = bdd.insert("deplacement", null, valeurs);
		return resultat;
	}
	
	public int deleteAllDeplacementsByIdAssociation(long idAssociation) {
		return bdd.delete("deplacement", "idAssociation = ?", new String[] { String.valueOf(idAssociation) });
	}
	
	public int deleteDeplacementById(long id) {
		return bdd.delete("deplacement", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	public ArrayList<Deplacement> getAllDeplacementsByIdAssociation(long idAssociation) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM deplacement WHERE idAssociation = ?",
				new String[] { String.valueOf(idAssociation) });
		ArrayList<Deplacement> liste = new ArrayList<Deplacement>();
		curseur.moveToFirst();
		while (!curseur.isAfterLast()) {
			liste.add(cursorToDeplacement(curseur));
			curseur.moveToNext();
		}
		curseur.close();
		return liste;
	}
	
	public Deplacement getDeplacementById(long id) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM deplacement WHERE _id = ?", new String[] { String.valueOf(id) });
		Deplacement deplacement = null;
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			deplacement = cursorToDeplacement(curseur);
		}
		curseur.close();
		return deplacement;
	}
	
	public long updateDeplacement(long id, int dateJour, int dateMois, int dateAnnee, String motif, String intituleTrajet,
			float nbKm, float montantPeage, float montantRepas, float montantHebergement, long idAssociation) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("date", dateAnnee + "-" + dateMois + "-" + dateJour);
		valeurs.put("motif", motif);
		valeurs.put("intituleTrajet", intituleTrajet);
		valeurs.put("nbKm", nbKm);
		valeurs.put("montantPeage", montantPeage);
		valeurs.put("montantRepas", montantRepas);
		valeurs.put("montantHebergement", montantHebergement);
		valeurs.put("idAssociation", idAssociation);
		long resultat = bdd.update("deplacement", valeurs, "_id = ?", new String[] { String.valueOf(id) });
		return resultat;
	}
	
	private Deplacement cursorToDeplacement(Cursor curseur) {
		String dateBDD = curseur.getString(1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		dateFormat.setCalendar(c);
		try {
			c.setTime(dateFormat.parse(dateBDD));
		} catch (ParseException e) {
			Log.d("mesMessages", "ParseException !!!");
		}
		return new Deplacement(curseur.getLong(0), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR),
				curseur.getString(2), curseur.getString(3), curseur.getFloat(4), curseur.getFloat(5), curseur.getFloat(6),
				curseur.getFloat(7), curseur.getLong(8));
	}
}
