package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe technique pour les associations.
 * @author Joël Troch
 */
public class AssociationDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private BDHelper bddHelper;
	private SQLiteDatabase bdd;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe AssociationDAO.
	 * @param context Contexte Android pour l'objet BDHelper (parent = SQLiteOpenHelper).
	 */
	public AssociationDAO(Context context) {
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
		// Cette ligne permet d'effacer le contenu de la table association.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'où DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM association;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute une association dans la base de données.
	 * @param nom Nom de l'association.
	 * @param adresse Adresse de l'association.
	 * @param ville Ville de l'association.
	 * @param cp Code postal de l'association.
	 * @return ID de l'association si la requête est un succès ou -1 si elle a échouée.
	 */
	public long createAssociation(String nom, String adresse, String ville, String cp) {
		ContentValues valeurs = new ContentValues();
		valeurs.put("nom", nom);
		valeurs.put("adresse", adresse);
		valeurs.put("ville", ville);
		valeurs.put("cp", cp);
		long resultat = bdd.insert("association", null, valeurs);
		return resultat;
	}
	
	/**
	 * Supprime une association dans la base de données.
	 * @param id ID de l'association à supprimer.
	 * @return 1 si la requête est un succès ou 0 si c'est un échec.
	 */
	public int deleteAssociationById(long id) {
		return bdd.delete("association", "_id = ?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * Récupère toutes les associations enregistrées dans la base de données.
	 * @return Liste des associations enregistrées dans la base de données.
	 */
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
	
	/**
	 * Récupère une seule association enregistrée dans la base de données.
	 * @param id ID de l'association à récupérer.
	 * @return L'association si elle est présente dans la base de données ou NULL dans le cas contraire.
	 */
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
	
	/**
	 * Met à jour une association existante dans la base de données.
	 * @param id ID de l'association à mettre à jour.
	 * @param nom Nouveau nom de l'association.
	 * @param adresse Nouvelle adresse de l'association.
	 * @param ville Nouvelle ville de l'association.
	 * @param cp Nouveau code postal de l'association.
	 * @return 1 si la requête est un succès ou 0 si c'est un échec.
	 */
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
