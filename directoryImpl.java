
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;
import org.omg.PortableServer.*;

public class directoryImpl extends directoryPOA
{
	protected POA poa_;
  private int number_of_file;
  private ArrayList<directory_entry> listEntry;
 
	public directoryImpl(POA poa){
		poa_ = poa;
		listEntry = new ArrayList<directory_entry>();
		number_of_file = 0;
	}

	public void open_regular_file(regular_fileHolder r, String name, mode m)throws invalid_type_file, no_such_file {
	//       raises (invalid_type_file, no_such_file);
	}

	public void open_directory(directoryHolder f, String name)throws invalid_type_file, no_such_file{
	//       raises (invalid_type_file, no_such_file);
	}

	public void create_regular_file(regular_fileHolder r, String name)throws already_exist{
	//       raises (already_exist);
	/*
      directory_entry tempDir = new directory_entry(name,file_type.regular_file_type);
      directoryImpl tempDImpl = new directoryImpl(tempDir);
      this.add(tempDImpl);
*/
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
			//
			this.listEntry.add(de);
			this.number_of_file++;
			System.out.println("nbre de file = "+this.number_of_file);
			} catch (Exception e){ System.out.println("POA Exception"+e);
			}			

     
        
    }

    public void delete_file(String name) throws no_such_file{
    }

    public int list_files(file_listHolder l){
      
      int compteur=0;
      file_listImpl iter = new file_listImpl(listEntry);
      l.value = (file_list)iter;
      
     return 0;
    }

    
    public int number_of_file(){
      return number_of_file;
    }
    
}
