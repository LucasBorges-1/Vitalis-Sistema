/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.form.other.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import tcc.application.model.Consulta;

/**
 *
 * @author Borges
 */
public class ModelHistorico extends AbstractTableModel {

    private List<Consulta> consultas = new ArrayList<>();

    @Override
    public int getRowCount() {
        return consultas.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return consultas.get(rowIndex).getId_consulta();
            }
            case 1 -> {
                return consultas.get(rowIndex).getUsuario().getNome();

            }
            case 2 -> {
                return consultas.get(rowIndex).getTipo();

            }

            case 3 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                return consultas.get(rowIndex).getHorario().format(formatter);
            }

            case 4 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return consultas.get(rowIndex).getData_consulta().format(formatter);
            }

            case 5 -> {
                return consultas.get(rowIndex).getEstado();

            }

        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 -> {
                return "Ordem";
            }
            case 1 -> {
                return "Nome";
            }

            case 2 -> {
                return "Tipo";
            }
            case 3 -> {
                return "Horário";
            }
            case 4 -> {
                return "Data";
            }
            case 5 -> {
                return "Estado";
            }

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
 public void removerDaTabela(int indice) {
        consultas.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }

    public Consulta pegarConsulta(int indice) {
        return consultas.get(indice);

    }
    
     public void limparTabela(){
        if (consultas.size()>=1) {
             fireTableRowsDeleted(0,consultas.size()-1);
        }
       
    }

}
