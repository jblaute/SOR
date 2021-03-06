package files;

import org.omg.CORBA.*;

import java.io.*;

import java.lang.*;

import java.util.*;


public class Client {
  public static void main(String[] args) throws IOException {
    ////////////////////////////////////////////////////
    // On initialise l'ORB
    ////////////////////////////////////////////////////
    ORB orb = ORB.init(args, null);

   /* if (args.length != 1) {
        System.err.println("utilisation : Client nombre");
        System.exit(1);
    }*/

    ////////////////////////////////////////////////////
    // Recuperation de la reference d'objet du serveur
    // Dans cet exemple, la reference est stockee sous
    // la forme d'une chaine de caracteres (IOR) dans un
    // fichier. A ce stade, il est bien sur possible 
    // d'invoquer un service de nommage.
    ////////////////////////////////////////////////////
    String ior = null;

    try {
        String nameFile = "gestiondirectory.ref";
        FileInputStream file = new FileInputStream(nameFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        ior = br.readLine();
        file.close();
    } catch (IOException ex) {
        System.err.println("Impossible de lire fichier : `" +
            ex.getMessage() + "'");
        System.exit(1);
    }

    ////////////////////////////////////////////////////
    // Construction d'une reference d'objet, non type d'abord,
    // puis "cast" en une reference sur l'interface 
    // "calcul"  avec narrow (generation d'une souche)
    ////////////////////////////////////////////////////
    org.omg.CORBA.Object obj = orb.string_to_object(ior);

    if (obj == null) {
        System.err.println("Erreur sur string_to_object() ");
        throw new RuntimeException();
    }
		
    directory dir = directoryHelper.narrow(obj);

    if (dir == null) {
        System.err.println("Erreur sur narrow() ");
        throw new RuntimeException();
    }

    ////////////////////////////////////////////////////
    // Test création d'un répertoire
    ////////////////////////////////////////////////////
  
    try {
      // a implémenter

			directoryHolder dirHold = new directoryHolder(); 
      dir.create_directory(dirHold, "MonPremierDir");
			Thread th = new Thread();
			th.sleep(2000);  
 			System.out.println("nombre  du rep créer"+dir.number_of_file());  
    
    } catch (already_exist ae) {
      // a implémenter
				System.err.println("répertoire existe déjà");
        System.exit(1);
			}
			catch (InterruptedException ie){
				System.err.println("erreur de thread");
        System.exit(1);
    	}

    System.exit(0);
  }
}
