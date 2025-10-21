/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aluno;

/**
 *
 * @author Anderson B. Gemo
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaExameAluno extends JFrame {

    private JTable tabela;
    private DefaultTableModel model;
    private Connection conn;
    private Aluno aluno;

    public TelaExameAluno(Aluno aluno) {
        this.aluno = aluno;

        setTitle("Exames - " + aluno.getNome() + " " + aluno.getApelido());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cabeçalho da tabela
        String[] colunas = {"Disciplina", "Exame", "Situação"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // aluno só pode ver
            }
        };

        tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);
        conectar();
        carregarExames();
        setVisible(true);
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/novo", "root", "2706"
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage());
        }
    }

    private void carregarExames() {
        model.setRowCount(0);
        try {
            String sql = "select d.nome_disciplina, e.nota, e.situacao " +
                         "from exame e " +
                         "inner JOIN disciplina d ON e.id_disciplina = d.id_disciplina " +
                         "where e.id_aluno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String disciplina = rs.getString("nome_disciplina");
                double exame = rs.getDouble("nota"); 
                String situacao = rs.getString("situacao");
                if (rs.wasNull()) {
                    exame = 0.0;
                    situacao = "Pendente";
                }
                model.addRow(new Object[]{disciplina, exame, situacao});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar exames: " + e.getMessage());
        }
    }

 
    public static void main(String[] args) {
        Aluno alunoTeste = new Aluno();
        SwingUtilities.invokeLater(() -> new TelaExameAluno(alunoTeste));
    }
}
