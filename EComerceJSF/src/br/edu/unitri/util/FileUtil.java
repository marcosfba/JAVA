/**
 * 
 */
package br.edu.unitri.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;

/**
 * @author Marcos
 *
 */
public class FileUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte[] buffer;  
	  
    public FileUtil(int tamanhoBuffer) {  
        if (tamanhoBuffer < 1) {  
            throw new IllegalArgumentException("O tamanho do buffer precisa ser maior que zero!");  
        }  
        buffer = new byte[tamanhoBuffer];  
    }  
    
	public void copiaArquivo(InputStream inputstream, String fileTbDestino) throws IOException {
    	OutputStream saida = new FileOutputStream(fileTbDestino);  
    	  
        int maximo = buffer.length;  
        int lidos = -1;  
        while ((lidos = inputstream.read(buffer, 0, maximo)) != -1) {  
            saida.write(buffer, 0, lidos);  
        }  
        saida.flush();  
        inputstream.close();  
        saida.close();  
	}
	
	public void copyFile(File source, File destination) throws IOException {
        if (destination.exists())
            destination.delete();

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
       }
   }

	
	

}
