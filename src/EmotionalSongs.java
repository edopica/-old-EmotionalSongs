//import java.io.File;
import java.nio.file.FileSystems;
//import java.nio.file.Paths;

class EmotionalSongs{
    
	public static void main(String[] args){
		
		Song s = new Song(3), s1 = new Song(4);
		s.print();
		s.inserisciEmozioniBrano();
        
		s1.print();
		s1.inserisciEmozioniBrano();

        
	}
}
