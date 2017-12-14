import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class TestTom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//File file = new File("C:/Users/Hakimb/Documents/workspaceL3Web/TomographieDiscrete/src/instances/2.tom");
		File file = new File("instances/14.tom");
		Grille grille = new Grille(file);
		Tomog t = new Tomog(grille);


		if(t.Propagation())
			System.out.println("Propagation : vrai");// elle fonctionne !
		if(t.Enumeration(0, 2) || t.Enumeration(0, 1))
				System.out.println("Enum : vrai");
		System.out.println(t.g);





	}

}
