/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Aluno.Aluno;
import Classe.Classe;
import Disciplina.Disciplina;
import Professor.Professor;
import Turma.Turma;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.sql.*;
import java.awt.Font;
import Trimestre.*;
import java.awt.Image;
import javax.swing.*;
import com.itextpdf.text.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anderson B. Gemo
 */
public class TelaAdmin2 extends JFrame{
    private Aluno aluno;
    private Professor professor;
    private Classe classe;
    private Disciplina disciplina;
    private Turma turma;
    private JButton btnDadosPriv,btnPautasExame, btnPautas, btnDisciplinas;
    private JPanel painelConteudo, menuEsquerdo;
    private JComboBox<String> cbPessoa;
    private JComboBox cbDisciplina, cbTrimestre, cbClasse;
    private int idAluno, idProf;
    private String senha; private JLabel  lbSenha;
    private JTextField txtId;
    private JScrollPane scroll;
    private JTable tabela;
    private DefaultTableModel linhas;
    private Trimestre trimestre;
    private Connection conn;
    
      public TelaAdmin2(Aluno aluno, Professor professor, Classe classe, Disciplina disciplina, Turma turma, Trimestre  trimestre){
        this.aluno = aluno;
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;
        this.turma = turma;
        this.trimestre = trimestre;
        setSize(900, 570);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Menu do Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         Font rw = new Font("Rockwell", Font.BOLD, 12); 
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        JLabel lblLogo = new JLabel(icon);
        lblLogo.setBounds(20, -45, 150, 150);
        add(lblLogo);
        
        FlatSVGIcon user = new FlatSVGIcon("svg/user.svg", 40, 40);
        JLabel lbuser = new JLabel("ES3FI - ADMINISTRADOR");
        lbuser.setBounds(190, 15, 220, 50);
        lbuser.setFont(rw);
        lbuser.setForeground(Color.white);
        lbuser.setIcon(user);
        add(lbuser);
        
        painelConteudo = new JPanel(null);
        painelConteudo.setBounds(190, 70, 680, 420);
        painelConteudo.setBackground(new Color(38, 38, 38));
        add(painelConteudo);
        
        menuEsquerdo = new JPanel();
        menuEsquerdo.setLayout(null);
        menuEsquerdo.setBounds(15, 70, 160, 410);
        menuEsquerdo.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        btnDadosPriv = new JButton("Senhas");
        btnDadosPriv.setBounds(10, 5, 130,50);
        btnDadosPriv.setForeground(Color.white);
        btnDadosPriv.addActionListener(e-> painelDadosPrivados());
        btnDadosPriv.setFont(new Font("Rockwell", Font.BOLD, 12));
        menuEsquerdo.add(btnDadosPriv);
        
       btnPautas = new JButton("Pauta-Testes");
       btnPautas.setBounds(10, 70, 130, 50);
       btnPautas.setForeground(Color.white);
       btnPautas.setFont(new Font("Rockwell", Font.BOLD, 12));
       btnPautas.addActionListener(e-> painelPautas());
       menuEsquerdo.add(btnPautas);
               
       btnPautasExame = new JButton("Pauta-Exames");
       btnPautasExame.setBounds(10, 135, 130, 50);
       btnPautasExame.setFont(rw);
       btnPautasExame.setForeground(Color.white);
       btnPautasExame.addActionListener(e-> painelPautasExame());
       menuEsquerdo.add(btnPautasExame);
       add(menuEsquerdo);
         
       btnDisciplinas = new JButton("Gerir Disciplinas");
       btnDisciplinas.setBounds(10, 200, 130, 50);
       btnDisciplinas.setFont(rw);
       btnDisciplinas.setForeground(Color.white);
       menuEsquerdo.add(btnDisciplinas);
         add(menuEsquerdo);
      }
        private void conectar() {
                try {
                   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                }
            }
      public void painelDadosPrivados(){
      painelConteudo.removeAll();
      painelConteudo.repaint();
      Font rw = new Font("Rockwell", Font.BOLD, 15);
      JLabel titulo = new JLabel("Acesso a Dados Privados");
      titulo.setBounds(250, 20, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      JLabel lb1 = new JLabel("Verificar Senha");
      lb1.setForeground(Color.white);
      lb1.setFont(rw);
      lb1.setBounds(10, 50, 150, 25);
      painelConteudo.add(lb1);
      
      JLabel lb3 = new JLabel("Digite o ID: ");
      lb3.setFont(rw);
      lb3.setForeground(Color.white);
      lb3.setBounds(10, 80, 100, 25);
      painelConteudo.add(lb3);
      
      txtId = new JTextField();
      txtId.setBounds(130, 80, 100, 30);
      painelConteudo.add(txtId);
     
      lbSenha = new JLabel();
      lbSenha.setFont(rw);
      lbSenha.setForeground(Color.white);
      lbSenha.setBounds(10, 120, 100, 25);
      painelConteudo.add(lbSenha); 
      
      JButton btnAluno = new JButton("Aluno");
      btnAluno.setBounds(250, 80, 100, 30);
      btnAluno.setFont(rw);
      btnAluno.setForeground(Color.white);
      btnAluno.addActionListener(e-> verSenhaAluno());
      painelConteudo.add(btnAluno);
      
      JButton btnProf = new JButton("Professor");
      btnProf.setBounds(360, 80, 120, 30);
      btnProf.setFont(rw);
      btnProf.addActionListener(e-> verSenhaprof());
      btnProf.setForeground(Color.white);
      painelConteudo.add(btnProf);
      }
      
      public void painelPautas(){
          painelConteudo.removeAll();
          painelConteudo.repaint();
      Font rw = new Font("Arial", Font.BOLD, 12);
      JLabel titulo = new JLabel("Pautas/Resultados");
      titulo.setBounds(250, 0, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      cbTrimestre = new JComboBox();
      cbTrimestre.setFont(rw);
      cbTrimestre.setForeground(Color.white);
      cbTrimestre.setBounds(10, 20, 100, 30);
      painelConteudo.add(cbTrimestre);
      
      cbDisciplina = new JComboBox();
      cbDisciplina.setForeground(Color.white);
      cbDisciplina.setFont(rw);
      cbDisciplina.setBounds(130, 20, 120, 30);
      painelConteudo.add(cbDisciplina);
      
      JButton btnPDF = new JButton("Imprimir Pauta");
      btnPDF.setFont(rw);
      btnPDF.setForeground(Color.white);
      btnPDF.setBounds(400, 20, 200, 30);
      btnPDF.addActionListener(e-> pdfPautaTestes(tabela, "pautas_disciplina.pdf"));
      painelConteudo.add(btnPDF);
      
    String Colunas[] = { "ID","Nome","Teste 1", "Teste 2", "Teste 3","Media","Situacao"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 50, 630, 350);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    painelConteudo.add(scroll);
    cbTrimestre.addActionListener(e -> carregarPautas());
      cbDisciplina.addActionListener(e -> carregarPautas());
      verDisciplinas();
      verTrimestre();
      }
      public void carregarPautas() {
    Trimestre trimestre = (Trimestre) cbTrimestre.getSelectedItem();
    Disciplina disciplina = (Disciplina) cbDisciplina.getSelectedItem();
    if (trimestre == null || disciplina == null) return;
    linhas.setRowCount(0);
    String sql = """
    Select a.id_aluno,
           Concat(a.nome_aluno, ' ', a.apelido_aluno) as nome_completo,
           n.n1, n.n2, n.n3,
           round((n.n1 + n.n2 + n.n3)/3, 2) AS media,
           n.situacao
    from nota n
    JOIN aluno a ON a.id_aluno = n.id_aluno
    where n.id_disciplina = ? and n.id_semestre = ?
      """;

    conectar();
    try {
         PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, disciplina.getId_disciplina());
        ps.setInt(2, trimestre.getId_Semstre());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             Object[] row = {
             rs.getInt("id_aluno"),
             rs.getString("nome_completo"),
             rs.getDouble("n1"),
             rs.getDouble("n2"),
             rs.getDouble("n3"),
             rs.getDouble("media"),
             rs.getString("situacao")
             };
         linhas.addRow(row);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
      public void painelPautasExame(){
       painelConteudo.removeAll();
       painelConteudo.repaint();
      Font rw = new Font("Arial", Font.BOLD, 12);
      JLabel titulo = new JLabel("Pautas/Resultados");
      titulo.setBounds(250, 0, 250, 25);
      titulo.setForeground(Color.white);
      titulo.setFont(rw);
      painelConteudo.add(titulo);
      
      cbClasse = new JComboBox();
      cbClasse.setForeground(Color.white);
      cbClasse.setFont(rw);
      cbClasse.setBounds(10, 20, 100, 30);
      cbClasse.addActionListener(e -> carregarPautasExame());
      painelConteudo.add(cbClasse);
      
      JButton btnPDF = new JButton("Imprimir Pauta");
      btnPDF.setFont(rw);
      btnPDF.setForeground(Color.white);
      btnPDF.setBounds(400, 20, 200, 30);
      btnPDF.addActionListener(e-> pdfPautaExames(tabela, "pautas_exames.pdf"));
      painelConteudo.add(btnPDF);
          
    String Colunas[] = {"Nome","Portugues", "Matematica", "Biologia","Fisica","Ingles","Media","Situacao"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 50, 630, 350);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    painelConteudo.add(scroll);
    verClasses();
      
      }
      private void verSenhaAluno(){
          try(Connection conn =  conexao.conectar()) {
              idAluno = Integer.parseInt(txtId.getText());
              String sql = "select senha_aluno from aluno where id_aluno=?";
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idAluno);
             ResultSet rs=  ps.executeQuery();
             while(rs.next()){
              senha = rs.getString("senha_aluno");
              lbSenha.setText(senha);
             }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
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
       public void pdfPautaTestes(JTable tabela, String caminho) {
        Document documento = new Document(PageSize.A4.rotate()); 
         try {
        PdfWriter.getInstance(documento, new FileOutputStream(caminho));
        documento.open();
       com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Resultados das Avaliacoes | "+cbDisciplina.getSelectedItem(), tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(Chunk.NEWLINE);
        
         com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        logo.scaleToFit(150, 150);
        logo.setAlignment(Element.ALIGN_CENTER);
         documento.add(logo);
        
         Paragraph titulo1 = new Paragraph("Classe: "+cbTrimestre.getSelectedItem(), tituloFont);
         documento.add(titulo1);
         documento.add(Chunk.NEWLINE);
         Paragraph titulo2 = new Paragraph("Disciplina: "+cbDisciplina.getSelectedItem(), tituloFont);
         documento.add(titulo2);
         documento.add(Chunk.NEWLINE);
  
        classe.getNome_Classe();
        PdfPTable pdfTabela = new PdfPTable(tabela.getColumnCount());
        pdfTabela.setWidthPercentage(100);

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            PdfPCell celula = new PdfPCell(new Phrase(tabela.getColumnName(i)));
            celula.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTabela.addCell(celula);
        }
        for (int linha = 0; linha < tabela.getRowCount(); linha++) {
            for (int coluna = 0; coluna < tabela.getColumnCount(); coluna++) {
                Object valor = tabela.getValueAt(linha, coluna);
                pdfTabela.addCell(valor != null ? valor.toString() : "");
            }
        }
        documento.add(pdfTabela);
        JOptionPane.showMessageDialog(null, "Pauta Criada!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar PDF: " + e.getMessage());
    } finally {
        documento.close();
    }
      }
       public void pdfPautaExames(JTable tabela, String caminho) {
    Document documento = new Document(PageSize.A4.rotate());
    try {
        PdfWriter.getInstance(documento, new FileOutputStream(caminho));
        documento.open();

        com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        com.itextpdf.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

        Paragraph titulo = new Paragraph("Pautas de Exame Final", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(Chunk.NEWLINE);

        com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(
            "C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        logo.scaleToFit(120, 120);
        logo.setAlignment(Element.ALIGN_CENTER);
        documento.add(logo);
        documento.add(Chunk.NEWLINE);

        Paragraph infoClasse = new Paragraph("Classe: " + cbClasse.getSelectedItem(), infoFont);
        infoClasse.setAlignment(Element.ALIGN_LEFT);
        documento.add(infoClasse);
        documento.add(Chunk.NEWLINE);

        PdfPTable pdfTabela = new PdfPTable(tabela.getColumnCount());
        pdfTabela.setWidthPercentage(100);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            String nomeColuna = tabela.getColumnName(i);
            if (nomeColuna.equalsIgnoreCase("apelido_aluno")) continue;
            if (nomeColuna.equalsIgnoreCase("nome_aluno")) nomeColuna = "Nome Completo";

            PdfPCell celula = new PdfPCell(new Phrase(nomeColuna, infoFont));
            celula.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTabela.addCell(celula);
        }

        for (int linha = 0; linha < tabela.getRowCount(); linha++) {
            String nome = "";
            String apelido = "";
            for (int coluna = 0; coluna < tabela.getColumnCount(); coluna++) {
                String nomeColuna = tabela.getColumnName(coluna);
                if (nomeColuna.equalsIgnoreCase("nome_aluno")) {
                    nome = tabela.getValueAt(linha, coluna).toString();
                } else if (nomeColuna.equalsIgnoreCase("apelido_aluno")) {
                    apelido = tabela.getValueAt(linha, coluna) != null ? tabela.getValueAt(linha, coluna).toString() : "";
                }
            }
            //Juntar nome e apelido na mesma linha/celula
        for (int coluna = 0; coluna < tabela.getColumnCount(); coluna++) {
        String nomeColuna = tabela.getColumnName(coluna);
        if (nomeColuna.equalsIgnoreCase("nome_aluno")) {
            String nomeCompleto = nome + (apelido.isEmpty() ? "" : " " + apelido + "");
            PdfPCell celula = new PdfPCell(new Phrase(nomeCompleto, infoFont));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTabela.addCell(celula);
        } 
        else if (!nomeColuna.equalsIgnoreCase("apelido_aluno")) {
            Object valor = tabela.getValueAt(linha, coluna);
            PdfPCell celula = new PdfPCell(new Phrase(valor != null ? valor.toString() : "", infoFont));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTabela.addCell(celula);
                }
            }
        }

        documento.add(pdfTabela);
        JOptionPane.showMessageDialog(null, "Pauta de Exames feita!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        documento.close();
    }
}
      private void verSenhaprof(){
          try(Connection conn =  conexao.conectar()) {
              idProf = Integer.parseInt(txtId.getText());
              String sql = "select senha from professor where id_prof=?";
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idProf);
             ResultSet rs=  ps.executeQuery();
             while(rs.next()){
              senha = rs.getString("senha");
              lbSenha.setText(senha);
             }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
          }
      }
      private void verTrimestre() {
       try (Connection conn = conexao.conectar()) {
        String sql = "Select id_trimestre, nome_semestre from trimestre";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Trimestre  trimestre = new Trimestre();
            trimestre.setId_Semstre(rs.getInt("id_trimestre"));
            trimestre.setNome_Semestre(rs.getString("nome_semestre"));
            cbTrimestre.addItem(trimestre);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
         } 
      
      private void verDisciplinas() {
     try(Connection conn = conexao.conectar()) {
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
      private void carregarPautasExame() {
    Classe classe = (Classe) cbClasse.getSelectedItem();
    if (classe == null) return;

    linhas.setRowCount(0);

    String sql = """
        SELECT 
            a.id_aluno,
            CONCAT(a.nome_aluno, ' ', a.apelido_aluno) AS nome_completo,
            MAX(CASE WHEN d.nome_disciplina LIKE 'Portugues%' THEN e.nota END) AS portugues,
            MAX(CASE WHEN d.nome_disciplina LIKE 'Matematica%' THEN e.nota END) AS matematica,
            MAX(CASE WHEN d.nome_disciplina LIKE 'Biologia%' THEN e.nota END) AS biologia,
            MAX(CASE WHEN d.nome_disciplina LIKE 'Fisica%' THEN e.nota END) AS fisica,
            MAX(CASE WHEN d.nome_disciplina LIKE 'Ingles%' THEN e.nota END) AS ingles,
            ROUND((
                COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Portugues%' THEN e.nota END),0) +
                COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Matematica%' THEN e.nota END),0) +
                COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Biologia%' THEN e.nota END),0) +
                COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Fisica%' THEN e.nota END),0) +
                COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Ingles%' THEN e.nota END),0)
            ) / 5, 2) AS media_final,
            CASE 
                WHEN ROUND((
                    COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Portugues%' THEN e.nota END),0) +
                    COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Matematica%' THEN e.nota END),0) +
                    COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Biologia%' THEN e.nota END),0) +
                    COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Fisica%' THEN e.nota END),0) +
                    COALESCE(MAX(CASE WHEN d.nome_disciplina LIKE 'Ingles%' THEN e.nota END),0)
                ) / 5, 2) >= 10 THEN 'Aprovado'
                ELSE 'Reprovado'
            END AS situacao
        FROM exame e
        JOIN aluno a ON e.id_aluno = a.id_aluno
        JOIN disciplina d ON e.id_disciplina = d.id_disciplina
        WHERE e.id_classe = ?
        GROUP BY a.id_aluno, nome_completo
        ORDER BY nome_completo;
    """;

    try (Connection conn = conexao.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, classe.getId_Classe());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nome = rs.getString("nome_completo");
            double port = rs.getDouble("portugues");
            double mat = rs.getDouble("matematica");
            double bio = rs.getDouble("biologia");
            double fis = rs.getDouble("fisica");
            double ing = rs.getDouble("ingles");
            double media = rs.getDouble("media_final");
             String situacao;
            if (port < 10 || mat < 10 || bio < 10 || fis < 10 || ing < 10) {
                situacao = "Reprovado";
            } else {
                situacao = "Aprovado";
            }
            linhas.addRow(new Object[]{
                nome, port, mat, bio, fis, ing, media, situacao
            });
        }
        tabela.setDefaultEditor(Object.class, null);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, 
            "erro:"+ e.getMessage());
    }
      }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception e) {
        }
        Aluno aluno = new Aluno();
        Professor professor = new Professor();
        Classe classe = new Classe();
        Disciplina disciplina = new Disciplina();
        Turma turma = new Turma();
        Trimestre t = new Trimestre();
        SwingUtilities.invokeLater(()-> new TelaAdmin2(aluno, professor, classe, disciplina, turma,t).setVisible(true));
    } 
}
