/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Professor;

/**
 *
 * @author Anderson Belarmino Gemo
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Classe.Classe;
import Disciplina.Disciplina;
import javax.swing.*;
import Trimestre.Trimestre;
import Classe.Classe;
import Disciplina.Disciplina;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TelaNotas extends JFrame {

    private JComboBox<String> cbTrimestre;
    private JTable tabela, tabelaExames;
    private DefaultTableModel linhas, linhas2;
    private JButton btnSalvar, btnExame;
    private Connection conn;
    private Professor professor;
    private Classe classe;
    private Disciplina disciplina;
    private JLabel lblLogo, lblProfessor, lblDisciplina, lblId, lblClasse;
    private JProgressBar pb;

    public TelaNotas(Professor professor, Classe classe, Disciplina disciplina, Trimestre trimestre) {
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;

        setTitle("ES3FI - Gestao de Notas");
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

      //  btnExame = new JButton("Exame");
      //  btnExame.setBounds(400, 40, 80, 25);
      //  add(btnExame);
     //   btnExame.addActionListener(new ActionListener(){
      //  public void actionPerformed(ActionEvent evt){
      //  TelaExame te = new TelaExame( professor, classe, disciplina, conn);
     //   }
       // });
        lblProfessor = new JLabel("Professor: " + professor.getNome_Professor()+" "+professor.getApelido_Professor());
        lblProfessor.setFont(rw);
        lblProfessor.setBounds(170, 20, 500, 22);
        lblProfessor.setForeground(Color.white);
        add(lblProfessor);
        
        lblId = new JLabel("Codigo: "+professor.getId_Professor());
        lblId.setFont(rw);
        lblId.setBounds(170, 40, 200, 22);
        lblId.setForeground(Color.white);
        add(lblId);

        lblDisciplina = new JLabel("Disciplina: " + disciplina.getNome_disciplina());
        lblDisciplina.setFont(rw);
        lblDisciplina.setBounds(170, 60, 400, 22);
        lblDisciplina.setForeground(Color.white);
        add(lblDisciplina);
        
        lblClasse = new JLabel("Classe: "+classe.getNome_Classe());
        lblClasse.setFont(rw);
        lblClasse.setBounds(170, 80, 400, 22);
        lblClasse.setForeground(Color.white);
        add(lblClasse);
        
        btnExame = new JButton("Exames");
        btnExame.setBounds(120, 470, 100, 30);
        btnExame.addActionListener(e -> {
            new TelaExame(conn, professor,disciplina, classe).setVisible(true);
                               });
         add(btnExame);

        cbTrimestre = new JComboBox<>();
        cbTrimestre.addItem("1 - Trimestre 1");
        cbTrimestre.addItem("2 - Trimestre 2");
        cbTrimestre.addItem("3 - Trimestre 3");
        cbTrimestre.setBounds(10, 130, 150, 25);
        cbTrimestre.setFont(rw);
        cbTrimestre.setForeground(Color.white);
        cbTrimestre.addActionListener(e -> carregarNotas(getTrimestreSelecionado()));
        add(cbTrimestre);
        
         FlatSVGIcon edit = new FlatSVGIcon("svg/editPessoa.svg");
        JButton btnEdit = new JButton();
        btnEdit.setBounds(680, 10, 35, 25);
        btnEdit.setIcon(edit);
        btnEdit.addActionListener(e-> mudar());
        add(btnEdit);

        String[] colunas = {"Codigo ", "Nome", "Teste 1", "Teste 2", "Teste 3", "Media", "Situacao"};
        linhas = new DefaultTableModel(colunas, 0) {
            @Override
        public boolean isCellEditable(int row, int column) {
            return column >= 2 && column <= 6; 
        }

        };
        tabela = new JTable(linhas);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 180, 860, 250);
        tabela.setFont(new Font("Arial Black", Font.BOLD, 12));
        tabela.setForeground(Color.white);
        add(scroll);
        
        pb = new JProgressBar(0, 100);
        pb.setBounds(30, 140, 220, 20);
        pb.setStringPainted(true);
        pb.setVisible(false);
        add(pb);
        

        btnSalvar = new JButton("Guardar");
        btnSalvar.setBounds(10, 470, 100, 30);
        btnSalvar.setFont(rw);
        btnSalvar.addActionListener(e -> guardarNotas(getTrimestreSelecionado()));
        add(btnSalvar);

        carregarNotas(getTrimestreSelecionado());
        setVisible(true);
           }

         private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
           
        }
              }
         

        private int getTrimestreSelecionado() {
            if (cbTrimestre.getSelectedItem() == null) return 1;
            return Integer.parseInt(cbTrimestre.getSelectedItem().toString().split(" - ")[0]);
        }

        private void carregarNotas(int trimestre) {
            linhas.setRowCount(0);
            try {
             String sql = "select a.id_aluno, a.nome_aluno, a.apelido_aluno, n.n1, n.n2, n.n3, n.situacao " +
             "from aluno a " +
             "left JOIN nota n on a.id_aluno = n.id_aluno " +
             "and n.id_semestre = ? and n.id_prof = ? and n.id_disciplina = ? and n.id_classe = ? " +
             "where a.id_classe = ? " +  
             "order BY a.nome_aluno";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, trimestre);
            ps.setInt(2, professor.getId_Professor());
            ps.setInt(3, disciplina.getId_disciplina());
            ps.setInt(4, classe.getId_Classe());
            ps.setInt(5, classe.getId_Classe());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            int id = rs.getInt("id_aluno");
            String nome = rs.getString("nome_aluno");
            String apelido = rs.getString("apelido_aluno");
            double n1 = rs.getDouble("n1");
            double n2 = rs.getDouble("n2");
            double n3 = rs.getDouble("n3");
            double mediaFinal = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
            String situacao;
            if (mediaFinal < 10) situacao = "Pessimo";
            else if (mediaFinal <= 14) situacao = "Bom";
            else situacao = "Excelente";

                linhas.addRow(new Object[]{id, nome+" "+apelido, n1, n2, n3, mediaFinal, situacao});
            }
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            }
             }

         private void guardarNotas(int trimestre) {
        try {
          String sql = "INSERT INTO nota (id_aluno, id_semestre, id_prof, id_disciplina, id_classe, n1, n2, n3, situacao) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
             "ON DUPLICATE KEY UPDATE n1=VALUES(n1), n2=VALUES(n2), n3=VALUES(n3), situacao=VALUES(situacao)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for(int i = 0; i < linhas.getRowCount(); i++) {
        int idAluno = (int) linhas.getValueAt(i, 0);
        double n1 = parseDoubleOrZero(linhas.getValueAt(i, 2));
        double n2 = parseDoubleOrZero(linhas.getValueAt(i, 3));
        double n3 = parseDoubleOrZero(linhas.getValueAt(i, 4));
        double mediaFinal = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
        String situacao;
        if (mediaFinal < 10) situacao = "Pessimo";
        else if (mediaFinal <= 14) situacao = "Bom";
        else situacao = "Excelente";

        ps.setInt(1, idAluno);
        ps.setInt(2, trimestre);
        ps.setInt(3, professor.getId_Professor());
        ps.setInt(4, disciplina.getId_disciplina());
        ps.setInt(5, classe.getId_Classe());
        ps.setDouble(6, n1);
        ps.setDouble(7, n2);
        ps.setDouble(8, n3);
        ps.setString(9, situacao);
        ps.executeUpdate();
        linhas.setValueAt(mediaFinal, i, 5);
        linhas.setValueAt(situacao, i, 6);
            }
        JOptionPane.showMessageDialog(null, "Notas Inseridas");
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }
          }
          public void mudar(){
           String senhaNova = JOptionPane.showInputDialog(null,"Digite a nova senha");
           try(Connection conn = Aluno.conexao.conectar()){
           String sql = "update professor set senha = ? where id_prof=?";
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setString(1, senhaNova);
           ps.setInt(2, professor.getId_Professor());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Actualizada");
           } catch(Exception erro){
           JOptionPane.showMessageDialog(null, erro.getMessage());
           }
                }
      
        private double parseDoubleOrZero(Object o) {
        if (o == null) return 0.0;
        try {
            return Double.parseDouble(o.toString());
        } catch (Exception e) {
            return 0.0;
        }
          
        }
        
        public static void main(String[] abg){
            try{
          UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
          }catch(Exception erro){
          erro.getMessage();
          }
            Professor professor = new Professor();
            Disciplina disciplina = new Disciplina();
            Trimestre trimestre = new Trimestre();
            Classe classe = new Classe();
        SwingUtilities.invokeLater(()-> new TelaNotas( professor,  classe,  disciplina,  trimestre).setVisible(true));
        }
        }


