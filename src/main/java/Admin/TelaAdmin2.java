/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Aluno.Aluno;
import Classe.Classe;
import Disciplina.Disciplina;
import Professor.Professor;
import Turma.Turma;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anderson B. Gemo
 */
public class TelaAdmin2 extends JFrame{
    private Aluno aluno;
    private Professor professor;
    private Classe classe;
    private Disciplina disciplina;
    private Turma turma;
    private JButton btnDadosPriv,btnPautasExame, btnPautas;
    private JPanel painelConteudo, menuEsquerdo;
    private JComboBox<String> cbPessoa;
    private JComboBox cbDisciplina, cbTrimestre, cbClasse;
    private int idAluno, idProf;
    private String senha; private JLabel  lbSenha;
    private JTextField txtId;
    private JScrollPane scroll;
    private JTable tabela;
    private DefaultTableModel linhas;
    
    
      public TelaAdmin2(Aluno aluno, Professor professor, Classe classe, Disciplina disciplina, Turma turma){
        this.aluno = aluno;
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;
        this.turma = turma;
        setSize(900, 570);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Menu do Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         Font rw = new Font("Rockwell", Font.BOLD, 12); 
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        JLabel lblLogo = new JLabel(icon);
        lblLogo.setBounds(20, -45, 150, 150);
        add(lblLogo);
        
        FlatSVGIcon user = new FlatSVGIcon("svg/user.svg", 40, 40);
        JLabel lbuser = new JLabel("ES3FI - ADMINISTRADOR");
        lbuser.setBounds(190, 15, 220, 50);
        lbuser.setFont(rw);
        lbuser.setForeground(Color.white);
        lbuser.setIcon(user);
        add(lbuser);
        
        painelConteudo = new JPanel(null);
        painelConteudo.setBounds(190, 70, 680, 420);
        painelConteudo.setBackground(new Color(38, 38, 38));
        add(painelConteudo);
        
        menuEsquerdo = new JPanel();
        menuEsquerdo.setLayout(null);
        menuEsquerdo.setBounds(15, 70, 160, 410);
        menuEsquerdo.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        btnDadosPriv = new JButton("Senhas");
        btnDadosPriv.setBounds(10, 5, 130,50);
        btnDadosPriv.setForeground(Color.white);
        btnDadosPriv.addActionListener(e-> painelDadosPrivados());
        btnDadosPriv.setFont(new Font("Rockwell", Font.BOLD, 12));
        menuEsquerdo.add(btnDadosPriv);
        
       btnPautas = new JButton("Pauta-Testes");
       btnPautas.setBounds(10, 70, 130, 50);
       btnPautas.setForeground(Color.white);
       btnPautas.setFont(new Font("Rockwell", Font.BOLD, 12));
       btnPautas.addActionListener(e-> painelPautas());
       menuEsquerdo.add(btnPautas);
     
          
       btnPautasExame = new JButton("Pauta-Exames");
       btnPautasExame.setBounds(10, 135, 130, 50);
       btnPautasExame.setFont(rw);
       btnPautasExame.setForeground(Color.white);
       btnPautasExame.addActionListener(e-> painelPautasExame());
       menuEsquerdo.add(btnPautasExame);
         add(menuEsquerdo);
      
     
      
      }
      public void painelDadosPrivados(){
      painelConteudo.removeAll();
      painelConteudo.repaint();
      Font rw = new Font("Rockwell", Font.BOLD, 15);
      JLabel titulo = new JLabel("Acesso a Dados Privados");
      titulo.setBounds(250, 20, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      JLabel lb1 = new JLabel("Verificar Senha");
      lb1.setForeground(Color.white);
      lb1.setFont(rw);
      lb1.setBounds(10, 50, 150, 25);
      painelConteudo.add(lb1);
      
      JLabel lb3 = new JLabel("Digite o ID: ");
      lb3.setFont(rw);
      lb3.setForeground(Color.white);
      lb3.setBounds(10, 80, 100, 25);
      painelConteudo.add(lb3);
      
      txtId = new JTextField();
      txtId.setBounds(130, 80, 100, 30);
      painelConteudo.add(txtId);
     
      lbSenha = new JLabel();
      lbSenha.setFont(rw);
      lbSenha.setForeground(Color.white);
      lbSenha.setBounds(10, 120, 100, 25);
      painelConteudo.add(lbSenha); 
      
      JButton btnAluno = new JButton("Aluno");
      btnAluno.setBounds(250, 80, 100, 30);
      btnAluno.setFont(rw);
      btnAluno.setForeground(Color.white);
      btnAluno.addActionListener(e-> verSenhaAluno());
      painelConteudo.add(btnAluno);
      
      JButton btnProf = new JButton("Professor");
      btnProf.setBounds(360, 80, 120, 30);
      btnProf.setFont(rw);
      btnProf.addActionListener(e-> verSenhaprof());
      btnProf.setForeground(Color.white);
      painelConteudo.add(btnProf);
      }
      
      public void painelPautas(){
          painelConteudo.removeAll();
          painelConteudo.repaint();
      Font rw = new Font("Rockwell", Font.BOLD, 15);
      JLabel titulo = new JLabel("Pautas/Resultados");
      titulo.setBounds(250, 0, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      cbTrimestre = new JComboBox();
      cbTrimestre.setFont(rw);
      cbTrimestre.setForeground(Color.white);
      cbTrimestre.setBounds(10, 20, 100, 30);
      painelConteudo.add(cbTrimestre);
      
      cbDisciplina = new JComboBox();
      cbDisciplina.setForeground(Color.white);
      cbDisciplina.setFont(rw);
      cbDisciplina.setBounds(130, 20, 100, 30);
      painelConteudo.add(cbDisciplina);
      
    String Colunas[] = { "ID","Nome","Teste 1", "Teste 2", "Teste 3","Media","Situacao"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 50, 630, 350);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    painelConteudo.add(scroll);
    
      }
      public void painelPautasExame(){
       painelConteudo.removeAll();
       painelConteudo.repaint();
      Font rw = new Font("Rockwell", Font.BOLD, 15);
      JLabel titulo = new JLabel("Pautas/Resultados");
      titulo.setBounds(250, 0, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      cbClasse = new JComboBox();
      cbClasse.setForeground(Color.white);
      cbClasse.setFont(rw);
      cbClasse.setBounds(10, 20, 100, 30);
      painelConteudo.add(cbClasse);
          
    String Colunas[] = {"Nome","Portugues", "Matematica", "Biologia","Fisica","Ingles","Media","Situacao"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 50, 630, 350);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    painelConteudo.add(scroll);
      
      }
      private void verSenhaAluno(){
          try(Connection conn =  conexao.conectar()) {
              idAluno = Integer.parseInt(txtId.getText());
              String sql = "select senha_aluno from aluno where id_aluno=?";
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idAluno);
             ResultSet rs=  ps.executeQuery();
             while(rs.next()){
              senha = rs.getString("senha_aluno");
              lbSenha.setText(senha);
             }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
          }
      }
      
      private void verSenhaprof(){
          try(Connection conn =  conexao.conectar()) {
              idProf = Integer.parseInt(txtId.getText());
              String sql = "select senha from professor where id_prof=?";
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idProf);
             ResultSet rs=  ps.executeQuery();
             while(rs.next()){
              senha = rs.getString("senha");
              lbSenha.setText(senha);
             }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
          }
      }
      
      
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception e) {
        }
        Aluno aluno = new Aluno();
        Professor professor = new Professor();
        Classe classe = new Classe();
        Disciplina disciplina = new Disciplina();
        Turma turma = new Turma();
        SwingUtilities.invokeLater(()-> new TelaAdmin2(aluno, professor, classe, disciplina, turma).setVisible(true));
    }
    
}
