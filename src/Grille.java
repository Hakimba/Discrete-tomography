import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class Grille {
	public int n;
	public int m;
	public int[][] grille ;
	public int[] tailleSeqL;
	public int[] tailleSeqC;
	public int[][] SeqL;
	public int[][] SeqC;

	public Grille(File file) {

		//Toute la partie ou j'extrait le contenu du fichier pour crer matrice/vecteur
		try(BufferedReader buff = new BufferedReader(new FileReader(file))){

			String[] dim = buff.readLine().split(" ");
			StringTokenizer tk;

			tailleSeqL = new int[Integer.parseInt(dim[0])];
			tailleSeqC = new int[Integer.parseInt(dim[1])];

			n = tailleSeqL.length;
			m = tailleSeqC.length;

			SeqL = new int[n][1];
			SeqC = new int[m][1];

			for (int i = 0; i < tailleSeqL.length; i++) {
				//seq = buff.readLine().split(" ");
				tk = new StringTokenizer(buff.readLine());
				tailleSeqL[i] = Integer.parseInt(tk.nextToken());
				SeqL[i] = new int[tailleSeqL[i]];

				for (int j = 0; j < SeqL[i].length; j++) {
					SeqL[i][j] = Integer.parseInt(tk.nextToken());
				}
			}
			buff.readLine(); // pour passer la ligne blanche des fichiers

			for (int i = 0; i < tailleSeqC.length; i++) {
				//seq = buff.readLine().split(" ");
				tk = new StringTokenizer(buff.readLine());
				tailleSeqC[i] = Integer.parseInt(tk.nextToken());
				SeqC[i] = new int[tailleSeqC[i]];

				for (int j = 0; j < SeqC[i].length; j++) {
					//System.out.println(tk.nextToken());
					SeqC[i][j] = Integer.parseInt(tk.nextToken());
				}
			}
		}
		catch(IOException e){
			System.out.println("Probleme de fichier");
			e.printStackTrace();
		}

		//Initialisation de toute les structures et variable utile

		grille = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				grille[i][j] = 0;
			}
		}
		System.out.println(n+" "+m);

	}

	public Grille(int[][] g, int[] tsl, int[] tsc, int[][] sl,int[][] sc){
		tailleSeqL = tsl;
		tailleSeqC = tsc;

		SeqL = sl;
		SeqC = sc;

		grille = g;
	}


	public boolean compareSeqCol(int j){
		boolean idem = false;
		if(tailleSeqC[j] > 0){
			int[] repliqSeqCol = new int[this.n];
			int cpt = 1;

			for (int k = 0; k < tailleSeqC[j]; k++) {
				repliqSeqCol[k] = 0;
			}

			for (int k = 0; k < this.n; k++) {
				if(grille[k][j] == 2){
					repliqSeqCol[(cpt-1)]++;
				}
				if(repliqSeqCol[(cpt-1)] > 0 && grille[k][j] == 1){
					cpt++;
				}
			}

			for (int k = 0; k < tailleSeqC[j]; k++) {
				if(repliqSeqCol[k] == SeqC[j][k])
					idem = true;
				else
					return false;
			}
		}
		else{
			for (int i = 0; i < this.n; i++) {
				if(grille[i][j] == 2){
					return false;
				}
			}
			idem = true;
		}
		return idem;
	}
	//La taille de la ligne est born par m donc algo en O(m)
	public boolean compareSeqLig(int i){
		boolean idem = false;
		if(tailleSeqL[i] > 0){
			int[] repliqSeqLigne = new int[this.m];
			int cpt = 1;

			for (int j = 0; j < tailleSeqL[i]; j++) {
				repliqSeqLigne[j] = 0;
			}

			for (int j = 0; j < this.m; j++) {
				if(grille[i][j] == 2){
					repliqSeqLigne[(cpt-1)]++;
				}
				if(repliqSeqLigne[(cpt-1)] > 0 && grille[i][j] == 1){
					cpt++;
				}
			}

			for (int j = 0; j < tailleSeqL[i]; j++) {
				if(repliqSeqLigne[j] == SeqL[i][j])
					idem = true;
				else
					return false;
			}
		}
		else{
			for (int j = 0; j < this.m; j++) {
				if(grille[i][j] == 2 || grille[i][j] == 0){
					return false;
				}
			}
			idem = true;
		}
		return idem;
	}

	public String toString(){
		String renderer = "Matrice de "+this.n+" x "+this.m+"\n";
		for (int j = 0; j < this.n ; j++ ) {
			renderer+="\n[ ";
			for (int k = 0;k < this.m ; k++ ) {
				if(grille[j][k] == 2)
					renderer +="o ";
				else
					renderer +=". ";
				//renderer +=grille[j][k]+" ";
			}
			renderer += "]";
		}
		renderer += "\n";
		return renderer;
	}


	//affichage si la grille est un vecteur
	public String toStringVec(Boolean[][] TT){
		String renderer = "TT : ";
		for (int j = 0; j < TT.length ; j++ ) {
			renderer+="\n[ ";
			for (int k = 0;k < TT[j].length ; k++ ) {
				renderer += TT[j][k]+" ";
			}
			renderer += "]";
		}
		renderer += "\n";
		return renderer;
	}
}
