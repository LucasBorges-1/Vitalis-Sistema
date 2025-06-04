/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
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
public class Horarios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_horario;
    private LocalDate dia;
    private Time inicio;
    private Time fim;

    //id_medico : int
    //data-inicio : date
    //data-final : date
    //horario-inicio-manha : time
    //horario-fim-manha : time
    //horario-inicio-tarde : time
    //horario-fim-tarde : time
    
    
    
    public Horarios() {
        
    }

    public Horarios(LocalDate dia, Time inicio, Time fim) {
        this.dia = dia;
         this.inicio = Time.valueOf("08:00:00");
        this.fim = Time.valueOf("17:00:00");
        
      
    }

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
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

    @Override
    public String toString() {
        return "Horarios{" + "dia=" + dia + ", inicio=" + inicio + ", fim=" + fim + '}';
    }


  

    
}
