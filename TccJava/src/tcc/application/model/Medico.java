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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
     @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;
    
    @OneToMany(mappedBy = "medico")
    private List<Horarios> horarios;
    
    @ManyToMany
    @JoinTable(
        name = "clinica_medico",
        joinColumns = @JoinColumn(name = "id_medico"),
        inverseJoinColumns = @JoinColumn(name = "id_clinica")
    )
    private List<Clinica> clinicas;
    
    
    
    public Medico() {
    }

    public Medico(String crm, List<Horarios> horarios, List<Clinica> clinicas, String email, String nome, String senha, String cpf, Date data_nascimento) {
        super(email, nome, senha, cpf, data_nascimento);
        this.crm = crm;
        this.consultas = null;
        this.horarios = horarios;
        this.clinicas = clinicas;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Horarios> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horarios> horarios) {
        this.horarios = horarios;
    }

    public List<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    @Override
    public String toString() {
        return "Medico{" + "crm=" + crm + ", consultas=" + consultas + ", horarios=" + horarios + ", clinicas=" + clinicas + '}';
    }

    
    
}
