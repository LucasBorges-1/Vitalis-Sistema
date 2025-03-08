/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name="medico")
public class Medico extends Pessoa{
  
    private String crm;
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="id_medico")
    private List<Clinica_medico> clinicas = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa; 
    public Medico() {
    }

    public Medico(String crm, Pessoa pessoa, String email, String nome, String senha, String cpf, Date data_nascimento) {
        super(email, nome, senha, cpf, data_nascimento);
        this.crm = crm;
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Clinica_medico> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica_medico> clinicas) {
        this.clinicas = clinicas;
    }

   

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public String toString() {
        return super.toString()+" Medico{" + "crm=" + crm + '}';
    }
    
    
    
}
