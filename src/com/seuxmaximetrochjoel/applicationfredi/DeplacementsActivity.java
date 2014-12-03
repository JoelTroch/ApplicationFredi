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
import android.widget.TextView;
import android.widget.Toast;

public class DeplacementsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================

	private ArrayAdapter<String> listeAdapter = null;
	private ArrayList<Deplacement> listeDeplacements = null;
	private ArrayList<String> listeDeplacementsAffichage = new ArrayList<String>();
	private DeplacementDAO manipDeplacementBDD = null;
	private Dialog dialogTuto = null;
	private Intent intent = null;
	private UtilisateurDAO manipUtilisateurBDD = null;
	
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
				Intent intent2 = new Intent(DeplacementsActivity.this, CalculFraisActivity.class);
				intent2.putExtra("EXTRA_ASSOCIATION_ID", intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
				intent2.putExtra("EXTRA_ASSOCIATION_NOM", intent.getStringExtra("EXTRA_ASSOCIATION_NOM"));
				startActivity(intent2);
			}
		});
		
		// Initialisation de la liste d'affichage, mise à jour de son contenu, affichage et paramétrage menu contextuel
		ListView liste = (ListView)findViewById(R.id.listViewDeplacements);
		manipDeplacementBDD = new DeplacementDAO(this);
		miseAJourListe();
		listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeDeplacementsAffichage);
		liste.setAdapter(listeAdapter);
		registerForContextMenu(liste);
	}
	
	private void miseAJourListe() {
		// On récupère toutes les associations et on prépare la liste d'affichage
		manipDeplacementBDD.open();
		listeDeplacements = manipDeplacementBDD.getAllDeplacementsByIdAssociation(intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
		manipDeplacementBDD.close();
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
			Intent intent2 = new Intent(DeplacementsActivity.this, CreerDeplacementActivity.class);
			intent2.putExtra("EXTRA_ASSOCIATION_ID", intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
			intent2.putExtra("EXTRA_ID", listeDeplacements.get(info.position).getId());
			intent2.putExtra("EXTRA_DATE_JOUR", listeDeplacements.get(info.position).getDateJour());
			intent2.putExtra("EXTRA_DATE_MOIS", listeDeplacements.get(info.position).getDateMois());
			intent2.putExtra("EXTRA_DATE_ANNEE", listeDeplacements.get(info.position).getDateAnnee());
			intent2.putExtra("EXTRA_MOTIF", listeDeplacements.get(info.position).getMotif());
			intent2.putExtra("EXTRA_INTITULE_TRAJET", listeDeplacements.get(info.position).getIntituleTrajet());
			intent2.putExtra("EXTRA_NB_KM", listeDeplacements.get(info.position).getNbKm());
			intent2.putExtra("EXTRA_MONTANT_PEAGE", listeDeplacements.get(info.position).getMontantPeage());
			intent2.putExtra("EXTRA_MONTANT_REPAS", listeDeplacements.get(info.position).getMontantRepas());
			intent2.putExtra("EXTRA_MONTANT_HEBERGEMENT", listeDeplacements.get(info.position).getMontantHebergement());
			startActivity(intent2);
		} else { // Effacer
			manipDeplacementBDD.open();
			manipDeplacementBDD.deleteDeplacementById(listeDeplacements.get(info.position).getId());
			manipDeplacementBDD.close();
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
		
		// Tutoriel
		manipUtilisateurBDD = new UtilisateurDAO(this);
		manipUtilisateurBDD.open();
		if (listeDeplacements.size() > 0 && manipUtilisateurBDD.getUtilisateur().getTutoDeplacementsFait() == 0) {
			dialogTuto = new Dialog(this);
			dialogTuto.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogTuto.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialogTuto.setContentView(R.layout.coach_mark_deplacements);
			dialogTuto.setCanceledOnTouchOutside(true);
			View dialogTutoMasterView = dialogTuto.findViewById(R.id.coach_mark_master_view);
			dialogTutoMasterView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogTuto.dismiss();
				}
			});
			manipUtilisateurBDD.setTutoDeplacementsFait();
			dialogTuto.show();
		}
		manipUtilisateurBDD.close();
	}
}
