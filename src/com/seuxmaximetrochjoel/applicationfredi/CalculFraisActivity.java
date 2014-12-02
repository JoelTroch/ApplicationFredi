package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CalculFraisActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private AlertDialog.Builder dialogPrixKm = null;
	private DeplacementDAO manipBDD = null;
	private EditText edtDialogPrixKm = null;
	private Intent intent = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calcul_frais);
		
		// Dialogue qui demande le tarif au kilomètre des trajets
		dialogPrixKm = new AlertDialog.Builder(this);
		dialogPrixKm.setTitle("Prix par kilomètre parcouru");
		dialogPrixKm.setMessage("Veuillez saisir le prix d\'un kilomètre pour les trajets.");
		edtDialogPrixKm = new EditText(this);
		edtDialogPrixKm.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		dialogPrixKm.setView(edtDialogPrixKm);
		dialogPrixKm.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	// Contrôle de saisie
            	if (edtDialogPrixKm.getText().length() > 0) {
	            	float prixKm = 0;
	                prixKm = Float.valueOf(edtDialogPrixKm.getText().toString());
	                
	                // Récupération des déplacements de l'association concernée
	                intent = getIntent();
	                manipBDD = new DeplacementDAO(CalculFraisActivity.this);
	                manipBDD.open();
	                ArrayList<Deplacement> listeDeplacements = manipBDD.getAllDeplacementsByIdAssociation(intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
	                manipBDD.close();
	                
	                // Calcul des frais
	                float kmTotal = 0;
	                float montantKilometres = 0;
	                float montantPeageTotal = 0;
	                float montantRepasTotal = 0;
	                float montantHebergementTotal = 0;
	                float totalFrais = 0;
	                for (Deplacement unDeplacement : listeDeplacements) {
	                	kmTotal += unDeplacement.getNbKm();
	                	montantPeageTotal += unDeplacement.getMontantPeage();
	                	montantRepasTotal += unDeplacement.getMontantRepas();
	                	montantHebergementTotal += unDeplacement.getMontantHebergement();
	                }
	                montantKilometres = kmTotal * prixKm;
	                totalFrais = montantKilometres + montantPeageTotal + montantRepasTotal + montantHebergementTotal;
	                
	                // Affichage des frais
	            	ArrayList<String> listeFraisAffichage = new ArrayList<String>();
	                listeFraisAffichage.add("Association : " + intent.getStringExtra("EXTRA_ASSOCIATION_NOM"));
	                listeFraisAffichage.add("Nombre de déplacements : " + String.valueOf(listeDeplacements.size()));
	                listeFraisAffichage.add("Nombre de kilomètres total : " + String.format("%.02f", kmTotal) + " km");
	                listeFraisAffichage.add("Montant kilomètres total : " + String.format("%.02f", montantKilometres) + " €");
	                listeFraisAffichage.add("Montant péage total : " + String.format("%.02f", montantPeageTotal) + " €");
	                listeFraisAffichage.add("Montant repas total : " + String.format("%.02f", montantRepasTotal) + " €");
	                listeFraisAffichage.add("Montant hébergement total : " + String.format("%.02f", montantHebergementTotal) + " €");
	                listeFraisAffichage.add("Total des frais : " + String.format("%.02f", totalFrais) + " €");
	                ArrayAdapter<String> listeAdapter = new ArrayAdapter<String>(CalculFraisActivity.this, android.R.layout.simple_list_item_1, listeFraisAffichage);
	            	ListView listViewFrais = (ListView)findViewById(R.id.listViewFrais);
	                listViewFrais.setAdapter(listeAdapter);
            	} else {
            		Toast.makeText(CalculFraisActivity.this, "Veuillez saisir une valeur", Toast.LENGTH_LONG).show();
            		CalculFraisActivity.this.finish();
            	}
            }
        });
		// BUGFIX : Si l'utilisateur annule la saisie, on évite une activité blanche
		dialogPrixKm.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		dialogPrixKm.show();
	}
}
