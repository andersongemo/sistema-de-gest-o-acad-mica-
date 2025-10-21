/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classe;

/**
 *
 * @author Anderson
 */
public class Classe {
private int id_Classe;
    private String nome_Classe;
    private String nivel_Classe;
    
    public int getId_Classe() {
        return id_Classe;
    }

    public void setId_Classe(int id_Classe) {
        this.id_Classe = id_Classe;
    }

    public String getNome_Classe() {
        return nome_Classe;
    }

    public void setNome_Classe(String nome_Classe) {
        this.nome_Classe = nome_Classe;
    }

    public String getNivel_Classe() {
        return nivel_Classe;
    }

    public void setNivel_Classe(String nivel_Classe) {
        this.nivel_Classe = nivel_Classe;
    }
    @Override 
    public String toString(){
     return nome_Classe;   
    }
    
}
