/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Professor.Professor;
import Classe.Classe;
import Aluno.Aluno;
import Disciplina.Disciplina;
import Professor.conexao;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
/*
 *
 * @author Anderson B. Gemo
 */
public class AdminProf extends JFrame{
    private Professor professor;
    private Disciplina disciplina;
    private Aluno aluno;
    private Classe classe;
    private JTable tabela;
    private DefaultTableModel linhas;
    private JScrollPane scroll;
    private menuAdmin menuA;
    private JComboBox <Disciplina> cbDisciplina, disciplinaEscolhida;
    private JComboBox <Classe> cbClasse, classeEscolhida;
    private JButton btnVerProf, btnAtualizar, btnVoltar;
    private int linhaTabela, id_Professor;
    private JTextField txtNome, txtApelido, txtDn;
    public AdminProf(Professor professor, Classe classe, Disciplina disciplina){
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;
        this.menuA = menuA;
    Font rw = new Font("Rockwell", Font.BOLD ,15);
    setSize(720, 580);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);
    FlatSVGIcon iconProf = new FlatSVGIcon("svg/prof.svg", 40, 35);
    JLabel icon = new JLabel("GESTAO DE PROFESSORES");
    icon.setFont(rw);
    icon.setForeground(Color.white);
    icon.setIcon(iconProf);
    icon.setBounds(240, 1, 300, 25);
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
    
    cbDisciplina = new JComboBox();
    cbDisciplina.setBounds(20, 300, 125, 35);
    cbDisciplina.setForeground(Color.white);
    //cbDisciplina.addActionListener(e-> verDisciplinas(disciplina));
    add(cbDisciplina);
    verDisciplinas();
    
    cbClasse = new JComboBox();
    cbClasse.setBounds(180, 300, 125, 35);
    cbClasse.setForeground(Color.white);
    add(cbClasse);
    verClasses();
    
    btnVerProf = new JButton("Ver");
    btnVerProf.setBounds(350, 300, 100, 30);
    btnVerProf.setFont(rw);
    btnVerProf.setForeground(Color.white);
    btnVerProf.addActionListener(e-> verProf());
    add(btnVerProf);
    
    String[] Colunas = {"Codigo", "Nome","Apelido", "Ano de Nascimento", "Classe","Disciplina"};
    linhas = new DefaultTableModel(Colunas,0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(20, 35, 680, 250);
    add(scroll);
    tabelaTxt();
    txtNome = new JTextField();
    txtNome.setForeground(Color.white);
    txtNome.setBounds(20, 350, 80, 30);
    add(txtNome);
    
    txtApelido = new JTextField();
    txtApelido.setForeground(Color.white);
    txtApelido.setBounds(120, 350, 80, 30);
    add(txtApelido);
    
    txtDn  = new JTextField();
    txtDn.setForeground(Color.white);
    txtDn.setBounds(220, 350, 80, 25);
    add(txtDn);
    FlatSVGIcon btnIcon = new FlatSVGIcon("svg/atualizar.svg");
    btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setIcon(btnIcon);
    btnAtualizar.setBounds(320, 350, 130, 30);
    btnAtualizar.setForeground(Color.white);
    btnAtualizar.setFont(rw);
    btnAtualizar.addActionListener(e-> atualizarFuncoes());
      add(btnAtualizar);
      
    
    }
    private void tabelaTxt(){
        tabela.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
        linhaTabela = tabela.getSelectedRow();
        if(linhaTabela !=-1){
        id_Professor =  Integer.parseInt(tabela.getValueAt(linhaTabela,0).toString());
        txtNome.setText(tabela.getValueAt(linhaTabela, 1).toString());
        txtApelido.setText(tabela.getValueAt(linhaTabela, 2).toString());
        txtDn.setText(tabela.getValueAt(linhaTabela, 3).toString());
        }
              }
        });
       
    }
    private void verProf(){
        try(Connection conn = conexao.conectar()){
            Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
        Disciplina disciplinaEscolhida = (Disciplina) cbDisciplina.getSelectedItem();
        String sql = "select * from professor where id_classe=? and id_disciplina=?";
    //String mySql = "select id_professor, nome_prof, apelido_prof, anonas_prof from professor where id_classe=? and id_disciplina=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    if(classeEscolhida != null){
    ps.setInt(1, classeEscolhida.getId_Classe());
    } 
    if(disciplinaEscolhida != null){
    ps.setInt(2, disciplinaEscolhida.getId_disciplina());
    }
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
     int idProf  = rs.getInt("id_prof");
     String nome  = rs.getString("nome_prof");
    String apelido = rs.getString("apelido_prof");
    String dn = rs.getString("anonas_prof");
    int id_classe = rs.getInt("id_classe");
    int id_disciplina = rs.getInt("id_disciplina");
    linhas.addRow(new Object[] {idProf, nome, apelido, dn, id_classe, id_disciplina});
             
    }
    } catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    private void verDisciplinas() {
      try (Connection conn = conexao.conectar()) {
     String select = "select id_disciplina, nome_disciplina from disciplina";
     PreparedStatement ps = conn.prepareStatement(select);
     ResultSet rs = ps.executeQuery();
     while (rs.next()) {
         Disciplina disciplina = new Disciplina();
         disciplina.setId_disciplina(rs.getInt("id_disciplina"));
         disciplina.setNome_disciplina(rs.getString("nome_disciplina")); 
         cbDisciplina.addItem(disciplina);
          }
      } catch (SQLException erro) {
     JOptionPane.showMessageDialog(null, erro.getMessage());
      }
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
       cbClasse.addItem(classe);
       }
       
       } catch(Exception erro){
       JOptionPane.showMessageDialog(null, erro.getMessage());
       }
    }
  
    private void atualizarFuncoes(){
    try(Connection conn = conexao.conectar()){
        Classe classe = new Classe();
        Disciplina disciplina =  new Disciplina();
        Disciplina disciplinaEscolhida = (Disciplina) cbDisciplina.getSelectedItem();
        int id_disciplina = disciplinaEscolhida.getId_disciplina();
        Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
        int id_classe  = classeEscolhida.getId_Classe();
        String sqlAP ="update professor set nome_prof=?, apelido_prof=?, anonas_prof=?, id_disciplina=?, id_classe=? where id_prof=?";
        PreparedStatement ps = conn.prepareStatement(sqlAP);
        professor.setId_Professor(id_Professor);
        professor.setNome_Professor(txtNome.getText());
        professor.setApelido_Professor(txtApelido.getText());
        professor.setDataNasc(txtDn.getText());
        ps.setString(1,professor.getNome_Professor());
        ps.setString(2, professor.getApelido_Professor());
        ps.setString(3, professor.getDataNasc());
        ps.setInt(4, id_disciplina);
        ps.setInt(5, id_classe);
        ps.setInt(6, professor.getId_Professor());
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Dados Atualizados com sucesso!");
    }catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
        public static void main(String[] abg){
            Professor professor = new Professor();
        Classe classe =  new Classe();
        Disciplina disciplina = new Disciplina();
            try{
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
            }catch(Exception erro){
            
            }
           SwingUtilities.invokeLater(()-> new AdminProf(professor, classe, disciplina).setVisible(true));
           }
    
}
