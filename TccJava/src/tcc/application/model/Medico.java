/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.bouncycastle.asn1.x500.style.RFC4519Style;

/**
 *
 * @author Borges
 */
@Entity
 @DiscriminatorValue(value="MEDICO")
@Table(name="medico")
public class Medico extends Pessoa implements Serializable{
    
    private String crm;
    
    @Column(name="tipo_medico")
    private String tipo_medico;
    
    @OneToMany
    @JoinColumn(name = "id_consultas") 
    private List<Consulta> consultas;
    
    @OneToMany
    @JoinColumn(name = "id_horarios") 
    private List<Horarios> horarios;

    @ManyToOne
    @JoinColumn(name = "id_clinica") 
    private Clinica clinica;
    
    
    
    public Medico() {
    }

    public Medico(String crm, Clinica clinica, String email, String nome, String senha, String cpf, LocalDate data_nascimento,String tipo_medico) {
        super(email, nome, senha, cpf, data_nascimento);
        this.crm = crm;
        this.tipo_medico=tipo_medico;
        this.consultas = null;
        this.horarios = null;
        this.clinica = clinica;
    }

   

    public String getCrm() {
        return crm;
    }

    public String getTipo_medico() {
        return tipo_medico;
    }

    public void setTipo_medico(String tipo_medico) {
        this.tipo_medico = tipo_medico;
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

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Medico(String crm, Clinica clinica) {
        this.crm = crm;
        this.clinica = clinica;
    }

    @Override
    public String toString() {
        return "Medico{" + "crm=" + crm + ", consultas=" + consultas + ", horarios=" + horarios + ", clinica=" + clinica + '}';
    }
    
   

    
    
    
}
