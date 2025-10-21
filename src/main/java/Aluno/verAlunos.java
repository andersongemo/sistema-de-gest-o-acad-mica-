/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aluno;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author Anderson B. Gemo
 */
public class verAlunos extends JFrame{
    private JTable tabela;
    private JScrollPane sc;
    private DefaultTableModel linhas;
    
    public verAlunos(){
    setSize(700, 600);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        setSize(700, 600);
        setLayout(new BorderLayout()); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] colunas = {"ID","Nome", "Apelido","Data Nasc.", "Sexo"};
        linhas = new DefaultTableModel(colunas, 0){
            @Override
        public boolean isCellEditable(int linhaTabela, int colunaTabela){
        return colunaTabela >=2 && colunaTabela <=3;
        }
        };
        tabela = new JTable(linhas);
        tabela.setBounds(20, 60, 500, 350); 
        sc = new JScrollPane(tabela);
        add(sc);
        alunos();
    
    }
    private void alunos(){
         linhas.setRowCount(0);
        int id;
        String nome, apelido, dataNas, sexo;
        try(Connection conn = conexao.conectar()){
    String sql ="select id_aluno, nome_aluno, apelido_aluno, anonas_aluno, sexo_aluno from aluno";
    PreparedStatement ps = conn.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    id = rs.getInt("id_aluno");
    nome = rs.getString("nome_aluno");
    apelido = rs.getString("apelido_aluno");
    dataNas = rs.getString("anonas_aluno");
    sexo = rs.getString("sexo_aluno");
    linhas.addRow(new Object[]{id, nome,apelido, dataNas, sexo});
    }
    }
    catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    public static void main(String[] abg){
    SwingUtilities.invokeLater(()-> new verAlunos().setVisible(true));
    }
}
