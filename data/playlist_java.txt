import java.util.Scanner ;
import java.io.*;

public class Playlist {

    public static void main(String[] args ){

        String IdUtente = "ddddd" ;
        RegistraPlaylist(IdUtente) ;
    }

    public static void  RegistraPlaylist(String IdUtente ){

        final String FilePath = "C:\\Users\\Federico\\Desktop\\laba\\Playlist.dati.csv" ;

        Scanner sc= new Scanner(System.in);// aprire lo stream
        System.out.print("Inserire il nome della playlist :");
        String nomePlaylist = sc.nextLine() ; // prende nome playlist in input

        try{

            FileWriter fw = new FileWriter(FilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(IdUtente +";"+ nomePlaylist);

            // oppure  gestire attraverso indice Canzoni.dati ?
            /*

            while (cond ) ;
              ricerca in repository --> cercaBranoMusicale() ;
              pw.print(cercaBranoMusicale() ) ;

            */
            pw.flush();
            pw.close();


        }catch (Exception e){

            e.printStackTrace();

        }

    }

}