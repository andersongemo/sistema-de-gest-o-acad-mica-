/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Professor;

/**
 *
 * @author Anderson B. Gemo
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import Aluno.Aluno;
import Classe.Classe;
import Disciplina.Disciplina;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaExame extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnSalvar;
    private Connection conn;
    private Professor professor;
    private Disciplina disciplina;
    private Classe classe;
    private Aluno aluno;

    public TelaExame(Connection conn, Professor professor ,Disciplina disciplina, Classe classe) {
        this.conn = conn;
        this.professor = professor;
        this.disciplina = disciplina;
        this.classe = classe;

        setTitle("Lançamento de Exames");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  

        modelo = new DefaultTableModel(new String[]{"ID", "Aluno", "Exame", "Situação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 2; 
            }
        };

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnSalvar = new JButton("Salvar Exames");
        btnSalvar.addActionListener(e -> salvarExames());
        add(scroll, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);

        carregarExames();
    }

    private void carregarExames() {
        modelo.setRowCount(0);
        try {
            String sql = "SELECT a.id_aluno, a.nome_aluno, a.apelido_aluno, e.nota, e.situacao " +
                         "FROM aluno a " +
                         "LEFT JOIN exame e ON a.id_aluno = e.id_aluno " +
                         "AND e.id_prof = ? AND e.id_disciplina = ? AND e.id_classe = ? " +
                         "WHERE a.id_classe = ? " +
                         "ORDER BY a.nome_aluno";
            PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, professor.getId_Professor());   // estava começando em 3
        ps.setInt(2, disciplina.getId_disciplina()); 
        ps.setInt(3, classe.getId_Classe());         
        ps.setInt(4, classe.getId_Classe());


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_aluno");
                String nome = rs.getString("nome_aluno") + " " + rs.getString("apelido_aluno");
                double nota = rs.getDouble("nota");
                String situacao = rs.getString("situacao");

                if (rs.wasNull()) {
                    nota = 0.0;
                    situacao = "Pendente";
                }

                modelo.addRow(new Object[]{id, nome, nota, situacao});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar exames: " + e.getMessage());
        }
    }

    private void salvarExames() {
        try {
            String sql = "INSERT INTO exame (id_aluno, id_prof, id_disciplina, id_classe, nota, situacao) " +
                         "VALUES (?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE nota = VALUES(nota), situacao = VALUES(situacao)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idAluno = (int) modelo.getValueAt(i, 0);
                double nota = Double.parseDouble(modelo.getValueAt(i, 2).toString());
                String situacao = (nota < 10) ? "Reprovado" : "Aprovado";

                ps.setInt(1, idAluno);
                ps.setInt(2, professor.getId_Professor());
                ps.setInt(3, disciplina.getId_disciplina());
                ps.setInt(4, classe.getId_Classe());
                ps.setDouble(5, nota);
                ps.setString(6, situacao);

                ps.addBatch();
            }

            ps.executeBatch();
            JOptionPane.showMessageDialog(this, "Exames salvos com sucesso!");
            carregarExames();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar exames: " + e.getMessage());
        }
    }
    public static void main(String[] abg){
        Professor professor = new Professor();
    Classe classe =  new Classe();
     Disciplina disciplina = new Disciplina();
     Connection conn = null;
    SwingUtilities.invokeLater(()-> new TelaExame( conn,  professor , disciplina,  classe).setVisible(true));
    }
}
