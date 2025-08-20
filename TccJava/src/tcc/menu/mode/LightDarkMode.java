package tcc.menu.mode;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.text.Normalizer.Form;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
//  import jdk.internal.org.jline.utils.Display;
import tcc.application.Application;
import tcc.application.form.ControllerPrincipal;
import tcc.application.form.other.DefaultForm;
import tcc.application.form.other.FormCalendar;
import tcc.application.form.other.FormMainMenu;
import tcc.application.model.Medico;
import tcc.menu.Menu;

public class LightDarkMode extends JPanel {

    private tcc.menu.Menu menu;
    private tcc.application.form.other.FormCalendar calendar;
    private tcc.application.form.other.FormMainMenu fmm;
    private tcc.application.form.ControllerPrincipal app;
    private tcc.application.form.other.DefaultForm def;
    private Medico medico;

    public LightDarkMode(Medico medico) {
        this.medico = medico;
        calendar = new FormCalendar();
        def=new DefaultForm("Atualizando");
        init();

    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        if (menuFull) {
            buttonLight.setVisible(true);
            buttonDark.setVisible(true);
            buttonLighDark.setVisible(false);
        } else {
            buttonLight.setVisible(false);
            buttonDark.setVisible(false);
            buttonLighDark.setVisible(true);
        }
    }

    private boolean menuFull = true;

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public boolean themeActive() {
        boolean isDark = FlatLaf.isLafDark();
        if (isDark) {
            return true;
        } else {
            return false;
        }

    }
//criar um me4todo atualiarComponente
    private void init() {
        setBorder(new EmptyBorder(2, 2, 2, 2));
        setLayout(new LightDarkModeLayout());
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;"
                + "background:$Menu.lightdark.background");
        buttonLight = new JButton("Claro", new FlatSVGIcon("tcc/menu/mode/light.svg"));
        buttonDark = new JButton("Escuro", new FlatSVGIcon("tcc/menu/mode/dark.svg"));
        buttonLighDark = new JButton();
        buttonLighDark.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:999;"
                + "background:$Menu.lightdark.button.background;"
                + "foreground:$Menu.foreground;"
                + "focusWidth:0;"
                + "borderWidth:0;"
                + "innerFocusWidth:0");
        buttonLighDark.addActionListener((ActionEvent e) -> {
            changeMode(!FlatLaf.isLafDark());

        });
        checkStyle();
        buttonDark.addActionListener((ActionEvent e) -> {
            changeMode(true);

        });
        buttonLight.addActionListener((ActionEvent e) -> {
            changeMode(false);

        });

        add(buttonLight);
        add(buttonDark);
        add(buttonLighDark);

    }

    private int change = 0;

    private void changeMode(boolean dark) {

        if (FlatLaf.isLafDark() != dark) {

            if (dark) {

                EventQueue.invokeLater(() -> {

                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacDarkLaf.setup();
                    FlatLaf.updateUI();
                    checkStyle();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    Medico m = this.getMedico();
                    if (app.getI() == 2) {
                        Application.atualizarComponentes();
                       

                    } else if (app.getI() == 3) {
                        
                        Application.showForm(new FormCalendar(m));

                    }

                });

            } else {

                EventQueue.invokeLater(() -> {

                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacLightLaf.setup();
                    FlatLaf.updateUI();
                    checkStyle();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    System.out.println("Valor de I: " + app.getI());
                    Medico m = this.getMedico();
                    if (app.getI() == 2) {
                        Application.atualizarComponentes();
                       
                     

                    } else if (app.getI() == 3) {
                        Application.showForm(new FormCalendar(m));

                    }

                    menu.header.setIcon(new ImageIcon(getClass().getResource("/tcc/icon/png/logoWhite.png")));
                });
            }

        }

    }

    private void checkStyle() {
        boolean isDark = FlatLaf.isLafDark();
        addStyle(buttonLight, !isDark);
        addStyle(buttonDark, isDark);
        if (isDark) {
            buttonLighDark.setIcon(new FlatSVGIcon("tcc/menu/mode/dark.svg"));
        } else {
            buttonLighDark.setIcon(new FlatSVGIcon("tcc/menu/mode/light.svg"));
        }
    }

    private void addStyle(JButton button, boolean style) {
        if (style) {

            button.putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc:999;"
                    + "background:$Menu.lightdark.button.background;"
                    + "foreground:$Menu.foreground;"
                    + "focusWidth:0;"
                    + "borderWidth:0;"
                    + "innerFocusWidth:0");
        } else {

            button.putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc:999;"
                    + "background:$Menu.lightdark.button.background;"
                    + "foreground:$Menu.foreground;"
                    + "focusWidth:0;"
                    + "borderWidth:0;"
                    + "innerFocusWidth:0;"
                    + "background:null");
        }
    }

    private JButton buttonLight;
    private JButton buttonDark;
    private JButton buttonLighDark;

    private class LightDarkModeLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, buttonDark.getPreferredSize().height + (menuFull ? 0 : 5));
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int gap = 5;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int buttonWidth = (width - gap) / 2;
                if (menuFull) {
                    buttonLight.setBounds(x, y, buttonWidth, height);
                    buttonDark.setBounds(x + buttonWidth + gap, y, buttonWidth, height);
                } else {
                    buttonLighDark.setBounds(x, y, width, height);
                }
            }
        }
    }
}
