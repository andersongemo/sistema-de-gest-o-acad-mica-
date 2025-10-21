/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import Classe.Classe;
/**
 *
 * @author Anderson B. Gemo
 */
public class insertD extends JFrame{
    private JLabel disc;
    private JTextField txtNome;
    private JComboBox <Classe> cbClasse;
    private JButton btnGuardar;
    private Classe classe;
    private int id_classe;
    public insertD(Classe classe){
        this.classe = classe;
    setSize(500, 500);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    disc = new JLabel("Nome");
    disc.setBounds(50, 20, 100, 25);
    add(disc);
    
    txtNome = new JTextField();
    txtNome.setBounds(50, 50, 120, 25);
    add(txtNome);
    
    btnGuardar = new JButton("Guardar");
    btnGuardar.setBounds(50, 100, 100, 30);
    btnGuardar.addActionListener(e-> guardarDisc());
    add(btnGuardar);
    
    cbClasse = new JComboBox();
    cbClasse.setBounds(50, 140, 100, 25);
    add(cbClasse);
    verClasse();
    
    
    }
    private void verClasse(){
    try(Connection conn = conexao.conectar()){
    String sql = "select id_classe, nome_classe from classe";
    PreparedStatement ps = conn.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Classe classe = new Classe();
    classe.setId_Classe(rs.getInt("id_classe"));
    classe.setNome_Classe(rs.getString("nome_classe"));
    cbClasse.addItem(classe);
    }
    }catch(Exception erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    private void guardarDisc(){
        String nomeD = txtNome.getText();
        Classe classeEscolhida = (Classe) cbClasse.getSelectedItem();
        id_classe = (classeEscolhida.getId_Classe());
        try(Connection conn = conexao.conectar()){
    String sql = "insert into disciplina(nome_disciplina, id_classe) values (?,?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, nomeD);
    ps.setInt(2,id_classe);
    ps.executeUpdate();
    JOptionPane.showMessageDialog(null, "Disciplina "+nomeD+" Guardada com sucesso, Classe: "+classeEscolhida.getId_Classe());
    } catch(SQLException erro){
    JOptionPane.showMessageDialog(null, erro.getMessage());
    }
    }
    public static void main(String[] abg){
        Classe classe = new Classe();
    SwingUtilities.invokeLater(()-> new insertD(classe).setVisible(true));
    }
}
