package implementazionePostgresDao;

import dao.WikiDao;
import database.ConnessioneDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WikiImplementazionePostgresDao implements WikiDao {
    private Connection connection;

    public WikiImplementazionePostgresDao() throws SQLException {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Errore Durante L'Accesso Al Database");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Errore Connessione");
        }
    }

    @Override
    public void addUtenteDB(String nome, String password, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into utente (NomeUtente,password,email) values (?,?,?)");
            preparedStatement.setString(1,nome);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            int number =preparedStatement.executeUpdate();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Errore nell'inserimento dell'utente");
        }
    }

    @Override
    public void addFrasiDB(String contenuto, String collegamento) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Frasi (contenuto,paginadestinazione) values (?,?)");
            preparedStatement.setString(1,contenuto);
            preparedStatement.setString(2,collegamento);
            preparedStatement.executeUpdate();
            //preparedStatement.setString(2,collegamento);
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Errore con inserimento frasi");
            e.printStackTrace();
        }
    }

    @Override
    public void addFrasiWithoutLink(String contenuto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Frasi (contenuto) values (?)");
            preparedStatement.setString(1,contenuto);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Errore con inserimento frasi");
            e.printStackTrace();
        }
    }

    @Override
    public void addPagina(String titolo, String nomeAutore) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into pagina (titolo,autore) values (?,?)");
            preparedStatement.setString(1,titolo);
            preparedStatement.setString(2,nomeAutore);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Errore inserimento Pagina");
        }
    }

    @Override
    public void cercaPagina(String titolo, ArrayList<String> parolePaginaCercata) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select f.contenuto from pagina p,pagina_frasi pf,frasi f where p.titolo=pf.titolopagina and pf.idfrase = f.idfrase and p.titolo = ?");
            preparedStatement.setString(1,titolo);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                parolePaginaCercata.add(rs.getString(1));
            }
        } catch (SQLException e)
        {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Si è verificato un errore con la ricerca della pagina");
        }
    }

    @Override
    public String getCollegamento(String pagina, String parola) {
        try{
            //ti spiego il ragionamento di quell'order by, da questa select potrebbero uscire due o più collegamenti
            //quello che si fa è che se sono presenti 2 o più collegamenti vuol dire che il collegamento c'è
            // e non è null quindi andremo a prendere il collegamento,questo potrebbe succedere ad esempio nel
            //database quando facciamo la query di darci due tuple una con due parole uguali però una ha una pagina vuota
            //quello che si fa è prendere il link e metterlo
            PreparedStatement preparedStatement = connection.prepareStatement("select f.paginadestinazione from pagina p , pagina_frasi pf, frasi f where p.titolo=pf.titolopagina and pf.idfrase = f.idfrase and p.titolo = ? and f.contenuto = ? ORDER BY COALESCE(paginadestinazione, 'ZZZZZZZ') ASC;");
            preparedStatement.setString(1,pagina);
            preparedStatement.setString(2,parola);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e)
        {
                JOptionPane.showMessageDialog(null,"Errore con ottenimento dei collegamenti");
                e.printStackTrace();
        }
        return "";
    }

    @Override
    public void addModifiche_testo(String contenuto, String nomeAutore, String titoloPagina) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("insert into modifiche_testo (contenuto,autore,titolopagina) values (?,?,?)");
            preparedStatement.setString(1,contenuto);
            preparedStatement.setString(2,nomeAutore);
            preparedStatement.setString(3,titoloPagina);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Errore con l'invio del testo modificato");
            e.printStackTrace();
        }
    }
}
