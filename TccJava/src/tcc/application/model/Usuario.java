/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Borges
 */
@Entity
 @DiscriminatorValue(value="USUARIO")
@Table(name = "usuario")
public class Usuario extends Pessoa implements Serializable {

    private Date data_cadastro;
    private String endereco;

    public Usuario() {
    }
    
    @OneToMany
    @JoinColumn(name = "id_consulta")
    private List<Consulta> consultas;
    
    public Usuario(Date data_cadastro, List<Consulta> consultas, String email, String nome, String senha, String cpf, LocalDate data_nascimento) {
        super(email, nome, senha, cpf, data_nascimento);
        this.data_cadastro = data_cadastro;
        this.consultas = consultas;
    }
    
    

   

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

    @Override
    public String toString() {
        return "Usuario{" + "data_cadastro=" + data_cadastro + ", endereco=" + endereco + '}';
    }

}
