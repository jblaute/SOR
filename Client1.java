package files;

import org.omg.CORBA.*;

import java.io.*;

import java.lang.*;

import java.util.*;


public class Client1 {
  public static void main(String[] args) throws IOException {
    System.out.println();
    System.out.println();
    System.out.println();
    ////////////////////////////////////////////////////
    // On initialise l'ORB
    ////////////////////////////////////////////////////
    ORB orb = ORB.init(args, null);
    System.out.println("Création de l'ORB : Ok");

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
        System.out.println("Récupératon de la référence d'objet du serveur : Ok");
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
    System.out.println();
    System.out.println("Test de création d'un répertoire nommé : MonPremierDir");

    try {

        directoryHolder dirHold = new directoryHolder(); 
        dir.create_directory(dirHold, "MonPremierDir");
		Thread th = new Thread();
		//th.sleep(2000);  
 		System.out.println("Nombre d'éléments dans le répertoire parent : "+dir.number_of_file());
    
    }catch (already_exist ae){
		System.err.println("Ce répertoire existe déjà");
        System.exit(1);
    }

    System.out.println();
    System.out.println("Test de création d'un fichier nommé : MonPremierFichier");

 	////////////////////////////////////////////////////
    // Test création d'un fichier
    ////////////////////////////////////////////////////

    try {
    	dir = directoryHelper.narrow(obj);
		regular_fileHolder regFileHold = new regular_fileHolder(); 
        dir.create_regular_file(regFileHold, "MonPremierFichier");
			
 		System.out.println("Nombre d'éléments dans le répertoire parent : "+dir.number_of_file());
			
		file_listHolder fl1 = new file_listHolder();
			
		directory_entryHolder dr1 = new directory_entryHolder();
		dr1.value = new directory_entry("",file_type.regular_file_type);
		int nbe = dir.list_files(fl1);
		if (fl1.value.next_one(dr1)){
			System.out.println("Nom du premier élément : "+dr1.value.name);
		}
   	} catch (already_exist ae) {
		System.err.println("le fichier existe déjà");
        System.exit(1);
	}
    
    System.out.println();
	System.out.println("On tente d'ouvrir un fichier existant : MonPremierFichier en mode lecture");

	//////////////////////////////////////////			
	// Test d'ouverture d'un fichier existant en mode read/write
	//////////////////////////////////////////

	regular_fileHolder openedFileHold = new regular_fileHolder();
	try{
		dir.open_regular_file(openedFileHold, "MonPremierFichier", mode.read_only);
		System.out.println("fichier "+openedFileHold.value.reg_name()+" ouvert en mode lecture");
	}catch(Exception e){
		System.out.println(e);
	}
	try{
	    System.out.println("On tente d'écrire quelques caractères : Vive corba !");
	    openedFileHold.value.write(12,"Vive corba !");
	}catch(Exception e){
		System.out.println(e);
        System.out.println("Et cela ne fonctionnes pas... (ouf !)");
	}
   	System.out.println("On referme le fichier, et on tente de l'ouvrir mode write_trunc");
   	try{
        openedFileHold.value.fermer();
	}catch(Exception e){
		System.out.println(e);
	}

	try{
		dir.open_regular_file(openedFileHold, "MonPremierFichier", mode.write_trunc);
		System.out.println("fichier "+openedFileHold.value.reg_name()+" ouvert");
	}catch(Exception e){
		System.out.println(e);
	}
	System.out.println("On tente d'écrire quelques caractères : je suis content");
	try{
    	openedFileHold.value.write(15,"je suis content");
	}catch(Exception e){
		System.out.println(e);
	}

	System.out.println("et on tente de le lire :");
	StringHolder texteHold1=new StringHolder();
	texteHold1.value=new String();

	try{
    	openedFileHold.value.read(5,texteHold1);
	}catch(Exception e){
		System.out.println(e);
    	System.out.println("Et cela ne fonctionnes pas... (décidément...)");
	}
    System.out.println();
	System.out.println("On ferme le fichier pour ensuite le réouvrir en lecture seule");

   	try{
        openedFileHold.value.fermer();
	}catch(Exception e){
		System.out.println(e);
	}
	try{
		dir.open_regular_file(openedFileHold, "MonPremierFichier", mode.read_only);
		System.out.println("fichier "+openedFileHold.value.reg_name()+" ouvert");
	}catch(Exception e){
		System.out.println(e);
	}
    System.out.println();
	System.out.println("On se déplace de 5 caractères");
   	try{
		openedFileHold.value.seek(5);
	}catch(Exception e){
		System.out.println(e);
	}
    System.out.println();
	System.out.println("On lit le 5 prochain caractères (resultat attendu : 'is co')");
	StringHolder texteHold=new StringHolder();
	texteHold.value=new String();
   	try{
    	openedFileHold.value.read(5,texteHold);
		System.out.println("texte obtenu : "+texteHold.value);
		System.out.println("\\o/ youpi !");
	}catch(Exception e){
		System.out.println(e);
	}
   	try{
        openedFileHold.value.fermer();
	}catch(Exception e){
		System.out.println(e);
	}
    
	
	////////////////////////////////////////////			
	// Test d'ouverture d'un fichier inexistant
	////////////////////////////////////////////
	System.out.println("On tente maintenant d'ouvrir un fichier inexistant : MonSecondFichier");
	try{
		dir.open_regular_file(openedFileHold, "MonSecondFichier", mode.read_only);
	}catch(Exception e){
		System.out.println(e);
		System.out.println("Et comme dirait un étudiant compilant son premier CORBA : 'Ca marche pas !'");
	}
	
	/////////////////////////////////////////////			
	// Test d'ouverture d'un repertoire existant
	/////////////////////////////////////////////
	System.out.println("On tente d'ouvrir un répertoire existant : MonPremierDir");
	directoryHolder createdDirHold = new directoryHolder();
	try{
		dir.open_directory(createdDirHold, "MonPremierDir");
		System.out.println("Nom du dossier récupéré : "+createdDirHold.value.dir_name());
	}catch(Exception e){
		System.out.println(e);
	}

	///////////////////////////////////////////////			
	// Test d'ouverture d'un repertoire inexistant
	///////////////////////////////////////////////
	System.out.println("On tente d'ouvrir un répertoire inexistant : MonSecondDir");
	try{
		dir.open_directory(createdDirHold, "MonSecondDir");
		System.out.println("Nom du dossier récupéré : "+createdDirHold.value.dir_name());
	}catch(Exception e){
		System.out.println(e);
		System.out.println("Raspe et gaspe ! : 'Ca marche pas !'");
	}


	System.out.println("Petit état des lieux :");
	
	///////////////////////////////////////////////			
	// Test de suppression d'un fichier
	///////////////////////////////////////////////
	System.out.println("On tente de supprimer un fichier: MonSecondFichier");
	try{
		dir.delete_file("MonSecondFichier");
	}catch(Exception e){
		System.out.println(e);
	}
	System.out.println("On tente de l'ouvrir, histoire de voir...");
	//on l'ouvre et on attend le renvoie de l'exception il n'existe pas
	try{
		dir.open_regular_file(openedFileHold, "MonSecondFichier", mode.read_only);
		System.out.println("Ca marche !'");
	}catch(Exception e){
		System.out.println(e);
		System.out.println("Décidement... : 'Ca marche pas !'");
	}
  System.exit(0);
  }
}
