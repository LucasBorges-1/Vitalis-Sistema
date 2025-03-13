/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tcc.application.model.Pessoa;

/**
 *
 * @author Borges
 */
public class Dao {

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("TccJavaPU");
    protected EntityManager em = emf.createEntityManager();

   
     
}
