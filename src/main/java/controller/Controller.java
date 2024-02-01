package controller;
import implementazionePostgresDao.WikiImplementazionePostgresDao;
import model.Frase;
import model.Utente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    public Utente utente;

    public Controller()
    {

    }

    public Utente addUtente(String nome,String password,String email) throws SQLException {
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        wikiImplementazionePostgresDao.addUtenteDB(nome,password,email);
        Utente u = new Utente(nome,password,email);
        return u;
    }

    public void addFrasi(ArrayList<Frase> frasi, ArrayList<String> frasiCliccabili, HashMap<String,String> hashMap) throws SQLException {
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        for (Frase f: frasi) {
            ArrayList<String> paroleFrasi = f.getParole();
            String risultato = String.join(" ",paroleFrasi);
            String collegamento = restituisciCollegamento(risultato,frasiCliccabili);
            System.out.println(""+collegamento);
            if(!collegamento.isEmpty()) {
                String link=hashMap.get(collegamento);
                wikiImplementazionePostgresDao.addFrasiDB(risultato, link);
            }
            else{
                wikiImplementazionePostgresDao.addFrasiWithoutLink(risultato);
            }
        }
    }

    public void addPagina(String titolo,String nomeAutore) throws SQLException {
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        wikiImplementazionePostgresDao.addPagina(titolo,nomeAutore);
    }

    public static boolean contenimento(String parola, ArrayList<String> frasi) {
        // Pulisci la parola da spazi aggiuntivi e caratteri speciali
        parola = parola.trim().replaceAll("[^a-zA-Z0-9]", "");

        for (String s : frasi) {
            // Pulisci anche la parola presente nell'ArrayList
            String parolaArrayList = s.trim().replaceAll("[^a-zA-Z0-9]", "");

            if (parola.contains(parolaArrayList)) {
                return true;
            }
        }
        for(String s : frasi)
        {
            String parolaArrayList = s.trim().replaceAll("[^a-zA-Z0-9]", "");
            if(parolaArrayList.contains(parola)){
                return true;
            }
        }
        return false;
    }
    public static String restituisciCollegamento(String parola, ArrayList<String> frasi) {
        // Pulisci la parola da spazi aggiuntivi e caratteri speciali
        parola = parola.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        for (String s : frasi) {
            // Pulisci anche la frase presente nell'ArrayList
            String fraseArrayList = s.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

            if (fraseArrayList.contains(parola)) {
                return s;
            }
        }
        return "";
    }

}
