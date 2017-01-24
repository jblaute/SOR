package files;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Serveur {
    public static void main(String[] args) throws IOException {
        ////////////////////////////////////////
        // Initialisation de l'ORB et de la POA 
        ////////////////////////////////////////
        try {
            //init ORB
            ORB orb = ORB.init(args, null);

            //init POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            ////////////////////////////////////////////////////////////////
            // Instantiation de l'objet : creation de 
            // l'implementation de l'objet
            ////////////////////////////////////////////////////////////////
	    /* création de la racine*/
            directoryImpl dirImpl = new directoryImpl(new directory_entry("racine",file_type.directory_type));
	    /* fin création de la racine */

            ////////////////////////////////////////////
    	    //  Activation de l'objet d'implementation 
            ////////////////////////////////////////////
            org.omg.CORBA.Object dir = poa.servant_to_reference(dirImpl);


            ////////////////////////////////////////////////////////
	    //  Sauvegarde de la reference d'objet dans un fichier
            ///////////////////////////////////////////////////////
            try {
                String calc_ref = orb.object_to_string(dir);
                String refFile = "calcul.ref";
                PrintWriter out = new PrintWriter(new FileOutputStream(refFile));
                out.println(calc_ref);
                out.close();
            } catch (IOException ex) {
                System.err.println(
                    "Impossible d'ecrire la reference dans calcul.ref");
                System.exit(1);
            }


	    ////////////////////////////////////////////////////////////////
	    // Lancement de la POA  et de l'ORB : a partir de cet instant, le serveur
	    // est capable de traiter les requetes sur les objets deja
	    // actives ainsi que ceux qui le seront par la suite
	    // La methode "orb.run" est bloquante
	    ////////////////////////////////////////////////////////////////
	    
	          
	    
            System.out.println("Le serveur est pret ");
            orb.run();

            System.exit(0);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
