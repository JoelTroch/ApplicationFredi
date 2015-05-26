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
 * @author Joël Troch
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
		// Cette ligne permet d'effacer le contenu de la table Remboursement.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM remboursement;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute un remboursement dans la base de données.
	 * @param idAssociation ID de l'association auquel est lié le remboursement.
	 * @param annee Année du remboursement.
	 * @param doitRembourser Doit-on rembourser le déplacement ?
	 * @return ID du remboursement si la requête est un succès ou -1 si elle a échouée.
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
	 * Supprime tous les remboursements concernant une association dans la base de données.
	 * @param idAssociation ID de l'association concernée.
	 * @return Le nombre de remboursements supprimés.
	 */
	public int deleteAllRemboursementsByIdAssociation(long idAssociation) {
		return bdd.delete("remboursement", "idAssociation = ?", new String[] { String.valueOf(idAssociation) });
	}
	
	/**
	 * Supprime un remboursement dans la base de données.
	 * @param idAssociation ID de l'association du remboursement à supprimer.
	 * @param annee Annee du remboursement à supprimer.
	 * @return 1 si la requête est un succès ou 0 si c'est un échec.
	 */
	public int deleteRemboursementByIdAssociationAndAnnee(long idAssociation, int annee) {
		return bdd.delete("remboursement", "_id = ? AND annee = ?", new String[] { String.valueOf(idAssociation), String.valueOf(annee) });
	}
	
	/**
	 * Récupère tous les remboursements d'une association enregistrés dans la base de données.
	 * @return Liste des remboursements d'une association enregistrés dans la base de données.
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
	 * Récupère un seul remboursement enregistré dans la base de données.
	 * @param id ID du remboursement à récupérer.
	 * @param annee Année du remboursement à récupérer.
	 * @return Le remboursement si il est présent dans la base de données ou NULL dans le cas contraire.
	 */
	public Remboursement getRemboursementByIdAssociationAndAnnee(long id, int annee) {
		// FAIL - J'ai merdé le WHERE id
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
