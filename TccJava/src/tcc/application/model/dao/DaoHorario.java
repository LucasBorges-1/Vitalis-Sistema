/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;
import javax.persistence.Query;
import tcc.application.model.Horarios;

/**
 *
 * @author Borges
 */
public class DaoHorario extends Dao{
    public boolean inserir(Horarios c) {
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

    public boolean remove(Horarios c) {
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

    public List<Horarios> listar() {
        return em.createQuery("select c from Horarios c").getResultList();
    }
    
    public Horarios HorariosPorMedico(String cpf){
        Query consulta=em.createQuery("select c from Horarios c where c.medico.nome=:m");
        consulta.setParameter("m", cpf);
        return (Horarios) consulta.getSingleResult();
    }
    
    
    public boolean editar(Horarios c) {
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
