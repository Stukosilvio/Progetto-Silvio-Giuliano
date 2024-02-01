package gui;
import controller.Controller;
import model.Frase;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreaPagina {
    public static JFrame frameHome;
    private String testoInput = "";
    private ArrayList<Frase> frasiTesto = new ArrayList<>();
    private JPanel panel1;
    private JLabel titoloLabel;
    private JTextField titoloPaginaTextField;

    private JTextField fraseCollegamentoTextField;

    private JTextField paginaDestinazioneTextField;
    public JTextPane contenutoPaginaTextArea = new JTextPane();  // Cambiato da JTextField a JTextArea
    private JButton salvaButton;
    private JButton annullaButton;
    private JButton indietroButton;
    private JButton creaCollegamentoButton;
    int flagAnnullamento = 0;
    public ArrayList<String> frasiSottolineate = new ArrayList<>();

    HashMap<String,String> hashMap = new HashMap<>();
    CreaPagina(JFrame frameChiamante, Controller controller) {

        frameHome = new JFrame("Pagina Iniziale");
        frameHome.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameHome.setSize(700, 600);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        frameHome.setContentPane(panel1);
        titoloLabel = new JLabel("Scrivi La Tua Pagina " + controller.utente.username);
        titoloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel1.add(titoloLabel);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        titoloPaginaTextField = new JTextField("Titolo della Pagina");
        titoloPaginaTextField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(titoloPaginaTextField);
        contenutoPaginaTextArea = new JTextPane();
        contenutoPaginaTextArea.setText("Contenuto Della Pagina");
        contenutoPaginaTextArea.setPreferredSize(new Dimension(600, 400));  // Dimensione personalizzata// Dimensione personalizzata
        JScrollPane scrollPane = new JScrollPane(contenutoPaginaTextArea);
        inputPanel.add(scrollPane);
        salvaButton = new JButton("Salva");
        annullaButton = new JButton("Annulla");
        indietroButton = new JButton("Torna Indietro");
        fraseCollegamentoTextField = new JTextField("Inserisci La Frase Da Linkare");
        paginaDestinazioneTextField = new JTextField("Inserisci La Pagina di Destinazione");
        creaCollegamentoButton = new JButton("Crea Collegamento");

        contenutoPaginaTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                frasiTesto.clear();
                testoInput = contenutoPaginaTextArea.getText();
                //testoInput = testoInput.replaceAll("\\s+", " ");
                String[] parole = testoInput.split("\\s+|(?<=\\p{Punct})|(?=\\p{Punct})");
                Frase frase = new Frase(); // inizio con una frase vuota
                for (String s : parole) {
                    if (s.equals(".")) {
                        frase.aggiungiParola(s);
                        frasiTesto.add(frase);
                        frase = new Frase(); // reimposta l'istanza di Frase quindi frase diventa vuota
                    }
                    else if(contenimento(s,frasiSottolineate)){
                        frasiTesto.add(frase);
                        frase = new Frase();
                        frase.aggiungiParola(s);
                        frasiTesto.add(frase);
                        frase = new Frase();
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
                //questo lo aggiungo perchè alla fine di tutto mi creerà degli spazi vuoti e io li levo

                for (Frase f : frasiTesto) {
                    f.stampaFrase(f);
                }

            }
        });

        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo = titoloPaginaTextField.getText();
                String contenuto = contenutoPaginaTextArea.getText();
                String nomeAutore = controller.utente.username;
                try {
                    controller.addPagina(titolo,nomeAutore);
                    controller.addFrasi(frasiTesto,frasiSottolineate,hashMap);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Errore");
                }
            }
        });
        titoloPaginaTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                titoloPaginaTextField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String t = titoloPaginaTextField.getText();
                titoloPaginaTextField.setText(t);
            }
        });

        fraseCollegamentoTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            public void focusLost(FocusEvent e) {
                String t = titoloPaginaTextField.getText();
                titoloPaginaTextField.setText(t);
            }

        });

        paginaDestinazioneTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                paginaDestinazioneTextField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String t = titoloPaginaTextField.getText();
                titoloPaginaTextField.setText(t);
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aggiungi qui la logica per annullare le modifiche
                titoloPaginaTextField.setText("");
                contenutoPaginaTextArea.setText("");
                flagAnnullamento=1;
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nascondiFinestra();
            }
        });

        creaCollegamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hashMap.put(fraseCollegamentoTextField.getText(),paginaDestinazioneTextField.getText());
                frasiSottolineate.add(fraseCollegamentoTextField.getText());
                aggiungiEffettoCliccabile();
            }
        });
        inputPanel.add(salvaButton);
        inputPanel.add(annullaButton);
        inputPanel.add(indietroButton);
        inputPanel.add(fraseCollegamentoTextField);
        inputPanel.add(paginaDestinazioneTextField);
        inputPanel.add(creaCollegamentoButton);
        panel1.add(inputPanel);
    }
    public void mostraFinestra()
    {
        frameHome.setVisible(true);
    }

    public void nascondiFinestra()
    {
        frameHome.setVisible(false);
    }

    private void aggiungiEffettoCliccabile() {
        String contenuto = contenutoPaginaTextArea.getText();
        String fraseCliccabile = fraseCollegamentoTextField.getText();
        if (contenuto.contains(fraseCliccabile)) {
            StyledDocument styledDocument = contenutoPaginaTextArea.getStyledDocument();
            SimpleAttributeSet attributi = new SimpleAttributeSet();
            StyleConstants.setUnderline(attributi, true);
            StyleConstants.setForeground(attributi, Color.BLUE);
            int start = contenuto.indexOf(fraseCliccabile);
            int end = start + fraseCliccabile.length();
            styledDocument.setCharacterAttributes(start, end - start, attributi, false);
        } else {
            JOptionPane.showMessageDialog(frameHome, "La frase cliccabile non è presente nel testo.");
        }
    }
    public static boolean contenimento(String parola, ArrayList<String> frasi) {
        // Pulisci la parola da spazi aggiuntivi e caratteri speciali
        parola = parola.trim().replaceAll("[^a-zA-Z0-9]", "");

        for (String s : frasi) {
            // Pulisci anche la parola presente nell'ArrayList
            String parolaArrayList = s.trim().replaceAll("[^a-zA-Z0-9]", "");

            if (parola.contains(parolaArrayList)) {
                return true;
            }
        }
        for(String s : frasi)
        {
            String parolaArrayList = s.trim().replaceAll("[^a-zA-Z0-9]", "");
            if(parolaArrayList.contains(parola)){
                return true;
            }
        }
        return false;
    }
}

