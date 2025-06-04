package tcc.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.net.URI;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import tcc.application.Application;
import tcc.application.form.other.FormCalendar;
import tcc.application.form.other.FormMainMenu;
import tcc.application.form.other.FormImpressão;
import tcc.application.form.other.FormHistorico;
import tcc.application.form.other.FormHorarios;

import tcc.application.form.other.FormPendentes;
import tcc.application.model.Medico;
import tcc.application.model.dao.DaoPessoa;
import tcc.menu.Menu;
import tcc.menu.MenuAction;

public class ControllerPrincipal extends JLayeredPane {

    private Medico medico;
    private DaoPessoa daoPessoa;

    public ControllerPrincipal(Medico medico) {

        daoPessoa = new DaoPessoa();
        this.medico = medico;

        init();
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    private static int i = 0;

    public static int getI() {
        return i;
    }

    public static void setI(int value) {
        i = value;
    }

    private void init() {
        System.out.println(this.getMedico().toString());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());

        String nomeDoMedico = this.getMedico().getNome();
        menu = new Menu();
        
        menu.header.setText("Dr(a) "+nomeDoMedico);
    
        panelBody = new JPanel(new BorderLayout());

        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            menu.header.setText("Dr(a) "+nomeDoMedico);
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("tcc/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            // Application.mainForm.showForm(new DefaultForm("Form : " + index + " " + subIndex));
            if (index == 0) {
                Application.showForm(new FormMainMenu());
            } else if (index == 1) {
                if (subIndex == 1) {
                    Application.showForm(new FormHistorico());
                } else if (subIndex == 2) {
                    Application.showForm(new FormPendentes());
                } else {
                    action.cancel();
                }
            } else if (index == 2) {
                Application.showForm(new FormImpressão());
            } else if (index == 3) {

                if (subIndex == 1) {
                    Application.showForm(new FormHorarios());
                } else if (subIndex == 2) {
                    ControllerPrincipal.setI(3);
                    Application.showForm(new FormCalendar());
                } else {
                    action.cancel();
                }

            } else if (index == 4) {
                if (subIndex == 1) {

                    try {
                        URI email = new URI("https://workspace.google.com/intl/pt-BR/gmail/");
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(email);
                        } else {
                            System.out.println("A funcionalidade de abrir navegador não é suportada neste sistema.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (subIndex == 2) {

                    try {
                        URI email = new URI("https://workspace.google.com/intl/pt-BR/gmail/");
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(email);
                        } else {
                            System.out.println("A funcionalidade de abrir navegador não é suportada neste sistema.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (subIndex == 3) {
                    try {
                        URI drive = new URI("https://drive.google.com/");
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(drive);
                        } else {
                            System.out.println("A funcionalidade de abrir navegador não é suportada neste sistema.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    action.cancel();
                }
            } else if (index == 5) {
                Application.logout();
            } else {
                action.cancel();
            }
        });
    }

    public int CalendarIsOpen() {
        if (i == 3) {
            return 3;
        } else {
            return 0;
        }
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("tcc/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
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
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}
