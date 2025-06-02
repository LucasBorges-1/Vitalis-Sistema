package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;
import tcc.application.model.Consulta;
import tcc.application.model.Consulta_;
import tcc.application.model.dao.DaoConsulta;

public class FormMainMenu extends javax.swing.JPanel {

    private tcc.application.form.ControllerPrincipal app;
    private ModelConsultas model;
    private DaoConsulta daoConsulta;
    private int contRow;
    private int contFinish = 0;

    private JLabel labelAgendadas = new JLabel("Agendadas para hoje: " + contRow);
    private JLabel labelRealizadas = new JLabel("Consultas concluidas: " + contFinish);

    public FormMainMenu() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        daoConsulta = new DaoConsulta();
        model = new ModelConsultas();

        //Configs
        configurarLayout();
        estiloTabela();
        gerenciandoTabela();
        app.setI(0);
        //

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onFinish(int row) {
                System.out.println("Consulta linha " + row + " terminada");
                if (MainTable.isEditing()) {
                    MainTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) MainTable.getModel();
                model.removeRow(row);
                contFinish += 1;
                labelRealizadas.setText("Consultas concluidas: " + contFinish);
                if (contFinish == 20) {
                    txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Não há mais consultas na data de hoje.");
                }
            }

            @Override
            public void onDelete(int row) {
                if (MainTable.isEditing()) {
                    MainTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) MainTable.getModel();
                model.removeRow(row);
                contFinish += 1;
                labelRealizadas.setText("Consultas concluidas: " + contFinish);
            }
        };

        //Barbaridade
        // MainTable.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        //MainTable.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
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

        // Define layout vertical para panelTable
        panelTable.setLayout(new BorderLayout()); // troca para BorderLayout
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de busca no topo
        JPanel painelBusca = new JPanel(new BorderLayout());
        txtSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE - 150, txtSearch.getPreferredSize().height));
        painelBusca.add(txtSearch, BorderLayout.CENTER);
        panelTable.add(painelBusca, BorderLayout.NORTH);

        // Área central com a tabela rolável
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        panelTable.add(jScrollPane1, BorderLayout.CENTER);

        // Rodapé com os botões no canto inferior direito
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelRodape.setOpaque(false);
        cmdX.setForeground(MainTable.getBackground());
     
        cmdX.setBackground(MainTable.getBackground());
        cmdC.setForeground(MainTable.getBackground());
        cmdC.setBackground(MainTable.getBackground());
        painelRodape.add(cmdX);
        painelRodape.add(cmdC);
        panelTable.add(painelRodape, BorderLayout.SOUTH);

        // Adiciona o panelTable no centro do layout principal
        add(panelTable, BorderLayout.CENTER);

    }

    public void carregarConsultas() {
        model.limpar();
        for (Consulta c : daoConsulta.listarConsultaDataAtual()) {
            model.addConsultas(c);
        }
    }

    public void carregarConsultasOt() {
        List<Consulta> consultas = daoConsulta.listarConsultaDataAtual();
        model.setConsultas(consultas);
    }

    public int contConsultashoje() {
        int i = 0;
        for (Consulta c : daoConsulta.listarConsultaDataAtual()) {
            i += 1;
        }
        return i;
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

        cmdX.setForeground(new java.awt.Color(102, 0, 102));
        cmdX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tcc/application/form/other/delete.png"))); // NOI18N
        cmdX.setContentAreaFilled(false);

        cmdC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tcc/application/form/other/certo (2).png"))); // NOI18N
        cmdC.setContentAreaFilled(false);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void gerenciandoTabela() {
        MainTable.setModel(model);

        carregarConsultasOt();
        Font fonteTabela = new Font("Segoe UI", Font.PLAIN, 14);

        MainTable.setFont(fonteTabela);
        MainTable.getTableHeader().setFont(fonteTabela);
        MainTable.setRowHeight(28);

        //Muda a largura da coluna ações para 70px
        // TableColumn colunaAcoes = MainTable.getColumn("Ações");
        //colunaAcoes.setMinWidth(70);
        //colunaAcoes.setMaxWidth(70);
        //colunaAcoes.setPreferredWidth(70);
        DefaultTableCellRenderer centroRenderer = new DefaultTableCellRenderer();
        centroRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        MainTable.getColumn("Nome").setCellRenderer(centroRenderer);
        MainTable.getColumn("Tipo").setCellRenderer(centroRenderer);
        MainTable.getColumn("Horário").setCellRenderer(centroRenderer);

        //Carrega as consultas do banco de dados
        labelAgendadas.setText("Agendadas para hoje: " + contConsultashoje());

    }

    public void estiloTabela() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        // + "margin:20,20,20,20;"
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
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                //+ "margin:15,15,15,15;"
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
                //  + "margin:5,20,5,20;"
                + "background:$Panel.background;");

        /*BtAtrasado.putClientProperty(FlatClientProperties.STYLE, ""
                 + "font:+2;"
                  + "arc:15;"
                + "background:$Panel.background;"
                + "borderWidth:0;"
                + "focusWidth:0");

        panelTable.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
         */
        MainTable.setGridColor(MainTable.getBackground());
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Hello sample message");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

    }//GEN-LAST:event_txtSearchKeyReleased

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
