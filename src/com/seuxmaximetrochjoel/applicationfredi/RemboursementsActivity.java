package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Code pour l'activit� "Remboursements".
 * @author Jo�l Troch
 */
public class RemboursementsActivity extends Activity {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================

	private AdaptateurListeRemboursements listeAdapter = null;
	private ArrayList<Remboursement> listeRemboursements = null;
	private RemboursementDAO manipRemboursementBDD = null;
	private Intent intent = null;
	private ListView liste = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remboursements);
		
		// Mise � jour du nom de l'association concern�e.
		intent = getIntent();
		TextView textViewAssociation = (TextView)findViewById(R.id.textViewAssociation);
		textViewAssociation.setText(getString(R.string.pour) + " " + intent.getStringExtra("EXTRA_ASSOCIATION_NOM"));
		
		// Param�trage du bouton "Ajouter".
		Button btnAjouterAnnee = (Button)findViewById(R.id.btnAjouterAnnee);
		btnAjouterAnnee.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText edtAnnee = (EditText)findViewById(R.id.edtAnnee);
				CheckBox cboxDoitRembourser = (CheckBox)findViewById(R.id.cboxDoitRembourser);
				if (edtAnnee.getText().length() == 4) {
					manipRemboursementBDD.open(false);
					manipRemboursementBDD.createRemboursement(intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1),
							Integer.valueOf(edtAnnee.getText().toString()), cboxDoitRembourser.isChecked() ? 1 : 0);
					manipRemboursementBDD.close();
					miseAJourListe();
				}
			}
			
		});
		
		// Initialisation de la liste d'affichage, mise � jour de son contenu, affichage et param�trage menu contextuel
		liste = (ListView)findViewById(R.id.listViewAnnees);
		manipRemboursementBDD = new RemboursementDAO(this);
		
		// Mise � jour de la liste, affichage et gestion du menu contextuel.
		miseAJourListe();
		registerForContextMenu(liste);
	}
	
	private void miseAJourListe() {
		// On r�cup�re tous les remboursements et on pr�pare la liste d'affichage
		manipRemboursementBDD.open(true);
		listeRemboursements = manipRemboursementBDD.getAllRemboursementsByIdAssociation(intent.getLongExtra("EXTRA_ASSOCIATION_ID", -1));
		listeAdapter = new AdaptateurListeRemboursements(this, listeRemboursements);
		liste.setAdapter(listeAdapter);
		manipRemboursementBDD.close();
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
		if (v.getId() == R.id.listViewAnnees) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(listeRemboursements.get(info.position).getAnnee());
			menu.add(Menu.NONE, 0, 0, getString(R.string.effacer));
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
		// Effacement du d�placement.
		manipRemboursementBDD.open(false);
		manipRemboursementBDD.deleteRemboursementByIdAssociationAndAnnee(
				listeRemboursements.get(info.position).getIdAssociation(),
				listeRemboursements.get(info.position).getAnnee());
		manipRemboursementBDD.close();
			
		// Mise � jour de la liste.
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
			
		// Message de confirmation.
		Toast.makeText(this, getString(R.string.suppression_effectuee), Toast.LENGTH_SHORT).show();
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
		miseAJourListe();
		listeAdapter.notifyDataSetChanged();
	}
}
