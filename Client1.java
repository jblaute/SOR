package files;

import org.omg.CORBA.*;

import java.io.*;

import java.lang.*;

import java.util.*;


public class Client1 {
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
			//th.sleep(2000);  
 			System.out.println("nombre  du rep créer"+dir.number_of_file());  
    
    } catch (already_exist ae) {
      // a implémenter
				System.err.println("répertoire existe déjà");
        System.exit(1);
			}
			/*catch (InterruptedException ie){
				System.err.println("erreur de thread");
        System.exit(1);
    	}*/
 		////////////////////////////////////////////////////
    // Test création d'un fichier
    ////////////////////////////////////////////////////
		 try {
      // a implémenter
			dir = directoryHelper.narrow(obj);
			regular_fileHolder regFileHold = new regular_fileHolder(); 
      dir.create_regular_file(regFileHold, "MonPremierFichier");
			
 			System.out.println("nombre  du rep créer"+dir.number_of_file());
			
			file_listHolder fl1 = new file_listHolder();
			
			
			
			directory_entryHolder dr1 = new directory_entryHolder();
			dr1.value = new directory_entry("",file_type.regular_file_type);
			int nbe = dir.list_files(fl1);
			System.out.println("nombre  d'élé : "+nbe);
			if (fl1.value.next_one(dr1)){

				System.out.println("nom du premier élément : "+dr1.value.name);
			}
			//System.out.println("nom du fichier :"+.value.name);  
    
    	} catch (already_exist ae) {
      // a implémenter
				System.err.println("le fichier existe déjà");
				
				
        System.exit(1);
			}
    
	//////////////////////////////////////////			
	// Test d'ouverture d'un fichier existant en mode read/write
	//////////////////////////////////////////
	System.out.println("On tente d'ouvrir un fichier existant : MonPremierFichier");
	try{
		regular_fileHolder openedFileHold = new regular_fileHolder();
		dir.open_regular_file(openedFileHold, "MonPremierFichier", mode.read_write);
		System.out.println("fichier "+openedFileHold.value.reg_name()+" ouvert");
	}catch(Exception e){
		System.out.println(e);
	}

	try{
		regular_fileHolder openedFileHold_again = new regular_fileHolder();
		dir.open_regular_file(openedFileHold_again, "MonPremierFichier", mode.read_write);
		System.out.println("fichier "+openedFileHold_again.value.reg_name()+" ouvert");

		System.out.println("On tente d'écrire quelques caractères : je suis content");
		openedFileHold_again.value.write(15,"je suis content");
		System.out.println("On ferme le fichier pour ensuite le réouvrir en lecture");
		openedFileHold_again.value.fermer();
		dir.open_regular_file(openedFileHold_again, "MonPremierFichier", mode.read_only);
		System.out.println("On se déplace de 5 caractères");
		openedFileHold_again.value.seek(5);
		System.out.println("On lit le 5 prochain caractères (resultat attendu : 'is co')");
		StringHolder texteHold=new StringHolder();
		texteHold.value=new String();
		openedFileHold_again.value.read(5,texteHold);
		System.out.println("texte obtenu : "+texteHold.value);

	}catch(Exception e){
		System.out.println(e);
	}


	
	
	
	
	////////////////////////////////////////////			
	// Test d'ouverture d'un fichier inexistant
	////////////////////////////////////////////
	System.out.println("On tente de récuperer un fichier inexistant : MonSecondFichier");
	try{
		regular_fileHolder openedFileHold2 = new regular_fileHolder();
		dir.open_regular_file(openedFileHold2, "MonSecondFichier", mode.read_only);
		System.out.println("Nom du fichier récupéré : "+openedFileHold2.value.reg_name());
	}catch(Exception e){
		System.out.println(e);
	}
	
	/////////////////////////////////////////////			
	// Test d'ouverture d'un repertoire existant
	/////////////////////////////////////////////
	System.out.println("On tente de récuperer un répertoire existant : MonPremierDir");
	try{
		directoryHolder createdDirHold = new directoryHolder();
		dir.open_directory(createdDirHold, "MonPremierDir");
		System.out.println("Nom du dossier récupéré : "+createdDirHold.value.dir_name());
	}catch(Exception e){
		System.out.println(e);
	}

	///////////////////////////////////////////////			
	// Test d'ouverture d'un repertoire inexistant
	///////////////////////////////////////////////
	System.out.println("On tente de récuperer un répertoire existant : MonSecondDir");
	try{
		directoryHolder createdDirHold2 = new directoryHolder();
		dir.open_directory(createdDirHold2, "MonSecondDir");
		System.out.println("Nom du dossier récupéré : "+createdDirHold2.value.dir_name());
	}catch(Exception e){
		System.out.println(e);
	}
	
	
    System.exit(0);
  }
}
