/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;
import javax.persistence.Query;
import tcc.application.model.Clinica;
import tcc.application.model.Horarios;

/**
 *
 * @author Borges
 */
public class DaoClinica extends Dao {
     public boolean inserir(Clinica c) {
        em.getTransaction().begin();
        try {
            em.persist(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean remove(Clinica c) {
        em.getTransaction().begin();
        try {
            em.remove(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public List<Clinica> listar() {
        return em.createQuery("select c from Clinica c").getResultList();
    }
    
     public Clinica selecionar(){
        Query consulta=em.createQuery("select c from Clinica c");
        return (Clinica) consulta.getSingleResult();
    }
    
    public Clinica HorariosPorMedico(String cpf){
        Query consulta=em.createQuery("select c from Clinica c where c.nome:m");
        consulta.setParameter("m", cpf);
        return (Clinica) consulta.getSingleResult();
    }
    
    
    public boolean editar(Clinica c) {
        em.getTransaction().begin();
        try {
            em.merge(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
}
