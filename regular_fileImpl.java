
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.io.*;
 
public class regular_fileImpl extends regular_filePOA
{
	private String name;
	private File file;
	private int offset;
	private mode mode_ouv;
	private FileReader fr;
	private BufferedReader br;
	private BufferedWriter bufWriter = null;
        private FileWriter fileWriter = null;
  
	public regular_fileImpl(String name){
		this.name = name;
		this.file = null;
		this.offset = 0;
	}
		
	public String reg_name(){
		return this.name;
	}
	public File get_file(){
		return this.file;
	}
	public int get_offset(){
		return this.offset;
	}

	public void mode_ouverture(mode m){
		this.mode_ouv = m;
	}
	public mode mode_ouverture(){
		return this.mode_ouv;
	}

   public int read(int size, StringHolder data) throws end_of_file,invalid_operation {
     if(this.mode_ouv.equals(mode.read_only)||this.mode_ouv.equals(mode.read_write)){
	String texte="";
	try{
		for(int compteur=offset;compteur<size+offset;compteur++){
			texte=texte+(char)br.read();
		}
		data.value=texte;
	}catch(Exception e){
		throw new end_of_file();
	}
     }else{
     	throw new invalid_operation();
     }
      return 0;
   }


//     read_only, write_append, write_trunc, read_write


    public void ouvrir(mode m){
	     if(this.mode_ouv.equals(mode.write_append)||this.mode_ouv.equals(mode.write_trunc)||this.mode_ouv.equals(mode.read_write)){
	     boolean si_append=(this.mode_ouv.equals(mode.write_append) || this.mode_ouv.equals(mode.read_write));
	     	try{
		    fileWriter = new FileWriter(this.name, si_append);
		    bufWriter = new BufferedWriter(fileWriter);
		}catch(Exception e){
	            System.err.println(e);
		}
	     }else{
	     	try{
		    fr = new FileReader(this.name);
		    br = new BufferedReader(fr);
		}catch(Exception e){
	            System.err.println(e);
		}
	     }
    }
    
    public int write(int size, String data) throws invalid_operation{
    	
     if(this.mode_ouv.equals(mode.write_append)||this.mode_ouv.equals(mode.write_trunc)||this.mode_ouv.equals(mode.read_write)){
        try {
            bufWriter.write(data);
        } catch (IOException ex) {
            System.err.println(ex);
        }
     }else{
     	throw new invalid_operation();
     }
      return 0;
    }

    public void seek(int new_offset) throws invalid_offset,invalid_operation {
     if(this.mode_ouv.equals(mode.read_only)||this.mode_ouv.equals(mode.read_write)){
	String s;
	try{
		for(int compteur=0;compteur<new_offset;compteur++){
			br.read();
		}
		this.offset=new_offset;
	}catch(Exception e){
		throw new invalid_offset();
	}
     }else{
     	throw new invalid_operation();
     }
    }

    public void fermer(){
	     if(this.mode_ouv.equals(mode.write_append)||this.mode_ouv.equals(mode.write_trunc)||this.mode_ouv.equals(mode.read_write)){
	     	try{
	                bufWriter.close();
        	        fileWriter.close();
        	}catch(Exception e){
        		System.err.println(e);
        	}
	     }else{
	     	try{
		    fr.close();
        	}catch(Exception e){
        		System.err.println(e);
        	}
	     }
    }
}
