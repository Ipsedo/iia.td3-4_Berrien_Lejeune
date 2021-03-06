package jeux.dominos;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesDominos{

	public static  Heuristique hblanc = new Heuristique(){
				
		public int eval(PlateauJeu p, Joueur j){
			/*A COMPLETER*/
			PlateauDominos tmpP = (PlateauDominos) p;
			return tmpP.nbCoupsBlanc() - tmpP.nbCoupsNoir();
		}
	};

	public static  Heuristique hnoir = new Heuristique(){
	
		public int eval(PlateauJeu p, Joueur j){
			/*A COMPLETER*/
			PlateauDominos tmpP = (PlateauDominos) p;
			return tmpP.nbCoupsNoir() - tmpP.nbCoupsBlanc();
		}
	};

}
