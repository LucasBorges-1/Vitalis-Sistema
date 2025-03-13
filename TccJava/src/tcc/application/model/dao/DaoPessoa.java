/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;

/**
 *
 * @author Borges
 */
public class DaoPessoa extends Dao{
   
     public boolean inserir(Pessoa pessoa) {
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
        
        return true;
    }


    public boolean editar(Pessoa p) {
        em.getTransaction().begin();
        try {
            em.merge(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean remover(Pessoa p) {
        em.getTransaction().begin();
        try {
            em.remove(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
    
    
    public Pessoa buscarPessoaPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Pessoa pessoa = em.find(Pessoa.class, id);
        em.close();
        return pessoa;
    }
    public List<Medico> listar() {
        return em.createQuery("select c from Pessoa c").getResultList();
    }
  
   public Medico buscarMedicoPorCrm(String crm) {
    EntityManager em = emf.createEntityManager();
    try {
        return em.createQuery("SELECT m FROM Medico m WHERE m.crm = :crm", Medico.class)
                 .setParameter("crm", crm)
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

   
    
    
    public boolean validarLogin(String crm, String senhaDigitada) {
        Pessoa pessoa = buscarMedicoPorCrm(crm);
        if (pessoa != null) {
            return BCryptUtil.verificarSenha(senhaDigitada, pessoa.getSenha());
        }
        return false;
    }

}
