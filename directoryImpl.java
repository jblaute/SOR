
package files;

import org.omg.CORBA.*;
import java.lang.*;


public class directoryImpl extends directoryPOA
{

    private int number_of_file;
    private arrayList<directory_entry> liste = new arrayList()<directory_entry>;
    
    public void directoryImpl(){
    }

    public void open_regular_file(regular_fileHolder r, String name, mode m) {
 //       raises (invalid_type_file, no_such_file);
    }

    public void open_directory(directoryHolder f, String name){
 //       raises (invalid_type_file, no_such_file);
    }

    public void create_regular_file(regular_fileHolder r, String name){
 //       raises (already_exist);
    }

    public void create_directory(directoryHolder f, String name){
 //       raises (already_exist);
    }

    public void delete_file(String name){
 //       raises (no_such_file);
    }

    public int list_files(file_listHolder l){
     return 0;
    }

    public int number_of_file(){
      return number_of_file;
    }
}
