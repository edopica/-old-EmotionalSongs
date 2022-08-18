//import java.io.File;
import java.nio.file.FileSystems;
import java.io.*;
//import java.nio.file.Paths;

class EmotionalSongs{
    
	public static void main(String[] args) throws IOException {
		
        Song s = new Song();
        s.inserisciEmozioniBrano();
        s.printEmotionsData();
	}
}
