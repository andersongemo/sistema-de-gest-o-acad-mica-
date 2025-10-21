/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Turma;

/**
 *
 * @author Anderson B. Gemo
 */
public class Turma {
private int id_Turma;
    private String nome_Turma;
    
    public int getId_Turma() {
        return id_Turma;
    }

    public void setId_Turma(int id_Turma) {
        this.id_Turma = id_Turma;
    }

    public String getNome_Turma() {
        return nome_Turma;
    }

    public void setNome_Turma(String nome_Turma) {
        this.nome_Turma = nome_Turma;
    }
    @Override
    public  String toString(){
    return nome_Turma;
    }
    
    
}
