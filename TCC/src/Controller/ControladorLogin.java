/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Wiew.TelaDeLogin;
import Wiew.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author User
 */
public class ControladorLogin {

    private TelaDeLogin telaDeLogin;
    private TelaPrincipal telaPrincipal;
    private ControlladorPrincipal controlladorPrincipal;

    public ControladorLogin() {
        telaDeLogin = new TelaDeLogin(null, true);
        telaPrincipal = new TelaPrincipal();
        controlladorPrincipal = new ControlladorPrincipal();
        inicializarComponentes();
    }

    public void inicializarComponentes() {

        telaDeLogin.btEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               telaPrincipal.edName.setText(telaDeLogin.edUsuario.getText());
               telaDeLogin.setVisible(false);
               abrirTelaPrincipal();
               

            }
        });

    }

    public void abrirTelaPrincipal() {
        controlladorPrincipal.abrirTelaPrincipal();

    }

    public void executar() {
        telaDeLogin.setVisible(true);
    }

}
