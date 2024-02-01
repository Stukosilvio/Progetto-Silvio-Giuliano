package model;

import java.util.ArrayList;

public class Autore extends Utente{
        public int H_index;

        public Autore(String nome,String password,String email)
        {
                super(nome,password, email);
        }

        ArrayList<Pagina> pagineCreate = new ArrayList<>();


}
