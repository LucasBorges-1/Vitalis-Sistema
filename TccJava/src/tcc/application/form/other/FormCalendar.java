package tcc.application.form.other;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
//import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import tcc.application.Application;

public class FormCalendar extends javax.swing.JPanel {


    public FormCalendar() {

        initComponents();

        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        estilizarcalendario();
        configuraçãoLayout();
        addMouseListenerToCalendarDays(calendarPanel);
      
    }

    private static void addMouseListenerToCalendarDays(CalendarPanel calendarPanel) {
       
        for (Component component : calendarPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel monthViewPanel = (JPanel) component;
                for (Component dayComponent : monthViewPanel.getComponents()) {
                    if (dayComponent instanceof JLabel) {
                        JLabel dayLabel = (JLabel) dayComponent;
                        dayLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getClickCount() == 2) {
                                    
                                    LocalDate selectedDate = calendarPanel.getSelectedDate();
                                    if (selectedDate != null) {
                                        
                                        JDialog dialog = new JDialog(); 
                                        dialog.setName("Data selecionada");
                                        dialog.setSize(300, 150);

                                        
                                        JLabel label = new JLabel("Data selecionada: " + selectedDate.toString());
                                        label.setHorizontalAlignment(JLabel.CENTER);
                                        dialog.add(label, BorderLayout.CENTER);

                                      
                                      

                                        dialog.setVisible(true);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private void configuraçãoLayout() {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lb, BorderLayout.NORTH);
        
        PainelCalendario.setLayout(new BorderLayout());
        PainelCalendario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        PainelCalendario.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        // + "margin:20,20,20,20;"
        );

        PainelCalendario.add(calendarPanel, BorderLayout.CENTER);

        // Criando o PainelEdicao
        add(PainelCalendario, BorderLayout.CENTER);

    }

    public void estilizarcalendario() {

        add(calendarPanel);

        DatePickerSettings settings = new DatePickerSettings();

        settings.setFormatForDatesCommonEra("dd/MM/yyyy");

        Color background = new Color(255, 255, 255);
        Color text = Color.GRAY;

        Color background2 = new Color(59, 75, 89);
        Color text2 = Color.lightGray;

        Font customFont = new Font("SansSerif", Font.PLAIN, 15);

        settings.setFontCalendarDateLabels(customFont); 
        settings.setFontCalendarWeekdayLabels(customFont); 
        settings.setFontCalendarWeekNumberLabels(customFont); 
        settings.setFontMonthAndYearMenuLabels(customFont);
        settings.setFontTodayLabel(customFont); 
        settings.setFontClearLabel(customFont);

        if (FlatLaf.isLafDark()) {

            settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, background2);
            settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, background2);
            settings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates, text2);
            settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, background2);
            settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels, text2);
            settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, background2);
            settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearNavigationButtons, text2);
            settings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, text2);
            settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, background2);
            settings.setColor(DatePickerSettings.DateArea.TextTodayLabel, text2);
            settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, background2);
            settings.setColor(DatePickerSettings.DateArea.TextClearLabel, text2);
            settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, background2);
            settings.setColor(DatePickerSettings.DateArea.TextCalendarPanelLabelsOnHover, text2);
            settings.setColorBackgroundWeekdayLabels(background2, true);

            CalendarBorderProperties normalDayBorder = new CalendarBorderProperties(
                    new Point(1, 1),
                    new Point(5, 5), 
                    text2,
                    1 
            );

            settings.getBorderPropertiesList().add(normalDayBorder);

            settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, background2);
            settings.setColor(DatePickerSettings.DateArea.CalendarBorderSelectedDate, text2);

        } else {
            settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, background);
            settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, background);
            settings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates, text);
            settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, background);
            settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels, text);
            settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, background);
            settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearNavigationButtons, text);
            settings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, text);
            settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, background);
            settings.setColor(DatePickerSettings.DateArea.TextTodayLabel, text);
            settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, background);
            settings.setColor(DatePickerSettings.DateArea.TextClearLabel, text);
            settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, background);
            settings.setColor(DatePickerSettings.DateArea.TextCalendarPanelLabelsOnHover, text);
            settings.setColorBackgroundWeekdayLabels(background, true);
            CalendarBorderProperties normalDayBorder = new CalendarBorderProperties(
                    new Point(1, 1),
                    new Point(5, 5),
                    text, 
                    1 
            );
            settings.getBorderPropertiesList().add(normalDayBorder);
            settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, background);
            settings.setColor(DatePickerSettings.DateArea.CalendarBorderSelectedDate, text);

        }

        PainelCalendario.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                Dimension novoTamanho = PainelCalendario.getSize();
                DatePickerSettings settings = calendarPanel.getSettings();
                settings.setSizeDatePanelMinimumHeight(novoTamanho.height - 100);
                settings.setSizeDatePanelMinimumWidth(novoTamanho.width - 30);
                calendarPanel.drawCalendar();
            }
        });

        calendarPanel.setSettings(settings);
        calendarPanel.revalidate();
        calendarPanel.repaint();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        PainelCalendario = new javax.swing.JPanel();
        calendarPanel = new com.github.lgooddatepicker.components.CalendarPanel();
        PainelEdicao = new javax.swing.JPanel();
        lhim = new javax.swing.JLabel();
        ComboboxInicioManha = new javax.swing.JComboBox<>();
        ComboboxFimManha = new javax.swing.JComboBox<>();
        lhfm = new javax.swing.JLabel();
        lhit = new javax.swing.JLabel();
        ComboboxinicioTarde = new javax.swing.JComboBox<>();
        Comboboxfimtarde = new javax.swing.JComboBox<>();
        lhft = new javax.swing.JLabel();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Calendario");

        javax.swing.GroupLayout PainelCalendarioLayout = new javax.swing.GroupLayout(PainelCalendario);
        PainelCalendario.setLayout(PainelCalendarioLayout);
        PainelCalendarioLayout.setHorizontalGroup(
            PainelCalendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelCalendarioLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PainelCalendarioLayout.setVerticalGroup(
            PainelCalendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelCalendarioLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lhim.setText("Hora de inicio da manhã");

        ComboboxInicioManha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboboxFimManha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lhfm.setText("Hora de término da manhã");

        lhit.setText("Hora de inicio da tarde");

        ComboboxinicioTarde.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Comboboxfimtarde.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lhft.setText("Hora de término da tarde");

        javax.swing.GroupLayout PainelEdicaoLayout = new javax.swing.GroupLayout(PainelEdicao);
        PainelEdicao.setLayout(PainelEdicaoLayout);
        PainelEdicaoLayout.setHorizontalGroup(
            PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelEdicaoLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lhft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Comboboxfimtarde, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lhit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComboboxinicioTarde, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lhfm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComboboxFimManha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lhim, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboboxInicioManha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        PainelEdicaoLayout.setVerticalGroup(
            PainelEdicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelEdicaoLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(lhim)
                .addGap(18, 18, 18)
                .addComponent(ComboboxInicioManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lhfm)
                .addGap(18, 18, 18)
                .addComponent(ComboboxFimManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lhit)
                .addGap(18, 18, 18)
                .addComponent(ComboboxinicioTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lhft, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Comboboxfimtarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(PainelCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(PainelEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PainelCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelEdicao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboboxFimManha;
    private javax.swing.JComboBox<String> ComboboxInicioManha;
    private javax.swing.JComboBox<String> Comboboxfimtarde;
    private javax.swing.JComboBox<String> ComboboxinicioTarde;
    private javax.swing.JPanel PainelCalendario;
    private javax.swing.JPanel PainelEdicao;
    public com.github.lgooddatepicker.components.CalendarPanel calendarPanel;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lhfm;
    private javax.swing.JLabel lhft;
    private javax.swing.JLabel lhim;
    private javax.swing.JLabel lhit;
    // End of variables declaration//GEN-END:variables
}
