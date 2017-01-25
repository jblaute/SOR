
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;


public class directoryImpl extends directoryPOA
{

    private int number_of_file;
    private ArrayList<directory_entry> listEntry;
   
    public directoryImpl(directory_entry parent){
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
    
      directory_entry tempDir = new directory_entry(name,file_type.regular_file_type);
      listEntry.add(tempDir);
      //on renvoie la référence du répertoire courant modifié
      f.value = (directory) this;
 //       raises (already_exist);
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
