
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;


public class file_listImpl extends file_listPOA
{
  private ArrayList listEntry;
  private Iterator it;
  
  public file_listImpl(ArrayList listEntry_p){
    listEntry=listEntry_p;
    it = listEntry.iterator();
  }
  
  public boolean next_one(directory_entryHolder e){
    e.value=(directory_entry)it.next();
    return it.hasNext();
  }
}
