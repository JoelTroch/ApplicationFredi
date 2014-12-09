package com.seuxmaximetrochjoel.applicationfredi;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreerDeplacementActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private Button btnDate = null;
	private DeplacementDAO manipBDD = null;
	private EditText edtMotif = null;
	private EditText edtIntituleTrajet = null;
	private EditText edtNbKm = null;
	private EditText edtMontantPeage = null;
	private EditText edtMontantRepas = null;
	private EditText edtMontantHebergement = null;
	private int annee;
	private int jour;
	private int mois;
	private Intent intent = null;
	private TextView textViewDate = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_deplacement);
		
		// Récupération des champs de texte et de l'Intent
		edtMotif = (EditText)findViewById(R.id.edtMotif);
		edtIntituleTrajet = (EditText)findViewById(R.id.edtIntituleTrajet);
		edtNbKm = (EditText)findViewById(R.id.edtNbKm);
		edtMontantPeage = (EditText)findViewById(R.id.edtMontantPeage);
		edtMontantRepas = (EditText)findViewById(R.id.edtMontantRepas);
		edtMontantHebergement = (EditText)findViewById(R.id.edtMontantHebergement);
		intent = getIntent();
		
		// Si c'est une mise à jour, on remplit les champs
		Calendar c = Calendar.getInstance();
		if (intent.getLongExtra("EXTRA_ID", -1) != -1) {
			edtMotif.setText(intent.getStringExtra("EXTRA_MOTIF"));
			edtIntituleTrajet.setText(intent.getStringExtra("EXTRA_INTITULE_TRAJET"));
			edtNbKm.setText(String.valueOf(intent.getFloatExtra("EXTRA_NB_KM", 0)));
			edtMontantPeage.setText(String.valueOf(intent.getFloatExtra("EXTRA_MONTANT_PEAGE", 0)));
			edtMontantRepas.setText(String.valueOf(intent.getFloatExtra("EXTRA_MONTANT_REPAS", 0)));
			edtMontantHebergement.setText(String.valueOf(intent.getFloatExtra("EXTRA_MONTANT_HEBERGEMENT", 0)));
			
			jour = intent.getIntExtra("EXTRA_DATE_JOUR", Calendar.DAY_OF_MONTH);
			mois = intent.getIntExtra("EXTRA_DATE_MOIS", Calendar.MONTH) + 1;
			annee = intent.getIntExtra("EXTRA_DATE_ANNEE", Calendar.YEAR);
			
			setTitle(R.string.title_activity_modifier_deplacement);
		} else {
			jour = c.get(Calendar.DAY_OF_MONTH);
			mois = c.get(Calendar.MONTH) + 1;
			annee = c.get(Calendar.YEAR);
		}
		
		// Mise à jour date
		textViewDate = (TextView)findViewById(R.id.textViewDate);
		textViewDate.setText(getString(R.string.date) + " " + jour + "/" + mois + "/" + annee);
		
		// Paramétrage du bouton "Changer la date"
		btnDate = (Button)findViewById(R.id.btnDate);
		btnDate.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				showDialog(999);
			}
		});
		
		// Initialisation des manipulations BDD et du bouton
		manipBDD = new DeplacementDAO(this);
		Button btnEnregistrer = (Button)findViewById(R.id.btnEnregistrer);
		btnEnregistrer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// On vérifie si au moins un champ est vide
				if (edtMotif.getText().length() > 0 && edtIntituleTrajet.getText().length() > 0 &&
						edtNbKm.getText().length() > 0 && edtMontantPeage.getText().length() > 0 &&
						edtMontantRepas.getText().length() > 0 && edtMontantHebergement.getText().length() > 0) {
					// Création ou mise à jour de l'association
					manipBDD.open(false);
					if (intent.getLongExtra("EXTRA_ID", -1) == -1) {
						manipBDD.createDeplacement(jour, mois, annee, edtMotif.getText().toString(),
								edtIntituleTrajet.getText().toString(), Float.valueOf(edtNbKm.getText().toString()),
								Float.valueOf(edtMontantPeage.getText().toString()),
								Float.valueOf(edtMontantRepas.getText().toString()),
								Float.valueOf(edtMontantHebergement.getText().toString()),
								intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
					} else {
						manipBDD.updateDeplacement(intent.getLongExtra("EXTRA_ID", -1), jour, mois, annee,
								edtMotif.getText().toString(), edtIntituleTrajet.getText().toString(),
								Float.valueOf(edtNbKm.getText().toString()),
								Float.valueOf(edtMontantPeage.getText().toString()),
								Float.valueOf(edtMontantRepas.getText().toString()),
								Float.valueOf(edtMontantHebergement.getText().toString()),
								intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
					}
					manipBDD.close();
					CreerDeplacementActivity.this.finish();
				} else {
					Toast.makeText(CreerDeplacementActivity.this, getString(R.string.saisir_toutes_les_infos), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 999:
		   return new DatePickerDialog(this, datePickerListener, annee, mois, jour);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker vue, int anneeChoisie, int moisChoisi, int jourChoisi) {
			annee = anneeChoisie;
			mois = moisChoisi + 1;
			jour = jourChoisi;
			textViewDate.setText(getString(R.string.date) + " " + jour + "/" + mois + "/" + annee);
		}
	};
}
