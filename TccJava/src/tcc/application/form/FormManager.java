package tcc.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.miginfocom.swing.MigLayout;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import raven.toast.Notifications;
import tcc.application.form.other.model.ModelMedico;
import tcc.application.model.Consulta;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;
import tcc.application.model.dao.DaoClinica;
import tcc.application.model.dao.DaoPessoa;

public class FormManager extends javax.swing.JPanel {

    private tcc.application.form.ControllerPessoa cp;
    private ModelMedico model;
    private DaoPessoa daoPessoa;
    private DaoClinica daoClinica;
    private ActionListener listenerCadastrarOriginal;

    public FormManager() {
        initComponents();
        daoPessoa = new DaoPessoa();
        daoClinica = new DaoClinica();
        model = new ModelMedico();
        gerenciandoTabela();
        carregarMedicos();
        estiloTabela();
        init();

    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        BtCadastrar.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        edNome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nome do Médico");
        edSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha para acesso");
        edCpf.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Insira o Cpf");
        edDataNa.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Informe seu Nascimento");
        edAreaAtuacao.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Informe sua Formação");
        edCrm.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Insira o Crm");
        edEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Informe o seu Email");
        configurarLayout();

        listenerCadastrarOriginal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp = new ControllerPessoa();
                String cpf = edCpf.getText();
                String email = edEmail.getText();
                String crm = edCrm.getText();
                String senha = edSenha.getText();
                String dataS = edDataNa.getText();
                String nome = edNome.getText();
                String tipo = edAreaAtuacao.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataS, formatter);
                cp.cadastrarMedico(crm, email, nome, senha, cpf, data, tipo);

                edNome.setText("");
                edCpf.setText("");
                edEmail.setText("");
                edDataNa.setText("");
                edCrm.setText("");
                edSenha.setText("");
                edAreaAtuacao.setText("");
                carregarMedicos();
            }
        };

        BtCadastrar.addActionListener(listenerCadastrarOriginal);

        btExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarMedico();
            }
        });

    }

    public void estiloTabela() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        TbMedico.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Login.background;"
        );

        TbMedico.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "background:$Login.background;"
        );

        TbMedico.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "rowHeight:30;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        jScrollPane1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Login.background;");

        TbMedico.setGridColor(TbMedico.getBackground());

    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        MainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 5);

        panelEsquerda.setLayout(new GridBagLayout());
        GridBagConstraints gbcEsquerda = new GridBagConstraints();
        gbcEsquerda.gridx = 0;
        gbcEsquerda.gridy = 0;
        gbcEsquerda.weightx = 1.0;

        gbcEsquerda.anchor = GridBagConstraints.NORTH;
        gbcEsquerda.insets = new Insets(5, 10, 5, 10);

        Dimension campoSize = new Dimension(250, 25);
        edNome.setPreferredSize(campoSize);
        edCpf.setPreferredSize(campoSize);
        edEmail.setPreferredSize(campoSize);
        edDataNa.setPreferredSize(campoSize);
        edCrm.setPreferredSize(campoSize);
        edSenha.setPreferredSize(campoSize);
        edAreaAtuacao.setPreferredSize(campoSize);
        BtCadastrar.setPreferredSize(new Dimension(120, 30));

        panelEsquerda.add(lNome, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edNome, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lCpf, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edCpf, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lEmail, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edEmail, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lDataNa, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edDataNa, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lCrm, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edCrm, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lSenha, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edSenha, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(lAreadeAtuacao, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(edAreaAtuacao, gbcEsquerda);
        gbcEsquerda.gridy++;
        panelEsquerda.add(BtCadastrar, gbcEsquerda);
        gbcEsquerda.gridy++;

        panelEsquerda.putClientProperty(FlatClientProperties.STYLE,
                "arc:25;"
                + "background:$Login.background;"
        );

        panelEsquerda.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        panelEsquerda.setPreferredSize(new Dimension(350, panelEsquerda.getPreferredSize().height));
        panelEsquerda.setMinimumSize(new Dimension(300, 0));

        MainPanel.add(panelEsquerda, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 0, 0);

        panelDireita.setLayout(new BorderLayout(10, 10));
        panelDireita.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        TbMedico.putClientProperty(FlatClientProperties.STYLE,
                "background:$Login.background;"
        );

        panelDireita.add(jScrollPane1, BorderLayout.CENTER);

        panelDireita.add(btExcluir, BorderLayout.SOUTH);

        panelDireita.putClientProperty(FlatClientProperties.STYLE,
                "arc:25;"
                + "background:$Login.background;"
        );

        MainPanel.add(panelDireita, gbc);
        add(MainPanel, BorderLayout.CENTER);
    }

    public void carregarMedicos() {
        model.limpar();
        for (Medico m : daoPessoa.listarMedico()) {
            model.addConsultas(m);
        }
    }

    public void gerenciandoTabela() {
        TbMedico.setModel(model);
        TbMedico.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < TbMedico.getColumnCount(); i++) {
            TbMedico.getColumnModel().getColumn(i).setResizable(false);
        }

        TbMedico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carregarMedicos();
        Font fonteTabela = new Font("Segoe UI", Font.PLAIN, 16);
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 20);
        TbMedico.setFont(fonteTabela);

        JTableHeader cabecalho = TbMedico.getTableHeader();
        cabecalho.setFont(fonteTitulo);
        TbMedico.getTableHeader().setFont(fonteTabela);
        TbMedico.setRowHeight(28);
        DefaultTableCellRenderer centroRenderer = new DefaultTableCellRenderer();
        centroRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TbMedico.getColumn("Nome").setCellRenderer(centroRenderer);
        TbMedico.getColumn("Crm").setCellRenderer(centroRenderer);

    }

public void editarMedico() {
    int indice = TbMedico.getSelectedRow();

    if (indice >= 0) {
        
        Medico medicoSelecionado = model.pegarMedico(indice);
        
        
        Medico medicoOriginal = daoPessoa.buscarPorId(medicoSelecionado.getId_pessoa());
        if (medicoOriginal == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                "Erro: médico não encontrado no banco.");
            return;
        }

        
        String senhaOriginal = medicoOriginal.getSenha();

        edNome.setText(medicoOriginal.getNome());
        edCpf.setText(medicoOriginal.getCpf());
        edEmail.setText(medicoOriginal.getEmail());
        edDataNa.setText(medicoOriginal.getData_nascimento().toString());
        edCrm.setText(medicoOriginal.getCrm());
        edAreaAtuacao.setText(medicoOriginal.getTipo_medico());

        edSenha.setEnabled(false);
        edSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Não é possível editar a senha.");

        BtCadastrar.setText("Confirmar edição");


        for (ActionListener al : BtCadastrar.getActionListeners()) {
            BtCadastrar.removeActionListener(al);
        }

        BtCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    medicoOriginal.setNome(edNome.getText());
                    medicoOriginal.setCpf(edCpf.getText());
                    medicoOriginal.setEmail(edEmail.getText());
                    medicoOriginal.setData_nascimento(LocalDate.parse(edDataNa.getText()));
                    medicoOriginal.setCrm(edCrm.getText());
                    medicoOriginal.setTipo_medico(edAreaAtuacao.getText());
                    medicoOriginal.setSenha(senhaOriginal);
                    medicoOriginal.setClinica(daoClinica.selecionar());

                    
                    if (daoPessoa.editar(medicoOriginal)) {
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                                "Médico editado com sucesso.");

                        
                        edNome.setText("");
                        edCpf.setText("");
                        edEmail.setText("");
                        edDataNa.setText("");
                        edCrm.setText("");
                        edSenha.setText("");
                        edAreaAtuacao.setText("");

                       
                        carregarMedicos();
                        BtCadastrar.setText("Cadastrar");

                        
                        for (ActionListener al : BtCadastrar.getActionListeners()) {
                            BtCadastrar.removeActionListener(al);
                        }
                        BtCadastrar.addActionListener(listenerCadastrarOriginal);

                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                                "Erro ao editar o médico.");
                    }

                } catch (Exception ex) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                            "Erro ao processar os dados. Verifique os campos.");
                    ex.printStackTrace();
                }
            }
        });

    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER,
                "Selecione um médico para editar.");
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        panelEsquerda = new javax.swing.JPanel();
        lNome = new javax.swing.JLabel();
        edNome = new javax.swing.JTextField();
        lCpf = new javax.swing.JLabel();
        edCpf = new javax.swing.JTextField();
        lEmail = new javax.swing.JLabel();
        edEmail = new javax.swing.JTextField();
        lDataNa = new javax.swing.JLabel();
        edDataNa = new javax.swing.JTextField();
        lCrm = new javax.swing.JLabel();
        edCrm = new javax.swing.JTextField();
        lSenha = new javax.swing.JLabel();
        BtCadastrar = new javax.swing.JButton();
        edAreaAtuacao = new javax.swing.JTextField();
        lAreadeAtuacao = new javax.swing.JLabel();
        edSenha = new javax.swing.JTextField();
        panelDireita = new javax.swing.JPanel();
        btExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TbMedico = new javax.swing.JTable();

        panelEsquerda.setPreferredSize(new java.awt.Dimension(357, 395));

        lNome.setText("Nome");

        lCpf.setText("Cpf");

        lEmail.setText("Email");

        lDataNa.setText("Data de nascimento");

        lCrm.setText("Crm");

        lSenha.setText("Senha");

        BtCadastrar.setText("Cadastrar");

        lAreadeAtuacao.setText("Area de Atuação");

        javax.swing.GroupLayout panelEsquerdaLayout = new javax.swing.GroupLayout(panelEsquerda);
        panelEsquerda.setLayout(panelEsquerdaLayout);
        panelEsquerdaLayout.setHorizontalGroup(
            panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEsquerdaLayout.createSequentialGroup()
                .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(BtCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEsquerdaLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lNome)
                            .addComponent(edNome, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(lCpf)
                            .addComponent(edCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(lEmail)
                            .addComponent(edEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(lDataNa)
                            .addComponent(edDataNa, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(lCrm)
                            .addComponent(edCrm, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(lSenha)
                            .addComponent(lAreadeAtuacao)
                            .addComponent(edAreaAtuacao, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(edSenha))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        panelEsquerdaLayout.setVerticalGroup(
            panelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEsquerdaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lNome)
                .addGap(6, 6, 6)
                .addComponent(edNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lCpf)
                .addGap(6, 6, 6)
                .addComponent(edCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lEmail)
                .addGap(6, 6, 6)
                .addComponent(edEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lDataNa)
                .addGap(6, 6, 6)
                .addComponent(edDataNa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lCrm)
                .addGap(6, 6, 6)
                .addComponent(edCrm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(edSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lAreadeAtuacao)
                .addGap(6, 6, 6)
                .addComponent(edAreaAtuacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtCadastrar)
                .addGap(12, 12, 12))
        );

        btExcluir.setText("Editar");

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
                    .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelDireitaLayout.setVerticalGroup(
            panelDireitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDireitaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btExcluir)
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtCadastrar;
    private javax.swing.JPanel MainPanel;
    public javax.swing.JTable TbMedico;
    public javax.swing.JButton btExcluir;
    public javax.swing.JTextField edAreaAtuacao;
    public javax.swing.JTextField edCpf;
    public javax.swing.JTextField edCrm;
    public javax.swing.JTextField edDataNa;
    public javax.swing.JTextField edEmail;
    public javax.swing.JTextField edNome;
    private javax.swing.JTextField edSenha;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lAreadeAtuacao;
    private javax.swing.JLabel lCpf;
    private javax.swing.JLabel lCrm;
    private javax.swing.JLabel lDataNa;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lSenha;
    private javax.swing.JPanel panelDireita;
    private javax.swing.JPanel panelEsquerda;
    // End of variables declaration//GEN-END:variables
}
