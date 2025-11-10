/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Professor;

/**
 *
 * @author Anderson
 */
public class Professor {
    private int id_Professor;
    private String senha_Professor;
    private String nome_Professor;
    private String apelido_Professor;
    private String dataNasc;
    private String NrBiProf;
    
    public String getNrBiProf() {
        return NrBiProf;
    }

    public void setNrBiProf(String NrBiProf) {
        this.NrBiProf = NrBiProf;
    }
    
    private String sexo_Prof;
    
    public String getSexo_Prof() {
        return sexo_Prof;
    }

    public void setSexo_Prof(String sexo_Prof) {
        this.sexo_Prof = sexo_Prof;
    }
    
 
    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }
    
    private int Id_Disciplina;

    public int getId_Professor() {
        return id_Professor;
    }

    public void setId_Professor(int id_Professor) {
        this.id_Professor = id_Professor;
    }

    public String getSenha_Professor() {
        return senha_Professor;
    }

    public void setSenha_Professor(String senha_Professor) {
        this.senha_Professor = senha_Professor;
    }

    public String getNome_Professor() {
        return nome_Professor;
    }

    public void setNome_Professor(String nome_Professor) {
        this.nome_Professor = nome_Professor;
    }

    public String getApelido_Professor() {
        return apelido_Professor;
    }

    public void setApelido_Professor(String apelido_Professor) {
        this.apelido_Professor = apelido_Professor;
    }

    public int getId_Disciplina() {
        return Id_Disciplina;
    }

    public void setId_Disciplina(int Id_Disciplina) {
        this.Id_Disciplina = Id_Disciplina;
    }

    
    
}
