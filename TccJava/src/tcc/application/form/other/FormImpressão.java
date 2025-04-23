package tcc.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class FormImpressão extends javax.swing.JPanel implements Printable {

    private tcc.application.form.ControllerPrincipal app;
    private JPanel panelImpressao;
    private JTextArea areaTexto;

    public FormImpressão() {
        initComponents();

        app.setI(0);
        configurarLayout();
    }

    public void configurarLayout() {
        // Painel principal com margens
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // margem em torno do painel de impressão

        // Painel de impressão
        panelImpressao = new JPanel(new BorderLayout());
        panelImpressao.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelImpressao.putClientProperty("FlatLaf.style", "arc:25");

        // Área de texto preenchendo quase tudo
           String receitaTemplate = """
            RECEITA MÉDICA

        Dr(a). _______________________________________
        CRM: _____________ UF: _____

        Paciente: ____________________________________
        Idade: _______    Sexo: _______
        Data: ___/___/_____

        ----------------------------------------------
        Prescrição:

        1. ___________________________________________
        Posologia: ________________________________

        2. ___________________________________________
        Posologia: ________________________________

        3. ___________________________________________
        Posologia: ________________________________

        ----------------------------------------------
        Observações:
        ______________________________________________
        ______________________________________________

        Assinatura e carimbo do médico:
        ______________________________________________
        """;
        areaTexto = new JTextArea(receitaTemplate);

        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        areaTexto.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        panelImpressao.add(scrollPane, BorderLayout.CENTER);

        // Botão de impressão
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> imprimirPainel());
        panelImpressao.add(btnImprimir, BorderLayout.SOUTH);

        // Adiciona o painel de impressão ao painel principal
        painelPrincipal.add(panelImpressao, BorderLayout.CENTER);

        // Adiciona ao JFrame
        setLayout(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        panelImpressao.printAll(g2d);

        return PAGE_EXISTS;
    }

    public void imprimirPainel() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
