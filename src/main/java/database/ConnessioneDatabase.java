package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnessioneDatabase {
    private static ConnessioneDatabase istance;

    public Connection connection = null;

    public String nome = "postgres";

    public String password = "Dragonball";

    String Url = "jdbc:postgresql://localhost:5432/SchemaFisicoPaginaWiki";

    private String driver = "org.postgresql.Driver";
    private ConnessioneDatabase() throws SQLException
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(Url,nome,password);
            this.connection=connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Andato male la connessione");
            e.printStackTrace();
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (istance == null) {
            istance = new ConnessioneDatabase();
        }
        else if (istance.connection.isClosed()) {
            istance = new ConnessioneDatabase();
        }
        return istance;
    }
}
