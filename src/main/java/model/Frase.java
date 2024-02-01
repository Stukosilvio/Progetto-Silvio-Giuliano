package model;

import java.util.ArrayList;

public class Frase {
    public ArrayList<String> parole = new ArrayList<>();

    public Frase(String Parole)
    {
        parole.add(Parole);
    }

    public void aggiungiParola(String parola){
            parole.add(parola);
    }

    public void stampaFrase(Frase frase)
    {
        ArrayList<String>parole=frase.getParole();
        for (String s:
             parole) {
            System.out.print(" "+s);
        }
        System.out.println("");
    }

    public ArrayList<String> getParole() {
        return parole;
    }

    public boolean isEmpty(Frase frase)
    {
        ArrayList<String> parole=frase.getParole();
        if(parole.isEmpty()){
            return true;
        }
        return false;
    }

    public Frase(){};

    ArrayList<Testo> testoAppartenenza = new ArrayList<>();

    public Pagina paginaDestinazione;
}
