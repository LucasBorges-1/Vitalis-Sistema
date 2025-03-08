package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class FormHistorico extends javax.swing.JPanel {
private tcc.application.form.ControllerPrincipal app;
    public FormHistorico() {
        initComponents();
        lb3.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        configurarLayout();
        estiloTabela();
        adicionandoDadosExemplos();
        app.setI(0);
        
    }
    
    
    private void configurarLayout() {
        
    setLayout(new BorderLayout(10, 10)); 
    add(lb3, BorderLayout.NORTH);
    
    paneltableHis.setLayout(new BorderLayout());
    
    
    paneltableHis.add(txtHisSearch, BorderLayout.NORTH);

    paneltableHis.add(jScrollPane1, BorderLayout.CENTER);
    

    jScrollPane1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    add(paneltableHis, BorderLayout.CENTER);
}

     public void estiloTabela() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        paneltableHis.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
               // + "margin:20,20,20,20;"
        );

        TableHis.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "background:$Login.background;"
                + "font:bold;");

        TableHis.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "rowHeight:30;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
               // + "margin:15,15,15,15;"
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Login.background;");
        
        txtHisSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtHisSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("tcc/icon/svg/search.svg", 0.8f));
        txtHisSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
             //   + "margin:5,20,5,20;"
                + "background:$Panel.background;");
      

        paneltableHis.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        //MainTable.setGridColor(new Color(49, 62, 74));

    }
     
     
     public void adicionandoDadosExemplos() {
    DefaultTableModel model = (DefaultTableModel) TableHis.getModel();

  model.addRow(new Object[]{1, "João Silva", "Cardiologista", "08:00", "2023-10-01"});
model.addRow(new Object[]{2, "Maria Santos", "Pediatra", "09:30", "2023-10-01"});
model.addRow(new Object[]{3, "Carlos Oliveira", "Ortopedista", "10:15", "2023-10-01"});
model.addRow(new Object[]{4, "Ana Costa", "Dermatologista", "11:00", "2023-10-01"});
model.addRow(new Object[]{5, "Pedro Alves", "Otorrinolaringologista", "13:30", "2023-10-02"});
model.addRow(new Object[]{6, "Fernanda Lima", "Ginecologista", "14:45", "2023-10-02"});
model.addRow(new Object[]{7, "Ricardo Souza", "Neurologista", "15:20", "2023-10-02"});
model.addRow(new Object[]{8, "Patrícia Rocha", "Psiquiatra", "16:10", "2023-10-02"});
model.addRow(new Object[]{9, "Lucas Mendes", "Endocrinologista", "17:00", "2023-10-03"});
model.addRow(new Object[]{10, "Juliana Pereira", "Oftalmologista", "08:30", "2023-10-03"});
model.addRow(new Object[]{11, "Roberto Fernandes", "Urologista", "09:00", "2023-10-03"});
model.addRow(new Object[]{12, "Camila Gonçalves", "Cardiologista", "10:00", "2023-10-03"});
model.addRow(new Object[]{13, "Gustavo Martins", "Pediatra", "11:30", "2023-10-04"});
model.addRow(new Object[]{14, "Isabela Ribeiro", "Ortopedista", "12:15", "2023-10-04"});
model.addRow(new Object[]{15, "Marcos Antunes", "Dermatologista", "14:00", "2023-10-04"});
model.addRow(new Object[]{16, "Tatiane Castro", "Otorrinolaringologista", "15:30", "2023-10-04"});
model.addRow(new Object[]{17, "Felipe Nunes", "Ginecologista", "16:45", "2023-10-05"});
model.addRow(new Object[]{18, "Vanessa Lopes", "Neurologista", "17:30", "2023-10-05"});
model.addRow(new Object[]{19, "Bruno Carvalho", "Psiquiatra", "18:00", "2023-10-05"});
model.addRow(new Object[]{20, "Daniela Freitas", "Endocrinologista", "19:15", "2023-10-05"});
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb3 = new javax.swing.JLabel();
        paneltableHis = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableHis = new javax.swing.JTable();
        txtHisSearch = new javax.swing.JTextField();

        lb3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb3.setText("Histórico");

        TableHis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ordem", "Nome", "Tipo", "Horário", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableHis);

        javax.swing.GroupLayout paneltableHisLayout = new javax.swing.GroupLayout(paneltableHis);
        paneltableHis.setLayout(paneltableHisLayout);
        paneltableHisLayout.setHorizontalGroup(
            paneltableHisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
            .addGroup(paneltableHisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHisSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneltableHisLayout.setVerticalGroup(
            paneltableHisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneltableHisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtHisSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(paneltableHis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(paneltableHis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TableHis;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb3;
    public javax.swing.JPanel paneltableHis;
    public javax.swing.JTextField txtHisSearch;
    // End of variables declaration//GEN-END:variables
}
