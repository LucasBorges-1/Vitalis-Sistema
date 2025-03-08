/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Borges
 */
@Entity
@Table(name = "clinica")
public class Clinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_clinica;
    private String cnpj; 
    private String nome;
    private String numero;
    private String endereco; 
    private String senha;
    

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="id_clinica")
    private List<Clinica_medico> medicosAssociados = new ArrayList<>();

    public Clinica() {
    }

    public Clinica(String cnpj, String nome, String numero, String endereco, String senha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.numero = numero;
        this.endereco = endereco;
        this.senha = senha;
    }

    public int getId_clinica() {
        return id_clinica;
    }

    public void setId_clinica(int id_clinica) {
        this.id_clinica = id_clinica;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Clinica_medico> getMedicosAssociados() {
        return medicosAssociados;
    }

    public void setMedicosAssociados(List<Clinica_medico> medicosAssociados) {
        this.medicosAssociados = medicosAssociados;
    }

    
    
    @Override
    public String toString() {
        return "Clinica{" + "cnpj=" + cnpj + ", nome=" + nome + ", numero=" + numero + ", endereco=" + endereco + ", senha=" + senha + '}';
    }
    
}
