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

/**
 * Code pour l'activit� "Associations".
 * @author Maxime Seux
 *
 */
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
		
		// Param�trage du bouton "Ajouter association".
		Button btnAjouterAssociation = (Button)findViewById(R.id.btnAjouterAssociation);
		btnAjouterAssociation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
				startActivity(intent);
			}
		});
		
		// Initialisation de la liste d'affichage et de la DAO.
		ListView listViewAssociations = (ListView)findViewById(R.id.listViewAssociations);
		manipAssociationBDD = new AssociationDAO(this);
		
		// Mise � jour de la liste et affichage.
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeAssociationsAffichage);
		listViewAssociations.setAdapter(listeAdapter);
		
		// Gestion du menu contextuel.
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
		// On r�cup�re toutes les associations et on pr�pare la liste d'affichage.
		manipAssociationBDD.open(true);
		listeAssociations = manipAssociationBDD.getAllAssociations();
		manipAssociationBDD.close();
		for (Association uneAssociation : listeAssociations)
			listeAssociationsAffichage.add(uneAssociation.getNom());
	}
	
	/**
	 * Surchage de la m�thode "onCreateContextMenu" de la classe "Activity" d'Android.
	 * Cette proc�dure �v�nementielle est appel�e � chaque fois qu'un menu contextuel est sur le point d'�tre
	 * cr��.
	 * @param ContextMenu Le menu contextuel en cours de cr�ation.
	 * @param View La vue auquel le menu contextuel est en cours de cr�ation.
	 * @param ContextMenuInfo Informations suppl�mentaires au sujet de "l'objet" du menu contextuel.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
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
	
	/**
	 * Surcharge de la m�thode "onContextItemSelected" de la classe "Activity" d'Android.
	 * Cette proc�dure �v�nementielle est appel�e � chaque fois qu'un "objet" dans un menu contextuel est
	 * s�lectionn�.
	 * @param "L'objet" du menu contextuel s�lectionn�.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if (item.getItemId() == 0) {
			// On ouvre l'activit� "Cr�er une association" en "mode modification".
			Intent intent = new Intent(AssociationsActivity.this, CreerAssociationActivity.class);
			intent.putExtra("EXTRA_ID", listeAssociations.get(info.position).getId());
			intent.putExtra("EXTRA_NOM", listeAssociations.get(info.position).getNom());
			intent.putExtra("EXTRA_ADRESSE", listeAssociations.get(info.position).getAdresse());
			intent.putExtra("EXTRA_VILLE", listeAssociations.get(info.position).getVille());
			intent.putExtra("EXTRA_CP", listeAssociations.get(info.position).getCP());
			startActivity(intent);
		} else {
			// Effacement des d�placements de l'association et enfin de l'association elle-m�me.
			DeplacementDAO manipBDDDeplacements = new DeplacementDAO(this);
			manipBDDDeplacements.open(false);
			manipBDDDeplacements.deleteAllDeplacementsByIdAssociation(listeAssociations.get(info.position).getId());
			manipBDDDeplacements.close();
			manipAssociationBDD.open(false);
			manipAssociationBDD.deleteAssociationById(listeAssociations.get(info.position).getId());
			manipAssociationBDD.close();
			
			// Mise � jour de la liste.
			listeAssociationsAffichage.clear();
			miseAJourListe();
			listeAdapter.notifyDataSetChanged();
			
			// Message de confirmation.
			Toast.makeText(this, getString(R.string.suppression_effectuee), Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	/**
	 * Surcharge de la m�thode "onResume" de la classe "Activity" d'Android.
	 * Cette proc�dure �v�nementielle est appel�e � chaque fois que l'activit� r�apparait.
	 * @see http://developer.android.com/reference/android/app/Activity.html#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		// Mise � jour de la liste.
		listeAssociationsAffichage.clear();
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
