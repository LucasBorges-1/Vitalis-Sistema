/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Borges
 */
@Entity
@Table(name = "usuario")
public class Usuario extends Pessoa {
    private Date data_cadastro;
    private String endereco;

    public Usuario() {
    }
  
    public Usuario(Date data_cadastro, String endereco, Pessoa pessoa, String email, String nome, String senha, String cpf, Date data_nascimento) {
        super(email, nome, senha, cpf, data_nascimento);
        this.data_cadastro = data_cadastro;
        this.endereco = endereco;
        this.pessoa = pessoa;
    }
         
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa; 

    
    
    
    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "Usuario{" + "data_cadastro=" + data_cadastro + ", endereco=" + endereco + '}';
    }
    
}
