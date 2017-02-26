
package files;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.lang.*;
import java.util.*;



public class file_listImpl extends file_listPOA{

	private ArrayList<directory> listDir;
	private ArrayList<regular_file> listReg;
	private Iterator <directory> itdir;
	private Iterator <regular_file> itreg;  

  public file_listImpl(ArrayList<directory> listDir,ArrayList<regular_file> listReg){
   	this.listDir = listDir;
   	this.listReg = listReg;
		this.itdir = this.listDir.iterator();
		this.itreg = this.listReg.iterator();
		
  }
  
  public boolean next_one(directory_entryHolder e){
		//s'il reste des répertoires	
		if(itdir.hasNext()){	
			directory_entry dentry = new directory_entry(itdir.next().dir_name(),file_type.directory_type);
			e.value = dentry;
			return true;
		}
		//s'il reste des fichiers	
		if(itreg.hasNext()){
			directory_entry dentry = new directory_entry(itreg.next().reg_name(),file_type.regular_file_type);
			e.value = dentry;
			return true;
		}
		return false;
	}

}

	

