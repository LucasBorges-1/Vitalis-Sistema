/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import tcc.application.model.Consulta;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;

/**
 *
 * @author Borges
 */
public class DaoConsulta extends Dao {
    
      public boolean inserir(Consulta c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
        return true;
    }


    public boolean editar(Consulta c) {
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
    
    
   

  
    
    
    
   
    public List<Consulta> listar(int id_pessoa) {
        Query consulta = em.createQuery("select c from Consulta c where c.medico.id_pessoa=:id_pessoa");
        consulta.setParameter("id_pessoa",id_pessoa);
        return consulta.getResultList();
        
    }
    
        public List<Consulta> buscarPorNome(String nome) {
        Query consulta = em.createQuery("select c from Consulta c where c.usuario.nome=:nome");
        consulta.setParameter("nome",nome);
        return consulta.getResultList();
    }
    
     public Consulta selecionar(int id_consulta){
        Query consulta=em.createQuery("select c from Consulta c where c.id_consulta:m");
        consulta.setParameter("m", id_consulta);
        return (Consulta) consulta.getSingleResult();
    }
    
    
   
   
}

