/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

/**
 *
 * @author Anderson B. Gemo
 */
import Aluno.conexao;
import java.awt.*;
import Aluno.Aluno;
import javax.swing.*;
import java.sql.*;
import Classe.Classe; 
import Disciplina.Disciplina;
import Professor.Professor;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.event.*;
import javax.swing.table.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 *
 * @author Anderson B. Gemo
 */
public class AdminClasse extends JFrame{
    private int id_Disc;
    private JTable tabela;
    private DefaultTableModel linhas;
    private String[] Colunas;
    private JTextField txtDisc;
    private JButton btnAtualizar, btnVoltar;
    private JScrollPane scroll;
    private Aluno aluno;
    private menuAdmin menuA;
    private Classe classe;
    private Disciplina disciplina; 
    private JComboBox <Disciplina> cbDisciplina;
    
    public AdminClasse(Classe classe, Disciplina disciplina){
        this.classe = classe;
        this.disciplina = disciplina;
    setSize(720, 580);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setTitle("ADMIN -  Classe");
    Font rw = new Font("Rockwell", Font.BOLD, 15);
    
    FlatSVGIcon backIcon = new FlatSVGIcon("svg/voltar.svg");
    btnVoltar = new JButton("");
    btnVoltar.setIcon(backIcon);
    btnVoltar.setForeground(Color.white);
    btnVoltar.setFont(rw);
    btnVoltar.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent evt){
          Professor professor = new Professor();
        Classe classe =  new Classe();
        Disciplina disciplina = new Disciplina();
      AdminProf ap = new AdminProf(professor, classe, disciplina);
       AdminClasse ac = new AdminClasse(classe, disciplina);
          AdminAluno aa = new AdminAluno(aluno, classe);
    menuAdmin menuA = new menuAdmin(ac, ap, aa);
    menuA.setVisible(true);
    dispose();
    }
    });
    btnVoltar.setBounds(10, 450, 60, 30);
    add(btnVoltar);
    
    String Colunas[] = {"Codigo", "Nome","Classe"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(50, 50, 500, 250);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    add(scroll);
    ver_Default();
    TextFieldTabela();
    
    txtDisc = new JTextField();
    txtDisc.setBounds(50, 350, 130, 30);
    txtDisc.setForeground(Color.white);
    txtDisc.setFont(rw);
    add(txtDisc);
    
    JButton btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setBounds(180, 350, 130, 30);
    btnAtualizar.setFont(rw);
    btnAtualizar.setForeground(Color.white);
    btnAtualizar.addActionListener(e-> atualizarDisc() );
    add(btnAtualizar);
    }
    
    private void TextFieldTabela(){
        tabela.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
          int linha = tabela.getSelectedRow();
    if(linha != -1){
       id_Disc = Integer.parseInt(tabela.getValueAt(linha, 0).toString());
    txtDisc.setText(tabela.getValueAt(linha, 1).toString());
        }
          }
        });
    }
    
    private void atualizarDisc(){
    try(Connection conn = conexao.conectar()){
        disciplina.setId_disciplina(id_Disc);
        disciplina.setNome_disciplina(txtDisc.getText());
     String sql = "update disciplina set nome_disciplina=? where id_disciplina=?";
     PreparedStatement ps = conn.prepareStatement(sql);
     ps.setString(1,disciplina.getNome_disciplina());
     ps.setInt(2, id_Disc);
     ps.executeUpdate();
    } catch(SQLException erro){
    erro.printStackTrace();
    }
    }
    
    private void ver_Default(){
        try(Connection conn = conexao.conectar()){
        String sql = "select id_disciplina, nome_disciplina, id_classe from disciplina";
        PreparedStatement ps  = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
        disciplina.setId_disciplina(rs.getInt("id_disciplina"));
        disciplina.setNome_disciplina(rs.getString("nome_disciplina"));
        classe.setId_Classe(rs.getInt("id_classe"));
        linhas.addRow(new Object[] {disciplina.getId_disciplina(), disciplina.getNome_disciplina(), classe.getId_Classe()});
        }
        }catch(SQLException erro){
        JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    
    }
    
    
   public static void main(String[] abg){
       Classe classe = new Classe();
       Disciplina disciplina = new Disciplina();
     
       try{
           UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
       }catch(Exception erro){
       JOptionPane.showMessageDialog(null, erro.getMessage());
       }
   SwingUtilities.invokeLater(()-> new AdminClasse(classe, disciplina).setVisible(true));
   }
}

