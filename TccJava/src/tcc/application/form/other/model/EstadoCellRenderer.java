/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.form.other.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Borges
 */
public class EstadoCellRenderer extends DefaultTableCellRenderer {
    
      public EstadoCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);  // Centraliza o texto
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null && column == 5) {  // Coluna "Estado"
            String estado = value.toString();
            if (estado.equalsIgnoreCase("CONCLUIDA")) {
                c.setForeground(Color.GREEN);
            } else if (estado.equalsIgnoreCase("CANCELADA")) {
                c.setForeground(Color.RED.brighter());
            } else {
                c.setForeground(Color.BLACK);
            }
        } else {
            c.setForeground(Color.BLACK);  
        }
        
     

        return c;
    }
}
