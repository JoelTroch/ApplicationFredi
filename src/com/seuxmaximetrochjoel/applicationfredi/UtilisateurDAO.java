package com.seuxmaximetrochjoel.applicationfredi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe technique pour l'utilisateur.
 * @author Jo�l Troch
 */
public class UtilisateurDAO {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private BDHelper bddHelper;
	private SQLiteDatabase bdd;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe UtilisateurDAO.
	 * @param context Contexte Android pour l'objet BDHelper (parent = SQLiteOpenHelper).
	 */
	public UtilisateurDAO(Context context) {
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
		// Cette ligne permet d'effacer le contenu de la table utilisateur.
		// A utiliser uniquement pour debug.
		// "TRUNCATE TABLE" n'existe pas sous SQLite (d'o� DELETE FROM sans WHERE)
		//bdd.execSQL("DELETE FROM utilisateur;");
		bddHelper.close();
	}
	
	/**
	 * Ajoute l'utilisateur dans la base de donn�es.
	 * @param nom Nom de l'utilisateur.
	 * @param prenom Pr�nom de l'utilisateur.
	 * @param adresse Adresse de l'utilisateur.
	 * @param ville Ville de l'utilisateur.
	 * @param cp Code postal de l'utilisateur.
	 * @return ID de l'utilisateur si la requ�te est un succ�s ou -1 si elle a �chou�e.
	 */
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
	
	/**
	 * R�cup�re l'utilisateur dans la base de donn�es.
	 * @return L'utilisateur enregistr� dans la base de donn�es.
	 */
	public Utilisateur getUtilisateur() {
		Cursor curseur = bdd.rawQuery("SELECT * FROM utilisateur", null);
		// On assume par d�faut que l'utilisateur ne s'est pas encore enregistr�
		Utilisateur utilisateur = null;
		// Il y a un enregistrement, alors on le prend en compte
		if (curseur.getCount() == 1) {
			curseur.moveToFirst();
			utilisateur = cursorToUtilisateur(curseur);
		}
		curseur.close();
		return utilisateur;
	}
	
	/**
	 * Indique que le tutoriel sur la manipulation des associations a �t� suivi.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
	public long setTutoAssociationsFait() {
		ContentValues valeurs = new ContentValues();
		valeurs.put("tutoAssociationsFait", 1);
		long resultat = bdd.update("utilisateur", valeurs, null, null);
		return resultat;
	}
	
	/**
	 * Indique que le tutoriel sur la manipulation des d�placements a �t� suivi.
	 * @return 1 si la requ�te est un succ�s ou 0 si c'est un �chec.
	 */
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
