/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Borges
 */

@Entity
@Table(name = "horarios")
@Access(AccessType.FIELD)
public class Horarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_horario;
    @Column(name = "data")
    private LocalDate data;
    
    @Column(name = "iniciomanha")
    private LocalTime inicioManha;

    @Column(name = "fimmanha")
    private LocalTime fimManha;

    @Column(name = "iniciotarde")
    private LocalTime inicioTarde;

    @Column(name = "fimtarde")
    private LocalTime fimTarde;
    
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    public Horarios(LocalDate data, LocalTime inicioManha, LocalTime fimManha, LocalTime inicioTarde, LocalTime fimTarde, Medico medico) {
        this.data = data;
        this.inicioManha = inicioManha;
        this.fimManha = fimManha;
        this.inicioTarde = inicioTarde;
        this.fimTarde = fimTarde;
        this.medico = medico;
    }

    public Horarios() {
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getInicioManha() {
        return inicioManha;
    }

    public void setInicioManha(LocalTime inicioManha) {
        this.inicioManha = inicioManha;
    }

    public LocalTime getFimManha() {
        return fimManha;
    }

    public void setFimManha(LocalTime fimManha) {
        this.fimManha = fimManha;
    }

    public LocalTime getInicioTarde() {
        return inicioTarde;
    }

    public void setInicioTarde(LocalTime inicioTarde) {
        this.inicioTarde = inicioTarde;
    }

    public LocalTime getFimTarde() {
        return fimTarde;
    }

    public void setFimTarde(LocalTime fimTarde) {
        this.fimTarde = fimTarde;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

   
}
