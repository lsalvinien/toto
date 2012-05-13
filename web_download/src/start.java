import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;




public class start 
{
	
	
	static int importFile( InputStream aReader, FileOutputStream aWriter, byte buffer[]) throws IOException 
	{
		int total=0, l=0;
		boolean encore=true;
		
		while( encore)
		{
			try { l = aReader.read(buffer);} 
			catch (IOException e) 
			{//2eme essais
				System.out.println("2eme Essais");
				try { l = aReader.read(buffer);} 
				catch (IOException f) 
				{//3eme essais => comme c'est le dernier essais on laisse la fonction toper l'exception
					System.out.println("3eme Essais");
					l = aReader.read(buffer);
				}

			}
			
			if (  l>0)
			{
				aWriter.write( buffer,0,l);
				total+=l;
			}
			else
			{
				encore = false;
			}
		}

		return total;
	}

	
	static int importFile(String remoteFileName, String localFileName) throws IOException 
	{
		// TODO Auto-generated method stub

		URL urlFile = null;
		InputStream reader=null;  
		
		FileOutputStream writer=null;
		
		int maxlen=1024*1024, total=0;
		int sizeRemoteFile=0;
		byte buffer[] = new byte[maxlen];

	
		System.out.print( localFileName);
			
		//Open the Source
		urlFile = new URL(remoteFileName); 
		sizeRemoteFile = urlFile.openConnection().getContentLength();
		try { reader = urlFile.openStream();} 
		catch (IOException e) 
		{ 
			System.out.println(" => file doesn't exist");
			return -1;
		}
		
				
		//Open the target (if it doesn't exist, then create it, if it exists then it is reseted
		writer = new FileOutputStream(localFileName);
		
	
		//
		try { total = start.importFile( reader, writer, buffer);} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.print( "....Pb de connection (");
			System.out.print( sizeRemoteFile);
			System.out.println( ")");
			//close stream
			writer.close();
			reader.close();	
			
			File f= new File(localFileName);
			f.delete();
			
			return -2;
		}
		
		//
		if( sizeRemoteFile!= total)
		{
			System.out.print( "....Erreur => ");
			System.out.print( sizeRemoteFile);
			System.out.print( " / ");
			System.out.print( total);
			System.out.println(" !!!!");
			
			//close stream
			writer.close();
			reader.close();	
			
			File f= new File(localFileName);
			f.delete();
			
			return -2;		
		}
		System.out.print( "....Done => ");
		System.out.println(total);
		
		//close stream
		writer.close();
		reader.close();	
		
		return 0;
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	static void importFile( int i) throws IOException, URISyntaxException 
	{
		
		int ret=0;
		
		String urlString = "http://media.kelbymediagroup.com/photoshopkillertips/video/podcasts/day"+i+".mp4";
		String fileName = "day"+i+".mp4";
			
		ret = start.importFile( urlString, fileName);
			
		switch (ret)
		{
			case -2:
					//pb de connection...on ressaye 1 fois puis on passe au suivamt
					ret = start.importFile( urlString, fileName);
					break;
					
			case  0: //pas de soucis, on passe au suivant
			case -1: //pas de source, on passe au suivant	
			default:  //on passe au suivant
		}
	}

	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException 
	{
		// TODO Auto-generated method stub

		
		importFile(104);

	
				
	}
}
