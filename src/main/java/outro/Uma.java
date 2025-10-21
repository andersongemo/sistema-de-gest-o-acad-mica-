/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package outro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
/**
 *
 * @author Anderson
 */
public class Uma extends JFrame {
    public Uma(Pessoa pessoa){

    setVisible(true);
    setSize(500, 500);
    setLayout(null);
    
    JButton btn = new JButton("OK");
    btn.setBounds(70, 200, 60, 20);
    add(btn);
    
    JLabel nome =new JLabel("Nome:");
    nome.setBounds(20, 70, 70, 20);
    add(nome);
    
    JTextField txtNome = new JTextField();
    txtNome.setBounds(20, 100, 70, 20);
    add(txtNome);
    
    JLabel senha =new JLabel("senha:");
    senha.setBounds(20, 140, 70, 20);
    add(senha);
    
    JTextField txtSenha = new JTextField();
    txtSenha.setBounds(20, 180, 70, 20);
     add(txtSenha);
    
    btn.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evt){
       pessoa.setId(Integer.parseInt(txtNome.getText()));
       pessoa.setSenha(txtSenha.getText());
       try(Connection conn = conexao.conectar()){
       String select = "select * from login where  id=? and senha=?";
       PreparedStatement ps = conn.prepareStatement(select);
       ps.setInt(1, pessoa.getId());
       ps.setString(2, pessoa.getSenha());
       ResultSet resultado = ps.executeQuery();
       if(resultado.next()){
           pessoa.setId(resultado.getInt("id"));
           pessoa.setSenha(resultado.getString("senha"));
       JOptionPane.showMessageDialog(null, "Logado");
       }
       else{
       JOptionPane.showMessageDialog(null, "Usuario invalido");
       }
       
       } catch(SQLException erro){
       JOptionPane.showMessageDialog(null, erro.getMessage());
       }
    }
    });
    
    }
    
    
    
    
    public static void main(String[] abg){
        Pessoa pessoa = new Pessoa();
       SwingUtilities.invokeLater(()-> new Uma(pessoa).setVisible(true));
    
}
}
