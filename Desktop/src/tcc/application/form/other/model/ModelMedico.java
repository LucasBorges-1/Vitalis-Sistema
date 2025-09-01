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
import tcc.application.model.Medico;

/**
 *
 * @author Borges
 */
public class ModelMedico extends AbstractTableModel {

    private List<Medico> medicos = new ArrayList<>();

    @Override
    public int getRowCount() {
        return medicos.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return medicos.get(rowIndex).getNome();
            } 
            case 1 -> {
                return medicos.get(rowIndex).getCrm();
            } 
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
                return "Crm";
            }
        }
        return "";
    }

    public void limpar() {
        medicos.clear();
    }

    public void addConsultas(Medico c) {
        medicos.add(c);
        fireTableRowsInserted(medicos.size() - 1, medicos.size() - 1);
    }

    public void removerDaTabela(int indice) {
        medicos.remove(indice);
        fireTableRowsDeleted(indice,indice);
    }
    
    public void limparTabela(){
        if (medicos.size()>=1) {
             fireTableRowsDeleted(0,medicos.size()-1);
        }
       
    }

    
    public Medico pegarMedico(int indice){
        return medicos.get(indice);
        
    }
    
}
