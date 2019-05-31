package nl.sw.jose.main;
import java.io.File;

import nl.sw.jose.main.ZipLib;
public class ResetMap {

	public void zipMap(File file,String name,String destino1) throws Exception{
		String origen = file+"/Mapas/"+name+".zip";
		String destino = destino1+"/worlds/"+name+"/region/";
		new ZipLib().unzipFile(origen,destino);
		
		
	}
	public void setReset(File file,String name,String string){
		try {
			this.zipMap(file,name,string);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
}
