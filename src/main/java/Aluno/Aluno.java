package Aluno;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anderson
 */
public class Aluno {
    private int id;
    private String nome;
    private String apelido;
    private String dataNas;
    private String nrBi; 
    private String senha;
    private int id_Classe;
    private int id_turma_Aluno;
    public int getId_turma_Aluno() {
        return id_turma_Aluno;
    }

    public void setId_turma_Aluno(int id_turma_Aluno) {
        this.id_turma_Aluno = id_turma_Aluno;
    }
  
    public String getNrBi() {
        return nrBi;
    }

    public void setNrBi(String nrBi) {
        this.nrBi = nrBi;
    }
    public int getId_Classe() {
        return id_Classe;
    }

    public void setId_Classe(int id_Classe) {
        this.id_Classe = id_Classe;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
   
    
    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    private String sexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getDataNas() {
        return dataNas;
    }

    public void setDataNas(String dataNas) {
        this.dataNas = dataNas;
    }

    
}
