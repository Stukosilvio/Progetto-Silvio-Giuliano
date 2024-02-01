package gui;
import controller.Controller;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class Registrazione {

            private JFrame frame;

            public Registrazione(JFrame frameChiamante, Controller controller) {
                frame = new JFrame("Registrazione");
                frame.setSize(400, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                initComponents(frameChiamante,controller);

                frame.setVisible(true);
            }

            private void initComponents(JFrame frameChiamante,Controller controller) {
                JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));

                // Etichette e campi di testo per utente
                JLabel userLabel = new JLabel("Utente:");
                JTextField userField = new JTextField();
                mainPanel.add(userLabel);
                mainPanel.add(userField);

                // Etichette e campi di testo per password
                JLabel passwordLabel = new JLabel("Password:");
                JPasswordField passwordField = new JPasswordField();
                mainPanel.add(passwordLabel);
                mainPanel.add(passwordField);

                // Etichette e campi di testo per email
                JLabel emailLabel = new JLabel("Email:");
                JTextField emailField = new JTextField();
                mainPanel.add(emailLabel);
                mainPanel.add(emailField);

                // Pulsante di registrazione
                JButton registerButton = new JButton("Registrati");
                JButton indietroButton = new JButton("Indietro");
                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = userField.getText();
                        String password = passwordField.getText();
                        String email = emailField.getText();
                        try {
                            Utente u =controller.addUtente(username,password,email);
                            controller.utente=u;
                            Home.accessoLabel.setText("Accesso Effettuato come : "+ controller.utente.username);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,"Errore inserimento Utente");
                        }

                    }
                });
                indietroButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frameChiamante.setEnabled(true);
                        nascondiFinestra();
                    }
                });

                // Aggiungi il pulsante al pannello principale
                mainPanel.add(registerButton);
                mainPanel.add(indietroButton);

                frame.add(mainPanel);
            }
            public void mostraFinestra()
            {
                frame.setVisible(true);
            }
            public void nascondiFinestra()
            {

                frame.setVisible(false);
            }
        }

