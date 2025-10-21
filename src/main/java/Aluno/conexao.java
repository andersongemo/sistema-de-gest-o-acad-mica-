package Aluno;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Anderson
 */

public class conexao {
    private static final String url = "jdbc:mysql://localhost:3306/novo";
    private static final String user = "root";
    private static final String ps = "2706";
    
    
    public static Connection conectar(){
    try{
    return DriverManager.getConnection(url,user, ps);
    } catch(Exception e){
    JOptionPane.showMessageDialog(null, e.getMessage());
    return null;
    }
    }
    
}
