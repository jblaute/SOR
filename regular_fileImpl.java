
package files;

import org.omg.CORBA.*;
import java.lang.*;
import java.io.*;


public class regular_fileImpl extends regular_filePOA
{
	private String name;
	private File file;
	private int offset;
  
  public regular_fileImpl(String name){
		this.name = name;
		this.file = null;
		this.offset = 0;
  }
		
	public String getRegName(){
		return this.name;
	}
	public File getFile(){
		return this.file;
	}
	public int getOffset(){
		return this.offset;
	}




   public int read(int size, StringHolder data) throws end_of_file,invalid_operation {
/*

  raises (end_of_file,invalid_operation);




      try{
      }catch(end_of_file e1, invalid_operation e2){   
      }
*/
      return 0;
   }

    public int write(int size, String data) throws invalid_operation{
     //   raises (invalid_operation);
      return 0;
    }

    public void seek(int new_offset) throws invalid_offset,invalid_operation {
       // raises (invalid_offset,invalid_operation);
    }

    public void close(){
    }
}
