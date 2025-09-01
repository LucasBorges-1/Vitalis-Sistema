/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.model.dao;

/**
 *
 * @author Borges
 */
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;
public class BCryptUtil {


    // Criptografa uma senha
    public static String hashSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    } 

    // Verifica se a senha está correta
    public static boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return BCrypt.checkpw(senhaDigitada, senhaCriptografada);
    }
}



