/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.sql.Time;
import javax.persistence.CascadeType;
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
public class Horarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_horario;
    private DateTime dia;
    private Time inicio;
    private Time fim;
    
    
    
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico; 

    public Horarios() {
        
    }

    public Horarios(DateTime dia, Time inicio, Time fim) {
        this.dia = dia;
         this.inicio = Time.valueOf("08:00:00");
        this.fim = Time.valueOf("17:00:00");
        this.medico = null;
    }

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public DateTime getDia() {
        return dia;
    }

    public void setDia(DateTime dia) {
        this.dia = dia;
    }

    public Time getInicio() {
        return inicio;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public Time getFim() {
        return fim;
    }

    public void setFim(Time fim) {
        this.fim = fim;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Horarios{" + "dia=" + dia + ", inicio=" + inicio + ", fim=" + fim + ", medico=" + medico + '}';
    }

    
}
