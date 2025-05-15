package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

public class FormMainMenu extends javax.swing.JPanel {
    private tcc.application.form.ControllerPrincipal app;
    private int contRow;
    private int contFinish=0;
    private JLabel labelAgendadas = new JLabel("Agendadas para hoje: " + contRow);
    private JLabel labelRealizadas = new JLabel("Consultas concluidas: "+contFinish);
    
    public FormMainMenu() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        
        configurarLayout();
        estiloTabela();
        adicionandoDadosExemplos();
        app.setI(0);
        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onFinish(int row) {
                System.out.println("Consulta linha " + row+" terminada");
                 if (MainTable.isEditing()) {
                    MainTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) MainTable.getModel();
                model.removeRow(row);
                contFinish+=1;
                labelRealizadas.setText("Consultas concluidas: " + contFinish);
                if(contFinish==20){
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
                contFinish+=1;
                 labelRealizadas.setText("Consultas concluidas: " + contFinish);
            }
        };

        MainTable.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        MainTable.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
        

    }

    private void configurarLayout() {

        setLayout(new BorderLayout(10, 10));

        JPanel painelContadores = new JPanel(new GridLayout(1, 2, 10, 0));
        painelContadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel painelLinhas = new JPanel();
        painelLinhas.setName("painelLinhas");

        
        labelAgendadas.putClientProperty(FlatClientProperties.STYLE, ""
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

        panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS));
        panelTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtSearch.getPreferredSize().height)); // faz ocupar largura total
        panelTable.add(Box.createVerticalStrut(10)); // espaço acima do search
        panelTable.add(txtSearch);
        panelTable.add(Box.createVerticalStrut(10)); // espaço entre search e tabela

        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(1, 15, 15, 15));
        panelTable.add(jScrollPane1);

        add(panelTable, BorderLayout.CENTER);
        
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

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 512, Short.MAX_VALUE))
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(609, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void adicionandoDadosExemplos() {
        DefaultTableModel model = (DefaultTableModel) MainTable.getModel();
        model.addRow(new Object[]{"João Silva", "Cardiologista", "08:00"});
        model.addRow(new Object[]{"Maria Santos", "Pediatra", "09:30"});
        model.addRow(new Object[]{"Carlos Oliveira", "Ortopedista", "10:15"});
        model.addRow(new Object[]{"Ana Costa", "Dermatologista", "11:00"});
        model.addRow(new Object[]{"Pedro Alves", "Otorrinolaringologista", "13:30"});
        model.addRow(new Object[]{"Fernanda Lima", "Ginecologista", "14:45"});
        model.addRow(new Object[]{"Ricardo Souza", "Neurologista", "15:20"});
        model.addRow(new Object[]{"Patrícia Rocha", "Psiquiatra", "16:10"});
        model.addRow(new Object[]{"Lucas Mendes", "Endocrinologista", "17:00"});
        model.addRow(new Object[]{"Juliana Pereira", "Oftalmologista", "08:30"});
        model.addRow(new Object[]{"Roberto Fernandes", "Urologista", "09:00"});
        model.addRow(new Object[]{"Camila Gonçalves", "Cardiologista", "10:00"});
        model.addRow(new Object[]{"Gustavo Martins", "Pediatra", "11:30"});
        model.addRow(new Object[]{"Isabela Ribeiro", "Ortopedista", "12:15"});
        model.addRow(new Object[]{"Marcos Antunes", "Dermatologista", "14:00"});
        model.addRow(new Object[]{"Tatiane Castro", "Otorrinolaringologista", "15:30"});
        model.addRow(new Object[]{"Felipe Nunes", "Ginecologista", "16:45"});
        model.addRow(new Object[]{"Vanessa Lopes", "Neurologista", "17:30"});
        model.addRow(new Object[]{"Bruno Carvalho", "Psiquiatra", "18:00"});
        model.addRow(new Object[]{"Daniela Freitas", "Endocrinologista", "19:15"});
        contRow=MainTable.getRowCount();
        labelAgendadas.setText("Agendadas para hoje: " + contRow);
        
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
        MainTable.setGridColor(new Color(49, 62, 74));
         */
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Hello sample message");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

    }//GEN-LAST:event_txtSearchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable MainTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb;
    public javax.swing.JPanel panelTable;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
