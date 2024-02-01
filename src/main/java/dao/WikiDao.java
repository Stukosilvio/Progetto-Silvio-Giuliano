package dao;

public interface WikiDao {
    public void addUtenteDB(String nome,String cognome,String email);

    public void addFrasiDB(String contenuto,String collegamento);

    public void addFrasiWithoutLink(String contenuto);

    public void addPagina(String titolo,String nomeAutore);
}
