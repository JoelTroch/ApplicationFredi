package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DeplacementsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================

	private ArrayAdapter<String> listeAdapter = null;
	private ArrayList<Deplacement> listeDeplacements = null;
	private ArrayList<String> listeDeplacementsAffichage = new ArrayList<String>();
	private DeplacementDAO manipBDD = null;
	private Intent intent = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deplacements);
		
		// Mise à jour nom association
		intent = getIntent();
		TextView textViewAssociation = (TextView)findViewById(R.id.textViewAssociation);
		textViewAssociation.setText(getString(R.string.pour) + " " + intent.getStringExtra("EXTRA_ASSOCIATION_NOM"));
		
		// Paramétrage du bouton "Ajouter"
		Button btnAjouterDeplacement = (Button)findViewById(R.id.btnAjouterDeplacement);
		btnAjouterDeplacement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent2 = new Intent(DeplacementsActivity.this, CreerDeplacementActivity.class);
				intent2.putExtra("EXTRA_ASSOCIATION_ID", intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
				startActivity(intent2);
			}
		});
		
		// Paramétrage du bouton "Calculer"
		Button btnCalculerFrais = (Button)findViewById(R.id.btnCalculerFrais);
		btnCalculerFrais.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO : Faire l'activité "Calcul frais"
			}
		});
		
		// Initialisation de la liste d'affichage, mise à jour de son contenu, affichage et paramétrage menu contextuel
		ListView liste = (ListView)findViewById(R.id.listViewDeplacements);
		manipBDD = new DeplacementDAO(this);
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeDeplacementsAffichage);
		liste.setAdapter(listeAdapter);
		registerForContextMenu(liste);
	}
	
	private void miseAJourListe() {
		// On récupère toutes les associations et on prépare la liste d'affichage
		manipBDD.open();
		listeDeplacements = manipBDD.getAllDeplacementsByIdAssociation(intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
		manipBDD.close();
		for (Deplacement unDeplacement : listeDeplacements)
			listeDeplacementsAffichage.add(unDeplacement.getMotif());
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listViewDeplacements) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeDeplacementsAffichage.get(info.position));
			menu.add(Menu.NONE, 0, 0, "Modifier");
			menu.add(Menu.NONE, 1, 1, "Effacer");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if (item.getItemId() == 0) { // Modifier
			// TODO : Modifier déplacement
		} else { // Effacer
			manipBDD.open();
			manipBDD.deleteDeplacementById(listeDeplacements.get(info.position).getId());
			manipBDD.close();
			// On efface la liste et on la met à jour
			listeDeplacementsAffichage.clear();
			miseAJourListe();
			listeAdapter.notifyDataSetChanged();
			// Message de confirmation
			Toast.makeText(this, "Suppression effectuée !", Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// On efface la liste et on la met à jour
		listeDeplacementsAffichage.clear();
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
	}
}
