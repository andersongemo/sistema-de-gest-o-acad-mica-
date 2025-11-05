/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acesso;

/**
 *
 * @author Anderson B. Gemo
 */

import Aluno.Aluno;
import Professor.Professor;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;

/*- *
- * @author Anderson B. Gemo
-*/
public class TelaSplash extends JFrame {
    private final JProgressBar pb;
    private final JLabel labelStatus;
 
    public TelaSplash() {
        setUndecorated(true);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
     
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setOpaque(false);

        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        JLabel labelImagem = new JLabel(icon);
         labelImagem.setBounds(220, 150, 200, 200);
         

        JLabel titulo = new JLabel("ES3FI", SwingConstants.CENTER);
        titulo.setFont(new Font("Rockwell", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        painelCentro.add(labelImagem, BorderLayout.CENTER);
        pb = new JProgressBar(0, 100);
        pb.setForeground(Color.ORANGE);
        pb.setStringPainted(true);

        labelStatus = new JLabel("A iniciar...", SwingConstants.CENTER);
        labelStatus.setForeground(Color.white);
        
        
        add(painelCentro, BorderLayout.CENTER);
        add(pb, BorderLayout.SOUTH);
        add(labelStatus, BorderLayout.NORTH);
    }

    public void iniciarCarregamento() {
        setVisible(true);
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50);
                    pb.setValue(i);
                    if (i == 20) labelStatus.setText("A iniciar...");
                    if (i == 50) labelStatus.setText("Bem vindo Anderson Gemo");                }
                dispose();
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
                } catch (Exception e) {
                }

                Aluno aluno = new Aluno();
                Professor professor = new Professor();
                new Login(professor, aluno).setVisible(true);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
            } catch (Exception e) {}

            TelaSplash splash = new TelaSplash();
            splash.iniciarCarregamento();
        });
    }
}
