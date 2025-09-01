package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;


public class FormPendentes extends javax.swing.JPanel {
private tcc.application.form.ControllerPrincipal app;
    public FormPendentes() {
        initComponents();
        lb3.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        configurarLayout();
        estiloTabela();
        adicionandoDadosExemplos();
        app.setI(0);
    }
    
        public void adicionandoDadosExemplos() {
    DefaultTableModel model = (DefaultTableModel) TablePen.getModel();

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

     private void configurarLayout() {
    setLayout(new BorderLayout(10, 10)); 
    add(lb5, BorderLayout.NORTH);
    
    panelPen.setLayout(new BorderLayout());
    
    
    panelPen.add(txtSearchPen, BorderLayout.NORTH);

    panelPen.add(jScrollPane1, BorderLayout.CENTER);
    add(lb3, BorderLayout.NORTH);

    jScrollPane1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    add(panelPen, BorderLayout.CENTER);
}

     public void estiloTabela() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelPen.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
                //+ "margin:20,20,20,20;"
        );

        TablePen.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "background:$Login.background;"
                + "font:bold;");

        TablePen.putClientProperty(FlatClientProperties.STYLE, ""
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
        
         lb5.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +5;"
               // + "margin:30,30,30,30;"
         );
        
        txtSearchPen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearchPen.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("tcc/icon/svg/search.svg", 0.8f));
        txtSearchPen.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
              //  + "margin:5,20,5,20;"
                + "background:$Panel.background;");
      

        panelPen.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        //MainTable.setGridColor(new Color(49, 62, 74));

    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePen = new javax.swing.JTable();
        txtSearchPen = new javax.swing.JTextField();
        lb5 = new javax.swing.JLabel();
        lb3 = new javax.swing.JLabel();

        TablePen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TablePen);

        txtSearchPen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchPenKeyReleased(evt);
            }
        });

        lb5.setText("CONSULTAS MARCADAS POREM NAO REALIZADAS");

        javax.swing.GroupLayout panelPenLayout = new javax.swing.GroupLayout(panelPen);
        panelPen.setLayout(panelPenLayout);
        panelPenLayout.setHorizontalGroup(
            panelPenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
            .addGroup(panelPenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearchPen))
                .addGap(26, 512, Short.MAX_VALUE))
        );
        panelPenLayout.setVerticalGroup(
            panelPenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPenLayout.createSequentialGroup()
                .addComponent(lb5)
                .addGap(7, 7, 7)
                .addComponent(txtSearchPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lb3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb3.setText("Pendentes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(panelPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(panelPen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchPenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPenKeyReleased

    }//GEN-LAST:event_txtSearchPenKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TablePen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb3;
    private javax.swing.JLabel lb5;
    public javax.swing.JPanel panelPen;
    public javax.swing.JTextField txtSearchPen;
    // End of variables declaration//GEN-END:variables
}
