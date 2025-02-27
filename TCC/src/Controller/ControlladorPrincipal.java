/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Wiew.TelaDeLogin;
import Wiew.TelaDeRelatorio;
import Wiew.TelaPrincipal;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControlladorPrincipal {
    private TelaPrincipal telaPrincipal;
    private TelaDeLogin telaDeLogin;
    private TelaDeRelatorio telaDeRelatorio;
    private ControladorLogin controladorLogin;
  
    private GerenciadorDeDocumentos gdd;
    
    
    public ControlladorPrincipal() {
        telaPrincipal=new TelaPrincipal();
        telaDeLogin=new TelaDeLogin(telaPrincipal, true);
        gdd=new GerenciadorDeDocumentos();
        telaDeRelatorio=new TelaDeRelatorio(telaPrincipal, true);
       
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
         
         telaDeLogin.btEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
               telaDeLogin.setVisible(false);
               abrirTelaPrincipal();
               

            }
        });
         
          telaPrincipal.BtAbrirTelaDeRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
               abrirTelaDeRelatorio();
               

            }
        });
         
          
          
          
    }
    
    
    
    
    public void limparTelaLogin(){
        telaDeLogin.edSenha.setText("");
        telaDeLogin.edUsuario.setText("");
    }
    
    public void abrirTelaDeRelatorio(){
        gdd.abrirRelatorio();
    }
    
    
   

    }

