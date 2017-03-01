package files;

import org.omg.CORBA.*;

import java.io.*;

import java.lang.*;
import java.io.Console;
import java.util.*;


public class Client_interactif {
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
        boolean sortie=false;
        String commande="";
        String nom="";
        int numCommande=0;
	    regular_fileHolder monFic = new regular_fileHolder();
	    mode modeFic = mode.read_only;
        while(!sortie){
            System.out.println("Que souhaitez vous faire ?");
            System.out.println();
            System.out.println("0) Sortir");
            System.out.println("1) Lister les éléments");
            System.out.println("2) Créer un répertoire");
            System.out.println("3) Ouvrir un répertoire");
            System.out.println("4) Créer un fichier");
            System.out.println("5) Suprimer un fichier");
            System.out.println("6) Ouvrir un fichier");
            System.out.println("7) Ecrire dans le fichier");
            System.out.println("8) Se déplacer dans le fichier");
            System.out.println("9) Lire le fichier");
            System.out.println("10) Fermer le fichier");
            commande = System.console().readLine();
            try{
                numCommande = Integer.parseInt(commande);
            }catch(Exception e){
                numCommande = 99;
            }
            switch(numCommande){
                case 0:
                    sortie=true;
                break;
                case 1:
                    listerElement(dir);
                break;
                case 2:
                    System.out.println("------------------------");
                    System.out.println("Création d'un répertoire");
                    System.out.println("------------------------");
                    System.out.print("Nom du nouveau répertoire : ");
                    nom = System.console().readLine();
                    creationRepertoire(dir,nom);
                break;
                case 3:
                    System.out.println("-------------------------");
                    System.out.println("Ouverture d'un répertoire");
                    System.out.println("-------------------------");
                    System.out.print("Nom du répertoire : ");
                    nom = System.console().readLine();
                    ouvrirRepertoire(dir, nom);
                break;
                case 4:
                    System.out.println("---------------------");
                    System.out.println("Création d'un fichier");
                    System.out.println("---------------------");
                    System.out.print("Nom du nouveau fichier : ");
                    nom = System.console().readLine();
                    creationFichier(dir, nom);
                break;
                case 5:
                    System.out.println("------------------------");
                    System.out.println("Suppression d'un fichier");
                    System.out.println("------------------------");
                    System.out.print("Nom du nouveau fichier : ");
                    nom = System.console().readLine();
                    supprimerFichier(dir,nom);
                break;
                case 6:
                    System.out.println("------------------------");
                    System.out.println("Ouverture d'un fichier");
                    System.out.println("------------------------");
                    System.out.print("Nom du fichier : ");
                    nom = System.console().readLine();
                    System.out.println("1) En mode lecture seule");
                    System.out.println("2) En mode lecture/ecriture");
                    System.out.println("3) En mode ajout");
                    System.out.println("4) En mode écrasement");
                    
                    commande = System.console().readLine();
                    try{
                        numCommande = Integer.parseInt(commande);
                    }catch(Exception e){
                        numCommande = 99;
                    }
                    switch(numCommande){
                       case 1:
                            modeFic=mode.read_only;
                       break;
                       case 2:
                            modeFic=mode.read_write;
                       break;
                       case 3:
                            modeFic=mode.write_append;
                       break;
                       case 4:
                            modeFic=mode.write_trunc;
                       break;
                    }
                    monFic = ouvrirFichier(dir,nom,modeFic);
                break;
                case 7:
                    System.out.println("------------------------");
                    System.out.println("Ecriture dans un fichier");
                    System.out.println("------------------------");
                    System.out.print("Texte à inserer : ");
                    nom = System.console().readLine();
                    ecrireFichier(dir,monFic, nom);
                    break;
                case 8:
                    System.out.println("---------------------------");
                    System.out.println("Déplacement dans un fichier");
                    System.out.println("---------------------------");
                    System.out.print("De combien de caractères : ");
                    commande = System.console().readLine();
                    try{
                        numCommande = Integer.parseInt(commande);
                    }catch(Exception e){
                        numCommande = -1;
                    }
                    if(numCommande!=-1){
                        deplacer(monFic,numCommande);
                    }
                    break;
                case 9:
                    System.out.println("---------------");
                    System.out.println("Lire un fichier");
                    System.out.println("---------------");
                    System.out.print("De combien de caractères : ");
                    commande = System.console().readLine();
                    try{
                        numCommande = Integer.parseInt(commande);
                    }catch(Exception e){
                        numCommande = -1;
                    }
                    if(numCommande>-1){
                        lire(monFic,numCommande);
                    }
                    break;
                case 10:
                    System.out.println("-----------------");
                    System.out.println("Fermer un fichier");
                    System.out.println("-----------------");
                    fermer(monFic);
                    break;
                default:
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("Je n'ai pas compris votre choix.");
                break;
            }
        }

        System.exit(0);
    }


    ////////////////////////////////////////////////////
    // Lire un fichier
    ////////////////////////////////////////////////////
    public static void lire(regular_fileHolder fic, int valeur)
    {
	    StringHolder texteHold=new StringHolder();
	    texteHold.value=new String();
       	try{
        	fic.value.read(valeur,texteHold);
            System.out.println("---------------------");
		    System.out.println(texteHold.value);
            System.out.println("---------------------");
	    }catch(Exception e){
		    System.out.println(e);
	    }
    }

    ////////////////////////////////////////////////////
    // Fermer un fichier
    ////////////////////////////////////////////////////
    public static void fermer(regular_fileHolder fic)
    {
        try{
            fic.value.fermer();
            System.out.println("--------------");
            System.out.println("Fichier fermé.");
            System.out.println("--------------");
	    }catch(Exception e){
		    System.out.println(e);
	    }
	}
	
    ////////////////////////////////////////////////////
    // Deplacer dans fichier
    ////////////////////////////////////////////////////
    public static void deplacer(regular_fileHolder fic, int valeur)
    {
       	try{
		    fic.value.seek(valeur);
            System.out.println("---------------------");
            System.out.println("Déplacement effectué.");
            System.out.println("---------------------");
	    }catch(Exception e){
		    System.out.println(e);
	    }
	}


    ////////////////////////////////////////////////////
    // Creer repertoire
    ////////////////////////////////////////////////////
    public static void creationRepertoire(directory dir, String nomRep)
    {
        directoryHolder dirHold = new directoryHolder(); 
        try {
            dir.create_directory(dirHold, nomRep);
            System.out.println("---------------------------------");
            System.out.println("Votre répertoire à bien été créé.");
            System.out.println("---------------------------------");
        }catch (already_exist ae){
		    System.err.println("/!\\Ce répertoire existe déjà/!\\");
        }
    }

    ////////////////////////////////////////////////////
    // Lister Elements
    ////////////////////////////////////////////////////
    public static void listerElement(directory dir){
	    file_listHolder fl1 = new file_listHolder();
	    directory_entryHolder dr1 = new directory_entryHolder();
	    dr1.value = new directory_entry("",file_type.regular_file_type);
	    int nbe = dir.list_files(fl1);
	    if(fl1.value.next_one(dr1)){
            System.out.println("---------------------------------------");
            System.out.println("Voici les éléments présents à ce niveau");
            System.out.println("---------------------------------------");
	        System.out.println(dr1.value.name);
	        while(fl1.value.next_one(dr1)){
		        System.out.println(dr1.value.name);
	        }
            System.out.println("---------------------------------------");
            System.out.println("------------ Fin de liste -------------");
            System.out.println("---------------------------------------");
        }
    }

    ////////////////////////////////////////////////////
    // Test création d'un fichier
    ////////////////////////////////////////////////////
    public static void creationFichier(directory dir, String nomFichier){
        try {
    	    regular_fileHolder newFic = new regular_fileHolder(); 
            dir.create_regular_file(newFic, nomFichier);
            System.out.println("------------------------------");
            System.out.println("Votre fichier à bien été créé.");
            System.out.println("------------------------------");
       	} catch (already_exist ae) {
		    System.err.println("/!\\Ce fichier existe déjà/!\\");
            System.exit(1);
	    }
    }
    
    ////////////////////////////////////////////			
    // Ecriture Fichier
    ////////////////////////////////////////////
    public static void ecrireFichier(directory dir,regular_fileHolder fic, String texte){
        try{
            System.out.println(texte.length()+" "+texte);
	        fic.value.write(texte.length(),texte);
            System.out.println("----------------------------------");
            System.out.println("Votre fichier à bien été alimenté.");
            System.out.println("----------------------------------");
	        
	    }catch(Exception e){
		    System.out.println(e);
	    }
    }
    
    ////////////////////////////////////////////			
    // Ouverture d'un fichier
    ////////////////////////////////////////////
    public static regular_fileHolder ouvrirFichier(directory dir, String nomFichier, mode modeOuv){
        regular_fileHolder monFicHold = new regular_fileHolder();
        try{
	        dir.open_regular_file(monFicHold, nomFichier, modeOuv);
            System.out.println("--------------------------------");
            System.out.println("Votre fichier à bien été ouvert.");
            System.out.println("--------------------------------");
        }catch(Exception e){
	        System.out.println(e);
        }
        return monFicHold;
    }
    
    ///////////////////////////////////////////////			
    // Ouverture d'un repertoire inexistant
    ///////////////////////////////////////////////
    public static void ouvrirRepertoire(directory dir, String nomRepertoire){
        directoryHolder monDirHold = new directoryHolder();
	    try{
		    dir.open_directory(monDirHold, nomRepertoire);
	    }catch(Exception e){
		    System.out.println(e);
	    }
    }
    
    ///////////////////////////////////////////////			
    // Suppression d'un fichier
    ///////////////////////////////////////////////
    public static void supprimerFichier(directory dir, String nomFichier){
	    try{
		    dir.delete_file(nomFichier);
	    }catch(Exception e){
		    System.out.println(e);
	    }
    }
}
