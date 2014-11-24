package com.seuxmaximetrochjoel.applicationfredi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	
		// Récupération de l'utilisateur
		manipBDD = new UtilisateurDAO(this);
		manipBDD.open();
		Utilisateur utilisateur = manipBDD.getUtilisateur();
		manipBDD.close();
		
		// Si il est enregistré
		if (utilisateur != null) {
			gotoAssociationsActivity(utilisateur.getNom());
		} else {
			// Si il n'est pas enregistré, on affiche la vue et on paramètre le bouton
			setContentView(R.layout.activity_main);
			btnEnregistrer = (Button)findViewById(R.id.edtNom);
			btnEnregistrer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Récupération du champs Texte et on vérifie si il y a une saisie
					edtNom = (EditText)findViewById(R.id.edtNom);
					if(edtNom.getText().length() > 0) {
						//Création de l'utilisateur
						manipBDD.open();
						manipBDD.createUtilisateur(edtNom.getText().toString());
						manipBDD.close();
						gotoAssociationsActivity(edtNom.getText().toString());
					} else {
						Toast.makeText(MainActivity.this, "Vous devez saisir un nom!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}
	
	private void gotoAssociationsActivity(String nomUtilisateur) {
		Intent intent = new Intent(MainActivity.this, AssociationsActivity.class);
		intent.putExtra("DATA_NOM", nomUtilisateur);
		startActivity(intent);
	}		
}
