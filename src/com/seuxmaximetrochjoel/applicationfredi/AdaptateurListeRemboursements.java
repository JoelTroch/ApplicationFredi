package com.seuxmaximetrochjoel.applicationfredi;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

/**
 * Classe utilitaire pour l'affichage des remboursements, les classes d'Android n'étant pas assez "évoluées".
 * @author Joël Troch
 */
@SuppressWarnings("deprecation")
public class AdaptateurListeRemboursements extends BaseAdapter {
	
	// ====================================================================================================
	// ATTRIBUTS
	// ====================================================================================================
	
	private Context context = null;
	private ArrayList<Remboursement> liste = null;
	
	// ====================================================================================================
	// METHODES
	// ====================================================================================================
	
	/**
	 * Constructeur de la classe AdaptateurListeRemboursements.
	 * @param context Contexte de l'application.
	 * @param liste Liste des remboursements.
	 * @see http://developer.android.com/reference/android/widget/BaseAdapter.html
	 */
	public AdaptateurListeRemboursements(Context context, ArrayList<Remboursement> liste) {
		this.context = context;
		this.liste = liste;
	}
	
	/**
	 * Récupère le nombre de remboursements dans la liste.
	 * @return Nombre de remboursements.
	 * @see http://developer.android.com/reference/android/widget/Adapter.html#getCount()
	 */
	@Override
    public int getCount() {
        return liste.size();
    }
	
	/**
	 * Récupère un remboursement selon sa position dans la liste.
	 * @return Remboursement à la position spécifiée dans la liste.
	 * @see http://developer.android.com/reference/android/widget/Adapter.html#getItem(int)
	 */
	@Override
    public Object getItem(int position) {
        return liste.get(position);
    }

	/**
	 * Récupère l'ID de la ligne dans la liste.
	 * @return 0
	 * @see http://developer.android.com/reference/android/widget/Adapter.html#getItemId(int)
	 */
    @Override
    public long getItemId(int position) {
        return 0;
    }
	
    /**
     * Surcharge de la méthode "getView" de "BaseAdapter".
     * @param position Position de l'élement dans l'adaptateur.
     * @param convertView L'ancienne vue à utiliser.
     * @param parent Parent auquel cette vue sera attachée.
     * @return Vue modifiée.
     * @see http://developer.android.com/reference/android/widget/Adapter.html#getView(int, android.view.View, android.view.ViewGroup)
     */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TwoLineListItem elements;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			elements = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);
		} else
			elements = (TwoLineListItem)convertView;
		
		TextView texte1 = elements.getText1();
		TextView texte2 = elements.getText2();
		texte1.setText(liste.get(position).getAnnee());
		texte2.setText((liste.get(position).getDoitRembourser() == 1) ? "Doit rembourser" : "Ne doit pas rembourser");
		return elements;
	}
}
