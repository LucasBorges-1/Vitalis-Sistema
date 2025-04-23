package tcc.application.form.other;

import tcc.application.form.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import tcc.application.Application;

public class FormHorarios extends javax.swing.JPanel {
    
    private tcc.application.form.ControllerPessoa cp;

    public FormHorarios() {
        initComponents();
        init();
       
    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        BtCadastrar.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        //edNome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "User Name");
        //edSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        configurarLayout();
        
        
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        MainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; // 
        gbc.gridy = 0; // 
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 5);

        panelEsquerda.add(ltitulo);
        panelEsquerda.add(lDatanicio);
        panelEsquerda.add(CdataInicio);
        panelEsquerda.add(ldataFim);
        panelEsquerda.add(CdataFim);
        panelEsquerda.add(lmanha);
        panelEsquerda.add(HinicioManha);
        panelEsquerda.add(HFimManha);
        panelEsquerda.add(ltarde);
        panelEsquerda.add(HinicioTarde);
        panelEsquerda.add(HFimoTarde);
        panelEsquerda.add(BtCadastrar);

        panelEsquerda.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        );

        panelEsquerda.setBorder(BorderFactory.createEmptyBorder(100, 160, 60, 60));
        MainPanel.add(panelEsquerda, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 0, 0);

        panelDireita.setLayout(new BorderLayout(10, 10));
        panelDireita.setBorder((BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        TbMedico.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
        );

        panelDireita.add(TbMedico, BorderLayout.CENTER);
        panelDireita.add(btEditar, BorderLayout.SOUTH);
        panelDireita.add(btExcluir, BorderLayout.SOUTH);
        TbMedico.add(jScrollPane1);

        panelDireita.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        );

        MainPanel.add(panelDireita, gbc);

        add(MainPanel, BorderLayout.CENTER);
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        panelEsquerda = new javax.swing.JPanel();
        BtCadastrar = new javax.swing.JButton();
        CdataInicio = new com.github.lgooddatepicker.components.DatePicker();
        HinicioManha = new com.github.lgooddatepicker.components.TimePicker();
        HFimManha = new com.github.lgooddatepicker.components.TimePicker();
        lDatanicio = new javax.swing.JLabel();
        ldataFim = new javax.swing.JLabel();
        CdataFim = new com.github.lgooddatepicker.components.DatePicker();
        ltitulo = new javax.swing.JLabel();
        lmanha = new javax.swing.JLabel();
        HinicioTarde = new com.github.lgooddatepicker.components.TimePicker();
        ltarde = new javax.swing.JLabel();
        HFimoTarde = new com.github.lgooddatepicker.components.TimePicker();
        panelDireita = new javax.swing.JPanel();
        btEditar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbMedico = new javax.swing.JTable();

        panelEsquerda.setPreferredSize(new java.awt.Dimension(357, 395));

        BtCadastrar.setText("Cadastrar");
        BtCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCadastrarActionPerformed(evt);
            }
        });

        lDatanicio.setText("Data de Inicio");

        ldataFim.setText("Data final");

        ltitulo.setText("Configurações de Intervalo");

        lmanha.setText("Manha");

        ltarde.setText("Tarde");

        javax.swing.GroupLayout panelEsquerdaLayout = new javax.swing.GroupLayout(panelEsquerda);
        panelEsquerda.setLayout(panelEsquerdaLayout);
        panelEsquerdaLayout.setHorizontalGroup(
            panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEsquerdaLayout.createSequentialGroup()
                .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelEsquerdaLayout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(BtCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEsquerdaLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEsquerdaLayout.createSequentialGroup()
                                    .addComponent(ltarde)
                                    .addGap(109, 109, 109))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEsquerdaLayout.createSequentialGroup()
                                    .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelEsquerdaLayout.createSequentialGroup()
                                            .addComponent(HinicioTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(HFimoTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelEsquerdaLayout.createSequentialGroup()
                                            .addComponent(HinicioManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(HFimManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(28, 28, 28)))))
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CdataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CdataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ltitulo)))
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(ldataFim)))
                .addGap(0, 57, Short.MAX_VALUE))
            .addGroup(panelEsquerdaLayout.createSequentialGroup()
                .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(lDatanicio))
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(lmanha)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEsquerdaLayout.setVerticalGroup(
            panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEsquerdaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ltitulo)
                .addGap(18, 18, 18)
                .addComponent(lDatanicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CdataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(ldataFim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CdataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lmanha)
                .addGap(18, 18, 18)
                .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HinicioManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HFimManha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ltarde)
                .addGap(18, 18, 18)
                .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HinicioTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HFimoTarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(BtCadastrar)
                .addGap(15, 15, 15))
        );

        btEditar.setText("Editar");

        btExcluir.setText("Excluir");

        TbMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lista de Médicos cadastrados"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TbMedico);
        if (TbMedico.getColumnModel().getColumnCount() > 0) {
            TbMedico.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout panelDireitaLayout = new javax.swing.GroupLayout(panelDireita);
        panelDireita.setLayout(panelDireitaLayout);
        panelDireitaLayout.setHorizontalGroup(
            panelDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireitaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDireitaLayout.createSequentialGroup()
                        .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelDireitaLayout.setVerticalGroup(
            panelDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireitaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btEditar)
                    .addComponent(btExcluir))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(panelEsquerda, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelDireita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEsquerda, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelDireita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCadastrarActionPerformed
        /*cp = new ControllerPessoa();
        String cpf = this.edCpf.getText();
        String email = this.edEmail.getText();
        String crm = this.edCrm.getText();
        String senha = this.edSenha.getText();
        String dataS = this.edDataNa.getText();
        String nome=this.edNome.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataS, formatter);
        cp.cadastrarMedico(crm, email, nome, senha,cpf,data);
      
*/
    }//GEN-LAST:event_BtCadastrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCadastrar;
    public com.github.lgooddatepicker.components.DatePicker CdataFim;
    public com.github.lgooddatepicker.components.DatePicker CdataInicio;
    public com.github.lgooddatepicker.components.TimePicker HFimManha;
    public com.github.lgooddatepicker.components.TimePicker HFimoTarde;
    public com.github.lgooddatepicker.components.TimePicker HinicioManha;
    public com.github.lgooddatepicker.components.TimePicker HinicioTarde;
    private javax.swing.JPanel MainPanel;
    public javax.swing.JTable TbMedico;
    public javax.swing.JButton btEditar;
    public javax.swing.JButton btExcluir;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lDatanicio;
    public javax.swing.JLabel ldataFim;
    public javax.swing.JLabel lmanha;
    public javax.swing.JLabel ltarde;
    public javax.swing.JLabel ltitulo;
    private javax.swing.JPanel panelDireita;
    private javax.swing.JPanel panelEsquerda;
    // End of variables declaration//GEN-END:variables
}
