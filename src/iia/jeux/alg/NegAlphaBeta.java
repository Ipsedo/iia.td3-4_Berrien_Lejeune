package iia.jeux.alg;

import java.util.ArrayList;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class NegAlphaBeta implements AlgoJeu {
	
	/** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilisée par l'algorithme
      */
   private Heuristique h;

    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;

    /**  Le nombre de noeuds développé par l'algorithme
     * (intéressant pour se faire une idée du nombre de noeuds développés) */
       private int nbnoeuds;

    /** Le nombre de feuilles évaluées par l'algorithme
     */
    private int nbfeuilles;
    
    public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
    }
    
    public String toString() {
        return "NegAlphaBeta(ProfMax="+profMax+")";
    }
	
	public CoupJeu meilleurCoup(PlateauJeu p) {
		this.nbfeuilles = 0;
		this.nbnoeuds = 0;
		ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(this.joueurMax);
		int alpha = Integer.MIN_VALUE+1;
		int beta = Integer.MAX_VALUE-1;
	    PlateauJeu tmpP = p.copy();
		CoupJeu meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.joue(this.joueurMax, meilleurCoup);
		alpha = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
		for(CoupJeu c : coupsPossibles){
			tmpP = p.copy();
			tmpP.joue(this.joueurMax, c);
			int newVal = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
			System.out.println(newVal);
			if(newVal > alpha){
				meilleurCoup = c;
				alpha = newVal;
			}
		}
		System.out.println("nbFeuilles : "+this.nbfeuilles+", nbNoeuds : "+this.nbnoeuds);
		   		   
	    return meilleurCoup;
	}
	
	private int negAB(int pronf, PlateauJeu plat, int alpha, int beta, int parité){
		Joueur joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		if(pronf <= 0 || plat.finDePartie()){
			this.nbfeuilles++;
			return parité * this.h.eval(plat, joueur);
		}else{
			for(CoupJeu c : plat.coupsPossibles(joueur)){
				this.nbnoeuds++;
	    		PlateauJeu tmp = plat.copy();
	    		tmp.joue(joueur, c);
	    		int tmpA = -negAB(pronf - 1, tmp, -beta, -alpha, -parité);
	    		alpha = Math.max(alpha, tmpA);
	    		if(alpha >= beta){
	    			return beta;
	    		}
			}
		}
		return alpha;
	}
}
