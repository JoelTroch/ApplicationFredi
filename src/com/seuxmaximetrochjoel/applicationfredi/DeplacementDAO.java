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

/**
 * Classe technique pour les d�placements.
 * @author Jo�l Troch
 *
 */
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
	
	/**
	 * Constructeur de la classe DeplacementDAO.
	 * @param context Contexte Android pour l'objet BDHelper (parent = SQLiteOpenHelper).
	 */
	public DeplacementDAO(Context context) {
		bddHelper = new BDHelper(context);
	}
	
	/**
	 * Ouvre la base de donn�es pour pouvoir effectuer des manipulations.
	 * @param lectureSeule Si vrai, la base de donn�es sera ouverte en mode "Lecture seule". Dans le cas
	 * contraire, elle sera ouverte en mode "Lecture et �criture".
	 * @throws SQLException Erreur si il y a eu un �chec lors de l'ouverture.
	 */
	public void open(Boolean lectureSeule) throws SQLException {
		bdd = lectureSeule ? bddHelper.getReadableDatabase() : bddHelper.getWritableDatabase();
	}
	
	/**
	 * Ferme la base de donn�es apr�s avoir effectu� des manipulations.
	 */
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table deplacement.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'o� DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM deplacement;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute un d�placement dans la base de donn�es.
	 * @param dateJour Jour auquel s'est d�roul� le d�placement.
	 * @param dateMois Mois auquel s'est d�roul� le d�placement.
	 * @param dateAnnee Ann�e auquel s'est d�roul� le d�placement.
	 * @param motif Motif du d�placement.
	 * @param intituleTrajet Intitul� du trajet du d�placement.
	 * @param nbKm Nombre de kilom�tres effectu�s lors du d�placement.
	 * @param montantPeage Montant du (des) p�age(s) lors du d�placement.
	 * @param montantRepas Montant du (des) repas lors du d�placement.
	 * @param montantHebergement Montant de ou des h�bergements lors du d�placement.
	 * @param idAssociation ID de l'association auquel est li� le d�placement.
	 * @return ID du d�placement si la requ�te est un succ�s ou -1 si elle a �chou�e.
	 */
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
	
	/**
	 * Supprime tous les d�placements concernant une association dans la base de donn�es.
	 * @param idAssociation ID de l'association concern�e.
	 * @return Le nombre de d�placements supprim�s.
	 */
	public int deleteAllDeplacementsByIdAssociation(long idAssociation) {
		return bdd.delete("deplacement", "idAssociation = ?", new String[] { String.valueOf(idAssociation) });
	}
	
	/**
	 * Supprime un d�placement dans la base de donn�es.
	 * @param id ID du d�placement � supprimer.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
	public int deleteDeplacementById(long id) {
		return bdd.delete("deplacement", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * R�cup�re tous les d�placements d'une association enregistr�s dans la base de donn�es.
	 * @return Liste des d�placements d'une association enregistr�s dans la base de donn�es.
	 */
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
	
	/**
	 * R�cup�re un seul d�placement enregistr� dans la base de donn�es.
	 * @param id ID du d�placement � r�cup�rer.
	 * @return Le d�placement si il est pr�sent dans la base de donn�es ou NULL dans le cas contraire.
	 */
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
	
	/**
	 * Met � jour un d�placement existant dans la base de donn�es.
	 * @param id ID du d�placement � mettre � jour.
	 * @param dateJour Nouveau jour auquel s'est d�roul� le d�placement.
	 * @param dateMois Nouveau mois auquel s'est d�roul� le d�placement.
	 * @param dateAnnee Nouvelle ann�e auquel s'est d�roul� le d�placement.
	 * @param motif Nouveau motif du d�placement.
	 * @param intituleTrajet Nouvel intitul� du trajet du d�placement.
	 * @param nbKm Nouveau nombre de kilom�tres effectu�s lors du d�placement.
	 * @param montantPeage Nouveau montant du (des) p�age(s) lors du d�placement.
	 * @param montantRepas Nouveau montant du (des) repas lors du d�placement.
	 * @param montantHebergement Nouveau montant de ou des h�bergements lors du d�placement.
	 * @param idAssociation Nouvel ID de l'association auquel est li� le d�placement (il n'est pas cens� changer).
	 * @return
	 */
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
