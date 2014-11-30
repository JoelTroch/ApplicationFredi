package com.seuxmaximetrochjoel.applicationfredi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreerAssociationActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private AssociationDAO manipBDD = null;
	private EditText edtNom = null;
	private EditText edtAdresse = null;
	private EditText edtVille = null;
	private EditText edtCp = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_association);
		
		// Récupération des champs de texte et de l'Intent
		edtNom = (EditText)findViewById(R.id.edtNom);
		edtAdresse = (EditText)findViewById(R.id.edtAdresse);
		edtVille = (EditText)findViewById(R.id.edtVille);
		edtCp = (EditText)findViewById(R.id.edtCp);
		
		// Initialisation des manipulations BDD et du bouton "Enregistrer"
		manipBDD = new AssociationDAO(this);
		Button btnEnregistrer = (Button)findViewById(R.id.btnEnregistrer);
		btnEnregistrer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// On vérifie si au moins un champ est vide
				if (edtNom.getText().length() > 0 && edtAdresse.getText().length() > 0 && edtVille.getText().length() > 0 &&
						edtCp.getText().length() > 0) {
					// Création de l'association
					manipBDD.open();
					manipBDD.createAssociation(edtNom.getText().toString(), edtAdresse.getText().toString(),
							edtVille.getText().toString(), edtCp.getText().toString());
					manipBDD.close();
					CreerAssociationActivity.this.finish();
				} else {
					Toast.makeText(CreerAssociationActivity.this, "Vous devez saisir toutes les informations !", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
