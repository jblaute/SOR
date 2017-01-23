
package files;

import org.omg.CORBA.*;
import java.lang.*;


public class file_listImpl extends file_listPOA
{
  public void file_listImpl(){
  }
  
  public boolean next_one(directory_entryHolder e){
    return true;
  }
}
