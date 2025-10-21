/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acesso;

import java.awt.*;
import Aluno.*;
import Professor.*;
import Disciplina.*;
import Classe.Classe;
import Trimestre.*;
import javax.swing.*;
import javax.swing.event.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Anderson B. Gemo
 */
public class Menu extends JFrame{
    private JButton btnAluno, btnProf, btnAdmin;
    public Menu(){
    setSize(700, 560);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    setLocationRelativeTo(null);
    ImageIcon logo = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
    JLabel foto = new JLabel(logo);
    foto.setBounds(240, 30, 200, 113);
    add(foto);
    Font rw = new Font("Rockwell", Font.BOLD, 18);
    
    JPanel painel = new JPanel();
    painel.setBounds(20, 20, 680, 30);
    painel.setBackground(new Color(27, 62, 117));
    JLabel titulo = new JLabel("Bem Vindo ao Sistema Academico do ES3FI");
    titulo.setFont(rw);
    titulo.setForeground(Color.white);
    titulo.setBounds(SwingConstants.CENTER, 10, 200, 25);
    painel.add(titulo);
    add(painel);
    
    JPanel painel1 = new JPanel();
    painel1.setBackground(new Color(27, 62, 117));
    JLabel texto = new JLabel("Aluno"); 
    painel1.add(texto);
    texto.setForeground(Color.white);
    texto.setFont(rw);
    texto.setBounds(1, 5, 90, 20);
    painel1.setBounds(150, 170, 90, 30);
    add(painel1);
    FlatSVGIcon iconAluno = new FlatSVGIcon("svg/aluno.svg", 90, 80);
    JLabel lblIconA = new JLabel(iconAluno);
    lblIconA.setBounds(150, 200,90,80);
    add(lblIconA);
    
    
    FlatSVGIcon btnIcon = new FlatSVGIcon("svg/avancar.svg");
    btnAluno = new JButton();
    btnAluno.setIcon(btnIcon);
    btnAluno.setForeground(Color.white);
    btnAluno.setBounds(180, 310, 45, 35);
    btnAluno.setBackground(new Color(0, 0, 139));
    add(btnAluno);
    btnAluno.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    Classe classe = new Classe();
    Aluno aluno = new Aluno();
//    loginAluno la = new loginAluno(aluno, classe);
   // la.setVisible(true);
    dispose();
    }
    });
    
    JPanel painel2 = new JPanel();
    painel2.setBackground(new Color(27, 62, 117));
    painel2.setBounds(380, 170, 100, 30);
    add(painel2);
    JLabel texto2 = new JLabel("Professor");
    texto2.setBounds(1, 5, 100, 20);
    texto2.setFont(rw);
    texto2.setForeground(Color.white);
    painel2.add(texto2);
    FlatSVGIcon iconProf = new FlatSVGIcon("svg/prof.svg", 100, 90);
    JLabel lblIconP = new JLabel(iconProf);
    lblIconP.setBounds(380, 200, 100, 90);
    add(lblIconP);
    
    btnProf = new JButton();
    btnProf.setIcon(btnIcon);
    btnProf.setBounds(410, 310, 45, 35);
    btnProf.setBackground(new Color(0, 0, 139));
    add(btnProf);
    btnProf.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evt){
// //   loginProf lp = new loginProf();
  //  lp.setVisible(true);
    dispose();
    }
    });
    
    FlatSVGIcon iconAdmin = new FlatSVGIcon("svg/boss.svg");
    btnAdmin = new JButton();
    btnAdmin.setBounds(600, 480, 60, 30);
    btnAdmin.setBackground(new Color(0,169,85));
    btnAdmin.setIcon(iconAdmin);
    add(btnAdmin);
    btnAdmin.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evt){
    String senhaAdmin = JOptionPane.showInputDialog(null,"Digite a senha");
    int senhaAdminInt = Integer.parseInt(senhaAdmin);
    if(senhaAdminInt == 2706){
    JOptionPane.showMessageDialog(null, "Bem vindo");
   // FuncaoAdmin fa = new FuncaoAdmin();
//    fa.setVisible(true);
    }
    else{
    JOptionPane.showMessageDialog(null, "Erro!");
    }
    }
    });
    
    JLabel titulo2 = new JLabel("Escolhe a sua Categoria");
    titulo2.setBounds(SwingConstants.CENTER, WIDTH, WIDTH, HEIGHT);
    }
    
    public static void main(String[]abg){
        try{
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        }catch(Exception erro){
        
        }
    SwingUtilities.invokeLater(()-> new Menu().setVisible(true));
    }
}
