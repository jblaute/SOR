
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.io.*;
import org.omg.PortableServer.*;
import java.util.*;



public class directoryImpl extends directoryPOA
{
	protected POA poa_;
	private String name;
	private String path;
  private int number_of_file;
  private ArrayList<directory> listDir;
	private ArrayList<regular_file> listReg;
	
 
	public directoryImpl(POA poa,String name){
		poa_ = poa;
		this.listDir = new ArrayList<directory>();
		this.listReg = new ArrayList<regular_file>();
		this.name = name;
		number_of_file = 0;
	}

	public String dir_name(){
		return this.name;
	}

	public int number_of_file(){
      return this.number_of_file;
    }

	public String get_path(){
      return this.path;
    }
	
	public void open_regular_file(regular_fileHolder r, String name, mode m)throws invalid_type_file, no_such_file {
	//       raises (invalid_type_file, no_such_file);
	}

	public void open_directory(directoryHolder f, String name)throws invalid_type_file, no_such_file{
	//       raises (invalid_type_file, no_such_file);
			
	}

	public void create_regular_file(regular_fileHolder r, String name)throws already_exist{
			//s'il existe déjà
			//Parcours de la liste des fichiers
			Iterator <regular_file> it = listReg.iterator();
			while (it.hasNext()){
				regular_file reg = it.next();
				if (reg.reg_name().equals(name)) throw new already_exist();
			}
			// création d'un nouveau fichier dans le repertoire du serveur
		
	
			
			try {
			
			// création d'un nouveau fichier et passage de la référence au client
				String pathfile = "./"+this.name+"/"+name;
				
				regular_fileImpl regfImpl = new regular_fileImpl(name);
				org.omg.CORBA.Object objc = poa_.servant_to_reference(regfImpl);
				regular_file regFile = regular_fileHelper.narrow(objc);
			//ajout du fichier sur le systeme de fichier
				File file = new File(pathfile);
				file.createNewFile();	
				r.value = regFile;
				//ajout de ce fichier dans le répertoire courant
				this.listReg.add(regFile);
				this.number_of_file++;
			} catch (Exception e){ System.out.println("POA Exception"+e);
			}		
    }

    public void create_directory(directoryHolder f, String name)throws already_exist{
			//s'il existe déjà
			//Parcours de la liste des fichiers
			Iterator <directory> it = this.listDir.iterator();
			while(it.hasNext()){
				directory dir = it.next();
				if(dir.dir_name().equals(name)) throw new already_exist();
			}
			
			// création d'un nouveau directory et passage en ref pour le client
			try{
				String pathfile = "./"+this.name+"/"+name;
		  	directoryImpl newdir = new directoryImpl(this.poa_, name);
				newdir.path = pathfile;
				org.omg.CORBA.Object objc = poa_.servant_to_reference(newdir);
				directory dir = directoryHelper.narrow(objc);
				f.value = dir;
				//ajout du répertoire sur le systeme de fichier
				File file = new File(pathfile);
				if (!file.exists()) file.mkdir();	
				// ajout dans le parent
				this.listDir.add(dir);
				this.number_of_file++;

			} catch (Exception e){ System.out.println("POA Exception"+e);
			}			    
    }

    public void delete_file(String name) throws no_such_file{
    }

    public int list_files(file_listHolder l){
     try { 
		    // création d'une liste de fichiers et repertoires du rep courant 
				file_listImpl flistImpl = new file_listImpl(this.listDir,this.listReg);
		    org.omg.CORBA.Object objc = poa_.servant_to_reference(flistImpl);
			
				//et envoie de la ref au client
				file_list lf = file_listHelper.narrow(objc);
				l.value = lf;
		    

		  }catch (Exception e){ System.out.println("POA Exception"+e);
			} 
			return this.number_of_file;	
    }
    
}
