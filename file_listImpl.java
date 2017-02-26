
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;



public class file_listImpl extends file_listPOA{

	private ArrayList<directory> listDir;
	private ArrayList<regular_file> listReg;
	private Iterator <directory> itdir;
	private Iterator <regular_file> itreg;  

  public file_listImpl(ArrayList<directory> listDir,ArrayList<regular_file> listReg){
   	this.listDir = listDir;
   	this.listReg = listReg;
		this.itdir = listDir.iterator();
		this.itreg = listReg.iterator();
		
  }
  
  public boolean next_one(directory_entryHolder e){
		//s'il reste des répertoires	
		if(itdir.hasNext()){
			itdir.next();
			directory_entry dentry = new directory_entry(itdir.getDirName(),file_type.directory_type);
			e.value = dentry;
			return true;
		}
		//s'il reste des fichiers	
		if(itreg.hasNext()){
			itreg.next();
			directory_entry dentry = new directory_entry(itreg.getRegName(),file_type.regular_file_type);
			e.value = dentry;
			return true;
		}
		return false;
	}

}

	

