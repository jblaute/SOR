
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;



public class file_listImpl extends file_listPOA
{
  private ArrayList <directory_entry> list;
  private Iterator <directory_entry>it;

  public file_listImpl(){
   	list = new ArrayList<directory_entry>();
   	it = list.iterator();
		System.out.println("objet cree");
		
  }
  
  public boolean next_one(directory_entryHolder e){
   	e.value = (directory_entry) it.next();
    return it.hasNext();
  }
	public void add(directory_entry de){
		this.list.add(de);
	}
	public void test(){
		System.out.println("test");
	}
}
