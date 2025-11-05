/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Acesso;

import Aluno.Aluno;
import Professor.Professor;
import javax.swing.UIManager;

/**
 *
 * @author Anderson B. Gemo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme");
        } catch (Exception e) {
           
        }
        Professor professor = new Professor();
        Aluno aluno = new Aluno();
        Login lg = new Login(professor, aluno);
        lg.setVisible(true);
    }
}
