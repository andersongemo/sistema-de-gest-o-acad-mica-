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
import Aluno.Aluno;
import Classe.Classe;
import Disciplina.Disciplina;
import Professor.Professor;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTAtomOneDarkIJTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 *
 * @author Anderson B. Gemo
 */
public class AdminAluno extends JFrame{
    private JTable tabela;
    private DefaultTableModel linhas;
    private JScrollPane scroll;
    private JComboBox <Classe> cbClasses;
    private JButton btnAtualizar, btnVoltar;
    private Aluno aluno;
    private menuAdmin menuA;
    private Classe classe, classeEscolhida;
    private JTextField txtNome, txtApelido, txtDn, txtSenha;
    private int id_Aluno;
    
    public AdminAluno(Aluno aluno, Classe classe){
    this.aluno =aluno;
    this.classe = classe;
    setSize(720, 580);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    Font rw = new Font("Rockwell", Font.BOLD, 15);

    FlatSVGIcon iconProf = new FlatSVGIcon("svg/aluno.svg", 40, 45);
    JLabel icon = new JLabel("GESTAO DE ALUNOS");
    icon.setFont(rw);
    icon.setForeground(Color.white);
    icon.setIcon(iconProf);
    icon.setBounds(240, 10, 300, 45);
    add(icon);
    
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
    
     String[] Colunas = {"CODIGO", "NOME","APELIDO", "DATA DE NASCIMENTO","SENHA"};
    linhas = new DefaultTableModel(Colunas,0);
    tabela = new JTable(linhas);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 60, 680, 250);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    add(scroll);
     verAlunos();
    
    FlatSVGIcon btnIcon  = new FlatSVGIcon("svg/atualizar.svg");
    btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setIcon(btnIcon);
    btnAtualizar.setBounds(10, 370, 130, 35);
    btnAtualizar.setForeground(Color.white);
     btnAtualizar.addActionListener(e-> atualizarDados());
    add(btnAtualizar);

    txtNome = new JTextField();
    txtNome.setBounds(10, 320, 100, 30);
    txtNome.setForeground(Color.white);
    txtApelido = new JTextField();
    txtApelido.setBounds(120, 320, 100, 30);
    txtApelido.setForeground(Color.white);
    txtDn = new JTextField();
    txtDn.setBounds(220, 320, 100, 30);
    txtDn.setForeground(Color.white);
    txtSenha = new JTextField();
    txtSenha.setBounds(340, 320, 100, 30);
    txtSenha.setForeground(Color.white);
    add(txtSenha);
    add(txtNome);
    add(txtApelido);
    add(txtDn);
    
    cbClasses = new JComboBox();
    cbClasses.setBounds(450, 320, 100, 30);
    add(cbClasses);
    verClasses();
    tabelaTextField();
   
   
    
    }
    
   private void verClasses(){
       try(Connection conn = conexao.conectar()){
       String mySql = "select id_classe, nome_classe from classe";
       PreparedStatement ps = conn.prepareStatement(mySql);
       ResultSet rs = ps.executeQuery();
       while(rs.next()){
           Classe  classe = new Classe();
       classe.setId_Classe(rs.getInt("id_classe"));
       classe.setNome_Classe(rs.getString("nome_classe"));
       cbClasses.addItem(classe);
       }
       
       } catch(Exception erro){
       JOptionPane.showMessageDialog(null, erro.getMessage());
       }
    }

    private void tabelaTextField(){
       tabela.addMouseListener(new MouseAdapter(){
       @Override
       public void mouseClicked(MouseEvent e){
       int linha= tabela.getSelectedRow();
       if(linha != -1){
           id_Aluno = Integer.parseInt(tabela.getValueAt(linha, 0).toString());
           txtNome.setText(tabela.getValueAt(linha, 1).toString());
           txtApelido.setText(tabela.getValueAt(linha, 2).toString());
           txtDn.setText(tabela.getValueAt(linha, 3).toString());
           txtSenha.setText(tabela.getValueAt(linha, 4).toString());
       }
             }
              });
                     }

    public void verAlunos(){
        //
    try(Connection conn = conexao.conectar()){
    String mySql = "select id_aluno, nome_aluno, apelido_aluno, anonas_aluno, senha_aluno from aluno";
    PreparedStatement ps = conn.prepareStatement(mySql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    int id_Aluno = rs.getInt("id_aluno");
    String nome = rs.getString("nome_aluno");
    String apelido = rs.getString("apelido_aluno");
    String dn = rs.getString("anonas_aluno");
    String senha = rs.getString("senha_aluno");
    linhas.addRow(new Object[] {id_Aluno, nome, apelido, dn, senha});
    }
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }

    private void atualizarDados(){
           try(Connection conn = conexao.conectar()){
            aluno.setId(id_Aluno);
           aluno.setNome(txtNome.getText());
           aluno.setApelido(txtApelido.getText());
           aluno.setDataNas(txtDn.getText());
           aluno.setSenha(txtSenha.getText());
           String  mySql ="update aluno set nome_aluno=?, apelido_aluno=?, anonas_aluno=?, senha_aluno=? where id_aluno=?";
           PreparedStatement ps = conn.prepareStatement(mySql);
           ps.setString(1, aluno.getNome());
           ps.setString(2, aluno.getApelido());
           ps.setString(3, aluno.getDataNas());
           ps.setString(4, aluno.getSenha());
           ps.setInt(5, aluno.getId());
           ps.executeUpdate();
           }
             catch(Exception erro){
           JOptionPane.showMessageDialog(null, erro.getMessage());
           }
                        }
       public static void main(String[] abg){
         try{
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        }catch(Exception erro){
       
        }
                Aluno aluno = new Aluno();
                Classe classe = new Classe();
    SwingUtilities.invokeLater(()-> new AdminAluno(aluno, classe).setVisible(true));
    }
}



