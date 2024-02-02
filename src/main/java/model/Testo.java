package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static gui.CreaPagina.contenimento;

public class Testo {
    public String contenuto;

    public Date dataUltimaModifica;

    public Time oraUltimaModifica;

    public Pagina pagina;

    public ArrayList<Frase> frasiTesto = new ArrayList<>();

    ArrayList<CronologiaTesti> testiPassati = new ArrayList<>();

    ArrayList<Modifiche_Testo> testiModificati = new ArrayList<>();

    public Testo(ArrayList<String> frasi)
    {
        Frase frase = new Frase();
        for (String s : frasi) {
            if (s.equals(".")) {
                frase.aggiungiParola(s);
                frasiTesto.add(frase);
                frase = new Frase(); // reimposta l'istanza di Frase quindi frase diventa vuota
            }
            else {
                frase.aggiungiParola(s);
            }
        }
        if (!frase.getParole().isEmpty()) {
            frasiTesto.add(frase);
        }

        Iterator<Frase> iterator = frasiTesto.iterator();
        while (iterator.hasNext()) {
            Frase f = iterator.next();
            if (f.isEmpty(f)) {
                iterator.remove();
            }
        }
    }

    public ArrayList<Frase> getFrasiTesto() {
        return frasiTesto;
    }
}
