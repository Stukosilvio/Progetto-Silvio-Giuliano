package controller;
import implementazionePostgresDao.WikiImplementazionePostgresDao;
import model.Frase;
import model.Pagina;
import model.Testo;
import model.Utente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    public Utente utente;


    public Pagina pagina;


    public Controller()
    {

    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public String getTitolo()
    {
        return pagina.titolo;
    }

    public String getNomeAutore()
    {
        return pagina.autorePagina.username;
    }

    public Utente getUtente() {
        return utente;
    }

    public String getNomeUtente()
    {
        return utente.username;
    }

    public Utente addUtente(String nome, String password, String email) throws SQLException {
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
        System.out.println("mammt");
        System.out.println("mAMMM");
    }


    public void cercaPagina(String titolo,ArrayList<String> parolePaginaCercata) throws SQLException {
        //notare che questo metodo mi riempe l'array list delle parole però ricordiamo che non ci sono ancora i collegamenti associati
        //li cerchermo con la prossima funzione
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        wikiImplementazionePostgresDao.cercaPagina(titolo,parolePaginaCercata);
        Testo testo = new Testo(parolePaginaCercata);
        Pagina pagina = new Pagina(titolo,testo);
        setPagina(pagina);
    }

    public String ottieniCollegamentoParola(String titolo,String parola) throws SQLException {
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        String link = wikiImplementazionePostgresDao.getCollegamento(titolo,parola);
        System.out.println(""+parola);
        if(link != null) {
            pagina.addFrasiLinkate(parola);
        }
        return link;
    }

    public void addModifiche_testo(String contenuto,String nomeAutore,String titoloPagina) throws SQLException {
        WikiImplementazionePostgresDao wikiImplementazionePostgresDao = new WikiImplementazionePostgresDao();
        wikiImplementazionePostgresDao.addModifiche_testo(contenuto,nomeAutore,titoloPagina);
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
