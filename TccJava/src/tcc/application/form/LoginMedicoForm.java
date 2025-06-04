package tcc.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import tcc.application.Application;

public class LoginMedicoForm extends javax.swing.JPanel {

    ControllerPessoa cp;

    public LoginMedicoForm() {
        initComponents();
        init();
        
    }

    private void init() {
        System.out.println();
        setLayout(new MigLayout("al center center"));
        
        btManegerArea.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h4.font");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "informe seu Crm");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "informe sua Senha");
        
        
        //Botão de login
        cmdLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmdLogin.setText("Entrando...");

                cp = new ControllerPessoa();
                char[] senhaChars = txtPass.getPassword();
                String senhaDigitada = new String(senhaChars);

                if (cp.verificarLoginMedico(txtUser.getText(), senhaDigitada)) {
                    
                    Application.login(txtUser.getText());

                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "CRM ou senha incorretos." );
                   
                   
                }
                cmdLogin.setText("Login");

            }
        });
    }
    
    public String pegarCrm(){
        if (txtUser.getText()!=null) {
            return txtUser.getText();
        }else{
            return "nuloa"; 
        }
        
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new tcc.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();
        btManegerArea = new javax.swing.JButton();

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Bem-vindo !");
        panelLogin1.add(lbTitle);

        lbUser.setText("Crm");
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setText("Senha");
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        cmdLogin.setText("Login");
        panelLogin1.add(cmdLogin);

        btManegerArea.setText("                                            Arêa do colaborador");
        btManegerArea.setBorder(null);
        btManegerArea.setBorderPainted(false);
        btManegerArea.setContentAreaFilled(false);
        btManegerArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btManegerAreaActionPerformed(evt);
            }
        });
        panelLogin1.add(btManegerArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btManegerAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btManegerAreaActionPerformed
        Application.OpenloginClinica();
    }//GEN-LAST:event_btManegerAreaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btManegerArea;
    private javax.swing.JButton cmdLogin;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private tcc.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    public javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
