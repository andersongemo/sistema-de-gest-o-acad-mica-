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
import Admin.conexao;
import Aluno.Aluno;
import Professor.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Professor.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import Professor.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notas extends JFrame {
    private JLabel lblLogo;
    private JPanel menuEsquerdo, painelConteudo;
    private JButton btnNotas, btnExame, btnEditDados, btnHist, btnSair, btnAtualizarDados;
    private Connection conn;
    private Aluno aluno;
    private JLabel lbNome, lbApelido, lbDn, lbDoc, lbSenha;
    private JTextField txtNome, txtApelido, txtDoc, txtDn, txtSenha;

    public Notas(Aluno aluno) {
        this.aluno = aluno;
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font rw = new Font("Rockwell", Font.BOLD, 12);
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        lblLogo = new JLabel(icon);
        lblLogo.setBounds(20, -45, 150, 150);
        add(lblLogo);

        FlatSVGIcon user = new FlatSVGIcon("svg/user.svg", 40, 40);
        JLabel lbuser = new JLabel(aluno.getNome() + " " + aluno.getApelido());
        lbuser.setForeground(Color.white);
        lbuser.setBounds(190, 15, 220, 50);
        lbuser.setFont(rw);
        lbuser.setIcon(user);
        add(lbuser);

        JLabel lbId = new JLabel("Codigo: " + aluno.getId());
        lbId.setBounds(250, 45, 120, 20);
        lbId.setForeground(Color.white);
        lbId.setFont(rw);
        add(lbId);

        menuEsquerdo = new JPanel();
        menuEsquerdo.setLayout(null);
        menuEsquerdo.setBounds(15, 70, 160, 420);
        add(menuEsquerdo);

        FlatSVGIcon iconMenu = new FlatSVGIcon("svg/menu.svg", 40, 40);
        JLabel lbIconMenu = new JLabel(iconMenu);
        lbIconMenu.setBounds(50, 10, 40, 40);
        menuEsquerdo.add(lbIconMenu);

        FlatSVGIcon iconNotas = new FlatSVGIcon("svg/teste.svg", 40, 40);
        btnNotas = new JButton("Notas", iconNotas);
        btnNotas.setBounds(10, 60, 130, 50);
        btnNotas.setFont(rw);
        btnNotas.setForeground(Color.white);
        btnNotas.addActionListener(e -> mostrarPainelNotas());
        menuEsquerdo.add(btnNotas);

        FlatSVGIcon iconExame = new FlatSVGIcon("svg/exame.svg", 40, 40);
        btnExame = new JButton("Exames", iconExame);
        btnExame.setBounds(10, 130, 130, 50);
        btnExame.setFont(rw);
        btnExame.setForeground(Color.white);
        btnExame.addActionListener(e -> mostrarPainelExames());
        menuEsquerdo.add(btnExame);

        FlatSVGIcon iconED = new FlatSVGIcon("svg/pessoas.svg", 40, 40);
        btnEditDados = new JButton("Editar", iconED);
        btnEditDados.setBounds(10, 200, 130, 50);
        btnEditDados.setFont(rw);
        btnEditDados.setForeground(Color.white);
        btnEditDados.addActionListener(e-> mostrarPainelEdit());
        menuEsquerdo.add(btnEditDados);

        FlatSVGIcon iconHist = new FlatSVGIcon("svg/pasta.svg", 40, 40);
        btnHist = new JButton("Dados", iconHist);
        btnHist.setBounds(10, 270, 130, 50);
        btnHist.setFont(rw);
        btnHist.setForeground(Color.white);
        menuEsquerdo.add(btnHist);

        FlatSVGIcon iconSair = new FlatSVGIcon("svg/userLogout.svg", 40, 40);
        btnSair = new JButton("Sair", iconSair);
        btnSair.setBounds(10, 340, 130, 50);
        btnSair.setFont(rw);
        btnSair.setForeground(Color.white);
        btnSair.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        dispose();
        Professor professor = new Professor();
        Aluno aluno = new Aluno();
            Login lg = new Login(professor,aluno);
            lg.setVisible(true);
        }
        });
        
        menuEsquerdo.add(btnSair);
        painelConteudo = new JPanel(null);
        painelConteudo.setBounds(190, 70, 680, 420);
        painelConteudo.setBackground(new Color(38, 38, 38));
        add(painelConteudo);
        conectar();
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }
   

    private void mostrarPainelNotas() {
        painelConteudo.removeAll();
        painelConteudo.repaint();

        Font rw = new Font("Rockwell", Font.BOLD, 14);

        JLabel lblTrim = new JLabel("Trimestre:");
        lblTrim.setFont(rw);
        lblTrim.setForeground(Color.white);
        lblTrim.setBounds(20, 20, 100, 25);
        painelConteudo.add(lblTrim);

        JComboBox<String> cbTrimestre = new JComboBox<>();
        cbTrimestre.addItem("1 - Trimestre 1");
        cbTrimestre.addItem("2 - Trimestre 2");
        cbTrimestre.addItem("3 - Trimestre 3");
        cbTrimestre.setForeground(Color.white);
        cbTrimestre.setBounds(120, 20, 150, 25);
        painelConteudo.add(cbTrimestre);

        String[] colunas = {"Disciplina", "Teste 1", "Teste 2", "Teste 3", "Media", "Situacao"};
        DefaultTableModel linhas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabela = new JTable(linhas);
        tabela.setFont(new Font("Rockwell", Font.BOLD, 13));
        tabela.setRowHeight(24);
        tabela.setForeground(Color.white);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 70, 640, 300);
        scroll.setForeground(Color.white);
        painelConteudo.add(scroll);

      /*  JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(300, 380, 120, 25);
        painelConteudo.add(btnAtualizar);

        ActionListener verNotas = e -> carregarNotas(linhas, cbTrimestre.getSelectedIndex() + 1);
        cbTrimestre.addActionListener(verNotas);
        btnAtualizar.addActionListener(verNotas);*/

        mostrarNotas(linhas, 1);
        painelConteudo.revalidate();
        }
        private void mostrarPainelExames() {
        painelConteudo.removeAll();
        painelConteudo.repaint();

    Font rw = new Font("Rockwell", Font.BOLD, 14);

    String[] colunas = {"Disciplina", "Exame", "Situação"};
    DefaultTableModel linhas2 = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };

    JTable tabela = new JTable(linhas2);
    tabela.setFont(new Font("Arial", Font.PLAIN, 13));
    tabela.setRowHeight(24);

    JScrollPane scroll = new JScrollPane(tabela);
    scroll.setBounds(20, 20, 640, 340);
    painelConteudo.add(scroll);

    JButton btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setBounds(300, 370, 120, 25);
    painelConteudo.add(btnAtualizar);

    ActionListener carregar = e -> mostrarExame(linhas2);
    btnAtualizar.addActionListener(carregar);
    mostrarExame(linhas2);
    painelConteudo.revalidate();
     }
     public void mostrarPainelEdit(){
         Font rw = new Font("Rockwell", Font.BOLD, 15); 
         
     painelConteudo.removeAll();
     painelConteudo.repaint();
    
    lbNome = new JLabel("Nome");
    lbNome.setBounds(20,50, 70, 30);
    lbNome.setFont(rw);
    lbNome.setForeground(Color.white);
    painelConteudo.add(lbNome);
    
    txtNome = new JTextField(""+aluno.getNome());
    txtNome.setBounds(100, 50, 180, 30);
    txtNome.setForeground(Color.white);
    painelConteudo.add(txtNome);
    
    lbApelido = new JLabel("Apelido");
    lbApelido.setBounds(20, 90 , 70, 30);
    lbApelido.setFont(rw);
    lbApelido.setForeground(Color.white);
    painelConteudo.add(lbApelido);
     
    txtApelido = new JTextField(""+aluno.getApelido());
    txtApelido.setBounds(100, 90, 180, 30);
    txtApelido.setForeground(Color.white);
    painelConteudo.add(txtApelido);
    
    lbDn = new JLabel("Data de Nascimento");
    lbDn.setBounds(20, 130, 150, 30);
    lbDn.setForeground(Color.white);
    lbDn.setFont(rw);
    painelConteudo.add(lbDn);
    
    txtDn = new JTextField(""+aluno.getDataNas());
    txtDn.setBounds(180, 130, 100, 30);
    txtDn.setForeground(Color.white);
    painelConteudo.add(txtDn);
    
    lbDoc = new JLabel("Bilhte de identidade");
     lbDoc.setBounds(290, 50, 120, 30);
     lbDoc.setForeground(Color.white);
     lbDoc.setFont(rw);
     painelConteudo.add(lbDoc);
     
     txtDoc = new JTextField(""+aluno.getNrBi());
     txtDoc.setBounds(420, 50, 80, 30);
     txtDoc.setForeground(Color.white);
     painelConteudo.add(txtDoc);
     
     lbSenha = new JLabel("Palavra-passe");
     lbSenha.setBounds(290, 90, 120, 30);
     lbSenha.setForeground(Color.white);
      lbSenha.setFont(rw);
      painelConteudo.add(lbSenha);
      
      txtSenha = new JTextField(""+aluno.getSenha());
      txtSenha.setBounds(420, 90, 80, 30);
      txtSenha.setForeground(Color.white);
      painelConteudo.add(txtSenha);
      
     FlatSVGIcon icon1 = new FlatSVGIcon("svg/atualizar.svg");
     btnAtualizarDados = new JButton();
     btnAtualizarDados.setBounds(10, 200, 80, 35);
     btnAtualizarDados.setIcon(icon1);
     btnAtualizarDados.addActionListener(e-> atualizarDados());
     painelConteudo.add(btnAtualizarDados);
     
     }
         private void atualizarDados(){
           try(Connection conn = conexao.conectar()){
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
           JOptionPane.showMessageDialog(null, "Dados atualizados");
           }
             catch(Exception erro){
           JOptionPane.showMessageDialog(null, erro.getMessage());
           }
                        }

    private void mostrarExame(DefaultTableModel modelo) {
    modelo.setRowCount(0);
    try {
        String sql = "select d.nome_disciplina, e.nota, e.situacao " +
                     "from exame e " +
                     "inner join disciplina d on e.id_disciplina = d.id_disciplina " +
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
            modelo.addRow(new Object[]{disciplina, exame, situacao});
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao carregar exames: " + e.getMessage());
         }
                 }


    private void mostrarNotas(DefaultTableModel modelo, int trimestre) {
        modelo.setRowCount(0);
        try {
            String sql = "select d.nome_disciplina, n.n1, n.n2, n.n3, n.situacao " +
                    "from disciplina d " +
                    "left join nota n on d.id_disciplina = n.id_disciplina and n.id_aluno = ? and n.id_semestre = ? " +
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
                double media = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
                String situacao = rs.getString("situacao");
                modelo.addRow(new Object[]{disciplina, n1, n2, n3, media, situacao});
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar notas: " + erro.getMessage());
        }
    }
    

    public static void main(String[] abg) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception ignore) {}

        Aluno aluno = new Aluno();

        SwingUtilities.invokeLater(() -> new Notas(aluno).setVisible(true));
    }
}
