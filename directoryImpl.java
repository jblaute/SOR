
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

    public void open_regular_file(regular_fileHolder r, String name, mode m) {
 //       raises (invalid_type_file, no_such_file);
    }

    public void open_directory(directoryHolder f, String name){
 //       raises (invalid_type_file, no_such_file);
    }

    public void create_regular_file(regular_fileHolder r, String name){
 //       raises (already_exist);
 /*
      directory_entry tempDir = new directory_entry(name,file_type.regular_file_type);
      directoryImpl tempDImpl = new directoryImpl(tempDir);
      this.add(tempDImpl);
*/
    }

    public void create_directory(directoryHolder f, String name){
    
      directory_entry tempDir = new directory_entry(name,file_type.regular_file_type);
      listEntry.add(tempDir);
      //on renvoie la référence du répertoire courant modifié
      f.value = (directory) this;
 //       raises (already_exist);
    }

    public void delete_file(String name){
 //       raises (no_such_file);
    }

    public int list_files(file_listHolder l){
      
      file_listImpl iter = new file_listImpl(this);
      directoryImpl temp;
      int compteur=0;
      while(l.nextOne(temp)){
        compteur++;
      }
      
     return compteur;
    }

    public ArrayList getListEntry(){
      return listEntry;
    }
    
    public int number_of_file(){
      return number_of_file;
    }
    
}
