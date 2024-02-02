package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CercaPagina {

    private JFrame frameCerca;
    private JTextField searchBar;
    private JButton searchButton;

    private JButton indietroButton;

    private JButton modificaButton;
    private JTextPane risultatiTextArea;
    private Controller controller;

    String termineRicerca ;
    CercaPagina(JFrame frameChiamante, Controller controller) {

        // Creazione della finestra CercaPagine
        frameCerca = new JFrame("Cerca Pagine");
        frameCerca.setSize(600, 400);
        frameCerca.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCerca.setLayout(new BorderLayout());
        // Creazione del pannello superiore con la barra di ricerca
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        searchBar = new JTextField(20);
        searchButton = new JButton("Cerca");
        indietroButton = new JButton("Indietro");
        modificaButton = new JButton("Modifica Testo");
        topPanel.add(searchBar);
        topPanel.add(searchButton);
        topPanel.add(indietroButton);
        topPanel.add(modificaButton);
        modificaButton.setVisible(false);


        // Creazione dell'area di testo per visualizzare i risultati
        risultatiTextArea = new JTextPane();
        risultatiTextArea.setContentType("text/html");
        JScrollPane scrollPane = new JScrollPane(risultatiTextArea);

        // Aggiunta dei componenti al layout della finestra
        frameCerca.add(topPanel, BorderLayout.NORTH);
        frameCerca.add(scrollPane, BorderLayout.CENTER);
        // Aggiunta dell'azione al pulsante di ricerca
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nascondiFinestra(frameChiamante);
            }
            });

        risultatiTextArea.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JTextPane source = (JTextPane) e.getSource();
                            // Ottieni la posizione del clic nel documento
                            int offset = source.viewToModel(e.getPoint());
                            // Ottieni la parola a partire dalla posizione del clic
                            try {
                                int start = Utilities.getWordStart(source, offset);
                                int end = Utilities.getWordEnd(source, offset);

                                String parolaCliccata = source.getText(start, end - start);
                                ArrayList<String> paroleTextCliccato = new ArrayList<>();
                                JOptionPane.showMessageDialog(null, "Hai cliccato sulla parola: " + parolaCliccata);
                            } catch (BadLocationException ex) {
                                JOptionPane.showMessageDialog(null, "Clicca Meglio !");
                            }
                        }


                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
         });
        modificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getUtente() == null || controller.getUtente().getNomeUtente() == null) {
                    JOptionPane.showMessageDialog(null, "Registrati");
                } else {
                    ModificaPagina modificaPagina = new ModificaPagina(frameCerca, controller);
                    modificaPagina.mostraFinestra();
                    frameCerca.setEnabled(false);
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String titoloPaginaDaCercare = searchBar.getText();
                ArrayList<String> parolePaginaDaCercare = new ArrayList<>();
                StringBuilder risultatoFinale = new StringBuilder("<html>");
                try {
                    //questo metodo mi rimpie parolePaginaDaCercare, ricorda sono parole però la traccia
                    //richiede che con la ricerca ci sia pure il vedere i collegamenti quindi adesso
                    //mi cerco i collegamenti relativi ad ogni parola
                    controller.cercaPagina(titoloPaginaDaCercare,parolePaginaDaCercare);
                    for (String parola: parolePaginaDaCercare) {
                         //adesso mi scorro le parole e vedo quale si questa ha dei collegamenti
                        //se il collegamento è != null allora sottolineo la parola all'interno del testo.
                        String link = controller.ottieniCollegamentoParola(titoloPaginaDaCercare,parola);
                        if(link != null)
                        {
                            risultatoFinale.append("<a href=\"").append(link).append("\">").append(parola).append("</a> ");
                        }
                        else
                        {
                            risultatoFinale.append(parola).append(" ");
                        }
                    }
                    risultatoFinale.append("</html>");
                    risultatiTextArea.setText(String.valueOf(risultatoFinale));
                    modificaButton.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Errore nella ricerca");
                }
            }
        });
    }
    public void mostraFinestra() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frameCerca.setVisible(true);
            }
        });
    }

    public void nascondiFinestra(JFrame frameChiamante) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frameChiamante.setEnabled(true);
                frameCerca.setVisible(false);
            }
        });
    }

}
