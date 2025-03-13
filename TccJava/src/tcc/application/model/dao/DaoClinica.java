/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import tcc.application.model.Clinica;
import tcc.application.model.Horarios;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;

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
    
    
    public Clinica buscarClinicaPorNome(String nome) {
    EntityManager em = emf.createEntityManager();
    try {
        return em.createQuery("SELECT c FROM Clinica c WHERE c.nome=:nome", Clinica.class)
                 .setParameter("nome", nome)
                 .getSingleResult();
    } catch (NoResultException e) {
        return null; // Médico não encontrado
    } catch (Exception e) {
        e.printStackTrace(); // Log do erro inesperado
        return null;
    } finally {
        em.close();
    }
}

   
    
    
    public boolean validarLogin(String nome,String senhaDigitada) {
        Clinica c = buscarClinicaPorNome(nome);
        if (c != null) {
            return BCryptUtil.verificarSenha(senhaDigitada,c.getSenha());
        }
        return false;
    }
}
