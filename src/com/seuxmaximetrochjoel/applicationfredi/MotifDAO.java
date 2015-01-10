package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe technique pour les motifs.
 * @author Jo�l Troch
 */
public class MotifDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private BDHelper bddHelper;
	private SQLiteDatabase bdd;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe MotifDAO.
	 * @param context Contexte Android pour l'objet BDHelper (parent = SQLiteOpenHelper).
	 */
	public MotifDAO(Context context) {
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
		// Cette ligne permet d'effacer le contenu de la table motif.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'o� DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM motif;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute un motif dans la base de donn�es.
	 * @param motif Libell� du motif.
	 * @return ID du motif si la requ�te est un succ�s ou -1 si elle a �chou�e.
	 */
	public long createMotif(String libelle) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("libelle", libelle);
		long resultat = bdd.insert("motif", null, valeurs);
		return resultat;
	}
	
	/**
	 * Supprime un motif dans la base de donn�es.
	 * @param id ID du motif � supprimer.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
	public int deleteMotifById(long id) {
		return bdd.delete("motif", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * R�cup�re tous les motifs enregistr�s dans la base de donn�es.
	 * @return Liste des motifs enregistr�s dans la base de donn�es.
	 */
	public ArrayList<Motif> getAllMotifs() {
		Cursor curseur = bdd.rawQuery("SELECT * FROM motif", null);
		ArrayList<Motif> liste = new ArrayList<Motif>();
		curseur.moveToFirst();
		while (!curseur.isAfterLast()) {
			liste.add(cursorToMotif(curseur));
			curseur.moveToNext();
		}
		curseur.close();
		return liste;
	}
	
	/**
	 * R�cup�re un seul motif enregistr� dans la base de donn�es.
	 * @param id ID du motif � r�cup�rer.
	 * @return Le motif si il est pr�sent dans la base de donn�es ou NULL dans le cas contraire.
	 */
	public Motif getMotifById(long id) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM motif WHERE _id = ?", new String[] { String.valueOf(id) });
		Motif motif = null;
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			motif = cursorToMotif(curseur);
		}
		curseur.close();
		return motif;
	}
	
	/**
	 * Met � jour un motif existant dans la base de donn�es.
	 * @param id ID du motif � mettre � jour.
	 * @param libelle Nouveau libell� du motif.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
	public long updateMotif(long id, String libelle) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("libelle", libelle);
		long resultat = bdd.update("motif", valeurs, "_id = ?", new String[] { String.valueOf(id) });
		return resultat;
	}
	
	private Motif cursorToMotif(Cursor curseur) {
		return new Motif(curseur.getLong(0), curseur.getString(1));
	}
}
