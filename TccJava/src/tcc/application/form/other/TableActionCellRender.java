package tcc.application.form.other;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableActionCellRender extends DefaultTableCellRenderer {

    private final PanelAction action = new PanelAction();

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!isSelected && row % 2 == 0) {
            action.setBackground(jtable.getBackground());
        } else {
            action.setBackground(jtable.getSelectionBackground());
        }
        return action;
    }
}

