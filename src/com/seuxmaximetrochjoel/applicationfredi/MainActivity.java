package com.seuxmaximetrochjoel.applicationfredi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private Button btnEnregistrer = null;
	private EditText edtNom = null;
	private UtilisateurDAO manipBDD = null; 
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//Récuperation de l'utilisateur
		manipBDD = new UtilisateurDAO(this);
		manipBDD.open();
		Utilisateur utilisateur = manipBDD.getUtilisateur();
		manipBDD.close();
		
	}
	
	private void gotoAssociationsActivity(String nomUtilisateur) {
		Intent intent = new Intent(MainActivity.this, AssociationsActivity.class);
		intent.putExtra("DATA_NOM", nomUtilisateur);
		startActivity(intent);
	}
}
