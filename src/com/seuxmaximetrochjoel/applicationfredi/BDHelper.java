package com.seuxmaximetrochjoel.applicationfredi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class BDHelper extends SQLiteOpenHelper {
	
	// TODO : Cr�er la requ�te SQL
	private final String requeteCreationBDD = "";
	
	public BDHelper(Context context) {
		/* Note concernant l'emplacement de la base de donn�es :
		 * Pour faciliter le debugging, elle est stock�e sur la carte SD.
		 * Par d�faut elle est stock�e dans un emplacement interdit (sauf si le terminal est root�).
		 * Une fois l'application termin�e, on pourra remettre son emplacement par d�faut.
		 */
		super(context, Environment.getExternalStorageDirectory().getPath() + "appliFredi_database.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase bdd) {
		bdd.execSQL(requeteCreationBDD);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase bdd, int ancienneVersion, int nouvelleVersion) {
		/* TODO : Si on a le temps, faire en sorte de conserver les donn�es lors d'une mise � jour de la base
		 * de donn�es. Ca serait frustant pour l'utilisateur de perdre ses donn�es � cause de �a.
		 */
		// TODO : Ecrire la requ�te SQL
		bdd.execSQL("");
		onCreate(bdd);
	}
}
