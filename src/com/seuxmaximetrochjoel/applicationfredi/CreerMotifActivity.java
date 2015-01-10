package com.seuxmaximetrochjoel.applicationfredi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Code pour l'activité "Créer un motif".
 * @author Maxime Seux
 */
public class CreerMotifActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private EditText edtMotif = null;
	private Intent intent = null;
	private MotifDAO manipBDD = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_motif);
		
		// Initialisation des éléments du layout.
		edtMotif = (EditText)findViewById(R.id.edtMotif);
		intent = getIntent();
		
		// Est-ce qu'on met à jour un motif existante ? Si c'est le cas on pré-remplit les champs.
		if (intent.getLongExtra("EXTRA_ID", -1) != -1) {
			edtMotif.setText(intent.getStringExtra("EXTRA_LIBELLE"));
			setTitle(getString(R.string.title_activity_modifier_motif));
		}
		
		// Initialisation des manipulations BDD et du bouton "Enregistrer".
		manipBDD = new MotifDAO(this);
		Button btnEnregistrer = (Button)findViewById(R.id.btnEnregistrer);
		btnEnregistrer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Contrôle de saisie.
				if (edtMotif.getText().length() > 0) {
					// Création ou mise à jour du motif.
					manipBDD.open(false);
					if (intent.getLongExtra("EXTRA_ID", -1) == -1)
						manipBDD.createMotif(edtMotif.getText().toString());
					else
						manipBDD.updateMotif(intent.getLongExtra("EXTRA_ID", -1), edtMotif.getText().toString());
					
					manipBDD.close();
					CreerMotifActivity.this.finish();
				} else
					Toast.makeText(CreerMotifActivity.this, getString(R.string.saisir_toutes_les_infos), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
