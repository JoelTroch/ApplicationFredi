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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Code pour l'activité "Associations".
 * @author Maxime Seux
 */
public class AssociationsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private AdaptateurListeAssociations listeAdapter = null;
	private ArrayList<Association> listeAssociations = null;
	private AssociationDAO manipAssociationBDD = null;
	private Dialog dialogTuto = null;
	private ListView listViewAssociations = null;
	private UtilisateurDAO manipUtilisateurBDD = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_associations);
		
		// Paramétrage du bouton "Ajouter association".
		Button btnAjouterAssociation = (Button)findViewById(R.id.btnAjouterAssociation);
		btnAjouterAssociation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
				startActivity(intent);
			}
		});
		
		// Initialisation de la liste d'affichage et de la DAO.
		listViewAssociations = (ListView)findViewById(R.id.listViewAssociations);
		manipAssociationBDD = new AssociationDAO(this);
		
		// Mise à jour de la liste, affichage et gestion du menu contextuel.
		miseAJourListe();
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
		// On récupère toutes les associations et on remplit la liste.
		manipAssociationBDD.open(true);
		listeAssociations = manipAssociationBDD.getAllAssociations();
		listeAdapter = new AdaptateurListeAssociations(this, listeAssociations);
		listViewAssociations.setAdapter(listeAdapter);
		manipAssociationBDD.close();
	}
	
	/**
	 * Surchage de la méthode "onCreateContextMenu" de la classe "Activity" d'Android.
	 * Cette procédure événementielle est appelée à chaque fois qu'un menu contextuel est sur le point d'être
	 * créé.
	 * @param ContextMenu Le menu contextuel en cours de création.
	 * @param View La vue auquel le menu contextuel est en cours de création.
	 * @param ContextMenuInfo Informations supplémentaires au sujet de "l'objet" du menu contextuel.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listViewAssociations) {
			// Ajout des actions au menu contextuel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeAssociations.get(info.position).getNom());
			menu.add(Menu.NONE, 0, 0, getString(R.string.modifier));
			menu.add(Menu.NONE, 1, 1, getString(R.string.effacer));
		}
	}
	
	/**
	 * Surcharge de la méthode "onContextItemSelected" de la classe "Activity" d'Android.
	 * Cette procédure événementielle est appelée à chaque fois qu'un "objet" dans un menu contextuel est
	 * sélectionné.
	 * @param "L'objet" du menu contextuel sélectionné.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if (item.getItemId() == 0) {
			// On ouvre l'activité "Créer une association" en "mode modification".
			Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
			intent.putExtra("EXTRA_ID", listeAssociations.get(info.position).getId());
			intent.putExtra("EXTRA_NOM", listeAssociations.get(info.position).getNom());
			intent.putExtra("EXTRA_ADRESSE", listeAssociations.get(info.position).getAdresse());
			intent.putExtra("EXTRA_VILLE", listeAssociations.get(info.position).getVille());
			intent.putExtra("EXTRA_CP", listeAssociations.get(info.position).getCP());
			startActivity(intent);
		} else {
			// Effacement des déplacements de l'association et enfin de l'association elle-même.
			DeplacementDAO manipBDDDeplacements = new DeplacementDAO(this);
			manipBDDDeplacements.open(false);
			manipBDDDeplacements.deleteAllDeplacementsByIdAssociation(listeAssociations.get(info.position).getId());
			manipBDDDeplacements.close();
			manipAssociationBDD.open(false);
			manipAssociationBDD.deleteAssociationById(listeAssociations.get(info.position).getId());
			manipAssociationBDD.close();
			
			// Mise à jour de la liste.
			miseAJourListe();
			listeAdapter.notifyDataSetChanged();
			
			// Message de confirmation.
			Toast.makeText(this, getString(R.string.suppression_effectuee), Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	/**
	 * Surcharge de la méthode "onResume" de la classe "Activity" d'Android.
	 * Cette procédure événementielle est appelée à chaque fois que l'activité réapparait.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		// Mise à jour de la liste.
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
		
		// Gestion du tutoriel.
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
