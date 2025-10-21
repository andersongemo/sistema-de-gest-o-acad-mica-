package Aluno;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Anderson
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import java.sql.*;
import Turma.Turma;
import Classe.Classe;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;


public class RenovaMatriculaAluno extends JFrame{
    private JLabel titulo;
    private Aluno aluno;
    private Classe classe;
    private Turma turma;
    private JLabel lblId, lblClasseA;
    private JTextField txtId;
    private JButton btnVerNotas;
   
    
    public RenovaMatriculaAluno(Aluno aluno, Classe classe){
        this.aluno = aluno;
        this.classe  = classe;
    setSize(720, 580);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    Font rw = new Font("Rockwell", Font.BOLD, 15);
    
    JLabel titulo = new JLabel("RENOVACAO DE MATRICULA");
    titulo.setFont(rw);
    titulo.setForeground(Color.white);
    titulo.setBounds(250, 10, 250, 22);
    add(titulo);
    
    ImageIcon logo = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
    JLabel foto = new JLabel(logo);
    foto.setBounds(250, 30, 200, 113);
    add(foto);
    
    lblId = new JLabel("Codigo do Aluno:");
    lblId.setFont(rw);
    lblId.setForeground(Color.white);
    lblId.setBounds(270, 150, 180, 20);
    add(lblId);
    
    txtId = new JTextField();
    txtId.setBounds(290, 180, 100, 20);
    add(txtId);
    
    btnVerNotas = new JButton("OK");
    btnVerNotas.setBounds(300, 210, 80, 25);
    add(btnVerNotas);
    
    JPanel Medias = new JPanel();
    Medias.setBackground(Color.darkGray);
    Medias.setLayout(null);
    //Medias.setForeground(Color.WHITE);
    Medias.setBounds(20, 250, 660, 190);
    add(Medias);
    
    JLabel nomeAluno = new JLabel("Nome: ");
    nomeAluno.setFont(rw);
    nomeAluno.setForeground(Color.WHITE);
    nomeAluno.setBounds(15, 10, 60, 20);
    Medias.add(nomeAluno);
    
    JLabel disc1 = new JLabel("Portugues:");
    disc1.setBounds(15, 45, 100, 20);
    disc1.setFont(rw);
    disc1.setForeground(Color.white);
    Medias.add(disc1);
    
    JLabel disc2 = new JLabel("Matematica:");
    disc2.setBounds(15, 75, 100, 20);
    disc2.setFont(rw);
    disc2.setForeground(Color.white);
    Medias.add(disc2);
    
     JLabel disc3 = new JLabel("Biologia:");
    disc3.setBounds(15, 105, 100, 20);
    disc3.setFont(rw);
    disc3.setForeground(Color.white);
    Medias.add(disc3);
    
    JLabel disc4 = new JLabel("Fisica:");
    disc4.setBounds(15, 135, 100, 20);
    disc4.setFont(rw);
    disc4.setForeground(Color.white);
    Medias.add(disc4);
    
    JLabel disc5 = new JLabel("Quimica: ");
    disc5.setBounds(200, 45, 100, 20);
    disc5.setFont(rw);
    disc5.setForeground(Color.white);
    Medias.add(disc5);
    
    JLabel disc6 = new JLabel("Geografia: ");
    disc6.setBounds(200, 75, 100, 20);
    disc6.setFont(rw);
    disc6.setForeground(Color.white);
    Medias.add(disc6);
    
    JLabel disc7 = new JLabel("Historia: ");
    disc7.setBounds(200, 105, 100, 20);
    disc7.setFont(rw);
    disc7.setForeground(Color.white);
    Medias.add(disc7);
    
    JLabel disc8 = new JLabel("Ingles: ");
    disc8.setBounds(200, 135, 100, 20);
    disc8.setFont(rw);
    disc8.setForeground(Color.white);
    Medias.add(disc8);
    
    JLabel disc9 = new JLabel("Ed. Fisica: ");
    disc9.setBounds(350, 45, 100, 20);
    disc9.setFont(rw);
    disc9.setForeground(Color.white);
    Medias.add(disc9);
    
    }
    
    private void verNotasAluno(){
       aluno.setId(Integer.parseInt(txtId.getText()));
    try(Connection conn = conexao.conectar()){
    String sql = "";
    } catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    public static void main(String[] abg){
        try{
          UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
          }catch(Exception erro){
          erro.getMessage();
          }
       Aluno aluno = new Aluno();
       Classe classe = new Classe();
    SwingUtilities.invokeLater(()-> new RenovaMatriculaAluno(aluno, classe).setVisible(true));
    }
}
