import java.io.*; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Playlist {

    private final String nomePlaylist ;
    private ArrayList<Integer> indiciPlaylist ;

    /* costruisce un oggetto di tipo Playlist da una stringa contenente le informazioni di una Playlist
       scelta dall' utente , usando come campi il nome della playlist(Stringa)
       e gli indici  (del file di memoria dedicato ) delle canzoni ( memoriArrayList di interi ) */
    public Playlist( String infoPlaylistScelta , String nomePlaylist ){

        this.nomePlaylist = nomePlaylist;
        indiciPlaylist = new ArrayList<>() ;

        /* toglie il nome della playlist dalla stringa e estrae i singoli indici delle canzoni
            es. nomePlaylist;1;34;66;  -->  [0]= 1 , [1]= 3, [2]= 66 */
        String[] indiciCanzoni = infoPlaylistScelta.substring(nomePlaylist.length()).split(";") ;

        // passa da vettore di stringhe a ArrayList di interi
        for (String i : indiciCanzoni ){
            indiciPlaylist.add(Integer.valueOf(i)) ;
        }
    }

    /*  il metodo ritorna una lista composta da stringhe alfanumeriche
        che rappresentano le playlist di un singolo utente passato come argomento .
    *   Ogni Stringa contiene il nome della Playlist come prima informazione
    *   e successivamente gli indici delle canzoni che la compongono .
        Le informazioni sono separate  dal carattere  ";"  es. nomePlaylist;1;34;66;   */
    public static ArrayList<String> ListaPlaylistUtente(String IdUtente)throws IOException{

        ArrayList<String> listaPlaylist = new ArrayList<>();
        String playlistCorrente;
        String path = "C:\\Users\\Federico\\Desktop\\laba\\data\\Playlist.dati.csv";
        int indiceSeparatore ;

        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine() ;  // salta la prima linea del file (vuota)

        // il ciclo si ferma quando il file è vuoto
        while ( ((playlistCorrente = br.readLine()) != null) && (!playlistCorrente.isEmpty()) ) {
            /* nel file una playlist è rappresentata così : IdUtente;nomePlaylist;indice1;indice2ecc.
               quindi dobbiamo verificare se IdUtente coincide con quello in input
               e in caso eliminarlo dalla informazioni prima di aggiungerle alla lista */
            indiceSeparatore = playlistCorrente.indexOf(';');

            if (IdUtente.equals(playlistCorrente.substring(0, indiceSeparatore)))
                listaPlaylist.add(playlistCorrente.substring(indiceSeparatore+1));

        }

        br.close();

        return listaPlaylist ;    // nota : sarà vuota se utente non possiede ancora playlist

    }

    /* registra la playlist dell'utente aggiungendola alla lista delle sue playlist e al file che memorizza tutte le playlist  */
    public static ArrayList<String>  RegistraPlaylist(String IdUtente, ArrayList<String> listaPlaylistUtente )throws IOException {

        String path = getPath() + (File.separator + "Playlist.dati.csv");
        Scanner sc= new Scanner(System.in);
        String nomePlaylist ;
        String messaggio ;

        do {
            System.out.print("\nInserire il nome della playlist (max 30 caratteri) :");
            nomePlaylist = sc.nextLine();

            System.out.print(messaggio = Controlli(nomePlaylist, listaPlaylistUtente));

        }while(!(messaggio.equals("la playlist "+ nomePlaylist + " è stata creata ")));
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(path,true));
        PrintWriter pw = new PrintWriter(bw);

        String stringaIndici = "";
        boolean aggiungiBrano = true ;   // playlist deve avere almeno un brano

        while (aggiungiBrano) {
            stringaIndici = stringaIndici.concat("1"+ ";") ; // "1" --> String.valueOf(cercabranomusicale()) ;
            System.out.print(" Il brano è stato aggiunto | Per Aggiungere un altro brano :  premere 1 ");
            aggiungiBrano = sc.nextInt()==1 ;
        }
        // aggiunge la playlist registrata al file Playlist.dati.csv
        pw.println(IdUtente +";"+ nomePlaylist+";"+ stringaIndici);
        // aggiunge la playlist registrata alla lista Playlist dell'utente
        listaPlaylistUtente.add(nomePlaylist+";"+stringaIndici);

        sc.close();
        pw.close();

        return listaPlaylistUtente;

    }

    /* il metodo verifica se il nome della playlist è accettabile attraverso una serie di controlli
     e ritorna un messaggio che afferma la correttezza o eventualmente descrive il tipo di inconvenienza */
    private static String  Controlli( String nomePlaylist , ArrayList<String> listaPlaylistUtente ) {

        // inizializziamo il messaggio al caso in cui l'operazione è stata completata correttamente
        String messaggio = "la playlist  "+ nomePlaylist + " è stata creata ";
        final int maxLength = 30 ;
        boolean accettabile = true ;  // il flag verrà assegnato a false quando il nome non passa il controllo

        // nel caso in cui il nome non passi il controllo cambiamo il messaggio in conformità con l'errore
        if(nomePlaylist.length() > maxLength){
            messaggio = "reinserire : Il nome eccede i caratteri massimi(30)";
            accettabile = false ;
        }
        else if((nomePlaylist.equals("")) ) {
            messaggio = "reinserire : Il nome non può essere una stringa vuota ";
            accettabile = false ;
        }
        if(accettabile) {

            int i = 0;
            // controlliamo che il nome non contenga il separatore ";" usato nei file di memoria ( .csv )
            while((i < nomePlaylist.length() && accettabile )){
                if (nomePlaylist.charAt(i) == ';'){
                    accettabile = false;
                    messaggio = " reinserire : il carattere ; non è accettabile " ;
                }
                i++;
            }

            if (accettabile) {
                int j  = 0 ;
                /*  ogni stringa in ListaPlaylistUtente rappresenta le informazioni
                    di una Playlist, e il nome si trova dall'indice 0  all' indice del separatore ";" */
                int indiceSeparatore = listaPlaylistUtente.get(j).indexOf(';');
                Iterator<String> iter = listaPlaylistUtente.iterator();

                // controlliamo che non esista già una playlist con lo stesso nome
                while (iter.hasNext() && accettabile ) {
                    if (nomePlaylist.equals(iter.next().substring(0,indiceSeparatore))) {
                        accettabile = false;
                        messaggio= "reinserire : esiste una Playlist con lo stesso nome nel catalogo utente " ;
                    }
                    if(iter.hasNext())
                        indiceSeparatore = listaPlaylistUtente.get(j++).indexOf(";");
                }
            }

        }

        return messaggio;
    }

    //per ottenere il filePath alla cartella /data (ogni OS)
    private static String getPath() {
        //ottengo la directory del progetto
        String userDirectory = System.getProperty("user.dir");
        //File.separator permette di creare percorsi su ogni OS
        String[] directories = userDirectory.split(File.separator);
        //cambio cartella per aprire i file in \data
        directories[directories.length -1] = "data";

        return String.join(File.separator, directories);

    }


}
