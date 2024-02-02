package gui;

import controller.Controller;
import model.Frase;
import model.Testo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModificaPagina {
    private JFrame frame;

    private JTextPane textPane;

    public ModificaPagina(JFrame frameChiamante, Controller controller)
    {
        frame = new JFrame("Modifica il tuo testo");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Etichetta in alto a sinistra
        JLabel titleLabel = new JLabel("Modifica il tuo testo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Creazione del TextPane per inserire il testo
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);

        // Creazione dei bottoni
        JButton salvaButton = new JButton("Salva");
        JButton annullaButton = new JButton("Indietro");

        // Pannello per i bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);

        // Aggiunta dei componenti al layout della finestra
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        aggiuntaTesto(controller);


        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //quando l'utente clicca su salva noi andiamo a inserire una riga nella tabella Modifiche_testo
                String contenuto = textPane.getText();
                String autore = controller.getNomeUtente();
                String titolo = controller.getTitolo();
                try {
                    controller.addModifiche_testo(contenuto,autore,titolo);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Errore nel salvataggio del testo");
                }
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nascondiFinestra(frameChiamante);
            }
        });
    }
    public void mostraFinestra()
    {
        frame.setVisible(true);
    }
    public void nascondiFinestra(JFrame framechiamante)
    {
        frame.setVisible(false);
        framechiamante.setEnabled(true);
    }

    public void aggiuntaTesto(Controller controller)
    {
        StringBuilder risultato = new StringBuilder();
        Testo testo =controller.pagina.getTesto();
        ArrayList<Frase> frasitesto=testo.getFrasiTesto();
        for (Frase f: frasitesto) {
            ArrayList<String> parolefrase = f.getParole();
            for (String s:parolefrase) {
                risultato.append(s).append(" ");
            }
        }
        textPane.setText(String.valueOf(risultato));
    }
}
