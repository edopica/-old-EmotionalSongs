//Classe che istanzia l'oggetto canzone, permette di selezionare una canzone
//e di valutare le emozioni che trsamette

import java.util.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.lang.NullPointerException;

class Song {

    private String name, author, album;
    private int year, duration, id;

    public Song() throws IOException, NumberFormatException, NumberFormatException{
        Scanner scan = new Scanner(System.in);
        //Chiedo di inserire un dato qualsiasi della canzone
        System.out.println("\nDigitare per la ricerca (nome, artista, album ...):");
        String name = scan.nextLine(), line, searchLine, id;
        String[] data = new String[6];
        List<String> list = new ArrayList<String>();
        int i=0;

        String path = getPath() + (File.separator + "Songs.csv");
        BufferedReader br = new BufferedReader(new FileReader(path));
        //Raggruppo tutte le canzoni in cui appare il dato cercato
        while((line = br.readLine()) != null) {
            data = line.split(",,");
            searchLine = String.join(",,", data[1],data[2],data[3]).toLowerCase();
            if(searchLine.contains(name.toLowerCase()))
                list.add(line);
        }
        if(list.size() > 0) {
            //Stampo la lista numerata delle canzoni trovate:
            //1) canzone1
            //2) Canzone2
            //e così via...
            for(String s : list){
                System.out.println(String.format("%d) %s",++i,list.get(i-1)));
            }
            //chiedo quale dei brani stampati è quello cercato e ne ricavi l'id
            //in modo da poterlo trovare nel dataset canzoni.csv
            System.out.println("qual è il brano cercato?\n");
            i = scan.nextInt() - 1;
            data = list.get(i).split(",,");
            
            this.id = Integer.parseInt(data[0]);
            this.name = data[1];
            this.author = data[2];
            this.album = data[3];
            this.duration = (int)Float.parseFloat(data[4]);
            this.year = Integer.parseInt(data[5]);
        }
        else {
            System.out.println("Nessun brano corrisponde alla ricerca!");
        }
    }

    //il costruttore prende in input l'inidice della canzone nel dataset
    //e da lì ottiene i dati.
    public Song(int id) throws IOException, NumberFormatException {

        this.id = id;
        String line="";
        String[] data  = getSongData(id);
        
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.author = data[2];
        this.album = data[3];
        this.duration = (int)Float.parseFloat(data[4]);
        this.year = Integer.parseInt(data[5]);
    }

    public void inserisciEmozioniBrano(String userId) throws IOException {
        boolean rated = false;
        Scanner scan = new Scanner(System.in);
        String amazement = scan.nextLine();
        String line, oldLine="", newLine = String.format("%d,,%s,,%s",this.id,userId,amazement); 
        String path = getPath() + (File.separator + "Emozioni.csv");
        //String[] data = new String[6];
        Writer output;
        //verifichiamo se questa canzone è già stata valutata o meno.
        BufferedReader br = new BufferedReader(new FileReader(path));
        while((line = br.readLine()) != null) {
            String[] data = line.split(",,");
            
            if(data[0].equals(Integer.toString(this.id)) & data[1].equals(userId)) {
                rated = true;
                oldLine = line;
                break;
            }
        }


        //se la canzone è già stata valutata rimpiaziamo la vecchia valutazione[
        if(rated) {
            //Instantiating the Scanner class to read the file
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

        }else {
            //apro il file in modalità append per aggiungere i dati di una canzone
            //per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
            output = new BufferedWriter(new FileWriter(path, true));
            output.append(newLine + System.lineSeparator());
            output.close();
        }
    }

    //per ottenere il filePath alla cartella /data (ogni OS)
    private String getPath() {
        //ottengo la directory del progetto
        String userDirectory = System.getProperty("user.dir");
        //File.separator permette di creare percorsi su ogni OS
        String[] directories = userDirectory.split(File.separator);
        //cambio cartella per aprire i file in \data
        directories[directories.length -1] = "data";

        return String.join(File.separator, directories);
    }	

    //funzione che preleva i dati della IDesima canzone nel dataset
    private String[] getSongData(int id) throws IOException, FileNotFoundException {
        
        String path = getPath() + (File.separator + "Songs.csv"), line="";
        BufferedReader br = new BufferedReader(new FileReader(path));

        for(int i=0;i<=id; i++)
            line = br.readLine();

        String[] values = line.split(",,");

        return values;
    }

    //funzione per ottenere le emozioni di una canzone dato l'id;
    private List<String[]> getEmotionsData(int id) throws IOException, FileNotFoundException {
        boolean check = false;
        String path = getPath() + (File.separator + "Emozioni.csv"), line;
        String[] rating = new String[3];
        List<String[]> ratings = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null) {
            rating = line.split(",,");

            if(rating[0].equals( Integer.toString(this.id))) {
                ratings.add(rating);
            }
        }

        return ratings;
    }

    public void printSongData() {
        String line = String.format("Nome: %s \nArtista: %s \n",this.name,this.author);
        System.out.print(line);
    }

    public void printEmotionsData() throws IOException {
        int i;
        List<String[]> ratings = getEmotionsData(this.id);
        String[] rating = new String[3];
        String line;

        for(i=0;i<ratings.size();i++) {
            rating  = ratings.get(i);
            line = String.format("User:%s\nAmazement: %s\n",rating[1], rating[2]);
            System.out.println(line);
        }
    }

}
