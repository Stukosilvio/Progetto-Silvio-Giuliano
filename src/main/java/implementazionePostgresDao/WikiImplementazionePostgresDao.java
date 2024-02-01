package implementazionePostgresDao;

import dao.WikiDao;
import database.ConnessioneDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
