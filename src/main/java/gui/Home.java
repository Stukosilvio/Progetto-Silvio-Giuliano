package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {

    private static JFrame frame;

    public static JLabel accessoLabel;

    public static Controller controller = new Controller();

    public static void main(String[] args) {
        frame = new JFrame("Wiki");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Etichetta al centro
        JLabel welcomeLabel = new JLabel("Benvenuto Su Wikipedia");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Scritta "Accesso non effettuato"
        accessoLabel = new JLabel("Accesso non effettuato");
        accessoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        accessoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pannello per i pulsanti con GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton creaPagina = new JButton("Cerca Pagina");
        addButtonToPanel(buttonPanel, creaPagina, gbc, 0, 0);

        JButton searchButton = new JButton("Crea Pagina");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        CreaPagina creaPagina1 = new CreaPagina(frame, controller);
                        creaPagina1.mostraFinestra();
                    } catch (NullPointerException e1)
                    {
                        JOptionPane.showMessageDialog(null,"Registrati Prima");
                    }
            }
        });
        creaPagina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    CercaPagina cercaPagina = new CercaPagina(frame,controller);
                    cercaPagina.mostraFinestra();
            }
        });
        addButtonToPanel(buttonPanel, searchButton, gbc, 1, 0);

        JButton registerButton = new JButton("Registrati");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrazione registrazione = new Registrazione(frame, controller);
                registrazione.mostraFinestra();
                frame.setEnabled(false);
            }
        });
        addButtonToPanel(buttonPanel, registerButton, gbc, 2, 0);

        JButton loginButton = new JButton("Entra");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Hai premuto Entra");
            }
        });
        addButtonToPanel(buttonPanel, loginButton, gbc, 3, 0);

        // Aggiungi componenti al pannello principale
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(accessoLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Aggiungi l'immagine in alto a destra
        try {
            URL imageUrl = new URL("https://icon-icons.com/it/icona/profilo-utente/114033");
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            JLabel imageLabel = new JLabel(imageIcon);
            mainPanel.add(imageLabel, BorderLayout.EAST);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        frame.add(mainPanel);
    }

    private static void addButtonToPanel(JPanel panel, JButton button, GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(5, 5, 5, 5); // Spaziatura tra i pulsanti
        panel.add(button, gbc);
    }
}

