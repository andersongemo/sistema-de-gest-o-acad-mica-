/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aluno;

/**
 *
 * @author Anderson B. Gemo
 */
import Acesso.Login;

import javax.swing.*;
import Classe.Classe;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaNotasAluno extends JFrame {
    private JComboBox <String> cbTrimestre;
    private JTable tabela;
    private DefaultTableModel linhas;
    private Connection conn;
    private JButton btnTrocar;
    private Aluno aluno;
    private Classe classe;
    private JLabel lblLogo, lblAluno, lblTrimestre, lblNome, lblClasse;

    public TelaNotasAluno(Aluno aluno) {
        this.aluno =aluno;
        setTitle("ES3F - Notas do Aluno");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        conectar();

        Font rw = new Font("Rockwell", Font.BOLD, 15);
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        lblLogo = new JLabel(icon);
        lblLogo.setBounds(10, -20, 150, 150);
        add(lblLogo);

        lblNome = new JLabel("Nome: "+aluno.getNome()+" "+aluno.getApelido());
        lblNome.setFont(rw);
        lblNome.setBounds(170, 20, 400, 22);
        lblNome.setForeground(Color.white);
        add(lblNome);
        
        lblAluno = new JLabel("Codigo: "+aluno.getId());
        lblAluno.setFont(rw);
        lblAluno.setBounds(170, 40, 400, 22);
        lblAluno.setForeground(Color.white);
        add(lblAluno);
        
        FlatSVGIcon icon2 = new FlatSVGIcon("svg/logout.svg");
        btnTrocar = new JButton();
        btnTrocar.setBounds(720, 10, 30, 25);
        btnTrocar.setIcon(icon2);
        btnTrocar.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
//          Login lg = new Login();
  //       lg.setVisible(true);
         dispose();
        }
        });
        add(btnTrocar);
        FlatSVGIcon edit = new FlatSVGIcon("svg/editPessoa.svg");
        JButton btnEdit = new JButton();
        btnEdit.setBounds(680, 10, 35, 25);
        btnEdit.setIcon(edit);
        btnEdit.addActionListener(e-> mudar());
        add(btnEdit);
        
        cbTrimestre = new JComboBox<>();
        cbTrimestre.addItem("1 - Trimestre 1");
        cbTrimestre.addItem("2 - Trimestre 2");
        cbTrimestre.addItem("3 - Trimestre 3");
        cbTrimestre.setBounds(20, 130, 150, 25);
        cbTrimestre.setFont(rw);
        cbTrimestre.setForeground(Color.white);
        cbTrimestre.addActionListener(e -> carregarNotas(getTrimestreEscolhido()));
        add(cbTrimestre);
        
        JButton btnVerExames = new JButton("Ver Exames");
        btnVerExames.setBounds(200, 130, 150, 25); 
        btnVerExames.setFont(rw);
        btnVerExames.addActionListener(e -> {
            int trimestre = getTrimestreEscolhido();
            new TelaExameAluno(aluno); });
          add(btnVerExames);

        String[] colunas = {"Disciplina", "Teste 1", "Teste 2", "Teste 3", "Media", "Situação"};
        linhas = new DefaultTableModel(colunas, 0) {
           @Override
        public boolean isCellEditable(int row, int column) {
             return false; 
            }
        };
        tabela = new JTable(linhas);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 180, 860, 250);
        tabela.setFont(new Font("Arial Black", Font.BOLD, 12));
        tabela.setForeground(Color.white);
        add(scroll);
        carregarNotas(getTrimestreEscolhido());
        setVisible(true);
        }

        private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
         } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: "+erro.getMessage());
         }
         }
         private int getTrimestreEscolhido() {
         if(cbTrimestre.getSelectedItem() == null) return 1;
         return Integer.parseInt(cbTrimestre.getSelectedItem().toString().split(" - ")[0]);
            }

         private void carregarNotas(int trimestre) {
          linhas.setRowCount(0);
         try {
          String sql = "select d.nome_disciplina, n.n1, n.n2, n.n3, n.situacao " +
             "from disciplina d " +
             "left join nota n " +
             "  on d.id_disciplina = n.id_disciplina " +
             "  and n.id_aluno = ? " +
             "  and n.id_semestre = ? " +
             "where d.id_classe = ? " +
             "order by d.nome_disciplina";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, aluno.getId());          
        ps.setInt(2, trimestre);              
        ps.setInt(3, aluno.getId_Classe());    
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String disciplina = rs.getString("nome_disciplina");
            double n1 = rs.getDouble("n1");
            double n2 = rs.getDouble("n2");
            double n3 = rs.getDouble("n3");
            String situacao = rs.getString("situacao");
            double media = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
            linhas.addRow(new Object[]{disciplina, n1, n2, n3, media, situacao});
        }
        } catch (SQLException erro) {
           JOptionPane.showMessageDialog(null, erro.getMessage());
        }
        }
        public void mudar(){
           String senhaNova = JOptionPane.showInputDialog(null,"Digite a nova senha");
           try(Connection conn = conexao.conectar()){
           String sql = "update aluno set senha_aluno = ? where id_aluno=?";
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setString(1, senhaNova);
           ps.setInt(2, aluno.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Actualizada");
           } catch(Exception erro){
           JOptionPane.showMessageDialog(null, erro.getMessage());
           }
             }
        public static void main(String[] args) {
        try{
          UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
          }catch(Exception erro){
        
          }
        Aluno aluno = new Aluno();
        SwingUtilities.invokeLater(() -> new TelaNotasAluno(aluno));
    }
}
