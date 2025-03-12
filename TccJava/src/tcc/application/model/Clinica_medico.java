/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Borges
 */
@Entity
@Table(name = "clinica_medico")
public class Clinica_medico implements Serializable {

    @EmbeddedId
    private Clinica_medico id;

    @ManyToOne
    @JoinColumn(name = "id_medico", insertable = false, updatable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_clinica", insertable = false, updatable = false)
    private Clinica clinica;

    public Clinica_medico() {
    }

    public Clinica_medico(Medico medico, Clinica clinica) {
        this.medico = medico;
        this.clinica = clinica;
    }

    public Clinica_medico getId() {
        return id;
    }

    public void setId(Clinica_medico id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    @Override
    public String toString() {
        return "Clinica_medico{" + "medico=" + medico + ", clinica=" + clinica + '}';
    }

   
}
