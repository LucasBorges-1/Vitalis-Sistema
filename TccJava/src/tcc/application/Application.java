package tcc.application;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import tcc.application.form.ControllerPrincipal;
import tcc.application.form.LoginMedicoForm;

import raven.toast.Notifications;
import tcc.application.form.FormManager;
import tcc.application.form.LoginClinicaForm;
import tcc.application.model.Clinica;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;
import tcc.application.model.dao.BCryptUtil;
import tcc.application.model.dao.DaoClinica;
import tcc.application.model.dao.DaoPessoa;

public class Application extends javax.swing.JFrame {

    public static Application app;
    private static ControllerPrincipal mainForm;
    private final LoginMedicoForm loginForm;
    private LoginClinicaForm loginclinicaForm;
    private FormManager formManager;
    private static LoginMedicoForm loginMedicoForm;
    private tcc.application.model.dao.BCryptUtil bCrypt;
    private DaoClinica daoClinica;
    private static DaoPessoa daoPessoa;

    private static int isOpen;
    private static Medico medico;

    public Application() {
        initComponents();
        
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        daoPessoa = new DaoPessoa();

        loginForm = new LoginMedicoForm();
        loginclinicaForm = new LoginClinicaForm();
        formManager = new FormManager();
        loginMedicoForm = new LoginMedicoForm();
        
        daoClinica = new DaoClinica();


        setContentPane(loginForm);
         
        revalidate();
        repaint();
        
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        Notifications.getInstance().setJFrame(this);

    }

    public LoginMedicoForm getLoginMedicoForm() {
        return loginMedicoForm;
    }

    public void setLoginMedicoForm(LoginMedicoForm loginMedicoForm) {
        this.loginMedicoForm = loginMedicoForm;
    }

    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }

    public static void login(String crm) {
       
        medico = daoPessoa.buscarMedicoPorCrm(crm);
        mainForm = new ControllerPrincipal(medico);
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainForm.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();

    }

    public static void OpenloginClinica() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginclinicaForm);
        app.loginclinicaForm.applyComponentOrientation(app.getComponentOrientation());
        //setSelectedMenu(0, 0);

        SwingUtilities.updateComponentTreeUI(app.loginclinicaForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void OpenClinicaManeger() {
        isOpen = 1;
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.formManager);
        app.formManager.applyComponentOrientation(app.getComponentOrientation());
        //setSelectedMenu(0, 0);;
        SwingUtilities.updateComponentTreeUI(app.formManager);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();

        app.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        app.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (app.formManager.isVisible()) {
                    int option = JOptionPane.showConfirmDialog(
                            app,
                            "Deseja realmente fechar o formulário?",
                            "Fechar Formulário",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        Application.voltar();
                        isOpen = 0;
                    } else {
                        app.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    }

                }
            }

        });
    }

    public static void logout() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void voltar() {
        if (isOpen == 0) {
            System.exit(0);
        } else {

            FlatAnimatedLafChange.showSnapshot();
            app.setContentPane(app.loginForm);
            app.loginForm.applyComponentOrientation(app.getComponentOrientation());
            SwingUtilities.updateComponentTreeUI(app.loginForm);
            FlatAnimatedLafChange.hideSnapshotWithAnimation();

        }

    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("tcc.theme");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            app.setVisible(true);
            
            
            /*
            BCryptUtil bc=new BCryptUtil();
            DaoClinica daoC=new DaoClinica();
            String senha = "clinica";
            
            Clinica c = new Clinica("11", "clinica", "asd", "asd",bc.hashSenha(senha));
            daoC.inserir(c);
             */

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
