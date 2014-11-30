package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
		
		// Paramétrage du bouton "Ajouter association"
		Button btnAjouterAssociation = (Button)findViewById(R.id.btnAjouterAssociation);
		btnAjouterAssociation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO : Afficher l'activité "Créer une association"
			}
		});
		
		// Initialisation de la liste d'affichage, mise à jour de son contenu, affichage et paramétrage menu contextuel
		ListView listViewAssociations = (ListView)findViewById(R.id.listViewAssociations);
		manipBDD = new AssociationDAO(this);
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeAssociationsAffichage);
		listViewAssociations.setAdapter(listeAdapter);
		registerForContextMenu(listViewAssociations);
	}
	
	private void miseAJourListe() {
		// On récupère toutes les associations et on prépare la liste d'affichage
		manipBDD.open();
		listeAssociations = manipBDD.getAllAssociations();
		manipBDD.close();
		for (Association uneAssociation : listeAssociations)
			listeAssociationsAffichage.add(uneAssociation.getNom());
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listViewAssociations) {
			// Ajout des actions au menu contextuel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeAssociationsAffichage.get(info.position));
			menu.add(Menu.NONE, 0, 0, "Modifier");
			menu.add(Menu.NONE, 1, 1, "Effacer");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if (item.getItemId() == 0) { // Modifier
			// TODO : Afficher l'activité "Créer une association" en mode modification
		} else { // Effacer
			// TODO : Effacer tous les déplacements de l'associations et ensuite effacer cette dernière
		}
		return true;
	}
}
