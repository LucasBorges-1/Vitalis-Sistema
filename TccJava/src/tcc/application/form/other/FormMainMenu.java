package tcc.application.form.other;

import tcc.application.form.other.model.ModelConsultas;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import raven.toast.Notifications;
import tcc.application.form.ControllerPrincipal;
import tcc.application.form.LoginMedicoForm;
import tcc.application.model.Consulta;
import tcc.application.model.Medico;
import tcc.application.model.dao.DaoConsulta;
import tcc.application.model.dao.DaoPessoa;

public class FormMainMenu extends javax.swing.JPanel {

    private tcc.application.form.ControllerPrincipal app;
    private Medico medicoSelecionado;
    private ModelConsultas model;
    private DaoConsulta daoConsulta;
    private DaoPessoa daoPessoa;

    private JLabel labelAgendadas;
    private JLabel labelRealizadas;

    private int contRow = 0;
    private int contFinish = 0;

    public FormMainMenu(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        daoConsulta = new DaoConsulta();
        model = new ModelConsultas();

        //Configs
        gerenciandoTabela();
        contadores();
        configurarLayout();
        estiloTabela();

        buscar();
        app.setI(0);
        //

    }

    public void contadores() {
        labelAgendadas = new JLabel("Agendadas para hoje: " + contConsultasHoje());
        labelRealizadas = new JLabel("Já realizadas: " + contFinish);

    }

    public Medico getMedicoSelecionado() {
        return medicoSelecionado;
    }

    public void setMedicoSelecionado(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel(new GridBagLayout());
        JPanel painelInferior = new JPanel(new BorderLayout());

        JPanel painelEsquerda = new JPanel(new GridLayout(2, 1, 0, 10));
        painelEsquerda.setPreferredSize(new Dimension(0, 0));

        // Painel "AGENDADAS"
        JPanel painelLinhas = new JPanel(new GridBagLayout());
        painelLinhas.setName("painelLinhas");
        painelLinhas.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        labelAgendadas.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        labelAgendadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelAgendadas.setVerticalAlignment(SwingConstants.CENTER);
        painelLinhas.add(labelAgendadas);
        painelLinhas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background");
        painelEsquerda.add(painelLinhas);

        // Painel "REALIZADAS"
        JPanel painelColunas = new JPanel(new GridBagLayout());
        painelColunas.setName("painelColunas");
        painelColunas.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        labelRealizadas.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        labelRealizadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelRealizadas.setVerticalAlignment(SwingConstants.CENTER);
        painelColunas.add(labelRealizadas);
        painelColunas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background");
        painelEsquerda.add(painelColunas);

        JPanel painelDireita = new JPanel();
        ChartPanel chartPanel = criarGrafico();

        if (dadosGrafico()[0] <= 0) {
            JLabel dadosNull = new JLabel("Nenhum dados para exibir em grafico");
            dadosNull.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
            painelDireita.setLayout(new BorderLayout());
            dadosNull.setHorizontalAlignment(SwingConstants.CENTER);
            painelDireita.add(dadosNull, BorderLayout.CENTER);
        } else {
            chartPanel.setPreferredSize(new Dimension(painelDireita.getWidth(), painelDireita.getHeight() - 15));
            painelDireita.setLayout(new BorderLayout());
            painelDireita.add(chartPanel, BorderLayout.CENTER);
        }

        JLabel info = new JLabel("Total: " + dadosGrafico()[0]
                + " Concluídas: " + dadosGrafico()[2]
                + " Canceladas: " + dadosGrafico()[1]);
        info.putClientProperty(FlatClientProperties.STYLE, "font:$h2.font");
        info.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel painelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelInfo.setOpaque(false); // para herdar a cor de fundo
        painelInfo.add(info);

        painelDireita.add(painelInfo, BorderLayout.SOUTH);
        painelDireita.setPreferredSize(new Dimension(0, 0));
        painelDireita.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelDireita.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.weightx = 0.45;
        painelEsquerda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelSuperior.add(painelEsquerda, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.55;
        painelSuperior.add(painelDireita, gbc);

        panelTable.setLayout(new BorderLayout());
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE - 150, txtSearch.getPreferredSize().height));
        panelTable.add(txtSearch, BorderLayout.NORTH);

        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        panelTable.add(jScrollPane1, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelRodape.setOpaque(false);
        painelRodape.putClientProperty(FlatClientProperties.STYLE, "background:$Login.background;");
        estilizarBotaoFundoTabela(cmdC);
        estilizarBotaoFundoTabela(cmdX);
        painelRodape.add(cmdX);
        painelRodape.add(cmdC);
        panelTable.add(painelRodape, BorderLayout.SOUTH);

        painelInferior.add(panelTable, BorderLayout.CENTER);
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCentral = new JPanel(new GridLayout(2, 1));
        painelCentral.add(painelSuperior);
        painelCentral.add(painelInferior);

        add(lb, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MainTable = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        cmdX = new ActionButton();
        cmdC =  new ActionButton();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Menu Principal");

        jButton1.setText("Show Notifications Test");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        MainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Tipo", "Horário", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MainTable.setRowHeight(60);
        jScrollPane1.setViewportView(MainTable);
        if (MainTable.getColumnModel().getColumnCount() > 0) {
            MainTable.getColumnModel().getColumn(0).setResizable(false);
            MainTable.getColumnModel().getColumn(1).setResizable(false);
            MainTable.getColumnModel().getColumn(2).setResizable(false);
            MainTable.getColumnModel().getColumn(3).setMinWidth(70);
            MainTable.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        cmdX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tcc/icon/png/expirado.png"))); // NOI18N
        cmdX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdXMouseExited(evt);
            }
        });
        cmdX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdXActionPerformed(evt);
            }
        });

        cmdC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tcc/icon/png/verificar.png"))); // NOI18N
        cmdC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdCMouseExited(evt);
            }
        });
        cmdC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addGroup(panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelTableLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSearch)
                        .addGap(87, 87, 87))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdC)
                .addGap(35, 35, 35))
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTableLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(cmdC))
                    .addGroup(panelTableLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdX))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void concluirConsulta(int indice) {
        if (indice >= 0) {
            Consulta c = model.pegarConsulta(indice);
            c.setEstado("CONCLUIDA");
            String paciente = c.getUsuario().getNome();
            if (daoConsulta.editar(c)) {
                model.removerDaTabela(indice);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                        "Consulta do paciente " + paciente + ",Concluida com sucesso.");
                contFinish += 1;
                labelRealizadas.setText("Já realizadas: " + contFinish);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Não foi possivel concluir a consulta do paciente " + paciente);
            }

        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Por favor,selecione uma ceélula para concluir");

        }
    }

    public void cancelarConsulta(int indice) {
        ModelConsultas model = (ModelConsultas) MainTable.getModel();

        if (indice >= 0) {
            Consulta c = model.pegarConsulta(indice);
            c.setEstado("CANCELADA");
            String paciente = c.getUsuario().getNome();
            if (daoConsulta.editar(c)) {
                model.removerDaTabela(indice);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                        "Consulta do paciente " + paciente + ",suspensa com sucesso.");
                contFinish += 1;
                labelRealizadas.setText("Já realizadas: " + contFinish);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Não foi possivel suspender a consulta do paciente. " + paciente);
            }

        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Por favor,selecione uma célula para suspender.");

        }
    }

    public void actionTableForm(Consulta c) {
        JDialog dialog = new JDialog((JFrame) null, "Consultando Paciente", true);
        dialog.setUndecorated(true);
        dialog.setOpacity(0.92f);
        dialog.setSize(450, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        // Topo com título e botão X
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("CONSULTANDO O PACIENTE \"Nome\"", JLabel.CENTER);
        titulo.putClientProperty(FlatClientProperties.STYLE, "foreground:$Login.textColor;font:$h2.font");
        topBar.add(titulo, BorderLayout.CENTER);

        JButton closeBtn = new JButton("X");
        closeBtn.setFocusPainted(false);
        closeBtn.putClientProperty(FlatClientProperties.STYLE, "foreground:$Login.textColor;font:$h2.font");
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorder(BorderFactory.createEmptyBorder());
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dialog.dispose());
        topBar.add(closeBtn, BorderLayout.EAST);

        dialog.add(topBar, BorderLayout.NORTH);

        // Painel principal
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Imagem do paciente (círculo)
        gbc.gridy = 0;
        JLabel imagemPaciente = new JLabel("", JLabel.CENTER);

        imagemPaciente.setPreferredSize(new Dimension(128, 128));
        imagemPaciente.setIcon(new ImageIcon(getClass().getResource("/tcc/icon/png/paciente128.png")));
        imagemPaciente.setHorizontalTextPosition(SwingConstants.CENTER);
        imagemPaciente.setVerticalTextPosition(SwingConstants.CENTER);
        centerPanel.add(imagemPaciente, gbc);

        // Espaço
        gbc.gridy++;
        centerPanel.add(Box.createVerticalStrut(20), gbc);

        // Campo de descrição
        gbc.gridy++;
        JTextArea descricao = new JTextArea("Descrição da consulta");
        descricao.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Insira a descrição");
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setRows(6);

        JScrollPane scroll = new JScrollPane(descricao);
        scroll.setPreferredSize(new Dimension(400, 220));
        centerPanel.add(scroll, gbc);

        // Espaço
        gbc.gridy++;
        centerPanel.add(Box.createVerticalStrut(30), gbc);

        // Botões
        gbc.gridy++;
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        JButton finalizar = new JButton("Finalizar");
        JButton suspender = new JButton("Suspender");

        configurarBotao(finalizar,this);
        configurarBotao(suspender,this);

        botoes.add(finalizar);
        botoes.add(suspender);
        botoes.setOpaque(false);
        centerPanel.add(botoes, gbc);

        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private static void configurarBotao(JButton botao, JPanel jp) {
        botao.setFocusPainted(false);

        botao.putClientProperty(FlatClientProperties.STYLE, "foreground:$Login.textColor;font:$h3.font");

        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setContentAreaFilled(false);
        botao.setPreferredSize(new Dimension(120, 40));

        // Efeito de hover com base no background do painel pai
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setOpaque(false);
                Color bgColor = jp.getBackground();
                Color hoverColor = bgColor.darker();
                botao.setForeground(hoverColor);
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setOpaque(true);
                Color fg=jp.getForeground();
                Color bgColor = jp.getBackground();
                Color hoverColor = bgColor.darker();
                botao.setForeground(bgColor);
                botao.setForeground(fg);
            }
        });
    }

    public void gerenciandoTabela() {
        MainTable.setModel(model);
        MainTable.getTableHeader().setReorderingAllowed(false);
        //

        for (int i = 0; i < MainTable.getColumnCount(); i++) {
            MainTable.getColumnModel().getColumn(i).setResizable(false);
        }

        MainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carregarConsultas();
        Font fonteTabela = new Font("Segoe UI", Font.PLAIN, 14);
        MainTable.setFont(fonteTabela);
        MainTable.getTableHeader().setFont(fonteTabela);
        MainTable.setRowHeight(28);
        DefaultTableCellRenderer centroRenderer = new DefaultTableCellRenderer();
        centroRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        MainTable.getColumn("Nome").setCellRenderer(centroRenderer);
        MainTable.getColumn("Tipo").setCellRenderer(centroRenderer);
        MainTable.getColumn("Horário").setCellRenderer(centroRenderer);
        // labelAgendadas.setText("Agendadas para hoje: " + model());
        estilizarBotaoFundoTabela(cmdC);
        estilizarBotaoFundoTabela(cmdX);

    }

    private void estilizarBotaoFundoTabela(JButton botao) {
        botao.getParent().setBackground(null);
        cmdC.getParent().setForeground(panelTable.getBackground());
        botao.setOpaque(true);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);

        botao.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "foreground:$Menu.title.foreground"
                + "arc:999;"
        );
        Color bgColor = MainTable.getBackground();
        Color hoverColor = bgColor.darker();

    }

    public void estiloTabela() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        );

        MainTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "background:$Login.background;"
                + "font:bold;");

        MainTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "rowHeight:30;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;");
        //  + "selectionBackground:$TableHeader.hoverBackground;"
        //  + "selectionForeground:$Table.foreground;"

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Login.background;");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("tcc/icon/svg/search.svg", 0.8f));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:$Panel.background;");

        MainTable.setGridColor(MainTable.getBackground());
        estilizarBotaoFundoTabela(cmdC);
        estilizarBotaoFundoTabela(cmdX);

    }

    public void buscar() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscarConsulta();
            }

            public void removeUpdate(DocumentEvent e) {
                buscarConsulta();
            }

            public void changedUpdate(DocumentEvent e) {
                buscarConsulta();
            }
        });
    }

    public void buscarConsulta() {
        pesquisarConsulta(txtSearch.getText());
    }

    public ChartPanel criarGrafico() {

        int canceladas = dadosGrafico()[1];
        int concluidas = dadosGrafico()[2];

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Canceladas/Suspensas", canceladas);
        dataset.setValue("Concluidas", concluidas);

        JFreeChart chart = ChartFactory.createPieChart(
                "", dataset, true, false, true
        );

        chart.removeLegend();

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setToolTipGenerator(new StandardPieToolTipGenerator() {
            @Override
            public String generateToolTip(PieDataset dataset, Comparable key) {
                return "<html><div style='"
                        + "background-color: #FFFFFF;"
                        + "color: #000000;"
                        + "padding: 5px;"
                        + "border: 1px solid #CCCCCC;"
                        + "'>"
                        + key + ": " + dataset.getValue(key)
                        + "</div></html>";
            }
        });
        Color fundoPainel = UIManager.getColor("$Login.background");

        Color corLegenda = UIManager.getColor("$Menu.foreground");

        //cor das fatias
        plot.setSectionPaint("Canceladas/Suspensas", new Color(50, 168, 82));
        plot.setSectionPaint("Concluidas", new Color(56, 142, 60));

        //Cor da legenda
        chart.setBackgroundPaint(fundoPainel);
        plot.setBackgroundPaint(fundoPainel);

        plot.setShadowPaint(null);
        plot.setOutlineVisible(false);
        plot.setSectionOutlinesVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart);
        plot.setToolTipGenerator(null);
        chartPanel.setOpaque(false);
        chartPanel.setBackground(fundoPainel);

        return chartPanel;
    }

    public int[] dadosGrafico() {
        LocalDate hoje = LocalDate.now();
        LocalDate trintaDiasAtras = hoje.minusDays(30);
        int contAgendadas = 0;
        int contCanceladas = 0;
        for (Consulta c : daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa())) {
            LocalDate dataConsulta = c.getData_consulta();

            if (c.getEstado().equals("CONCLUIDA")
                    && c.getMedico().getId_pessoa() == this.getMedicoSelecionado().getId_pessoa()
                    && (!dataConsulta.isBefore(trintaDiasAtras) && !dataConsulta.isAfter(hoje))) {

                contAgendadas++;
            }

            if (c.getEstado().equals("CANCELADA")
                    && c.getMedico().getId_pessoa() == this.getMedicoSelecionado().getId_pessoa()
                    && (!dataConsulta.isBefore(trintaDiasAtras) && !dataConsulta.isAfter(hoje))) {

                contCanceladas++;
            }
        }

        int total = contAgendadas + contCanceladas;

        int[] dados = {total, contCanceladas, contAgendadas};
        return dados;

    }

    // Abaixo remover o comentario no filtro de data 
    public void pesquisarConsulta(String nome) {
        model.limparTabela();
        model.limpar();

        String termo = nome.toLowerCase();
        List<Consulta> consultas = daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa());

        for (Consulta c : consultas) {
            String nomePaciente = c.getUsuario().getNome().toLowerCase();

            if (this.getMedicoSelecionado().getId_pessoa() == c.getMedico().getId_pessoa()) {
                if ((c.getEstado().equals("AGENDADA") /*&& c.getData_consulta().equals(hoje)*/)
                        && nomePaciente.contains(termo)) {
                    model.addConsultas(c);
                }
            }

        }
    }

    public void carregarConsultas() {
        LocalDate hoje = LocalDate.now();
        model.limpar();
        for (Consulta c : daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa())) {

            if (c.getEstado().equals("AGENDADA") /*&& c.getData_consulta().equals(hoje)*/
                    && c.getMedico().getId_pessoa() == this.getMedicoSelecionado().getId_pessoa()) {
                model.addConsultas(c);
            }
        }
    }

    public int contConsultasHoje() {
        LocalDate hoje = LocalDate.now();
        int cont = 0;

        for (Consulta c : daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa())) {
            if (c.getEstado().equals("AGENDADA") || c.getEstado().equals("CONCLUIDA") /*&& c.getData_consulta().equals(hoje)*/
                    && c.getMedico().getId_pessoa() == this.getMedicoSelecionado().getId_pessoa()) {
                cont++;
            }
        }

        return cont;
    }

    //Fim

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Hello sample message");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

    }//GEN-LAST:event_txtSearchKeyReleased

    private void cmdXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdXActionPerformed
        int indice = MainTable.getSelectedRow();
        cancelarConsulta(indice);
    }//GEN-LAST:event_cmdXActionPerformed

    private void cmdCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCActionPerformed
        int indice = MainTable.getSelectedRow();
        concluirConsulta(indice);

    }//GEN-LAST:event_cmdCActionPerformed

    private void cmdXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdXMouseEntered
        cmdX.setOpaque(false);
        Color bgColor = MainTable.getBackground();
        Color hoverColor = bgColor.darker();
        cmdX.setForeground(hoverColor);

    }//GEN-LAST:event_cmdXMouseEntered

    private void cmdCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCMouseExited
        cmdC.setOpaque(true);
        Color bgColor = MainTable.getBackground();
        Color hoverColor = bgColor.darker();
        cmdC.setForeground(bgColor);

    }//GEN-LAST:event_cmdCMouseExited

    private void cmdCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCMouseEntered
        cmdC.setOpaque(false);
        Color bgColor = MainTable.getBackground();
        Color hoverColor = bgColor.darker();
        cmdC.setForeground(hoverColor);

    }//GEN-LAST:event_cmdCMouseEntered

    private void cmdXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdXMouseExited
        cmdX.setOpaque(true);
        Color bgColor = MainTable.getBackground();
        Color hoverColor = bgColor.darker();
        cmdX.setForeground(bgColor);

    }//GEN-LAST:event_cmdXMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable MainTable;
    public javax.swing.JButton cmdC;
    public javax.swing.JButton cmdX;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb;
    public javax.swing.JPanel panelTable;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
