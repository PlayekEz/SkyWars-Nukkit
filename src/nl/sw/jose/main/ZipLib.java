package nl.sw.jose.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipLib {
	
	
	public void unzipFile(String filePath,String destino) throws Exception{
		 String zipFile = filePath;
	        String outputFolder = destino;

	        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
	        ZipEntry ze = zis.getNextEntry();
	        while(ze!=null){
	            String entryName = ze.getName();

	            File f = new File(outputFolder + File.separator +  entryName);
	           
	            f.getParentFile().mkdirs();
	            FileOutputStream fos = new FileOutputStream(f);
	            int len;
	            byte buffer[] = new byte[1024];
	            while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	            fos.close();   
	           
	            ze = zis.getNextEntry();
	        }
	        zis.closeEntry();
	        zis.close();
    }
	public void sendZip(String folder,String destino) throws Exception{
		   String sourceFolderName =  folder;
	        String outputFileName = destino;
	 
	        FileOutputStream fos = new FileOutputStream(outputFileName);
	        ZipOutputStream zos = new ZipOutputStream(fos);
	      
	        zos.setLevel(9);
	        addFolder(zos, sourceFolderName, sourceFolderName);
	 
	        zos.close();
	}
	private static void addFolder(ZipOutputStream zos,String folderName,String baseFolderName)throws Exception{
	        File f = new File(folderName);
	        if(f.exists()){
	 
	            if(f.isDirectory()){
	               
	                if(!folderName.equalsIgnoreCase(baseFolderName)){
	                    String entryName = folderName.substring(baseFolderName.length()+1,folderName.length()) + File.separatorChar;
	                    
	                    ZipEntry ze= new ZipEntry(entryName);
	                    zos.putNextEntry(ze);    
	                }
	                File f2[] = f.listFiles();
	                for(int i=0;i<f2.length;i++){
	                    addFolder(zos,f2[i].getAbsolutePath(),baseFolderName);    
	                }
	            }else{
	               
	                String entryName = folderName.substring(baseFolderName.length()+1,folderName.length());
	              
	                ZipEntry ze= new ZipEntry(entryName);
	                zos.putNextEntry(ze);
	                FileInputStream in = new FileInputStream(folderName);
	                int len;
	                byte buffer[] = new byte[1024];
	                while ((len = in.read(buffer)) > 0) {
	                    zos.write(buffer, 0, len);
	                }
	                in.close();
	                zos.closeEntry();
	               
	 
	            }
	        }else{
	            
	        }
	 
	    }
}
