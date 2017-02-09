
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;
import org.omg.PortableServer.*;
import java.util.*;
//import java.util.Map;

public class directoryImpl extends directoryPOA
{
	protected POA poa_;
  private int number_of_file;
  private HashMap<String, directory_entry> listDir;
	private HashMap<String, directory_entry> listReg;
 
	public directoryImpl(POA poa){
		poa_ = poa;
		listDir = new HashMap<String, directory_entry>();
		listReg = new HashMap<String, directory_entry>();

		number_of_file = 0;
	}

	public void open_regular_file(regular_fileHolder r, String name, mode m)throws invalid_type_file, no_such_file {
	//       raises (invalid_type_file, no_such_file);
	}

	public void open_directory(directoryHolder f, String name)throws invalid_type_file, no_such_file{
	//       raises (invalid_type_file, no_such_file);
			
	}

	public void create_regular_file(regular_fileHolder r, String name)throws already_exist{
			//si il existe déjà avec list_files
			if (false) throw new already_exist();
			// création d'un nouveau fichier
			try {
			// création d'un nouveau fichier et passage de la référence au client
				regular_fileImpl regfImpl = new regular_fileImpl();
				org.omg.CORBA.Object objc = poa_.servant_to_reference(regfImpl);
				r.value = regular_fileHelper.narrow(objc);
				//ajout de ce fichier dans le répertoir courant
				directory_entry regulFile = new directory_entry(name,file_type.regular_file_type);
				this.listReg.put(name, regulFile);
				this.number_of_file++;
			} catch (Exception e){ System.out.println("POA Exception"+e);
			}		
    }

    public void create_directory(directoryHolder f, String name)throws already_exist{
			//si il existe déjà avec list_files

			if (false) throw new already_exist();
			// création d'un nouveau directory et passage en ref pour le client
			try{
    	directoryImpl newdir = new directoryImpl(this.poa_);
			org.omg.CORBA.Object objc = poa_.servant_to_reference(newdir);
			f.value = directoryHelper.narrow(objc);
			// ajout dans le parent de directory_entry(java.lang.String name, files.file_type type)			
			directory_entry de = new directory_entry(name,file_type.directory_type);
System.out.println(de.name);
			this.listDir.put(name, de);
			this.number_of_file++;
			//System.out.println("nbre de file = "+this.number_of_file);
			} catch (Exception e){ System.out.println("POA Exception"+e);
			}			

     
        
    }

    public void delete_file(String name) throws no_such_file{
    }

    public int list_files(file_listHolder l){
      
     try { 
		    // création d'une liste de fichiers et repertoires du rep courant 
				file_listImpl flistImpl = new file_listImpl();
		    org.omg.CORBA.Object objc = poa_.servant_to_reference(flistImpl);
			
				// on remplie la liste avec les directory_entry du rep courant
				for (Map.Entry<String,directory_entry> dirEntry : this.listDir.entrySet())
					flistImpl.add(dirEntry.getValue());
				for (Map.Entry<String,directory_entry> dirEntry : this.listReg.entrySet())
					flistImpl.add(dirEntry.getValue());
				//et envoie de la ref au client
				l.value = file_listHelper.narrow(objc);	
		   return this.number_of_file;
		  }catch (Exception e){ System.out.println("POA Exception"+e);
				return -1;
			} 
    }
    public int number_of_file(){
      return this.number_of_file;
    }
    
}
