//Classe che istanzia l'oggetto canzone, permette di selezionare una canzone
//e di valutare le emozioni che trsamette

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;


class Song{
	
	private String name, author, album;
	private int year, duration, id;
	
	public Song(int id){
		
		this.id = id;
		String line="";
		
		try{
			String path = getPath() + "\\songs.csv";
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
		Scanner scan = new Scanner(System.in);
		String amazement = scan.nextLine();
		
		String path = getPath() + "\\Emozioni.txt";
		
		Writer output;
		try{
			//apro il file in modalità append per aggiungere i dati di una canzone
			//per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
			output = new BufferedWriter(new FileWriter(path, true));
			output.append(String.format("\n%d,%s",this.id,amazement));
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//per ottenere il filePath su ogni dispositivo (solo windows)
	private String getPath(){
		//ottengo la directory del progetto
		String userDirectory = System.getProperty("user.dir");
		String[] directories = userDirectory.split("\\\\");
		//cambio cartella per aprire i file in \data
		directories[directories.length -1] = "data";
		
		return String.join("\\", directories);
		
		
	}
	
	public void print(){
		String line = String.format("Nome: %s \nArtista: %s \n",this.name,this.author);
		System.out.print(line);
	}
	
}