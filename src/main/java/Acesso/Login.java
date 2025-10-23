/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acesso;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import Aluno.Notas;
import Aluno.Aluno;
import Classe.Classe;
import Disciplina.Disciplina;
import Professor.Professor;
import Professor.TelaProfessor;
import com.formdev.flatlaf.extras.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import  Admin.*;
import Turma.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JLabel lblId, lblSenha, lblTipo;
    private JTextField txtId;
    private JPasswordField txtSenha;
    private JComboBox<String> cbTipo;
    private JButton btnEntrar;
    private Connection conn;
    private Professor professor;
    private Aluno aluno;

    public Login(Professor professor, Aluno aluno) {
        this.professor = professor;
        this.aluno = aluno;
        setTitle("Login - Sistema Academico ES3FI");
        setSize(680, 480);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        conectar();

        Font rw = new Font("Rockwell", Font.BOLD, 15);
        ImageIcon logo = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        JLabel foto = new JLabel(logo);
        foto.setBounds(70, 55, 220, 133);
        add(foto);
        
        FlatSVGIcon logo2 = new FlatSVGIcon("svg/science.svg", 90, 90);
        JLabel foto2 = new JLabel(logo2);
        foto2.setBounds(350, 50,80 , 90);
        add(foto2);
        JLabel lbTitulo = new JLabel("Gestão Académica ");
        lbTitulo.setFont(new Font("Rockwell", Font.PLAIN, 19));
        lbTitulo.setForeground(Color.white);
        lbTitulo.setBounds(330, 150, 200, 30);
        add(lbTitulo);

        lblTipo = new JLabel("Categoria:");
        lblTipo.setFont(rw);
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setBounds(180, 230, 120, 25);
        add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"Aluno", "Professor"});
        cbTipo.setBounds(300, 230, 200, 25);
        cbTipo.setFont(rw);
        cbTipo.setForeground(Color.WHITE);
        add(cbTipo);

        lblId = new JLabel("ID:");
        lblId.setFont(rw);
        lblId.setForeground(Color.WHITE);
        lblId.setBounds(180, 270, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(300, 270, 200, 25);
        txtId.setForeground(Color.WHITE);
        txtId.setBackground(Color.DARK_GRAY);
        add(txtId);

        lblSenha = new JLabel("Senha:");
        lblSenha.setFont(rw);
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setBounds(180, 310, 120, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(300, 310, 200, 25);
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setBackground(Color.DARK_GRAY);
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(250, 365, 200, 35);
        btnEntrar.setFont(rw);
        add(btnEntrar);
        btnEntrar.addActionListener(e -> login());

       
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }
    }

    private void login() {
        String tipoUsuario = cbTipo.getSelectedItem().toString();
        String idText = txtId.getText();
        String senha = new String(txtSenha.getPassword());

        if (idText.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos Vazios");
            return;
        }
        if (idText.equals("1234") && senha.equals("1234")) {
             Aluno aluno = new Aluno();
             Professor professor = new Professor();
            Classe classe = new Classe();
            Disciplina disciplina = new Disciplina();
            Turma turma = new Turma();
            TelaAdmin  ta = new TelaAdmin(aluno, professor, classe, disciplina, turma);
            ta.setVisible(true);
            dispose();
            return;
        }
        if (tipoUsuario.equals("Aluno")) {
            loginAluno(idText, senha);
        } else {
            loginProfessor(idText, senha);
        }
    }

    private void loginAluno(String txtID_aluno, String txtSenha_aluno) {
        try {
            aluno.setId(Integer.parseInt(txtID_aluno));
            aluno.setSenha(txtSenha_aluno);
            String sql = "Select * from aluno where id_aluno=? and senha_aluno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setString(2, aluno.getSenha());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aluno.setNome(rs.getString("nome_aluno"));
                aluno.setApelido(rs.getString("apelido_aluno"));
                aluno.setId_Classe(rs.getInt("id_classe"));
                aluno.setSenha(rs.getString("senha_aluno"));
                aluno.setDataNas(rs.getString("anonas_aluno"));
                aluno.setNrBi(rs.getString("nr_bi_aluno"));
                JOptionPane.showMessageDialog(null, "Bem-vindo " + aluno.getNome() + " " + aluno.getApelido());
                Notas n = new Notas(aluno);
                n.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalido!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage(),"ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginProfessor(String txtId, String txtSenha) {
        try {
            professor.setId_Professor(Integer.parseInt(txtId));
            professor.setSenha_Professor(txtSenha);
            String sql = "select * from professor where id_prof=? and senha=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, professor.getId_Professor());
            ps.setString(2, professor.getSenha_Professor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                professor.setNome_Professor(rs.getString("nome_prof"));
                professor.setApelido_Professor(rs.getString("apelido_prof"));
                professor.setNrBiProf(rs.getString("nr_bi_prof"));
                professor.setSenha_Professor(rs.getString("senha"));
                professor.setDataNasc(rs.getString("anonas_prof"));
                Classe classe = verClasseProfessor(professor.getId_Professor());
                Disciplina disc = verDisciplinaPorProfessor(professor.getId_Professor());

                JOptionPane.showMessageDialog(null, "Bem-vindo, " + professor.getNome_Professor() + " " + professor.getApelido_Professor());
                TelaProfessor tp = new TelaProfessor(professor, classe, disc);
                tp.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalido");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private Classe verClasseProfessor(int id_professor) {
        try {
            String sql = "Select c.* from classe c join professor p on c.id_classe = p.id_classe where p.id_prof = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, professor.getId_Professor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Classe classe = new Classe();
                classe.setId_Classe(rs.getInt("id_classe"));
                classe.setNome_Classe(rs.getString("nome_classe"));
                return classe;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Classe Invalida" + e.getMessage());
        }
        return null;
    }

    private Disciplina verDisciplinaPorProfessor(int id_professor) {
        try {
            String sql = "Select d.* from disciplina d join professor p on d.id_disciplina = p.id_disciplina where p.id_prof = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, professor.getId_Professor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId_disciplina(rs.getInt("id_disciplina"));
                d.setNome_disciplina(rs.getString("nome_disciplina"));
                return d;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar disciplina: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception e) {
        }
        Professor professor = new Professor();
        Aluno aluno = new Aluno();
        SwingUtilities.invokeLater(() -> new Login(professor, aluno).setVisible(true));
    }
}
