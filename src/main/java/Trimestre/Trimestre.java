/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Trimestre;

/**
 *
 * @author Anderson B. Gemo
 */
public class Trimestre {
private int Id_Trimestre;
    private String nome_Trimestre;
    
    public int getId_Semstre() {
        return Id_Trimestre;
    }

    public void setId_Semstre(int Id_Semstre) {
        this.Id_Trimestre = Id_Semstre;
    }

    public String getNome_Semestre() {
        return nome_Trimestre;
    }

    public void setNome_Semestre(String nome_Semestre) {
        this.nome_Trimestre = nome_Semestre;
    }
    public String toString(){
    return nome_Trimestre; 
    }
    
}
