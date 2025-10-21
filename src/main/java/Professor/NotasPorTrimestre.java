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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class NotasPorTrimestre extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JComboBox<String> cbSemestre;

    private int idClasse = 1;
    private int idDisciplina = 1;
    private int idProf = 1;

    public NotasPorTrimestre() {
        setTitle("Lançamento de Notas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ComboBox para escolher semestre
        cbSemestre = new JComboBox<>(new String[]{"Semestre 1", "Semestre 2", "Semestre 3"});
        cbSemestre.addActionListener(e -> carregarAlunos());
        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Escolha o semestre:"));
        painelTopo.add(cbSemestre);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela: Nome, N1, N2, N3, Média, Situação
        String[] colunas = {"ID Aluno", "Nome Aluno", "N1", "N2", "N3", "Média", "Situação"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col >= 2 && col <= 4; // notas editáveis
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);

        // Centralizar notas, média e situação
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 2; i <= 6; i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(center);
            tabela.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Atualiza média e situação ao alterar nota
        modelo.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                try {
                    double n1 = Double.parseDouble(modelo.getValueAt(row, 2).toString());
                    double n2 = Double.parseDouble(modelo.getValueAt(row, 3).toString());
                    double n3 = Double.parseDouble(modelo.getValueAt(row, 4).toString());
                    double media = (n1 + n2 + n3) / 3.0;
                    modelo.setValueAt(String.format("%.2f", media), row, 5);
                    modelo.setValueAt(media >= 10 ? "Aprovado" : "Reprovado", row, 6);
                } catch (NumberFormatException ex) {
                    modelo.setValueAt(0.0, row, 5);
                    modelo.setValueAt("Reprovado", row, 6);
                }
            }
        });

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar Notas");
        btnSalvar.addActionListener(e -> salvarNotas());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarAlunos();
    }

    private void carregarAlunos() {
        modelo.setRowCount(0);
        int semestreSelecionado = cbSemestre.getSelectedIndex() + 1;

        String url = "jdbc:mysql://localhost:3306/novo";
        String usuario = "root";
        String senha = "2706";

        String sql = "SELECT id_aluno, nome_aluno FROM aluno WHERE id_turma=?";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClasse);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] linha = new Object[7];
                linha[0] = rs.getInt("id_aluno");
                linha[1] = rs.getString("nome_aluno");
                linha[2] = 0.0;
                linha[3] = 0.0;
                linha[4] = 0.0;
                linha[5] = 0.0;
                linha[6] = "";
                modelo.addRow(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + e.getMessage());
        }
    }

    private void salvarNotas() {
        int semestreSelecionado = cbSemestre.getSelectedIndex() + 1;
        String url = "jdbc:mysql://localhost:3306/novo";
        String usuario = "root";
        String senha = "2706";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idAluno = (int) modelo.getValueAt(i, 0);
                double n1 = Double.parseDouble(modelo.getValueAt(i, 2).toString());
                double n2 = Double.parseDouble(modelo.getValueAt(i, 3).toString());
                double n3 = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                double media = Double.parseDouble(modelo.getValueAt(i, 5).toString());
                String situacao = modelo.getValueAt(i, 6).toString();

                // Verifica se já existe
                String check = "SELECT COUNT(*) FROM nota WHERE id_aluno=? AND id_disciplina=? AND id_prof=? AND id_classe=? AND id_semestre=?";
                PreparedStatement psCheck = conn.prepareStatement(check);
                psCheck.setInt(1, idAluno);
                psCheck.setInt(2, idDisciplina);
                psCheck.setInt(3, idProf);
                psCheck.setInt(4, idClasse);
                psCheck.setInt(5, semestreSelecionado);
                ResultSet rs = psCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    // INSERT
                    String insert = "INSERT INTO nota (id_aluno,id_prof,id_disciplina,id_classe,id_semestre,n1,n2,n3,situacao) VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement psInsert = conn.prepareStatement(insert);
                    psInsert.setInt(1, idAluno);
                    psInsert.setInt(2, idProf);
                    psInsert.setInt(3, idDisciplina);
                    psInsert.setInt(4, idClasse);
                    psInsert.setInt(5, semestreSelecionado);
                    psInsert.setDouble(6, n1);
                    psInsert.setDouble(7, n2);
                    psInsert.setDouble(8, n3);
                    psInsert.setString(9, situacao);
                    psInsert.executeUpdate();
                } else {
                    // UPDATE
                    String update = "UPDATE nota SET n1=?, n2=?, n3=?, situacao=? WHERE id_aluno=? AND id_disciplina=? AND id_prof=? AND id_classe=? AND id_semestre=?";
                    PreparedStatement psUpdate = conn.prepareStatement(update);
                    psUpdate.setDouble(1, n1);
                    psUpdate.setDouble(2, n2);
                    psUpdate.setDouble(3, n3);
                    psUpdate.setString(4, situacao);
                    psUpdate.setInt(5, idAluno);
                    psUpdate.setInt(6, idDisciplina);
                    psUpdate.setInt(7, idProf);
                    psUpdate.setInt(8, idClasse);
                    psUpdate.setInt(9, semestreSelecionado);
                    psUpdate.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Notas salvas com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar notas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotasPorTrimestre().setVisible(true));
    }
}
