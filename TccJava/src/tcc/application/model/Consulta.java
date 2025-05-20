/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.bouncycastle.asn1.cms.Time;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Borges
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_consulta;
    private LocalDate data_consulta;
    @Column(name = "type")
    private String tipo;
    private LocalDateTime horario;
    
    //A discutir
    //private String caminho_documentos;
            
    public Consulta() {
    }

    public Consulta(LocalDate data_consulta,String tipo,LocalDateTime horario) {
        this.data_consulta = data_consulta;
        this.tipo=tipo;
        this.horario=horario;
    }

    public int getId_consulta() {
        return id_consulta;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

    public LocalDate getData_consulta() {
        return data_consulta;
    }

    public void setData_consulta(LocalDate data_consulta) {
        this.data_consulta = data_consulta;
    }

    @Override
    public String toString() {
        return "Consulta{" + "data_consulta=" + data_consulta + ", tipo=" + tipo + '}';
    }

    
    

    
    
   }
