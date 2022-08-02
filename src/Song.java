//Classe che istanzia l'oggetto canzone, permette di selezionare una canzone
//e di valutare le emozioni che trsamette

import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.lang.NullPointerException;

class Song{
	
	private String name, author, album;
	private int year, duration, id;

	//il costruttore prende in input l'inidice della canzone nel datast
    //e da lì ottiene i dati.
	public Song(int id){
		
		this.id = id;
		String line="";
		
		try{
			String path = getPath() + (File.separator + "songs.csv");
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			for(int i=0;i <= id; i++)
                line = br.readLine();
			
			String[] values = line.split(",");
				
			this.name = values[0];
			this.author = values[1];
			this.album = values[2];
				
			try{
				this.duration = (int)Float.parseFloat(values[3]);
				this.year = Integer.parseInt(values[4]);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
				
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void inserisciEmozioniBrano(){
		boolean rated = false;
        Scanner scan = new Scanner(System.in);
		String amazement = scan.nextLine();
        String line, newLine = String.format("%d,%s",this.id,amazement), oldLine="";
		Writer output;
		String path = getPath() + (File.separator + "Emozioni.txt");
        //verifichiamo se questa canzone è già stata valutata o meno.
        try {			
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null){
                if(line.startsWith(Integer.toString(this.id))) {
                    rated = true;
                    oldLine = line;
                    break;
                }
            }
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
        
        //se la canzone è già stata valutata rimpiaziamo la vecchia valutazione[
        if(rated) {
            //Instantiating the Scanner class to read the file
            try {
                Scanner sc = new Scanner(new File(path));
                //instantiating the StringBuffer class
                StringBuffer buffer = new StringBuffer();
                //Reading lines of the file and appending them to StringBuffer
                while (sc.hasNextLine()) {
                   buffer.append(sc.nextLine()+System.lineSeparator());
                }
                String fileContents = buffer.toString();
                //closing the Scanner object
                sc.close();

                //Replacing the old line with new line
                fileContents = fileContents.replaceAll(oldLine, newLine);
                //instantiating the FileWriter class
                FileWriter writer = new FileWriter(path);
                writer.append(fileContents);
                writer.flush();
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }


        }else {
            try{
                //apro il file in modalità append per aggiungere i dati di una canzone
                //per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
                output = new BufferedWriter(new FileWriter(path, true));
                output.append(newLine + System.lineSeparator());
                output.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
	}
	
	//per ottenere il filePath alla cartella /data (ogni OS)
	private String getPath(){
		//ottengo la directory del progetto
		String userDirectory = System.getProperty("user.dir");
		String[] directories = userDirectory.split(File.separator);
		//cambio cartella per aprire i file in \data
		directories[directories.length -1] = "data";
		
		return String.join(File.separator, directories);
		
		
	}	
	public void print(){
		String line = String.format("Nome: %s \nArtista: %s \n",this.name,this.author);
		System.out.print(line);
	}
	
}
