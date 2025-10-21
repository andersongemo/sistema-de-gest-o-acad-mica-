/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercicios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Anderson
 */
public class Tela extends JFrame{
    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblEmail;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JButton btnInserir;
    private JButton btnSelecionar;
    private JButton btnExcluir;
    private JButton btnAlterar;
    private JButton btnLimpar;
    private DefaultTableModel colunas;
    private JTable tabela;
            
    public Tela(){
    setSize(720, 600);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Font rw = new Font("Rockwell", Font.BOLD, 15);
    lblId = new JLabel("ID");
    lblId.setBounds(30, 55, 20, 20);
    lblId.setFont(rw);
    add(lblId);
    
    txtId = new JTextField();
    txtId.setBounds(100, 55, 100, 20);
    add(txtId);
    
    lblNome = new JLabel("Nome:");
    lblNome.setBounds(30, 80, 50, 20);
    lblNome.setFont(rw);
    add(lblNome);
    
    txtNome = new JTextField();
    txtNome.setBounds(100, 80, 100, 20);
    add(txtNome);
    
    lblEmail = new JLabel("Email:");
     lblEmail.setBounds(30, 110, 70, 20);
     lblEmail.setFont(rw);
    add(lblEmail);
    
    txtEmail = new JTextField();
    txtEmail.setBounds(100, 110, 100, 20);
    add(txtEmail);
    
    btnInserir = new JButton("Inserir");
    btnInserir.setBounds(40, 150, 90, 25);
    add(btnInserir);
    
    btnSelecionar = new JButton("Selecionar");
    btnSelecionar.setBounds(150, 150, 90, 25);
    add(btnSelecionar);
    
    btnExcluir = new JButton("Excluir");
    btnExcluir.setBounds(240, 150, 90, 25);
    add(btnExcluir);
    
    btnAlterar = new JButton("Alterar");
    btnAlterar.setBounds(370, 150, 90, 25);
    add(btnAlterar);
    
    btnLimpar = new JButton("Limpar");
    btnLimpar.setBounds(490, 150, 90, 25);
    add(btnLimpar);
    
    colunas = new DefaultTableModel(new String[] {"ID","NOME","E-MAIL"},0);
    tabela = new JTable(colunas);
    JScrollPane sp = new JScrollPane(tabela);
    sp.setBounds(80, 200, 440, 180 );
    add(sp);
    ver();
    btnAlterar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evt){
    int parteClicada = tabela.getSelectedRow();
    if(parteClicada != -1){
    colunas.setValueAt(txtId.getText(), parteClicada, 0);
    colunas.setValueAt(txtNome.getText(), parteClicada, 1);
    colunas.setValueAt(txtEmail.getText(), parteClicada, 2);
    
    }
    }
    });
    btnInserir.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evt){
    try(Connection conn = conexao.conectar()){
        String nome = txtNome.getText();
        String email = txtEmail.getText();
    String inserir = "insert into cliente(nome, email) values (?,?)";
    PreparedStatement ps = conn.prepareStatement(inserir);
    ps.setString(1, nome);
    ps.setString(2, email);
    ps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Guardado");
    } catch(Exception erro){
    
    }
    }
    });
    
  //  btnExcluir.addActionListener(n);
    }
    private void ver(){
        try(Connection conn = conexao.conectar()){
        colunas.setRowCount(0); 
        String sql = "select * from cliente";
        PreparedStatement ps = conn.prepareStatement(sql);
    ResultSet rs = ps.executeQuery(sql);
   
    if(rs.next()){
    colunas.addRow(new Object[] {rs.getInt("id"), rs.getString("nome"), rs.getString("email")});
    }
        } catch(Exception e){
            
            
        }}
    
    public static void main(String[] abg){
    SwingUtilities.invokeLater(()-> new Tela().setVisible(true));
    }    
}
