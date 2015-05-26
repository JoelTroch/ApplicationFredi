package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe technique pour les remboursements.
 * @author Jo�l Troch
 */
@SuppressLint("SimpleDateFormat")
public class RemboursementDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private BDHelper bddHelper;
	private SQLiteDatabase bdd;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe RemboursementDAO.
	 * @param context Contexte Android pour l'objet BDHelper (parent = SQLiteOpenHelper).
	 */
	public RemboursementDAO(Context context) {
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
		// Cette ligne permet d'effacer le contenu de la table Remboursement.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'o� DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM remboursement;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute un remboursement dans la base de donn�es.
	 * @param idAssociation ID de l'association auquel est li� le remboursement.
	 * @param annee Ann�e du remboursement.
	 * @param doitRembourser Doit-on rembourser le d�placement ?
	 * @return ID du remboursement si la requ�te est un succ�s ou -1 si elle a �chou�e.
	 */
	public long createRemboursement(long idAssociation, int annee, long doitRembourser) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("idAssociation", idAssociation);
		valeurs.put("annee", annee);
		valeurs.put("doitRembourser", doitRembourser);
		long resultat = bdd.insert("remboursement", null, valeurs);
		return resultat;
	}
	
	/**
	 * Supprime tous les remboursements concernant une association dans la base de donn�es.
	 * @param idAssociation ID de l'association concern�e.
	 * @return Le nombre de remboursements supprim�s.
	 */
	public int deleteAllRemboursementsByIdAssociation(long idAssociation) {
		return bdd.delete("remboursement", "idAssociation = ?", new String[] { String.valueOf(idAssociation) });
	}
	
	/**
	 * Supprime un remboursement dans la base de donn�es.
	 * @param idAssociation ID de l'association du remboursement � supprimer.
	 * @param annee Annee du remboursement � supprimer.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
	public int deleteRemboursementByIdAssociationAndAnnee(long idAssociation, int annee) {
		return bdd.delete("remboursement", "_id = ? AND annee = ?", new String[] { String.valueOf(idAssociation), String.valueOf(annee) });
	}
	
	/**
	 * R�cup�re tous les remboursements d'une association enregistr�s dans la base de donn�es.
	 * @return Liste des remboursements d'une association enregistr�s dans la base de donn�es.
	 */
	public ArrayList<Remboursement> getAllRemboursementsByIdAssociation(long idAssociation) {
		Cursor curseur = bdd.rawQuery("SELECT * FROM remboursement WHERE idAssociation = ?",
				new String[] { String.valueOf(idAssociation) });
		ArrayList<Remboursement> liste = new ArrayList<Remboursement>();
		curseur.moveToFirst();
		while (!curseur.isAfterLast()) {
			liste.add(cursorToRemboursement(curseur));
			curseur.moveToNext();
		}
		curseur.close();
		return liste;
	}
	
	/**
	 * R�cup�re un seul remboursement enregistr� dans la base de donn�es.
	 * @param id ID du remboursement � r�cup�rer.
	 * @param annee Ann�e du remboursement � r�cup�rer.
	 * @return Le remboursement si il est pr�sent dans la base de donn�es ou NULL dans le cas contraire.
	 */
	public Remboursement getRemboursementByIdAssociationAndAnnee(long id, int annee) {
		// FAIL - J'ai merd� le WHERE id
		Cursor curseur = bdd.rawQuery("SELECT * FROM remboursement WHERE idAssociation = ? AND annee = ?", new String[] { String.valueOf(id), String.valueOf(annee) });
		Remboursement remboursement = null;
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			remboursement = cursorToRemboursement(curseur);
		}
		curseur.close();
		return remboursement;
	}
	
	private Remboursement cursorToRemboursement(Cursor curseur) {
		return new Remboursement(curseur.getLong(0), curseur.getInt(1), curseur.getLong(2));
	}
}
