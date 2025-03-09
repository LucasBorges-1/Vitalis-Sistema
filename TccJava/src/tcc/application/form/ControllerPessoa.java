/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tcc.application.Application;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;

/**
 *
 * @author Borges
 */
public class ControllerPessoa {
    private Pessoa pessoa;
    private Medico medico;
    private FormManager formManager;
    private LoginMedicoForm loginMedicoForm;
    private LoginClinicaForm loginClinicaForm;
    private Application app;
    
    public ControllerPessoa() {
        pessoa=new Pessoa();
        medico=new Medico();
        formManager=new FormManager();
        loginMedicoForm =new LoginMedicoForm();
        loginClinicaForm =new LoginClinicaForm();
        app=new Application();
        init();
    }
    
    
    public void init(){
     loginMedicoForm.btManegerArea.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
           showMedicoForm();
         }
     });
     
     
    
    }
    
     public void showMedicoForm(){
         app.showForm(loginClinicaForm);
     }
    
    
    
    
}
