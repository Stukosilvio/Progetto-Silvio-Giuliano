package dao;

import java.util.ArrayList;

public interface WikiDao {
    public void addUtenteDB(String nome,String cognome,String email);

    public void addFrasiDB(String contenuto,String collegamento);

    public void addFrasiWithoutLink(String contenuto);

    public void addPagina(String titolo,String nomeAutore);

    public void cercaPagina(String titolo, ArrayList<String> parolePaginaCercata);

    public String getCollegamento(String pagina,String parola);

    public void addModifiche_testo(String contenuto,String nomeAutore,String titoloPagina);



}
