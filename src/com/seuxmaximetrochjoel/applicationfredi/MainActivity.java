package com.seuxmaximetrochjoel.applicationfredi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Code pour l'activité principale "Enregistrement utilisateur".
 * @author Maxime Seux
 */
public class MainActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private Button btnEnregistrer = null;
	private EditText edtNom = null;
	private EditText edtPrenom = null;
	private EditText edtAdresse = null;
	private EditText edtVille = null;
	private EditText edtCp = null;
	private UtilisateurDAO manipBDD = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// Tentative de récupération de l'utilisateur.
		manipBDD = new UtilisateurDAO(this);
		manipBDD.open(true);
		Utilisateur utilisateur = manipBDD.getUtilisateur();
		manipBDD.close();
		
		// Si il est enregistré, on passe tout de suite à l'activité "Associations", dans le cas contraire
		// on initialise le bouton "Enregistrer".
		if (utilisateur != null)
			gotoAssociationsActivity();
		else {
			setContentView(R.layout.activity_main);
			btnEnregistrer = (Button)findViewById(R.id.btnEnregistrer);
			btnEnregistrer.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					// Initialisation des champs de texte.
					edtNom = (EditText)findViewById(R.id.edtNom);
					edtPrenom = (EditText)findViewById(R.id.edtPrenom);
					edtAdresse = (EditText)findViewById(R.id.edtAdresse);
					edtVille = (EditText)findViewById(R.id.edtVille);
					edtCp = (EditText)findViewById(R.id.edtCp);
					
					// Contrôle de saisie.
					if (edtNom.getText().length() > 0 && edtPrenom.getText().length() > 0 && edtAdresse.getText().length() > 0 &&
							edtVille.getText().length() > 0 && edtCp.getText().length() > 0) {
						
						// Création de l'utilisateur.
						manipBDD.open(false);
						manipBDD.createUtilisateur(edtNom.getText().toString(), edtPrenom.getText().toString(),
								edtAdresse.getText().toString(), edtVille.getText().toString(), edtCp.getText().toString());
						manipBDD.close();
						gotoAssociationsActivity();
					} else
						Toast.makeText(MainActivity.this, getString(R.string.saisir_toutes_les_infos), Toast.LENGTH_SHORT).show();
				}
				
			});
		}
	}
	
	private void gotoAssociationsActivity() {
		Intent intent = new Intent(MainActivity.this, AssociationsActivity.class);
		startActivity(intent);
	}		
}
