package model;

import java.util.ArrayList;

public class Pagina {
    public String titolo;

    public Autore autorePagina;

    public Testo testo;

    ArrayList<String> frasiLinkate = new ArrayList<>();

    public Pagina(String titolo,Testo testo,String nomeAutore)
    {
        this.titolo=titolo;
        this.testo=testo;
        Autore autore = new Autore(nomeAutore);
        this.autorePagina=autore;
    }

    public Pagina(String titolo,Testo testo)
    {
        this.titolo=titolo;
        this.testo=testo;
    }

    public void addFrasiLinkate(String parola)
    {
        frasiLinkate.add(parola);
    }

    public Testo getTesto() {
        return testo;
    }
}
