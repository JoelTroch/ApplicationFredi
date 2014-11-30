package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AssociationsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private ArrayAdapter<String> listeAdapter = null;
	private ArrayList<Association> listeAssociations = null;
	private ArrayList<String> listeAssociationsAffichage = new ArrayList<String>();
	private AssociationDAO manipBDD = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_associations);
		
		// Param�trage du bouton "Ajouter association"
		Button btnAjouterAssociation = (Button)findViewById(R.id.btnAjouterAssociation);
		btnAjouterAssociation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO : Afficher l'activit� "Cr�er une association"
			}
		});
		
		// Initialisation de la liste d'affichage, mise � jour de son contenu, affichage et param�trage menu contextuel
		ListView listViewAssociations = (ListView)findViewById(R.id.listViewAssociations);
		manipBDD = new AssociationDAO(this);
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeAssociationsAffichage);
		listViewAssociations.setAdapter(listeAdapter);
	}
	
	private void miseAJourListe() {
		// On r�cup�re toutes les associations et on pr�pare la liste d'affichage
		manipBDD.open();
		listeAssociations = manipBDD.getAllAssociations();
		manipBDD.close();
		for (Association uneAssociation : listeAssociations)
			listeAssociationsAffichage.add(uneAssociation.getNom());
	}
}
