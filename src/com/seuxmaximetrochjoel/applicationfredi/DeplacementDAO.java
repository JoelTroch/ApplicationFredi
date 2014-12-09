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
 * Classe technique pour les déplacements.
 * @author Joël Troch
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
	 * Ouvre la base de données pour pouvoir effectuer des manipulations.
	 * @param lectureSeule Si vrai, la base de données sera ouverte en mode "Lecture seule". Dans le cas
	 * contraire, elle sera ouverte en mode "Lecture et écriture".
	 * @throws SQLException Erreur si il y a eu un échec lors de l'ouverture.
	 */
	public void open(Boolean lectureSeule) throws SQLException {
		bdd = lectureSeule ? bddHelper.getReadableDatabase() : bddHelper.getWritableDatabase();
	}
	
	/**
	 * Ferme la base de données après avoir effectué des manipulations.
	 */
	public void close() {
		// Cette ligne permet d'effacer le contenu de la table deplacement.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM deplacement;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute un déplacement dans la base de données.
	 * @param dateJour Jour auquel s'est déroulé le déplacement.
	 * @param dateMois Mois auquel s'est déroulé le déplacement.
	 * @param dateAnnee Année auquel s'est déroulé le déplacement.
	 * @param motif Motif du déplacement.
	 * @param intituleTrajet Intitulé du trajet du déplacement.
	 * @param nbKm Nombre de kilomètres effectués lors du déplacement.
	 * @param montantPeage Montant du (des) péage(s) lors du déplacement.
	 * @param montantRepas Montant du (des) repas lors du déplacement.
	 * @param montantHebergement Montant de ou des hébergements lors du déplacement.
	 * @param idAssociation ID de l'association auquel est lié le déplacement.
	 * @return ID du déplacement si la requête est un succès ou -1 si elle a échouée.
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
	 * Supprime tous les déplacements concernant une association dans la base de données.
	 * @param idAssociation ID de l'association concernée.
	 * @return Le nombre de déplacements supprimés.
	 */
	public int deleteAllDeplacementsByIdAssociation(long idAssociation) {
		return bdd.delete("deplacement", "idAssociation = ?", new String[] { String.valueOf(idAssociation) });
	}
	
	/**
	 * Supprime un déplacement dans la base de données.
	 * @param id ID du déplacement à supprimer.
	 * @return 1 si la requête est un succès ou 0 si c'est un échec.
	 */
	public int deleteDeplacementById(long id) {
		return bdd.delete("deplacement", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * Récupère tous les déplacements d'une association enregistrés dans la base de données.
	 * @return Liste des déplacements d'une association enregistrés dans la base de données.
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
	 * Récupère un seul déplacement enregistré dans la base de données.
	 * @param id ID du déplacement à récupérer.
	 * @return Le déplacement si il est présent dans la base de données ou NULL dans le cas contraire.
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
	 * Met à jour un déplacement existant dans la base de données.
	 * @param id ID du déplacement à mettre à jour.
	 * @param dateJour Nouveau jour auquel s'est déroulé le déplacement.
	 * @param dateMois Nouveau mois auquel s'est déroulé le déplacement.
	 * @param dateAnnee Nouvelle année auquel s'est déroulé le déplacement.
	 * @param motif Nouveau motif du déplacement.
	 * @param intituleTrajet Nouvel intitulé du trajet du déplacement.
	 * @param nbKm Nouveau nombre de kilomètres effectués lors du déplacement.
	 * @param montantPeage Nouveau montant du (des) péage(s) lors du déplacement.
	 * @param montantRepas Nouveau montant du (des) repas lors du déplacement.
	 * @param montantHebergement Nouveau montant de ou des hébergements lors du déplacement.
	 * @param idAssociation Nouvel ID de l'association auquel est lié le déplacement (il n'est pas censé changer).
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
