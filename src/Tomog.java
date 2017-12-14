import instances.EtatCase;


public class Tomog {
	public Grille g;
	public Boolean[][] TTL;
	public Boolean[][] TTC;
	public Boolean[][] TT;
	public int nb;

	public Tomog(Grille g) {
		this.g = g;
	}

	public void initTTVec(int n, int m){
		TT = new Boolean[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				TT[i][j] = null;
			}
		}
	}

	public boolean Enumeration(int k,int c){
		boolean ok, raz;
		int i, j;
		i = (k/g.m);
		j = (k % g.m);

		if(g.grille[i][j] == 0){
			g.grille[i][j] = c;
			raz = true;
		}
		else {
			if(g.grille[i][j] != c){
				return false;
			}
			else {
				raz = false;
			}
		}

		ok = true;
		if(g.n > 1){
			if(i == (g.n - 1)){
				ok = g.compareSeqCol(j);
			}
		}
		if(g.m > 1){
			if(ok && j == (g.m - 1)){
				ok = g.compareSeqLig(i);
			}
		}
		if(ok){
			if((i == (g.n - 1)) && (j == (g.m - 1))){
				return true;
			}
			ok = (this.Enumeration(k + 1, 1)) || (this.Enumeration(k+1, 2));
		}
		if((!ok) && raz){
			g.grille[i][j] = 0;
		}
		return ok;
	}

	public boolean TestSiAucunLigne(Grille v, int j1, int j2, int val, int indice){
		for (int k = j1; k <= j2; k++) {
			if(v.grille[indice][k] == val)
				return false;
		}
		return true;
	}
	public boolean TestSiAucunColonne(Grille v, int j1, int j2, int val, int indice){
		for (int k = j1; k <= j2; k++) {
			if(v.grille[k][indice] == val)
				return false;
		}
		return true;
	}

	//elle est li a propagation
	public boolean TestVecteurLigne_Rec(int i, int j, int l){
		boolean c1;
		boolean c2;

		if(l == 0)
			return TestSiAucunLigne(g, 0, j, 2,i);
		if((l == 1) && (j == (g.SeqL[i][l-1]-1))){
			return TestSiAucunLigne(g, 0, j, 1,i);
		}
		if(j <= (g.SeqL[i][l-1]-1)){
			return false;
		}
		if(TTL[j][l-1] != null){
			return TTL[j][l-1];
		}
		if(g.grille[i][j] == 2){
			c1 = false;
		}
		else
			c1 = TestVecteurLigne_Rec(i,j-1, l);

		if(!TestSiAucunLigne(g,(j-(g.SeqL[i][l-1]-1)),j,1,i)){
			c2 = false;
		}
		else{
			if(g.grille[i][j-g.SeqL[i][l-1]] == 2){

				c2 = false;
			}
			else
				c2 = TestVecteurLigne_Rec(i,j-g.SeqL[i][l-1]-1, l-1);
		}
		TTL[j][l-1] = (c2 || c1);
		return TTL[j][l-1];

	}
	//elle est li a propagation
	public boolean TestVecteurColonne_Rec(int i, int j, int l){
		boolean c1;
		boolean c2;
		if(l == 0)
			return TestSiAucunColonne(g, 0, j, 2,i);
		if((l == 1) && (j == (g.SeqC[i][l-1]-1))){
			return TestSiAucunColonne(g, 0, j, 1,i);
		}
		if(j <= (g.SeqC[i][l-1]-1)){
			return false;
		}

		if(TTC[j][l-1] != null){
			return TTC[j][l-1];
		}
		if(g.grille[j][i] == 2){
			c1 = false;
		}
		else
			c1 = TestVecteurColonne_Rec(i,(j-1), l);

		if(!TestSiAucunColonne(g,(j-(g.SeqC[i][l-1]-1)),j,1,i)){
			c2 = false;
		}
		else{
			if(g.grille[(j-((g.SeqC[i][l-1])))][i] == 2){

				c2 = false;
			}
			else
				c2 = TestVecteurColonne_Rec(i,(j-g.SeqC[i][l-1]-1), l-1);
		}
		TTC[j][l-1] = (c2 || c1);
		return TTC[j][l-1];
	}


	public boolean PropagCol(int j, boolean[] marqueL){
		boolean c1,c2;
		int cptcolor;
		cptcolor = 0;
		nb = 0;
		TTC = new Boolean[g.n][g.tailleSeqC[j]];

		for (int i = 0; i < g.n; i++) {
			if(g.grille[i][j] == 0){
				g.grille[i][j] = 1;

				for (int v = 0; v < g.n; v++) {
					for (int b = 0; b < g.tailleSeqC[j]; b++) {
						TTC[v][b] = null;
					}
				}

				c1 = TestVecteurColonne_Rec(j,g.n-1,g.tailleSeqC[j]);

				g.grille[i][j] = 2;

				for (int v = 0; v < g.n; v++) {
					for (int b = 0; b < g.tailleSeqC[j]; b++) {
						TTC[v][b] = null;
					}
				}


				c2 = TestVecteurColonne_Rec(j,g.n-1,g.tailleSeqC[j]);

				g.grille[i][j] = 0;
				if((!c1) && (!c2)){
					return false;
				}
				if((c1) && (!c2)){
					g.grille[i][j] = 1;
					cptcolor++;
					if(!marqueL[i]){
						marqueL[i] = true;
						nb++;
					}
				}
				if((!c1) && (c2)){
					g.grille[i][j] = 2;
					cptcolor++;
					if(!marqueL[i]){
						marqueL[i] = true;
						nb++;
					}
				}
			}
		}
		return true;
	}

	public boolean PropagLigne(int i, boolean[] marqueC){
		boolean c1,c2;
		int cptcolor;
		cptcolor = 0;
		nb = 0;
		TTL = new Boolean[g.m][g.tailleSeqL[i]];
		for (int j = 0; j < g.m; j++) {
			if(g.grille[i][j] == 0){
				g.grille[i][j] = 1;

				//initTT(g.m, g.tailleSeqL[i]);
				for (int v = 0; v < g.m; v++) {
					for (int b = 0; b < g.tailleSeqL[i]; b++) {
						TTL[v][b] = null;
					}
				}

				c1 = TestVecteurLigne_Rec(i,g.m-1,g.tailleSeqL[i]);
				g.grille[i][j] = 2;

				//initTT(g.m, g.tailleSeqL[i]);

				for (int v = 0; v < g.m; v++) {
					for (int b = 0; b < g.tailleSeqL[i]; b++) {
						TTL[v][b] = null;
					}
				}

				c2 = TestVecteurLigne_Rec(i,g.m-1,g.tailleSeqL[i]);
				g.grille[i][j] = 0;
				if((!c1) && (!c2)){
					return false;
				}
				if((c1) && (!c2)){
					g.grille[i][j] = 1;
					cptcolor++;
					if(!marqueC[j]){
						marqueC[j] = true;
						nb++;
					}
				}
				if((!c1) && (c2)){
					g.grille[i][j] = 2;
					cptcolor++;
					if(!marqueC[j]){
						marqueC[j] = true;
						nb++;
					}
				}
			}
		}
		return true;
	}

	public boolean Propagation(){
		boolean marqueL[] = new boolean[g.n];
		boolean marqueC[] = new boolean[g.m];

		int nbmL = g.n;
		int nbmC = g.m;

		int i;
		int j;

		boolean ok = true;
		for (int k = 0; k < g.m; k++) {
			marqueC[k] = true;
		}
		for (int p = 0; p < g.n; p++) {
			marqueL[p] = true;
		}
		while (ok && ((nbmL != 0)||(nbmC != 0))) {
			i = 0;
			while (ok && (i < g.n)){
				if(marqueL[i]){
					ok = PropagLigne(i,marqueC);
					nbmC = nbmC+nb;
					marqueL[i] = false;
					nbmL--;
				}
				i++;
			}
			j = 0;
			while (ok && (j < g.m)) {
				if(marqueC[j]){
					ok = PropagCol(j,marqueL);
					nbmL = nbmL+nb;
					marqueC[j] = false;
					nbmC--;
				}
				j++;
			}
		}
		return ok;
	}
}
