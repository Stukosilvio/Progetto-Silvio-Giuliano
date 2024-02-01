package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Testo {
    public String contenuto;

    public Date dataUltimaModifica;

    public Time oraUltimaModifica;

    public Pagina pagina;

    ArrayList<Frase> frasiTesto = new ArrayList<>();

    ArrayList<CronologiaTesti> testiPassati = new ArrayList<>();

    ArrayList<Modifiche_Testo> testiModificati = new ArrayList<>();

}
