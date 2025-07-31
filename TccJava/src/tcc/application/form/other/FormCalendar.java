package tcc.application.form.other;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
//import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import org.bouncycastle.asn1.cms.Time;
import raven.toast.Notifications;
import tcc.application.Application;
import tcc.application.model.Horarios;
import tcc.application.model.Medico;
import tcc.application.model.dao.DaoHorario;

public class FormCalendar extends javax.swing.JPanel {

    private Medico medicoSelecionado;
    private DaoHorario daoH;
    private TimePicker HinicioManha;
    private TimePicker HFimManha;
    private TimePicker HinicioTarde;
    private TimePicker HFimTarde;

    public FormCalendar(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
        initComponents();

        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        daoH = new DaoHorario();
        estilizarcalendario();
        configuraçãoLayout();
        addMouseListenerToCalendarDays(calendarPanel);

    }

    public FormCalendar() {
    }

    public Medico getMedicoSelecionado() {
        return medicoSelecionado;
    }

    public void setMedicoSelecionado(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
    }

    private void addMouseListenerToCalendarDays(CalendarPanel calendarPanel) {

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
                                    LocalDate hoje = LocalDate.now();
                                    LocalDate selectedDate = calendarPanel.getSelectedDate();
                                    if (selectedDate == null || selectedDate.isBefore(hoje)) {
                                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Selecione uma data válida (hoje ou futura).");
                                    } else {
                                        JDialog dialog = new JDialog((JFrame) null, "Editar carga Horária", true);

                                        dialog.setUndecorated(true);
                                        dialog.setOpacity(0.92f);
                                        dialog.setSize(380, 430);
                                        dialog.setLocationRelativeTo(null);
                                        dialog.setLayout(new BorderLayout());

                                        // Topo com título e botão fechar
                                        JPanel topBar = new JPanel(new BorderLayout());
                                        topBar.setOpaque(false);
                                        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

                                        JLabel titulo = new JLabel("Editar carga horária", JLabel.CENTER);
                                        titulo.putClientProperty(FlatClientProperties.STYLE, "foreground:$Login.textColor;font:$h1.font");
                                        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
                                        topBar.add(titulo, BorderLayout.CENTER);

                                        JButton closeBtn = new JButton("X");
                                        closeBtn.setFocusPainted(false);
                                        closeBtn.setMargin(new Insets(2, 8, 2, 8));
                                        closeBtn.setContentAreaFilled(false);
                                        closeBtn.setBorder(BorderFactory.createEmptyBorder());
                                        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        closeBtn.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                dialog.dispose();
                                            }
                                        });
                                        topBar.add(closeBtn, BorderLayout.EAST);

                                        dialog.add(topBar, BorderLayout.NORTH);

                                        // Painel principal
                                        JPanel centerPanel = new JPanel(new GridBagLayout());
                                        centerPanel.setOpaque(false);
                                        GridBagConstraints gbc = new GridBagConstraints();
                                        gbc.gridx = 0;
                                        gbc.gridwidth = 2;
                                        gbc.fill = GridBagConstraints.HORIZONTAL;

                                        gbc.gridy = 0;
                                        gbc.insets = new Insets(0, 20, 10, 20);
                                        JLabel lDataSelecionada = new JLabel("Data Selecionada: " + selectedDate.toString(), JLabel.CENTER);
                                        lDataSelecionada.putClientProperty(FlatClientProperties.STYLE, "foreground:$Login.textColor;font:$h3.font");
                                        centerPanel.add(lDataSelecionada, gbc);

                                        // Label Manhã
                                        gbc.gridy++;
                                        gbc.insets = new Insets(10, 20, 5, 20);
                                        JLabel lmanha = new JLabel("Horário Manhã (Início / Fim):", JLabel.CENTER);
                                        centerPanel.add(lmanha, gbc);

                                        // Campos Manhã
                                        gbc.gridy++;
                                        JPanel manhaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
                                        manhaPanel.setOpaque(false);

                                        // INÍCIO MANHÃ
                                        TimePickerSettings settings = new TimePickerSettings();


                                        
                                        HinicioManha = new TimePicker();
                                        HFimManha = new TimePicker();
                                        HinicioTarde = new TimePicker();
                                        HFimTarde = new TimePicker();

                                        manhaPanel.add(HinicioManha);
                                        manhaPanel.add(HFimManha);
                                        centerPanel.add(manhaPanel, gbc);

                                        // Label Tarde
                                        gbc.gridy++;
                                        JLabel ltarde = new JLabel("Horário Tarde (Início / Fim):", JLabel.CENTER);
                                        centerPanel.add(ltarde, gbc);

                                        // Campos Tarde
                                        gbc.gridy++;
                                        JPanel tardePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
                                        tardePanel.setOpaque(false);

                                        tardePanel.add(HinicioTarde);
                                        tardePanel.add(HFimTarde);
                                        centerPanel.add(tardePanel, gbc);

                                        // Espaço extra antes do botão
                                        gbc.gridy++;
                                        centerPanel.add(Box.createVerticalStrut(20), gbc); // espaço antes do botão

                                        // Botão Cadastrar - mais abaixo
                                        gbc.gridy++;
                                        gbc.insets = new Insets(10, 20, 20, 20); // espaço ao redor do botão
                                        JButton BtCadastrar = new JButton("Cadastrar");
                                        BtCadastrar.setFocusPainted(false);
                                        BtCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        BtCadastrar.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0;focusWidth:0");
                                        BtCadastrar.putClientProperty("JButton.arc", 20);

                                        BtCadastrar.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                //A data selecionada é uma variavel selectedDate

                                                //Manha
                                                LocalTime horarioInicioManha = HinicioManha.getTime();
                                                LocalTime horarioFimManha = HFimManha.getTime();
                                                //Tarde
                                                LocalTime horarioInicioTarde = HinicioTarde.getTime();
                                                LocalTime horarioFimTarde = HFimTarde.getTime();

                                                if (horarioInicioManha == null || horarioInicioManha == null
                                                        || horarioInicioTarde == null || horarioFimTarde == null) {
                                                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Você deixou algum campo em branco.");
                                                } else {
                                                    System.out.println();
                                                    Horarios r = new Horarios(selectedDate, horarioInicioManha, horarioFimManha, horarioInicioTarde, horarioFimTarde, medicoSelecionado);
                                                    // Verifica se já existe cadastro

                                                    Horarios horarioSelecionado = daoH.selecionar(selectedDate);
                                                    if (horarioSelecionado == null) {
                                                        if (daoH.inserir(r)) {
                                                            Notifications.getInstance().show(
                                                                    Notifications.Type.SUCCESS,
                                                                    Notifications.Location.TOP_CENTER,
                                                                    "Horário cadastrado com sucesso."
                                                            );
                                                        } else {
                                                            Notifications.getInstance().show(
                                                                    Notifications.Type.ERROR,
                                                                    Notifications.Location.TOP_CENTER,
                                                                    "Erro ao cadastrar horário."
                                                            );
                                                        }
                                                    } else {
                                                        Notifications.getInstance().show(
                                                                Notifications.Type.SUCCESS,
                                                                Notifications.Location.TOP_CENTER,
                                                                "Já existe um cadastro para essa data,Por isso ela foi editada."
                                                        );
                                                        System.out.println("Já existe? " + daoH.existeCadastroParaData(selectedDate));

                                                        horarioSelecionado.setInicioManha(horarioInicioManha);
                                                        horarioSelecionado.setFimManha(horarioFimManha);
                                                        horarioSelecionado.setInicioTarde(horarioInicioTarde);
                                                        horarioSelecionado.setFimTarde(horarioFimTarde);

                                                        daoH.editar(horarioSelecionado);
                                                        return;
                                                    }

                                                }

                                            }

                                        });

                                        JPanel botaoPanel = new JPanel();
                                        botaoPanel.setOpaque(false);
                                        botaoPanel.add(BtCadastrar);

                                        centerPanel.add(botaoPanel, gbc);

                                        dialog.add(centerPanel, BorderLayout.CENTER);
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
                .addContainerGap(101, Short.MAX_VALUE))
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
                .addContainerGap(397, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addGap(48, 48, 48)
                .addComponent(PainelCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCalendario;
    public com.github.lgooddatepicker.components.CalendarPanel calendarPanel;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
