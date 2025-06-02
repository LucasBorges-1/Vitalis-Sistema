/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.form.other;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import tcc.application.model.Consulta;

/**
 *
 * @author Borges
 */
public class ModelConsultas extends AbstractTableModel {

    private List<Consulta> consultas = new ArrayList<>();

    @Override
    public int getRowCount() {
        return consultas.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return consultas.get(rowIndex).getUsuario().getNome();
            }
            case 1 -> {
                return consultas.get(rowIndex).getTipo();

            }
            case 2 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                return consultas.get(rowIndex).getHorario().format(formatter);

            }
           // case 3 -> {
                //Ações
           // }

        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "Nome";
            }
            case 1 -> {
                return "Tipo";
            }

            case 2 -> {
                return "Horário";
            }
           // case 3 -> {
              //  return "Ações";
           // }

        }
        return "";
    }

    public void limpar() {
        consultas.clear();
    }

    public void addConsultas(Consulta c) {
        consultas.add(c);
        fireTableRowsInserted(consultas.size() - 1, consultas.size() - 1);
    }

    public void excluirCliente(int indice) {
        consultas.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
        fireTableDataChanged();
    }
}
