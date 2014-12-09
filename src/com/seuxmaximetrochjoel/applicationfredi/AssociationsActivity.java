package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AssociationsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private ArrayAdapter<String> listeAdapter = null;
	private ArrayList<Association> listeAssociations = null;
	private ArrayList<String> listeAssociationsAffichage = new ArrayList<String>();
	private AssociationDAO manipAssociationBDD = null;
	private Dialog dialogTuto = null;
	private UtilisateurDAO manipUtilisateurBDD = null;
	
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
				Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
				startActivity(intent);
			}
		});
		
		// Initialisation de la liste d'affichage, mise à jour de son contenu, affichage et paramétrage menu contextuel
		ListView listViewAssociations = (ListView)findViewById(R.id.listViewAssociations);
		manipAssociationBDD = new AssociationDAO(this);
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeAssociationsAffichage);
		listViewAssociations.setAdapter(listeAdapter);
		registerForContextMenu(listViewAssociations);
		listViewAssociations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(AssociationsActivity.this, DeplacementsActivity.class);
				intent.putExtra("EXTRA_ASSOCIATION_ID", listeAssociations.get(position).getId());
				intent.putExtra("EXTRA_ASSOCIATION_NOM", listeAssociations.get(position).getNom());
				startActivity(intent);
			}
		});
	}
	
	private void miseAJourListe() {
		// On récupère toutes les associations et on prépare la liste d'affichage
		manipAssociationBDD.open(true);
		listeAssociations = manipAssociationBDD.getAllAssociations();
		manipAssociationBDD.close();
		for (Association uneAssociation : listeAssociations)
			listeAssociationsAffichage.add(uneAssociation.getNom());
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listViewAssociations) {
			// Ajout des actions au menu contextuel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeAssociationsAffichage.get(info.position));
			menu.add(Menu.NONE, 0, 0, getString(R.string.modifier));
			menu.add(Menu.NONE, 1, 1, getString(R.string.effacer));
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if (item.getItemId() == 0) { // Modifier
			Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
			intent.putExtra("EXTRA_ID", listeAssociations.get(info.position).getId());
			intent.putExtra("EXTRA_NOM", listeAssociations.get(info.position).getNom());
			intent.putExtra("EXTRA_ADRESSE", listeAssociations.get(info.position).getAdresse());
			intent.putExtra("EXTRA_VILLE", listeAssociations.get(info.position).getVille());
			intent.putExtra("EXTRA_CP", listeAssociations.get(info.position).getCP());
			startActivity(intent);
		} else { // Effacer
			DeplacementDAO manipBDDDeplacements = new DeplacementDAO(this);
			manipBDDDeplacements.open(false);
			manipBDDDeplacements.deleteAllDeplacementsByIdAssociation(listeAssociations.get(info.position).getId());
			manipBDDDeplacements.close();
			manipAssociationBDD.open(false);
			manipAssociationBDD.deleteAssociationById(listeAssociations.get(info.position).getId());
			manipAssociationBDD.close();
			// On efface la liste et on la met à jour
			listeAssociationsAffichage.clear();
			miseAJourListe();
			listeAdapter.notifyDataSetChanged();
			// Message de confirmation
			Toast.makeText(this, getString(R.string.suppression_effectuee), Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// On efface la liste et on la met à jour
		listeAssociationsAffichage.clear();
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
		
		// Tutoriel
		manipUtilisateurBDD = new UtilisateurDAO(this);
		manipUtilisateurBDD.open(true);
		if (listeAssociations.size() > 0 && manipUtilisateurBDD.getUtilisateur().getTutoAssociationsFait() == 0) {
			dialogTuto = new Dialog(this);
			dialogTuto.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogTuto.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialogTuto.setContentView(R.layout.coach_mark_associations);
			dialogTuto.setCanceledOnTouchOutside(true);
			View dialogTutoMasterView = dialogTuto.findViewById(R.id.coach_mark_master_view);
			dialogTutoMasterView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogTuto.dismiss();
				}
			});
			manipUtilisateurBDD.setTutoAssociationsFait();
			dialogTuto.show();
		}
		manipUtilisateurBDD.close();
	}
}
