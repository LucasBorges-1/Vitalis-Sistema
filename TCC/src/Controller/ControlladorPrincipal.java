/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Wiew.TelaDeLogin;
import Wiew.TelaPrincipal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author User
 */
public class ControlladorPrincipal {
    private TelaPrincipal telaPrincipal;
    private TelaDeLogin telaDeLogin;

    public ControlladorPrincipal() {
        telaPrincipal=new TelaPrincipal();
        
        inicializarComponentes();
    }
    
    
    public void abrirTelaPrincipal(){
        telaPrincipal.setVisible(true);
        
        
    }
    
    public void inicializarComponentes(){
        
         telaPrincipal.btDesconectar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 telaPrincipal.setVisible(false);
                 telaDeLogin.setVisible(true);
                 
             }
        });
         
          
    }
    
    public void limparTelaLogin(){
        telaDeLogin.edSenha.setText("");
        telaDeLogin.edUsuario.setText("");
    }

    }

