package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import raven.toast.Notifications;

public class FormDashboard extends javax.swing.JPanel {
    
    public FormDashboard() {
       
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        estiloTabela();
        configurarLayout();

    }

    private void configurarLayout() {
        // Remove o layout atual (GroupLayout) e define um BorderLayout
        setLayout(new BorderLayout(10, 10)); // Margens de 10px

        // Cria um painel para os contadores (linhas e colunas)
        JPanel painelContadores = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 linha, 2 colunas, espaçamento de 10px
        painelContadores.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10)); // Margens: topo, esquerda, inferior, direita

        // Painel para o número de linhas
        JPanel painelLinhas = new JPanel();
        painelLinhas.setName("painelLinhas"); // Nome para identificar o painel
        painelLinhas.add(new JLabel("Número de Linhas: " + MainTable.getRowCount())); // Exibe o número de linhas

        painelLinhas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background"
        );
        painelContadores.add(painelLinhas);

        // Painel para o número de colunas
        JPanel painelColunas = new JPanel();
        painelColunas.setName("painelColunas"); // Nome para identificar o painel
        painelColunas.add(new JLabel("Número de Colunas: " + MainTable.getColumnCount())); // Exibe o número de colunas

        painelColunas.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "border:5,5,5,5;"
                + "background:$Login.background"
        );

        painelContadores.add(painelColunas);

        // Adiciona os componentes no BorderLayout
        add(lb, BorderLayout.NORTH); // Label no topo
        add(painelContadores, BorderLayout.CENTER); // Painel de contadores no centro
        add(jScrollPane1, BorderLayout.SOUTH); // Tabela na parte inferior

        // Define margens para o JScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margens: topo, esquerda, inferior, direita
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MainTable = new javax.swing.JTable();

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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(MainTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(91, 91, 91)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void estiloTabela() {
        this.MainTable.setRowHeight(30); // Altura das linhas
        this.MainTable.setShowGrid(true);
        this.MainTable.setGridColor(new Color(230, 230, 230)); // Cor das linhas da grade
        //this.MainTable.setIntercellSpacing(new Dimension(10, 10)); // Espaçamento entre células
        this.MainTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "background:$Login.background;"
          
        );
        
        
        // Centraliza o conteúdo das células
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.MainTable.getColumnCount(); i++) {
            this.MainTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        MainTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Alternar cores de fundo das linhas
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 240, 240)); // Linhas pares
                } else {
                    c.setBackground(Color.WHITE); // Linhas ímpares
                  
                     
                }

                // Cor do texto selecionado
                if (isSelected) {
                    c.setBackground(new Color(0, 120, 215)); // Fundo selecionado
                    c.setForeground(Color.WHITE); // Texto selecionado
                } else {
                    c.setForeground(Color.BLACK); // Texto normal
                }

                return c;
            }
        });

    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Hello sample message");
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable MainTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
