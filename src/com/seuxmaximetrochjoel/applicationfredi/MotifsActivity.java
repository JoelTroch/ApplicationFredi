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
 * Code pour l'activité "Motifs".
 * @author Maxime Seux
 */
public class MotifsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private ArrayAdapter<String> listeAdapter = null;
	private ArrayList<Motif> listeMotifs = null;
	private ArrayList<String> listeMotifsAffichage = new ArrayList<String>();
	private Dialog dialogTuto = null;
	private MotifDAO manipMotifBDD = null;
	private UtilisateurDAO manipUtilisateurBDD = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motifs);
		
		// Paramétrage du bouton "Ajouter motif".
		Button btnAjouterMotif = (Button)findViewById(R.id.btnAjouterMotif);
		btnAjouterMotif.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MotifsActivity.this, CreerMotifActivity.class);
				startActivity(intent);
			}
		});
		
		// Initialisation de la liste d'affichage et de la DAO.
		ListView listViewMotifs = (ListView)findViewById(R.id.listViewMotifs);
		manipMotifBDD = new MotifDAO(this);
		
		// Mise à jour de la liste et affichage.
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeMotifsAffichage);
		listViewMotifs.setAdapter(listeAdapter);
		
		// Gestion du menu contextuel.
		registerForContextMenu(listViewMotifs);
		listViewMotifs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent();
				intent.putExtra("MOTIF_PREDEFINI", listeMotifs.get(position).getLibelle());
				setResult(RESULT_OK, intent);
				finish();
			}
			
		});
	}
	
	private void miseAJourListe() {
		// On récupère tous les motifs et on prépare la liste d'affichage.
		manipMotifBDD.open(true);
		listeMotifs = manipMotifBDD.getAllMotifs();
		manipMotifBDD.close();
		for (Motif unMotif : listeMotifs)
			listeMotifsAffichage.add(unMotif.getLibelle());
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
		if (v.getId() == R.id.listViewMotifs) {
			// Ajout des actions au menu contextuel
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeMotifsAffichage.get(info.position));
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
			// On ouvre l'activité "Créer un motif" en "mode modification".
			Intent intent = new Intent(MotifsActivity.this, CreerMotifActivity.class);
			intent.putExtra("EXTRA_ID", listeMotifs.get(info.position).getId());
			intent.putExtra("EXTRA_LIBELLE", listeMotifs.get(info.position).getLibelle());
			startActivity(intent);
		} else {
			// Effacement du motif.
			manipMotifBDD.open(false);
			manipMotifBDD.deleteMotifById(listeMotifs.get(info.position).getId());
			manipMotifBDD.close();
			
			// Mise à jour de la liste.
			listeMotifsAffichage.clear();
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
		listeMotifsAffichage.clear();
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
		
		// Gestion du tutoriel.
		manipUtilisateurBDD = new UtilisateurDAO(this);
		manipUtilisateurBDD.open(true);
		if (listeMotifs.size() > 0 && manipUtilisateurBDD.getUtilisateur().getTutoMotifsFait() == 0) {
			dialogTuto = new Dialog(this);
			dialogTuto.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogTuto.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialogTuto.setContentView(R.layout.coach_mark_motifs);
			dialogTuto.setCanceledOnTouchOutside(true);
			
			View dialogTutoMasterView = dialogTuto.findViewById(R.id.coach_mark_master_view);
			dialogTutoMasterView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialogTuto.dismiss();
				}
				
			});
			manipUtilisateurBDD.setTutoMotifsFait();
			dialogTuto.show();
		}
		manipUtilisateurBDD.close();
	}
}
