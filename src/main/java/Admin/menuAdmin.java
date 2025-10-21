/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Aluno.Aluno;
import Professor.Professor;
import Classe.Classe;
import Disciplina.Disciplina;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.UIManager;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class menuAdmin extends JFrame {
    private JButton btnProf;
    private JButton btnAluno;
    private JButton btnDisciplinas;
    private JButton btnClasses;
    private AdminClasse ac;
    private AdminProf ap;
    private AdminAluno aa;
    public menuAdmin(AdminClasse ac, AdminProf ap, AdminAluno aa){
        this.ac = ac;
        this.ap = ap;
        Aluno aluno = new Aluno();
        Classe classe = new Classe();
        Disciplina disciplina = new Disciplina();
        Professor professor = new Professor();
        Font rw = new Font("Rockwell", Font.BOLD, 15);
        setSize(720, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        setForeground(Color.white);
        FlatSVGIcon adminIcon = new FlatSVGIcon("svg/boss.svg");
        JPanel titulo = new JPanel();
        titulo.setBounds(20, 20, 660, 30);
        JLabel titulo1 = new JLabel("Menu do Administrador - Dados");
        titulo1.setIcon(adminIcon);
        titulo1.setFont(rw);
        titulo1.setForeground(Color.white);
        titulo1.setBounds(250, 10, 250, 20);
        add(titulo);
        titulo.setBackground(new Color(27, 62, 117));
        titulo.add(titulo1);
        
        JLabel infoP = new JLabel("PROFESSORES");
        infoP.setBounds(80, 60, 140, 20);
        infoP.setFont(rw);
        infoP.setForeground(Color.white);
        add(infoP);
        FlatSVGIcon iconProf = new FlatSVGIcon("svg/prof.svg", 100, 90);
        JLabel lblIconProf = new JLabel(iconProf);
        lblIconProf.setBounds(80, 70, 100, 100);
        add(lblIconProf);
        btnProf = new JButton("OK");
        btnProf.setBackground(new Color(0, 0, 139));
        btnProf.setForeground(Color.white);
        btnProf.setBounds(100, 170, 60, 25);
        btnProf.addActionListener(new ActionListener(){
            @Override
        public void actionPerformed(ActionEvent e){
        ap.setVisible(true);
        dispose();
        }
        } );
        add(btnProf);
        
         JLabel infoA = new JLabel("ALUNOS");
        infoA.setBounds(400, 60, 100, 20);
        infoA.setFont(rw);
        infoA.setForeground(Color.white);
        add(infoA);
        FlatSVGIcon iconAluno = new FlatSVGIcon("svg/aluno.svg", 90, 80);
        JLabel lblIconAluno = new JLabel(iconAluno);
        lblIconAluno.setBounds(400, 70, 100, 100);
        add(lblIconAluno);
        btnAluno = new JButton("OK");
        btnAluno.setBackground(new Color(0, 0, 139));
        btnAluno.setForeground(Color.white);
        btnAluno.setBounds(420, 170, 60, 25);
        btnAluno.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        aa.setVisible(true);
        dispose();
        }
        } );
        add(btnAluno);
        
         JLabel infoD = new JLabel("DISCIPLINAS");
        infoD.setBounds(80, 240, 100, 20);
        infoD.setFont(rw);
        infoD.setForeground(Color.white);
        add(infoD);
        FlatSVGIcon iconDisc = new FlatSVGIcon("svg/livro.svg", 90, 80);
        JLabel lblIconDisc = new JLabel(iconDisc);
        lblIconDisc.setBounds(80, 250, 100, 100);
        add(lblIconDisc);
        btnDisciplinas = new JButton("OK");
        btnDisciplinas.setForeground(Color.white);
        btnDisciplinas.setBackground(new Color(0, 0, 139));
        btnDisciplinas.setBounds(100, 350, 60, 25);
        btnDisciplinas.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        ac.setVisible(true);
        dispose();
        }
        } );
        add(btnDisciplinas);
         
         JLabel infoC = new JLabel("CLASSES");
        infoC.setBounds(420, 240, 100, 20);
        infoC.setFont(rw);
        infoC.setForeground(Color.white);
        add(infoC);
        FlatSVGIcon iconClasse = new FlatSVGIcon("svg/classe.svg", 90,80);
        JLabel lbliconClasse = new JLabel(iconClasse);
        lbliconClasse.setBounds(400, 250, 100, 100);
        add(lbliconClasse);
        btnClasses = new JButton("OK");
        btnClasses.setBackground(new Color(0, 0, 139));
        btnClasses.setForeground(Color.white);
        btnClasses.setBounds(420, 350, 60, 25);
        btnClasses.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        ac.setVisible(true);
        dispose();
        }
        } );
        add(btnClasses);
        
        ImageIcon logo = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        JLabel foto = new JLabel(logo);
        foto.setBounds(20, 450, 140, 74);
        add(foto);
         
      } 
    public static void  main(String[] abg){
        Professor professor = new Professor();
        Classe classe =  new Classe();
        Aluno aluno = new Aluno();
        Disciplina disciplina = new Disciplina();
        AdminProf ap = new AdminProf(professor, classe, disciplina);
       AdminClasse ac = new AdminClasse(classe, disciplina);
       AdminAluno aa = new AdminAluno(aluno, classe);
       try{
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        }catch(Exception erro){
        erro.printStackTrace();
        }
    SwingUtilities.invokeLater(()-> new menuAdmin(ac, ap,aa).setVisible(true));
    }
}
