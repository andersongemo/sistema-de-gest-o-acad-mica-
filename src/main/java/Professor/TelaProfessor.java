package Professor;

import javax.swing.*;
import java.awt.*;
import Classe.Classe;
import Disciplina.Disciplina;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import Classe.Classe;
import Disciplina.Disciplina;
import Trimestre.Trimestre;

public class TelaProfessor extends JFrame {

    private JLabel lblLogo, lblTipoUsuario, lbNome, lbApelido, lbDn, lbDoc, lbSenha;
    private JPanel menuEsquerdo, menuInferior, painelConteudo;
    private JButton btnNotas, btnExame, btnEditDados, btnHist, btnSair, btnAtualizarDados;
    private Connection conn;
    private Professor professor;
    private Disciplina disciplina;
    private Classe classe;
    private Trimestre trimestre;
    private JTextField txtNome, txtApelido, txtDoc, txtDn, txtSenha;

    // Componentes painel de notas
    private JTable tabela, tabelaExame;
    private DefaultTableModel linhas, modelo;
    private JComboBox<String> cbTrimestre;
    private JButton btnSalvar, btnExamePainel, btnSalvarExame, btnEditar;
    private JProgressBar pb;

    public TelaProfessor(Professor professor, Classe classe, Disciplina disciplina) {
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;
        this.trimestre = new Trimestre();

        setSize(900, 570);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font rw = new Font("Rockwell", Font.BOLD, 12);

        conectar();

        // Logo e cabeçalho
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        lblLogo = new JLabel(icon);
        lblLogo.setBounds(20, -45, 150, 150);
        add(lblLogo);

        FlatSVGIcon user = new FlatSVGIcon("svg/user.svg", 40, 40);
        JLabel lbuser = new JLabel("Professor "+professor.getNome_Professor() + "  " + professor.getApelido_Professor());
        lbuser.setBounds(190, 12, 300, 50);
        lbuser.setFont(rw);
        lbuser.setIcon(user);
        lbuser.setForeground(Color.white);
        add(lbuser);

        JLabel lbId = new JLabel("" + professor.getId_Professor());
        lbId.setBounds(240, 40, 20, 20);
        lbId.setFont(rw);
        lbId.setForeground(Color.white);
        add(lbId);

        menuEsquerdo = new JPanel(null);
        menuEsquerdo.setBounds(15, 70, 160, 410);

        FlatSVGIcon iconMenu = new FlatSVGIcon("svg/menu.svg", 40, 40);
        JLabel lbIconMenu = new JLabel(iconMenu);
        lbIconMenu.setBounds(50, 10, 40, 40);
        menuEsquerdo.add(lbIconMenu);

        FlatSVGIcon iconNotas = new FlatSVGIcon("svg/teste.svg", 40, 40);
        btnNotas = new JButton("Notas");
        btnNotas.setIcon(iconNotas);
        btnNotas.setBounds(10, 60, 130, 50);
        btnNotas.setFont(rw);
        btnNotas.setForeground(Color.white);
        menuEsquerdo.add(btnNotas);
        add(menuEsquerdo);
        
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

        menuInferior = new JPanel(null);
        menuInferior.setBackground(new Color(38, 38, 38));
        menuInferior.setBounds(15, 500, 850, 30);
        lblTipoUsuario = new JLabel("Menu de Actividades do professor/ES3FI/");
        lblTipoUsuario.setFont(new Font("Rockwell", Font.BOLD, 15));
        lblTipoUsuario.setForeground(Color.white);
        lblTipoUsuario.setBounds(SwingConstants.CENTER, 5, 300, 20);
        menuInferior.add(lblTipoUsuario);
        add(menuInferior);

        painelConteudo = new JPanel(null);
        painelConteudo.setBounds(190, 70, 670, 410);
        painelConteudo.setBackground(new Color(38, 38, 38));
        add(painelConteudo);

        btnNotas.addActionListener(e -> mostrarPainelNotas(rw));
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + e.getMessage());
        }
    }
     public void mostrarPainelEdit(){
         Font rw = new Font("Rockwell", Font.BOLD, 15); 
         
     painelConteudo.removeAll();
     painelConteudo.revalidate();
     painelConteudo.repaint();
    
    lbNome = new JLabel("Nome");
    lbNome.setBounds(20,50, 70, 30);
    lbNome.setFont(rw);
    lbNome.setForeground(Color.white);
    painelConteudo.add(lbNome);
    
    txtNome = new JTextField(""+professor.getNome_Professor());
    txtNome.setBounds(100, 50, 180, 30);
    txtNome.setForeground(Color.white);
    painelConteudo.add(txtNome);
    
    lbApelido = new JLabel("Apelido");
    lbApelido.setBounds(20, 90 , 70, 30);
    lbApelido.setFont(rw);
    lbApelido.setForeground(Color.white);
    painelConteudo.add(lbApelido);
     
    txtApelido = new JTextField(""+professor.getApelido_Professor());
    txtApelido.setBounds(100, 90, 180, 30);
    txtApelido.setForeground(Color.white);
    painelConteudo.add(txtApelido);
    
    lbDn = new JLabel("Data de Nascimento");
    lbDn.setBounds(20, 130, 150, 30);
    lbDn.setForeground(Color.white);
    lbDn.setFont(rw);
    painelConteudo.add(lbDn);
    
    txtDn = new JTextField(""+professor.getDataNasc());
    txtDn.setBounds(180, 130, 100, 30);
    txtDn.setForeground(Color.white);
    painelConteudo.add(txtDn);
    
    lbDoc = new JLabel("Bilhte de identidade");
     lbDoc.setBounds(290, 50, 120, 30);
     lbDoc.setForeground(Color.white);
     lbDoc.setFont(rw);
     painelConteudo.add(lbDoc);
     
     txtDoc = new JTextField(""+professor.getNrBiProf());
     txtDoc.setBounds(420, 50, 80, 30);
     txtDoc.setForeground(Color.white);
     painelConteudo.add(txtDoc);
     
     lbSenha = new JLabel("Palavra-passe");
     lbSenha.setBounds(290, 90, 120, 30);
     lbSenha.setForeground(Color.white);
      lbSenha.setFont(rw);
      painelConteudo.add(lbSenha);
      
      txtSenha = new JTextField(""+professor.getSenha_Professor());
      txtSenha.setBounds(420, 90, 80, 30);
      txtSenha.setForeground(Color.white);
      painelConteudo.add(txtSenha);
      
     FlatSVGIcon icon1 = new FlatSVGIcon("svg/atualizar.svg");
     btnAtualizarDados = new JButton();
     btnAtualizarDados.setBounds(10, 200, 80, 35);
     btnAtualizarDados.setIcon(icon1);
     btnAtualizarDados.addActionListener(e-> atualizarDadosProf());
     painelConteudo.add(btnAtualizarDados);
     
     painelConteudo.revalidate();
    painelConteudo.repaint();
     
     }

     private void mostrarPainelNotas(Font rw) {
    painelConteudo.removeAll();
    painelConteudo.revalidate();
    painelConteudo.repaint();

    cbTrimestre = new JComboBox<>();
    cbTrimestre.addItem("1 - Trimestre 1");
    cbTrimestre.addItem("2 - Trimestre 2");
    cbTrimestre.addItem("3 - Trimestre 3");
    cbTrimestre.setBounds(10, 10, 150, 25);
    cbTrimestre.setFont(rw);
    painelConteudo.add(cbTrimestre);

    btnSalvar = new JButton("Guardar");
    btnSalvar.setBounds(10, 360, 100, 30);
    btnSalvar.setFont(rw);
    btnSalvar.setForeground(Color.white);
    painelConteudo.add(btnSalvar);

    String[] colunas = {"ID", "Nome", "Teste 1", "Teste 2", "Teste 3", "Media", "Situacao"};
    linhas = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column >= 2 && column <= 4;
        }
    };

    tabela = new JTable(linhas);
    tabela.setFont(new Font("Rockwell", Font.BOLD, 12));
    tabela.setForeground(Color.white);
    JScrollPane scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 50, 640, 290);
    scroll.getViewport().setBackground(new Color(38, 38, 38)); 
    scroll.setBackground(new Color(38, 38, 38)); 
    painelConteudo.add(scroll);

    mostrarNotas(getTrimestreSelecionado());

    cbTrimestre.addActionListener(e -> mostrarNotas(getTrimestreSelecionado()));
    btnSalvar.addActionListener(e -> guardarNotas(getTrimestreSelecionado()));

    painelConteudo.revalidate();
    painelConteudo.repaint();
}

     private void atualizarDadosProf(){
    try(Connection conn = conexao.conectar()){
        String sqlAP ="update professor set nome_prof=?, apelido_prof=?, anonas_prof=?, nr_bi_prof=?, senha=? where id_prof=?";
        PreparedStatement ps = conn.prepareStatement(sqlAP);
        professor.setNome_Professor(txtNome.getText());
        professor.setApelido_Professor(txtApelido.getText());
        professor.setDataNasc(txtDn.getText());
        professor.setNrBiProf(txtDoc.getText());
        professor.setSenha_Professor(txtSenha.getText());
        ps.setString(1,professor.getNome_Professor());
        ps.setString(2, professor.getApelido_Professor());
        ps.setString(3, professor.getDataNasc());
        ps.setString(4, professor.getNrBiProf());
        ps.setString(5, professor.getSenha_Professor());
        ps.setInt(6, professor.getId_Professor());
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Dados Atualizados com sucesso!");
    }catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }

    private int getTrimestreSelecionado() {
        if (cbTrimestre.getSelectedItem() == null) return 1;
        return Integer.parseInt(cbTrimestre.getSelectedItem().toString().split(" - ")[0]);
    }

    private void mostrarNotas(int trimestre) {
        linhas.setRowCount(0);
        try {
            String sql = """
             select a.id_aluno, a.nome_aluno, a.apelido_aluno, n.n1, n.n2, n.n3, n.situacao
             from aluno a
             left join nota n ON a.id_aluno = n.id_aluno
             and n.id_semestre = ? and n.id_prof = ? and n.id_disciplina = ? and n.id_classe = ?
             where a.id_classe = ?
             order BY a.nome_aluno
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, trimestre);
            ps.setInt(2, professor.getId_Professor());
            ps.setInt(3, disciplina.getId_disciplina());
            ps.setInt(4, classe.getId_Classe());
            ps.setInt(5, classe.getId_Classe());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_aluno");
                String nome = rs.getString("nome_aluno") + " " + rs.getString("apelido_aluno");
                double n1 = rs.getDouble("n1");
                double n2 = rs.getDouble("n2");
                double n3 = rs.getDouble("n3");
                double media = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
                String situacao = (media < 10) ? "Péssimo" : (media <= 14 ? "Bom" : "Excelente");
                linhas.addRow(new Object[]{id, nome, n1, n2, n3, media, situacao});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar notas: " + e.getMessage());
        }
    }

    private void guardarNotas(int trimestre) {
        try {
    String sql = """
     insert into nota (id_aluno, id_semestre, id_prof, id_disciplina, id_classe, n1, n2, n3, situacao)
     values (?, ?, ?, ?, ?, ?, ?, ?, ?)
     on duplicate key update n1=VALUES(n1), n2=VALUES(n2), n3=VALUES(n3), situacao=VALUES(situacao)
    """;
    PreparedStatement ps = conn.prepareStatement(sql);
    for (int i = 0; i < linhas.getRowCount(); i++) {
        int idAluno = (int) linhas.getValueAt(i, 0);
        double n1 = parseDoubleOrZero(linhas.getValueAt(i, 2));
        double n2 = parseDoubleOrZero(linhas.getValueAt(i, 3));
        double n3 = parseDoubleOrZero(linhas.getValueAt(i, 4));
        double media = n1 * 0.30 + n2 * 0.30 + n3 * 0.40;
        String situacao = (media < 10) ? "Péssimo" : (media <= 14 ? "Bom" : "Excelente");

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

        linhas.setValueAt(media, i, 5);
        linhas.setValueAt(situacao, i, 6);
            }
            JOptionPane.showMessageDialog(null, "Notas inseridas com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    } 
   public void mostrarPainelExames() {
    painelConteudo.removeAll();
    painelConteudo.revalidate();
    painelConteudo.repaint();
    painelConteudo.setLayout(null);

    // 🔹 Modelo igual ao original
    modelo = new DefaultTableModel(new String[]{"ID", "Aluno", "Exame", "Situação"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 2;
        }
    };

    tabelaExame = new JTable(modelo);
    JScrollPane scroll = new JScrollPane(tabelaExame);
    scroll.setBounds(10, 50, 640, 290);
    painelConteudo.add(scroll);

    btnSalvarExame = new JButton("Guardar");
    btnSalvarExame.setBounds(650, 390, 120, 35);
    btnSalvarExame.addActionListener(e -> salvarExames());
    painelConteudo.add(btnSalvarExame);

    carregarExames();

    painelConteudo.revalidate();
    painelConteudo.repaint();
}




      
     private void carregarExames() {
        modelo.setRowCount(0);
        try {
            String sql = "select a.id_aluno, a.nome_aluno, a.apelido_aluno, e.nota, e.situacao " +
        "from aluno a " +
                         "left JOIN exame e ON a.id_aluno = e.id_aluno " +
                         "and e.id_prof = ? and e.id_disciplina = ? AND e.id_classe = ? " +
                         "where a.id_classe = ? " +
                         "order by a.nome_aluno";
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
                ps.executeUpdate();
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Notas inseridas!");
            carregarExames();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception ignore) {}
        Professor p = new Professor();
        Classe c = new Classe();
        Disciplina d = new Disciplina();
        SwingUtilities.invokeLater(() -> new TelaProfessor(p, c, d).setVisible(true));
    }
}
