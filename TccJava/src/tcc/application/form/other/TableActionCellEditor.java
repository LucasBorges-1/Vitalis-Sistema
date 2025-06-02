package tcc.application.form.other;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;


public class TableActionCellEditor extends DefaultCellEditor {

    private final PanelAction action = new PanelAction();

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        action.initEvent(event, -1, this); // Use -1 como valor inicial
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Atualiza o evento com a linha correta
        action.updateRow(row); // método que você pode criar no PanelAction
        return action;
    }
}

