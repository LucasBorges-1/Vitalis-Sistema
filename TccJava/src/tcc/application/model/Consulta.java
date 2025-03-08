/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Borges
 */
@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_consulta;
    private DateTime data_consulta;
    
    //A discutir
    //private String caminho_documentos;
    
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario; 
       
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_medico")
    private Medico medico; 
            
    public Consulta() {
    }

    public Consulta(DateTime data_consulta, Usuario usuario, Medico mediico) {
        this.data_consulta = data_consulta;
        this.usuario = usuario;
        this.medico = mediico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Medico getMediico() {
        return medico;
    }

    public void setMediico(Medico mediico) {
        this.medico = mediico;
    }
    
    

    

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

    public DateTime getData_consulta() {
        return data_consulta;
    }

    public void setData_consulta(DateTime data_consulta) {
        this.data_consulta = data_consulta;
    }

    @Override
    public String toString() {
        return "Consulta{" + "data_consulta=" + data_consulta + '}';
    }
    
    
}
