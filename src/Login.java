import java.util.*;
import java.io.*;
import java.nio.file.FileSystems;

public class Login{
    
    private String userName, password;
    private boolean logged;

    //Quando il costruttore viene chiamato senza argomenti viene effettuata la registrazione.
    public Login() throws IOException {    
        
        boolean check = false;
        Scanner scan = new Scanner(System.in);
        List<String[]> users = getUsers();

        do{
            check = false;

            System.out.println("inserire il nome utente: ");
            this.userName = scan.nextLine();
            System.out.println("inserire la password: ");
            this.password = scan.nextLine();

            for(String[] user : users){
                if(this.userName.equals(user[0])){
                    check = true;
                    System.out.println("\nERRORE: NOME GIA' IN USO\n");
                    break;
                }
            }

        }while(check);

        String newLine = String.format("%s,%s",this.userName,this.password);
        String path = getPath() + (File.separator + "UtentiRegistrati.csv");
        BufferedWriter output = new BufferedWriter(new FileWriter(path, true));
        output.append(newLine + System.lineSeparator());
        output.close();

        this.logged = true;
    }

    //se si passano nome utente e password invece si effettua il login.
    public Login(String userName, String password) throws IOException {
        //cerco fra gli utenti una coppia corrispondente di userName e password.
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(userName.equals(user[0]) && password.equals(user[1])) {
                this.logged = true;
                this.userName = userName;
                this.password = password;
                break;
            }
        }

        if(!this.logged)
            System.out.println("\nAccesso fallito\n");

    }

    public boolean isLogged(){
        return logged;
    }

    private List<String[]> getUsers() throws IOException {
        //ottengo i dati di tutti gli utenti e li divido in un array per potervi accedere 
        //singolarmente.
        List<String[]> list = new ArrayList<String[]>();
        String line;
        String path = getPath() + (File.separator + "UtentiRegistrati.csv");
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null)
            list.add(line.split(","));

        list.remove(0);

        return list;
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
}
