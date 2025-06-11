package tcc.application.form.other;

import tcc.application.form.other.model.ModelConsultas;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import raven.toast.Notifications;
import tcc.application.form.ControllerPrincipal;
import tcc.application.form.LoginMedicoForm;
import tcc.application.model.Consulta;
import tcc.application.model.Consulta_;
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
        labelAgendadas = new JLabel("Agendadas para hoje: " + model.getRowCount());
        labelRealizadas = new JLabel("Já realizadas: " + contFinish);

    }

    public Medico getMedicoSelecionado() {
        return medicoSelecionado;
    }

    public void setMedicoSelecionado(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
    }

    private void configurarLayout() {

        setLayout(new BorderLayout(10, 10));

        JPanel painelContadores = new JPanel(new GridLayout(1, 2, 10, 0));
        painelContadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel painelLinhas = new JPanel();
        painelLinhas.setName("painelLinhas");

        labelAgendadas.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h2.font");

       
        
        MainTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h2.font");
        
        labelAgendadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelAgendadas.setVerticalAlignment(SwingConstants.CENTER);
        painelLinhas.add(labelAgendadas);
        painelLinhas.setLayout(new GridBagLayout());

        painelLinhas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background"
        );

        painelContadores.add(painelLinhas);

        JPanel painelColunas = new JPanel();
        painelColunas.setName("painelColunas");

        labelRealizadas.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h2.font");
        labelRealizadas.setHorizontalAlignment(SwingConstants.CENTER);
        labelRealizadas.setVerticalAlignment(SwingConstants.CENTER);
        painelColunas.add(labelRealizadas);
        painelColunas.setLayout(new GridBagLayout());

        painelColunas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background"
        );

        painelContadores.add(painelColunas);
        add(lb, BorderLayout.NORTH);
        add(painelContadores, BorderLayout.NORTH);
        estilizarBotaoFundoTabela(cmdC);
        estilizarBotaoFundoTabela(cmdX);

        panelTable.setLayout(new BorderLayout());
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE - 150, txtSearch.getPreferredSize().height));
        panelTable.add(txtSearch, BorderLayout.NORTH);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        panelTable.add(jScrollPane1, BorderLayout.CENTER);
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelRodape.setOpaque(false);
        painelRodape.putClientProperty(FlatClientProperties.STYLE, "background:$Login.background;");
        painelRodape.add(cmdX);
        painelRodape.add(cmdC);
        panelTable.add(painelRodape, BorderLayout.SOUTH);
        add(panelTable, BorderLayout.CENTER);

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
        lb.setText("Dashboard");

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

    public void gerenciandoTabela() {
        MainTable.setModel(model);
        MainTable.getTableHeader().setReorderingAllowed(false);

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

    public void buscarConsulta() {
        pesquisarConsulta(txtSearch.getText());
    }


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
