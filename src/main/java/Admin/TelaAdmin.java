/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Aluno.*;
import Classe.Classe;
import Professor.*;
import java.awt.*;
import Disciplina.*;
import Trimestre.Trimestre;
import java.util.*;
import Turma.Turma;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import javax.swing.table.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme ;
import com.formdev.flatlaf.extras.*;
import com.itextpdf.text.Document;
import com.itextpdf.testutils.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.Resultset;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Anderson B. Gemo
 */
public class TelaAdmin extends JFrame{
      private JLabel lblLogo, lblTipoUsuario, lb1, lb2;
    private JPanel menuEsquerdo, menuInferior, painelConteudo;
    private JButton btnNotas, btnExame, btnEditDisciplina, btnNovoA, btnSair;
    private JTable tabela;
    private DefaultTableModel linhas;
    private JComboBox <Disciplina> cbDisciplina, disciplinaEscolhida;
    private JComboBox <Classe>  classeEscolhida;
    private JComboBox cbSexo;
    private JButton btnVerProf, btnGuardarAluno, btnVoltar, btnAtualizar, btnNovoProf, btnGuardarProf;
    private int linhaTabela, id_Professor, Id_Classe;
    private JTextField txtNome, txtApelido, txtDn, txtDisc, txtSenha;
    private JTextField txtNomeAluno, txtApelidoAluno, txtDocAluno, txtDnAluno;
    private JTextField txtNomeProf, txtIdAlunoRM, txtApelidoProf, txtDocProf, txtDnProf;
    private JPasswordField txtSenhaAluno,txtSenhaProf;
    private JLabel lbNomeA, lbApelidoA, lbDocA, lbDnA, lbSenhaA;
    private JScrollPane scroll;
    private int id_disc,Id_turma,id_classe;
    private Connection conn; 
    private JComboBox <Classe> cbClasse;
    private JComboBox<Turma> cbTurmas, turmaEscolhida;
    private JButton btnAtualizar2;
    private Aluno aluno;
    private Disciplina disciplina;
    private Professor professor;
    private Turma turma;
    private Classe classe, classeEscolhida2;
    private JTextField txtNome2, txtApelido2, txtDn2;
    private int id_Aluno;
    
    public TelaAdmin(Aluno aluno, Professor professor, Classe classe, Disciplina disciplina, Turma turma){
        this.aluno = aluno;
        this.professor = professor;
        this.classe = classe;
        this.disciplina = disciplina;
        this.turma = turma;
        setSize(900, 570);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Menu do Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font rw = new Font("Rockwell", Font.BOLD, 12); 
        ImageIcon icon = new ImageIcon("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
        lblLogo = new JLabel(icon);
        lblLogo.setBounds(20, -45, 150, 150);
        add(lblLogo);
        
        FlatSVGIcon user = new FlatSVGIcon("svg/user.svg", 40, 40);
        JLabel lbuser = new JLabel("ES3FI - ADMINISTRADOR");
        lbuser.setBounds(190, 15, 220, 50);
        lbuser.setFont(rw);
        lbuser.setForeground(Color.white);
        lbuser.setIcon(user);
        add(lbuser);
        
        Trimestre t = new Trimestre();
        TelaAdmin2 ta2 = new TelaAdmin2(aluno, professor, classe, disciplina, turma,t);
        FlatSVGIcon proximo = new FlatSVGIcon("svg/proximo.svg", 25, 25);
        JButton novaTela = new JButton();
        novaTela.setBounds(800, 30, 50, 30);
        novaTela.setIcon(proximo);
        novaTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta2.setVisible(true);
                dispose();
            }
        });
        add(novaTela);
        
        
        painelConteudo = new JPanel(null);
        painelConteudo.setBounds(190, 70, 680, 420);
        painelConteudo.setBackground(new Color(38, 38, 38));
        add(painelConteudo);
        
        menuEsquerdo = new JPanel();
        menuEsquerdo.setLayout(null);
        menuEsquerdo.setBounds(15, 70, 160, 410);
        menuEsquerdo.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        JButton btnRenova = new JButton("Renovar Matricula");
        btnRenova.setBounds(10, 5, 130,50);
        btnRenova.setForeground(Color.white);
        btnRenova.addActionListener(e-> painelRenovaMatricula());
        btnRenova.setFont(new Font("Rockwell", Font.BOLD, 12));
        menuEsquerdo.add(btnRenova);
        
       FlatSVGIcon iconNotas  = new FlatSVGIcon("svg/student.svg", 40, 40);
       btnNotas = new JButton("Alunos");
       btnNotas.setIcon(iconNotas);
       btnNotas.setBounds(10, 70, 130, 50);
       btnNotas.setForeground(Color.white);
       btnNotas.setFont(new Font("Rockwell", Font.BOLD, 12));
       btnNotas.addActionListener(e-> painelAlunos());
       menuEsquerdo.add(btnNotas);
     
        
      FlatSVGIcon iconExame  = new FlatSVGIcon("svg/teachings.svg", 40, 40);
      btnExame = new JButton("Professores");
      btnExame.setFont(new Font("Rockwell", Font.BOLD, 10));
      btnExame.setForeground(Color.white);
      btnExame.setIcon(iconExame);
      //  btnExame.setBackground(Color.white);
      btnExame.setBounds(10, 130, 130, 50);
      menuEsquerdo.add(btnExame);
      btnExame.addActionListener(e-> painelProfessor());
      add(menuEsquerdo);
      
      FlatSVGIcon iconDisc = new FlatSVGIcon("svg/stack-of-books.svg", 40, 40);
      btnEditDisciplina = new JButton("Disciplinas");
      btnEditDisciplina.setFont(new Font("Rockwell", Font.BOLD, 10));
      btnEditDisciplina.setBounds(10, 200,130, 50);
      btnEditDisciplina.setForeground(Color.white);
      btnEditDisciplina.setIcon(iconDisc);
      btnEditDisciplina.addActionListener(e-> painelDisciplina());
      menuEsquerdo.add(btnEditDisciplina);
      
      FlatSVGIcon iconHist = new FlatSVGIcon("svg/newstudent.svg", 40, 40);
      btnNovoA = new JButton("Novo A.");
      btnNovoA.setBounds(10, 270, 130, 50);
      btnNovoA.setFont(new Font("Rockwell", Font.BOLD, 12));
      btnNovoA.setForeground(Color.white);
      btnNovoA.setIcon(iconHist);
      btnNovoA.addActionListener(e-> painelNovoAluno());
      menuEsquerdo.add(btnNovoA);
      
      FlatSVGIcon iconSair = new FlatSVGIcon("svg/professora.svg", 40, 40);
      btnNovoProf = new JButton("Novo P.");
      btnNovoProf.setBounds(10,340, 130, 50);
      btnNovoProf.setFont(new Font("Rockwell", Font.BOLD, 12));
      btnNovoProf.setForeground(Color.white);
      btnNovoProf.setIcon(iconSair);
      btnNovoProf.addActionListener(e-> painelNovoProfessor());
      menuEsquerdo.add(btnNovoProf);
      
      menuInferior = new JPanel();
      menuInferior.setBackground(Color.red);
      menuInferior.setBounds(15, 490, 850, 30);
      menuInferior.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
      menuInferior.setLayout(null);
      menuInferior.setBackground(new Color(38, 38, 38));
      lblTipoUsuario = new JLabel("Menu de Actividades do Admin/ES3FI/ABG");
      lblTipoUsuario.setFont(new Font("Rockwell", Font.BOLD, 15));
      lblTipoUsuario.setForeground(Color.white);
      lblTipoUsuario.setBounds(SwingConstants.CENTER, 5, 390, 20);
      menuInferior.add(lblTipoUsuario);
      add(menuInferior);
    }
    
    private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/novo", "root", "2706");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
        }
    }
    public void painelRenovaMatricula(){
    painelConteudo.removeAll();
    painelConteudo.repaint();
    Font rw  = new Font("rockwell", Font.BOLD, 15);
    JLabel titulo = new JLabel("RENOVACAO DE MATRICULA");
    titulo.setBounds(250, 20, 250, 25);
    titulo.setForeground(Color.white);
    titulo.setFont(rw);
    painelConteudo.add(titulo);
    JLabel info = new JLabel("Digite o ID do Aluno");
    info.setFont(rw);
    info.setForeground(Color.white);
    info.setBounds(20,55, 150, 30);
    painelConteudo.add(info);
    
    txtIdAlunoRM = new JTextField();
    txtIdAlunoRM.setBounds(20, 90, 100, 25);
    painelConteudo.add(txtIdAlunoRM);
    
    JButton btnVer = new JButton("Verificar");
    btnVer.setFont(rw);
    btnVer.addActionListener(e-> verDadosAluno());
    btnVer.setBounds(20, 120, 100, 25);
    btnVer.setForeground(Color.white);
    painelConteudo.add(btnVer);
    
    lb1= new JLabel();
    lb1.setForeground(Color.white);
    lb1.setBounds(300, 50, 200, 30);
    lb1.setFont(rw);
    painelConteudo.add(lb1);
            
    lb2 = new JLabel();
    lb2.setBounds(300, 70, 200, 30);
    lb2.setForeground(Color.white);
    lb2.setFont(rw);
    painelConteudo.add(lb2);
    
    cbClasse = new JComboBox<>();
    cbClasse.setBounds(20, 150, 100, 30);
    painelConteudo.add(cbClasse);
    
    cbTurmas = new JComboBox<>();
    cbTurmas.setBounds(140, 150, 100, 30);
    painelConteudo.add(cbTurmas);
    verClasses();
    mostrarTurmas();
    
    JButton btnAtualizarMatricula = new JButton("Renovar");
    btnAtualizarMatricula.setFont(rw);
    btnAtualizarMatricula.setBounds(20, 190, 100, 30);
    btnAtualizarMatricula.setForeground(Color.white);
    btnAtualizarMatricula.addActionListener(e-> renovaMatricula());
    painelConteudo.add(btnAtualizarMatricula);
    
    }
    private void verDadosAluno(){
    conectar();
        try {  
        aluno.setId(Integer.parseInt(txtIdAlunoRM.getText()));
        String sql = "select * from aluno where id_aluno=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, aluno.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
         lb1.setText(rs.getString("nome_aluno")+" "+rs.getString("apelido_aluno"));   
        lb2.setText("Nivel de Classe: "+rs.getInt("id_classe"));
        }    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void renovaMatricula(){
    conectar();
    try{
         Turma turmaEscolhida = (Turma) cbTurmas.getSelectedItem();
         Id_turma = turmaEscolhida.getId_Turma();
         Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
         Id_Classe = classeEscolhida.getId_Classe();
    String Sql = "update aluno set id_classe=?, id_turma=? where id_aluno=?";
    PreparedStatement ps = conn.prepareStatement(Sql);
    ps.setInt(1, Id_Classe);
    ps.setInt(2,Id_turma);
    ps.setInt(3, aluno.getId());
    ps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Renovacao feita com sucesso");
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    
    public void reciboMatricula(){
    Document reciboAluno = new Document();
    String nomePDF ="Recibo matricula.pdf";
        try {
            
            LocalDateTime horaMatricula = LocalDateTime.now();
            DateTimeFormatter tipoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataMatricula = horaMatricula.format(tipoData);
            
            conectar();
            Classe classeEscolhida = (Classe)  cbClasse.getSelectedItem();
            int idClasse = classeEscolhida.getId_Classe();
            String sql =" insert into matricula(id_aluno, id_classe,data_Matricula) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setInt(2, idClasse);
            ps.setString(3, dataMatricula);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Matricula Registrada com sucesso!");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    try{
    PdfWriter writer = PdfWriter.getInstance(reciboAluno, new FileOutputStream(nomePDF));
    reciboAluno.open();
     com.itextpdf.text.Font titulo = FontFactory.getFont(FontFactory.COURIER_BOLD, 16);
     
     Paragraph p1 = new Paragraph("RECIBO DE MATRICULA DO ALUNO", titulo);
     
     p1.setAlignment(Element.ALIGN_CENTER);
     reciboAluno.add(p1);
     
     Paragraph p2 = new Paragraph("");
     reciboAluno.add(p2);
     
    Image logo = Image.getInstance("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
    logo.scaleToFit(150, 150);
    logo.setAlignment(Element.ALIGN_CENTER);
    reciboAluno.add(logo);
    
    Paragraph p3 = new Paragraph("");
    reciboAluno.add(p3);
    
    com.itextpdf.text.Font par = FontFactory.getFont(FontFactory.COURIER_BOLD, 12);
    Paragraph p4 = new Paragraph("DETALHES DA MATRICULA", titulo);
    p4.setAlignment(Element.ALIGN_CENTER);
    reciboAluno.add(p4);
    
      Paragraph p6 = new Paragraph("");
      Paragraph p600 = new Paragraph("");
    
    Paragraph p5 = new Paragraph("Nome: "+txtNomeAluno.getText()+" "+txtApelidoAluno.getText()+"",par);
    reciboAluno.add(p5);
    
    Paragraph p100 = new Paragraph("");
    Paragraph p7 = new Paragraph("DATA DE NASCIMENTO: "+txtDnAluno.getText()+" ",par);
    reciboAluno.add(p7);
    Paragraph p200 = new Paragraph("");

    Paragraph p8 = new Paragraph("SEXO: "+cbSexo.getSelectedItem()+" ",par);
    reciboAluno.add(p8);
    Paragraph p300 = new Paragraph("");
      Paragraph p9 = new Paragraph("CLASSE: "+cbClasse.getSelectedItem()+" ",par);
    reciboAluno.add(p9);
    Paragraph p400 = new Paragraph("");
      Paragraph p10 = new Paragraph("TURMA: "+cbTurmas.getSelectedItem()+" ",par);
    reciboAluno.add(p10);
    Paragraph p500 = new Paragraph("");
      Paragraph p11 = new Paragraph("CODIGO: "+aluno.getId()+" ",par);
    reciboAluno.add(p11);
    Paragraph p311 = new Paragraph("");
    reciboAluno.add(p311);
     LocalDateTime horaMatricula = LocalDateTime.now();
     DateTimeFormatter tipoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     String dataMatricula = horaMatricula.format(tipoData);
     
     Paragraph p12 = new Paragraph("Documento criado em:"+dataMatricula+"",par);
    reciboAluno.add(p12);
    reciboAluno.close();
    
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
       }
      
    private void painelAlunos(){
         Font rw = new Font("Rockwell", Font.BOLD, 12); 
    painelConteudo.removeAll();
    painelConteudo.repaint();
    String[] Colunas = {"CODIGO", "NOME","APELIDO", "DATA DE NASCIMENTO"};
    linhas = new DefaultTableModel(Colunas,0);
    tabela = new JTable(linhas);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 40, 600, 250);
    tabela.setForeground(Color.white);//tabela.setFont(rw);
    painelConteudo.add(scroll); 
    // verAlunos();
    
    FlatSVGIcon btnIcon  = new FlatSVGIcon("svg/atualizar.svg");
    btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setIcon(btnIcon);
    btnAtualizar.setBounds(10, 370, 130, 35);
    btnAtualizar.setForeground(Color.white);
    btnAtualizar.addActionListener(e-> atualizarDados());
    painelConteudo.add(btnAtualizar);

    txtNome = new JTextField();
    txtNome.setBounds(10, 320, 100, 30);
    txtNome.setForeground(Color.white);
    txtApelido = new JTextField();
    txtApelido.setBounds(120, 320, 100, 30);
    txtApelido.setForeground(Color.white);
    painelConteudo.add(txtNome);
    painelConteudo.add(txtApelido);
    
    txtDn = new JTextField();
    txtDn.setBounds(240, 320, 100, 30);
    txtDn.setForeground(Color.white);
    painelConteudo.add(txtDn);
    
    cbClasse = new JComboBox();
    cbClasse.setBounds(390, 320, 100, 30);
    cbClasse.setFont(rw);
    cbClasse.addActionListener(e-> verAlunosPorClasse());
    painelConteudo.add(cbClasse);
    verClasses();
    tabelaTextField();
    
    FlatSVGIcon icon = new FlatSVGIcon("svg/trash.svg", 30, 30);
    JButton btnApagarAluno = new JButton("Apagar");
    btnApagarAluno.setFont(rw);
    btnApagarAluno.setIcon(icon);
    btnApagarAluno.setForeground(Color.white);
    btnApagarAluno.addActionListener(e-> apagarAluno());
    btnApagarAluno.setBounds(160, 370, 120, 40);
    painelConteudo.add(btnApagarAluno);
    }
    
    private void verAlunosPorClasse(){
    conectar();
        try {
            Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
             int idClasse = classeEscolhida.getId_Classe();
            String sql = "select * from aluno where id_classe=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idClasse);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
                int idAluno = rs.getInt(1);
                String nomeAluno = rs.getString("nome_aluno");
                String apelidoAlnuo =  rs.getString("apelido_aluno");
                String dn = rs.getString("anonas_Aluno");
            linhas.addRow(new Object[]{idAluno, nomeAluno, apelidoAlnuo,dn});
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void apagarAluno(){
    conectar();
        try {
            int linhas = tabela.getSelectedRow();
            if (linhas != -1) {
            id_Aluno = Integer.parseInt(tabela.getValueAt(linhas, 0).toString());
            aluno.setId(id_Aluno);
            }
            String sql = "delete from aluno where id_aluno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Aluno apagado!");
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
     
    private void painelNovoAluno(){
    painelConteudo.removeAll();
    painelConteudo.repaint();
        Font rw = new Font("Rockwell", Font.BOLD, 14);
    JLabel titulo = new JLabel("MATRICULA DE ALUNOS");
    titulo.setFont(rw);
    titulo.setForeground(Color.white);
    titulo.setBounds(260, 20, 250, 30);
    painelConteudo.add(titulo);

    lbNomeA = new JLabel("Nome");
    lbNomeA.setBounds(20,50, 70, 30);
    lbNomeA.setFont(rw);
    lbNomeA.setForeground(Color.white);
    painelConteudo.add(lbNomeA);
    
    txtNomeAluno = new JTextField();
    txtNomeAluno.setBounds(100, 50, 180, 30);
    txtNomeAluno.setForeground(Color.white);
    painelConteudo.add(txtNomeAluno);
    
    lbApelidoA = new JLabel("Apelido");
    lbApelidoA.setBounds(20, 90 , 70, 30);
    lbApelidoA.setFont(rw);
    lbApelidoA.setForeground(Color.white);
    painelConteudo.add(lbApelidoA);
     
    txtApelidoAluno = new JTextField();
    txtApelidoAluno.setBounds(100, 90, 180, 30);
    txtApelidoAluno.setForeground(Color.white);
    painelConteudo.add(txtApelidoAluno);
    
    lbDnA = new JLabel("Data de Nascimento");
    lbDnA.setBounds(20, 130, 150, 30);
    lbDnA.setForeground(Color.white);
    lbDnA.setFont(rw);
    painelConteudo.add(lbDnA);
    
    txtDnAluno = new JTextField();
    txtDnAluno.setBounds(180, 130, 100, 30);
    txtDnAluno.setForeground(Color.white);
    painelConteudo.add(txtDnAluno);
    
    lbDocA = new JLabel("Bilhte de identidade");
    lbDocA.setBounds(290, 50, 150, 30);
    lbDocA.setForeground(Color.white);
    lbDocA.setFont(rw);
    painelConteudo.add(lbDocA);

    txtDocAluno = new JTextField();
    txtDocAluno.setBounds(440, 50, 120, 30);
    txtDocAluno.setForeground(Color.white);
    painelConteudo.add(txtDocAluno);

    lbSenhaA = new JLabel("Palavra-passe");
    lbSenhaA.setBounds(290, 90, 130, 30);
    lbSenhaA.setForeground(Color.white);
    lbSenhaA.setFont(rw);
    painelConteudo.add(lbSenhaA);

    txtSenhaAluno = new JPasswordField();
    txtSenhaAluno.setBounds(440, 90, 120, 30);
    txtSenhaAluno.setForeground(Color.white);
    painelConteudo.add(txtSenhaAluno);

    JLabel infoClasse = new JLabel("classe");
    infoClasse.setFont(rw);
    infoClasse.setForeground(Color.white);
    infoClasse.setBounds(210, 170, 70, 30);
    painelConteudo.add(infoClasse);

    cbClasse = new JComboBox<>();
    cbClasse.setBounds(310, 170, 100, 30);
    cbClasse.setFont(rw);  
    cbClasse.setForeground(Color.white);
    painelConteudo.add(cbClasse);
    verClasses();
      
    JLabel infoSexo = new JLabel("Sexo");
    infoSexo.setFont(rw);
    infoSexo.setForeground(Color.white);
    infoSexo.setBounds(20, 170, 70, 30);
    painelConteudo.add(infoSexo);

    cbSexo = new JComboBox<>();
    cbSexo.addItem("Masculino");
    cbSexo.addItem("Feminino");
    cbSexo.setFont(rw);
    cbSexo.setForeground(Color.white);
    cbSexo.setBounds(60, 170, 120, 30);
    painelConteudo.add(cbSexo);
      
     FlatSVGIcon icon1 = new FlatSVGIcon("svg/save.svg", 40, 30);
     btnGuardarAluno = new JButton("Guardar");
     btnGuardarAluno.setBounds(20, 250, 140, 45);
     btnGuardarAluno.setForeground(Color.white);
     btnGuardarAluno.setIcon(icon1);
     btnGuardarAluno.setFont(rw);
     btnGuardarAluno.addActionListener(e-> guardarAluno());
     painelConteudo.add(btnGuardarAluno);
     
     FlatSVGIcon icon2 = new FlatSVGIcon("svg/pdf.svg", 40 ,30);
     JButton btnRecibopdf = new JButton("Recibo");
     btnRecibopdf.setBounds(180,250, 140, 45);
     btnRecibopdf.addActionListener(e-> reciboMatricula());
     btnRecibopdf.setFont(rw);
     btnRecibopdf.setIcon(icon2);
     painelConteudo.add(btnRecibopdf);
     
     JLabel lbTurma = new JLabel("Turma");
      lbTurma.setBounds(440, 170, 70, 30);
      lbTurma.setForeground(Color.white);
      lbTurma.setFont(rw);
      painelConteudo.add(lbTurma);
      
     cbTurmas = new JComboBox();
     cbTurmas.setBounds(520,170, 100, 30);
     cbTurmas.setFont(rw);
     cbTurmas.setForeground(Color.white);
     painelConteudo.add(cbTurmas);
     mostrarTurmas();
     }
    
    
    public void mostrarTurmas(){
    conectar();
    try{
    String verTurmas = "select id_turma, nome_turma from turma";
    PreparedStatement ps = conn.prepareStatement(verTurmas);
    ResultSet busca = ps.executeQuery();
    while(busca.next()){
    Turma turma = new Turma();
    turma.setId_Turma(busca.getInt("id_turma"));
    turma.setNome_Turma(busca.getString("nome_turma"));
    cbTurmas.addItem(turma);
    }
    }
    catch(Exception erro){
    JOptionPane.showMessageDialog(null, "Erro: "+erro.getMessage());
    }
    }
    
    public void guardarProfessor(){
    professor.setNome_Professor(txtNomeProf.getText());
    professor.setApelido_Professor(txtApelidoProf.getText());
    professor.setDataNasc(txtDnProf.getText());
    professor.setNrBiProf((txtDocProf.getText()));
    professor.setSexo_Prof(cbSexo.getSelectedItem().toString());
    professor.setSenha_Professor(new String(txtSenhaProf.getPassword()));
    Disciplina disciplinaEscolhida = (Disciplina) cbDisciplina.getSelectedItem();
    Classe classeEscolhida  = (Classe) cbClasse.getSelectedItem();
    try(Connection conn = conexao.conectar()){
    String inserirProf = "insert into professor(nome_prof, apelido_prof, nr_bi_prof, anonas_prof, sexo_prof, senha, id_disciplina, id_classe) values (?,?,?,?,?,?,?,?)";
    PreparedStatement ps = conn.prepareStatement(inserirProf);
    ps.setString(1, professor.getNome_Professor());
    ps.setString(2, professor.getApelido_Professor());
    ps.setString(3, professor.getNrBiProf());
    ps.setString(4, professor.getDataNasc());
    ps.setString(5, professor.getSexo_Prof());
    ps.setString(6, professor.getSenha_Professor());
    ps.setInt(7,disciplinaEscolhida.getId_disciplina());
    ps.setInt(8, classeEscolhida.getId_Classe());
    ps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Professor Registrado");
    } catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    
    }
    private void guardarAluno(){
        conectar();
         aluno.setNome(txtNomeAluno.getText()); 
         aluno.setApelido(txtApelidoAluno.getText());
         aluno.setDataNas(txtDnAluno.getText());
         aluno.setSexo(cbSexo.getSelectedItem().toString());
         aluno.setNrBi(txtDocAluno.getText());
         Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
           Id_Classe = classeEscolhida.getId_Classe();
           classe.setId_Classe(Id_Classe);
           Turma turmaEscolhida = (Turma) cbTurmas.getSelectedItem();
           Id_turma = turmaEscolhida.getId_Turma();
           turma.setId_Turma(Id_turma);
           aluno.setSenha(new String(txtSenhaAluno.getPassword()));
   try{
    String sql = "insert into aluno(nome_aluno, apelido_aluno, anonas_aluno, Sexo_aluno, nr_bi_aluno, id_classe, id_turma,senha_aluno) values (?,?,?,?,?,?,?,?)";
    PreparedStatement ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS);
    ps.setString(1, aluno.getNome());
    ps.setString(2, aluno.getApelido());
    ps.setString(3, aluno.getDataNas());
    ps.setString(5, aluno.getNrBi());
    ps.setString(4, aluno.getSexo());
    ps.setInt(6, Id_Classe);
    ps.setInt(7, Id_turma); 
    ps.setString(8,  aluno.getSenha());
    ps.executeUpdate();
    ResultSet rs = ps.getGeneratedKeys();
    while(rs.next()){
        int id_Aluno = rs.getInt(1);
    aluno.setId(id_Aluno);
    }
    JOptionPane.showMessageDialog(null, "Aluno Gravado");
       }
         catch(SQLException erro){
        JOptionPane.showMessageDialog(null, "Erro: "+erro.getMessage());

    }
}
    private void tabelaTextField(){
       tabela.addMouseListener(new MouseAdapter(){
       @Override
       public void mouseClicked(MouseEvent e){
       int linha= tabela.getSelectedRow();
       if(linha != -1){
           id_Aluno = Integer.parseInt(tabela.getValueAt(linha, 0).toString());
           txtNome.setText(tabela.getValueAt(linha, 1).toString());
           txtApelido.setText(tabela.getValueAt(linha, 2).toString());
           txtDn.setText(tabela.getValueAt(linha, 3).toString());
             }
             }
              });
                     }

    public void verAlunos(){
    try(Connection conn = conexao.conectar()){
    String mySql = "select id_aluno, nome_aluno, apelido_aluno, anonas_aluno from aluno";
    PreparedStatement ps = conn.prepareStatement(mySql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    int id_Aluno = rs.getInt("id_aluno");
    String nome = rs.getString("nome_aluno");
    String apelido = rs.getString("apelido_aluno");
    String dn = rs.getString("anonas_aluno");
    linhas.addRow(new Object[] {id_Aluno, nome, apelido, dn});
    } 
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
      }
    } 
    private void atualizarDados(){
           try(Connection conn = conexao.conectar()){
        aluno.setId(id_Aluno);
        aluno.setNome(txtNome.getText());
        aluno.setApelido(txtApelido.getText());
        aluno.setDataNas(txtDn.getText());
        String  mySql ="update aluno set nome_aluno=?, apelido_aluno=?, anonas_aluno=? where id_aluno=?";
        PreparedStatement ps = conn.prepareStatement(mySql);
        ps.setString(1, aluno.getNome());
        ps.setString(2, aluno.getApelido());
        ps.setString(3, aluno.getDataNas());
        ps.setInt(4, aluno.getId());
        ps.executeUpdate();
           }
             catch(Exception erro){
           JOptionPane.showMessageDialog(null, erro.getMessage());
           }
                        }
  
      private void painelProfessor(){
        
      Font rw = new Font("Rockwell", Font.BOLD, 12); 
     painelConteudo.removeAll();
     painelConteudo.repaint();
     cbDisciplina = new JComboBox();
     cbDisciplina.setBounds(20, 300, 150, 35);
     cbDisciplina.setForeground(Color.white);
     painelConteudo.add(cbDisciplina);
     verDisciplinas();
    
   
    btnVerProf = new JButton("Ver");
    btnVerProf.setBounds(300, 300, 100, 30);
    btnVerProf.setFont(rw);
    btnVerProf.setForeground(Color.white);
    btnVerProf.addActionListener(e-> verProf());
     painelConteudo.add(btnVerProf);
    
    String[] Colunas = {"Codigo", "Nome","Apelido", "Ano de Nascimento", "Disciplina"};
    linhas = new DefaultTableModel(Colunas,0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(10, 20, 650, 250);
    painelConteudo.add(scroll);
    
    tabelaTxt();
    txtNome = new JTextField();
    txtNome.setForeground(Color.white);
    txtNome.setBounds(20, 350, 80, 30);
     painelConteudo.add(txtNome);
    
    txtApelido = new JTextField();
    txtApelido.setForeground(Color.white);
    txtApelido.setBounds(120, 350, 80, 30);
    painelConteudo.add(txtApelido);
    
    txtDn  = new JTextField();
    txtDn.setForeground(Color.white);
    txtDn.setBounds(220, 350, 80, 25);
    painelConteudo.add(txtDn);
    FlatSVGIcon btnIcon = new FlatSVGIcon("svg/atualizar.svg");
    btnAtualizar2 = new JButton("Atualizar");
    btnAtualizar2.setIcon(btnIcon);
    btnAtualizar2.setBounds(320, 350, 130, 30);
    btnAtualizar2.setForeground(Color.white);
    btnAtualizar2.setFont(rw);
    btnAtualizar2.addActionListener(e-> atualizarFuncoes());
    painelConteudo.add(btnAtualizar2);
     
     FlatSVGIcon iconLixo = new FlatSVGIcon("svg/trash.svg",30,30);
     JButton btnApagarProf = new JButton("Apagar");
     btnApagarProf.setBounds(480, 350, 140, 40);
     btnApagarProf.setFont(rw);
     btnApagarProf.setIcon(iconLixo);
     btnApagarProf.addActionListener(e-> apagarProf());
     btnApagarProf.setForeground(Color.white);
     painelConteudo.add(btnApagarProf);
    }
      
      private void apagarProf(){
      try {
    int linhas = tabela.getSelectedRow();
    if (linhas != -1) {
    id_Professor = Integer.parseInt(tabela.getValueAt(linhas, 0).toString());
    professor.setId_Professor(id_Professor);
    }
    String sql = "delete from professor where id_prof=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(1, professor.getId_Professor());
    ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Professor apagado!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
      }
    public void painelNovoProfessor(){
    painelConteudo.removeAll();
    painelConteudo.repaint();
    
    Font rw = new Font("Rockwell", Font.BOLD, 14);
    JLabel titulo = new JLabel("REGISTRO DE PROFESSORES");
    titulo.setFont(rw);
    titulo.setForeground(Color.white);
    titulo.setBounds(260, 20, 250, 30);
    painelConteudo.add(titulo);

    lbNomeA = new JLabel("Nome");
    lbNomeA.setBounds(20,50, 70, 30);
    lbNomeA.setFont(rw);
    lbNomeA.setForeground(Color.white);
    painelConteudo.add(lbNomeA);
    
    txtNomeProf = new JTextField();
    txtNomeProf.setBounds(100, 50, 180, 30);
    txtNomeProf.setForeground(Color.white);
    painelConteudo.add(txtNomeProf);
    
    lbApelidoA = new JLabel("Apelido");
    lbApelidoA.setBounds(20, 90 , 70, 30);
    lbApelidoA.setFont(rw);
    lbApelidoA.setForeground(Color.white);
    painelConteudo.add(lbApelidoA);
    
    txtApelidoProf = new JTextField();
    txtApelidoProf.setBounds(100, 90, 180, 30);
    txtApelidoProf.setForeground(Color.white);
    painelConteudo.add(txtApelidoProf);
    
    lbDnA = new JLabel("Data de Nascimento");
    lbDnA.setBounds(20, 130, 150, 30);
    lbDnA.setForeground(Color.white);
    lbDnA.setFont(rw);
    painelConteudo.add(lbDnA);
    
    txtDnProf = new JTextField();
    txtDnProf.setBounds(180, 130, 100, 30);
    txtDnProf.setForeground(Color.white);
    painelConteudo.add(txtDnProf);
    
    lbDocA = new JLabel("Bilhte de identidade");
     lbDocA.setBounds(290, 50, 120, 30);
     lbDocA.setForeground(Color.white);
     lbDocA.setFont(rw);
     painelConteudo.add(lbDocA);
     
     txtDocProf = new JTextField();
     txtDocProf.setBounds(420, 50, 120, 30);
     txtDocProf.setForeground(Color.white);
     painelConteudo.add(txtDocProf);
     
      lbSenhaA = new JLabel("Palavra-passe");
      lbSenhaA.setBounds(290, 90, 120, 30);
      lbSenhaA.setForeground(Color.white);
      lbSenhaA.setFont(rw);
      painelConteudo.add(lbSenhaA);
      
      txtSenhaProf = new JPasswordField();
      txtSenhaProf.setBounds(420, 90, 120, 30);
      txtSenhaProf.setForeground(Color.white);
      painelConteudo.add(txtSenhaProf);
     
      JLabel infoClasse = new JLabel("Disciplina");
      infoClasse.setFont(rw);
      infoClasse.setForeground(Color.white);
      infoClasse.setBounds(360, 170, 100, 30);
      painelConteudo.add(infoClasse);
    
      cbClasse = new JComboBox<>();
      cbClasse.setBounds(220, 170, 120, 30);
      cbClasse.setFont(rw);  
      cbClasse.setForeground(Color.white);
      painelConteudo.add(cbClasse);
      verClasses();
      
      JLabel infoSexo = new JLabel("Sexo");
      infoSexo.setFont(rw);
      infoSexo.setForeground(Color.white);
      infoSexo.setBounds(20, 170, 70, 30);
      painelConteudo.add(infoSexo);
      
      cbSexo = new JComboBox<>();
      cbSexo.addItem("Masculino");
      cbSexo.addItem("Feminino");
      cbSexo.setFont(rw);
      cbSexo.setForeground(Color.white);
      cbSexo.setBounds(80, 170, 120, 30);
      painelConteudo.add(cbSexo);
      
      cbDisciplina = new JComboBox<>();
      cbDisciplina.setBounds(450, 170, 140,30);
      cbDisciplina.setFont(rw);
      cbDisciplina.setForeground(Color.white);
      painelConteudo.add(cbDisciplina);
      
      verDisciplinas();
      
     FlatSVGIcon icon4 = new FlatSVGIcon("svg/save.svg", 40, 30);
     btnGuardarProf = new JButton("Guardar");
     btnGuardarProf.setIcon(icon4);
     btnGuardarProf.setBounds(20, 230, 140, 45);
     btnGuardarProf.setForeground(Color.white);
     btnGuardarProf.setFont(rw);
     btnGuardarProf.addActionListener(e-> guardarProfessor());
     painelConteudo.add(btnGuardarProf);
     
     FlatSVGIcon icon3 = new FlatSVGIcon("svg/pdf.svg", 40, 30);
     JButton btnReciboProf  = new JButton("Recibo");
     btnReciboProf.setBounds(190, 230, 130, 45);
     btnReciboProf.setFont(rw);
     btnReciboProf.setIcon(icon3);
     btnReciboProf.setForeground(Color.white);
     btnReciboProf.addActionListener(e-> reciboProf());
     painelConteudo.add(btnReciboProf);
      }
    private void reciboProf(){
    Document reciboProf = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(reciboProf, new FileOutputStream("Recibo professor "+txtNomeProf.getText()+""+txtApelidoProf.getText()));
               reciboProf.open();
     com.itextpdf.text.Font titulo = FontFactory.getFont(FontFactory.COURIER_BOLD, 16);
     
     Paragraph p1 = new Paragraph("RECIBO DE MATRICULA DO PROFESSOR", titulo);
     
     p1.setAlignment(Element.ALIGN_CENTER);
     reciboProf.add(p1);
     
     Paragraph p2 = new Paragraph("");
     reciboProf.add(p2);
     
    Image logo = Image.getInstance("C:/Users/Anderson/Onedrive/NetBeansProjects/ProjectoES3FI/src/main/java/Professor/ES3.png");
    logo.scaleToFit(150, 150);
    logo.setAlignment(Element.ALIGN_CENTER);
    reciboProf.add(logo);
    
    Paragraph p3 = new Paragraph("");
    reciboProf.add(p3);
    
    com.itextpdf.text.Font par = FontFactory.getFont(FontFactory.COURIER_BOLD, 12);
    Paragraph p4 = new Paragraph("DETALHES DA MATRICULA", titulo);
    p4.setAlignment(Element.ALIGN_CENTER);
    reciboProf.add(p4);
    
      Paragraph p6 = new Paragraph("");
      Paragraph p600 = new Paragraph("");
    
    Paragraph p5 = new Paragraph("Nome: "+txtNomeProf.getText()+" "+txtApelidoProf.getText()+"",par);
    reciboProf.add(p5);
    
    Paragraph p100 = new Paragraph("");
    Paragraph p7 = new Paragraph("DATA DE NASCIMENTO: "+txtDnProf.getText()+" ",par);
    reciboProf.add(p7);
    Paragraph p200 = new Paragraph("");

    Paragraph p8 = new Paragraph("SEXO: "+cbSexo.getSelectedItem()+" ",par);
    reciboProf.add(p8);
    Paragraph p300 = new Paragraph("");
      Paragraph p9 = new Paragraph("Disciplina: "+cbDisciplina.getSelectedItem()+" ",par);
    reciboProf.add(p9);
    Paragraph p500 = new Paragraph("");
      Paragraph p11 = new Paragraph("CODIGO: "+professor.getId_Professor()+" ",par);
    reciboProf.add(p11);
    Paragraph p311 = new Paragraph("");
    reciboProf.add(p311);
     LocalDateTime horaMatricula = LocalDateTime.now();
     DateTimeFormatter tipoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     String dataMatricula = horaMatricula.format(tipoData);
     
     Paragraph p12 = new Paragraph("Documento criado em:"+dataMatricula+"",par);
     reciboProf.add(p12);
    reciboProf.close();
    
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
      
    private void painelDisciplina(){
    painelConteudo.removeAll();
    painelConteudo.repaint();
    Font rw = new Font("Rockwell", Font.BOLD, 14); 
    String Colunas[] = {"Codigo", "Nome","Nivel"};
    linhas = new DefaultTableModel(Colunas, 0);
    tabela = new JTable(linhas);
    tabela.setForeground(Color.white);
    tabela.setFont(rw);
    scroll = new JScrollPane(tabela);
    scroll.setBounds(50, 50, 500, 250);
    scroll.setFont(rw);
    scroll.setForeground(Color.white);
    painelConteudo.add(scroll);
    ver_Default();
    TextFieldTabela();
    
    txtDisc = new JTextField();
    txtDisc.setBounds(50, 350, 130, 30);
    txtDisc.setForeground(Color.white);
    txtDisc.setFont(rw);
    painelConteudo.add(txtDisc);
    
    JButton btnAtualizar = new JButton("Atualizar");
    btnAtualizar.setBounds(180, 350, 130, 30);
    btnAtualizar.setFont(rw);
    btnAtualizar.setForeground(Color.white);
    painelConteudo.add(btnAtualizar);
    btnAtualizar.addActionListener(e-> atualizarDisc() ); 
    
    cbClasse = new JComboBox<>();
    cbClasse.setBounds(340, 350, 100, 30);
    cbClasse.setFont(rw);
    painelConteudo.add(cbClasse);
    verClasses();
    
    JButton btnGuardarDisc = new JButton("Guardar");
    btnGuardarDisc.setBounds(460,350, 120, 30);
    btnGuardarDisc.setForeground(Color.white);
    btnGuardarDisc.setFont(rw);
    btnGuardarDisc.addActionListener(e-> guardarDisc());
    painelConteudo.add(btnGuardarDisc);
    
    FlatSVGIcon iconLixo = new FlatSVGIcon("svg/trash.svg",30,30);
    JButton btnApagar = new JButton();
    btnApagar.setBounds(600, 350, 70, 40);
    btnApagar.setIcon(iconLixo);
    btnApagar.addActionListener(e-> apagarDisciplina());
    painelConteudo.add(btnApagar);
    }
    
    private void apagarDisciplina() {
    int linhaSelecionada = tabela.getSelectedRow();
    if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(null, "Escolhe uma disciplina!");
        return;
    }
    int idDisciplina = (int) tabela.getValueAt(linhaSelecionada, 0); 
    int confirmar = JOptionPane.showConfirmDialog(
            null,
            "Deseja realmente apagar esta disciplina?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION );
    if (confirmar == JOptionPane.YES_OPTION) {
        String sql = "Delete from disciplina where id_disciplina = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDisciplina);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Disciplina apagada com sucesso!");
            verDisciplinas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
      private void guardarDisc(){
      conectar();
    try {
        String nome = txtDisc.getText();
        Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
         String sql = "insert into disciplina(nome_disciplina,id_classe) values (?,?)";
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, nome);
         ps.setInt(2, classeEscolhida.getId_Classe());
         ps.executeUpdate();
         JOptionPane.showMessageDialog(null, "Disciplina Guardada");
         } 
         catch (Exception erro) {
              JOptionPane.showMessageDialog(null, erro.getMessage());
          }
      }
      
       private void TextFieldTabela(){
        tabela.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
          int linha = tabela.getSelectedRow();
    if(linha != -1){
       id_disc = Integer.parseInt(tabela.getValueAt(linha, 0).toString());
    txtDisc.setText(tabela.getValueAt(linha, 1).toString());
        }
          }
        });
    }
        private void atualizarDisc(){
    try(Connection conn = conexao.conectar()){
        disciplina.setId_disciplina(id_disc);
        disciplina.setNome_disciplina(txtDisc.getText());
     String sql = "update disciplina set nome_disciplina=? where id_disciplina=?";
     PreparedStatement ps = conn.prepareStatement(sql);
     ps.setString(1,disciplina.getNome_disciplina());
     ps.setInt(2, id_disc);
     ps.executeUpdate();
     verDisciplinas();
    } catch(SQLException erro){
    erro.printStackTrace();
    }
    }
    
    private void ver_Default(){
        try(Connection conn = conexao.conectar()){
  
        String sql = "select id_disciplina, nome_disciplina, id_classe from disciplina";
        PreparedStatement ps  = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
        disciplina.setId_disciplina(rs.getInt("id_disciplina"));
        disciplina.setNome_disciplina(rs.getString("nome_disciplina"));
        classe.setId_Classe(rs.getInt("id_classe"));
        linhas.addRow(new Object[] {disciplina.getId_disciplina(), disciplina.getNome_disciplina(), classe.getId_Classe()});
        }
        }catch(SQLException erro){
        JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }
    
    private void tabelaTxt(){
        tabela.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
        linhaTabela = tabela.getSelectedRow();
        if(linhaTabela !=-1){
        id_Professor =  Integer.parseInt(tabela.getValueAt(linhaTabela,0).toString());
        txtNome.setText(tabela.getValueAt(linhaTabela, 1).toString());
        txtApelido.setText(tabela.getValueAt(linhaTabela, 2).toString());
        txtDn.setText(tabela.getValueAt(linhaTabela, 3).toString());
        }
              }
        }); 
    }
    private void verProf(){
        tabela.repaint();
        try(Connection conn = conexao.conectar()){
        Disciplina disciplinaEscolhida = (Disciplina) cbDisciplina.getSelectedItem();
        String sql = "select * from professor where id_disciplina=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    if(disciplinaEscolhida != null){
    ps.setInt(1, disciplinaEscolhida.getId_disciplina());
    }
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
     int idProf  = rs.getInt("id_prof");
     String nome  = rs.getString("nome_prof");
    String apelido = rs.getString("apelido_prof");
    String dn = rs.getString("anonas_prof");
    int id_classe = rs.getInt("id_classe");
    int id_disciplina = rs.getInt("id_disciplina");
    linhas.addRow(new Object[] {idProf, nome, apelido, dn, id_disciplina});          
    }
    } catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    private void verDisciplinasProfessor() {
     try(Connection conn = conexao.conectar()) {
     String select = "select id_disciplina, nome_disciplina from disciplina where id_classe=?";
     PreparedStatement ps = conn.prepareStatement(select);
     ps.setInt(1, Id_Classe);
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
   
    public void verDisciplinasProf() {
      try (Connection conn = conexao.conectar()) {
     String select = "select id_disciplina, nome_disciplina from disciplina where id_classe=?";
     PreparedStatement ps = conn.prepareStatement(select);
     ps.setInt(1, id_classe);
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
  
    private void atualizarFuncoes(){
    try(Connection conn = conexao.conectar()){
        Classe classe = new Classe();
        Disciplina disciplina =  new Disciplina();
        Disciplina disciplinaEscolhida = (Disciplina) cbDisciplina.getSelectedItem();
        int id_disciplina = disciplinaEscolhida.getId_disciplina();
        String sqlAP ="update professor set nome_prof=?, apelido_prof=?, anonas_prof=?, id_disciplina=? where id_prof=?";
        PreparedStatement ps = conn.prepareStatement(sqlAP);
        professor.setId_Professor(id_Professor);
        professor.setNome_Professor(txtNome.getText());
        professor.setApelido_Professor(txtApelido.getText());
        professor.setDataNasc(txtDn.getText());
        ps.setString(1,professor.getNome_Professor());
        ps.setString(2, professor.getApelido_Professor());
        ps.setString(3, professor.getDataNasc());
        ps.setInt(4, id_disciplina);
        ps.setInt(5, professor.getId_Professor());
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Dados Atualizados com sucesso!");
    }catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }

    public static void main(String[] abg){
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception e) {
        }
        Aluno aluno = new Aluno();
        Professor professor = new Professor();
        Classe classe = new Classe();
        Disciplina disciplina = new Disciplina();
        Turma turma = new Turma();
                SwingUtilities.invokeLater(()-> new TelaAdmin(aluno,professor,classe, disciplina, turma).setVisible(true));
    }
}
