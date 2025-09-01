package tcc.application.form.other;

import tcc.application.form.other.model.ModelHistorico;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import tcc.application.form.other.model.EstadoCellRenderer;
import tcc.application.model.Consulta;
import tcc.application.model.Medico;
import tcc.application.model.dao.DaoConsulta;

public class FormHistorico extends javax.swing.JPanel {

    private tcc.application.form.ControllerPrincipal app;
    private ModelHistorico model;
    private DaoConsulta daoConsulta;
    private Medico medicoSelecionado;

    public FormHistorico(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
        initComponents();
        lb3.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        model = new ModelHistorico();
        daoConsulta = new DaoConsulta();
        configurarLayout();
        gerenciandoTabela();
        estiloTabela();
        buscar();
        app.setI(0);

    }

    public Medico getMedicoSelecionado() {
        return medicoSelecionado;
    }

    public void setMedicoSelecionado(Medico medicoSelecionado) {
        this.medicoSelecionado = medicoSelecionado;
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
                + "cellFocusColor:$TableHeader.hoverBackground;");
               // + "selectionBackground:$TableHeader.hoverBackground;"
               // + "selectionForeground:$Table.foreground;"

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

    public void addHistórico() {

    }

    public void carregarConsultas() {
        model.limpar();
        for (Consulta c : daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa())) {

            if (c.getEstado().equals("CONCLUIDA") || c.getEstado().equals("CANCELADA")) {
                model.addConsultas(c);
            }

        }
    }

    public void buscar() {
        txtHisSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscarConsulta();
            }

            public void removeUpdate(DocumentEvent e) {
                buscarConsulta();
            }

            public void changedUpdate(DocumentEvent e) {
                buscarConsulta();
            }
        });
    }

    public void pesquisarConsulta(String nome) {
        model.limparTabela();
        model.limpar();

        String termo = nome.toLowerCase();
        List<Consulta> consultas = daoConsulta.listar(this.getMedicoSelecionado().getId_pessoa()); // pega todas as consultas

        for (Consulta c : consultas) {
            String nomePaciente = c.getUsuario().getNome().toLowerCase();

            if ((c.getEstado().equals("CONCLUIDA") || c.getEstado().equals("CANCELADA"))
                    && nomePaciente.contains(termo)) {
                model.addConsultas(c);
            }
        }
    }

    public void buscarConsulta() {
        pesquisarConsulta(txtHisSearch.getText());
    }

    public void gerenciandoTabela() {
        TableHis.setModel(model);
        TableHis.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < TableHis.getColumnCount(); i++) {
            TableHis.getColumnModel().getColumn(i).setResizable(false);
        }

        TableHis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        carregarConsultas();
        Font fonteTabela = new Font("Segoe UI", Font.PLAIN, 14);

        TableHis.setFont(fonteTabela);
        TableHis.getTableHeader().setFont(fonteTabela);
        TableHis.setRowHeight(28);
        DefaultTableCellRenderer centroRenderer = new DefaultTableCellRenderer();
        centroRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        TableHis.getColumn("Ordem").setCellRenderer(centroRenderer);
        TableHis.getColumn("Nome").setCellRenderer(centroRenderer);
        TableHis.getColumn("Tipo").setCellRenderer(centroRenderer);
        TableHis.getColumn("Horário").setCellRenderer(centroRenderer);
        TableHis.getColumn("Data").setCellRenderer(centroRenderer);
        TableHis.getColumn("Estado").setCellRenderer(centroRenderer);
        TableHis.getColumnModel().getColumn(5).setCellRenderer(new EstadoCellRenderer());

        // Coluna "Ordem" (coluna 0)
        TableHis.getColumnModel().getColumn(0).setMinWidth(50);
        TableHis.getColumnModel().getColumn(0).setMaxWidth(60);
        TableHis.getColumnModel().getColumn(0).setPreferredWidth(55);

       
        // Coluna "Horário" (coluna 3)
        TableHis.getColumnModel().getColumn(3).setMinWidth(70);
        TableHis.getColumnModel().getColumn(3).setMaxWidth(80);
        TableHis.getColumnModel().getColumn(3).setPreferredWidth(75);

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
