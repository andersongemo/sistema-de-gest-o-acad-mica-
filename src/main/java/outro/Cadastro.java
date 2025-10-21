/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Anderson
 */
public class Cadastro extends JFrame{
    private JLabel titulo;
    private JLabel lblNome;
    private JLabel lblApelido;
    private JLabel lblSenha;
    private JTextField txtNome;
    private JTextField  txtApelido;
    private JPasswordField txtSenha;
    private JButton btnGuardar;
    private JTable tabela;
    private JButton btnMostar;
    
            
    
    public Cadastro(){
        setSize(720, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        JTextField nome2 = new JTextField();
        Font rw = new Font("Rockwell", Font.BOLD, 15);
        titulo = new JLabel("Cadastro");
        titulo.setBounds(290, 20, 100, 20);
        titulo.setFont(rw);
        titulo.setForeground(Color.BLACK);
        add(titulo);
        
        lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 100, 70, 20);
        lblNome.setFont(rw);
        add(lblNome);        
        
        txtNome = new JTextField();
        txtNome.setBounds(120, 100, 80, 20);
        add(txtNome);
        
        lblApelido = new JLabel("Apelido:");
        lblApelido.setBounds(30, 130, 80, 20);
        lblApelido.setFont(rw);
        add(lblApelido);
        
        txtApelido = new JTextField();
        txtApelido.setBounds(120, 130, 80, 20);
        add(txtApelido);
        
        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(30, 160, 80, 20);
        lblSenha.setFont(rw);
        add(lblSenha);
        
        txtSenha = new JPasswordField();
        txtSenha.setBounds(120, 160, 80, 20);
        add(txtSenha);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(60, 190, 80, 20);
        add(btnGuardar);
        
        btnMostar = new JButton("Mostar");
        btnMostar.setBounds(60, 220, 80, 20);
        add(btnMostar);
        btnMostar.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
        try(Connection conn = conexao.conectar()){
        String select = "select * from login";
        DefaultTableModel modelo = new DefaultTableModel(new String[ ]{"ID", "Nome", "Apelido","Senha"},0);
        modelo.setRowCount(0);
        
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String apelido = rs.getString("apelido");
        String senha = rs.getString("senha");
        modelo.addRow(new Object[] {id, nome, apelido, senha});
        tabela.setModel(modelo);
        
        }
        
        
        
        } catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        
        }
        }
        });
        
        tabela = new JTable();
        tabela.setBounds(100, 270, 400, 400);
        add(tabela);

         

    btnGuardar.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evento){
        cadastro();
        }
        });

        }

        

    
    public void cadastro(){
    try(Connection mysql = conexao.conectar()){
        String nome = txtNome.getText();
        String apelido =  txtApelido.getText();
        String senha = new String(txtSenha.getPassword());
      String insert  = "insert into login(nome, apelido, senha) values (?,?,?)";
      PreparedStatement ps = mysql.prepareStatement(insert);
      ps.setString(1, nome);
      ps.setString(2, apelido);
      ps.setString(3, senha);
      ps.executeUpdate();
      JOptionPane.showMessageDialog(null, "Usuario Cadastrado");
    } catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }

    public static void main(String[] abg){
    SwingUtilities.invokeLater(()-> new Cadastro().setVisible(true));
    }
}
